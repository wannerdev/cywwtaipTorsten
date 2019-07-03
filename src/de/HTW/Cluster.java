package de.HTW;

import lenz.htw.cywwtaip.world.GraphNode;

import java.util.*;

public class Cluster {
    /*DBSCAN(D, eps, MinPts)
   C = 0
   for each unvisited point P in dataset D
      mark P as visited
      N = D.regionQuery(P, eps)
      if sizeof(N) < MinPts
         mark P as NOISE
      else
         C = next cluster
         expandCluster(P, N, C, eps, MinPts)*/
    ArrayList<Integer> visited=new ArrayList<>();
    ArrayList<Integer> noise=new ArrayList<>();
    ArrayList<ArrayList<GraphNode>> clusters=new ArrayList<>();
    GraphNode graD[];
    private int owner=-1;

    //minpts limit to start a cluster
    //eps neighborhood size?

    /**
     *
     * @param owner
     * @param graphD
     * @param eps
     * @param minPts
     */
    public Cluster (int owner,GraphNode graphD[], int eps, int minPts){
        graD = graphD;//Init for class
        owner = owner;
        ArrayList<GraphNode> C;
        for(int i=0; i <  graphD.length;i++){ //evtl sont look at all
            GraphNode tp = graphD[i];
            visited.add(tp.hashCode());
            ArrayList<GraphNode> groupN=query(tp,eps);
            if(groupN.size() < minPts){
                noise.add(tp.hashCode());
            }else{
                C= new ArrayList<>();
                expandCluster(tp,groupN,C,eps, minPts);
            }
        }
        System.out.println("Cluster init");
    }

/*expandCluster(P, N, C, eps, MinPts)
   add P to cluster C
   for each point P' in N
      if P' is not visited
         mark P' as visited
         N' = D.regionQuery(P', eps)
         if sizeof(N') >= MinPts
            N = N joined with N'
      if P' is not yet member of any cluster
         add P' to cluster C
         unmark P' as NOISE if necessary*/
    public void expandCluster(GraphNode p, ArrayList<GraphNode> N, ArrayList<GraphNode> c, int eps,int MinPts){
       c.add(p);
       for (GraphNode newp: N) {
           if (!visited.contains(newp)) {// ' is not visited
               visited.add(newp.hashCode());//mark P' as visited
               ArrayList<GraphNode> newN = query(newp, eps);
               if (newN.size() >= MinPts) {
                   N.addAll(newN); //joined with N'
                   if (!c.contains(newp)) {// is not yet member of any cluster
                       c.add(p); //P' to cluster C
                       if (noise.contains(p)) {//unmark P' as NOISE if necessary
                           noise.remove(p);
                       }
                   }

               }
           }
       }
        clusters.add(c);
    }

    ArrayList<GraphNode> getBiggestCluster(){
        Collections.sort(clusters, new Comparator<ArrayList>() {
            public int compare(ArrayList a1, ArrayList a2) {
                return a2.size() - a1.size(); // assumes you want biggest to smallest
            }
        });
        return clusters.get(0);
    }

    //eps radius of neighbors
    public ArrayList<GraphNode> query(GraphNode tin,int eps){
        ArrayList<GraphNode> res = new ArrayList<>();
        res.add(tin);
        ArrayList<GraphNode> list=new ArrayList<>();;
        ArrayList<GraphNode> list2=new ArrayList<>();;
        list.add(tin);
        for(int i=0; i < eps; i++ ){
                for (GraphNode no : list) {
                    //todo change
                    if (!isMyColor(no)) { //|| RATEFUNCTION ||
                        res.add(no);
                        //list2.addAll(Arrays.asList(no.neighbors));
                    }
                }
            /*for (GraphNode no : list2) {
                //todo change
                if (!isMyColor(no)) { //|| RATEFUNCTION ||
                    res.add(no);
                    list.addAll(Arrays.asList(no.neighbors));
                }
            }*/
        }
        return res;
    }

    private boolean isMyColor(GraphNode no) {
        return no.owner == this.owner;
    }
/*
regionQuery(P, eps)
   return all points within P's eps-neighborhood (including P)*/

}
