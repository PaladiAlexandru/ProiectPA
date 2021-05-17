package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa care pornește serverul și stochează clienții care se conectează într-o listă
 */
public class MyServer {
    public static final int PORT = 8101;
    List<Socket> players;


    public MyServer() {
        players = new ArrayList<>();

    }

    /**
     * Așteaptă cereri de conexiune, iar când sun găsite doua sau mai multe cereri se pornește un nou thread
     * prin care comunică serverul cu cei doi clienți
     */
    public void startServer() {
        boolean running = true;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            while (running) {
                System.out.println("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));

                players.add(socket);


                if (players.size() >= 2) {
                    new ClientThread(players.get(0), players.get(1)).start();
                    players.remove(0);
                    players.remove(0);
                }


            }
        } catch (IOException e) {
            System.err.println("Ooops... " + e);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
