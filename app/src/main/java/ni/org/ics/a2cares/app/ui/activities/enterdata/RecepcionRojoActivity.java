package ni.org.ics.a2cares.app.ui.activities.enterdata;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ni.org.ics.a2cares.app.AbstractAsyncActivity;
import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.domain.supervisor.RecepcionMuestra;
import ni.org.ics.a2cares.app.preferences.PreferencesActivity;
import ni.org.ics.a2cares.app.ui.activities.list.ListaRecepcionesActivity;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuSupervisorActivity;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.utils.DeviceInfo;

/**
 * Created by Miguel Salinas on 30/8/2021.
 */
public class RecepcionRojoActivity extends AbstractAsyncActivity {

    protected static final String TAG = RecepcionRojoActivity.class.getSimpleName();
    private String codigo;
    private Double volumen;
    private String observacion;
    private String lugarText;
    private ImageButton mBarcodeButton;
    private EditText editCodigo;
    private EditText editVolumen;
    private EditText editObs;
    private TextView labelVolumen;
    private Spinner lugar;
    private Spinner mMetodoView;
    private Date todayWithZeroTime = null;
    private String username;
    private SharedPreferences settings;
    private DeviceInfo infoMovil;

    public static final int BARCODE_CAPTURE = 2;
    private static final int MENU_VIEW = Menu.FIRST + 2;

    private AlertDialog mAlertDialog;
    private boolean mAlertShowing;
    private boolean mAlertExitShowing;
    private static final String ALERT_SHOWING = "alertshowing";
    private static final String ALERT_EXIT_SHOWING = "alertexitshowing";
    private static final String KEEP_CODIGO = "keepcodigo";

