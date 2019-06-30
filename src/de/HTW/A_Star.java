package de.HTW;

import lenz.htw.cywwtaip.world.GraphNode;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class A_Star {

    HashSet<TNode> openSet;
    HashMap<Integer, Float> fScore;
    HashMap<Integer, Float> gscore = new HashMap<>();

    private GraphNode[] graph;
    private TNode Start, Goal;


    public A_Star(GraphNode[] sourceGraph, TNode start, TNode goal) {
        graph = sourceGraph;
        gscore = new HashMap<>();
        this.Start = start;

        this.Goal = goal;
        for (int i = 0; i < graph.length; i++) {

            //Remove obstacles from Map?
            if (!graph[i].blocked) {
                TNode node = new TNode(graph[i]);
                node.id = i;
                if (node.hashCode() == Start.hashCode()) {
                    Start.id = i;
                    gscore.put(node.id, 0.0f);
                    node.gScore = 0;
                    Start.gScore = 0;

                } else {
                    if (node.hashCode() == Goal.hashCode()) {
                        Goal.id = i;
                        Goal.gScore = Float.MAX_VALUE;
                    }
                    gscore.put(node.id, Float.MAX_VALUE);
                    node.gScore = Float.MAX_VALUE;
                }



            }


        }
        this.Start = start;
        this.Start.distance = 0;
        this.Goal = goal;


    }

    public ArrayList<TNode> reconstruct_path(HashMap<Integer, TNode> cameFrom, TNode current) {
        ArrayList<TNode> total_path = new ArrayList<TNode>();
        total_path.add(current);
        while (cameFrom.containsKey(current.id)) {
            current = cameFrom.get(current.id);
            total_path.add(current);
        }
        //System.out.printf("finished Path");
        return total_path;
    }

    public ArrayList<TNode> A_Star() {
        // The set of nodes already evaluated
        HashSet<TNode> closedSet = new HashSet<TNode>();

        // The set of currently discovered nodes that are not evaluated yet.
        // Initially, only the start node is known.
        openSet = new HashSet<TNode>();
        openSet.add(Start);


        // For each node, which node it can most efficiently be reached from.
        // If a node can be reached from many nodes, cameFrom will eventually contain the
        // most efficient previous step.
        HashMap<Integer, TNode> cameFrom = new HashMap<>();

        // For each node, the cost of getting from the start node to that node.
        //<Id,cost>

        //init with infinity
        //gScore:=map with default
        //value of Infinity

        // The cost of going from start to start is zero.
        // done in init now
        // gscore.put(Start.id,0f);
        //gscore.get(start.id) =0;

        // For each node, the total cost of getting from the start node to the goal
        // by passing by that node. That value is partly known, partly heuristic.
        HashMap<Integer, Float> fScore = new HashMap<>();
        //fScore:=map with default
        //value of Infinity

        // For the first node, that value is completely heuristic.
        fScore.put(Start.id, heuristic_cost_estimate(Start, Goal));

        while (!openSet.isEmpty()) {
            TNode current = lowestFcost();// openSet.iterator() the node in openSet having the lowest fScore[] value;

            if (current.id == Goal.id|| closedSet.size() >= 1000) {
                return reconstruct_path(cameFrom, current);
            }

            openSet.remove(current);
            closedSet.add(current);
            current.setNeighbors(findSiblingNode(current).neighbors, graph);
            for (TNode nei : current.neighbors) {

                if (closedSet.contains(nei)) {
                    continue;        // Ignore the neighbor which is already evaluated.
                }

                // The distance from start to a neighbor
                float tentative_gScore = gscore.get(current.id) + dist_between(current, nei);

                if (!openSet.contains(nei)) {    // Discover a new node
                    openSet.add(nei);
                } else if (tentative_gScore >= gscore.get(nei.id)) {
                    continue;
                }

                // This path is the best until now. Record it!
                cameFrom.put(nei.id, current);
                //cameFrom[neighbor] :=current
                gscore.put(nei.id, tentative_gScore);
                nei.gScore = tentative_gScore;
                float fscore = gscore.get(nei.id) + heuristic_cost_estimate(nei, Goal);
                fScore.put(nei.id, fscore);
                nei.fScore = fscore;
            }
        }
        return null;
    }

    private TNode lowest() {
        if (openSet.size() == 1) return (TNode) openSet.iterator().next();
        Iterator it = openSet.iterator();
        TNode lowest = (TNode) it.next();
        while (it.hasNext()) {
            TNode testing = (TNode) it.next();
            if (fScore.get(lowest.id) > fScore.get(testing.id)) {
                lowest = testing;
            }
        }
        return lowest;
    }

    private TNode lowestFcost() {

        if (openSet.size() == 1) {
            return (TNode) openSet.iterator().next();
        }
        List<TNode> cache = new ArrayList<TNode>(openSet);

        // sort using Collections.sort(); method
        Collections.sort(cache);
        return cache.get(0);

    }

    private float dist_between(TNode no1, TNode no2) {
        Vector3D v1 = new Vector3D(no1.x, no1.y, no1.z);
        Vector3D v2 = new Vector3D(no2.x, no2.y, no2.z);
        return (float) v1.distance(v2);
    }

    //
    private float heuristic_cost_estimate(TNode start, TNode goal) {
        float dx = Math.abs(start.x - goal.x);
        float dy = Math.abs(start.y - goal.y);
        float D = 1; //test performance  with D=6
        return D * (float) Math.sqrt(dx * dx + dy * dy);
    }

    private GraphNode findSiblingNode(TNode source) {

        if (source == null) {

            return null;
        }
        for (int i = 0; i < graph.length; i++) {

            if (source.hashCode() == graph[i].hashCode()) {

                return graph[i];

            }
        }
        System.out.printf("didnt find sibling");
        return null;


    }
}
