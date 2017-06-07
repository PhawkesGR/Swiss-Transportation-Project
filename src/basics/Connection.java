/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basics;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.simple.parser.ParseException;
import storage.DataBase;
import storage.LinksHaveDataException;

public class Connection {

    JSONReader a = new JSONReader();
    Location b = new Location();
    DataBase db = new DataBase();
    String links = "";
    String DepartureCity;
    String DepartureCityID;
    String ArrivalCity;
    String ArrivalCityID;

    String depTime;
    String arrTime;
    ArrayList<String> directLinks = new ArrayList<String>();

    public ArrayList<String> getConnections() throws IOException, ParseException, JSONException, SQLException, LinksHaveDataException {
        try {

            for (int j = 0; j < b.getCities().size() - 1; j++) {
                for (int i = j + 1; i < b.getCities().size(); i++) {
                    JSONObject json = a.readJsonFromUrl("http://transport.opendata.ch/v1/connections?from=" + b.getCities().get(j) + "&to=" + b.getCities().get(i) + "&direct=1");

                    JSONArray array = (JSONArray) json.get("connections");
                    if (array.length() == 0) {
                        continue;
                    }
                    JSONObject first = (JSONObject) array.get(0);
                    JSONObject second = (JSONObject) first.get("from");
                    JSONObject third = (JSONObject) second.get("station");
                    JSONObject fourth = (JSONObject) first.get("to");
                    JSONObject fifth = (JSONObject) fourth.get("station");

                    depTime = second.getString("departure").replace("T", " ").replace(":00+0200", "");
                    arrTime = fourth.getString("arrival").replace("T", " ").replace(":00+0200", "");
                    DepartureCity = b.getCities().get(j);
                    ArrivalCity = b.getCities().get(i);
                    DepartureCityID = third.getString("id");
                    ArrivalCityID = fifth.getString("id");

                    DataBase.InsertDataToDL(DepartureCity, DepartureCityID, depTime, ArrivalCity, ArrivalCityID, arrTime);
                    directLinks.add("Departure City: " + DepartureCity + " id= " + DepartureCityID + "Departure Time: " + depTime + " Arrival City: " + ArrivalCity + " id= " + ArrivalCityID + "Arrival Time: " + arrTime);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return directLinks;
    }

    /* μια μέθοδος για να ψάχνει κατευθείαν για απευθείας σύνδεση όταν βάζει ο χρήστης τις δύο πόλεις και αυτες
     δεν έχουν περαστεί στη βάση. Έτρεξα την πάνω μέθοδο για περίπου 300 συνδιασμούς αλλά επειδή μετά από λίγο
     πάντα πέταγε ένα error ο σέρβερ (Server returned HTTP response code: 500 for URL),
     έκανα κι αυτή τη μέθοδο για να ψάχνει και να βγάζει τα αποτελέσματα κατευθείαν*/
    public String SingleConn(String z, String x) throws IOException, JSONException, SQLException, LinksHaveDataException {
        String conn = " ";

        JSONObject json = a.readJsonFromUrl("http://transport.opendata.ch/v1/connections?from=" + z + "&to=" + x + "&direct=1");

        JSONArray array = (JSONArray) json.get("connections");
        if (array.length() != 0) {

            JSONObject first = (JSONObject) array.get(0);
            JSONObject second = (JSONObject) first.get("from");
            JSONObject third = (JSONObject) second.get("station");
            JSONObject fourth = (JSONObject) first.get("to");
            JSONObject fifth = (JSONObject) fourth.get("station");
            

            depTime = second.getString("departure").replace("T", " ").replace(":00+0200", "");
            arrTime = fourth.getString("arrival").replace("T", " ").replace(":00+0200", "");
            DepartureCity = z;
            ArrivalCity = x;
            DepartureCityID = third.getString("id");
            ArrivalCityID = fifth.getString("id");

            DataBase.InsertDataToDL(DepartureCity, DepartureCityID, depTime, ArrivalCity, ArrivalCityID, arrTime);
            conn = "Departure City: " + DepartureCity + " id= " + DepartureCityID + " Departure Time: " + depTime + "\n" + "Arrival City: " + ArrivalCity + " id= " + ArrivalCityID + " Arrival Time: " + arrTime;

        }
        return conn;
    }

}
