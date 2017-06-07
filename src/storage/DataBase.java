/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storage;

import java.sql.*;

public class DataBase {

    ResultSet rs = null;
    static Connection connection = null;

    public static void init() throws SQLException {
        if (connection == null) {
            System.out.println("Oracle JDBC Connection Trying");
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                System.out.println("Where is your Oracle JDBC driver?");
                e.printStackTrace();
                return;
            }
            System.out.println("Oracle JDBC Driver Registered");

            try {
                String url = "jdbc:oracle:thin:@10.100.51.123:1521:orcl";
                String username = "it21438";
                String password = "panathinaikosgate13";
                connection = DriverManager.getConnection(url, username, password);

            } catch (SQLException s) {
                System.out.println("Connection failed! Check output console");
                s.printStackTrace();
                return;
            }
        }
        if (connection != null) {
            System.out.println("You made it!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }

    public static void CreateCitiesTable() throws SQLException {  //table gia tis poleis
        PreparedStatement prepStatement = null;
        String CreateSQL = "CREATE TABLE Cities(" + "name VARCHAR(40) NOT NULL, " + "id NUMBER(15) NOT NULL, " + "x NUMBER(10,8) NOT NULL , " + "y NUMBER(10,8) NOT NULL" + ")";
        prepStatement = connection.prepareCall(CreateSQL);
        prepStatement.executeUpdate();
        System.out.println("First table created!");

    }

    public static void CreateDLTable() throws SQLException {   //table gia ta direct links
        PreparedStatement prepStatement = null;
        String CreateSQL = "CREATE TABLE DirectLinks(" + "DepartureCity VARCHAR(40) NOT NULL, " + "DepartureCityID NUMBER(15) NOT NULL, " + "DepartureTime VARCHAR(40) NOT NULL," + "ArrivalCity VARCHAR(40) NOT NULL, " + "ArrivalCityID NUMBER(15) NOT NULL," + "ArrivalTime VARCHAR(40) NOT NULL," + ")";
        prepStatement = connection.prepareCall(CreateSQL);
        prepStatement.executeUpdate();
        System.out.println("Second table created!");
    }

    public static void InsertDataToCities(String name, Object id, Object x, Object y) throws SQLException, CitiesHaveDataException {
        String insertData = "INSERT INTO Cities" + "(name, id ,x ,y) VALUES" + "(?,?,?,?)";
        PreparedStatement insertPrepState = connection.prepareStatement(insertData);
        insertPrepState.setString(1, name);
        insertPrepState.setObject(2, id);
        insertPrepState.setObject(3, x);
        insertPrepState.setObject(4, y);
        insertPrepState.executeUpdate();
        System.out.println("Row inserted!");
    }

    public static void InsertDataToDL(String DepartureCity, String DepartureCityID, String DepartureTime, String ArrivalCity, String ArrivalCityID, String ArrivalTime) throws SQLException, LinksHaveDataException {
        String insertData = "INSERT INTO DirectLinks" + "(DepartureCity, DepartureCityID, DepartureTime, ArrivalCity, ArrivalCityID, ArrivalTime) VALUES" + "(?,?,?,?,?,?)";
        PreparedStatement insertPrepState = connection.prepareStatement(insertData);
        Statement stmt = connection.createStatement();
        String sqlselect = "SELECT * from DirectLinks where DepartureCity='" + DepartureCity + "'" + " " + "and ArrivalCity='" + ArrivalCity + "'";

        insertPrepState.setObject(1, DepartureCity);
        insertPrepState.setObject(2, DepartureCityID);
        insertPrepState.setObject(3, DepartureTime);

        insertPrepState.setObject(4, ArrivalCity);
        insertPrepState.setObject(5, ArrivalCityID);
        insertPrepState.setObject(6, ArrivalTime);

        insertPrepState.executeUpdate();
        System.out.println("Row inserted!");

    }

    public void ResetCities() throws SQLException {
        if (connection != null) {
            Statement stmt = connection.createStatement();
            String SQLselect = "DELETE FROM Cities";
            ResultSet rs = stmt.executeQuery(SQLselect);
        }
    }

    public void ResetDL() throws SQLException {
        if (connection != null) {
            Statement stmt = connection.createStatement();
            String SQLselect = "DELETE FROM DirectLinks";
            ResultSet rs = stmt.executeQuery(SQLselect);
        }
    }

    public void dropTables() throws SQLException {
        if (connection != null) {
            Statement stmt = connection.createStatement();
            String SQLselect = "DROP table DirectLinks";
            String as = "DROP table Cities";
            ResultSet rs = stmt.executeQuery(SQLselect);
            rs = stmt.executeQuery(as);
        }
    }

    public String SearchCity(String a) throws SQLException {
        String city = null;
        if (connection != null) {
            Statement stmt = connection.createStatement();
            String sqlselect = "SELECT * from Cities where name='" + a + "'";
            rs = stmt.executeQuery(sqlselect);
            rs = stmt.getResultSet();
            while (rs.next()) {
                String name = rs.getString("name");
                int id = rs.getInt("id");
                float x = rs.getFloat("x");
                float y = rs.getFloat("y");
                //System.out.println(name + " " + id + " " + y + " " + x);
                city = (name + "\n" + "id=" + id + "\n" + "y=" + y + "\n" + "x=" + x);
            }
        }
        return city;
    }

    public String SearchDL(String a, String b) throws SQLException {
        String dl = null;
        if (connection != null) {
            Statement stmt = connection.createStatement();
            String sqlselect = "SELECT * from DirectLinks where DepartureCity='" + a + "'" + " " + "and ArrivalCity='" + b + "'";
            rs = stmt.executeQuery(sqlselect);
            rs = stmt.getResultSet();
            while (rs.next()) {
                String DepName = rs.getString("DepartureCity");
                String ArrName = rs.getString("ArrivalCity");
                int DepID = rs.getInt("DepartureCityID");
                int ArrID = rs.getInt("ArrivalCityID");
                String depTime = rs.getString("DepartureTime");
                String arrTime = rs.getString("ArrivalTime");

                //System.out.println(DepName + " " + DepID + " " + ArrName + " " + ArrID);
                dl = (DepName + " id:" + DepID + " Departure Time:" + depTime + "\n" + ArrName + " id:" + ArrID + " Arrival Time:" + arrTime);
            }
        }
        return dl;
    }

    public void ReadCitiesFromDB() {

    }

    public void ReadDLFromDB() {

    }

}
