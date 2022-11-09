package uet.oop.bomberman;
import java.io.*;
import java.security.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class Highscore {
    public Highscore() {
    }
    private ArrayList<Integer> high = new ArrayList<Integer>();
    public void highScore() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("res/high.txt"));
            String line = reader.readLine();
            while (line != null) {
                try {
                    Integer score = Integer.parseInt(line);
                    System.out.println(score);
                    high.add(score);
                } catch (NumberFormatException e1) {
                }
                line = reader.readLine();
            }
            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Highscore he=new Highscore();
        he.highScore();
    }
}

