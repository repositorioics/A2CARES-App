package ni.org.ics.a2cares.app;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import ni.org.ics.a2cares.app.bluetooth.activity.ChatActivity;
import ni.org.ics.a2cares.app.preferences.PreferencesActivity;
import ni.org.ics.a2cares.app.ui.activities.BuscarCasaActivity;
import ni.org.ics.a2cares.app.ui.activities.BuscarParticipanteActivity;
import ni.org.ics.a2cares.app.ui.activities.enterdata.NuevoTamizajeActivity;
import ni.org.ics.a2cares.app.ui.activities.server.DownloadBaseActivity;
import ni.org.ics.a2cares.app.ui.activities.server.UploadAllActivity;
import ni.org.ics.a2cares.app.ui.adapters.MainActivityAdapter;
import ni.org.ics.a2cares.app.utils.Constants;

public class MainActivity extends AbstractAsyncActivity {

    private static final int EXIT = 1;
    private static final int UPDATE_EQUIPO = 11;
    private static final int UPDATE_SERVER = 12;

    private GridView gridView;
    private TextView textView;
    private String[] menu_principal;

    private AlertDialog alertDialog;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.label);
        gridView = (GridView) findViewById(R.id.gridView1);
        menu_principal = getResources().getStringArray(R.array.menu_principal);

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
                        i = new Intent(getApplicationContext(), DownloadBaseActivity.class);
                        startActivityForResult(i, UPDATE_EQUIPO);
                        break;
                    case 5:
                        i = new Intent(getApplicationContext(), UploadAllActivity.class);
                        startActivityForResult(i, UPDATE_SERVER);
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
        }
        else{
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
            /*case DOWNLOAD:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(this.getString(R.string.downloading));
                builder.setIcon(android.R.drawable.ic_menu_help);
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        estudiosAdapter.open();
                        boolean hayDatos = estudiosAdapter.verificarData();
                        estudiosAdapter.close();
                        if (hayDatos) {
                            createDialog(VERIFY);
                        } else {
                            Intent ie = new Intent(getApplicationContext(), DownloadBaseActivity.class);
                            startActivityForResult(ie, UPDATE_EQUIPO);
                        }
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
            case UPLOAD:

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
            case VERIFY_DOWNLOAD:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(getString(R.string.enter_pin_download));
                builder.setIcon(R.drawable.ic_menu_login);
                // Set an EditText view to get user input
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);
                builder.setPositiveButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (input.getText().toString().length()>0) {
                            String mPin = input.getText().toString();
                            new VerificarPinTask().execute(mPin);
                        }else{
                            createDialog(VERIFY_DOWNLOAD);
                            Toast.makeText(getApplicationContext(),	getString(R.string.pin_required), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton(this.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                break;*/
            default:
                break;
        }
        alertDialog = builder.create();
        alertDialog.show();
    }
}