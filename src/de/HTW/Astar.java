package de.HTW;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import lenz.htw.cywwtaip.world.GraphNode;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Astar {

    private Stack<GraphNode> Q;
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

    public GraphNode[] start(GraphNode[] graph, int start){
        while(Q!=null){
            //todo Tocode
            GraphNode u= smallestDistance(Q);
            Q.remove(u);
            for (GraphNode v : u.neighbors){
                if(Q.contains(v)){
                    distance_update(u,v,distance[],predecessor[]);
                }
            }
        }
        return predecessor[];
    }

    /*
    Methode initialisiere(Graph,Startknoten,abstand[],vorgänger[],Q):
            2      für jeden Knoten v in Graph:
            3          abstand[v]:= unendlich
 4          vorgänger[v]:= null
            5      abstand[Startknoten]:= 0
            6      Q:= Die Menge aller Knoten in Graph
    *///init, start als int oder GraphNode?
    public Astar( GraphNode graph[], int start, float distance[], GraphNode predecessors[], Stack<GraphNode> Q){
        for(int i=0; i< graph.length; i++){
            distance[i]=Float.MAX_VALUE;
            predecessors[i]=null;
        }
        distance[start]=0;
        Q.addAll(Arrays.asList(graph));
        //Remove obstacles from Map?
        for (GraphNode a: Q){
            if(a.blocked) Q.remove(a);
        }
    }

    /**
     * 1  Methode distanz_update(u,v,abstand[],vorgänger[]):
     *  2      alternativ:= abstand[u] + abstand_zwischen(u, v)   // Weglänge vom Startknoten nach v über u
     *  3      falls alternativ < abstand[v]:
     *  4          abstand[v]:= alternativ
     *  5          vorgänger[v]:= u
     */
     public void distance_update(int u, int v, float distance[], GraphNode predecessors[]){
         float alternativ = distance[u] + distance_between(u, v);
         if(alternativ < distance[v] ){
             distance[v] = alternativ;
             predecessors[v] = Q.get(u); //makes sense?
         }
     }

    private float distance_between(int u, int v) {

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

    public GraphNode[] createShortestPath(int target, GraphNode predecessor[]){
        PriorityQueue<GraphNode> path = new PriorityQueue<GraphNode>();
        path.add(Q.get(target));
        GraphNode u=Q.get(target);
        while(predecessor[]!= null){
            u=predecessor[u];
        }
        return (GraphNode[]) path.toArray();
    }
}
