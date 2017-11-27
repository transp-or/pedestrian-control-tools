/**
 * Dijkstra algorithm for shorterst path search
 * @author Yuki Oyama
 * Latest update: 2017/11/01
 */

package ue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/*
public class Dijkstra {
	public static List<Long> getPath(List<Long> nodeIDList, Map<Long, Node> nodeMap, List<Long> linkIDList, Map<Long, Link> linkMap, Long stnodeID, Long ennodeID){
		//output
		List<Long> path = new ArrayList<Long>();
		
		int N = nodeIDList.size();
		int L = linkIDList.size();
		
//		System.out.println(stnodeID);
//		System.out.println(ennodeID);
		

	     //STEP0: Initialization

		Heap heap = new Heap(L);
		
		//for all nodes, aff=0, cost=inf and pointer=0
		for(int n = 0; n < N; n++){
			Long nodeID = nodeIDList.get(n);
			Node node = nodeMap.get(nodeID);
			node.setAff(0);
			node.setCost(Double.POSITIVE_INFINITY);
			node.setPointer(0);
			List<Long> outgoingList = new ArrayList<Long>();
			for(int l = 0; l < L; l++){
		    	Long linkID = linkIDList.get(l);
		    	Link link = linkMap.get(linkID);
		    	if(link.getSource() == nodeID){
		    		outgoingList.add(linkID);
		    	}//if
		    }//for l
			node.setOutgoingList(outgoingList);
			nodeMap.put(nodeID, node);			
	    }//for n
		
		//start node
		Node stnode = nodeMap.get(stnodeID);
		stnode.setAff(1);
		stnode.setCost(0);
		stnode.setPointer(stnodeID);
		nodeMap.put(stnodeID, stnode);
		
		//current nodeID
		Long cnodeID = stnodeID;
		

	     //STEP1: Searching
	     	while(cnodeID != ennodeID){
			Node cnode = nodeMap.get(cnodeID);
			List<Long> outgoingList = cnode.getOutgoingList();
			for(int l = 0; l < outgoingList.size(); l++){
				Long linkID = outgoingList.get(l);
				Link link = linkMap.get(linkID);
				//next node
				Node nnode = nodeMap.get(link.getDestination());
				if(nnode.getAff() == 0){
					nnode.setAff(2);
					nnode.setCost(cnode.getCost() + link.getCost());
					nnode.setPointer(cnodeID);
					heap.insert(nodeIDList, nodeMap, nnode.getNodeID());
				}else if(nnode.getAff() == 2 && nnode.getCost() > cnode.getCost() + link.getCost()){
					nnode.setCost(cnode.getCost() + link.getCost());
					nnode.setPointer(cnodeID);
					heap.decreaseKey(nodeIDList, nodeMap, nnode.getNodeID());
				}
				nodeMap.put(nnode.getNodeID(), nnode);
			}//for l
			//determine node with minimum cost
			long minnodeID = heap.deleteMin(nodeIDList, nodeMap);
			Node minnode = nodeMap.get(minnodeID);
			minnode.setAff(1);
			nodeMap.put(minnodeID, minnode);
			//update current node
			cnodeID = minnodeID;
		}//while
		
		//STEP2: Output

		long element = ennodeID;
		while(element != stnodeID){
			Node node = nodeMap.get(element);
			long pointer = node.getPointer();
			Node pnode = nodeMap.get(pointer);
			List<Long> outgoingList = pnode.getOutgoingList();
			long elinkID = 0;
			for(int l = 0; l < outgoingList.size(); l++){
				Long linkID = outgoingList.get(l);
				Link link = linkMap.get(linkID);
				if(link.getDestination() == element){
					elinkID = link.getLinkID();
				}//if
			}//for l
			//add link to path set
			path.add(elinkID);
			//update element node
			element = pointer;
		}//while
		
		//return
		return path;
	}
}
*/