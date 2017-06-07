package basics;

import java.io.IOException;
import java.sql.SQLException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import storage.CitiesHaveDataException;
import storage.DataBase;

/**
 *
 * @author Dimitris
 */
public class Location {

    JSONReader a = new JSONReader();
    public ArrayList<String> city = new ArrayList<String>();
    ArrayList<String> cityInfo = new ArrayList<String>();
    DataBase db = new DataBase();
    String name;
    String id;
    String x;
    String y;
    
    public ArrayList<String> getCities() throws IOException {   //εδώ παίρνω τις πόλεις από τη wikipedia και τις αποθηκεύω σε ένα arraylist

        Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/List_of_cities_in_Switzerland").userAgent("Chrome").get();
        if (city.isEmpty()) {
            for (Element table : doc.select("table.wikitable")) {
                for (Element rows : table.select("tr")) {
                    Elements tds = rows.select("td");
                    if (tds.size() > 5) {
                        city.add(tds.get(0).text());
                    }

                }

            }
        }
        return city;
    }

    public ArrayList<String> GetInfo() throws IOException, ParseException, JSONException, SQLException, CitiesHaveDataException {
        if (cityInfo.isEmpty()) { // επαναληπτικά για κάθε πόλη της πάνω arraylist χτυπάω το API, παίρνω τις πληροφορίες που θέλω και τις αποθηκεύω σε ένα arraylist καθώς και στη βάση
            try {
                for (int j = 0; j < city.size(); j++) {

                    JSONObject json = a.readJsonFromUrl("http://transport.opendata.ch/v1/locations?query=" + city.get(j));

                    JSONArray array = (JSONArray) json.get("stations");

                    JSONObject first = (JSONObject) array.get(0);
                    JSONObject second = (JSONObject) first.get("coordinate");

                    /*eprepe na valw ayto to if, giati toulaxiston se mia polh to url den edine to sunhthismeno array
                     alla ena allo para poly megalytero kai periergo opote den evriske to id*/
                    if (first.has("id")) {
                        cityInfo.add(city.get(j) + " " + first.get("id") + " " + second.get("x") + " " + second.get("y"));
                        String name = city.get(j);
                        Object id = first.get("id");
                        Object x = (float) second.get("x");
                        Object y = (float) second.get("y");
                        DataBase.InsertDataToCities(name, id, x, y);
                    } else {
                        cityInfo.add("could not get information");
                    }

                }

            } catch (IOException e) {
            }
        }
        return cityInfo;
    }

    /* μέθοδος που χτυπάει το API για μία μόνο πόλη, σε περίπτωση που η πόλη που έβαλε ο χρήστης
     δεν βρεθεί στη βάση*/
    public String infoForOneCity(String t) throws IOException, JSONException, SQLException, CitiesHaveDataException {
        String info = null;
        if (city.contains(t)) {
            JSONObject json = a.readJsonFromUrl("http://transport.opendata.ch/v1/locations?query=" + t);

            JSONArray array = (JSONArray) json.get("stations");

            JSONObject first = (JSONObject) array.get(0);
            JSONObject second = (JSONObject) first.get("coordinate");

            if (first.has("id")) {

                String name = t;
                Object id = first.get("id");
                Object x = second.get("x");
                Object y = second.get("y");
                //DataBase.InsertDataToCities(name, id, x, y);
                info = t + " " + first.get("id") + " " + second.get("x") + " " + second.get("y");
            } else {
                System.out.println("could not get information");
            }

        }
        return info;
    }
}
