package ni.org.ics.a2cares.app.ui.activities.enterdata;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import ni.org.ics.a2cares.app.AbstractAsyncActivity;
import ni.org.ics.a2cares.app.MainActivity;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.Casa;
import ni.org.ics.a2cares.app.domain.core.DatosCoordenadas;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.domain.puntos.PuntoCandidato;
import ni.org.ics.a2cares.app.preferences.PreferencesActivity;
import ni.org.ics.a2cares.app.ui.activities.maps.CoordenadasMapActivity;
import ni.org.ics.a2cares.app.ui.activities.maps.CoordenadasOnlyActivity;
import ni.org.ics.a2cares.app.ui.activities.maps.PolygonsHelper;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuCasaActivity;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuParticipanteActivity;
import ni.org.ics.a2cares.app.ui.fragments.enterdata.NuevaMuestraEnfermoFragment;
import ni.org.ics.a2cares.app.ui.fragments.enterdata.NuevoControlAsistenciaFragment;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.utils.DeviceInfo;
import ni.org.ics.a2cares.app.wizard.model.ModelCallbacks;
import ni.org.ics.a2cares.app.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.a2cares.app.wizard.ui.ReviewFragment;
import ni.org.ics.a2cares.app.wizard.model.*;



public class NuevoControlAsistenciaActivity extends AppCompatActivity implements OnMapReadyCallback,
		GoogleMap.OnMyLocationButtonClickListener,
		OnInfoWindowClickListener {

    public static final String TAG = "NuevaMuestraEnfermoActivity";
    
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

	private LocationRequest mLocationRequest;
	//private Location mLastLocation;
	private FusedLocationProviderClient mFusedLocationClient;
	private EditText inputLatlong;
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
       setContentView(R.layout.fragment_nuevo_control_asistencia);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapView = mapFragment.getView();
		mapFragment.getMapAsync(this);
		inputLatlong = (EditText) findViewById(R.id.latlong);
		//username = settings.getString(PreferencesActivity.KEY_USERNAME, null);
		//SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		//mapView = mapFragment.getView();
		//mapFragment.getMapAsync(this);
		//requestLocation();
		//onMapReady(mGoogleMap);
		// TODO: Set LOCATION_PROVIDER here:
		String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
		} else {
		//	locationStart();
		}


		if (savedInstanceState == null) {
			setContentView(R.layout.layout_data_enter_control_asistencia);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			NuevoControlAsistenciaFragment fragment = new NuevoControlAsistenciaFragment();
            transaction.replace(R.id.data_content_fragment, fragment);
			//transaction.replace(R.id.mapa_control_asistencia_fragment, fragment);
            transaction.commit();



        }

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
	public void onBackPressed() {
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
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
				}else {
					mGoogleMap.setMyLocationEnabled(true);
				}
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

			LatLng puntoactual = new LatLng(12.112823, -86.324148);
			Marker mLocationMarker = mGoogleMap.addMarker(new MarkerOptions()
					.position(puntoactual)
					.title("Punto " + username)
					.zIndex(1.0f)
					.icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("ic_marker_main",120,105)))
					.draggable(false)
			);
			setLocationOnInputLatLong(mLocationMarker);
			//mover la camara en el mGoogleMap para mostrar el punto
			mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(puntoactual, 15));
			if (locationList.size() > 0) {
				//The last location in the list is the newest
				Location location = locationList.get(locationList.size() - 1);
				if (mLocationMarker != null) {
					//   mLocationMarker.remove();
				}

				//Place current location marker
				LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
				MarkerOptions markerOptions = new MarkerOptions();
				//markerOptions.position(latLng);
				markerOptions.position(latLng);
				markerOptions.title("Posicion actual - " + username );
				markerOptions.draggable(false);

				markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

			//	mLocationMarker = mGoogleMap.addMarker(markerOptions);
				setLocationOnInputLatLong(mLocationMarker);
				//mover la camara en el mGoogleMap para mostrar el punto
			 	mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
				//mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(puntoactual, 15));
			} else {
				// Si no se encuentran datos de ubicacion en el movil, poner el punto del centro de salud por defecto
				if (ubicacion == Constants.UBICACION_NJ) {
					LatLng nejapa = new LatLng(12.112823, -86.324148);
					mLocationMarker = mGoogleMap.addMarker(new MarkerOptions()
							.position(nejapa)
							.title("Nejapa")
							.draggable(true)
					);
					mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nejapa, 15.0f));
				} else if (ubicacion == Constants.UBICACION_CO) {
					LatLng camilo = new LatLng(12.096661142575222, -86.30627129226923);
					mLocationMarker = mGoogleMap.addMarker(new MarkerOptions()
							.position(camilo)
							.title("Camilo")
							.draggable(true)
					);
					mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(camilo, 15.0f));
				}
				setLocationOnInputLatLong(mLocationMarker);

			}
		}
	};

	@Override
	@SuppressWarnings("MissingPermission")
	public void onMapReady(GoogleMap googleMap) {
		mGoogleMap = googleMap;
		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(100000); // cada 5 minutos actualiza la localidad actual
		mLocationRequest.setFastestInterval(300000);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
		//al cargar el mapa, carga en la posicion actual del dispositivo

			requestLocation();

		mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
		// Eventos
		//Listener al mover el punto
		//mGoogleMap.setOnMarkerDragListener((GoogleMap.OnMarkerDragListener) this);
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
			layoutParams.setMargins(0, 0, 30, 300);
		}

	}
	/**
	 * Enables the My Location layer if the fine location permission has been granted.
	 */
	private void enableMyLocation() {
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
				!= PackageManager.PERMISSION_GRANTED) {
			// Permission to access the location is missing.

		} else if (mGoogleMap != null) {
			// Access to the location has been granted to the app.
			mGoogleMap.setMyLocationEnabled(true);
		}
	}

	private void addPolygon(List<LatLng> vertices) {
		PolygonOptions polygonOptions = new PolygonOptions();
		polygonOptions.addAll(vertices);
		polygonOptions.strokeColor(Color.parseColor("#7B11A2"));
		polygonOptions.fillColor(Color.argb(32, 156, 39, 176));
		//Polygon polygon = mGoogleMap.addPolygon(polygonOptions);
		mGoogleMap.addPolygon(polygonOptions);
	}

    /*@Override
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
    }*/

	@Override
	public void onInfoWindowClick(Marker marker) {
		if (marker.equals(mLocationMarker)) {
			//createDialog(INFO);
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

	private void setLocationOnInputLatLong(Marker marker) {
		String position = String.format(Locale.getDefault(),
				getString(R.string.marker_detail_latlng),
				marker.getPosition().latitude,
				marker.getPosition().longitude);
		inputLatlong.setText(position);
	}

	private void setHouseLocation(Casa casa) {
		//Si el participante tiene coordenadas, ponerlo en el mapa
		LatLng puntoCasa = new LatLng(casa.getLatitud(), casa.getLongitud());
		mLocationMarker = mGoogleMap.addMarker(new MarkerOptions()
				.position(puntoCasa)
				.title("Casa " + casa.getCodigo())
				.draggable(true)
		);
		setLocationOnInputLatLong(mLocationMarker);
		//mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(12.154, -86.290), 15.0f));
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(puntoCasa, 15));
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
		}else {
			mGoogleMap.setMyLocationEnabled(true);
		}
	}

	private void setPointLocation(PuntoCandidato puntoCandidato) {
		//Si el participante tiene coordenadas, ponerlo en el mapa
		LatLng puntoCasa = new LatLng(puntoCandidato.getLatitud(), puntoCandidato.getLongitud());
		Marker mLocationMarker = mGoogleMap.addMarker(new MarkerOptions()
				.position(puntoCasa)
				.title("Punto " + puntoCandidato.getCodigo())
				.zIndex(1.0f)
				.icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("ic_marker_main",120,105)))
				.draggable(false)
		);

		setLocationOnInputLatLong(mLocationMarker);
		//mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(12.154, -86.290), 15.0f));
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(puntoCasa, 15));

		//new NuevoControlAsistenciaActivity().CargarCandidatosBarrioPuntoTask().execute();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
		}else {
			mGoogleMap.setMyLocationEnabled(true);
		}
	}

	private void setPointsBarrioLocation(){
		/*for(PuntoCandidato punto : mPuntos){
			mGoogleMap.addMarker(new MarkerOptions()
					//.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("ic_marker_secondary",83,72)))
					.position(new LatLng(punto.getLatitud(), punto.getLongitud()))
					.title(punto.getCodigo().toString())
					//.alpha(0.7f) opacidad
					.draggable(false)
			);
		}*/
	}

	public Bitmap resizeBitmap(String drawableName, int width, int height){
		Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(drawableName, "mipmap", getPackageName()));
		return Bitmap.createScaledBitmap(imageBitmap, width, height, false);
	}

	private void setParticipantLocation() {
		//Si el participante tiene coordenadas, ponerlo en el mapa
		LatLng cssfv = new LatLng(mParticipante.getCasa().getLatitud(), mParticipante.getCasa().getLongitud());
		mLocationMarker = mGoogleMap.addMarker(new MarkerOptions()
				.position(cssfv)
				.title("Participante " + mParticipante.getCodigo())
				.draggable(true)
		);
		setLocationOnInputLatLong(mLocationMarker);
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(12.154, -86.290), 15.0f));
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
		}else {
			mGoogleMap.setMyLocationEnabled(true);
		}
	}

	private void requestLocation(){

		setLocationOnInputLatLong(mLocationMarker);
		//mover la camara en el mGoogleMap para mostrar el punto

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			// Controles UI
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
					== PackageManager.PERMISSION_GRANTED) {
				mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
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
			nDialog = new ProgressDialog(NuevoControlAsistenciaActivity.this);
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
					mParticipante = estudiosAdapter.getParticipante(MainDBConstants.codigo + "='" + codigoParticipante+"'", null);
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

	private class CargarCandidatosBarrioPuntoTask extends AsyncTask<String, Void, String> {

		private ProgressDialog nDialog;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			nDialog = new ProgressDialog(NuevoControlAsistenciaActivity.this);
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
				mPuntos = estudiosAdapter.getPuntoCandidatos(MainDBConstants.barrio + "='"+mPunto.getBarrio()+"' and "+MainDBConstants.codigo + " <> " + mPunto.getCodigo(), null);
				return "exito";
			} catch (Exception e) {
				Log.e(this.getClass().getName(), e.getLocalizedMessage(), e);
				return "error";
			}finally {
			//	estudiosAdapter.close();
			}
		}

		protected void onPostExecute(String resultado) {
			// after the request completes, hide the progress indicator
			nDialog.dismiss();
			setPointsBarrioLocation();
		}
	}

	private class SaveCoordenadaTask extends AsyncTask<String, Void, String> {

		private ProgressDialog nDialog;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			nDialog = new ProgressDialog(NuevoControlAsistenciaActivity.this);
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
				//estudiosAdapter.close();
			}
			if (error.equals("")) {
				return getApplicationContext().getString(R.string.success);
			} else {
				return error;
			}
		}

		protected void onPostExecute(String resultado) {
			nDialog.dismiss();
			Toast.makeText(NuevoControlAsistenciaActivity.this, resultado, Toast.LENGTH_LONG).show();
			// after the request completes, hide the progress indicator
			Intent i = new Intent(getApplicationContext(),
					MenuParticipanteActivity.class);

			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
		}
	}

}