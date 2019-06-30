package de.HTW;

import lenz.htw.cywwtaip.world.GraphNode;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Stack;

public class dijkstra {

    private ArrayList<TNode> unsettledNodes = new ArrayList<>();
    private ArrayList<TNode> settledNodes = new ArrayList<>();
    private TNode sourceNode, targetNode;
    private GraphNode[] graph;
    private ArrayList<TNode> graphNodes = new ArrayList<>();


    public dijkstra(GraphNode[] sourceGraph, TNode rootNode, TNode targetNode) {

        graph = sourceGraph;


        for (int i = 0; i < graph.length; i++) {
            TNode node = new TNode(graph[i]);
            if (node.hashCode() == rootNode.hashCode()) {

                node.distance = 0;
            }
            //Remove obstacles from Map?
            if (!node.blocked) {
                graphNodes.add(node);
            }


        }
        this.sourceNode = rootNode;
        this.sourceNode.distance = 0;

        unsettledNodes.addAll(graphNodes);

        calculateShortestPathFromSource();
        System.out.printf("finished evaluating");
    }

    private void calculateShortestPathFromSource() {


        int i = 0 ;
        while (unsettledNodes.size() != 0) {
            TNode currentNode = getNodewithShortestDistance(graphNodes);


            currentNode.setNeighbors(findSiblingNode(currentNode).neighbors);
            unsettledNodes.remove(currentNode);

            if (currentNode == targetNode) {

                return;
            }
            settledNodes.add(currentNode);
            for (TNode neighbor : currentNode.neighbors)

                if (!settledNodes.contains(neighbor)) {

                    double cost = currentNode.distance + getLength(currentNode, neighbor);

                    if (cost < neighbor.distance) {

                        neighbor.distance = cost;
                        neighbor.predecessor = currentNode;


                        updateTnode(neighbor);
                    }

                }
            System.out.printf("evaluated: "+ i++ + " nodes" );
        }


    }

   public ArrayList<TNode> createShortestPath(){

       ArrayList<TNode> path = new ArrayList<>();
       path.add(targetNode);
       TNode u = targetNode;
       while (u.predecessor != null || u.hashCode() == sourceNode.hashCode()) {
           u = u.predecessor;
           path.add(u);
       }
       return path;

    }


    private double getLength(TNode no1, TNode no2) {


        Vector3D v1 = new Vector3D(no1.x, no1.y, no1.z);
        Vector3D v2 = new Vector3D(no2.x, no2.y, no2.z);
        return v1.distance(v2);
    }

    /*
    Get the node with the shortest distance from the stack
     */
    private TNode getNodewithShortestDistance(ArrayList<TNode> Nodes) {

        TNode shortestDistNode = new TNode();
        for (TNode node : Nodes) {

            if (node.distance < shortestDistNode.distance) {

                shortestDistNode = node;
                // if I am the source node then return me immedeatly;
                if (shortestDistNode.distance == 0) {

                    return shortestDistNode;

                }
            }

        }

        return shortestDistNode;


    }


    void updateTnode(TNode source) {

        for (TNode node : graphNodes) {


            if (node.hashCode() == source.hashCode()) {
                node.predecessor = source.predecessor;
                node.distance = source.distance;
                break;
            }

        }

        for (TNode node : unsettledNodes) {


            if (node.hashCode() == source.hashCode()) {
                node.predecessor = source.predecessor;
                node.distance = source.distance;
                break;
            }

        }

    }

    GraphNode findSiblingNode(TNode source) {

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

    /**
     * Funktion Dijkstra(Graph, Startknoten):
     * 2      initialisiere(Graph,Startknoten,abstand[],vorgänger[],Q)
     * 3      solange Q nicht leer:                       // Der eigentliche Algorithmus
     * 4          u:= Knoten in Q mit kleinstem Wert in abstand[]
     * 5          entferne u aus Q                                // für u ist der kürzeste Weg nun bestimmt
     * 6          für jeden Nachbarn v von u:
     * 7              falls v in Q:                            // falls noch nicht berechnet
     * 8                 distanz_update(u,v,abstand[],vorgänger[])   // prüfe Abstand vom Startknoten zu v
     * 9      return vorgänger[]
     * <p>
     * public TNode start(GraphNode[] graph, int start){
     * //Init im Konstruktor
     * TNode predecessor= new TNode(graph[0]); //fix result should be array
     * if(Q != null) {
     * while ( !Q.isEmpty()){
     * //todo Tocode
     * TNode u = Q.poll();
     * Q.remove(u);
     * for (TNode v : u.neighbors) {
     * if (Q.contains(v)) {
     * distance_update(u, v);
     * }
     * }
     * }
     * }
     * return predecessor;
     * }
     */


    private GraphNode smallestDistance(PriorityQueue<GraphNode> sta) {
        /* for each node check the distance and return the closest
         * If stack is sorted correctly simply pop*/
       /*for( TNode a : sta){
            a.distance=Double.MAX_VALUE;
            a.predecessor=null;
        }*/
        return sta.poll();//todo create comparator
    }

    /**
     * 1  Methode distanz_update(u,v,abstand[],vorgänger[]):
     * 2      alternativ:= abstand[u] + abstand_zwischen(u, v)   // Weglänge vom Startknoten nach v über u
     * 3      falls alternativ < abstand[v]:
     * 4          abstand[v]:= alternativ
     * 5          vorgänger[v]:= u
     */
    public void distance_update(TNode u, TNode v) {
        double alternativ = u.distance + distance_between(u, v);
        if (alternativ < v.distance) {
             /*
             TNode cache = new TNode(); //Maybe create better copy of v?
             Q.remove(v);
             cache.distance = alternativ;
             cache.predecessor = u;
             Q.add(cache);*/
        }
    }

    private double distance_between(TNode no1, TNode no2) {
        Vector3D v1 = new Vector3D(no1.x, no1.y, no1.z);
        Vector3D v2 = new Vector3D(no2.x, no2.y, no2.z);
        return v1.distance(v2);
    }

    /**
     * Funktion erstelleKürzestenPfad(Zielknoten,vorgänger[])
     * 2   Weg[]:= [Zielknoten]
     * 3   u:= Zielknoten
     * 4   solange vorgänger[u] nicht null:   // Der Vorgänger des Startknotens ist null
     * 5       u:= vorgänger[u]
     * 6       füge u am Anfang von Weg[] ein
     * 7   return Weg[]
     */
    public TNode[] createShortestPath(TNode target, TNode predecessor[]) {
        PriorityQueue<TNode> path = new PriorityQueue<>();
        path.add(target);
        TNode u = target;
        while (u.predecessor != null) {
            u = u.predecessor;
            path.add(u);
        }
        return (TNode[]) path.toArray();
    }

    //graphnode not identified uniquely by position?
    public GraphNode search(GraphNode target, GraphNode predecessor[]) {
        GraphNode u;
        if (predecessor != null && predecessor.length > 0) return null;
        for (int i = 0; i < predecessor.length; i++) {
            if (predecessor[i].equals(target)) return predecessor[i];
        }
        return null;
    }

}
