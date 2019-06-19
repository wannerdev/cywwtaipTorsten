package de.HTW;

import lenz.htw.cywwtaip.net.NetworkClient;
import lenz.htw.cywwtaip.world.GraphNode;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Client {

    // java -Djava.library.path=lib/native -jar cywwtaip.jar
    public static void main(String[] args) {
        NetworkClient client = new NetworkClient("localhost", " Torsten", "SUPER!");
        Rotator rot = new Rotator();
        while (client.isAlive()) {
            client.getBotSpeed(0); // raw constant
            client.getScore(client.getMyPlayerNumber());
            GraphNode[] myGraph = client.getGraph();
            int nodeNo = 0 ;
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

           /* GraphNode[] graph = client.getGraph();
            for (GraphNode n : graph[0].neighbors) {
                System.out.println(n + ": " + n.owner + ", " + n.blocked);
            }*/

        }
    }
}
