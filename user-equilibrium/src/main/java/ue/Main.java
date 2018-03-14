/**
 * Frank Wolfe algorithm for solving User Equilibrium problem
 * @author Yuki Oyama
 * Latest update: 2017/11/01
 */

package ue;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    // mod for charles
	public static void main(String[] args) {
		// TODO Network reduction

		/**
		 * STEP0: Network file setting
		 */
		//network folder
		String network = "SiouxFalls";
		//input data pass
		String nodefile = "data/network/" + network + "/node.csv";
		String linkfile = "data/network/" + network + "/link.csv";
		String odfile = "data/network/" + network + "/OD.csv";
		//output data pass
		String outputfile = "data/network/" + network + "/result/linkflow.csv";

		//Node reading
		ReadFile.setNode(nodefile);
		//List<Long> nodeIDList = ReadFile.getNodeIDList();
		Map<Long, Node> nodeMap = ReadFile.getNodeMap();
		//Link reading
		ReadFile.setLink(linkfile);
		//List<Long> linkIDList = ReadFile.getLinkIDList();
		Map<Long, Link> linkMap = ReadFile.getLinkMap();
		//OD reading
		ReadFile.setOD(odfile);
		//List<Long> odIDList = ReadFile.getODIDList();
		Map<Long, OD> odMap = ReadFile.getODMap();

		UE ueSolver = new UE(nodeMap, linkMap, odMap, true);
		ueSolver.setVerbose(true);
		ueSolver.solve();
	}
}
