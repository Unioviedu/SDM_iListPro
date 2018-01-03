package com.example.eduardomartinez.sdm_ilistpro.Localizacion;

import android.location.Location;
import android.util.Log;

import com.example.eduardomartinez.sdm_ilistpro.GestorNewListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.SupermercadoNombres;
import com.example.eduardomartinez.sdm_ilistpro.database.model.Supermercado;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by UO244924 on 02/01/2018.
 */

public class LectorXML {

    private List<Supermercado> supers = new ArrayList<Supermercado>();
    public LectorXML(String url){
        readFile(url);
    }

    public void readFile(String url){

        //String path = "xml.xml";
        //String url = "https://maps.googleapis.com/maps/api/place/textsearch/xml?query=supermarkets&location=43.367148,-5.872105&radius=150&key=AIzaSyCER1eyVzWXAFNhWmdqowGS-OJn1cMSupo";
        try{
            //File xml = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            //Document doc = dBuilder.parse(xml);
            Document doc = dBuilder.parse(new URL(url).openStream());

            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("result");

            if(doc.hasChildNodes()){
                getChilds(/*doc.getChildNodes()*/nList);
            }
        }catch(Exception e){
            e.printStackTrace();
            Log.i("miguel", e.getMessage());
        }
    }

    private void getChilds(NodeList list){
        String name;
        Long id;
        double lat = 0.0;
        double lon = 0.0;
        for(int i = 0; i < list.getLength(); i++){
            Log.i("miguel", list.getLength()+"");
            Node temp = list.item(i);
            if(temp.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) temp;
                Log.i("miguel", element.toString());
                if(temp.hasChildNodes()){
                    Log.i("miguel","We reach here ");

                    if(element.getElementsByTagName("geometry") != null) {
                        Element geometry = (Element) element.getElementsByTagName("geometry").item(0);
                        Element location = (Element) geometry.getElementsByTagName("location").item(0);

                        Log.i("miguel", location.getElementsByTagName("lat").item(0).getTextContent());

                        lat = Float.parseFloat(location.getElementsByTagName("lat").item(0).getTextContent());
                        lon = Float.parseFloat(location.getElementsByTagName("lng").item(0).getTextContent());

                        Log.i("miguel","Value of lat:" +lat);
                        Log.i("miguel","Value of lon:" +lon);
                    }
                    Log.i("miguel","We reach here too");
                }
                name = element.getElementsByTagName("name").item(0).getTextContent();
                Location location = new Location("");
                location.setLatitude(lat);
                location.setLongitude(lon);
                Supermercado sp = new Supermercado(null,name,location.getLatitude(), location.getLongitude());

                if (SupermercadoNombres.estaSupermercado(sp.getNombre()))
                    if (noEstaDuplicado(sp))
                        supers.add(sp);
            }
            Log.i("miguel","No error on child: "+i);
        }

        GestorNewListaCompra.getInstance().setSupermercadosCercanos(supers);
    }

    private boolean noEstaDuplicado(Supermercado sp) {
        for (Supermercado s: supers)
            if (s.getNombre().contains(sp.getNombre()))
                return false;

        return true;
    }
    public void showList(){
        for(Supermercado s : supers){
            Log.i("miguel","prueba "+s.toString());
        }
    }

    public List<Supermercado> getSupers(){
        return supers;
    }

    public class Supermarket {
        private String name;
        private String id;
        private Location location;

        public Supermarket(String name, Location location, String id){
            this.name = name;
            this.id = id;
            this.location = location;
        }

        public String toString(){
            return "[Supermarket: "+name+", with coordinates: [lon: "+location.getLongitude()+",lat: "+location.getLatitude()+"] and id: "+id+"]";
        }
        public String getName(){
            return name;
        }
        public String getId(){
            return id;
        }
        public Location getLocation(){
            return location;
        }
        public void setLocation(Location location){
            this.location = location;
        }
        public void setName(String name){
            this.name = name;
        }
        public void setId(String id){
            this.id = id;
        }
    }
}
