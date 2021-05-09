package main;

import client.MyClient;
import frames.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new MainFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
//        MyClient client = new MyClient();
//        client.connect();

    }
}
