package main;

import server.MyServer;

import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) {
        MyServer server = new MyServer();
        server.startServer();
    }
}
