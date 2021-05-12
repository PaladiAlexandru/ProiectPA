package frames;


import game.components.Board;

import javax.swing.*;


public class MainFrame extends JFrame  {
    Board board;


    public MainFrame(){
        super("Backgammon");
        init();
        board.addMouseListener(this.board);
    }

    private void init(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        board = new Board(this);
        add(board);

        
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
