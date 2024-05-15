package ni.org.ics.a2cares.app.ui.activities.enterdata;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.domain.core.Casa;
import ni.org.ics.a2cares.app.domain.core.DatosCoordenadas;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.domain.puntos.PuntoCandidato;
import ni.org.ics.a2cares.app.ui.fragments.enterdata.NuevoControlTemperaturaFragment;
import ni.org.ics.a2cares.app.utils.DeviceInfo;


public class NuevoControlTemperaturaTermoActivity extends AppCompatActivity {
	public static final String TAG = "NuevoControlTemperaturaTermoActivity";

	private Button mCancelView;
	private static final int EXIT = 1;
	private static final int LOCATION_REQUEST_CODE = 1;
	private AlertDialog alertDialog;
	private static Participante mParticipante = new Participante();
	private static Casa mCasa = null;
	private static PuntoCandidato mPunto = null;
	private List<PuntoCandidato> mPuntos = new ArrayList<>();
	private static Boolean esEntomologia = false;
	private Marker mLocationMarker;
	private GoogleMap mGoogleMap;
	private EditText latgps;
	private EditText longgps;
	private String latgpss;
	private String longgpss;
	private LocationRequest mLocationRequest;
	//private Location mLastLocation;
	private FusedLocationProviderClient mFusedLocationClient;
	private static int ubicacion;
	private GoogleApiClient googleApiClient;
	private View mapView;
	private FusedLocationProviderClient fusedLocationClient;
	private EstudioDBAdapter estudiosAdapter;
	private String username;
	private SharedPreferences settings;
	private DeviceInfo infoMovil;
	private static String codigoParticipante;
	private boolean visExitosa = false;
	private DatosCoordenadas mCoordenadas;
	private String PREFS_KEY = "mispreferencias";
	private View rootView;
	// Constants:
	final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
	// App ID to use OpenWeather data
	final String APP_ID = "5563c6a4ddd7d7181b257988cc2b1ad1";
	// Time between location updates (5000 milliseconds or 5 seconds)
	final long MIN_TIME = 5000;
	// Distance between location updates (1000m or 1km)
	final float MIN_DISTANCE = 1000;
	//Request Code
	final int REQUEST_CODE = 123;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		setContentView(R.layout.fragment_control_temperatura_termo);
	/*	if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
		} else {
			locationStart();
		}*/
		//if (savedInstanceState == null) {
			setContentView(R.layout.layout_data_enter_control_temp);
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			NuevoControlTemperaturaFragment fragment = new NuevoControlTemperaturaFragment();
			transaction.replace(R.id.data_content_fragment, fragment);
			//transaction.replace(R.id.mapa_control_asistencia_fragment, fragment);
			transaction.commit();



		//}
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_control_temperatura_termo, container, false);
	//	longgps = (EditText) rootView.findViewById(R.id.longitud);
	//	latgps = (EditText) rootView.findViewById(R.id.latitud);



		return rootView;
	}
	private void locationStart() {
		LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Localizacion Local = new Localizacion();
		Local.setMainActivity(this);
		final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (!gpsEnabled) {
			Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(settingsIntent);
		}
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
			return;
		}
		mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
		//mensaje1.setText("Localización agregada");
		//mensaje2.setText("");

	}

	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		if (requestCode == 1000) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				locationStart();
				return;
			}
		}
	}
	public void saveValuePreference(Context context, String latitud) {
		SharedPreferences settings;
		SharedPreferences.Editor editor;
		settings = PreferenceManager.getDefaultSharedPreferences(context);
		editor = settings.edit();
		editor.putString("latitudgps", latitud);
		editor.commit();
	}
	public void setLocation(Location loc) {
		//Obtener la direccion de la calle a partir de la latitud y la longitud
		if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
			try {
				Geocoder geocoder = new Geocoder(this, Locale.getDefault());
				List<Address> list = geocoder.getFromLocation(
						loc.getLatitude(), loc.getLongitude(), 1);
				latgpss =  Double.toString(loc.getLatitude());
				longgpss = Double.toString(loc.getLongitude());
				((MyIcsApplication) this.getApplication()).setLatitudapp(latgpss);
				((MyIcsApplication) this.getApplication()).setLongitudapp(longgpss);

				if (!list.isEmpty()) {
					Address DirCalle = list.get(0);
					//inputLatlong.setText("Mi direccion es: \n"
					//		+ DirCalle.getAddressLine(0));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/* Aqui empieza la Clase Localizacion */
	public class Localizacion implements LocationListener {
		NuevoControlTemperaturaTermoActivity nuevoControlAsistenciaGPSActivity;
		public NuevoControlTemperaturaTermoActivity getMainActivity() {
			return nuevoControlAsistenciaGPSActivity;
		}
		public void setMainActivity(NuevoControlTemperaturaTermoActivity nuevoControlAsistenciaGPSActivity) {
			this.nuevoControlAsistenciaGPSActivity = nuevoControlAsistenciaGPSActivity;
		}
		@Override
		public void onLocationChanged(Location loc) {
			// Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
			// debido a la deteccion de un cambio de ubicacion
			loc.getLatitude();
			loc.getLongitude();
			String Text = "Mi ubicacion actual es: " + "\n Lat = "
					+ loc.getLatitude() + "\n Long = " + loc.getLongitude();
			//mensaje1.setText(Text);
			this.nuevoControlAsistenciaGPSActivity.setLocation(loc);
		}
		@Override
		public void onProviderDisabled(String provider) {
			// Este metodo se ejecuta cuando el GPS es desactivado
			//mensaje1.setText("GPS Desactivado");
		}
		@Override
		public void onProviderEnabled(String provider) {
			// Este metodo se ejecuta cuando el GPS es activado
			//mensaje1.setText("GPS Activado");
		}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			switch (status) {
				case LocationProvider.AVAILABLE:
					Log.d("debug", "LocationProvider.AVAILABLE");
					break;
				case LocationProvider.OUT_OF_SERVICE:
					Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
					break;
				case LocationProvider.TEMPORARILY_UNAVAILABLE:
					Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
					break;
			}
		}
	}
}