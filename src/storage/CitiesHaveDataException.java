/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Dimitris
 */
public class CitiesHaveDataException extends Exception {

    public CitiesHaveDataException() throws SQLException{
            Statement stmt = DataBase.connection.createStatement();
            String SQLselect = "SELECT * FROM Cities";
            ResultSet rs = stmt.executeQuery(SQLselect);
            if (rs.isBeforeFirst() ) {    
                System.out.println("Database already has data!"); 
            } 
    }   

}
