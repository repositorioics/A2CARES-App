package ni.org.ics.a2cares.app.ui.activities.menu;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
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
import ni.org.ics.a2cares.app.database.constants.EncuestasDBConstants;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.MuestraEnfermo;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.domain.survey.EncuestaCasa;
import ni.org.ics.a2cares.app.domain.survey.EncuestaParticipante;
import ni.org.ics.a2cares.app.domain.survey.EncuestaPesoTalla;
import ni.org.ics.a2cares.app.ui.activities.enterdata.NuevaEncuestaCasaActivity;
import ni.org.ics.a2cares.app.ui.activities.enterdata.NuevaEncuestaParticipanteActivity;
import ni.org.ics.a2cares.app.ui.activities.enterdata.NuevaEncuestaPesoTallaActivity;
import ni.org.ics.a2cares.app.ui.activities.enterdata.NuevaEncuestaSatisfaccionActivity;
import ni.org.ics.a2cares.app.ui.activities.enterdata.NuevaMuestraEnfermoActivity;
import ni.org.ics.a2cares.app.ui.activities.enterdata.NuevaVisitaTerrenoActivity;
import ni.org.ics.a2cares.app.ui.activities.enterdata.NuevoObsequioActivity;
import ni.org.ics.a2cares.app.ui.activities.enterdata.RazonNoDataActivity;
import ni.org.ics.a2cares.app.ui.activities.list.ListaMuestrasParticipanteActivity;
import ni.org.ics.a2cares.app.ui.adapters.MenuParticipanteAdapter;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.utils.DateUtil;


public class MenuParticipanteActivity extends AbstractAsyncActivity {

    private GridView gridView;
    private TextView textView;
    private String[] menu_participante;
    private static Participante participante = new Participante();
    private List<MuestraEnfermo> mMuestrasEnf = new ArrayList<MuestraEnfermo>();
    private EstudioDBAdapter estudiosAdapter;
    private boolean pendienteEncuestaParticip = false;
    private boolean pendienteEncuestaCasa = false;
    private boolean pendienteEncuestaPeso = false;
    private boolean pendienteMuestras = false;
    private boolean pendienteObseq = false;
    private boolean visitaExitosa = false;
    private boolean pendienteEncuestaSatisfaccion = false;

    private final int OPCION_VISITA = 0;
    private final int OPCION_ENCUESTA_CASA = 1;
    private final int OPCION_ENCUESTA_PARTICIPANTE = 2;
    private final int OPCION_ENCUESTA_PESOTALLA = 3;
    private final int OPCION_MUESTRAS = 4;
    private final int OPCION_OBSEQUIO = 5;
    private final int OPCION_IR_CASA = 6;
    private final int OPCION_MUESTRAS_ENF = 7;
    //Encuesta de satisfaccion de usuario
    private final int OPCION_ENCUESTA_SATISFACCION = 8;

    private static final int EXIT = 1;

