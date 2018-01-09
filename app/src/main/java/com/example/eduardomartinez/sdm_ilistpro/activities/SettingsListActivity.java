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
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eduardomartinez.sdm_ilistpro.GestorListaCompraActual;
import com.example.eduardomartinez.sdm_ilistpro.GestorNewListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.Localizacion.Localizacion;
import com.example.eduardomartinez.sdm_ilistpro.R;
import com.example.eduardomartinez.sdm_ilistpro.SupermercadoNombres;
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

    private Switch switchPrecioMin;
    private Switch switchPrecioMax;
    private SeekBar seekPrecioMin;
    private SeekBar seekPrecioMax;
    private TextView textPrecioMin;
    private TextView textPrecioMax;

    private TextView longitud;
    private TextView latitud;

    private static SharedPreferences preferences;
    private View btEstablecerUbicacion;
    private Localizacion loc = Localizacion.getInstance();

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
        spinnerSupermercados = (Spinner) findViewById(R.id.spinnerSupermercado);
        soloUnSupermercado = (Switch) findViewById(R.id.switchUnSupermercado);

        latitud = (TextView) findViewById(R.id.latitud);
        String lat;
        if (GestorNewListaCompra.getInstance().getUbicacion() != null)
            lat = String.valueOf(GestorNewListaCompra.getInstance().getUbicacion().getLatitude());
        else
            lat = "OFFLINE";
        latitud.setText(lat);

        longitud = (TextView) findViewById(R.id.longitud);
        String lng;
        if (GestorNewListaCompra.getInstance().getUbicacion() != null)
            lng = String.valueOf(GestorNewListaCompra.getInstance().getUbicacion().getLongitude());
        else
            lng = "OFFLINE";
        longitud.setText(lng);

        switchPrecioMin = (Switch) findViewById(R.id.switchPrecioMin);
        switchPrecioMax = (Switch) findViewById(R.id.switchPrecioMax);
        seekPrecioMin = (SeekBar) findViewById(R.id.seekBarPrecioMin);
        seekPrecioMax = (SeekBar) findViewById(R.id.seekBarPrecioMax);
        textPrecioMin = (TextView) findViewById(R.id.textViewPrecioMin);
        textPrecioMax = (TextView) findViewById(R.id.textViewPrecioMax);

        añadirFunciones();
        actualizarPrecios();
        llenarSpinner();
        restablecerPreferencias();

        textPrecioMin.setText(String.valueOf(seekPrecioMin.getProgress()));
        textPrecioMax.setText(String.valueOf(seekPrecioMax.getProgress()));
        seekPrecioMin.setEnabled(switchPrecioMin.isChecked());
        seekPrecioMax.setEnabled(switchPrecioMax.isChecked());
    }

    private void actualizarPrecios() {
        GestorNewListaCompra gestor = GestorNewListaCompra.getInstance();
        gestor.activadoPrecioMin = switchPrecioMin.isChecked();
        gestor.activadoPrecioMax = switchPrecioMax.isChecked();

        gestor.precioMin = seekPrecioMin.getProgress();
        gestor.precioMax = seekPrecioMax.getProgress();
    }

    private void restablecerPreferencias() {
        Log.i("ed7", "Restablecer preferencias "+preferences.getInt(TiposPreferencias.SupermercadoSeleccionado, 0));
        switchPrecioMin.setChecked(preferences.getBoolean(TiposPreferencias.PrecioMinSeleccionado, false));
        switchPrecioMax.setChecked(preferences.getBoolean(TiposPreferencias.PrecioMaxSeleccionado, false));

        seekPrecioMin.setProgress(preferences.getInt(TiposPreferencias.PrecioMin, 10));
        seekPrecioMax.setProgress(preferences.getInt(TiposPreferencias.PrecioMax, 10));

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

        switchPrecioMin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                seekPrecioMin.setEnabled(isChecked);
                actualizarPrecios();
            }
        });

        switchPrecioMax.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                seekPrecioMax.setEnabled(isChecked);
                actualizarPrecios();
            }
        });

        spinnerSupermercados.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                        seleccionarSupermercado();
                    }
                    public void onNothingSelected(AdapterView<?> spn) {
                    }
                });

        seekPrecioMin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textPrecioMin.setText(String.valueOf(progress));
                actualizarPrecios();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekPrecioMax.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textPrecioMax.setText(String.valueOf(progress));
                actualizarPrecios();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        cambiarPreferencias();
    }

    private void cambiarPreferencias () {
        Log.i("ed7", "Cambiar preferencias "+spinnerSupermercados.getSelectedItemPosition());
        final SharedPreferences.Editor mEditor = preferences.edit();
        mEditor.putBoolean(TiposPreferencias.PrecioMaxSeleccionado, switchPrecioMax.isChecked());
        mEditor.putInt(TiposPreferencias.PrecioMax, seekPrecioMax.getProgress());
        mEditor.putBoolean(TiposPreferencias.PrecioMinSeleccionado, switchPrecioMin.isChecked());
        mEditor.putInt(TiposPreferencias.PrecioMin, seekPrecioMin.getProgress());
        mEditor.putBoolean(TiposPreferencias.SoloUnSupermercado, soloUnSupermercado.isChecked());
        mEditor.putInt(TiposPreferencias.SupermercadoSeleccionado, spinnerSupermercados.getSelectedItemPosition());
        mEditor.commit();
    }

    public static void preferenciasPorDefecto() {
        Log.i("ed7", "Preferencias defecto");
        if (preferences != null) {
            final SharedPreferences.Editor mEditor = preferences.edit();
            mEditor.putBoolean(TiposPreferencias.PrecioMaxSeleccionado, false);
            mEditor.putInt(TiposPreferencias.PrecioMax, 70);
            mEditor.putBoolean(TiposPreferencias.PrecioMinSeleccionado, false);
            mEditor.putInt(TiposPreferencias.PrecioMin, 10);
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

    private void llenarSpinner() {
        List<Supermercado> supermercadoList = new LinkedList<>();
        supermercadoList = GestorNewListaCompra.getInstance().getSupermercadosCercanos();

        ArrayAdapter<Supermercado> adapter =
                new ArrayAdapter<Supermercado>(this, R.layout.support_simple_spinner_dropdown_item, supermercadoList);

        spinnerSupermercados.setAdapter(adapter);
        spinnerSupermercados.refreshDrawableState();
    }
}
