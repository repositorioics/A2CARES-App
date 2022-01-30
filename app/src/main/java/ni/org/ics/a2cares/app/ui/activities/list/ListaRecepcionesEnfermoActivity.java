package ni.org.ics.a2cares.app.ui.activities.list;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ni.org.ics.a2cares.app.AbstractAsyncActivity;
import ni.org.ics.a2cares.app.MainActivity;
import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.laboratorio.RecepcionEnfermo;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.ui.activities.BuscarParticipanteActivity;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuLaboratorioActivity;
import ni.org.ics.a2cares.app.ui.adapters.RecepcionesEnfermoListAdapter;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.utils.DateUtil;

public class ListaRecepcionesEnfermoActivity extends AbstractAsyncActivity {

	private EstudioDBAdapter estudiosAdapter;
	private RecyclerView recyclerView;
	private TextView textView;
	private Button mAddReception;
	private Integer opcion;
	private List<RecepcionEnfermo> mRecepciones = new ArrayList<RecepcionEnfermo>();
	private RecepcionesEnfermoListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ordenes_lab_list);


		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(),mPass,false,false);

		mAddReception = (Button) findViewById(R.id.add_order);
		textView = findViewById(R.id.label_logo);
		recyclerView = findViewById(R.id.recycler_view);

		textView.setText(getString(R.string.sick_receptions));
		textView.setCompoundDrawablesWithIntrinsicBounds(
				R.mipmap.ic_sick,
				0, //top
				0, //right
				0);//bottom

		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		mAddReception.setText(getString(R.string.agregarRecepcion));

		mAddReception.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle arguments = new Bundle();
				Intent i;

				i = new Intent(getApplicationContext(),
						BuscarParticipanteActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra(Constants.DESDE_LABO, true);
				i.putExtras(arguments);
				startActivity(i);
				finish();
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
	public void onBackPressed () {
		Bundle arguments = new Bundle();
		Intent i;
		i = new Intent(getApplicationContext(),
				MenuLaboratorioActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.putExtras(arguments);
		startActivity(i);

		finish();
	}

	private void refreshView() {
		adapter.notifyDataSetChanged();
		if (mRecepciones.isEmpty()) showToast(getString(R.string.no_items));
	}

	private class FetchDataTask extends AsyncTask<String, Void, String> {
		private List<MessageResource> mCatTipoMuestra;
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			try {
				estudiosAdapter.open();
				mRecepciones = estudiosAdapter.getRecepcionesEnfermo(MainDBConstants.recordDate +" >= " + DateUtil.getTodayWithZeroTime().getTime(), MainDBConstants.participante);
				mCatTipoMuestra = estudiosAdapter.getMessageResources(MainDBConstants.catRoot + "='CAT_FASE_MX'", MainDBConstants.order);
				estudiosAdapter.close();
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
			}
			return "exito";
		}

		protected void onPostExecute(String resultado) {
			dismissProgressDialog();
			adapter = new RecepcionesEnfermoListAdapter(mRecepciones, mCatTipoMuestra);
			recyclerView.setAdapter(adapter);
			refreshView();
		}
	}

}
