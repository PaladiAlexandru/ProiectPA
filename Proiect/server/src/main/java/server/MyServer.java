package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {
    public static final int PORT = 8101;
    List<Socket> players;

    public MyServer(){
        players = new ArrayList<>();
    }
    public void startServer(){
        boolean running = true;
        ServerSocket serverSocket = null ;
        try {
            serverSocket = new ServerSocket(PORT);
            while (running) {
                System.out.println ("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                players.add(socket);
                while(players.size() >= 2){
                    System.out.println("Two players found!");
                    new ClientThread(players.get(0),players.get(1)).start();
                    players.remove(0);
                    players.remove(0);
                }

            }
        } catch ( IOException e) {
            System.err. println ("Ooops... " + e);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
