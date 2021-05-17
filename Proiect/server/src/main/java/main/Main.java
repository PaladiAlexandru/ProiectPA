package main;

import server.MyServer;


/**
 * Pornim serverul să ruleze la protul setat in MyServer
 */
public class Main {
    public static void main(String[] args) {
        MyServer server = new MyServer();
        server.startServer();
    }
}
