package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class MyClient {

    private boolean myTurn= false;
    private boolean stillPlaying = true;
    public void connect() {
        boolean running = true;
        String serverAddress = "127.0.0.1";
        int PORT = 8101;
        Scanner sc = new Scanner(System.in);
        try (
                Socket socket = new Socket(serverAddress, PORT);
                PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()))) {
            if(in.readLine().equals("1")){
                myTurn = true;
            }
            while (stillPlaying){
                if(myTurn){
                    System.out.println("Enter the move :");
                    String move = sc.nextLine();
                    sendTo(out,move);
                    myTurn=false;
                }
                String response = in.readLine();
                System.out.println(response);
                myTurn=true;
            }
            String response = in.readLine();
            System.out.println(response);




        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendTo(PrintWriter to, String message){
        to.println(message);
        to.flush();

    }
}
