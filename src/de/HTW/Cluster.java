package de.HTW;

import lenz.htw.cywwtaip.world.GraphNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
    ArrayList<Integer> visited;
    ArrayList<Integer> noise;
    TNode graD[];
    //minpts limit to start a cluster
    //eps neighborhood size?
    public Cluster (TNode graphD[], int eps, int minPts){
        ArrayList<TNode> C;
        graD = graphD;
        for(TNode t : graphD){
            visited.add(t.id);
            ArrayList<TNode> groupN=query(t,eps);
            if(groupN.size() < minPts){
                noise.add(t.id);
            }else{
                C= new ArrayList<>();
                expandCluster(t,groupN,C,eps, minPts);
            }
        }
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
    public void expandCluster(TNode p, ArrayList<TNode> N, ArrayList<TNode> c, int eps,int MinPts)
    {
        c.add(p);
       for (TNode newp: N) {
           if (!visited.contains(newp)) {// ' is not visited
               visited.add(newp.id);//mark P' as visited
               ArrayList<TNode> newN = query(newp, eps);
               if (newN.size() >= MinPts) {
                   N.addAll(newN); //joined with N'
                   if (!c.contains(newp)) {// is not yet member of any cluster
                       c.add(p); //P' to cluster C
                       if (noise.contains(p)) {
                           noise.remove(p);
                       }
                   }
                   //unmark P' as NOISE if necessary
               }
           }
       }
    }

    //eps radius of neighbors
    public ArrayList<TNode> query(TNode tin,int eps){
        ArrayList<TNode> res = new ArrayList<>();
        res.add(tin);
        ArrayList<TNode> list=new ArrayList<>();;
        list.add(tin);
        while(eps!=0){
            for(TNode t : list) {
                for (TNode no : t.neighbors) {
                    //todo change
                    if (no.owner == 1) { //|| RATEFUNCTION ||
                        res.add(no);
                    }
                }
                list = t.neighbors;
                eps--;
            }
        }
        //res=(TNode[])t.neighbors.toArray();
        //res[7]=t;
        return res;
    }

/*


regionQuery(P, eps)
   return all points within P's eps-neighborhood (including P)*/

}
