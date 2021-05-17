package utils;

import game.components.Board;
import game.components.Spike;

import java.io.PrintWriter;

public class Utils {

    /**
     * Verifică dacă x-ul și y-ul sunt pe tablă
     * @param x - poziția x pe care vrem să o verificăm
     * @param y - poziția y pe care vrem să o verificăm
     * @return true, false
     */
    public static boolean onTheBoard(int x, int y){
        return x < Board.getW() && y < Board.getH();
    }

    /**
     * Verificăm dacă spike-ul este sus
     * @param spike - spike-ul pe care vrem să-l verificăm
     * @return true, false
     */
    public static boolean topSpike(Spike spike){
        return spike.getSY() == Board.getMargin();
    }

    /**
     * Verifică dacă s-a apăsat click pe spike
     * @param x - poziția x pe care vrem să o verificăm
     * @param y - poziția y pe care vrem să o verificăm
     * @param spike - spike-ul pe care vrem să-l verificăm
     * @return true, false
     */
    public static boolean onTheSpike(int x,int y,Spike spike){
        if(topSpike(spike)){
            return x>spike.getSX()&&x < spike.getSX() + spike.getW() && y> spike.getY() &&y < spike.getSY() + spike.getH();
        }
        return x < spike.getSX() + spike.getW() && x > spike.getSX()&& y < spike.getSY() && y > spike.getSY() - spike.getH();


    }

    /**
     * Trimite un mesaj către to
     * @param to - serverul către care vrem să trimitem mesajul
     * @param message - mesajul pe care vrem să-l trimitem
     */
    public static void sendTo(PrintWriter to, String message){
        to.println(message);
        to.flush();

    }

}
