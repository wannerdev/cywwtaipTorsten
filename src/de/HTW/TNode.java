package de.HTW;

import lenz.htw.cywwtaip.world.GraphNode;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.Comparator;

public class TNode implements Comparable<TNode>  {
    static private final Comparator<TNode> COMPARATOR = Comparator.comparing(TNode::getfScore);
    static int counter = 0;
    //Maybe by importing create a id for each node
    public int id;
    public double distance;
    public float gScore, fScore;
    public TNode predecessor;
    public ArrayList<TNode> neighbors = new ArrayList<>();

    public float x;
    public float y;
    public float z;
    public boolean blocked;
    public int owner;

    public TNode(GraphNode in) {

        predecessor = null;
        distance = Double.MAX_VALUE;


        this.x = in.x;
        this.y = in.y;
        this.z = in.z;
        this.blocked = in.blocked;
        this.owner = in.owner;

        //this.neighbors = new TNode[in.neighbors.length];
        //this.setNeighbors(in.neighbors);
        //for(int i=0; i <neighbors.length; i++){
        //    this.neighbors[i] = new TNode(in_neighbors[i], in_neighbors[i].neighbors);
        //}

    }


    public TNode() {
        distance = Double.MAX_VALUE;
    }

    public double getDistance() {
        return distance;
    }

    public float getfScore() { return  fScore;}

    public int hashCode() {
        return (int) (((float) ((int) (((float) ((int) (this.x * 1260.0F)) + this.y) * 1260.0F)) + this.z) * 1260.0F);
    }

    public boolean equals(Object obj) {
        if (obj instanceof TNode) {
            TNode obj1 = (TNode) obj;
            return this.id == obj1.id;// && this.y == obj1.y && this.z == obj1.z;
            //return this.x == obj1.x && this.y == obj1.y && this.z == obj1.z;
        } else {
            return false;
        }
    }

    public void setNeighbors(GraphNode[] neighbors, GraphNode[] graph) {
        for (int i = 0; i < neighbors.length; i++) {
            if (!neighbors[i].blocked){

                TNode neigh = new TNode(neighbors[i]);
                neigh.id = java.util.Arrays.asList(graph).indexOf(neighbors[i]);
                this.neighbors.add(neigh);
            }


        }
    }


    public String toString() {
        return "x=" + this.x + ", y=" + this.y + ", z=" + this.z;
    }

    @Override
    public int compareTo(TNode o) {
        return COMPARATOR.compare(this,o);
    }
}

