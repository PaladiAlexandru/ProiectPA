package client;

import frames.MainFrame;
import game.components.Board;
import game.components.Spike;
import game.logic.listeners.GameListener;


import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;

/**
 * Clasa responsabila cu rularea jocului
 */
public class GameRunningThread extends Thread {
    PrintWriter out;
    BufferedReader in;
    GameListener gameListener;
    Board board;
    MainFrame frame;

    public GameRunningThread(PrintWriter out, BufferedReader in, GameListener gameListener, Board board, MainFrame frame) {
        this.out = out;
        this.in = in;
        this.gameListener = gameListener;
        this.board = board;
        this.frame = frame;
    }

    /**
     * Cât timp nici un jucător nu a câștigat,mută pe rând.
     * Dacă nu este rânduljucătorului curent, acesta așteaptă până când myTurn este setat pe true
     */
    public void run() {

        try {
            while (!gameListener.isOver()) {


                if (!gameListener.getPlayer().isMyTurn()) {
                    System.out.println("Intru not my turn");
                    String fromX = in.readLine();
                    String fromY = in.readLine();
                    String toX = in.readLine();
                    String toY = in.readLine();
                    moveLocaly(Integer.parseInt(fromX), Integer.parseInt(fromY), Integer.parseInt(toX), Integer.parseInt(toY));
                    if(iWon()){
                        winner();
                        gameListener.setOver(true);
                    }

                    gameListener.getPlayer().setMyTurn(true);
                }

            }


        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    /**
     * Verifică dacă player-ul a pus două sau mai multe piese pe spike-ul 19
     * @return true - a câștigat, false - nu a câștigat
     */
    private boolean iWon() {
        for(Spike spike:board.getSpikes()){
            if(spike.getId() == 7) {
                if(spike.hasPieces())
                    if(spike.getPieces().size() >= 2)
                        return true;
            }
        }
        return false;
    }

    /**
     * Afișează pe ecran câștigătorul
     */
    private void winner(){
        JLabel label = new JLabel("Player " +board.getSpikes().get(7).getPieces().get(0).getColor() + "WON!!");
        label.setFont(new Font("Calibri", Font.PLAIN, 24));

        frame.remove(board);
        frame.add(label,BorderLayout.SOUTH);
        frame.setVisible(true);
    }


    /**
     * Mută local o pisă
     * @param fromX - de pe pozitia x
     * @param fromY - de pe pozitia y
     * @param toX - pe pozitia x
     * @param toY - pe pozitia y
     */
    private void moveLocaly(int fromX, int fromY, int toX, int toY) {
        System.out.println("Fromx: " +fromX);
        System.out.println("FromY: " +fromY);
        System.out.println("Tox: " +toX);
        System.out.println("toy: " +toY);
        System.out.println("INTRU IN MOVELOCALY");
        for (Spike spike : gameListener.getBoard().getSpikes()) {
            if (onTheSpike(fromX, fromY, spike)) {
                gameListener.getBoard().setSpikeFrom(spike);
            }
            if (onTheSpike(toX, toY, spike)) {
                gameListener.getBoard().setSpikeTo(spike);
            }
        }

        if (gameListener.getBoard().getSpikeFrom().hasPieces() && gameListener.getBoard().getSpikeTo().hasPieces()) {
            if (sameColor()) {
                gameListener.getBoard().getSpikeTo().addPiece(gameListener.getBoard().getSpikeFrom().getPieces().get(0));
                gameListener.getBoard().getSpikeFrom().removePiece();

            } else {
                if (gameListener.getBoard().getSpikeTo().onePiece()) {
                    gameListener.getBoard().getSpikeTo().eatPiece(0);
                    gameListener.getBoard().getSpikeTo().addPiece(gameListener.getBoard().getSpikeFrom().getPieces().get(0));
                    gameListener.getBoard().getSpikeFrom().removePiece();
                    gameListener.getBoard().repaint();
                }

            }
        } else if (gameListener.getBoard().getSpikeFrom().hasPieces() && !gameListener.getBoard().getSpikeTo().hasPieces()) {
            gameListener.getBoard().getSpikeTo().addPiece(gameListener.getBoard().getSpikeFrom().getPieces().get(0));
            for (Spike spike : gameListener.getBoard().getSpikes()) {
                if (spike.equals(gameListener.getBoard().getSpikeFrom())) {
                    spike.removePiece();
                    gameListener.getBoard().repaint();
                }
            }
        }
    }
    /**
     * Verifică dacă piesele de pe spike-ul de unde vrem să mutăm au aceeși culoare cu piesele de pe spike-ul
     * unde vrem să mutăm
     * @return true, false
     */
    private boolean sameColor() {
        return gameListener.getBoard().getSpikeFrom().getPieces().get(0).getColor().equals(gameListener.getBoard().getSpikeTo().getPieces().get(0).getColor());
    }


    private boolean onTheSpike(int x, int y, Spike spike) {
        return x == spike.getSX() && y == spike.getSY();

    }

}
