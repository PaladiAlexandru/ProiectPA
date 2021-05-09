package game.components;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Spike extends JPanel {
    private final Board board;
    private int id;
    private List<Piece> pieces;
    private int sx;
    private int sy;
    private Color color;
    private int h;
    private int w;


    public Spike(Board board,int id, int sx, int y, Color color, int height,int width) {
        this.board = board;
        this.id = id;
        this.sx= sx;
        this.sy = y;
        this.color = color;
        this.h = height;
        this.w = width;

        init();
    }
    private void init(){
        this.board.graphics.setColor(color);
        Polygon spike = new Polygon();
        spike.addPoint(sx,sy);
        spike.addPoint(sx+w,sy);
        spike.addPoint(sx+w/2,h);
        this.board.graphics.fillPolygon(spike);
    }
    public void addPiece(Piece piece){
        this.pieces.add(piece);
    }

    public Board getBoard() {
        return board;
    }


    public int getW() {
        return w;
    }

    public void setW(int width) {
        this.w = width;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }


    public int getSX() {
        return sx;
    }

    public void setX(int sx) {
        this.sx = sx;
    }

    public int getSY() {
        return sy;
    }

    public void setY(int y) {
        this.sy = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    public int getH() {
        return h;
    }

    public void setH(int height) {
        this.h = height;
    }
    @Override
    public void update(Graphics g) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(!(pieces ==null)){
            for (Piece piece:pieces){
                add(piece);
            }

        }

    }
}