    private EstudioDBAdapter estudiosAdapter = null;
    private List<MessageResource> mLugares;
    // ***************************************
    // Activity methods
    // ***************************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recepcionrojo);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        try {
            todayWithZeroTime =formatter.parse(formatter.format(today));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        infoMovil = new DeviceInfo(RecepcionRojoActivity.this);

        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(),mPass,false,false);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(ALERT_SHOWING)) {
                mAlertShowing = savedInstanceState.getBoolean(ALERT_SHOWING, false);
            }
            if (savedInstanceState.containsKey(ALERT_EXIT_SHOWING)) {
                mAlertExitShowing = savedInstanceState.getBoolean(ALERT_EXIT_SHOWING, false);
            }
            if (savedInstanceState.containsKey(KEEP_CODIGO)) {
                codigo = savedInstanceState.getString(KEEP_CODIGO);
            }
        }

        mMetodoView = (Spinner) findViewById(R.id.metodo_busqueda);
        List<String> list = new ArrayList<String>();
        list.add(getString(R.string.desc_barcode));
        list.add(getString(R.string.enter)+" "+getString(R.string.code));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        mMetodoView.setAdapter(dataAdapter);

        mMetodoView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                editCodigo.setText("");
                editCodigo.setHint(getString(R.string.code));
                if (position==0){
                    editCodigo.setEnabled(false);
                    mBarcodeButton.setVisibility(View.VISIBLE);
                }
                else{
                    editCodigo.setFocusable(true);
                    editCodigo.setEnabled(true);
                    editCodigo.setFocusableInTouchMode(true);
                    mBarcodeButton.setVisibility(View.GONE);
                    editCodigo.requestFocus();
                    if (position==1){
                        editCodigo.setInputType(InputType.TYPE_CLASS_NUMBER);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

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
                    Toast t = Toast.makeText(getApplicationContext(),
                            getString(R.string.error, getString(R.string.barcode_error)),
                            Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    t.show();
                }

            }
        });

        editCodigo = (EditText) findViewById(R.id.codigo);
        editCodigo.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus == false)
                {
                    try{
                        if (!editCodigo.getText().toString().isEmpty())
                            codigo = editCodigo.getText().toString();
                    }
                    catch(Exception e){
                        codigo = null;
                        editCodigo.setText(null);
                        showToast("Código Inválido!!!!");
                        return;
                    }

                    if (codigo != null && codigo.matches("^\\d{4}$")){
                        estudiosAdapter.open();
                        if (estudiosAdapter.recepcionRegistrada(MainDBConstants.codigoMx + "='" + codigo + "' and " + MainDBConstants.tipoTubo + "='" + Constants.TIPO_TUBO_ROJO + "' and " +
                                MainDBConstants.fechaRecepcion + "=" + todayWithZeroTime.getTime())) {
                            editCodigo.setText(null);
                            codigo = null;
                            showToast("Ya ingresó este código!!!!");
                        }
                        estudiosAdapter.close();
                    }
                    else
                    {
                        editCodigo.setText(null);
                        codigo = null;
                        showToast("Código Inválido!!!!");
                    }
                }
            }
        });

        labelVolumen = ((TextView) findViewById(R.id.label_volumen));
        editVolumen = ((EditText) findViewById(R.id.volumen));
        editVolumen.setFocusableInTouchMode(true);
        editVolumen.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus == false)
                {
                    try{
                        volumen = Double.valueOf(editVolumen.getText().toString());
                    }
                    catch(Exception e){
                        return;
                    }

                    if (!(volumen>= Constants.VOLUMEN_MIN_ROJO && volumen<= Constants.VOLUMEN_MAX_ROJO)){
                        labelVolumen.setText("Volumen Inválido");
                        labelVolumen.setTextColor(Color.RED);
                    }
                    else{
                        labelVolumen.setText("Volumen");
                        labelVolumen.setTextColor(Color.BLACK);
                    }
                }
            }
        });
        editObs = ((EditText) findViewById(R.id.obs));
        lugar = (Spinner) findViewById(R.id.lugar);

        getData();
        mLugares.add(new MessageResource("",0,this.getString(R.string.select)));
        Collections.sort(mLugares);

        ArrayAdapter<MessageResource> dataAdapterLugar = new ArrayAdapter<MessageResource>(this,
                android.R.layout.simple_spinner_item, mLugares);
        dataAdapterLugar.setDropDownViewResource(R.layout.spinner_item);
        lugar.setAdapter(dataAdapterLugar);

        final Button saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //captura entrada de la muestra
                //editCodigo.requestFocus();
                try{
                    volumen = Double.valueOf(editVolumen.getText().toString());
                }
                catch(Exception e){
                    showToast("No ingresó volumen");
                    return;
                }
                observacion = editObs.getText().toString();
                MessageResource lugarSelec = (MessageResource) lugar.getSelectedItem();
                lugarText = lugarSelec.getSpanish(); //lugar.getSelectedItem().toString();

                if(validarEntrada()){
                    RecepcionMuestra recepcionMuestra = new RecepcionMuestra();
                    recepcionMuestra.setIdRecepcion(infoMovil.getId());
                    recepcionMuestra.setCodigoMx(codigo.toString());
                    recepcionMuestra.setFechaRecepcion(todayWithZeroTime);
                    recepcionMuestra.setVolumen(volumen);
                    recepcionMuestra.setObservacion(observacion);
                    recepcionMuestra.setLugar(lugarText);
                    recepcionMuestra.setTipoTubo(Constants.TIPO_TUBO_ROJO);
                    recepcionMuestra.setRecordUser(username);
                    recepcionMuestra.setEstado(Constants.STATUS_NOT_SUBMITTED);
                    recepcionMuestra.setRecordDate(new Date());
                    recepcionMuestra.setDeviceid(infoMovil.getDeviceId());
                    try {
                        estudiosAdapter.open();
                        estudiosAdapter.crearRecepcionMuestra(recepcionMuestra);
                        showToast("Registro Guardado");
                        reiniciar();
                    }catch (Exception ex){
                        ex.printStackTrace();
                        showToast("Error al guardar registro. "+ex.getLocalizedMessage());
                    }finally {
                        estudiosAdapter.close();
                    }
                }
            }

        });

        final Button endButton = (Button) findViewById(R.id.finish);
        endButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //finaliza la actividad
                createExitDialog();
            }

        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ALERT_SHOWING, mAlertShowing);
        outState.putBoolean(ALERT_EXIT_SHOWING, mAlertExitShowing);
        if (codigo!=null) outState.putString(KEEP_CODIGO, codigo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.general, menu);
        menu.add(0, MENU_VIEW, 0, getString(R.string.list))
                .setIcon(R.mipmap.ic_search);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.MENU_BACK:
                createExitDialog();
                return true;
            case R.id.MENU_HOME:
                i = new Intent(getApplicationContext(),
                        MenuSupervisorActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                return true;
            case MENU_VIEW:
                i = new Intent(getApplicationContext(),
                        ListaRecepcionesActivity.class);
                i.putExtra(Constants.TIPO_TUBO, Constants.TIPO_TUBO_ROJO);
                startActivity(i);
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
                try{
                    codigo = sb;
                }
                catch(Exception e){
                    showToast("Lectura Inválida!!!!");
                    return;
                }
            }
            if (codigo != null && codigo.matches("^\\d{4}$")){
                estudiosAdapter.open();
                if (estudiosAdapter.recepcionRegistrada(MainDBConstants.codigoMx + "='" + codigo + "' and " + MainDBConstants.tipoTubo + "='" + Constants.TIPO_TUBO_ROJO + "' and " +
                        MainDBConstants.fechaRecepcion + "=" + todayWithZeroTime.getTime())) {
                    showToast("Ya ingresó este código!!!!");
                }else{
                    editCodigo.setText(codigo.toString());
                }
                estudiosAdapter.close();
            }
            else
            {
                showToast("Lectura Inválida!!!!");
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    private boolean validarEntrada() {
        //Valida la entrada
        if (codigo == null){
            showToast(this.getString( R.string.error_codigo));
            return false;
        }
        else if (volumen == null){
            showToast(this.getString( R.string.error_volumen));
            return false;
        }
        else if (lugarText.matches(this.getString(R.string.select))){
            showToast(this.getString( R.string.error_lugar));
            return false;
        }
        else if (!(volumen>=Constants.VOLUMEN_MIN_ROJO && volumen<=Constants.VOLUMEN_MAX_ROJO)){
            showToast(this.getString( R.string.error_volumen));
            return false;
        }
        else if (!codigo.matches("^\\d{4}$")){
            showToast(this.getString( R.string.error_codigo));
            return false;
        }
        else{
            return true;
        }
    }

    private void reiniciar(){
        editCodigo.setText(null);
        codigo = null;
        editVolumen.setText(null);
        volumen = null;
        lugarText = null;
        editObs.setText(null);
        observacion =null;
    }


    @Override
    public void onBackPressed (){
        createExitDialog();
    }
    @SuppressWarnings("deprecation")
    private void createExitDialog() {
        // Pregunta si desea salir o no
        mAlertDialog = new AlertDialog.Builder(this).create();
        mAlertDialog.setIcon(android.R.drawable.ic_dialog_info);
        mAlertDialog.setTitle(getString(R.string.confirm));
        mAlertDialog.setMessage(getString(R.string.exit));

        DialogInterface.OnClickListener uploadDialogListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                switch (i) {
                    case DialogInterface.BUTTON1: // yes
                        mAlertExitShowing = false;
                        finish();
                    case DialogInterface.BUTTON2: // no
                        mAlertExitShowing = false;
                        dialog.dismiss();
                        break;
                }
            }
        };
        mAlertDialog.setCancelable(false);
        mAlertDialog.setButton(getString(R.string.yes), uploadDialogListener);
        mAlertDialog.setButton2(getString(R.string.no), uploadDialogListener);
        mAlertExitShowing = true;
        mAlertDialog.show();
    }

    private void getData() {
        estudiosAdapter.open();
        mLugares = estudiosAdapter.getMessageResources(MainDBConstants.catRoot + "='CAT_LUGAR_RECEP_MX'", MainDBConstants.order);
        estudiosAdapter.close();
    }

}
