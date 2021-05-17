package frames;


import game.components.Board;

import javax.swing.*;
import java.awt.*;


public class MainFrame extends JFrame  {
    Board board;
    JLabel winner;


    public MainFrame(){
        super("Backgammon");
        init();

    }

    private void init(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }



}
