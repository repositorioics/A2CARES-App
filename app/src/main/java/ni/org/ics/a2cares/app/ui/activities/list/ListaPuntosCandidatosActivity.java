package ni.org.ics.a2cares.app.ui.activities.list;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ni.org.ics.a2cares.app.AbstractAsyncActivity;
import ni.org.ics.a2cares.app.MainActivity;
import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.puntos.PuntoCandidato;
import ni.org.ics.a2cares.app.domain.supervisor.RecepcionMuestra;
import ni.org.ics.a2cares.app.ui.adapters.PuntosCandidatosListAdapter;

public class ListaPuntosCandidatosActivity extends AbstractAsyncActivity {

	private EstudioDBAdapter estudiosAdapter;
	private RecyclerView recyclerView;
	private TextView textView;
	private Integer opcion;
	private List<PuntoCandidato> puntoCandidatoList = new ArrayList<PuntoCandidato>();
	private PuntosCandidatosListAdapter adapter;
	private EditText mSearchText;
	private ImageButton mBarcodeButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_list);

		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(),mPass,false,false);

		textView = findViewById(R.id.label_logo);
		textView.setCompoundDrawablesWithIntrinsicBounds(
				R.mipmap.ic_gps_candidates,
				0, //top
				0, //right
				0);//bottom

		textView.setText(getString(R.string.candidatePoints));

		mSearchText = findViewById(R.id.search_text);
		mSearchText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				filter(editable.toString());
			}
		});

		recyclerView = findViewById(R.id.recycler_view);
		new FetchDataCasaTask().execute();
		adapter = new PuntosCandidatosListAdapter(puntoCandidatoList);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(adapter);
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
		if (puntoCandidatoList.isEmpty()) showToast(getString(R.string.no_items));
	}

	private void filter(String text) {
		ArrayList<PuntoCandidato> filteredList = new ArrayList<>();
		for (PuntoCandidato item : puntoCandidatoList) {
			if (item.getCodigo().toString().toLowerCase().contains(text.toLowerCase())
		|| (item.getBarrio() != null && item.getBarrio().toLowerCase().contains(text.toLowerCase()))
			) {
				filteredList.add(item);
			}
		}
		adapter.filterList(filteredList);
	}

	private class FetchDataCasaTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			try {
				estudiosAdapter.open();
				puntoCandidatoList.clear();
				puntoCandidatoList.addAll(estudiosAdapter.getPuntoCandidatos(MainDBConstants.descartado + " = '0'", MainDBConstants.barrio+ ", "+MainDBConstants.codigo));
				estudiosAdapter.close();
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
			}
			return "exito";
		}

		protected void onPostExecute(String resultado) {
			dismissProgressDialog();
			refreshView();
		}
	}

}
