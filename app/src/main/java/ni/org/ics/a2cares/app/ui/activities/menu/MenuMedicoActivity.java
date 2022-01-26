package ni.org.ics.a2cares.app.ui.activities.menu;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
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
import ni.org.ics.a2cares.app.ui.activities.list.ListaOrdenesLaboratorioActivity;
import ni.org.ics.a2cares.app.ui.activities.list.ListaParticipantesCasaActivity;
import ni.org.ics.a2cares.app.ui.activities.list.ListaTelefonosActivity;
import ni.org.ics.a2cares.app.ui.adapters.MenuMedicoAdapter;
import ni.org.ics.a2cares.app.utils.DateUtil;

public class MenuMedicoActivity extends AbstractAsyncActivity {

	private GridView gridView;
	private TextView textView;
	private String[] menu_doctor;

    private final int OPCION_LISTA_ORDENES_LAB = 0;
    private final int OPCION_LISTA_TELEFONOS = 1;

	private EstudioDBAdapter estudiosAdapter;

	@TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_medico);

		textView = (TextView) findViewById(R.id.label);
		gridView = (GridView) findViewById(R.id.gridView1);
		menu_doctor = getResources().getStringArray(R.array.menu_doctor);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(),mPass,false,false);
		new FetchDataCasaTask().execute();
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Bundle arguments = new Bundle();
				Intent i;
				switch (position){
					case OPCION_LISTA_ORDENES_LAB:
						i = new Intent(getApplicationContext(),
								ListaOrdenesLaboratorioActivity.class);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						i.putExtras(arguments);
						startActivity(i);
			        	break;
                    case OPCION_LISTA_TELEFONOS:
    					i = new Intent(getApplicationContext(),
    							ListaTelefonosActivity.class);
    					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    					i.putExtras(arguments);
    					startActivity(i);
    					break;
				    default:
                        break;
		        }
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
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@SuppressLint("NonConstantResourceId")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.MENU_BACK:
			finish();
			return true;
		case R.id.MENU_HOME:
			i = new Intent(getApplicationContext(),
					MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

    private class FetchDataCasaTask extends AsyncTask<String, Void, String> {
		List<OrdenLaboratorio> mOrdenes = new ArrayList<OrdenLaboratorio>();
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			try {
				estudiosAdapter.open();
				mOrdenes = estudiosAdapter.getOrdenesLaboratorio(MainDBConstants.fechaOrden + "="+ DateUtil.getTodayWithZeroTime().getTime(),null);
				estudiosAdapter.close();
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
			}
			return "exito";
		}

		protected void onPostExecute(String resultado) {
			// after the request completes, hide the progress indicator
			//textView.setText("");
			//textView.setTextColor(Color.BLACK);
			//textView.setText(getString(R.string.menu_doctor));
			gridView.setAdapter(new MenuMedicoAdapter(getApplicationContext(), R.layout.menu_item_2, menu_doctor, mOrdenes.size()));
			dismissProgressDialog();
		}

	}	
		
}
	
