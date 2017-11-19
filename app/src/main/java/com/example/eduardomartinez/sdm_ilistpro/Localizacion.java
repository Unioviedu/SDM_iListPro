package com.example.eduardomartinez.sdm_ilistpro;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.example.eduardomartinez.sdm_ilistpro.activities.SettingsListActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by eduardomartinez on 16/11/17.
 */

public class Localizacion implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static Localizacion instance;

    //campos necesarios para la geolocalización
    private static final int ERROR = -1;
    private Location ubicacion;
    private static GoogleApiClient cliente;
    private LocationRequest mLocationRequest;

    private static SettingsListActivity settings;


    private Localizacion() {
        if (cliente == null) {
            cliente = new GoogleApiClient.Builder(settings)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    public static Localizacion getInstance() {
        return instance;
    }

    public static Localizacion getInstance(SettingsListActivity s) {
        if (instance == null) {
            settings = s;
            instance = new Localizacion();
            cliente.connect();
        }

        return instance;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(settings, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(settings, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        // LocationServices.FusedLocationApi.requestLocationUpdates(clienteLocalizacion, mLocationRequest, (LocationListener) this);
        ubicacion = LocationServices.FusedLocationApi.getLastLocation(cliente);
        //ver a que distancia estamos de los supermercados
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Toast.makeText(this, "Se ha suspendido la conexión con el cliente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //Toast.makeText(this, "Error al conectar con el cliente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        ubicacion = location;
        //aqui deberiamos llamar a un metodo que compruebe si hemos entrado en el radio de algun supermercado (Geofencing)
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public Location pedirUbicacion(){
        //en este metodo recibiremos de un text field la localizacion
        return null;
    }

    protected void crearLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(
                LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public Location getUbicacionActual() {
        return ubicacion;
    }
}
