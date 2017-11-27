package ue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;


/** Originally written by Yuki Oyama (yuki.oyama@epfl.ch) and edited by Nicholas Molyneaux (nicholas.molyneauy@epfl.ch)
 *
 * Solves the user equilibrium problem for a given network and demand.
 *  The network is specified with the following elements:
 *   - a map containing the node IDs and nodes
 *   - a map containing the link IDs and links
 *
 *   The demand is specified as an OD matrix. For each OD pair, the number of people
 *   wishing to accomplish that trip. This demand is specified:
 *    - a map of OD objects where each OD object stores the flow
 */
public class UE {

    // list of node IDs
    private List<Long> nodeIDList;

    // Map storing the nodes
    private Map<Long, Node> nodeMap;

    // List of link IDs
    private List<Long> linkIDList;

    // Map storingt he links
    private Map<Long, Link> linkMap;

    // List of OD Ids
    private List<Long> odIDList;

    // Map storing the OD data
    private Map<Long, OD> odMap;

    // Solution of the UE problem in terms of flows
    private double[] flowSolution;

    /** Getter method for accessing the flows
     *
     * @return flows at equilibrium
     */
    public double[] getFlowSolution() { return flowSolution; }

    /** Getter for accessing the flows on each link in a map.
     * The key is the link ID while the value is the flow on the link.
     *
     * @return hash map storing the flows on each link
     */
    public HashMap<Long, Double> getFlowSolutionAsMap() {
        HashMap<Long, Double> res = new HashMap<>();
        for (int i = 0; i < L; i++) {
            res.put(linkIDList.get(i), flowSolution[i]);
        }
        return res;
    }

    // Solution of the UE problem in terms of travel times
    private double[] travelTime;

    /** Getter method for accessing the travel times
     *
     * @return travel times at equilibrium
     */
    public double[] getTravelTimes() { return travelTime; }

    /** Getter for accessing the travel times on each link in a map.
     * The key is the link ID while the value is the travel time on the link.
     *
     * @return hash map storing the travel time of each link
     */
    public HashMap<Long, Double> getTravelTimesAsMap() {
        HashMap<Long, Double> res = new HashMap<>();
        for (int i = 0; i < L; i++) {
            res.put(linkIDList.get(i), travelTime[i]);
        }
        return res;
    }

    // number of nodes
    private int N;

    // number of OD pairs
    private int M;

    // number of links
    private int L;

    // Should the code print information during the solving process
    private Boolean verbose = false;

    /** Setter method for the verbose parameters.
     *
     * @param v Boolean indicating whether to be verbose or not
     */
    public void setVerbose(Boolean v) { this.verbose = v; }

    // Graph object for finding the shortest path
    private DefaultDirectedWeightedGraph<Node, Link> graph;

    // Shortest path algorithm
    private DijkstraShortestPath<Node, Link> shortestPathBuilder;

    /** Computes the shortest path between origin and destination.
     * The same graph and algorithm can be used, the weights of each edge must be updated.
     *
     * @param o origin node
     * @param d destination node
     * @return Shortest path represented as a list of links
     */
    private List<Long> getShortestPath(Node o, Node d) {
        List<Long> res = new ArrayList<>();
        for (Link l: this.shortestPathBuilder.getPath(o,d).getEdgeList()) {
            res.add(l.getLinkID());
        }
        return res;
    }

    public UE(Map<Long, Node> nodeMapInput, Map<Long, Link> linkMapInput, Map<Long, OD> odMapInput, Boolean v) {

        //
        this.verbose = v;

        if (verbose) System.out.println("Creation of user equilibrium assignment solver.");

        // Passes the arguments to the members
        this.nodeIDList = new ArrayList<>();
        this.nodeIDList.addAll(nodeMapInput.keySet());
        this.nodeMap = nodeMapInput;
        this.linkIDList = new ArrayList<>();
        this.linkIDList.addAll(linkMapInput.keySet());
        this.linkMap = linkMapInput;
        this.odIDList = new ArrayList<>();
        this.odIDList.addAll(odMapInput.keySet());
        this.odMap = odMapInput;

        // Computes the sizes of the input data
        this.N = nodeIDList.size();
        this.L = linkIDList.size();
        this.M = odIDList.size();

        int zeroFlows = 0;
        for (OD od: this.odMap.values()) {
            if (od.getFlow() == 0.0) zeroFlows++;
        }

        if (this.verbose) {
            System.out.println("Problem to solve has:");
            System.out.println("  - " + N + " nodes");
            System.out.println("  - " + L + " links");
            System.out.println("  - " + M + " od pairs, of which " + zeroFlows + " have 0.0 flows");
        }

        // builds graph for jgrapht Dijkstra algorithm
        this.graph = new DefaultDirectedWeightedGraph<Node, Link>(Link.class);
        for (Node n: this.nodeMap.values()) { graph.addVertex(n); }
        for (Link l: this.linkMap.values()) {
            graph.addEdge(this.nodeMap.get(l.getOrigin()), this.nodeMap.get(l.getDestination()), l);
            graph.setEdgeWeight(l, l.getFree());
        }

        // builds shortest path algorithm based on network
        this.shortestPathBuilder = new DijkstraShortestPath<Node, Link>(this.graph);

    }

