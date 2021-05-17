package game.AI;

import frames.MainFrame;
import game.components.Board;
import game.components.Dice;
import game.components.Spike;
import utils.Utils;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clasa care reprezintă computerul contra care joaca jucatorul
 */
public class Computer {

    Board board;

    Spike spikeFrom;
    Spike spikeTo;
    Color pieceColor = Color.BLUE;
    List<Spike> possibleSpikes;
    Dice computerDice;
    MainFrame frame;

    public Computer(Board board, MainFrame frame) {
        this.board = board;
        this.frame = frame;
        computerDice = new Dice();
    }

    /**
     * Setează un zar propriu, apoi caută posibilele mutări căutând să mute tot timpul cele mai aproape
     * de bază.
     */
    public void move() {
        Random random = new Random();
        int min = 1;
        int max = 6;
        computerDice.setNr1(random.nextInt(max) % (max - min + 1) + min);
        computerDice.setNr2(random.nextInt(max) % (max - min + 1) + min);
        findSpikeFrom();
        findSpikeTo();
        if(spikeTo != null && spikeFrom != null)
            movePiece();
        if(iWon())
            winner();
        board.repaint();

    }
    /**
     * Verifică dacă player-ul a pus două sau mai multe piese pe spike-ul 7
     * @return true - a câștigat, false - nu a câștigat
     */
    private boolean iWon(){
        for(Spike spike:board.getSpikes()){
            if(spike.getId() == 7)
                if(spike.getPieces().size() >= 2)
                    return true;
        }
        return false;
    }
    /**
     * Mută o piesă de pe spikeFrom pe spikeTo
     */
    private void movePiece() {
        if (spikeFrom.hasPieces() && spikeTo.hasPieces()) {
            if (sameColor()) {
                spikeTo.addPiece(spikeFrom.getPieces().get(0));
                spikeFrom.removePiece();

            } else {
                if (spikeTo.onePiece()) {
                    spikeTo.eatPiece(0);
                    spikeTo.addPiece(spikeFrom.getPieces().get(0));
                    spikeFrom.removePiece();

                }

            }
        } else if (spikeFrom.hasPieces() && !spikeTo.hasPieces()) {
            spikeTo.addPiece(spikeFrom.getPieces().get(0));
            for (Spike spike : board.getSpikes()) {
                if (spike.equals(spikeFrom)) {
                    spike.removePiece();

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
        return spikeFrom.getPieces().get(0).getColor().equals(spikeTo.getPieces().get(0).getColor());
    }

    /**
     * Seteaza spikeTo ca fiind unul  din posibilele spike-uri.
     * Este ales random
     */
    private void findSpikeTo() {
        Random random = new Random();
        if (!possibleSpikes.isEmpty()) {
            int spikeId = random.nextInt(possibleSpikes.size());
            spikeTo = possibleSpikes.get(spikeId);
        }

    }

    /**
     * Setează spikeFrom dacă respectă regulile jocului
     */
    private void findSpikeFrom() {
        for (Spike spike : board.getSpikes()) {
            if (spike.hasPieces())
                if (spike.getPieces().get(0).getColor() == pieceColor)
                    if (hasLegalMove(spike)) {
                        spikeFrom = spike;
                        break;
                    }
        }
    }

    /**
     * Dacă spike-ul este în bază
     * @param spike - spikeul pe care vrem să-l verificăm
     * @return true, false
     */
    private boolean notInBase(Spike spike) {
        return spike.getSX() <= 400 || spike.getSY() != 30;
    }

    /**
     * Verifică toate cazurile cu zarul. Cele care resprecăt regulile jocului, sunt adăugate în spike-uri
     * posibile
     * @param spike - spike-ul pe care vrem să-l verificăm
     * @return true, false
     */
    private boolean hasLegalMove(Spike spike) {
        possibleSpikes = new ArrayList<>();
        int index1 = 25, index2 = 25, index3 = 25;
        if (Utils.topSpike(spike)){
            if(notInBase(spike)){
                if(spike.getId() + computerDice.getNr1() < 12)
                    index1 = spike.getId() + computerDice.getNr1();
                if(spike.getId() + computerDice.getNr2() < 12)
                    index2 = spike.getId() + computerDice.getNr2();
                if((spike.getId() + computerDice.getNr1() + computerDice.getNr2()) < 12)
                    index3 = spike.getId() + computerDice.getNr1() + computerDice.getNr2();
            }


        } else {
            int spikeId = spike.getId();
            int dice1 = computerDice.getNr1();
            int dice2 = computerDice.getNr2();
            if (spikeId - dice1 < 12) {
                while (spikeId > 12) {
                    spikeId--;
                    dice1--;
                }
                index1 = dice1;
            }
            if (spikeId - dice2 < 12) {
                while (spikeId > 12) {
                    spikeId--;
                    dice1--;
                }
                index2 = dice2;
            }
            if (spikeId - (dice1 + dice2) < 12) {
                while (spikeId > 12) {
                    spikeId--;
                    dice1--;
                }
                index3 = dice1 + dice2;
            }

        }

        if (index1 < 24) {
            if (notInBase(spike))
                if (board.getSpikes().get(index1).onePiece())
                    possibleSpikes.add(board.getSpikes().get(index1));
                else if (!board.getSpikes().get(index1).hasPieces())
                    possibleSpikes.add(board.getSpikes().get(index1));
                else if (board.getSpikes().get(index1).hasPieces() && board.getSpikes().get(index1).getPieces().get(0).getColor() == pieceColor)
                    possibleSpikes.add(board.getSpikes().get(index1));
        }
        if (index2 < 24) {
            if (notInBase(spike))
                if (board.getSpikes().get(index2).onePiece())
                    possibleSpikes.add(board.getSpikes().get(index2));
                else if (!board.getSpikes().get(index2).hasPieces())
                    possibleSpikes.add(board.getSpikes().get(index2));
                else if (board.getSpikes().get(index2).hasPieces() && board.getSpikes().get(index2).getPieces().get(0).getColor() == pieceColor)
                    possibleSpikes.add(board.getSpikes().get(index2));
        }
        if (index3 < 24) {
            if (notInBase(spike))
                if (board.getSpikes().get(index3).onePiece())
                    possibleSpikes.add(board.getSpikes().get(index3));
                else if (!board.getSpikes().get(index3).hasPieces())
                    possibleSpikes.add(board.getSpikes().get(index3));
                else if (board.getSpikes().get(index3).hasPieces() && board.getSpikes().get(index3).getPieces().get(0).getColor() == pieceColor)
                    possibleSpikes.add(board.getSpikes().get(index3));
        }


        return !possibleSpikes.isEmpty();

    }

    /**
     * Afișează pe ecran câștigătorul
     */
    private void winner(){
        JLabel label = new JLabel("Computer Won!!");
        label.setFont(new Font("Calibri", Font.PLAIN, 24));

        frame.remove(board);
        frame.add(label,BorderLayout.SOUTH);
        frame.setVisible(true);
    }


}
