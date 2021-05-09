package game.components;

import javax.swing.*;
import java.awt.*;

public class Piece extends JPanel{
    private Spike spike;
    private int id;
    private Color color;


    public Piece(int id, Color color) {

        this.id = id;
        this.color = color;

    }

    public void init(){
        spike.getBoard().graphics.fillOval(0,0,10,10);
    }


    @Override
    public void update(Graphics g) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(!(spike == null))
        g.drawImage(spike.getBoard().image, 0, 0, this);
    }



}
