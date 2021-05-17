package main;

import frames.MainFrame;
import frames.SettingsGame;

import javax.swing.*;

/**
 * Creează un JFrame la care adaugă componenta SettingsGame, de unde începe jocul
 */
public class Main {
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        SettingsGame settingsGame = new SettingsGame(frame);
        frame.add(settingsGame);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
    }
}
