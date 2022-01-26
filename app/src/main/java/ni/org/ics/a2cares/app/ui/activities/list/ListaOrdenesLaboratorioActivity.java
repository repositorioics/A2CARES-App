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
import ni.org.ics.a2cares.app.domain.medico.OrdenLaboratorio;
import ni.org.ics.a2cares.app.ui.activities.enterdata.NuevaMuestraActivity;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuMedicoActivity;
import ni.org.ics.a2cares.app.ui.adapters.OrdenesLabListAdapter;
import ni.org.ics.a2cares.app.utils.DateUtil;

public class ListaOrdenesLaboratorioActivity extends AbstractAsyncActivity {

	private EstudioDBAdapter estudiosAdapter;
	private RecyclerView recyclerView;
	private TextView textView;
	private Button mAddSample;
	private Integer opcion;
	private List<OrdenLaboratorio> mOrdenes = new ArrayList<OrdenLaboratorio>();
	private OrdenesLabListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ordenes_lab_list);


		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(),mPass,false,false);

		textView = findViewById(R.id.label_logo);
		//textView.setText(getString(R.string.lab_orders));

		recyclerView = findViewById(R.id.recycler_view);
		new FetchDataTask().execute();
		adapter = new OrdenesLabListAdapter(mOrdenes);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(adapter);

		mAddSample = (Button) findViewById(R.id.add_order);
		mAddSample.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle arguments = new Bundle();
				Intent i;

				i = new Intent(getApplicationContext(),
						NuevaMuestraActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtras(arguments);
				startActivity(i);
			}
		});
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
				MenuMedicoActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.putExtras(arguments);
		startActivity(i);

		finish();
	}

	private void refreshView() {
		adapter.notifyDataSetChanged();
		if (mOrdenes.isEmpty()) showToast(getString(R.string.no_items));
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
				mOrdenes = estudiosAdapter.getOrdenesLaboratorio(MainDBConstants.fechaOrden +" = " + DateUtil.getTodayWithZeroTime().getTime(), MainDBConstants.participante);
				//List<MessageResource> catSinMuestra = estudiosAdapter.getMessageResources(MainDBConstants.catRoot + "='CAT_RAZON_NO_MX'", null);
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
