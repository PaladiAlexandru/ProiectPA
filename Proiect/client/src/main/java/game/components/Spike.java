package game.components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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


    public Spike(Board board, int id, int sx, int y, Color color, int height, int width) {
        this.pieces = new ArrayList<>();
        this.board = board;
        this.id = id;
        this.sx = sx;
        this.sy = y;
        this.color = color;
        this.h = height;
        this.w = width;

        draw();
    }

    private void draw() {
        this.board.graphics.setColor(color);
        Polygon spike = new Polygon();
        spike.addPoint(sx, sy);
        spike.addPoint(sx + w, sy);
        spike.addPoint(sx + w / 2, h);
        this.board.graphics.fillPolygon(spike);

    }

    public void addPiece(int id, Color color) {
        Piece piece = new Piece(board, this, id, color, 50, 50);
        this.pieces.add(piece);
        drawPieces(this.sx, this.sy);
    }

    public void drawPieces(int x, int y) {
        // If we are drawing the bottom pieces
        if (y == Board.getH() - Board.getMargin()) {
            //To be on the table
            y = y - pieces.get(0).getPheight();
            for (Piece piece : pieces) {
                board.graphics.setColor(piece.getColor());
                board.graphics.fillOval(x, y, piece.getPwidth(), piece.getPheight());
                y = y - piece.getPheight();
            }
            // If we are drawing the top pieces
        } else {
            for (Piece piece : pieces) {
                board.graphics.setColor(piece.getColor());
                board.graphics.fillOval(x, y, piece.getPwidth(), piece.getPheight());
                y = y + piece.getPheight();
            }
        }

    }

    public void eatPiece(int index) {
        
        pieces.remove(index);
        repaintSpike();
    }

    public void clearSpike(int x, int y){
        this.board.graphics.setColor(Color.white);
        this.board.graphics.fillRect(x,y,w-5,h);
        this.board.repaint();
    }

    public void repaintSpike() {


        int x = this.sx;
        int y = this.sy;
        if (y == Board.getH() - Board.getMargin()) {

            clearSpike(x,y-h);
            draw();
            y = y - pieces.get(0).getPheight();
            for (Piece piece : pieces) {
                board.graphics.setColor(piece.getColor());
                board.graphics.fillOval(x, y, piece.getPwidth(), piece.getPheight());
                y = y - piece.getPheight();
            }
        } else {
            clearSpike(x,y);
            draw();
            for (Piece piece : pieces) {
                board.graphics.setColor(piece.getColor());
                board.graphics.fillOval(x, y, piece.getPwidth(), piece.getPheight());
                y = y + piece.getPheight();
            }
        }
        repaint();

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
    }
}
