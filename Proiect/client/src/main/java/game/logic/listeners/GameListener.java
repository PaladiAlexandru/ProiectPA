package game.logic.listeners;

import frames.MainFrame;
import game.components.Board;
import game.components.Spike;
import game.logic.Player;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintWriter;

/**
 * Clasa responsabilă cu citirea mutărilor atunci când se apasă click
 */
public class GameListener implements MouseListener {
    MainFrame frame;
    Board board;
    Player player;
    boolean over = false;
    PrintWriter out;

    public GameListener(Board board, PrintWriter out, MainFrame frame) {
        this.board = board;
        this.out = out;
        this.frame =frame;
    }

    /**
     * Verifică dacă piesele de pe spike-ul de unde vrem să mutăm au aceeși culoare cu piesele de pe spike-ul
     * unde vrem să mutăm
     * @return true, false
     */
    private boolean sameColor(){
        return board.getSpikeFrom().getPieces().get(0).getColor().equals(board.getSpikeTo().getPieces().get(0).getColor());
    }
    /**
     * Mută o piesă de pe spikeFrom pe spikeTo
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
    private boolean iWon() {
        for(Spike spike:board.getSpikes()){
            if(spike.getId() == 19) {
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
        JLabel label = new JLabel("Player " +board.getSpikes().get(19).getPieces().get(0).getColor() + "WON!!");
        label.setFont(new Font("Calibri", Font.PLAIN, 24));

        frame.remove(board);
        frame.add(label,BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
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
     * Setează spikeTo, și   mută piesa de pe spikeFrom pe spikeTo
     * @param e - click event
     */
    @Override
    public void mouseReleased(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();
        if(player.isMyTurn()){
            if(Utils.onTheBoard(x,y)){
                for (Spike spike:board.getSpikes()){
                    if(Utils.onTheSpike(x,y,spike)){
                        System.out.println("Pe spike " + spike.getId());
                        board.setSpikeTo(spike);
                        movePiece();
                        if(iWon()){
                            winner();
                        }
                        Utils.sendTo(out,Integer.toString(board.getSpikeFrom().getSX()));
                        Utils.sendTo(out,Integer.toString(board.getSpikeFrom().getSY()));
                        Utils.sendTo(out,Integer.toString(spike.getSX()));
                        Utils.sendTo(out,Integer.toString(spike.getSY()));
                        player.setMyTurn(false);

                        break;
                    }
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
