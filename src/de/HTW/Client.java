package de.HTW;

import lenz.htw.cywwtaip.net.NetworkClient;
import lenz.htw.cywwtaip.world.GraphNode;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Client {

    private static float nodesOfBots[];

    // java -Djava.library.path=lib/native -jar cywwtaip.jar
    public static void main(String[] args) {
        NetworkClient client = new NetworkClient("localhost", args[0]+" Torsten", "SUPER!");
        Rotator rot = new Rotator();
        while (client.isAlive()) {
            client.getBotSpeed(0); // raw constant
            client.getScore(client.getMyPlayerNumber());
            GraphNode[] myGraph = client.getGraph();
            int nodeNo = 0;
            nodesOfBots = new float[]{-1,-1,-1};
            Vector3D Destination =  new Vector3D( 0,1,0);//myGraph[nodeNo].x,myGraph[nodeNo].y,myGraph[nodeNo].z);
          //  System.out.println(Destination.toString());
            float ang1 =  rot.getRotationAngle(Destination,client,0, client.getMyPlayerNumber());
            float ang2 =  rot.getRotationAngle(Destination,client,1, client.getMyPlayerNumber());
            float ang3 =  rot.getRotationAngle(Destination,client,2, client.getMyPlayerNumber());
            client.changeMoveDirection(1, ang2);
            client.changeMoveDirection(0, ang1);
            client.changeMoveDirection(2,ang3);

           // float[] position = client.getBotPosition(0, 0); // array with x,y,z
            //float[] direction = client.getBotDirection(0); // array with x,y,z

            /*GraphNode[] graph = client.getGraph();
            for (GraphNode n : graph[0].neighbors) {
                System.out.println(n + ": " + n.owner + ", " + n.blocked);
            }*/

        }
    }

    /**
     * Sets the array nodesOfBots to the GraphNode indexes on the server
     * @param client
     */
    private static void getMyNodes(NetworkClient client){
        GraphNode[] graph = client.getGraph();
        float pos1[] = client.getBotPosition(client.getMyPlayerNumber(),0);
        float pos2[] = client.getBotPosition(client.getMyPlayerNumber(),0);
        float pos3[] = client.getBotPosition(client.getMyPlayerNumber(),0);
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
