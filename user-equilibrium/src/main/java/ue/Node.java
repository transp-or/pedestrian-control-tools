package ue;
import java.util.*;

public class Node {
	private long nodeID;
	private int aff; //searched or not
	private double cost;
	private long pointer;
	private List<Long> outgoingList;
	
	public long getNodeID() {
		return nodeID;
	}
	public void setNodeID(long nodeID) {
		this.nodeID = nodeID;
	}

	public int getAff(){
		return aff;
	}
	public void setAff(int aff){
		this.aff = aff;
	}

	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
	    assert(cost > 0.0);
	    this.cost = cost;
	}

	public long getPointer() {
		return pointer;
	}
	public void setPointer(long pointer) {
		this.pointer = pointer;
	}

	public List<Long> getOutgoingList(){
		return outgoingList;
	}
	public void setOutgoingList(List<Long> outgoingList){
		this.outgoingList = outgoingList;
	}
}
