package frames;

import client.MyClient;
import client.GameRunningThread;
import game.AI.ComputerListener;
import game.components.Board;
import game.logic.listeners.GameListener;
import game.logic.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Clasa responsabilă cu alegerea tipului de joc
 */
public class SettingsGame extends JPanel implements ActionListener {
    final MainFrame frame;
    JComboBox<String> gameType;

    public SettingsGame(MainFrame frame) {
        this.frame = frame;
        init();
    }

    /**
     * Adaugă pe frame butonul de start și lista cu tipurile de joc
     */
    private void init() {
        JButton startBtn = new JButton("Start");
        startBtn.addActionListener(this);
        add(startBtn);

        gameType = new JComboBox<>();
        gameType.addItem("Human");
        gameType.addItem("Computer");
        add(gameType);
    }

    /**
     * Când este apăsat butonul de start, dacă tipul jocului este "Human" atunci se connectează la server.
     * Dacă tipul de joc este "Computer" pornește un joc local cu computer-ul.
     * @param e - evenimentul creat
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (gameType.getSelectedItem().equals("Human")) {
            MyClient client = new MyClient();
            client.connect();
            Board board = new Board(frame);
            GameListener gameListener = new GameListener(board, client.getOut(),frame);
            board.addMouseListener(gameListener);
            frame.remove(this);
            frame.add(board);
            frame.setVisible(true);

            gameListener.setPlayer(new Player("player"));



            try {
                gameListener.getPlayer().setMyTurn(client.getIn().readLine().equals("3"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            System.out.println(gameListener.getPlayer().isMyTurn());
            new GameRunningThread(client.getOut(), client.getIn(), gameListener,board,frame).start();
        }else{
            Board board = new Board(frame);
            ComputerListener computerListener = new ComputerListener(board,frame);
            board.addMouseListener(computerListener);
            frame.remove(this);
            frame.add(board);
            frame.setVisible(true);
        }


    }


}
