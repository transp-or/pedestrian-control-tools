package ue;

import org.jgrapht.graph.DefaultWeightedEdge;

public class Link extends DefaultWeightedEdge {
	private long linkID;
	private long origin;
	private long destination;
	private double capacity;
	private double length;
	private double free; //free flow travel time
	private double alpha; //parameter1
	private double power; //parameter2
	private double cost;
	private double topSpeed;
	private int cat; //1 = walking, 2 = cmw, 3 = amw


	public long getLinkID() {
		return linkID;
	}
	public void setLinkID(long linkID) {
	    this.linkID = linkID;
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

	public double getCapacity() {
		return capacity;
	}
	public void setCapacity(double capacity) {
	    if (capacity <= 0.0) {throw new IllegalArgumentException("Capacity <= than 0 for link " + this.linkID + "(" + capacity + ")");}
	    this.capacity = capacity;
	}

	public double getLength() {
		return length;
	}
	public void setLength(double length) {
	    if (length <= 0.0) {throw new IllegalArgumentException("Length <= than 0 for link " + this.linkID);}
	    this.length = length;
	}

	public double getFree() {
		return free;
	}
	public void setFree(double free) {
	    if (free <= 0.0) {throw new IllegalArgumentException("Free flow travel time <= than 0 for link " + this.linkID);}
	    this.free = free;
	}

	public double getAlpha() {
		return alpha;
	}
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public double getPower() {
		return power;
	}
	public void setPower(double power) {
		this.power = power;
	}

	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
	    if (cost < 0.0) {throw new IllegalArgumentException("Cost < than 0 for link " + this.linkID);}
	    this.cost = cost;
	}

	public double getTopSpeed() {
		return topSpeed;
	}
	public void setTopSpeed(double topSpeed) {
		this.topSpeed = topSpeed;
	}

	public double getCat() {
		return cat;
	}
	public void setCat(int cat) {
		this.cat = cat;
	}


}
