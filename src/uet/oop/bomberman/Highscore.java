package uet.oop.bomberman;

//import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import javafx.application.Platform;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Highscore {
    private Statement statement = null;

    private Connection connection = null;

    String DB_URL = "jdbc:mysql://127.0.0.1:3307/";
    String USER_NAME = "root";
    String PASSWORD = "12345678";

    private Highscore() {
        try {
            connection = getConnection(DB_URL, USER_NAME, PASSWORD);
            statement = connection.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
            reConnection();
        }
    }

    private int reconnectCount = 0;

    private static final Highscore highScore = new Highscore();

    public static Highscore getInstance() {
        return highScore;
    }

    public void reConnection() {
        try {
            if (reconnectCount > 10) {
                if (!connection.isClosed()) {
                    connection.close();
                }
                Platform.exit();
            }
            if (!connection.isClosed()) {
                connection.close();
            }
            connection = getConnection(DB_URL, USER_NAME, PASSWORD);
            statement = connection.createStatement();
            reconnectCount++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection(String dbURL, String userName,
                                           String password) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, userName, password);
            System.out.println("connect successfully!");
            Statement stmt = conn.createStatement();
            boolean rs = stmt.execute("CREATE DATABASE IF NOT EXISTS bombermangame;");
            rs = stmt.execute("USE bombermangame;");
            rs = stmt.execute("CREATE TABLE IF NOT EXISTS highscorers(" +
                    "id INT NOT NULL UNIQUE AUTO_INCREMENT," +
                    "name VARCHAR(50) DEFAULT \"Bomber\"," +
                    "score INT NOT NULL," +
                    "CONSTRAINT id_pk PRIMARY KEY (id)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            rs = stmt.execute("CREATE TABLE IF NOT EXISTS savegame(" +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE," +
                    "name VARCHAR(50) DEFAULT \"Bomber\"," +
                    "score INT NOT NULL," +
                    "level INT NOT NULL," +
                    "CONSTRAINT id_pk PRIMARY KEY (id)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8");
            System.out.println("Create successfully");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }
        return conn;
    }

    public List<Pair<String, Integer>> getHighScore() {
        List<Pair<String, Integer>> highScore = new ArrayList<>();
        try  {
            ResultSet rs = statement.executeQuery("SELECT name, score FROM highscorers ORDER BY score DESC LIMIT 5;");
            while (rs.next()) {
                highScore.add(new Pair<>(rs.getString(1), rs.getInt(2)));
            }
            rs.close();
            return highScore;
        } catch (Exception ex) {
            ex.printStackTrace();
            reConnection();
            return getHighScore();
        }
    }

    public void addScore(String name, int score) {
        try {
            if (name.length() == 0) {
                boolean rs = statement.execute("INSERT INTO highscorers(score) VALUE(" +
                        score + ");");
                return;
            }
            boolean rs = statement.execute("INSERT INTO highscorers(name, score) VALUE(" + '"' + name + '"' +
                    ", " + score + ");");
        } catch (Exception ex) {
            ex.printStackTrace();
            reConnection();
            addScore(name, score);
        }
    }

    public void addSave(String name, int score, int level) {
        try {
            if (name.length() == 0) {
                boolean rs = statement.execute("INSERT INTO savegame(score, level) VALUE(" +
                        score + "," + level + ");");
                return;
            }
            boolean rs = statement.execute("INSERT INTO savegame(name, score, level) VALUE(" + '"' + name + '"' +
                    ", " + score + "," + level + ");");
        } catch (Exception ex) {
            ex.printStackTrace();
            reConnection();
            addSave(name, score, level);
        }
    }

    public Pair<Pair<String, Integer>,Integer> returnSave() {
        try  {
            ResultSet rs = statement.executeQuery("SELECT name, score, level FROM savegame ORDER BY id DESC LIMIT 1;");
            Pair<Pair<String, Integer>, Integer> save = null;
            while (rs.next()) {
                save = new Pair<>(new Pair<>(rs.getString(1), rs.getInt(2)), rs.getInt(3));
            }
            rs.close();
            boolean rsUpdate = statement.execute("DELETE FROM savegame");
            return save;
        } catch (Exception ex) {
            ex.printStackTrace();
            reConnection();
            return returnSave();
        }
    }
}

