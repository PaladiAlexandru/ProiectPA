package game.components;

import javax.swing.*;
import java.awt.*;

public class Piece extends JPanel{
    private final Board board;
    private Spike spike;
    private int id;
    private Color color;
    private int pwidth;



    public Piece(Board board, Spike spike, int id, Color color, int width) {

        this.board = board;
        this.spike = spike;
        this.id = id;
        this.color = color;
        this.pwidth = width;

    }

    public Board getBoard() {
        return board;
    }

    public Spike getSpike() {
        return spike;
    }

    public void setSpike(Spike spike) {
        this.spike = spike;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getPwidth() {
        return pwidth;
    }

    public void setPwidth(int pwidth) {
        this.pwidth = pwidth;
    }





    @Override
    public void update(Graphics g) {
    }

    @Override
    protected void paintComponent(Graphics g) {

    }



}
