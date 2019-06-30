package de.HTW;

import lenz.htw.cywwtaip.net.NetworkClient;
import lenz.htw.cywwtaip.world.GraphNode;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;

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
        GraphNode myGraphNode = getMyNodeId(client,0);
        TNode myTNode = new TNode(myGraphNode);
        TNode targetNode = new TNode(graph[100]);
        dijkstra D  = new dijkstra(graph,myTNode,targetNode);
        ArrayList<TNode> path = D.createShortestPath();
        while (client.isAlive()) {
            //GraphNode[] myGraph = client.getGraph();

            //System.out.printf("First TNode"+TGraph[0].id);

//            Vector3D Destination =  new Vector3D( 0,0,0);//myGraph[nodeNo].x,myGraph[nodeNo].y,myGraph[nodeNo].z);
            Vector3D Destination =  new Vector3D( -0.94,0,0);//myGraph[nodeNo].x,myGraph[nodeNo].y,myGraph[nodeNo].z);
            //check Vector3D Destination =  new Vector3D( 0,0.95,0);//myGraph[nodeNo].x,myGraph[nodeNo].y,myGraph[nodeNo].z);
            //check1 Vector3D Destination =  new Vector3D( 0,0,0.95);//myGraph[nodeNo].x,myGraph[nodeNo].y,myGraph[nodeNo].z);

            Vector3D Destination0 = tar.giveClosestEnergy(client.getBotPosition(myPlNumber,0));
            Vector3D Destination1 = tar.giveClosestEnergy(client.getBotPosition(myPlNumber,1));
            Vector3D Destination2 = tar.giveClosestEnergy(client.getBotPosition(myPlNumber,2));
            // System.out.println(Destination.toString());
            float ang1 =  rot.getRotationAngle(Destination0, client,0, myPlNumber);
            float ang2 =  rot.getRotationAngle(Destination1, client,1, myPlNumber);

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
    private static void getMyNodes(NetworkClient client){
        GraphNode[] graph = client.getGraph();
        float pos1[] = client.getBotPosition(client.getMyPlayerNumber(),0);
        float pos2[] = client.getBotPosition(client.getMyPlayerNumber(),1);
        float pos3[] = client.getBotPosition(client.getMyPlayerNumber(),2);
        for (int i =0 ; i< graph.length; i++) {
            GraphNode n = graph[i];
            if(atNode(n,pos1))nodesOfBots[0]=i;
            if(atNode(n,pos2))nodesOfBots[1]=i;
            if(atNode(n,pos3))nodesOfBots[2]=i;
        }
    }

    /**
     * Searches graph for the node with the corresponding bot.(dumb search)
     * @param client
     * @param bot
     * @returns GraphNode of the position of the Bot
     */
    public static GraphNode getMyNodeId(NetworkClient client, int bot){
        GraphNode[] graph = client.getGraph();
        float pos1[] = client.getBotPosition(client.getMyPlayerNumber(),bot);
        int i=0;
        for (; i< graph.length; i++) {
            if(atNode(graph[i],pos1)){
                nodesOfBots[bot]=i;
                break;
            }
        }
        return graph[i];
    }

    /**
     * Just a simple check if the positions match
     * @param n
     * @param spot
     * @return
     */
    public static boolean atNode(GraphNode n, float spot[]){
        if (n.blocked) return false;
        if(n.x ==spot[0]&& n.y ==spot[1]&& n.z ==spot[2]) {
            return true;
        }
        return false;
    }
}
