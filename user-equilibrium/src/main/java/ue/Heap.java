/**
 * Heap for data structure
 * @author Yuki Oyama
 * Latest update: 2017/11/01
 */

//package ue;
//import java.util.*;
/*
public class  Heap{
	//heap for nodeID
	public int[] heap;
	//current #elements
  	public int num;
  	//constractor
  	//as default, 1024 size of heap
  	public Heap(int linkMAX){
  		heap = new int [linkMAX];
  		num = 0;
  	}
	//
	 //TODO Insert key
	 // @param nodeIDList
	 // @param nodeMap
	 // @param nodeID
	 //
	public void insert(List<Long> nodeIDList, Map<Long, Node> nodeMap,long nodeID){
    	heap[num++] = nodeIDList.indexOf(nodeID);
    	int i = num, j = i / 2;
    	while(i > 1 && nodeMap.get(nodeIDList.get(heap[i-1])).getCost() < nodeMap.get(nodeIDList.get(heap[j-1])).getCost()){
    	  int t = heap[i-1];
    	  heap[i-1] = heap[j-1];
    	  heap[j-1] = t;
    	  i = j;
    	  j = i/2;
    	}
  	}

	//
	 // TODO Delete key
	 // @param nodeIDList
	 // @param node
	 // @param nodeID
	 //
	public void decreaseKey(List<Long> nodeIDList, Map<Long, Node> nodeMap, long nodeID){
		int nodePosition = 1;
		//Search node position in heap
		while(true){
			if(nodeID == nodeIDList.get(heap[nodePosition-1])){
				break;
			}
			nodePosition++;
		}
		//
    	int i=nodePosition,j=i/2;
    	while(i > 1 && nodeMap.get(nodeIDList.get(heap[i-1])).getCost() < nodeMap.get(nodeIDList.get(heap[j-1])).getCost()){
      	  int t = heap[i-1];
      	  heap[i-1] = heap[j-1];
      	  heap[j-1] = t;
      	  i = j;
      	  j = i / 2;
      	}
  	}

	//
	 // TODO Pick up and remove the top element
	 // @param nodeIDList
	 // @param nodeMap
	 // @return
	 //
	public long deleteMin(List<Long> nodeIDList, Map<Long, Node> nodeMap){
    	try{
			int r = heap[0];
	    	heap[0] = heap[--num];
	    	int i = 1, j = i * 2;
	    	while(j <= num){
	    		if(j + 1 <= num && nodeMap.get(nodeIDList.get(heap[j-1])).getCost() > nodeMap.get(nodeIDList.get(heap[j])).getCost()){
	    			j++;
	    		}
	    		if(nodeMap.get(nodeIDList.get(heap[i-1])).getCost() > nodeMap.get(nodeIDList.get(heap[j-1])).getCost()){
					int t = heap[i-1];
					heap[i-1] = heap[j-1];
					heap[j-1] = t;
	    	  	}
	    	  	i = j;
	    	  	j = i * 2;
	    	}
	    	return nodeIDList.get(r);
    	}catch (ArrayIndexOutOfBoundsException e){
    		return 0;
    	}

  	}
}*/
