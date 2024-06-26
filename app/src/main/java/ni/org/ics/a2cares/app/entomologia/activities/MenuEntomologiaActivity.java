package ni.org.ics.a2cares.app.entomologia.activities;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import ni.org.ics.a2cares.app.AbstractAsyncActivity;
import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.domain.core.ControlAsistencia;
import ni.org.ics.a2cares.app.entomologia.adapters.MenuEntomologiaAdapter;
import ni.org.ics.a2cares.app.entomologia.constants.EntomologiaBConstants;
import ni.org.ics.a2cares.app.entomologia.domain.CuestionarioHogar;
import ni.org.ics.a2cares.app.entomologia.domain.CuestionarioPuntoClave;
import ni.org.ics.a2cares.app.entomologia.server.DownloadAllEntoActivity;
import ni.org.ics.a2cares.app.entomologia.server.UploadAllEntoActivity;
import ni.org.ics.a2cares.app.preferences.PreferencesActivity;
import ni.org.ics.a2cares.app.ui.activities.BuscarCasaActivity;
import ni.org.ics.a2cares.app.ui.activities.enterdata.NuevoControlAsistenciaGPSEntoActivity;
import ni.org.ics.a2cares.app.ui.activities.server.UploadAllActivity;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MenuEntomologiaActivity extends AbstractAsyncActivity {

	private GridView gridView;
	private TextView textView;
	private String[] menu_entomologia;
	
	private static final int UPDATE_EQUIPO = 11;
	private static final int UPDATE_SERVER = 12;
	
	private static final int DOWNLOAD = 1;
	private static final int UPLOAD = 2;
	private static final int VERIFY = 3;
	private static final int EXIT = 4;
	
	private AlertDialog alertDialog;

    private EstudioDBAdapter estudiosAdapter;


	private SharedPreferences settings;

	private String username;
	private ArrayList<CuestionarioHogar> mCuestionarios = new ArrayList<CuestionarioHogar>();
	private ArrayList<CuestionarioPuntoClave> mCuestionariosPC = new ArrayList<CuestionarioPuntoClave>();


	@TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_participante);

		settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);


		textView = (TextView) findViewById(R.id.label);
        gridView = (GridView) findViewById(R.id.gridView1);
        menu_entomologia = getResources().getStringArray(R.array.menu_entomologia);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(),mPass,false,false);
        new FetchDataTask().execute();

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Intent i;
				switch (position) {
					case 0:
						i = new Intent(getApplicationContext(),
								NuevoCuestionarioHogarActivity.class);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
						finish();
						break;
					case 1:
						Bundle arguments = new Bundle();
						arguments.putString(Constants.TITLE, getString(R.string.cuestionario_hogar));
						if (mCuestionarios !=null) arguments.putSerializable(Constants.OBJECTO , mCuestionarios);
						i = new Intent(getApplicationContext(),
								ListReviewActivity.class);
						i.putExtras(arguments);
						startActivity(i);
						finish();
						break;
					case 2:
						i = new Intent(getApplicationContext(),
								NuevoCuestionarioPuntoClaveActivity.class);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
						finish();
						break;
					case 3:
						Bundle arguments2 = new Bundle();
						arguments2.putString(Constants.TITLE, getString(R.string.cuestionario_pc));
						if (mCuestionariosPC !=null) arguments2.putSerializable(Constants.OBJECTO , mCuestionariosPC);
						i = new Intent(getApplicationContext(),
								ListReviewActivity.class);
						i.putExtras(arguments2);
						startActivity(i);
						finish();
						break;
					case 4:
						createDialog(DOWNLOAD);
						break;
					case 5:
						createDialog(UPLOAD);
						break;
					case 6:
						i = new Intent(getApplicationContext(), BuscarCasaActivity.class);
						i.putExtra(Constants.MENU_ENTO, true);
						startActivity(i);
						break;
					case 7: //Control Asistencia
						//estudiosAdapter.open();
						//Boolean esPersonA2cares = estudiosAdapter.buscarRol(username, "ROLE_PERSONAL_A2CARES");
						//estudiosAdapter.close();
						//if (esPersonA2cares){
						// i = new Intent(getApplicationContext(), NuevoControlAsistenciaActivity.class);
						i = new Intent(getApplicationContext(), NuevoControlAsistenciaGPSEntoActivity.class);
						startActivity(i);

						/*}
						else{
							showToast(getApplicationContext().getString(R.string.perm_error));
						}*/
						break;
					case 8:
						createDialog(EXIT);
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
		getMenuInflater().inflate(R.menu.menu_ento, menu);
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
		Intent i;
		switch (item.getItemId()) {
			case android.R.id.home:
				return true;
			case R.id.MENU_BACK:
				finish();
				return true;
			case R.id.MENU_HOME:
				return true;
			case R.id.MENU_PREFERENCES:
				Intent ig = new Intent(this, PreferencesActivity.class);
				startActivity(ig);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(dialog){
		case EXIT:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.exiting));
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// Finish app
					dialog.dismiss();
					finish();
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
					estudiosAdapter.open();
					boolean hayDatos = estudiosAdapter.verificarDataEnto();
					estudiosAdapter.close();
					if(hayDatos){
						createDialog(VERIFY);
					}
					else{
						Intent ie = new Intent(getApplicationContext(), DownloadAllEntoActivity.class);
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
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.uploading));
			builder.setIcon(android.R.drawable.ic_menu_help);
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					Intent ie = new Intent(getApplicationContext(), UploadAllEntoActivity.class);
					startActivityForResult(ie, UPDATE_SERVER);

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
					Intent ie = new Intent(getApplicationContext(), DownloadAllEntoActivity.class);
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
		default:
			break;
		}
		alertDialog = builder.create();
		alertDialog.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {	
		super.onActivityResult(requestCode, resultCode, intent);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String mensaje = "";
		if (requestCode == UPDATE_SERVER){
			if (resultCode == RESULT_CANCELED) {
				builder.setTitle(getApplicationContext().getString(R.string.error));
				builder.setIcon(android.R.drawable.ic_dialog_alert);
				mensaje = intent.getStringExtra("resultado");
			}
			else{
				builder.setTitle(getApplicationContext().getString(R.string.confirm));
				builder.setIcon(android.R.drawable.ic_dialog_info);
				mensaje = getApplicationContext().getString(R.string.success);
			}
		}
		if (requestCode == UPDATE_EQUIPO) {
			if (resultCode == RESULT_CANCELED) {
				builder.setTitle(getApplicationContext().getString(R.string.error));
				builder.setIcon(android.R.drawable.ic_dialog_alert);
				mensaje = intent.getStringExtra("resultado");
			} else {
				builder.setTitle(getApplicationContext().getString(R.string.confirm));
				builder.setIcon(android.R.drawable.ic_dialog_info);
				mensaje = getApplicationContext().getString(R.string.success);
				//new FetchDataCasasCasosTask().execute();
			}
		}
		if (resultCode == Constants.RESULT_NO_DATA) {
			if (requestCode == UPDATE_SERVER){
				builder.setTitle(getApplicationContext().getString(R.string.confirm));
				builder.setIcon(android.R.drawable.ic_dialog_info);
				mensaje = getApplicationContext().getString(R.string.no_data_to_send);
			}
		}
		builder.setMessage(mensaje)
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

    private class FetchDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date dateWithoutTime = null;
			try {
				dateWithoutTime = sdf.parse(sdf.format(new Date()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
            // before the request begins, show a progress indicator
			estudiosAdapter.open();
			mCuestionarios = (ArrayList<CuestionarioHogar>) estudiosAdapter.getCuestionariosHogar(MainDBConstants.recordDate + ">=" + timeStamp.getTime(), EntomologiaBConstants.codigoVivienda);
			mCuestionariosPC = (ArrayList<CuestionarioPuntoClave>) estudiosAdapter.getCuestionariosPuntoClave(EntomologiaBConstants.TODAY + ">=" + timeStamp.getTime(), EntomologiaBConstants.nombrePuntoClave);
			//masistenciaEnto = (ArrayList<ControlAsistencia>) estudiosAdapter.getControlAsistencial(EntomologiaBConstants.TODAY + ">=" + timeStamp.getTime(),EntomologiaBConstants.USUARIO);
			//mCuestionariosPC = (ArrayList<CuestionarioPuntoClave>) estudiosAdapter.getCuestionariosPuntoClave(EntomologiaBConstants.TODAY + ">=" + "1664554960000", EntomologiaBConstants.nombrePuntoClave);
			estudiosAdapter.close();
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
            return "exito";
        }

        protected void onPostExecute(String resultado) {

            textView.setText("");
            textView.setTextColor(Color.BLACK);
            textView.setText(getString(R.string.entomologia)+"\n"+getString(R.string.title_home));
            gridView.setAdapter(new MenuEntomologiaAdapter(getApplicationContext(), R.layout.menu_item_2, menu_entomologia, mCuestionarios.size(), mCuestionariosPC.size()));
            dismissProgressDialog();
        }
    }
		
}
	