    /** In place update of the travel times on the links. Based on the flows on each links, updates the
     * time taken to travers the link. Sets the values of travel times on each link object.
     *
     * @param currentFlow current flows on each link
     */
    private void updateTravelTimes(double[] currentFlow) {
        for (int a = 0; a < L; a++) {
            long linkID = linkIDList.get(a);
            Link link = linkMap.get(linkID);
            double cost = link.getFree() * (1 + link.getAlpha() * Math.pow(currentFlow[a] / link.getCapacity(), link.getPower()));
            //System.out.println(link.getLinkID() + ", " +  cost);
            link.setCost(cost);
            this.graph.setEdgeWeight(link, cost);
            linkMap.put(linkID, link);
        }
    }

    /** Based on the current solution, performs the flow assignment. The users take the shortest path from origin
     * to destination.
     *
     * @return flows on each link
     */
    private double[] assignFlows() {
        double[] flowSolution = new double[L];
        for (int m = 0; m < M; m++) {
            long odID = odIDList.get(m);
            OD od = odMap.get(odID);
            double flow = od.getFlow();
            long origin = od.getOrigin();
            long destination = od.getDestination();
            //finding shortest path by dijkstra algorithm
            //List<Long> path = Dijkstra.getPath(nodeIDList, nodeMap, linkIDList, linkMap, origin, destination);
            List<Long> path = getShortestPath(this.nodeMap.get(origin), this.nodeMap.get(destination));
            //add flow to every link in the shortest path
            for (Long l: path) {
                flowSolution[linkIDList.indexOf(l)] += flow;
            }// for l
        }// for m
        return flowSolution;
    }

    /** Performs the first assignment based on zero flows.
     * Initializes the flows to 0 on each link, then computes the travel times for each link. Based on this
     * initial computation of travel times, the new flows are assigned.
     *
     * @return Array containing the flows on each link.
     */
    private double[] computeInitialSolution() {
        if (verbose) System.out.print("Calculating initial solution...");

        // zero flow (when initializing an array of Double it is filled with 0 by default)
        double[] initialFlows = new double[L];

        // update travel time
        updateTravelTimes(initialFlows);

        // initial flows
        double[] initialSolution = assignFlows();

        if (verbose) System.out.println(" done !");

        return initialSolution;
    }

    /** Iterative procedure to compute the user equilibrium in terms of travel times.
     * All the modifications are done in place, hence there is no arguments and no output from this method.
     * The flowSolution variable is updated at each iteration.
     *
     */
    private void computeSolution() {
    // TODO: add solver parameters as argument
        int maxIterations = 10000;
        double diff_max = 10000;
        double criteriaIndividual = 0.0001;
        double criteriaTotal = 0.001;



        if (verbose) {
            System.out.println("Parameters for solver are:");
            System.out.println("  - relative individual increment = " + criteriaIndividual);
            System.out.println("  - relative total increment = " + criteriaTotal);
            System.out.println("  - max iterations = " + maxIterations);
            System.out.print("Started solver iterations...");

        }

        // iteration counter
        int iteration = 0;
        double total_diff = 10000;

        // Iterative procedure. Two stopping criterion are used:
        // - maximum number of iterations
        // - improvement compared to the previous solution
        while ( (!(diff_max < criteriaIndividual) || !(total_diff < criteriaTotal)) && iteration <= maxIterations ) {

            // increment counter
            iteration++;

            // update travel times based on current solution
            updateTravelTimes(flowSolution);

            // assign flows based on updated travel times
            double[] y = assignFlows();

            // Unidimensional search by Golden Section method
            double[] newSolution = GoldenSection.getNewFlow(flowSolution, y, linkIDList, linkMap, nodeIDList);

            // check for convergence
            diff_max = 0.0;
            total_diff = 0.0;
            //System.out.println("Printing improvements");
            for (int a = 0; a < L; a++) {
                double diff = 0;
                //System.out.println(newSolution[a] + ", " + flowSolution[a]);
                if (flowSolution[a] == 0) {
                    diff = Math.abs(newSolution[a] - flowSolution[a]);
                } else {
                    diff = Math.abs((newSolution[a] - flowSolution[a]) / flowSolution[a]);
                }//if
                //System.out.println(diff + ", " + total_diff);

                total_diff = total_diff + diff;
                if (diff > diff_max) {
                    diff_max = diff;
                }
            }

            //update solution
            flowSolution = newSolution;
        }// while

        if (verbose) {
            System.out.print(" done ! \nFinished iterating");
            System.out.println("  - number of iterations: " + String.valueOf(iteration));
            System.out.println("  - final total improvement: " + String.valueOf(total_diff));
        }
    }

    /** Actually does the work. Call this method to solve the problem.
     * The two variables containing interesting data are flowSolution and travelTimes.
     */
    public void solve() {

        // Computes initial solution based on zero flows
        flowSolution = computeInitialSolution();

        // calls the iterative procedure
        computeSolution();

        // collects the travel times of the final solution into an array
        travelTime = new double[L];
        for (int a = 0; a < L; a++) {
            long linkID = linkIDList.get(a);
            Link link = linkMap.get(linkID);
            travelTime[a] = link.getFree() * (1 + link.getAlpha() * Math.pow(flowSolution[a] / link.getCapacity(), link.getPower()));
        }
    }
}
