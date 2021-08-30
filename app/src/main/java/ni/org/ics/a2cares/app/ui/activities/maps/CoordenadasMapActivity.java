package ni.org.ics.a2cares.app.ui.activities.maps;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.DatosCoordenadas;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.preferences.PreferencesActivity;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuParticipanteActivity;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.utils.DeviceInfo;

public class CoordenadasMapActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnInfoWindowClickListener{

    public static final String EXTRA_LATITUD = "LATITUD";
    public static final String EXTRA_LONGITUD = "LONGITUD";
    private static final int LOCATION_REQUEST_CODE = 1;
    private static final int EXIT = 2;
    private static final int SAVE = 3;
    private static final int INFO = 4;

    private Marker mLocationMarker;

    private EditText inputLatlong;
    private ImageButton mButtonSave;
    private AlertDialog alertDialog;
    private View mapView;

    private static Participante mParticipante = new Participante();

    private GoogleMap mGoogleMap;

    private LocationRequest mLocationRequest;
    //private Location mLastLocation;
    private FusedLocationProviderClient mFusedLocationClient;

    private EstudioDBAdapter estudiosAdapter;
    private String username;
    private SharedPreferences settings;
    private DeviceInfo infoMovil;
    private boolean visExitosa = false;
    private DatosCoordenadas mCoordenadas;
    private static String codigoParticipante; //cuando es desde el wizard solo viene el codigo del participante

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markers);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);

        infoMovil = new DeviceInfo(CoordenadasMapActivity.this);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        username = settings.getString(PreferencesActivity.KEY_USERNAME, null);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(),mPass,false,false);
        mParticipante = (Participante) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
        visExitosa = getIntent().getBooleanExtra(Constants.VISITA_EXITOSA,false);

        codigoParticipante = getIntent().getStringExtra(Constants.COD_PARTICIPANTE);

        inputLatlong = (EditText) findViewById(R.id.latlong);
        mButtonSave = (ImageButton) findViewById(R.id.saveLatLong);
        mButtonSave.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDialog(SAVE);
            }
        });
        new CoordenadaActualTask().execute();
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    public void onBackPressed (){
        createDialog(EXIT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            // ¿Permisos asignados?
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(this, "Error de permisos", Toast.LENGTH_LONG).show();
            }
        }
    }

    //Se encarga de obtener la posición actual del dispositivo y poner el punto en el mapa
    final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                //mLastLocation = location;
                if (mLocationMarker != null) {
                    mLocationMarker.remove();
                }

                //Place current location marker
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Posicion actual");
                markerOptions.draggable(true);
                //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                mLocationMarker = mGoogleMap.addMarker(markerOptions);
                setLocationOnInputLatLong(mLocationMarker);
                //mover la camara en el mGoogleMap para mostrar el punto
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            } else {
                // Si no se encuentran datos de ubicacion en el movil, poner el punto del centro de salud por defecto
                LatLng cssfv = new LatLng(12.154826, -86.291345);
                mLocationMarker = mGoogleMap.addMarker(new MarkerOptions()
                        .position(cssfv)
                        .title("CSSFV")
                        .draggable(true)
                );
                //setLocationOnInputLatLong(mLocationMarker);
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(12.154, -86.290), 15.0f));
            }
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(300000); // cada 5 minutos actualiza la localidad actual
        mLocationRequest.setFastestInterval(300000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //al cargar el mapa, carga en la posicion actual del dispositivo
        if (mParticipante==null || (mParticipante.getCasa()!=null && (mParticipante.getCasa().getLatitud()==null || mParticipante.getCasa().getLatitud()<=0))){
            requestLocation();
        }else{
            setParticipantLocation();
        }
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        // Eventos
        //Listener al mover el punto
        mGoogleMap.setOnMarkerDragListener(this);
        //Listener al pedir locacion actual
        mGoogleMap.setOnMyLocationButtonClickListener(this);
        //Listener para mostrar direccion al dar click sobre la marca
        mGoogleMap.setOnInfoWindowClickListener(this);
        //Se cambia de posicion en la vista el boton 'myLocation'
        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 190);
        }

        /*PolygonsHelper polygonsHelper = new PolygonsHelper();
        addPolygon(polygonsHelper.getPolygonBoer());*/
    }

    private void addPolygon(List<LatLng> vertices){
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.addAll(vertices);
        polygonOptions.strokeColor(Color.parseColor("#7B11A2"));
        polygonOptions.fillColor(Color.argb(32, 156, 39, 176));
        //Polygon polygon = mGoogleMap.addPolygon(polygonOptions);
        mGoogleMap.addPolygon(polygonOptions);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        if (marker.equals(mLocationMarker)) {
            Toast.makeText(this, "INICIO", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        if (marker.equals(mLocationMarker)) {
            setLocationOnInputLatLong(marker);
        }
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        if (marker.equals(mLocationMarker)) {
            Toast.makeText(this, "FIN", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (marker.equals(mLocationMarker)) {
            createDialog(INFO);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        requestLocation();
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void setLocationOnInputLatLong(Marker marker){
        String position = String.format(Locale.getDefault(),
                getString(R.string.marker_detail_latlng),
                marker.getPosition().latitude,
                marker.getPosition().longitude);
        inputLatlong.setText(position);
    }

    private void setParticipantLocation(){
        //Si el participante tiene coordenadas, ponerlo en el mapa
        LatLng cssfv = new LatLng(mParticipante.getCasa().getLatitud(), mParticipante.getCasa().getLongitud());
        mLocationMarker = mGoogleMap.addMarker(new MarkerOptions()
                .position(cssfv)
                .title("Ubicacion participante")
                .draggable(true)
        );
        setLocationOnInputLatLong(mLocationMarker);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(12.154, -86.290), 15.0f));
        mGoogleMap.setMyLocationEnabled(true);
    }

    private void requestLocation(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Controles UI
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // Mostrar diálogo explicativo
                } else {
                    // Solicitar permiso
                    ActivityCompat.requestPermissions(
                            this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            LOCATION_REQUEST_CODE);
                }
            }
        }
        else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    private void createDialog(int dialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(dialog){
            case EXIT:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(this.getString(R.string.exiting));
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (codigoParticipante!=null){
                            Intent intent1 = new Intent();
                            intent1.putExtra("CODE_RESULT", "");
                            setResult(RESULT_OK, intent1);
                            finish();
                        }else {
                            // Finish app
                            Intent i = new Intent(getApplicationContext(),
                                    MenuParticipanteActivity.class);
                            i.putExtra(Constants.PARTICIPANTE, mParticipante);
                            i.putExtra(Constants.VISITA_EXITOSA, visExitosa);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.err_cancel), Toast.LENGTH_LONG);
                            toast.show();
                            dialog.dismiss();
                            finish();
                        }
                    }
                });
                builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                break;
            case SAVE:
                builder.setTitle(this.getString(R.string.confirm));
                if (codigoParticipante != null && codigoParticipante.equalsIgnoreCase("00000")) {
                    builder.setMessage("Confirma agregar coordenada al nuevo participante?");
                }else {
                    builder.setMessage("Desea agregar coordenada al participante " + (mParticipante != null ? mParticipante.getCodigo() : codigoParticipante) + "?");
                }
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Finish app
                        dialog.dismiss();
                        //saveCoordinate();
                        if (!inputLatlong.getText().toString().isEmpty()) {
                            if (mLocationMarker.getPosition() != null) {
                                if (codigoParticipante!=null){
                                        Intent intent1 = new Intent();
                                        intent1.putExtra("CODE_RESULT", inputLatlong.getText().toString());
                                        setResult(RESULT_OK, intent1);
                                        finish();
                                }else {
                                    new SaveCoordenadaTask().execute(mLocationMarker.getPosition().toString(),
                                            String.format(Locale.getDefault(), getString(R.string.marker_latlng_format), mLocationMarker.getPosition().latitude),
                                            String.format(Locale.getDefault(), getString(R.string.marker_latlng_format), mLocationMarker.getPosition().longitude));
                                }
                            }else {
                                showToast(getApplicationContext().getString(R.string.error_marker));
                            }
                        }else{
                            showToast(getApplicationContext().getString(R.string.wrongSelect,getApplicationContext().getString(R.string.latlng)));
                        }
                    }
                });
                builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                break;
            case INFO:
                builder.setTitle(this.getString(R.string.direccion)+ " -> "+ mParticipante.getCodigo());
                if (mCoordenadas!=null && mCoordenadas.getDireccion()!=null) {
                    builder.setMessage(mCoordenadas.getDireccion());
                } else if (mParticipante.getCasa().getDireccion()!=null) {
                    builder.setMessage(mParticipante.getCasa().getDireccion());
                } else {
                    builder.setMessage(getApplicationContext().getString(R.string.without_direction));
                }
                builder.setPositiveButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Finish app
                        dialog.dismiss();
                    }
                });
                break;
            default:
                break;
        }
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void showToast(String mensaje){
        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        ImageView imageView = (ImageView) layout.findViewById(R.id.image);
        imageView.setImageResource(R.mipmap.ic_help);

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(mensaje);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

  // ***************************************
    // Private classes
    // ***************************************
    private class CoordenadaActualTask extends AsyncTask<String, Void, String> {

        private ProgressDialog nDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(CoordenadasMapActivity.this);
            nDialog.setMessage("....");
            nDialog.setTitle("Cargando mapa");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            try {
                estudiosAdapter.open();
                if (codigoParticipante!=null && mParticipante == null)
                    mParticipante = estudiosAdapter.getParticipante(MainDBConstants.codigo + "=" + codigoParticipante, null);
                if (mParticipante!=null)
                    mCoordenadas = estudiosAdapter.getDatosCoordenada(MainDBConstants.participante + "=" + mParticipante.getCodigo(), null);
                return "exito";
            } catch (Exception e) {
                Log.e(this.getClass().getName(), e.getLocalizedMessage(), e);
                return "error";
            }finally {
                estudiosAdapter.close();
            }
        }

        protected void onPostExecute(String resultado) {
            // after the request completes, hide the progress indicator
            nDialog.dismiss();
        }
    }

    private class SaveCoordenadaTask extends AsyncTask<String, Void, String> {

        private ProgressDialog nDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(CoordenadasMapActivity.this);
            nDialog.setMessage("Favor espere....");
            nDialog.setTitle("Guardando");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            String error = "";
            try {
                String coordenadas = arg0[0];
                Double latitud = Double.valueOf(arg0[1]);
                Double longitud = Double.valueOf(arg0[2]);

                estudiosAdapter.open();

                boolean crearRegistro = false;
                if (mCoordenadas == null) {
                    mCoordenadas = new DatosCoordenadas();
                    mCoordenadas.setParticipante(mParticipante);
                    mCoordenadas.setCodigoCasa(mParticipante.getCasa().getCodigo());
                    mCoordenadas.setEstudios("A2CARES");
                    mCoordenadas.setMotivo("1");
                    //se toman los datos actuales del participante como validos
                    mCoordenadas.setBarrio(mParticipante.getCasa().getBarrio());
                    mCoordenadas.setManzana(mParticipante.getCasa().getManzana() != null ? Integer.parseInt(mParticipante.getCasa().getManzana()) : 0);
                    mCoordenadas.setDireccion(mParticipante.getCasa().getDireccion());
                    UUID deviceUuid = new UUID(infoMovil.getDeviceId().hashCode(), new Date().hashCode());
                    mCoordenadas.setCodigo(deviceUuid.toString());
                    crearRegistro = true;
                }
                mCoordenadas.setRecordDate(new Date());
                mCoordenadas.setRecordUser(username);
                mCoordenadas.setDeviceid(infoMovil.getDeviceId());
                mCoordenadas.setEstado(Constants.STATUS_NOT_SUBMITTED);
                mCoordenadas.setPasive(Constants.STATUS_NOT_PASIVE);
                mCoordenadas.setRazonNoGeoref(null);
                mCoordenadas.setOtraRazonNoGeoref(null);
                mCoordenadas.setPuntoGps(coordenadas);
                mCoordenadas.setLatitud(latitud);
                mCoordenadas.setLongitud(longitud);
                mCoordenadas.setConpunto(Constants.YESKEYSND);

                if (crearRegistro)
                    estudiosAdapter.crearDatosCoordenadas(mCoordenadas);
                else
                    estudiosAdapter.editarDatosCoordenadas(mCoordenadas);

                //mParticipante.getProcesos().setMovilInfo(movilInfo);
                //estudiosAdapter.actualizarParticipanteProcesos(mParticipante.getProcesos());
            } catch (Exception e) {
                // Presenta el error al parsear el xml
                error = e.toString();
                e.printStackTrace();
            } finally {
                estudiosAdapter.close();
            }
            if (error.equals("")) {
                return getApplicationContext().getString(R.string.success);
            } else {
                return error;
            }
        }

        protected void onPostExecute(String resultado) {
            nDialog.dismiss();
            Toast.makeText(CoordenadasMapActivity.this, resultado, Toast.LENGTH_LONG).show();
            // after the request completes, hide the progress indicator
            Intent i = new Intent(getApplicationContext(),
                    MenuParticipanteActivity.class);
            i.putExtra(Constants.PARTICIPANTE, mParticipante);
            i.putExtra(Constants.VISITA_EXITOSA, visExitosa);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }

}
