package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ClientThread extends Thread {
    private Socket player1;
    private Socket player2;

    public ClientThread(Socket player1,Socket player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void run() {
        try {

            BufferedReader in1 = new BufferedReader(
                    new InputStreamReader(player1.getInputStream()));

//            String player1Move = in2.readLine();
            PrintWriter out1 = new PrintWriter(player1.getOutputStream());

            BufferedReader in2 = new BufferedReader(
                    new InputStreamReader(player2.getInputStream()));
//            String player2Move = in1.readLine();
            PrintWriter out2 = new PrintWriter(player2.getOutputStream());
            boolean stillPlaying = true;
            int count= 0;
            // Ii spunem playerului1 că este primul player
            sendTo(out1,"1");
            sendTo(out2,"0");

            while(stillPlaying){


                String player1Move = in1.readLine();
                String raspuns2 = "Oponent move: " + player1Move;
                sendTo(out2,raspuns2);
                String player2Move = in2.readLine();
                String raspuns1;
                raspuns1 = "Oponent move: " + player2Move;
                out1.println(raspuns1);
                out1.flush();
                if(count==4){
                    stillPlaying = false;
                }
                count++;
            }



        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                player2.close();
                player1.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
    private void sendTo(PrintWriter to, String message){
        to.println(message);
        to.flush();

    }

}


