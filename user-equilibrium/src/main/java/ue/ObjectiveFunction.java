package ue;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;

public class ObjectiveFunction {
	public static double getZ(double[] x, double[] y, double alpha, List<Long> linkIDList, Map<Long, Link> linkMap, List<Long> nodeIDLIst){
		//output
		double zx = 0;
		int L = linkIDList.size();
		
		//current solution
		double[] v = new double[L];
		
		for(int a = 0; a < L; a++){
			long linkID = linkIDList.get(a);
			Link link = linkMap.get(linkID);
			// current link flow
			v[a] = (1 - alpha) * x[a] + alpha * y[a];

			//if (v[a]== 0){return link.getFree();}

			// integrate of t(x) from 0 to v[a]
			double za = link.getFree() * (
					v[a] + 
					link.getAlpha() * link.getCapacity() / (link.getPower() + 1) *  
					Math.pow(v[a]/link.getCapacity(), link.getPower() + 1)
					);

			if (link.getCat() == 3){

				SimpsonIntegrator integrator = new SimpsonIntegrator();

				double t_0 = link.getLength()/1.34;
				double t_e = (link.getTopSpeed() - 0.7) / 0.43;
				double x_a = (Math.pow(link.getTopSpeed(), 2) - Math.pow(0.7, 2)) / (2 * 0.43);


				UnivariateFunction f = omega -> ((link.getLength() - 2*x_a)
						/ (link.getTopSpeed() + ((link.getLength()-2*x_a)/(t_0
						* (1+link.getAlpha()*Math.pow(omega/link.getCapacity(),link.getPower())))))) + 2*t_e;

                if (v[a]== 0){za=0;}
                else{


                za= integrator.integrate(1000, f, 0.0, v[a]);}

			}
			else if (link.getCat() == 2){

				SimpsonIntegrator integrator = new SimpsonIntegrator();

				double t_0 = link.getLength()/1.34;
				double t_e = (link.getTopSpeed() - 0.7) / 0.43;
				double x_a = (Math.pow(link.getTopSpeed(), 2) - Math.pow(0.7, 2)) / (2 * 0.43);


				UnivariateFunction f = omega -> ((link.getLength())
						/ (link.getTopSpeed() + (link.getLength()/(t_0
						* (1+link.getAlpha()*Math.pow(omega/link.getCapacity(),link.getPower()))))));


                if (v[a]== 0){za=0;}
                else {
                    za = integrator.integrate(1000, f, 0.0, v[a]);
                }

			}




			// z = sum of za
			zx = zx + za;
		}//for
	
		//return
		return zx;
	}//z
}
