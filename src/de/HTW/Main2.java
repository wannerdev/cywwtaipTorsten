package de.HTW;

import lenz.htw.cywwtaip.net.NetworkClient;
import lenz.htw.cywwtaip.world.GraphNode;

public class Main2 {

    public static void main(String[] args) {
        NetworkClient client = new NetworkClient(null, args[0]+" Torsten", "SUPER!");

        while (client.isAlive()) {
            client.getBotSpeed(0); // raw constant
            client.getScore(client.getMyPlayerNumber());
            client.changeMoveDirection(1, -0.08f);
            client.changeMoveDirection(0, 25);
            client.changeMoveDirection(2,10);

            float[] position = client.getBotPosition(0, 0); // array with x,y,z
            float[] direction = client.getBotDirection(0); // array with x,y,z

            GraphNode[] graph = client.getGraph();
            for (GraphNode n : graph[0].neighbors) {
                System.out.println(n + ": " + n.owner + ", " + n.blocked);
            }

        }
    }
}
