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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import ni.org.ics.a2cares.app.AbstractAsyncActivity;
import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.RazonNoData;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.domain.puntos.PuntoCandidato;
import ni.org.ics.a2cares.app.preferences.PreferencesActivity;
import ni.org.ics.a2cares.app.ui.activities.BuscarParticipanteActivity;
import ni.org.ics.a2cares.app.ui.activities.list.ListaPuntosCandidatosActivity;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.utils.DeviceInfo;
import ni.org.ics.a2cares.app.wizard.model.LabelPage;

/**
 * Created by Miguel Salinas on 28/12/2021.
 */
public class DescartarPuntoCandidatoActivity extends AbstractAsyncActivity {

	protected static final String TAG = DescartarPuntoCandidatoActivity.class.getSimpleName();
	private String razon;
	private String oRazon = "";
	private Spinner rndSpinner;
	private TextView labelOrazon;
	private EditText editOrazon;
	private TextView labelRazon;

	private SharedPreferences settings;
	private DeviceInfo infoMovil;
	private String username;

	private EstudioDBAdapter estudiosAdapter;
	private List<MessageResource> mRazones;

	private PuntoCandidato mPunto;
	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discard_candidate_point);

		infoMovil = new DeviceInfo(DescartarPuntoCandidatoActivity.this);

		settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
		String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
		estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(), mPass, false, false);
		mPunto = (PuntoCandidato) getIntent().getExtras().getSerializable(Constants.PUNTO_GPS);
		rndSpinner = (Spinner) findViewById(R.id.razon);
		getData();
		mRazones.add(new MessageResource("",0,this.getString(R.string.select)));
		Collections.sort(mRazones);
		ArrayAdapter<MessageResource> dataAdapterVisit = new ArrayAdapter<MessageResource>(this, android.R.layout.simple_spinner_item, mRazones);
		dataAdapterVisit.setDropDownViewResource(R.layout.spinner_item_2);
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

		labelRazon = (TextView) findViewById(R.id.label_razon);
		labelRazon.setText(this.getString(R.string.reason_discard, mPunto.getCodigo().toString()));
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
					mPunto.setDescartado(Constants.YESKEYSND);
					mPunto.setRazonDescarte(razon);
					mPunto.setOtraRazonDescarte(oRazon);
					mPunto.setUsuarioDescarte(username);
					mPunto.setEstado(Constants.STATUS_NOT_SUBMITTED);
					mPunto.setFechaDescarte(new Date());
					estudiosAdapter.open();
					estudiosAdapter.editarPuntoCandidato(mPunto);
					estudiosAdapter.close();
					Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_LONG).show();
					Intent i;
					i = new Intent(getApplicationContext(),
							ListaPuntosCandidatosActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					finish();
				}
			}

		});
	}

	@Override
	public void onBackPressed() {
		finish();
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
		mRazones = estudiosAdapter.getMessageResources(MainDBConstants.catRoot + "='CAT_RAZON_DESCARTE'", MainDBConstants.order);
		estudiosAdapter.close();
	}

}
