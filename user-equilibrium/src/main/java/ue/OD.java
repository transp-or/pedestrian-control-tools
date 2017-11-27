package ue;

public class OD {
	private long odID;
	private long origin;
	private long destination;
	private double flow;
	
	public long getODID() {
		return odID;
	}
	public void setODID(long odID) {
		this.odID = odID;
	}

	public long getOrigin() {
		return origin;
	}
	public void setOrigin(long origin) {
		this.origin = origin;
	}

	public long getDestination() {
		return destination;
	}
	public void setDestination(long destination) {
		this.destination = destination;
	}

	public double getFlow() {
		return flow;
	}
	public void setFlow(double flow) {
	    if (flow <= 0.0) {throw new IllegalArgumentException("flow <= 0 for od pair " + this.odID + " (" + flow + ")");}
	    this.flow = flow;
	}
}
