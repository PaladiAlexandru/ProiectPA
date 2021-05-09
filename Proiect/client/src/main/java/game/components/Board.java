package game.components;

import frames.MainFrame;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Board extends JPanel implements MouseListener {
    final MainFrame frame;
    final static int W = 740, H = 800;
    BufferedImage image;
    Graphics2D graphics;
    List<Spike> spikes;
    List<Piece> pieces;


    public Board(MainFrame frame) {
        this.spikes = new ArrayList<>();
        this.pieces = new ArrayList<>();
        this.frame = frame;
        createOffscreenImage();
        createSpikes();
        createPieces();
        addPieces();
        addSpikes();

    }

    private void createSpikes() {
        Color marginColor = Color.darkGray;
        int heightSpike = 300;
        int widthSpike = 60;
        int margin = 10;
        int spikeId = 0;
        Color spikeColor1 = Color.darkGray;
        Color spikeColor2= Color.gray;
        Color spikeColor;
        for (int i = margin; i <= H - 120; i = i + widthSpike) {
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
        for (int i = margin; i <= H - 120; i = i + widthSpike) {
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
    private void createPieces(){

        for(int i =0; i<30;i++){
            if(i<=14)
                pieces.add(new Piece(i,Color.white));
            else
                pieces.add(new Piece(i,Color.BLACK));
        }
    }
    private void addSpikes(){
        for(Spike spike:spikes){
            add(spike);
        }
    }
    private void addPieces(){
        for(int i=0;i<=4;i++)
            spikes.get(0).add(pieces.get(i));
        for(int i=5;i<=7;i++)
            spikes.get(4).add(pieces.get(i));
        for(int i=8;i<=12;i++)
            spikes.get(6).add(pieces.get(i));
        for(int i=13;i<=14;i++)
            spikes.get(11).add(pieces.get(i));
        for(int i=15;i<=19;i++)
            spikes.get(12).add(pieces.get(i));
        for(int i=20;i<=22;i++)
            spikes.get(16).add(pieces.get(i));
        for(int i=23;i<=27;i++)
            spikes.get(18).add(pieces.get(i));
        for(int i=28;i<=29;i++)
            spikes.get(23).add(pieces.get(i));

    }

    private void drawMargins(int margin,Color color,int widthSpike) {
        int rightWidth =margin+12*widthSpike+10;
        graphics.setColor(color);
        graphics.fillRect(0,0, margin, H);
        graphics.fillRect(0,0,rightWidth,margin);
        graphics.fillRect(rightWidth-10,0,margin,H);
        graphics.fillRect(0,H-margin,rightWidth,margin);

    }

    private boolean isMiddle(int i, int width, int margin) {
        return i - margin == width * 6;
    }

    public void createOffscreenImage() {
        image = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, W, H);
    }


    private void drawMiddleLine(int x, Color color) {
        graphics.setColor(color);
        graphics.fillRect(x-10, 0, 20, H);


    }

    private void drawPieces(){

        graphics.fillOval(0,0,100,100);
    }

    @Override
    public void update(Graphics g) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("cacat");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
