/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ni.org.ics.a2cares.app.ui.activities.enterdata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

import ni.org.ics.a2cares.app.AbstractAsyncActivity;
import ni.org.ics.a2cares.app.MainActivity;
import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.RazonNoData;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.preferences.PreferencesActivity;
import ni.org.ics.a2cares.app.ui.activities.BuscarParticipanteActivity;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.utils.DeviceInfo;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Miguel Salinas on 28/12/2021.
 */
public class RazonNoDataActivity extends AbstractAsyncActivity {

	protected static final String TAG = RazonNoDataActivity.class.getSimpleName();
	private String codigo;
	private String razon;
	private String oRazon = "";
	private Spinner rndSpinner;
	private TextView labelOrazon;
	private EditText editOrazon;

	private SharedPreferences settings;
	private DeviceInfo infoMovil;
	private String username;

	private EstudioDBAdapter estudiosAdapter;
	private List<MessageResource> mVisitas;
	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reason_pending_data);

		infoMovil = new DeviceInfo(RazonNoDataActivity.this);

		settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(), mPass, false, false);
		codigo = getIntent().getStringExtra(Constants.PARTICIPANTE);
		rndSpinner = (Spinner) findViewById(R.id.razon);
		getData();
		mVisitas.add(new MessageResource("",0,this.getString(R.string.select)));
		Collections.sort(mVisitas);
		ArrayAdapter<MessageResource> dataAdapterVisit = new ArrayAdapter<MessageResource>(this, android.R.layout.simple_spinner_item, mVisitas);
		dataAdapterVisit.setDropDownViewResource(R.layout.spinner_item);
		rndSpinner.setAdapter(dataAdapterVisit);
		rndSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> spinner, View v,
									   int arg2, long arg3) {
				MessageResource mr = (MessageResource) spinner.getSelectedItem();
				if (!mr.getCatKey().isEmpty()){
					razon = mr.getCatKey();
				}
				if (razon != null && razon .equalsIgnoreCase("998")) {
					editOrazon.setVisibility(View.VISIBLE);
					labelOrazon.setVisibility(View.VISIBLE);
				} else {
					editOrazon.setVisibility(View.GONE);
					labelOrazon.setVisibility(View.GONE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		editOrazon = (EditText) findViewById(R.id.orazon);
		labelOrazon = (TextView) findViewById(R.id.label_orazon);
		editOrazon.setVisibility(View.GONE);
		labelOrazon.setVisibility(View.GONE);


		final Button saveButton = (Button) findViewById(R.id.save);
		saveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//captura entrada de la muestra
				oRazon = editOrazon.getText().toString();
				if (validarEntrada()) {
					RazonNoData rnd = new RazonNoData();
					rnd.setId(null);
					rnd.setCodigoParticipante(codigo);
					rnd.setRazon(razon);
					rnd.setOtraRazon(oRazon);
					rnd.setRecordUser(username);
					rnd.setEstado(Constants.STATUS_NOT_SUBMITTED);
					rnd.setRecordDate(new Date());
					rnd.setDeviceid(infoMovil.getDeviceId());
					estudiosAdapter.open();
					estudiosAdapter.crearRazonNoDatas(rnd);
					estudiosAdapter.close();
					Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_LONG).show();
					Intent i;
					i = new Intent(getApplicationContext(),
							MainActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					finish();
				}
			}

		});
	}

	@Override
	public void onBackPressed() {
		Toast.makeText(getApplicationContext(), this.getString(R.string.header_pending_data), Toast.LENGTH_LONG).show();
	}

	private boolean validarEntrada() {
		if (rndSpinner.getSelectedItem().toString().matches(this.getString(R.string.select))) {
			Toast.makeText(getApplicationContext(), this.getString(R.string.wrongSelect, "Razón"), Toast.LENGTH_LONG).show();
			rndSpinner.requestFocus();
			return false;
		} else if (rndSpinner.getSelectedItem().toString().matches("Otra razón") && oRazon.matches("")) {
			Toast.makeText(getApplicationContext(), this.getString(R.string.wrongSelect, this.getString(R.string.orazon_pending_data)), Toast.LENGTH_LONG).show();
			editOrazon.requestFocus();
			return false;
		} else {
			return true;
		}
	}


	private void getData() {
		estudiosAdapter.open();
		mVisitas = estudiosAdapter.getMessageResources(MainDBConstants.catRoot + "='CAT_RAZON_NO_DATA'", MainDBConstants.order);
		estudiosAdapter.close();
	}

}
