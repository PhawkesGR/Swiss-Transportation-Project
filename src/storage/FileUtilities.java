/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import basics.Connection;
import basics.Location;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtilities {

    Connection a = new Connection();
    Location b = new Location();
    String FileNameForLinks = " ";

    public void setFileNameForLinks(String FileNameForLinks) {
        this.FileNameForLinks = FileNameForLinks;
    }
    String FileName = " ";

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }
    

    public void WriteDirectLinksToFile(String FileNameForLinks) throws IOException, ParseException, JSONException, SQLException, LinksHaveDataException {
        Scanner input = new Scanner(System.in);
        System.out.println("Please give a name for the file");
        setFileNameForLinks(input.next());
        String wr = a.getConnections().toString();
        File file = new File(FileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file, false);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(wr);
        bw.close();

    }

    public void WriteCitiesToFile(String FileName) throws IOException, ParseException, JSONException, SQLException, CitiesHaveDataException {
        String w = b.GetInfo().toString();
        File file = new File(FileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file, false);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(w);
        bw.close();

    }

    public ArrayList<String> ReadCitiesInfoFromFile(String FileName) {
        ArrayList<String> cityinfo = null;
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FileName))) {

            while ((line = br.readLine()) != null) {
                cityinfo.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityinfo;

    }
}
