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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ni.org.ics.a2cares.app.AbstractAsyncActivity;
import ni.org.ics.a2cares.app.MainActivity;
import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.supervisor.RecepcionMuestra;
import ni.org.ics.a2cares.app.ui.adapters.RecepcionListAdapter;
import ni.org.ics.a2cares.app.utils.Constants;

public class ListaRecepcionesActivity extends AbstractAsyncActivity {

	private EstudioDBAdapter estudiosAdapter;
	private RecyclerView recyclerView;
	//private TextView textView;
	private Integer opcion;
	private List<RecepcionMuestra> mRecepcionMuestras = new ArrayList<RecepcionMuestra>();
	private RecepcionListAdapter adapter;
	private String tipoTubo;
	private EditText mSearchText;
	private ImageButton mBarcodeButton;

	public static final int BARCODE_CAPTURE = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receptions_list);

		tipoTubo = getIntent().getStringExtra(Constants.TIPO_TUBO);

		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(),mPass,false,false);

		//textView = findViewById(R.id.label_logo);
		//textView.setText(getString(R.string.recepcionesHoy));

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

		mBarcodeButton = (ImageButton) findViewById(R.id.barcode_button);
		mBarcodeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent("com.google.zxing.client.android.SCAN");
				try {
					startActivityForResult(i, BARCODE_CAPTURE);
				} catch (ActivityNotFoundException e) {
					showToast(getString(R.string.error, getString(R.string.barcode_error)));
				}
			}
		});

		recyclerView = findViewById(R.id.recycler_view);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		//recyclerView.setLayoutManager(new LinearLayoutManager(this));
		//recyclerView.setAdapter(adapter);
		new FetchDataCasaTask().execute(tipoTubo);
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
	protected void onActivityResult(int requestCode, int resultCode,
									Intent intent) {

		if (requestCode == BARCODE_CAPTURE && intent != null) {
			String sb = intent.getStringExtra("SCAN_RESULT");
			if (sb != null && sb.length() > 0) {
				mSearchText.setText(sb);
			}
		}
		super.onActivityResult(requestCode, resultCode, intent);
	}

	private void filter(String text) {
		ArrayList<RecepcionMuestra> filteredList = new ArrayList<>();
		for (RecepcionMuestra item : mRecepcionMuestras) {
			if (item.getCodigoMx().toLowerCase().contains(text.toLowerCase())) {
				filteredList.add(item);
			}
		}
		adapter.filterList(filteredList);
	}

	private void refreshView() {
		adapter.notifyDataSetChanged();
		if (mRecepcionMuestras.isEmpty()) showToast(getString(R.string.no_items));
	}

	private class FetchDataCasaTask extends AsyncTask<String, Void, String> {
		private String tipoTubo = null;
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			tipoTubo = values[0];
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date today = new Date();
				Date todayWithZeroTime = null;
				try {
					todayWithZeroTime =formatter.parse(formatter.format(today));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				estudiosAdapter.open();
				mRecepcionMuestras.clear();
				mRecepcionMuestras = estudiosAdapter.getRecepcionMuestras(MainDBConstants.fechaRecepcion +" = " + todayWithZeroTime.getTime() + " and "+ MainDBConstants.tipoTubo + " = '"+ tipoTubo + "'",
						MainDBConstants.codigoMx);
				estudiosAdapter.close();
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
			}
			return "exito";
		}

		protected void onPostExecute(String resultado) {
			adapter = new RecepcionListAdapter(mRecepcionMuestras);
			recyclerView.setAdapter(adapter);
			dismissProgressDialog();
			refreshView();
		}
	}

}
