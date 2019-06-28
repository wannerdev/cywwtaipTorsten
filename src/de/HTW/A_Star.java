package de.HTW;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.*;

public class A_Star {

    HashSet<TNode> openSet;
    HashMap<Integer,Float> fScore;

    public PriorityQueue<TNode> reconstruct_path(HashMap<Integer, TNode>cameFrom, TNode current) {
        PriorityQueue<TNode> total_path = new PriorityQueue<TNode>();
        total_path.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            total_path.add(current);
        }
        return total_path;
    }

    public PriorityQueue<TNode> A_Star(TNode start, TNode goal) {
        // The set of nodes already evaluated
        HashSet<TNode> closedSet = new HashSet<TNode>();

        // The set of currently discovered nodes that are not evaluated yet.
        // Initially, only the start node is known.
        openSet = new HashSet<TNode>();
        openSet.add(start);


        // For each node, which node it can most efficiently be reached from.
        // If a node can be reached from many nodes, cameFrom will eventually contain the
        // most efficient previous step.
        HashMap<Integer, TNode> cameFrom = new HashMap<>();

        // For each node, the cost of getting from the start node to that node.
        //<Id,cost>
        HashMap<Integer,Float> gscore = new HashMap<>();
        //init with infinity
        //gScore:=map with default
        //value of Infinity

        // The cost of going from start to start is zero.
        gscore.put(start.id,0f);
        //gscore.get(start.id) =0;

        // For each node, the total cost of getting from the start node to the goal
        // by passing by that node. That value is partly known, partly heuristic.
        HashMap<Integer,Float> fScore = new HashMap<>();
        //fScore:=map with default
        //value of Infinity

        // For the first node, that value is completely heuristic.
        fScore.put(start.id,heuristic_cost_estimate(start, goal));

        while (!openSet.isEmpty()) {
            TNode current=lowest();// openSet.iterator() the node in openSet having the lowest fScore[] value;
            if (current.equals(goal)) {
                return reconstruct_path(cameFrom, current);
            }

            openSet.remove(current);
            closedSet.add(current);

            for (TNode nei : current.neighbors){

                if (closedSet.contains(nei)) {
                    continue;        // Ignore the neighbor which is already evaluated.
                }

                // The distance from start to a neighbor
                float tentative_gScore =gscore.get(current.id) + dist_between(current, nei);

                if (!openSet.contains(nei)){    // Discover a new node
                    openSet.add(nei);
                }else if (tentative_gScore >= gscore.get(nei.id)) {
                    continue;
                }

                // This path is the best until now. Record it!
                cameFrom.put(nei.id,current);
                //cameFrom[neighbor] :=current
                gscore.put(nei.id,tentative_gScore);
                fScore.put(nei.id, gscore.get(nei) + heuristic_cost_estimate(nei, goal));
            }
        }
        return null;
    }

    private TNode lowest() {
        if(openSet.size()==1)return (TNode)openSet.iterator().next();
        Iterator it = openSet.iterator();
        TNode lowest =(TNode)it.next();
        while(it.hasNext()){
            TNode testing = (TNode)it.next();
            if(fScore.get(lowest.id) >fScore.get(testing.id)){
                 lowest = testing;
            }
        }
        return lowest;
    }

    private float dist_between(TNode no1, TNode no2) {
        Vector3D v1 = new Vector3D(no1.x,no1.y,no1.z);
        Vector3D v2 = new Vector3D(no2.x,no2.y,no2.z);
        return (float)v1.distance(v2);
    }

    //Manhatten distance adapted
    private float heuristic_cost_estimate(TNode start, TNode goal) {
        return (Math.abs(start.x - goal.x) + Math.abs(start.y - goal.y) + Math.abs(start.z - goal.z)) / 2;
    }
}
