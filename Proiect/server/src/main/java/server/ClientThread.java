package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Un thread pentru a comunica cu  doi clienții
 */
public class ClientThread extends Thread {
    private Socket player1;
    private Socket player2;

    public ClientThread(Socket player1, Socket player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Cât timp nu primește "4" de la un client serverul ia mutarea de la un jucător și o trimite oponentului său
     */
    public void run() {
        try {

            BufferedReader in1 = new BufferedReader(
                    new InputStreamReader(player1.getInputStream()));

            PrintWriter out1 = new PrintWriter(player1.getOutputStream());

            BufferedReader in2 = new BufferedReader(
                    new InputStreamReader(player2.getInputStream()));
            PrintWriter out2 = new PrintWriter(player2.getOutputStream());
            boolean stillPlaying = true;


            // Ii spunem jucatorului 1 că are prima mutare
            sendTo(out1, "3");
            sendTo(out2, "2");

            while (stillPlaying) {


                String player1MoveFromX = in1.readLine();
                String player1MoveFromY = in1.readLine();
                String player1MoveToX = in1.readLine();
                String player1MoveToY = in1.readLine();
                if (player1MoveFromX.equals("4")) {
                    stillPlaying = false;
                    sendTo(out2, player1MoveFromX);
                    sendTo(out2, player1MoveFromY);
                    sendTo(out2, player1MoveToX);
                    sendTo(out2, player1MoveToY);
                } else {
                    sendTo(out2, player1MoveFromX);
                    sendTo(out2, player1MoveFromY);
                    sendTo(out2, player1MoveToX);
                    sendTo(out2, player1MoveToY);
                    String player2MoveFromX = in2.readLine();
                    String player2MoveFromY = in2.readLine();
                    String player2MoveToX = in2.readLine();
                    String player2MoveToY = in2.readLine();
                    if (player2MoveFromX.equals("4")) {
                        stillPlaying = false;
                        sendTo(out1, player2MoveFromX);
                        sendTo(out1, player2MoveFromY);
                        sendTo(out1, player2MoveToX);
                        sendTo(out1, player2MoveToY);
                    } else {
                        sendTo(out1, player2MoveFromX);
                        sendTo(out1, player2MoveFromY);
                        sendTo(out1, player2MoveToX);
                        sendTo(out1, player2MoveToY);

                    }
                }

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

    /**
     * Trimite un mesaj către un client
     * @param to - clientul către care se trimite mesajul
     * @param message - mesajul trimis
     */
    private void sendTo(PrintWriter to, String message) {
        System.out.println("Trimit: " + message);
        to.println(message);
        to.flush();

    }

}


