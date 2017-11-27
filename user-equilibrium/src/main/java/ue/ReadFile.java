/**
 * Input file reading
 * @author Yuki Oyama
 * Latest update: 2017/11/01
 */

package ue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadFile {
	private static List<Long> linkIDList = new ArrayList<Long>();
	private static Map<Long, Link> linkMap = new HashMap<Long, Link>();
	private static List<Long> nodeIDList = new ArrayList<Long>();
	private static Map<Long, Node> nodeMap = new HashMap<Long, Node>();
	private static List<Long> odIDList = new ArrayList<Long>();
	private static Map<Long, OD> odMap = new HashMap<Long, OD>();
	
	/**
     * TODO link data reading
     */
    public static void setLink(String linkfile){
    	try {
    		//List and Map initialization
    		linkIDList.clear();
    		linkMap.clear();
	    	BufferedReader br = new BufferedReader(new FileReader(linkfile));//Preparing: FileReader line0;
	    	System.out.println("File reading：" + linkfile);
	    	br.readLine();//header
	    	String line;
    		while((line = br.readLine()) != null){
		    	line = line.replaceAll("\"", "");
		    	line = line.replaceAll(" ", "");
    			String[] sl = line.split(",");
    			Long linkID = Long.parseLong(sl[0]);
    			//LinkID
    			linkIDList.add(linkID);
    			Long source = Long.parseLong(sl[1]);
    			Long sink = Long.parseLong(sl[2]);
    			Double capacity = Double.parseDouble(sl[3]);
    			Double length = Double.parseDouble(sl[4]);
    			Double free = Double.parseDouble(sl[5]);
    			Double alpha = Double.parseDouble(sl[6]);
    			Double power = Double.parseDouble(sl[7]);
    			//Link data
    			Link link = new Link();
    			link.setLinkID(linkID);
    			link.setOrigin(source);
    			link.setDestination(sink);
    			link.setCapacity(capacity);
    			link.setLength(length);
    			link.setFree(free);
    			link.setAlpha(alpha);
    			link.setPower(power);
    			linkMap.put(linkID, link);
    		}
    		br.close();
    	}
    	catch( IOException e ) {
    		System.out.println("Fail to read linkfile");
    	}
	}
    
    /**
     * TODO node data reading
     */
    public static void setNode(String nodefile){
    	try {
    		//List and Map initialization
    		nodeIDList.clear();
    		nodeMap.clear();
	    	BufferedReader br = new BufferedReader(new FileReader(nodefile));//Preparing: FileReader line0;
	    	System.out.println("File reading：" + nodefile);
	    	br.readLine();//header
	    	String line;
    		while((line = br.readLine()) != null){
		    	line = line.replaceAll("\"", "");
		    	line = line.replaceAll(" ", "");
    			String[] sl = line.split(",");
    			Long nodeID = Long.parseLong(sl[0]);
    			//NodeID
    			nodeIDList.add(nodeID);
    			//Node data
    			Node node = new Node();
    			node.setNodeID(nodeID);
    			node.setCost(0);
    			node.setPointer(0);
    			nodeMap.put(nodeID, node);
    		}
    		br.close();
    	}
    	catch( IOException e ) {
    		System.out.println("Fail to read nodefile");
    	}
	}
    
    /**
     * TODO node data reading
     */
    public static void setOD(String odfile){
    	try {
    		//List and Map initialization
    		odIDList.clear();
    		odMap.clear();
	    	BufferedReader br = new BufferedReader(new FileReader(odfile));//Preparing: FileReader line0;
	    	System.out.println("File reading：" + odfile);
	    	br.readLine();//header
	    	String line;
    		while((line = br.readLine()) != null){
		    	line = line.replaceAll("\"", "");
		    	line = line.replaceAll(" ", "");
    			String[] sl = line.split(",");
    			Long odID = Long.parseLong(sl[0]);
    			//ODID
    			odIDList.add(odID);
    			Long origin = Long.parseLong(sl[1]);
    			Long destination = Long.parseLong(sl[2]);
    			Double flow = Double.parseDouble(sl[3]);
    			//OD data
				if (flow > 0.0) {
    			OD od = new OD();
    			od.setODID(odID);
    			od.setOrigin(origin);
    			od.setDestination(destination);
    			od.setFlow(flow);
    			odMap.put(odID, od);
				}
    		}
    		br.close();
    	}
    	catch( IOException e ) {
    		System.out.println("Fail to read odfile");
    	}
	}
    
  //link method
    public static List<Long> getLinkIDList() {
		return linkIDList;
	}
	public static void setLinkIDList(List<Long> linkIDList) {
		ReadFile.linkIDList = linkIDList;
	}
	public static Map<Long, Link> getLinkMap() {
		return linkMap;
	}
	public static void setLinkMap(Map<Long, Link> linkMap) {
		ReadFile.linkMap = linkMap;
	}
	//node method
	public static List<Long> getNodeIDList() {
		return nodeIDList;
	}
	public static void setNodeIDList(List<Long> nodeIDList) {
		ReadFile.nodeIDList = nodeIDList;
	}
	public static Map<Long, Node> getNodeMap() {
		return nodeMap;
	}
	public static void setNodeMap(Map<Long, Node> nodeMap) {
		ReadFile.nodeMap = nodeMap;
	}
	//od method
	public static List<Long> getODIDList() {
		return odIDList;
	}
	public static void setODIDList(List<Long> odIDList) {
		ReadFile.odIDList = odIDList;
	}
	public static Map<Long, OD> getODMap() {
		return odMap;
	}
	public static void setODMap(Map<Long, OD> odMap) {
		ReadFile.odMap = odMap;
	}


}
