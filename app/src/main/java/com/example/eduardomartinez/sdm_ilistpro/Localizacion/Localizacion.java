package com.example.eduardomartinez.sdm_ilistpro.Localizacion;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.eduardomartinez.sdm_ilistpro.GestorNewListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.SupermercadoNombres;
import com.example.eduardomartinez.sdm_ilistpro.database.model.Supermercado;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by eduardomartinez on 16/11/17.
 */

public class Localizacion  {

    private static Localizacion instance;

    //campos necesarios para la geolocalización
    private static final int MY_ACCESS_FINE_LOCATION = 1;
    private Location ubicacion;
    private static GoogleApiClient cliente;
    private LocationRequest mLocationRequest;

    private static Activity activity;
    private Geocoder geocoder;
    private Location ubicacionDir;

    public boolean acabado = false;

    private final String APIkey = "AIzaSyAhU5WdMZV-D4mmhcTk-PpuE7zbjPNuGdc";

    private Localizacion() {
        pedirPermisos();
        crearClienteLocalizacion();
        crearPeticionLocalizacion();
        cliente.connect();
        geocoder = new Geocoder(activity, Locale.getDefault());
    }

    public static Localizacion getInstance() {return instance;}

    public static Localizacion getInstance(Activity a) {
        if(instance == null){
            activity = a;
            instance = new Localizacion();
        }
        return instance;
    }
/*
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        // Para actualizar la ubicación según nos movemos:
        // ubicacion = LocationServices.FusedLocationApi.requestLocationUpdates(cliente, mLocationRequest, (LocationListener) settings);
        ubicacion = LocationServices.FusedLocationApi.getLastLocation(cliente);
        //ver a que distancia estamos de los supermercados
    }
    @Override
    public void onLocationChanged(Location location) {
        ubicacion = location;
        //aqui deberiamos llamar a un metodo que compruebe si hemos entrado en el radio de algun supermercado (com.example.eduardomartinez.sdm_ilistpro.Geofencing)
        //checkGeofences();
    }*/

    private void pedirPermisos(){
        //comprobamos si tenemos permiso para acceder a la ubicacion del dispositivo
        if(ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                return;
            }else{
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_ACCESS_FINE_LOCATION);
            }
        }
    }
    private void crearClienteLocalizacion(){
        if(cliente == null){
            cliente = new GoogleApiClient.Builder(activity)
                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) activity)
                    .addApi(LocationServices.API)
                    .build();
        }
    }
    private void crearPeticionLocalizacion(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public Location getUbicacionActual() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        ubicacion = LocationServices.FusedLocationApi.getLastLocation(cliente);
        return ubicacion;
    }

    public Location pedirUbicacion(String selected){
        //en este metodo recibiremos de un text field la localizacion
        ubicacionDir = getResults(selected);
        return ubicacionDir;
    }
    public Location getResults(String selected){
        ubicacionDir = new Location("");
        if(!selected.equals("")){
            try{
                List<Address> geoResults = geocoder.getFromLocationName(selected,1);
                while(geoResults.size() == 0){
                    geoResults = geocoder.getFromLocationName(selected,1);
                }
                if(geoResults.size() > 0 ){
                    Address direccion = geoResults.get(0);
                    Log.i("miguel","Los datos de la ubicacion seleccionada son: [lat: "+direccion.getLatitude()+", lon: "+direccion.getLongitude()+"]");
                    ubicacionDir.setLatitude(direccion.getLatitude());
                    ubicacionDir.setLongitude(direccion.getLongitude());
                }
            }catch(Exception exc){
                Log.e("direccion",exc.getMessage());
            }
        }else{
            Toast.makeText(activity,"El campo de dirección no puede estar vacío para determinar la ubicación",Toast.LENGTH_LONG).show();
        }
        return ubicacionDir;
    }

    public boolean getSupermercadosCercanos() {
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/xml?query=supermarkets&location=";
        ubicacion = GestorNewListaCompra.getInstance().getUbicacion();
        url += ubicacion.getLatitude()+","+ubicacion.getLongitude()+"&radius=";
        String radius = "1000";
        url += radius+"&key="+APIkey;
        Log.i("miguel", url);
        new OpenFile().execute(url);

        return true;
    }

    class OpenFile extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urlString){
            Log.i("miguel","Doing things in background...");
            LectorXML xml = new LectorXML(urlString[0]);
            xml.showList();
            /*try{
                URL url = new URL(urlString[0]);
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = factory.newSAXParser();
                XMLReader reader = parser.getXMLReader();
                InputSource is = new InputSource(url.openStream());
                reader.parse(is);
            }catch(Exception e){
                Log.e("miguel","Error with the asynchronous code!");
            }
            */
            return "";
        }
    }
}
