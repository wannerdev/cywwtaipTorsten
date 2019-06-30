package de.HTW;

import lenz.htw.cywwtaip.net.NetworkClient;
import lenz.htw.cywwtaip.world.GraphNode;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import java.util.Collections;
import org.apache.commons.math3.ml.neuralnet.Network;

import java.util.ArrayList;

public class BotBehavior {

    int botNo, PlayerNo;
    public boolean ready = false;
    public TNode target,currentNode,finalDest;
    NetworkClient Client;
    public GraphNode[] graph;
    ArrayList<TNode> myPath;
    public float energyLevel;
    public Vector3D pos;
    public double distToTarget;
    Rotator rot;
    Vector3D targetPos;
    int counter = 0 ;

    public enum state {
        idle,
        moving,
        painting,
        recharge;

    }
    public state currentState;

    public BotBehavior(int botNo, GraphNode[] graph, NetworkClient client, int PlayerNo) {
        this.graph = graph;
        this.botNo = botNo;
        this.Client = client;
        this.PlayerNo = PlayerNo;
        currentState = state.idle;
        updateInfo();
        getNewDestination();

    }

    public void updateInfo() {
        float[] vec = Client.getBotPosition(PlayerNo, botNo);
        pos = new Vector3D(vec[0], vec[1], vec[2]);
        energyLevel = Client.getBotSpeed(botNo);

        if (target != null) {
            distToTarget = Vector3D.distance(pos, targetPos);
        }



    }

    void getNewDestination(){

        /// HIER KOMMT DEIN CODE HIN JOHANNES!!
        finalDest = new TNode(graph[(int)(Math.random() * (graph.length - 0) + 1) + 0]);
        GraphNode myGraphNode = getMyNodeId();
        if (myGraphNode!= null){
            currentNode = new TNode(myGraphNode);
        }
        calcPath();
    }

    public void Update() {
        if (!ready){
            currentState = state.idle;
        }
        updateInfo();

        switch (currentState) {

            case moving:
                move();

                if (distToTarget <= 0.01f){
                    myPath.remove(0);
                    if (myPath.size() <=0){

                        currentState = state.idle;
                        getNewDestination();
                    }else{
                        currentNode = target;

                        target = myPath.get(0);
                        targetPos = new Vector3D(target.x, target.y, target.z);
                    }
                }
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

        Client.changeMoveDirection(botNo, angle);

    }

    public void moveRandom() {


    }

    public void idle() {

        float angle = 0;
        angle += 0.001f;

        Client.changeMoveDirection(botNo, angle);
    }

    public void recharge() {


    }

    public void calcPath() {
        counter++;
        A_Star A = new A_Star(graph,currentNode,finalDest);
        myPath = A.A_Star();
        Collections.reverse(myPath);
        myPath.remove(0);
        target = myPath.get(0);
        targetPos = new Vector3D(target.x, target.y, target.z);
        if (PlayerNo == 1){
            System.out.printf("I was calles  "+counter +" times");
            System.out.printf("Player " + PlayerNo + " Bot "+ botNo+" finished Path");
        }



        ready = true;
        currentState = state.moving;

    }


    /**
     * Searches graph for the node with the corresponding bot.(dumb search)
     *

     * @returns GraphNode of the position of the Bot
     */
    public GraphNode getMyNodeId() {

        for (int i = 0; i < graph.length; i++) {
            if (Client.getBotPosition(PlayerNo,botNo)[0] == graph[i].x && Client.getBotPosition(PlayerNo,botNo)[1] == graph[i].y && Client.getBotPosition(PlayerNo,botNo)[2] == graph[i].z ) {

                return  graph[i];
            }
        }
      //  System.out.printf("no Node for Pos");
        return null;

    }



}
