package db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

//metodo para conectar o banco de dados:
    private static Connection conn = null;

    public static Connection getCon(){

        if (conn == null){ // se o metodo estiver nulo, utiliza a classe para achar a conexão
            Properties properties = loadPropperties();
            String url = properties.getProperty("dburl");
            try {
                conn = DriverManager.getConnection(url, properties);
            } catch (SQLException e) {
                throw new DbExeptions(e.getMessage());
            }
        }
        return conn;
    }

    public static void closeStatement(Statement statement){
        if (statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
            throw new DbExeptions(e.getMessage()); // para nao obrigar o java a tratar e liberar memoria
            }
        }
    }

    public static void closeResultset(ResultSet resultSet){
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DbExeptions(e.getMessage());
            }
        }
    }

    public static void closeConnection(){
        if (conn != null){
            try {
                conn.close();
            }
            catch (SQLException e){
                throw new DbExeptions(e.getMessage());
            }
        }
    }


    private static Properties loadPropperties(){
        try (FileInputStream fs = new FileInputStream("db.properties")){
            Properties properties = new Properties();
            properties.load(fs);
            return properties;

        } catch (IOException e) {
            throw new DbExeptions(e.getMessage());
        }
    }

}
