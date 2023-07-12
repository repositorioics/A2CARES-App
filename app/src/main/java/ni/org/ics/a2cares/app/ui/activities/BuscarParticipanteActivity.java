package ni.org.ics.a2cares.app.ui.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bea.xml.stream.samples.Parse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import ni.org.ics.a2cares.app.AbstractAsyncActivity;
import ni.org.ics.a2cares.app.MainActivity;
import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.domain.laboratorio.RecepcionEnfermo;
import ni.org.ics.a2cares.app.domain.laboratorio.RecepcionEnfermomessage;
import ni.org.ics.a2cares.app.domain.core.MuestraEnfermo;
import ni.org.ics.a2cares.app.domain.laboratorio.RecepcionEnfermo;
import ni.org.ics.a2cares.app.domain.users.UserPermissions;
import ni.org.ics.a2cares.app.preferences.PreferencesActivity;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuParticipanteActivity;
import ni.org.ics.a2cares.app.ui.adapters.ParticipanteListAdapter;
import ni.org.ics.a2cares.app.utils.Constants;

public class BuscarParticipanteActivity extends AbstractAsyncActivity {

	private EstudioDBAdapter estudiosAdapter;
	private RecyclerView recyclerView;
	private TextView textView;
	private Spinner mMetodoView;
	private EditText mCodigoView;
	private ImageButton mFindButton;
	private ImageButton mBarcodeButton;
	private Integer opcion;
	public static final int BARCODE_CAPTURE = 2;
	private List<Participante> mParticipantes = new ArrayList<Participante>();
	private List<RecepcionEnfermo> mRecepcionEnfermo = new ArrayList<RecepcionEnfermo>();
	private List<RecepcionEnfermomessage > mRecepcionEnfermomesage = new ArrayList<RecepcionEnfermomessage>();
	private static RecepcionEnfermo recepcionenfermo = new RecepcionEnfermo();
	private static RecepcionEnfermomessage recepcionenfermom = new RecepcionEnfermomessage();
	private ParticipanteListAdapter adapter;
	private static UserPermissions mUser = new UserPermissions();
	private SharedPreferences settings;
	private String username;
	private boolean desdeMedico = false;
	private boolean desdeLaboratorio = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_participants_list);
		settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(),mPass,false,false);

		desdeMedico = getIntent().getBooleanExtra(Constants.DESDE_MEDICO, false);
		desdeLaboratorio = getIntent().getBooleanExtra(Constants.DESDE_LABO, false);

		textView = findViewById(R.id.label_logo);
		if (desdeMedico) {
			textView.setText(getString(R.string.selec_part_order));
		} else
		if (desdeLaboratorio) {
			textView.setText(getString(R.string.selec_part_recep));
		}
		mMetodoView = (Spinner) findViewById(R.id.metodo_busqueda);
		mBarcodeButton = (ImageButton) findViewById(R.id.barcode_button);
		mFindButton = (ImageButton) findViewById(R.id.find_button);
		mCodigoView = (EditText) findViewById(R.id.codigo);
		recyclerView = findViewById(R.id.recycler_view);

		List<String> list = new ArrayList<String>();
		list.add(getString(R.string.desc_barcode));
		list.add(getString(R.string.enter_code));
		list.add(getString(R.string.enter_name));
		list.add(getString(R.string.enter_lastname));

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		mMetodoView.setAdapter(dataAdapter);

		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		/*adapter = new ParticipanteListAdapter(mParticipantes);
		recyclerView.setAdapter(adapter);*/

		mMetodoView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				if (position==0){
					mCodigoView.setVisibility(View.GONE);
					mFindButton.setVisibility(View.GONE);
					mBarcodeButton.setVisibility(View.VISIBLE);
				}
				else{
					mCodigoView.setVisibility(View.VISIBLE);
					mFindButton.setVisibility(View.VISIBLE);
					mBarcodeButton.setVisibility(View.GONE);
					mCodigoView.requestFocus();
					opcion = position;
					switch(position){
						case 1:
							mCodigoView.setHint(getString(R.string.enter_code));
							mCodigoView.setInputType(InputType.TYPE_CLASS_NUMBER);
							break;
						case 2:
							mCodigoView.setHint(getString(R.string.enter_name));
							mCodigoView.setInputType(InputType.TYPE_CLASS_TEXT);
							break;
						case 3:
							mCodigoView.setHint(getString(R.string.enter_lastname));
							mCodigoView.setInputType(InputType.TYPE_CLASS_TEXT);
							break;
						default:
							mCodigoView.setHint(getString(R.string.enter_code));
							break;

					}
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parentView) {

			}
		});

		mBarcodeButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				Intent i = new Intent("com.google.zxing.client.android.SCAN");
				try {
					startActivityForResult(i, BARCODE_CAPTURE);
				} catch (ActivityNotFoundException e) {
					Toast t = Toast.makeText(getApplicationContext(),
							getString(R.string.error, getString(R.string.barcode_error)),
							Toast.LENGTH_LONG);
					t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
					t.show();
				}
			}
		});

		mFindButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				mParticipantes.clear();
				mRecepcionEnfermo.clear();
				mRecepcionEnfermomesage.clear();
				if ((mCodigoView.getText().toString()==null) || (mCodigoView.getText().toString().matches(""))){
					mCodigoView.requestFocus();
					mCodigoView.setError(getString(R.string.search_hint));
					return;
				}
				buscarParticipante(mCodigoView.getText().toString(),opcion);
			}
		});

		new FetchDataTask().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.general, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
		case R.id.MENU_BACK:
			finish();
			return true;
			case R.id.MENU_HOME:
				Intent i = new Intent(getApplicationContext(),
						MainActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == BARCODE_CAPTURE && intent != null) {
			String codigoScanned = intent.getStringExtra("SCAN_RESULT");
			if (codigoScanned != null && codigoScanned.length() > 0) {

				try{
					//CohorteAdapterGetObjects ca = new CohorteAdapterGetObjects();
					//ca.open();
					estudiosAdapter.open();
					Participante mParticipante = estudiosAdapter.getParticipante(MainDBConstants.codigo+ " = '"+codigoScanned+"'", null);

					if (mParticipante!= null && mParticipante.getCodigo() != null){
						//ParticipanteProcesos mParticipanteProc = mParticipante.getProcesos(); //ca.getParticipanteProceso(codigoScanned);
						//if (mParticipanteProc != null && (mParticipanteProc.getEstudio() != null && !mParticipanteProc.getEstudio().isEmpty())
						//        || (mParticipanteProc != null && mParticipanteProc.getReConsDeng()!=null && mParticipanteProc.getReConsDeng().matches("Si"))) {
						Bundle arguments = new Bundle();
						arguments.putSerializable(Constants.PARTICIPANTE , mParticipante);
						Intent i = new Intent(getApplicationContext(),
								MenuParticipanteActivity.class);
						//i.putExtra(Constants.CODIGO, codigo);
						i.putExtra(Constants.VISITA_EXITOSA, !mUser.getVisitas());//si no tiene permiso de visita, se va a asumir la visita como exitosa para no solicitar visita al momento de ingresar informacion
						i.putExtras(arguments);
						startActivity(i);
						finish();
						//}else{
						//showToast("("+codigoScanned+") - " + getString(R.string.retired_error));
						//}

					}
					else {
						//mSearchText.setText(sb);
						showToast("("+codigoScanned+") - " + getString(R.string.code_notfound));
					}
				}
				catch(Exception e){
					showToast(getString(R.string.scan_error)+ "("+codigoScanned+") - " +e.getLocalizedMessage());
					return;
				}finally {
					//ca.close();
					estudiosAdapter.close();
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, intent);
	}

	public void buscarParticipante(String parametro, int opcion){
		String filtro = "";
		String filtroR = "";
		switch (opcion) {
			case 0:
				filtro = MainDBConstants.codigo + "='" + parametro +"'";
				filtroR = MainDBConstants.participante + "='" + parametro +"'";
				break;
			case 1:
				filtro = MainDBConstants.codigo + "='" + parametro +"'";
				filtroR = MainDBConstants.participante + "='" + parametro +"'";
				break;
			case 2:
				filtro = MainDBConstants.nombre1 + " like '%" + parametro + "%' or " + MainDBConstants.nombre2 + " like '%" + parametro + "%'";
				break;
			case 3:
				filtro = MainDBConstants.apellido1 + " like '%" + parametro + "%' or " + MainDBConstants.apellido1 + " like '%" + parametro + "%'";
				break;
		}
		new FetchParticipantsTask().execute(filtro);
	}

	private void refreshView() {
		adapter.notifyDataSetChanged();
		if (mParticipantes.isEmpty()) showToast(getString(R.string.no_items));
	}

	// ***************************************
	// Private classes
	// ***************************************
	private class FetchParticipantsTask extends AsyncTask<String, Void, String> {
		private String filtro = null;
		private String filtroR = null;
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			filtro = values[0];
			filtroR = values[0];
			try {
				estudiosAdapter.open();
				mParticipantes.clear();
				mParticipantes.addAll(estudiosAdapter.getParticipantes(filtro, MainDBConstants.codigo));
				filtroR = MainDBConstants.participante + "='" + mParticipantes.get(0).getCodigo() +"'";
				//recepcionenfermo = estudiosAdapter.getRecepcionEnfermo(MainDBConstants.participante  + "='" + mParticipantes.get(0).getCodigo()+"'", null);
/**/
				recepcionenfermom = estudiosAdapter.getRecepcionEnfermo1(MainDBConstants.participante  + "='" + mParticipantes.get(0).getCodigo()+"'", null);
				Date fechaactual = new Date(System.currentTimeMillis());
				int milisecondsByDay = 86400000;
				if (recepcionenfermom.getFechaRecepcion() != null) {
					int dias = (int) ((fechaactual.getTime() - recepcionenfermom.getFechaRecepcion().getTime()) / milisecondsByDay);

					recepcionenfermom.setPositivo(String.valueOf(dias));
					//  recepcionenfermom.setFis();
					// recepcionenfermo.setObservacion(recepcionenfermo.getFis().toString());
					// mRecepcionEnfermo.add(recepcionenfermo);
					mRecepcionEnfermomesage.add(recepcionenfermom);
				}
			//	mRecepcionEnfermo.addAll((Collection<? extends RecepcionEnfermo>) estudiosAdapter.getRecepcionEnfermo(filtroR, MainDBConstants.participante));
				estudiosAdapter.close();
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
			}
			return "exito";
		}

		protected void onPostExecute(String resultado) {
			// after the network request completes, hide the progress indicator
			dismissProgressDialog();
			refreshView();
		}

	}

	private class FetchDataTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			try {
				estudiosAdapter.open();
				mUser = estudiosAdapter.getPermisosUsuario(MainDBConstants.USERNAME + "='" +username+"'", null);
				estudiosAdapter.close();
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
			}
			return "exito";
		}

		protected void onPostExecute(String resultado) {
			adapter = new ParticipanteListAdapter(mParticipantes,mRecepcionEnfermomesage, mUser.getVisitas(), desdeMedico, desdeLaboratorio);
			recyclerView.setAdapter(adapter);
			dismissProgressDialog();
		}
	}

}
