package ni.org.ics.a2cares.app;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import ni.org.ics.a2cares.app.bluetooth.activity.ChatActivity;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.preferences.PreferencesActivity;
import ni.org.ics.a2cares.app.ui.activities.BuscarCasaActivity;
import ni.org.ics.a2cares.app.ui.activities.BuscarParticipanteActivity;
import ni.org.ics.a2cares.app.ui.activities.enterdata.NuevoTamizajeActivity;
import ni.org.ics.a2cares.app.ui.activities.list.ListaPuntosCandidatosActivity;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuLaboratorioActivity;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuSupervisorActivity;
import ni.org.ics.a2cares.app.ui.activities.server.DownloadBaseActivity;
import ni.org.ics.a2cares.app.ui.activities.server.UploadAllActivity;
import ni.org.ics.a2cares.app.ui.adapters.MainActivityAdapter;
import ni.org.ics.a2cares.app.utils.Constants;

public class MainActivity extends AbstractAsyncActivity {

    private static final int EXIT = 1;
    private static final int DOWNLOAD = 2;
    private static final int VERIFY = 3;
    private static final int UPDATE_EQUIPO = 4;
    private static final int UPDATE_SERVER = 5;
    private static final int NO_DATA_SEND = 6;

    private GridView gridView;
    private TextView textView;
    private String[] menu_principal;

    private AlertDialog alertDialog;

    private SharedPreferences settings;
    private EstudioDBAdapter estudiosAdapter;
    private String username;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.label);
        gridView = (GridView) findViewById(R.id.gridView1);
        menu_principal = getResources().getStringArray(R.array.menu_principal);

        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(), mPass, false, false);

        textView.setText("");
        textView.setTextColor(Color.BLACK);
        textView.setText(getString(R.string.main_header)+"\n"+getString(R.string.title_home));
        gridView.setAdapter(new MainActivityAdapter(getApplicationContext(), R.layout.menu_item_2, menu_principal));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent i;
                switch (position){
                    case 0:
                        i = new Intent(getApplicationContext(), NuevoTamizajeActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        break;
                    case 1:
                        i = new Intent(getApplicationContext(), BuscarParticipanteActivity.class);
                        startActivity(i);
                        break;
                    case 2:
                        i = new Intent(getApplicationContext(), BuscarCasaActivity.class);
                        startActivity(i);
                        break;
                    case 3:
                        Bundle arguments = new Bundle();
                        i = new Intent(getApplicationContext(),
                                ChatActivity.class);
                        arguments.putSerializable(Constants.CASA , null);
                        i.putExtra(Constants.ACCION, Constants.RECEIVING);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        break;
                    case 4:
                        createDialog(DOWNLOAD);
                        /*estudiosAdapter.open();
                        boolean hayDatos = estudiosAdapter.verificarData();
                        estudiosAdapter.close();
                        if (hayDatos) {
                        createDialog(VERIFY);
                        } else {
                            i = new Intent(getApplicationContext(), DownloadBaseActivity.class);
                            startActivityForResult(i, UPDATE_EQUIPO);
                        }*/
                        break;
                    case 5:
                        i = new Intent(getApplicationContext(), UploadAllActivity.class);
                        startActivityForResult(i, UPDATE_SERVER);
                        break;
                    case 6:
                        estudiosAdapter.open();
                        Boolean esSuperV = estudiosAdapter.buscarRol(username, "ROLE_SUPER");
                        estudiosAdapter.close();
                        if (esSuperV){
                            i = new Intent(getApplicationContext(), MenuSupervisorActivity.class);
                            startActivity(i);
                        }
                        else{
                            showToast(getApplicationContext().getString(R.string.perm_error));
                        }
                        break;
                    case 7:
                        estudiosAdapter.open();
                        Boolean esLaboratorista = estudiosAdapter.buscarRol(username, "ROLE_LABO");
                        estudiosAdapter.close();
                        if (esLaboratorista){
                            i = new Intent(getApplicationContext(), MenuLaboratorioActivity.class);
                            startActivity(i);
                        }
                        else{
                            showToast(getApplicationContext().getString(R.string.perm_error));
                        }
                        break;
                    case 8:
                            i = new Intent(getApplicationContext(), ListaPuntosCandidatosActivity.class);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MENU_EXIT:
                createDialog(EXIT);
                return true;
            case R.id.MENU_PREFERENCES:
                Intent ig = new Intent(this, PreferencesActivity.class);
                startActivity(ig);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_CANCELED) {
            if (requestCode == UPDATE_EQUIPO||requestCode == UPDATE_SERVER){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getApplicationContext().getString(R.string.error, ""));
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setMessage(intent.getStringExtra("resultado"))
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                return;
            }
        } else if (resultCode == Constants.RESULT_NO_DATA) {
            if (requestCode == UPDATE_SERVER){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getApplicationContext().getString(R.string.confirm));
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setMessage(getApplicationContext().getString(R.string.no_data_to_send))
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        } else{
            if (requestCode == UPDATE_EQUIPO||requestCode == UPDATE_SERVER){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getApplicationContext().getString(R.string.confirm));
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setMessage(getApplicationContext().getString(R.string.success))
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        }
    }

    private void createDialog(int dialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(dialog) {
            case EXIT:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(this.getString(R.string.exiting));
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Finish app
                        dialog.dismiss();
                        finish();
                        int pid = android.os.Process.myPid();
                        android.os.Process.killProcess(pid);
                        System.exit(0);
                    }
                });
                builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                break;
            case DOWNLOAD:

                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(this.getString(R.string.downloading));
                builder.setIcon(android.R.drawable.ic_menu_help);
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new DownLoadTask().execute();
                    }
                });
                builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                break;
            case VERIFY:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(this.getString(R.string.data_not_sent));
                builder.setIcon(android.R.drawable.ic_menu_help);
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent ie = new Intent(getApplicationContext(), DownloadBaseActivity.class);
                        startActivityForResult(ie, UPDATE_EQUIPO);
                    }
                });
                builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                break;
            case NO_DATA_SEND:
                builder.setTitle(getApplicationContext().getString(R.string.confirm));
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setMessage(getApplicationContext().getString(R.string.no_data_to_send))
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                break;
            default:
                break;
        }
        alertDialog = builder.create();
        alertDialog.show();
    }

    public class DownLoadTask extends AsyncTask<String, Void, String> {
        boolean hayDatos = false;
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... params) {
            estudiosAdapter.open();
            hayDatos = estudiosAdapter.verificarData();
            estudiosAdapter.close();
            return "OK";
        }

        @Override
        protected void onPostExecute(final String respuesta) {
            dismissProgressDialog();
            if (hayDatos) {
                createDialog(VERIFY);
            } else {
                Intent ie = new Intent(getApplicationContext(), DownloadBaseActivity.class);
                startActivityForResult(ie, UPDATE_EQUIPO);
            }
        }

        @Override
        protected void onCancelled() {
            dismissProgressDialog();
        }
    }
}