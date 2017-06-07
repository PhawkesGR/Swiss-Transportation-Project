/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basics;

import gui.GuiDemo;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import storage.CitiesHaveDataException;
import storage.DataBase;
import storage.FileUtilities;
import storage.LinksHaveDataException;

public class Transport {

    public static void main(String[] args) throws IOException, ParseException, JSONException, SQLException, CitiesHaveDataException, LinksHaveDataException {
        Location a = new Location();
        Connection b = new Connection();
        //FileUtilities c = new FileUtilities();
        DataBase d = new DataBase();
        DataBase.init();

////    DataBase.CreateCitiesTable();
//       DataBase.CreateDLTable();
//        a.getCities();
//        a.GetInfo();
        // b.getConnections();
//        Scanner input = new Scanner(System.in);
//        System.out.println("Please give a name for the cities file");
//        String NameForCities = input.next();
//        c.WriteCitiesToFile(NameForCities);
//        Scanner input2 = new Scanner(System.in);
//        System.out.println("Please give a name for the direct links file");
//        String NameForDL = input2.next();
//        c.WriteDirectLinksToFile(NameForDL);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GuiDemo().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Transport.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

}
