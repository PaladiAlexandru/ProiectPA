package game.logic.listeners;

import game.components.Dice;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class DiceListener implements ActionListener {
    Dice dice;
    JLabel val;

    public DiceListener(Dice dice, JLabel val) {
        this.dice = dice;
        this.val = val;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Random random = new Random();
        int min= 1;
        int max = 6;
        dice.setNr1(random.nextInt(max) % (max - min + 1) + min);
        dice.setNr2(random.nextInt(max) % (max - min + 1) + min);
        val.setText("Value: " + dice.getNr1() + ", " + dice.getNr2());
    }
}
