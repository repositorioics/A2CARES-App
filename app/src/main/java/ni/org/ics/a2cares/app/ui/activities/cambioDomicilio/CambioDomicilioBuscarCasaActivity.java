package ni.org.ics.a2cares.app.ui.activities.cambioDomicilio;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ni.org.ics.a2cares.app.AbstractAsyncActivity;
import ni.org.ics.a2cares.app.MainActivity;
import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.Casa;
import ni.org.ics.a2cares.app.entomologia.activities.MenuEntomologiaActivity;
import ni.org.ics.a2cares.app.ui.activities.BuscarCasaActivity;
import ni.org.ics.a2cares.app.ui.adapters.CambioDomicilioCasaListAdapter;
import ni.org.ics.a2cares.app.ui.adapters.CasaListAdapter;
import ni.org.ics.a2cares.app.utils.Constants;

public class CambioDomicilioBuscarCasaActivity extends AbstractAsyncActivity {
    private Spinner mMetodoView;
    private EditText mParametroView;
    private ImageButton mBarcodeButton;
    private ImageButton mFindButton;
    private RecyclerView recyclerView;

    public static final int BARCODE_CAPTURE = 2;
    private int opcion;

    private EstudioDBAdapter estudiosAdapter;
    private Casa mCasa = new Casa();
    private List<Casa> mCasas = new ArrayList<Casa>();
    private CambioDomicilioCasaListAdapter adapter;
    //private static final int HACER_TAMIZAJE = 1;
    private static final int SELECT_HOUSE = 2;
    private AlertDialog alertDialog;
    private Boolean nuevoIngreso;
    private Boolean desdeMenuEnto;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_house_list);

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(),mPass,false,false);
        nuevoIngreso = getIntent().getBooleanExtra(Constants.NUEVO_INGRESO,false);
        desdeMenuEnto = getIntent().getBooleanExtra(Constants.MENU_ENTO, false);
        mMetodoView = (Spinner) findViewById(R.id.metodo_busqueda);
        recyclerView = findViewById(R.id.recycler_view);

        List<String> list = new ArrayList<String>();
        list.add(getString(R.string.desc_barcode));
        list.add(getString(R.string.enter)+" "+getString(R.string.code)+" "+getString(R.string.casa));
        //list.add(getString(R.string.enter)+" "+getString(R.string.casa_jefe_familia_nombre));
        //list.add(getString(R.string.enter)+" "+getString(R.string.casa_jefe_familia_apellido));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mMetodoView.setAdapter(dataAdapter);

        adapter = new CambioDomicilioCasaListAdapter(mCasas, nuevoIngreso, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        mMetodoView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                opcion = position;
                if (position==0){
                    mParametroView.setVisibility(View.GONE);
                    mFindButton.setVisibility(View.GONE);
                    mBarcodeButton.setVisibility(View.VISIBLE);
                }
                else{
                    mParametroView.setVisibility(View.VISIBLE);
                    mFindButton.setVisibility(View.VISIBLE);
                    mBarcodeButton.setVisibility(View.GONE);
                    mParametroView.requestFocus();
                    //if (position==1){
                    mParametroView.setInputType(InputType.TYPE_CLASS_NUMBER);
                    mParametroView.setHint(getString(R.string.code)+" "+getString(R.string.casa));
                    //}
					/*else if (position==2){
						mParametroView.setInputType(InputType.TYPE_CLASS_TEXT);
						mParametroView.setHint(getString(R.string.casa_jefe_familia_nombre));
					}
					else if (position==3){
						mParametroView.setInputType(InputType.TYPE_CLASS_TEXT);
						mParametroView.setHint(getString(R.string.casa_jefe_familia_apellido));
					}*/
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        mParametroView = (EditText) findViewById(R.id.codigo);
        mParametroView.setVisibility(View.GONE);


        mBarcodeButton = (ImageButton) findViewById(R.id.barcode_button);
        mFindButton = (ImageButton) findViewById(R.id.find_button);

        mBarcodeButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("com.google.zxing.client.android.SCAN");
                try {
                    startActivityForResult(i, BARCODE_CAPTURE);
                } catch (ActivityNotFoundException e) {
                    Toast t = Toast.makeText(getApplicationContext(),
                            getString(R.string.error, R.string.barcode_error),
                            Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    t.show();
                }
            }
        });

        mFindButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                if ((mParametroView.getText().toString()==null) || (mParametroView.getText().toString().matches(""))){
                    mParametroView.requestFocus();
                    mParametroView.setError(getString(R.string.search_hint));
                    return;
                }
                buscarCasa(mParametroView.getText().toString(),opcion);
            }
        });

        mFindButton.setVisibility(View.GONE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.general, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        else if(item.getItemId()==R.id.MENU_BACK){
            finish();
            return true;
        }
        else if(item.getItemId()==R.id.MENU_HOME){
            if (!desdeMenuEnto) {
                Intent i = new Intent(getApplicationContext(),
                        CambioDomicilioMainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                return true;
            } else {
                Intent i = new Intent(getApplicationContext(),
                        CambioDomicilioMainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                return true;
            }
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == BARCODE_CAPTURE && intent != null) {
            String sb = intent.getStringExtra("SCAN_RESULT");
            if (sb != null && sb.length() > 0) {
                try{
                    Integer.parseInt(sb);
                    buscarCasa(sb,0);
                }
                catch(Exception e){
                    showToast(e.getLocalizedMessage());
                    return;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);

    }

    public void buscarCasa(String parametro, int opcion){
        String filtro = "";
        switch (opcion) {
            case 0:
                filtro = MainDBConstants.codigo + "=" + parametro;
                break;
            case 1:
                filtro = MainDBConstants.codigo + "=" + parametro;
                break;
		/*case 2:
			filtro = MainDBConstants.nombre1JefeFamilia + " like '%" + parametro + "%' or " + MainDBConstants.nombre2JefeFamilia + " like '%" + parametro + "%'";
			break;
		case 3:
			filtro = MainDBConstants.apellido1JefeFamilia + " like '%" + parametro + "%' or " + MainDBConstants.apellido1JefeFamilia + " like '%" + parametro + "%'";
			break;	*/
        }
        new CambioDomicilioBuscarCasaActivity.FetchCambioDomicilioDataTask().execute(filtro);
    }


    // ***************************************
    // Private classes
    // ***************************************
    private class FetchCambioDomicilioDataTask extends AsyncTask<String, Void, String> {
        private String filtro = null;
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
            filtro = values[0];
            try {
                estudiosAdapter.open();
                mCasas.clear();
                mCasas.addAll(estudiosAdapter.getCasas(filtro, MainDBConstants.codigo));
                estudiosAdapter.close();
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return "error";
            }
            return "exito";
        }

        protected void onPostExecute(String resultado) {
            // after the network request completes, hide the progress indicator
            dismissProgressDialog();
            showResult();
        }

    }

    // ***************************************
    // Private methods
    // ***************************************
    private void showResult() {
        adapter.notifyDataSetChanged();
        if (mCasas.isEmpty()) showToast(getString(R.string.no_items));
    }
}
