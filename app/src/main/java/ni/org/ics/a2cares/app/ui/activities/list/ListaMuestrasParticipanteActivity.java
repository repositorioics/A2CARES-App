package ni.org.ics.a2cares.app.ui.activities.list;

import android.content.Intent;
import android.graphics.Color;
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
import ni.org.ics.a2cares.app.domain.core.Muestra;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.dto.MuestraDTO;
import ni.org.ics.a2cares.app.ui.activities.enterdata.NuevaMuestraActivity;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuParticipanteActivity;
import ni.org.ics.a2cares.app.ui.adapters.MuestraListAdapter;
import ni.org.ics.a2cares.app.utils.Constants;

public class ListaMuestrasParticipanteActivity extends AbstractAsyncActivity {

	private EstudioDBAdapter estudiosAdapter;
	private RecyclerView recyclerView;
	private TextView textView;
	private Button mAddSample;
	private Integer opcion;
	private List<Muestra> mMuestras = new ArrayList<Muestra>();
	private List<MuestraDTO> mMuestrasDTO = new ArrayList<MuestraDTO>();
	private MuestraListAdapter adapter;
	private static Participante participante = new Participante();
	private boolean visitaExitosa = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.samples_list);

		participante = (Participante) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
		visitaExitosa = getIntent().getBooleanExtra(Constants.VISITA_EXITOSA, false);

		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(),mPass,false,false);

		textView = findViewById(R.id.label_logo);
		textView.setText(getString(R.string.muestras)+"\n"+ getString(R.string.code)+ " "+ getString(R.string.participant)+ ": "+ participante.getCodigo());

		recyclerView = findViewById(R.id.recycler_view);
		new FetchDataCasaTask().execute(participante.getCodigo().toString());
		adapter = new MuestraListAdapter(mMuestrasDTO);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(adapter);

		mAddSample = (Button) findViewById(R.id.add_sample);
		mAddSample.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle arguments = new Bundle();
				Intent i;

				if (participante!=null) arguments.putSerializable(Constants.PARTICIPANTE , participante);
				i = new Intent(getApplicationContext(),
						NuevaMuestraActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtras(arguments);
				i.putExtra(Constants.VISITA_EXITOSA, visitaExitosa);
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
		arguments.putSerializable(Constants.PARTICIPANTE, participante);
		i = new Intent(getApplicationContext(),
				MenuParticipanteActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.putExtra(Constants.VISITA_EXITOSA, visitaExitosa);
		i.putExtras(arguments);
		startActivity(i);

		finish();
	}

	private void refreshView() {
		adapter.notifyDataSetChanged();
		if (mMuestrasDTO.isEmpty()) showToast(getString(R.string.no_items));
	}

	private class FetchDataCasaTask extends AsyncTask<String, Void, String> {
		private String codigoParticipante = null;
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			codigoParticipante = values[0];
			try {
				estudiosAdapter.open();
				mMuestrasDTO.clear();
				mMuestras = estudiosAdapter.getMuestras(MainDBConstants.participante +" = '" + codigoParticipante + "'", MainDBConstants.fechaMuestra);
				List<MessageResource> catSinMuestra = estudiosAdapter.getMessageResources(MainDBConstants.catRoot + "='CAT_RAZON_NO_MX'", null);
				estudiosAdapter.close();

				for(Muestra muestra : mMuestras) {
					MuestraDTO muestraDTO = new MuestraDTO();
					muestraDTO.setIdMuestra(muestra.getIdMuestra());
					muestraDTO.setTipoTubo("Rojo");
					muestraDTO.setColor(Color.RED);
					muestraDTO.setTomoMuestra(muestra.getTuboRojo());
					muestraDTO.setCodigoMx(muestra.getCodigoRojo());
					muestraDTO.setVolumen(muestra.getVolumenRojo());
					muestraDTO.setFechaMuestra(muestra.getFechaMuestra());
					if (muestra.getTuboRojo().equalsIgnoreCase(Constants.NOKEYSND)){
						for (MessageResource messageResource : catSinMuestra) {
							if (messageResource.getCatKey().equalsIgnoreCase(muestra.getRazonNoRojo())){
								muestraDTO.setRazonNoToma(getString(R.string.noSample) + " : " + messageResource.getSpanish());
								break;
							}
						}
					} else {
						muestraDTO.setRazonNoToma(muestra.getRazonNoRojo());
					}

					muestraDTO.setOtraRazonNoToma(muestra.getOtraRazonNoRojo());
					muestraDTO.setParticipante(muestra.getParticipante());
					muestraDTO.setProposito(muestra.getProposito());
					mMuestrasDTO.add(muestraDTO);
					/*
					muestraDTO = new MuestraDTO();
					muestraDTO.setIdMuestra(muestra.getIdMuestra());
					muestraDTO.setTipoTubo("BHC");
					muestraDTO.setColor(Color.MAGENTA);
					muestraDTO.setTomoMuestra(muestra.getTuboBHC());
					muestraDTO.setCodigoMx(muestra.getCodigoBHC());
					muestraDTO.setVolumen(muestra.getVolumenBHC());
					muestraDTO.setFechaMuestra(muestra.getFechaMuestra());
					if (muestra.getTuboBHC().equalsIgnoreCase(Constants.NOKEYSND)){
						for (MessageResource messageResource : catSinMuestra) {
							if (messageResource.getCatKey().equalsIgnoreCase(muestra.getRazonNoBHC())){
								muestraDTO.setRazonNoToma(getString(R.string.noSample) + " : " + messageResource.getSpanish());
								break;
							}
						}
					} else {
						muestraDTO.setRazonNoToma(muestra.getRazonNoBHC());
					}
					muestraDTO.setOtraRazonNoToma(muestra.getOtraRazonNoBHC());
					muestraDTO.setParticipante(muestra.getParticipante());
					muestraDTO.setProposito(muestra.getProposito());
					mMuestrasDTO.add(muestraDTO);
					 */
				}

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
