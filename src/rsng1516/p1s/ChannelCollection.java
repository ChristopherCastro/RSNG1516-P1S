/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsng1516.p1s;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carol
 */
public class ChannelCollection {

    protected ArrayList<Channel> collection;
    protected String pathFile;

    public ChannelCollection(String pathFile) {
        this.collection = new ArrayList();        

        try {
            BufferedReader br = new BufferedReader(new FileReader(pathFile));
            String line = br.readLine();
            while (line != null) {
                String[] parameter = line.split("\t");
                int id = Integer.parseInt(parameter[0]);
                Channel canal = new Channel(id, parameter[1], parameter[2]);
                collection.add(canal);
                line = br.readLine();
            }

        } catch (IOException | NumberFormatException ex) {            
        }
    }
    

    public Channel getById(int i) {
        return this.collection.get(i);
    }

    public ArrayList<Channel> getCollection() {
        return collection;
    }
}
