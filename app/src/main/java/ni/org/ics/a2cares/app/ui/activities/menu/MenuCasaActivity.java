package ni.org.ics.a2cares.app.ui.activities.menu;

import java.util.ArrayList;
import java.util.List;

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

import ni.org.ics.a2cares.app.AbstractAsyncActivity;
import ni.org.ics.a2cares.app.MainActivity;
import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.bluetooth.activity.ChatActivity;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.Casa;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.domain.core.TelefonoContacto;
import ni.org.ics.a2cares.app.ui.activities.list.ListaParticipantesCasaActivity;
import ni.org.ics.a2cares.app.ui.activities.list.ListaTelefonosActivity;
import ni.org.ics.a2cares.app.ui.activities.maps.CoordenadasMapActivity;
import ni.org.ics.a2cares.app.ui.adapters.MenuCasaAdapter;
import ni.org.ics.a2cares.app.utils.Constants;

public class MenuCasaActivity extends AbstractAsyncActivity {

	private GridView gridView;
	private TextView textView;
	private String[] menu_casa;
	private static Casa casa = new Casa();
	private List<Participante> mParticipantes = new ArrayList<Participante>();
	//private List<Cuarto> mCuartos = new ArrayList<Cuarto>();
	//private List<AreaAmbiente> mAreas = new ArrayList<AreaAmbiente>();
	private List<TelefonoContacto> mTelefonosContacto = new ArrayList<TelefonoContacto>();
    private AlertDialog alertDialog;

    private final int OPCION_LISTA_PARTICIPANTES = 0;
    private final int OPCION_LISTA_TELEFONOS = 1;
    private final int OPCION_ENVIAR_CASA = 2;
	private final int OPCION_UBICAR_CASA = 3;
    private boolean desdeParticipante = false;

	private EstudioDBAdapter estudiosAdapter;

	@TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_participante);

		textView = (TextView) findViewById(R.id.label);
		gridView = (GridView) findViewById(R.id.gridView1);
		menu_casa = getResources().getStringArray(R.array.menu_casa);
		casa = (Casa) getIntent().getExtras().getSerializable(Constants.CASA);
		desdeParticipante = getIntent().getBooleanExtra(Constants.DESDE_PARTICIPANTE, false);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(),mPass,false,false);
		new FetchDataCasaTask().execute(casa.getCodigo().toString());
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Bundle arguments = new Bundle();
				Intent i;
				switch (position){
					case OPCION_LISTA_PARTICIPANTES:
						if (casa!=null) arguments.putSerializable(Constants.CASA , casa);
						i = new Intent(getApplicationContext(),
								ListaParticipantesCasaActivity.class);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						i.putExtras(arguments);
						startActivity(i);
			        	break;
                    case OPCION_LISTA_TELEFONOS:
                    	if (casa!=null) arguments.putSerializable(Constants.CASA , casa);
    					i = new Intent(getApplicationContext(),
    							ListaTelefonosActivity.class);
    					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    					i.putExtras(arguments);
    					startActivity(i);
    					break;
                    case OPCION_ENVIAR_CASA:
            			i = new Intent(getApplicationContext(),
            					ChatActivity.class);
            			if (casa!=null) arguments.putSerializable(Constants.CASA , casa);
            			i.putExtra(Constants.ACCION, Constants.SENDING);
            			i.putExtras(arguments);
            			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            			startActivity(i);
            			break;
            		case OPCION_UBICAR_CASA:
						i = new Intent(getApplicationContext(),
								CoordenadasMapActivity.class);
						if (casa!=null) arguments.putSerializable(Constants.CASA , casa);
						i.putExtras(arguments);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
		private String codigoCasaCHF = null;
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			codigoCasaCHF = values[0];
			try {
				estudiosAdapter.open();
				mParticipantes = estudiosAdapter.getParticipantes(MainDBConstants.casa +" = " + codigoCasaCHF, MainDBConstants.codigo);
				mTelefonosContacto = estudiosAdapter.getTelefonosContacto(MainDBConstants.casa +" = " + casa.getCodigo() + " and " + MainDBConstants.pasive + " ='0'", MainDBConstants.numero);
				estudiosAdapter.close();
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
			}
			return "exito";
		}

		protected void onPostExecute(String resultado) {
			// after the request completes, hide the progress indicator
			textView.setText("");
			textView.setTextColor(Color.BLACK);
			textView.setText(getString(R.string.menu_house) +"\n"+ getString(R.string.code)+ " "+ getString(R.string.casa)+ ": "+ casa.getCodigo());
			gridView.setAdapter(new MenuCasaAdapter(getApplicationContext(), R.layout.menu_item_2, menu_casa, mParticipantes.size(), mTelefonosContacto.size()));
			dismissProgressDialog();
		}

	}	
		
}
	