    private AlertDialog alertDialog;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_participante);

        textView = (TextView) findViewById(R.id.label);
        gridView = (GridView) findViewById(R.id.gridView1);
        menu_participante = getResources().getStringArray(R.array.menu_participante);
        participante = (Participante) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
        visitaExitosa = getIntent().getBooleanExtra(Constants.VISITA_EXITOSA, false);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(),mPass,false,false);
        new FetchDataCasaTask().execute();


        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                /*String tituloEncuesta = "";
                boolean existeEncuesta = false;
                switch (position){
                    case OPCION_VISITA:
                        tituloEncuesta = getString(R.string.new_visit);
                        //existeEncuesta = existeencuestaParticip;
                        break;
                    case OPCION_ENCUESTA_CASA:
                        tituloEncuesta = getString(R.string.new_survey_1);
                        existeEncuesta = pendienteEncuestaParticip;
                        break;
                    case OPCION_ENCUESTA_PARTICIPANTE:
                        tituloEncuesta = getString(R.string.new_survey_2);
                        existeEncuesta = pendienteEncuestaParticip;
                        break;
                    case OPCION_ENCUESTA_PESOTALLA:
                        tituloEncuesta = getString(R.string.new_survey_3);
                        existeEncuesta = pendienteEncuestaPeso;
                        break;
                    case OPCION_MUESTRAS:
                        tituloEncuesta = getString(R.string.samples);
                        break;
                    case OPCION_OBSEQUIO:
                        tituloEncuesta = getString(R.string.gift);
                        break;
                    case OPCION_IR_CASA:
                        tituloEncuesta = getString(R.string.go_home);
                        break;
                    default:
                        break;
                }
                if (!tituloEncuesta.isEmpty()){
                    crearFomulario(position);
                }*/

                crearFomulario(position);
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
    
	@Override
	public void onBackPressed (){
        if (datosPendintes()){
            createDialog(EXIT);
        }
        else {

            Bundle arguments = new Bundle();
            Intent i = new Intent(getApplicationContext(),
                    MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtras(arguments);
            startActivity(i);
            finish();
        }
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case android.R.id.home:
                if (datosPendintes()){
                    createDialog(EXIT);
                }
                else{
                    finish();
                }
                return true;
            case R.id.MENU_BACK:
                if (datosPendintes()){
                    createDialog(EXIT);
                }
                else{
                    finish();
                }
                return true;
            case R.id.MENU_HOME:
                if (datosPendintes()){
                    createDialog(EXIT);
                }
                else{
                    i = new Intent(getApplicationContext(),
                            MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean datosPendintes() {
        return (visitaExitosa && (pendienteMuestras || pendienteEncuestaPeso || pendienteEncuestaParticip || pendienteEncuestaCasa || pendienteObseq || pendienteEncuestaSatisfaccion));
    }

    private void crearFomulario(int position){
        /*if (position == OPCION_ENCUESTA_MUESTRAS) {
            Bundle arguments = new Bundle();
            Intent i;
            if (participanteCHF != null) arguments.putSerializable(Constants.PARTICIPANTE, participanteCHF);
            i = new Intent(getApplicationContext(),
                    ListaMuestrasActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtras(arguments);
            i.putExtra(Constants.ACCION, Constants.ENTERING);
            startActivity(i);
        }else {*/
            new OpenDataEnterActivityTask().execute(String.valueOf(position));
        //}
    }

    private void createDialog(int dialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(dialog){
            case EXIT:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(this.getString(R.string.confirm_pendiente));
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Finish app
                        dialog.dismiss();
                        Intent i = new Intent(getApplicationContext(),
                                RazonNoDataActivity.class);
                        i.putExtra(Constants.PARTICIPANTE, participante.getCodigo());
                        startActivity(i);
                        finish();
                        //mExitShowing=false;
                    }

                });
                builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                        //mExitShowing=false;
                    }
                });
                //mExitShowing=true;
                break;
            default:
                break;
        }
        alertDialog = builder.create();
        alertDialog.show();
    }
    // ***************************************
    // Private classes
    // ***************************************
    private class OpenDataEnterActivityTask extends AsyncTask<String, Void, String> {
        private int position = 0;
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
            position = Integer.valueOf(values[0]);
            Bundle arguments = new Bundle();
            Intent i;
            try {
                estudiosAdapter.open();
                //String filtro = EncuestasDBConstants.participante + "=" + participanteCHF.getParticipante().getCodigo();
                switch (position){
                    case OPCION_VISITA:
                        arguments.putSerializable(Constants.PARTICIPANTE, participante);
                        i = new Intent(getApplicationContext(),
                                NuevaVisitaTerrenoActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        startActivity(i);
                        finish();
                        break;
                    case OPCION_ENCUESTA_CASA:
                        arguments.putSerializable(Constants.PARTICIPANTE, participante);
                        i = new Intent(getApplicationContext(),
                                NuevaEncuestaCasaActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        i.putExtra(Constants.VISITA_EXITOSA, visitaExitosa);
                        startActivity(i);
                        finish();
                        break;
                    case OPCION_ENCUESTA_PARTICIPANTE:
                        arguments.putSerializable(Constants.PARTICIPANTE, participante);
                        i = new Intent(getApplicationContext(),
                                NuevaEncuestaParticipanteActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        i.putExtra(Constants.VISITA_EXITOSA, visitaExitosa);
                        startActivity(i);
                        finish();
                        break;
                    case OPCION_ENCUESTA_PESOTALLA:
                            if (participante != null)
                                arguments.putSerializable(Constants.PARTICIPANTE, participante);
                            i = new Intent(getApplicationContext(),
                                    NuevaEncuestaPesoTallaActivity.class);
                            i.putExtra(Constants.VISITA_EXITOSA, visitaExitosa);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtras(arguments);
                            startActivity(i);
                            finish();
                        break;
                    case OPCION_MUESTRAS:
                        arguments.putSerializable(Constants.PARTICIPANTE, participante);
                        i = new Intent(getApplicationContext(),
                                ListaMuestrasParticipanteActivity.class);
                        i.putExtra(Constants.VISITA_EXITOSA, visitaExitosa);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        startActivity(i);
                        finish();
                        break;
                    case OPCION_OBSEQUIO:
                        arguments.putSerializable(Constants.PARTICIPANTE, participante);
                        i = new Intent(getApplicationContext(),
                                NuevoObsequioActivity.class);
                        i.putExtra(Constants.VISITA_EXITOSA, visitaExitosa);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        startActivity(i);
                        finish();
                        break;
                    case OPCION_IR_CASA:
                        arguments.putSerializable(Constants.CASA, participante.getCasa());
                        i = new Intent(getApplicationContext(),
                                MenuCasaActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(Constants.DESDE_PARTICIPANTE, true);
                        i.putExtras(arguments);
                        startActivity(i);
                        break;
                    case OPCION_MUESTRAS_ENF:
                        arguments.putSerializable(Constants.PARTICIPANTE, participante);
                        i = new Intent(getApplicationContext(),
                                NuevaMuestraEnfermoActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        startActivity(i);
                        finish();
                        break;
                    case OPCION_ENCUESTA_SATISFACCION:
                        arguments.putSerializable(Constants.PARTICIPANTE, participante);
                        i = new Intent(getApplicationContext(),
                                NuevaEncuestaSatisfaccionActivity.class);
                        i.putExtra(Constants.VISITA_EXITOSA, visitaExitosa);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        startActivity(i);
                        finish();
                        break;
                    default:
                        break;

                }

            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return "error";
            }finally {
                estudiosAdapter.close();
            }
            return "exito";
        }

        protected void onPostExecute(String resultado) {
            // after the request completes, hide the progress indicator
            dismissProgressDialog();
        }

    }

    private class FetchDataCasaTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
            try {
                estudiosAdapter.open();

                /*EncuestaCasa encuesta = estudiosAdapter.getEncuestaCasa(EncuestasDBConstants.casa + "=" + participante.getCasa().getCodigo(), null);
                if (encuesta!=null) {
                    pendienteEncuestaCasa = true;
                }
                EncuestaParticipante encuestap = estudiosAdapter.getEncuestasParticipante(EncuestasDBConstants.participante + "="+participante.getCodigo(), null);
                if (encuestap!=null) {
                    pendienteEncuestaParticip = true;
                }
                EncuestaPesoTalla encuestaPesoTalla = estudiosAdapter.getEncuestasPesoTalla(EncuestasDBConstants.participante + "=" + participante.getCodigo(), null);
                if (encuestaPesoTalla!=null) {
                    pendienteEncuestaPeso = true;
                }*/
                //actualizar por cualquier cambio en la base
                participante = estudiosAdapter.getParticipante(MainDBConstants.codigo + "='" + participante.getCodigo()+"'", null);
                mMuestrasEnf = estudiosAdapter.getMuestrasEnfermo(MainDBConstants.fechaMuestra + " = "+ DateUtil.getTodayWithZeroTime().getTime(), null);

                estudiosAdapter.close();


                pendienteEncuestaCasa = participante.getProcesos().getPendienteEnCasa().equalsIgnoreCase(Constants.YESKEYSND);
                pendienteEncuestaParticip = participante.getProcesos().getPendienteEncPart().equalsIgnoreCase(Constants.YESKEYSND);
                pendienteEncuestaPeso = participante.getProcesos().getPendientePyT().equalsIgnoreCase(Constants.YESKEYSND);
                pendienteMuestras = participante.getProcesos().getPendienteMxMA().equalsIgnoreCase(Constants.YESKEYSND);
                pendienteObseq = participante.getProcesos().getPendienteObseq().equalsIgnoreCase(Constants.YESKEYSND);

                pendienteEncuestaSatisfaccion = participante.getProcesos().getEsatUsuario().equalsIgnoreCase(Constants.YESKEYSND);

            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return "error";
            }
            return "exito";
        }

        protected void onPostExecute(String resultado) {
            // after the request completes, hide the progress indicator
            String edadFormateada = "";
            if (!participante.getEdad().equalsIgnoreCase("ND")) {
                String edad[] = participante.getEdad().split("/");
                if (edad.length > 0) {
                    edadFormateada = edad[0] + " años " + edad[1] + " meses " + edad[2] + " dias";
                }
            }
            textView.setText("");
            textView.setTextColor(Color.BLACK);
            String header = participante.getNombreCompleto() + "<br /> <font size='2'>" + getString(R.string.casa) + ": " + participante.getCasa().getCodigo()+ " - " +
                    getString(R.string.participant)+ ": "+ participante.getCodigo()+ "</font> <br /> <small>"
                    + getString(R.string.edad) + ": " + edadFormateada + " - " + getString(R.string.sexo) + ": " + participante.getSexo() + "</small>";

            textView.setText(Html.fromHtml(header));

            gridView.setAdapter(new MenuParticipanteAdapter(getApplicationContext(), R.layout.menu_item_2, menu_participante, participante, pendienteEncuestaCasa, pendienteEncuestaParticip,
                    pendienteEncuestaPeso, pendienteMuestras, pendienteObseq, visitaExitosa, mMuestrasEnf.size(), pendienteEncuestaSatisfaccion));
            dismissProgressDialog();
        }
    }
}

