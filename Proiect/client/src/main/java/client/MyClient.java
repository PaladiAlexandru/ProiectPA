package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Clasa responsabilă cu conectarea clientului la server
 */
public class MyClient {

    PrintWriter out;
    BufferedReader in;

    public MyClient() {
    }

    /**
     * Se conectează la server și setează output-ul și input-ul
     */
    public void connect() {

        String serverAddress = "127.0.0.1";
        int PORT = 8101;
        try {
            Socket socket = new Socket(serverAddress, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }


    public PrintWriter getOut() {
        return out;
    }

    public BufferedReader getIn() {
        return in;
    }
}
