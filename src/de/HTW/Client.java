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
        NetworkClient client = new NetworkClient("localhost", args[0] + " Torsten", "SUPER!");
        int myPlNumber = client.getMyPlayerNumber();
        GraphNode[] graph = client.getGraph();

        BotBehavior Bot0 = new BotBehavior(0, graph, client, myPlNumber);
        BotBehavior Bot1 = new BotBehavior(1, graph, client, myPlNumber);
        BotBehavior Bot2 = new BotBehavior(2, graph, client, myPlNumber);


        int frames = 0;
        float angle0 = 0;
        float angle1 = 0;
        float angle2 = 0;
        float rotspeed = 0.001f;
        while (client.isAlive()) {


            if (!client.isGameRunning()) {
                return;
            }

            Bot0.Update();


            Bot1.Update();


            Bot2.Update();


            if (frames % 500 == 0 && myPlNumber == 1) {
                //  printBotDistances(Bot0,Bot1,Bot2);
                //float speed = client.getBotSpeed(0);
                // System.out.printf(" " +speed);

            }
            frames++;
        }
    }


    static void printBotDistances(BotBehavior Bot0, BotBehavior Bot1, BotBehavior Bot2) {

      /*  System.out.printf("BOT0 DIST :: " + Bot0.distToTarget);
        System.out.printf("BOT1 DIST :: " + Bot1.distToTarget);
        System.out.printf("BOT2 DIST :: " + Bot2.distToTarget);*/
        // System.out.printf();
        System.out.printf("BOT1 STATE :: " + Bot1.currentState);
        System.out.printf("BOT2 STATE :: " + Bot2.currentState);


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
