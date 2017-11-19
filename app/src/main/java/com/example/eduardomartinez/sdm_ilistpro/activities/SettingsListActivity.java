package com.example.eduardomartinez.sdm_ilistpro.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.eduardomartinez.sdm_ilistpro.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

<<<<<<< HEAD
import android.widget.Button;

=======
>>>>>>> 6a9fa523faf25bc3d5b991ac62b49b1f8c73b413
public class SettingsListActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, LocationListener {

    private static final int ERROR = -1;
    private static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 1;

<<<<<<< HEAD
    protected Location ubicacion;
    private GoogleApiClient cliente;
    private LocationRequest mLocationRequest;

    protected Button mLocationButton;
=======
    private static final int PETICION_PERMISO_LOCALIZACION = 101;

    protected Location ubicacion;
    private GoogleApiClient cliente;
    private LocationRequest mLocationRequest;
>>>>>>> 6a9fa523faf25bc3d5b991ac62b49b1f8c73b413

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        setContentView(R.layout.activity_settings_list);

        mLocationButton = (Button) findViewById(R.id.localizameButton);
=======
>>>>>>> 6a9fa523faf25bc3d5b991ac62b49b1f8c73b413

        pedirPermisos();

        if (cliente == null) {
            cliente = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        crearLocationRequest();
    }

    @Override
    public void onStart(){
        super.onStart();
        cliente.connect();
    }

    @Override
    public void onStop(){
        super.onStop();
        cliente.disconnect();
    }

    @Override
    public void onLocationChanged(Location location) {
        ubicacion = location;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //estoyAqui = LocationServices.FusedLocationApi.getLastLocation(clienteLocalizacion);
        //actualizarInterfaz(estoyAqui, new Date());
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this,"Conexión con el cliente perdida",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this,"Conexión no establecida con el cliente",Toast.LENGTH_SHORT).show();
    }

    private void pedirPermisos() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_ACCESS_FINE_LOCATION);

            }
        }
    }

    protected void crearLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(
                LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public Location localizame(View view){
        // Empezar a recoger actualizaciones, lo llamaremos desde el main en cuanto abramos la applicacion

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
<<<<<<< HEAD
        //LocationServices.FusedLocationApi.requestLocationUpdates(cliente, mLocationRequest, (LocationListener) this);
        ubicacion = LocationServices.FusedLocationApi.getLastLocation(cliente);
        Toast.makeText(this,"Empezamos a actualizar la localización!",Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"Nuestra ubicación es: [Latitud: "+ubicacion.getLatitude()+"; Longitud: "+ubicacion.getLongitude()+"]", Toast.LENGTH_LONG).show();
=======
        LocationServices.FusedLocationApi.requestLocationUpdates(cliente, mLocationRequest, (LocationListener) this);
        Toast.makeText(this,"Empezamos a actualizar la localización!",Toast.LENGTH_SHORT).show();
>>>>>>> 6a9fa523faf25bc3d5b991ac62b49b1f8c73b413
        return ubicacion;
    }
}
