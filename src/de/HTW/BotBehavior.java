package de.HTW;

import lenz.htw.cywwtaip.net.NetworkClient;
import lenz.htw.cywwtaip.world.GraphNode;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import java.util.Collections;
import org.apache.commons.math3.ml.neuralnet.Network;

import java.util.ArrayList;

public class BotBehavior {

    int botNo, PlayerNo;
    public boolean atTarget = false;
    public TNode target,currentNode,finalDest;
    NetworkClient Client;
    public GraphNode[] graph;
    ArrayList<TNode> myPath;
    public float energyLevel;
    public Vector3D pos;
    public double distToTarget;
    Rotator rot;
    Vector3D targetPos;

    public enum state {
        idle,
        moving,
        painting,
        recharge;

    }

    public state currentState;

    public BotBehavior(int botNo, GraphNode[] graph, NetworkClient client, int PlayerNo) {
        this.graph = graph;
        this.Client = client;
        this.PlayerNo = PlayerNo;
        GraphNode myGraphNode = getMyNodeId(Client, botNo);
        currentNode = new TNode(myGraphNode);
        finalDest = new TNode(graph[])
        currentState = state.idle;
    }

    public void updateInfo() {
        float[] vec = Client.getBotPosition(PlayerNo, botNo);
        pos = new Vector3D(vec[0], vec[1], vec[2]);
        energyLevel = Client.getBotSpeed(botNo);
        GraphNode myGraphNode = getMyNodeId(Client, botNo);
        currentNode = new TNode(myGraphNode);

        if (target != null) {
            targetPos = new Vector3D(target.x, target.y, target.z);
            distToTarget = Vector3D.distance(pos, targetPos);
        }

    }


    public void Update() {

        updateInfo();

        switch (currentState) {

            case moving:
                move();
                break;
            case idle:
                idle();
                break;
            case recharge:
                recharge();
                break;

            case painting:
                moveRandom();
                break;


        }


    }

    public void move() {

        if (target == null)return;

        float angle =  rot.getRotationAngle(targetPos, Client,botNo, PlayerNo);



    }

    public void moveRandom() {


    }

    public void idle() {


    }

    public void recharge() {


    }

    public void calcPath() {

        A_Star A = new A_Star(graph,currentNode,target);
        myPath = A.A_Star();
        Collections.reverse(myPath);
        myPath.remove(0);
        target = myPath.get(0);
    }

    private static void getMyNodes(NetworkClient client) {
        GraphNode[] graph = client.getGraph();
        float pos1[] = client.getBotPosition(client.getMyPlayerNumber(), 0);
        float pos2[] = client.getBotPosition(client.getMyPlayerNumber(), 1);
        float pos3[] = client.getBotPosition(client.getMyPlayerNumber(), 2);
        for (int i = 0; i < graph.length; i++) {
            GraphNode n = graph[i];
            if (atNode(n, pos1)) nodesOfBots[0] = i;
            if (atNode(n, pos2)) nodesOfBots[1] = i;
            if (atNode(n, pos3)) nodesOfBots[2] = i;
        }
    }

    /**
     * Searches graph for the node with the corresponding bot.(dumb search)
     *
     * @param client
     * @param bot
     * @returns GraphNode of the position of the Bot
     */
    public static GraphNode getMyNodeId(NetworkClient client, int bot) {
        GraphNode[] graph = client.getGraph();
        float pos1[] = client.getBotPosition(client.getMyPlayerNumber(), bot);
        int i = 0;
        for (; i < graph.length; i++) {
            if (atNode(graph[i], pos1)) {
                nodesOfBots[bot] = i;
                break;
            }
        }
        return graph[i];
    }

    /**
     * Just a simple check if the positions match
     *
     * @param n
     * @param spot
     * @return
     */
    public static boolean atNode(GraphNode n, float spot[]) {
        if (n.blocked) return false;
        if (n.x == spot[0] && n.y == spot[1] && n.z == spot[2]) {
            return true;
        }
        return false;
    }

}
