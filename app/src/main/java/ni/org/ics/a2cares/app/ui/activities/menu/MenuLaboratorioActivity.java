package ni.org.ics.a2cares.app.ui.activities.menu;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import ni.org.ics.a2cares.app.AbstractAsyncActivity;
import ni.org.ics.a2cares.app.MainActivity;
import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.ui.activities.enterdata.RecepcionBhcActivity;
import ni.org.ics.a2cares.app.ui.activities.enterdata.RecepcionRojoActivity;
import ni.org.ics.a2cares.app.ui.activities.enterdata.RecepcionRojoLaboratorioActivity;
import ni.org.ics.a2cares.app.ui.activities.list.ListaRecepcionesEnfermoActivity;
import ni.org.ics.a2cares.app.ui.adapters.MenuLaboratorioAdapter;
import ni.org.ics.a2cares.app.ui.adapters.MenuSupervisorAdapter;

public class MenuLaboratorioActivity extends AbstractAsyncActivity {

	private GridView gridView;
	private TextView textView;
	private String[] menu_casa;
    private AlertDialog alertDialog;

    private final int OPCION_RECEPCION_BHC = 2;
    private final int OPCION_RECEPCION_ROJO = 0;
	private final int OPCION_RECEPCION_ENFERMO = 1;

	@TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_participante);

		textView = (TextView) findViewById(R.id.label);
		gridView = (GridView) findViewById(R.id.gridView1);
		menu_casa = getResources().getStringArray(R.array.menu_laboratorio);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		new FetchDataTask().execute();

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Bundle arguments = new Bundle();
				Intent i;
				switch (position){
					case OPCION_RECEPCION_BHC:
						i = new Intent(getApplicationContext(),
								RecepcionBhcActivity.class);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						i.putExtras(arguments);
						startActivity(i);
			        	break;
                    case OPCION_RECEPCION_ROJO:
                    	i = new Intent(getApplicationContext(),
								RecepcionRojoLaboratorioActivity.class);
    					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    					i.putExtras(arguments);
    					startActivity(i);
    					break;
					case OPCION_RECEPCION_ENFERMO:
						i = new Intent(getApplicationContext(),
								ListaRecepcionesEnfermoActivity.class);
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

	private class FetchDataTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			return "exito";
		}

		protected void onPostExecute(String resultado) {
			// after the request completes, hide the progress indicator
			textView.setText("");
			textView.setTextColor(Color.BLACK);
			textView.setText(getString(R.string.main_8));
			gridView.setAdapter(new MenuLaboratorioAdapter(getApplicationContext(), R.layout.menu_item_2, menu_casa));
			dismissProgressDialog();
		}

	}
}
	
