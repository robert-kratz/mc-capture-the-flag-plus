package us.rjks.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 21.04.2021 / 00:20
 *
 **************************************************************************/

public class MySQL {

    private static String username = "root";
    private static String password = "";
    private static String database = "test";
    private static String host = "127.0.0.1";
    private static String port = "3306";

    public static Connection con;


    public static void connect() {

        if(!isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host +":" + port + "/" + database + "?autoReconnect=true", username, password);
                System.out.println("[DB] Connected successfully to SQL Database");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static void close() {
        if(isConnected()) {
            try {
                con.close();
                System.out.println("[DB] Disconnected successfully to SQL Database");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }


    public static boolean isConnected() {
        return con != null;
    }

    public static void createTable() {

        if(isConnected()) {
            try {
                con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS game_stats(uuid VARCHAR(100), data TEXT, created_at TEXT)");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public static void update(String qry) {

        if(isConnected()) {
            try {
                con.createStatement().executeUpdate(qry);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public static ResultSet getResult(String qry) {
        if(isConnected()) {
            try {
                return con.createStatement().executeQuery(qry);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return null;
    }

}
