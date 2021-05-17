package game.AI;

import frames.MainFrame;
import game.components.Board;
import game.components.Spike;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Clasa care muta piesele pe care se apasă click
 */
public class ComputerListener implements MouseListener {
    private Board board;
    private MainFrame frame;
    private int moves=0;


    public ComputerListener(Board board,MainFrame frame) {
        this.frame = frame;
        this.board = board;
    }


    /**
     * Întoarce daca spikeFrom și spikeTo au aceeași culoare
     * @return true, false
     */
    private boolean sameColor(){
        return board.getSpikeFrom().getPieces().get(0).getColor().equals(board.getSpikeTo().getPieces().get(0).getColor());
    }
    /**
     * Mută o piesă din spikeFrom în spikeTo
     */
    private void movePiece(){

        if(board.getSpikeFrom().hasPieces() && board.getSpikeTo().hasPieces()){
            if(sameColor()){
                board.getSpikeTo().addPiece(board.getSpikeFrom().getPieces().get(0));
                board.getSpikeFrom().removePiece();

            }else {
                if(board.getSpikeTo().onePiece()){
                    board.getSpikeTo().eatPiece(0);
                    board.getSpikeTo().addPiece(board.getSpikeFrom().getPieces().get(0));
                    board.getSpikeFrom().removePiece();
                    board.repaint();
                }

            }
        }else if(board.getSpikeFrom().hasPieces() && !board.getSpikeTo().hasPieces()){
            board.getSpikeTo().addPiece(board.getSpikeFrom().getPieces().get(0));
            for(Spike spike: board.getSpikes()){
                if(spike.equals(board.getSpikeFrom())){
                    spike.removePiece();
                    board.repaint();
                }
            }
        }

    }

     /**
     * Verifică dacă player-ul a pus două sau mai multe piese pe spike-ul 19
     * @return true - a câștigat, false - nu a câștigat
     */
    private boolean iWon(){
        for(Spike spike:board.getSpikes()){
            if(spike.getId() == 19)
                if(spike.getPieces().size() >= 2)
                    return true;
        }
        return false;
    }
    /**
     * Afișează pe ecran câștigătorul
     */
    private void winner(){
        JLabel label = new JLabel("Player Won!!");
        label.setFont(new Font("Calibri", Font.PLAIN, 24));

        frame.remove(board);
        frame.add(label,BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Setează spikeFrom dacă se apasă pe un spike de pe tablă
     * @param e -click event
     */
    @Override
    public void mousePressed(MouseEvent e) {

        System.out.println("Mouse Clicked!!");
        int x = e.getX();
        int y = e.getY();
        if(Utils.onTheBoard(x,y)){
            for (Spike spike:board.getSpikes()){
                if(Utils.onTheSpike(x,y,spike)){
                    System.out.println("Pe spike " + spike.getId());
                    board.setSpikeFrom(spike);
                    break;
                }
            }
        }

    }
    /**
     * Setează spikeTo, și mută piesa de pe spikeFrom pe spikeTo.
     * După o mutare a jucătorului, computerul mută
     * @param e - click event
     */
    @Override
    public void mouseReleased(MouseEvent e) {

        System.out.println("Mouse released!");
        int x = e.getX();
        int y = e.getY();
            if(Utils.onTheBoard(x,y)){
                for (Spike spike:board.getSpikes()){
                    if(Utils.onTheSpike(x,y,spike)){
                        System.out.println("x: " + spike.getSX());
                        System.out.println("y: " + spike.getSY());
                        board.setSpikeTo(spike);
                        movePiece();
                        if(iWon())
                            winner();
                        moves++;
                        Computer computer = new Computer(board,frame);
                        System.out.println("MOVES: " + moves);
                        if(moves%2 ==0)
                            computer.move();
                        break;
                    }
                }
            }



    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
