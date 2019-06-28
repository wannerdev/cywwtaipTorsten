package de.HTW;

import lenz.htw.cywwtaip.world.GraphNode;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Stack;

public class dijkstra {

    private PriorityQueue<TNode> Q;
    /*
        1    Methode initialisiere(Graph,Startknoten,abstand[],vorgänger[],Q):
        2      für jeden Knoten v in Graph:
        3          abstand[v]:= unendlich
        4          vorgänger[v]:= null
        5      abstand[Startknoten]:= 0
        6      Q:= Die Menge aller Knoten in Graph
    *///init, start als int oder GraphNode?
    // double distance[], GraphNode predecessors[],
    public dijkstra(TNode graph[], TNode start, Stack<TNode> Q){

        for( TNode a :graph){
            a.distance=Double.MAX_VALUE;
            a.predecessor=null;
        }
        start.distance=0;
        Q.addAll(Arrays.asList(graph));
        //Remove obstacles from Map?
        for (TNode a: Q){
            if(a.blocked) Q.remove(a);
        }
    }

    /**
     *   Funktion Dijkstra(Graph, Startknoten):
     *  2      initialisiere(Graph,Startknoten,abstand[],vorgänger[],Q)
     *  3      solange Q nicht leer:                       // Der eigentliche Algorithmus
     *  4          u:= Knoten in Q mit kleinstem Wert in abstand[]
     *  5          entferne u aus Q                                // für u ist der kürzeste Weg nun bestimmt
     *  6          für jeden Nachbarn v von u:
     *  7              falls v in Q:                            // falls noch nicht berechnet
     *  8                 distanz_update(u,v,abstand[],vorgänger[])   // prüfe Abstand vom Startknoten zu v
     *  9      return vorgänger[]
     */
    public TNode start(GraphNode[] graph, int start){
        //Init im Konstruktor
        TNode predecessor= new TNode(graph[0]); //fix result should be array
        if(Q != null) {
            while ( !Q.isEmpty()){
                //todo Tocode
                TNode u = Q.poll();
                Q.remove(u);
                for (TNode v : u.neighbors) {
                    if (Q.contains(v)) {
                        distance_update(u, v);
                    }
                }
            }
        }
        return predecessor;
    }

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
     *  2      alternativ:= abstand[u] + abstand_zwischen(u, v)   // Weglänge vom Startknoten nach v über u
     *  3      falls alternativ < abstand[v]:
     *  4          abstand[v]:= alternativ
     *  5          vorgänger[v]:= u
     */
    public void distance_update(TNode u, TNode v){
         double alternativ = u.distance + distance_between(u, v);
         if(alternativ < v.distance ){
             /*
             TNode cache = new TNode(); //Maybe create better copy of v?
             Q.remove(v);
             cache.distance = alternativ;
             cache.predecessor = u;
             Q.add(cache);*/
         }
    }

    private double distance_between(TNode no1, TNode no2) {
        Vector3D v1 = new Vector3D(no1.x,no1.y,no1.z);
        Vector3D v2 = new Vector3D(no2.x,no2.y,no2.z);
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
    public TNode[] createShortestPath(TNode target, TNode predecessor[]){
        PriorityQueue<TNode> path = new PriorityQueue<>();
        path.add(target);
        TNode u = target;
        while(u.predecessor != null ){
            u = u.predecessor;
            path.add(u);
        }
        return (TNode[]) path.toArray();
    }

    //graphnode not identified uniquely by position?
    public GraphNode search(GraphNode target, GraphNode predecessor[]){
        GraphNode u;
        if(predecessor != null && predecessor.length>0)return null;
        for(int i=0; i < predecessor.length;i++){
            if(predecessor[i].equals(target) )return predecessor[i];
        }
        return null;
    }

}
