package game.components;

import frames.MainFrame;
import game.logic.listeners.DiceListener;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa care reprezintă tabla de joc
 */
public class Board extends JPanel  {
    final MainFrame frame;
    final static int W = 1000, H = 800,margin = 30;
    BufferedImage image;
    Graphics2D graphics;
    List<Spike> spikes;
    List<Piece> wDeadPieces;
    List<Piece> bDeadPieces;
    Spike spikeFrom;    //for when you move
    Spike spikeTo;
    Dice dice;
    JLabel value;





    public Board(MainFrame frame) {
        this.spikes = new ArrayList<>();
        this.wDeadPieces = new ArrayList<>();
        this.bDeadPieces = new ArrayList<>();
        this.frame = frame;
        init();
    }

    /**
     * Se creează toate componentele necesare unui joc
     */
    private void init(){
        createOffscreenImage();
        addSpikes();
        addPieces();
        addDice();

    }

    /**
     * Adaugăm un zar care ia valori random intre 1, si 6
     */
    private void addDice() {
        JPanel panel = new JPanel();

        JButton diceBtn = new JButton("Roll");
        value = new JLabel("Value: 0, 0");
        dice = new Dice();
        diceBtn.addActionListener(new DiceListener(dice,value));
        panel.add(diceBtn);
        panel.add(value);
        add(panel);
    }

    /**
     * Se deseneaza și se adaugă la lista de spike-uri fiecare spike în parte
     */
    private void addSpikes() {
        Color marginColor = Color.darkGray;
        int heightSpike = 300;
        int widthSpike = 60;

        int spikeId = 0;
        Color spikeColor1 = Color.darkGray;
        Color spikeColor2= Color.gray;
        Color spikeColor;
        for (int i = margin; i <= H-100 ; i = i + widthSpike) {
            if (spikeId % 2 == 0) {
                spikeColor = spikeColor1;
            } else {
                spikeColor = spikeColor2;
            }
            if (isMiddle(i, widthSpike, margin))
                i = i + 10;

            spikes.add(new Spike(this,spikeId,i,margin,spikeColor,heightSpike,widthSpike));
            spikeId++;
        }
        for (int i = margin; i <= H-100 ; i = i + widthSpike) {
            if (spikeId % 2 != 0) {
                spikeColor = spikeColor1;
            } else {
                spikeColor = spikeColor2;
            }
            if (isMiddle(i, widthSpike, margin))
                i = i + 10;

            spikes.add(new Spike(this,spikeId,i,H-margin,spikeColor,heightSpike+150,widthSpike));
            spikeId++;

        }
        drawMiddleLine(margin + 6 * widthSpike, marginColor);
        drawMargins(margin,marginColor,widthSpike);

    }

    /**
     * Adaugăm piesele inițiale pe fiecare spike in parte
     */
    private void addPieces(){
        Color white = Color.BLUE;
        Color black = Color.GREEN;
        int pieceSize = 50;
        for(int i=0;i<=4;i++)
            spikes.get(0).addPiece(new Piece(this, spikes.get(0), i, black, pieceSize));
        for(int i=5;i<=7;i++)
            spikes.get(4).addPiece(new Piece(this, spikes.get(4), i, white, pieceSize));
        for(int i=8;i<=12;i++)
            spikes.get(6).addPiece(new Piece(this, spikes.get(6), i, white, pieceSize));
        for(int i=13;i<=14;i++)
            spikes.get(11).addPiece(new Piece(this, spikes.get(11), i, black, pieceSize));
        for(int i=15;i<=19;i++)
            spikes.get(12).addPiece(new Piece(this, spikes.get(12), i, white, pieceSize));
        for(int i=20;i<=22;i++)
            spikes.get(16).addPiece(new Piece(this, spikes.get(16), i, black, pieceSize));
        for(int i=23;i<=27;i++)
            spikes.get(18).addPiece(new Piece(this, spikes.get(18), i, black, pieceSize));
        for(int i=28;i<=29;i++)
            spikes.get(23).addPiece(new Piece(this, spikes.get(23), i, white, pieceSize));

    }

    /**
     * Deseneaza marginile tablei de joc
     * @param margin - marginea dorită
     * @param color - culoarea dorită
     * @param widthSpike - lățimea spike-urilor
     */
    private void drawMargins(int margin,Color color,int widthSpike) {
        int rightWidth =margin+12*widthSpike+margin;
        graphics.setColor(color);
        graphics.fillRect(0,0, margin, H);
        graphics.fillRect(0,0,rightWidth,margin);
        graphics.fillRect(rightWidth-10,0,margin,H);
        graphics.fillRect(0,H-margin,rightWidth,margin);

    }

    /**
     * Verifica daca i este la mijlocul tablei
     * @param i - valoarea care vrem sa fie verificată
     * @param width - lățimea spike-urilor
     * @param margin - marginea tablei de joc
     * @return
     */
    private boolean isMiddle(int i, int width, int margin) {
        return i - margin == width * 6;
    }

    /**
     * Se creează suprafața pe care urmează să se deseneze tabla
     */
    public void createOffscreenImage() {
        image = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, W, H);
    }

    /**
     * Deseneaza linia de mijloc a tablei de joc
     * @param x - poziția unde să înceapă linia
     * @param color - culoarea liniei
     */
    private void drawMiddleLine(int x, Color color) {
        graphics.setColor(color);
        graphics.fillRect(x-10, 0, 20, H);


    }

    public static int getW() {
        return W;
    }

    public static int getH() {
        return H;
    }

    public static int getMargin() {
        return margin;
    }

    public List<Spike> getSpikes() {
        return spikes;
    }

    public void setSpikes(List<Spike> spikes) {
        this.spikes = spikes;
    }

    public Spike getSpikeFrom() {
        return spikeFrom;
    }

    public void setSpikeFrom(Spike spikeFrom) {
        this.spikeFrom = spikeFrom;
    }

    public Spike getSpikeTo() {
        return spikeTo;
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public void setSpikeTo(Spike spikeTo) {
        this.spikeTo = spikeTo;
    }

    @Override
    public void update(Graphics g) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }




}
