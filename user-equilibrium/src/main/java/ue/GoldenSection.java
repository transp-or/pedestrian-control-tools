/**
 * Golden Section method for unidimensional search
 * @author Yuki Oyama
 * Latest update: 2017/11/01
 */

package ue;

import java.util.List;
import java.util.Map;

public class GoldenSection {
	public static double[] getNewFlow(double[] x, double[] y, List<Long> linkIDList, Map<Long, Link> linkMap, List<Long> nodeIDList){
		//output
		double[] newx = new double[linkIDList.size()];
		
		/**
	     * STEP0: Preparation
	     */
		//upper, lower bounds
		double upper = 1;
		double lower = 0;
		//golden ratio
		double n = (Math.sqrt(5) - 1)/(Math.sqrt(5) + 1);
		//step size
		double[] a = new double[4];
		double bound = upper - lower;
		double tau = bound * n;
		a[0] = lower;
		a[1] = lower + tau;
		a[2] = upper - tau;
		a[3] = upper;
		
		/**
	     * STEP1: Golden Section algorithm
	     */
	    while(bound > 0.0001){
	    	double[] b = new double[4];
	    	if(ObjectiveFunction.getZ(x, y, a[1], linkIDList, linkMap, nodeIDList) < ObjectiveFunction.getZ(x, y, a[2], linkIDList, linkMap, nodeIDList)){
	    		b[0] = a[0];
	            b[1] = a[2] - tau;
	            b[2] = a[1];
	            b[3] = a[2];
	    	}else{
	    		b[0] = a[1];
	            b[1] = a[2];
	            b[2] = a[1] + tau;
	            b[3] = a[3];
	    	}	            
	        a = b;
	        bound = a[3] - a[0];
	        tau = n * bound;
	    }
	    
	    /**
	     * STEP2: Update solution
	     */
	    for(int l = 0; l < linkIDList.size(); l++){
	    	newx[l] = (1 - a[0]) * x[l] + a[0] * y[l];
	    }
		return newx; 
	}
}
