package ue;

import java.util.List;
import java.util.Map;

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
			// integrate of t(x) from 0 to v[a]
			double za = link.getFree() * (
					v[a] + 
					link.getAlpha() * link.getCapacity() / (link.getPower() + 1) *  
					Math.pow(v[a]/link.getCapacity(), link.getPower() + 1)
					);
			// z = sum of za
			zx = zx + za;
		}//for
	
		//return
		return zx;
	}//z
}
