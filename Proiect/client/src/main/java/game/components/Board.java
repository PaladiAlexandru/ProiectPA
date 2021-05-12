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
    final static int W = 1000, H = 800,margin = 30;
    BufferedImage image;
    Graphics2D graphics;
    List<Spike> spikes;
    List<Piece> wDeadPieces;
    List<Piece> bDeadPieces;


    public static int getMargin() {
        return margin;
    }



    public Board(MainFrame frame) {
        this.spikes = new ArrayList<>();
        this.wDeadPieces = new ArrayList<>();
        this.bDeadPieces = new ArrayList<>();
        this.frame = frame;
        init();
    }
    private void init(){
        createOffscreenImage();
        addSpikes();
        addPieces();

    }

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


    private void addPieces(){
        Color white = Color.BLUE;
        Color black = Color.GREEN;
        for(int i=0;i<=4;i++)
            spikes.get(0).addPiece(i,black);
        for(int i=5;i<=7;i++)
            spikes.get(4).addPiece(i,white);
        for(int i=8;i<=12;i++)
            spikes.get(6).addPiece(i,white);
        for(int i=13;i<=14;i++)
            spikes.get(11).addPiece(i,black);
        for(int i=15;i<=19;i++)
            spikes.get(12).addPiece(i,white);
        for(int i=20;i<=22;i++)
            spikes.get(16).addPiece(i,black);
        for(int i=23;i<=27;i++)
            spikes.get(18).addPiece(i,black);
        for(int i=28;i<=29;i++)
            spikes.get(23).addPiece(i,white);

    }
    private boolean onTheBoard(int x,int y){
        if(x<Board.W&&y<Board.H)
            return true;
        return false;
    }
    private boolean onTheSpike(int x,int y,Spike spike){
        if(topSpike(spike)){
            return x>spike.getSX()&&x < spike.getSX() + spike.getW() && y> spike.getY() &&y < spike.getSY() + spike.getH();
        }
        return x < spike.getSX() + spike.getW() && x > spike.getSX()&& y < spike.getSY() && y > spike.getSY() - spike.getH();


    }
    private boolean topSpike(Spike spike){
        return spike.getSY() == Board.margin;
    }

    private void drawMargins(int margin,Color color,int widthSpike) {
        int rightWidth =margin+12*widthSpike+margin;
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

    public static int getW() {
        return W;
    }

    public static int getH() {
        return H;
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
        System.out.println("Xc: " + e.getX());
        System.out.println("Yc: " + e.getY());
        System.out.println("cacat");
        int x = e.getX();
        int y = e.getY();
        if(onTheBoard(x,y)){
            System.out.println("Pe tabla");
            for (Spike spike:spikes){
                if(onTheSpike(x,y,spike)){
                    System.out.println("Pe spike " + spike.getId());

                    spike.eatPiece(0);
                    break;
                }
            }
        }
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
