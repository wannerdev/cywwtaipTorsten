package de.HTW;

import java.io.*;

import static java.lang.Thread.sleep;


public class Start {

    public static void main(String[] args) throws InterruptedException {

        new Thread(()->{
            // Server.main(new String[]{"720", "460"});

            try{
                Process proc = Runtime.getRuntime().exec("java -Djava.library.path=\"D:\\Projekte\\uni\\GT1\\libraries\\lib\\native\" -jar \"D:\\Projekte\\uni\\GT1\\libraries\\cywwtaip.jar\"  1280 720");
                BufferedReader bir  = new BufferedReader(new InputStreamReader(proc.getInputStream()));

                String line = "";
                while ((line = bir.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        sleep(2000);

        new Thread(()->{
            Client.main(new String[]{});
        }).start();

        new Thread(()->{
            Client.main(new String[]{});
        }).start();

        new Thread(()->{
            Client.main(new String[]{});
        }).start();
    }
}
