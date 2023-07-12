package ni.org.ics.a2cares.app.ui.activities.list;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ni.org.ics.a2cares.app.AbstractAsyncActivity;
import ni.org.ics.a2cares.app.MainActivity;
import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.Casa;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.domain.core.MuestraEnfermo;
import ni.org.ics.a2cares.app.domain.laboratorio.RecepcionEnfermo;
import ni.org.ics.a2cares.app.domain.laboratorio.RecepcionEnfermomessage;
import ni.org.ics.a2cares.app.domain.users.UserPermissions;
import ni.org.ics.a2cares.app.preferences.PreferencesActivity;
import ni.org.ics.a2cares.app.ui.adapters.MenuCasaAdapter;
import ni.org.ics.a2cares.app.ui.adapters.ParticipanteListAdapter;
import ni.org.ics.a2cares.app.utils.Constants;

public class ListaParticipantesCasaActivity extends AbstractAsyncActivity {

	private EstudioDBAdapter estudiosAdapter;
	private RecyclerView recyclerView;
	private TextView textView;
	private Integer opcion;
	private List<Participante> mParticipantes = new ArrayList<Participante>();
	private List<RecepcionEnfermo> mRecepcionEnfermo = new ArrayList<RecepcionEnfermo>();
	private List<RecepcionEnfermomessage> mRecepcionEnfermom = new ArrayList<RecepcionEnfermomessage>();
	private ParticipanteListAdapter adapter;
	private static Casa casa = new Casa();
	private static UserPermissions mUser = new UserPermissions();
	private SharedPreferences settings;
	private String username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.participants_list);

		settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();

		casa = (Casa) getIntent().getExtras().getSerializable(Constants.CASA);

		estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(),mPass,false,false);

		textView = findViewById(R.id.label_logo);
		textView.setText(getString(R.string.participants)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+casa.getCodigo());
		recyclerView = findViewById(R.id.recycler_view);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		new FetchDataCasaTask().execute(casa.getCodigo().toString());
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

	private void refreshView() {
		adapter.notifyDataSetChanged();
		if (mParticipantes.isEmpty()) showToast(getString(R.string.no_items));
	}

	private class FetchDataCasaTask extends AsyncTask<String, Void, String> {
		private String codigoCasa = null;
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			codigoCasa = values[0];
			try {
				estudiosAdapter.open();
				mParticipantes.clear();
				mRecepcionEnfermo.clear();
				mParticipantes.addAll(estudiosAdapter.getParticipantes(MainDBConstants.casa +" = " + codigoCasa, MainDBConstants.codigo));
				mUser = estudiosAdapter.getPermisosUsuario(MainDBConstants.USERNAME + "='" +username+"'", null);
				estudiosAdapter.close();
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
			}
			return "exito";
		}

		protected void onPostExecute(String resultado) {
			dismissProgressDialog();
			adapter = new ParticipanteListAdapter(mParticipantes,mRecepcionEnfermom, mUser.getVisitas(), false, false);
			recyclerView.setAdapter(adapter);
			refreshView();
		}
	}



}
