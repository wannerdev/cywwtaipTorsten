package de.HTW;

import lenz.htw.cywwtaip.net.NetworkClient;
import lenz.htw.cywwtaip.world.GraphNode;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Client {

    private static float nodesOfBots[];

    // java -Djava.library.path=lib/native -jar cywwtaip.jar
    public static void main(String[] args) {
        NetworkClient client = new NetworkClient("localhost", args[0]+" Torsten", "SUPER!");
        int myPlNumber = client.getMyPlayerNumber();
        Rotator rot = new Rotator();
        Target tar = new Target();
        float ang3=0.0001f,angle =0.00001f; //Try to fill in circle motion
        float ticks=0;
        GraphNode[] graph = client.getGraph();
        TNode first= null;
        //System.out.printf(args[0]+" TNodes:"+TGraph.length);
        //System.out.printf("first graphnode"+graph[0].toString());
        //System.out.printf("Nei from one"+graph[0].neighbors.toString());
        nodesOfBots = new float[]{-1,-1,-1};




        while (client.isAlive()) {


//            Vector3D Destination =  new Vector3D( 0,0,0);//myGraph[nodeNo].x,myGraph[nodeNo].y,myGraph[nodeNo].z);
            Vector3D Destination =  new Vector3D( -0.94,0,0);//myGraph[nodeNo].x,myGraph[nodeNo].y,myGraph[nodeNo].z);
            //check Vector3D Destination =  new Vector3D( 0,0.95,0);//myGraph[nodeNo].x,myGraph[nodeNo].y,myGraph[nodeNo].z);
            //check1 Vector3D Destination =  new Vector3D( 0,0,0.95);//myGraph[nodeNo].x,myGraph[nodeNo].y,myGraph[nodeNo].z);

            Vector3D Destination0 = tar.giveClosestEnergy(client.getBotPosition(myPlNumber,0));
            Vector3D Destination1 = tar.giveClosestEnergy(client.getBotPosition(myPlNumber,1));
            Vector3D Destination2 = tar.giveClosestEnergy(client.getBotPosition(myPlNumber,2));
            // System.out.println(Destination.toString());


         

            client.changeMoveDirection(0, ang1);
            client.changeMoveDirection(1, ang2);


            client.changeMoveDirection(2, ang3);

            //float[] position = client.getBotPosition(0, 0); // array with x,y,z
            //float[] direction = client.getBotDirection(0); // array with x,y,z


        }
    }

    /** Working circle angle
     *              ang3=0;
     *             ticks++;
     *             if(ticks%15==0){
     *                 ang3 = 0.00001f ;
     *             }else{
     *                 ang3=0;
     *             }
     *              //move in reducing circle only with Bot2(because of speed).
     *             ticks++;
     *             if(ticks== Float.MAX_VALUE)ticks=0;
     *             if(ticks%15==0){
     *                 ang3 = angle ;
     *                 angle = angle - 0.000000000000001f;
     *                 if(angle <=0)angle=0.00001f;
     *             }else{
     *                 ang3=0;
     *             }
     */

    /**
     * Sets the array nodesOfBots to the GraphNode indexes on the server
     * @param client
     */

}
