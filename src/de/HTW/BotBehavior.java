package de.HTW;

import lenz.htw.cywwtaip.net.NetworkClient;
import lenz.htw.cywwtaip.world.GraphNode;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import java.util.Collections;

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
    int ticks=0;
    Cluster cl;
    float angle0=0,angle1=0,angle2 = 0;

    public enum state {
        idle,
        moving,
        painting,
        recharge;

    }
    public enum type {
        random,
        recharger,
        cluster;
    }
    public type  botType = type.random;
    public state currentState;

    public BotBehavior(int botNo, GraphNode[] graph, NetworkClient client, int PlayerNo,BotBehavior.type behaviorType){//, Cluster cl) {
        this.graph = graph;
        this.botNo = botNo;
        this.Client = client;
        this.PlayerNo = PlayerNo;
       // this.cl = cl;
        botType = behaviorType;
        currentState = state.idle;
        updateInfo();
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
        switch (botType) {

            case random:
                finalDest = new TNode(graph[(int)(Math.random() * (graph.length))]);
                break;

            case cluster:
                cl.getBiggestCluster().get(0);
                finalDest  = new TNode(cl.getBiggestCluster().get(0));
                break;

            case recharger:
                float[] pos = {Client.getBotPosition(PlayerNo,botNo)[0],Client.getBotPosition(PlayerNo,botNo)[1],Client.getBotPosition(PlayerNo,botNo)[2]};
                Vector3D vec =Target.giveClosestEnergy(pos);

                GraphNode myGraphNode = getMyNodeId(vec.getX(),vec.getY(),vec.getZ());
                finalDest  = new TNode(myGraphNode);
                break;
        }

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

        ticks++;
        if(ticks%15==0) {
            switch (this.botNo) {
                case 0: {//einfarbig
                    angle0 = 0.0000000001f;
                    Client.changeMoveDirection(botNo, angle0);
                    break;
                }
                case 1: {//gepunktet
                    angle1 = 0.000001f;
                    Client.changeMoveDirection(botNo, angle1);
                    break;
                }
                case 2: {//gestreift
                    angle2 = 0.00001f;
                    angle2 =- 0.000001f;
                    Client.changeMoveDirection(botNo, angle2);
                    break;
                }
            }
        }
    }

    public void recharge() {


    }

    public void calcPath() {
        counter++;
        A_Star A = new A_Star(graph,currentNode,finalDest);
        myPath = A.A_Start();
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
        System.out.printf("no Node for Pos");
        return null;

    }

    public GraphNode getMyNodeId(double x, double y, double z) {

        for (int i = 0; i < graph.length; i++) {
            if (x == graph[i].x && y == graph[i].y && z == graph[i].z ) {

                return  graph[i];
            }
        }
        System.out.printf("no Node for Pos with params");
        return null;

    }



}
