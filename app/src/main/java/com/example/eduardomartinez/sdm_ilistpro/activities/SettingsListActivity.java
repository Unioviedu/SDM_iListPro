package com.example.eduardomartinez.sdm_ilistpro.activities;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.eduardomartinez.sdm_ilistpro.GestorNewListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.R;
import com.example.eduardomartinez.sdm_ilistpro.TiposPreferencias;
import com.example.eduardomartinez.sdm_ilistpro.database.DatabaseORM;
import com.example.eduardomartinez.sdm_ilistpro.database.model.Supermercado;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import android.widget.Button;

import java.util.LinkedList;
import java.util.List;

public class SettingsListActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, LocationListener {

    private static final int ERROR = -1;
    private static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 1;

    private static final int PETICION_PERMISO_LOCALIZACION = 101;

    protected Location ubicacion;
    private GoogleApiClient cliente;
    private LocationRequest mLocationRequest;

    private Spinner spinnerSupermercados;
    private Switch soloUnSupermercado;
    private Switch miLocalizacion;
    private EditText nuevaLocalizacion;

    private static SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_list);

        pedirPermisos();

        if (cliente == null) {
            cliente = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        miLocalizacion = (Switch) findViewById(R.id.switchLocalizacion);
        spinnerSupermercados = (Spinner) findViewById(R.id.spinnerSupermercado);
        nuevaLocalizacion = (EditText) findViewById(R.id.editTextNuevaLocalizacion);
        llenarSpinner();
        soloUnSupermercado = (Switch) findViewById(R.id.switchUnSupermercado);
        restablecerPreferencias();
        añadirFunciones();
        crearLocationRequest();
    }

    private void restablecerPreferencias() {
        miLocalizacion.setChecked(preferences.getBoolean(TiposPreferencias.MiLocalizacion, true));
        nuevaLocalizacion.setEnabled(!miLocalizacion.isChecked());
        soloUnSupermercado.setChecked(preferences.getBoolean(TiposPreferencias.SoloUnSupermercado, false));
        spinnerSupermercados.setSelection(preferences.getInt(TiposPreferencias.SupermercadoSeleccionado, 0));
        spinnerSupermercados.setEnabled(soloUnSupermercado.isChecked());
    }

    private void añadirFunciones() {
        soloUnSupermercado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GestorNewListaCompra.getInstance().setSoloUnSupermercado(isChecked);
                seleccionarSupermercado();
                spinnerSupermercados.setEnabled(isChecked);
            }
        });

        miLocalizacion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                nuevaLocalizacion.setEnabled(!isChecked);
            }
        });

        spinnerSupermercados.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                        seleccionarSupermercado();
                    }
                    public void onNothingSelected(AdapterView<?> spn) {
                    }
                });
    }

    public void seleccionarSupermercado() {
        Supermercado supermercado = (Supermercado) spinnerSupermercados.getSelectedItem();
        GestorNewListaCompra.getInstance().setSupermercadoSeleccionado(supermercado);
    }

    @Override
    public void onStart(){
        super.onStart();
        cliente.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        cambiarPreferencias();
    }

    @Override
    public void onStop(){
        super.onStop();
        cliente.disconnect();
    }

    private void cambiarPreferencias () {
        final SharedPreferences.Editor mEditor = preferences.edit();
        mEditor.putBoolean(TiposPreferencias.MiLocalizacion, miLocalizacion.isChecked());
        mEditor.putBoolean(TiposPreferencias.SoloUnSupermercado, soloUnSupermercado.isChecked());
        mEditor.putInt(TiposPreferencias.SupermercadoSeleccionado, spinnerSupermercados.getSelectedItemPosition());
        mEditor.commit();
    }

    public static void preferenciasPorDefecto() {
        if (preferences != null) {
            final SharedPreferences.Editor mEditor = preferences.edit();
            mEditor.putBoolean(TiposPreferencias.MiLocalizacion, true);
            mEditor.putBoolean(TiposPreferencias.SoloUnSupermercado, false);
            mEditor.putInt(TiposPreferencias.SupermercadoSeleccionado, 0);
            mEditor.commit();
        }
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

        //LocationServices.FusedLocationApi.requestLocationUpdates(cliente, mLocationRequest, (LocationListener) this);
        ubicacion = LocationServices.FusedLocationApi.getLastLocation(cliente);
        Toast.makeText(this,"Empezamos a actualizar la localización!",Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"Nuestra ubicación es: [Latitud: "+ubicacion.getLatitude()+"; Longitud: "+ubicacion.getLongitude()+"]", Toast.LENGTH_LONG).show();

        return ubicacion;
    }

    private void llenarSpinner() {
        List<Supermercado> supermercadoList = new LinkedList<>();
        supermercadoList = DatabaseORM.getInstance().getSuperMercadosCercanos(0.0, 0.0);

        ArrayAdapter<Supermercado> adapter =
                new ArrayAdapter<Supermercado>(this, R.layout.support_simple_spinner_dropdown_item, supermercadoList);

        spinnerSupermercados.setAdapter(adapter);
    }
}
