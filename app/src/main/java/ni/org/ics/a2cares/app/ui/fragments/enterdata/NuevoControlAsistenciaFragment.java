package ni.org.ics.a2cares.app.ui.fragments.enterdata;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.joda.time.DateMidnight;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ni.org.ics.a2cares.app.ControAsistenciaGPSApplication;
import ni.org.ics.a2cares.app.MainActivity;
import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.bluetooth.common.logger.Log;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.MuestraEnfermo;
import ni.org.ics.a2cares.app.domain.core.ControlAsistencia;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.preferences.PreferencesActivity;
import ni.org.ics.a2cares.app.ui.activities.enterdata.NuevoControlAsistenciaActivity;
import ni.org.ics.a2cares.app.ui.activities.enterdata.NuevoControlAsistenciaGPSActivity;
import ni.org.ics.a2cares.app.ui.activities.maps.CoordenadasMapActivity;
import ni.org.ics.a2cares.app.ui.activities.maps.CoordenadasOnlyActivity;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuParticipanteActivity;
import ni.org.ics.a2cares.app.ui.forms.ControlAsistenciaFormLabels;
import ni.org.ics.a2cares.app.ui.forms.TamizajeFormLabels;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.utils.DeviceInfo;
import ni.org.ics.a2cares.app.wizard.model.AbstractWizardModel;
import ni.org.ics.a2cares.app.wizard.model.MapaBarriosPage;
import ni.org.ics.a2cares.app.wizard.model.Page;
import ni.org.ics.a2cares.app.wizard.ui.BarriosMapFragment;
import ni.org.ics.a2cares.app.wizard.ui.PageFragmentCallbacks;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;

import android.support.v4.app.FragmentActivity;

/**
 * Created by Everts Morales on 28/07/2023.
 */
public class NuevoControlAsistenciaFragment extends Fragment {
    private static final String TAG = "NuevoControlAsistenciaFragment";
    //base de datos
    private EstudioDBAdapter estudiosAdapter;
    private Participante mParticipante;
    private List<MessageResource> mCatTipoOrden;
    private List<MessageResource> mCatConsulta;
    private List<MessageResource> mCatCategoria;
    private List<MessageResource> mCatTipoMuestra;
    protected static final int PART_CAPTURE = 2;
    private ControlAsistencia nuevoControlAsistencia;

    private String fechaAsistencia;
    private String horaAsistencia;
    private Double latitud;
    private Double longitud;
    private String fis;
    private String fif;
    private String categoria; //A,B,C
    private String consulta; //Inicial, Seguimiento, Convaleciente
    private String tipoMuestra; //Aguda, Convaleciente
    private String observacion;
    private AbstractWizardModel mWizardModel;

    protected static final int DATE_MX = 101;
    protected static final int DATE_FIS = 102;
    protected static final int DATE_FIF = 103;
    protected static final int TIME_MX = 103;

    private int horaDefectoTimer = 7;
    private int minutoDefectoTimer = 0;

    final Calendar calendar = Calendar.getInstance();

    //Para el id
    private DeviceInfo infoMovil;
    private String username;
    private SharedPreferences settings;

    private View rootView;
    //Widgets en el View
    private Button mSaveView;
    private TextView mTitleView;
    private TextView mParticipanteView;
    private EditText mNameView;
    private EditText mInputFechaAsistencia;
    private EditText mInputHoraAsistencia;
    private EditText logintudP;
    private EditText latitudP;
    private EditText mInputObservacion;
    private EditText mInputVolumen;

    private ImageButton mImageButtonFis;
    private ImageButton mImageButtonFechaControlA;
    private ImageButton mImageButtonFif;
    protected ImageButton mImageButtonHoraControlA;

    private Spinner spinConsulta;
    private Spinner spinCategoria;
    private Spinner spinTipoMuestra;
    private Spinner spinTipoTubo;
    private GoogleApiClient googleApiClient;
    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private Page mPage;
    private ControlAsistenciaFormLabels  labels;
    protected static final String ARG_KEY = "key";

    private String horaentrada;
    private String horasalida;

    Bundle datos = new Bundle();


    public static NuevoControlAsistenciaFragment create(String key) {
        NuevoControlAsistenciaFragment fragment = new NuevoControlAsistenciaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        NuevoControlAsistenciaFragment f = new NuevoControlAsistenciaFragment();
        f.setArguments(args);

        return f;


    }

    public NuevoControlAsistenciaFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String mPass = ((MyIcsApplication) this.getActivity().getApplication()).getPassApp();
        estudiosAdapter = new EstudioDBAdapter(this.getActivity(),mPass,false,false);
        //mParticipante = (Participante) getActivity().getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);

        infoMovil = new DeviceInfo(getActivity());
        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        username = settings.getString(PreferencesActivity.KEY_USERNAME,null);


        long ahora = System.currentTimeMillis();
        Date fecha = new Date(ahora);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
        String salida = df.format(fecha);

     /*   Bundle args = getArguments();

        Intent intent = new Intent(getActivity().getApplicationContext(),
                NuevoControlAsistenciaGPSActivity.class);
        MapaBarriosPage page = (MapaBarriosPage) mPage;
       // intent.putExtra(Constants.UBICACION, page.getmUnidadSalud());
        startActivityForResult(intent, PART_CAPTURE);*/


      //  new FetchCatalogosTask().execute();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent intent) {
        if (requestCode == PART_CAPTURE && intent != null) {
            String codigo = intent.getStringExtra("CODE_RESULT");
            //mEditTextInput.setText(codigo);
            mPage.getData().putString(Page.SIMPLE_DATA_KEY,codigo);
            mPage.notifyDataChanged();
        }
        super.onActivityResult(requestCode, resultCode, intent);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_nuevo_control_asistencia, container, false);
        mTitleView =  ((TextView) rootView.findViewById(android.R.id.title));
        mParticipanteView =  ((TextView) rootView.findViewById(android.R.id.primary));
       // mParticipanteView.setText(mParticipante.getCodigoNombreCompleto());
        mParticipanteView.setText(settings.getString(PreferencesActivity.KEY_USERNAME,null));
        //mNameView.setText(R.string.usuarioa2cares);

      /*  spinConsulta = (Spinner) rootView.findViewById(R.id.spinConsulta);
        spinCategoria = (Spinner) rootView.findViewById(R.id.spinCategoria);
        spinTipoMuestra = (Spinner) rootView.findViewById(R.id.spinTipoMuestra);
        spinTipoTubo = (Spinner) rootView.findViewById(R.id.spinTipoTubo);

        mImageButtonFis = (ImageButton) rootView.findViewById(R.id.imageButtonFis);*/
        mImageButtonFechaControlA = (ImageButton) rootView.findViewById(R.id.imageButtonFechaMx);
    //    mImageButtonFif = (ImageButton) rootView.findViewById(R.id.imageButtonFif);
        mImageButtonHoraControlA = (ImageButton) rootView.findViewById(R.id.imageButtonHoraMx);



        mInputFechaAsistencia = (EditText) rootView.findViewById(R.id.inputFechaAsistencia);
        createDateDialog(DATE_MX);
        mInputHoraAsistencia = (EditText) rootView.findViewById(R.id.inputHoraAsistencia);
        createTimeDialog(TIME_MX);

       /* mInputFIS = (EditText) rootView.findViewById(R.id.inputFIS);
        mInputFIF = (EditText) rootView.findViewById(R.id.inputFIF);
        mInputObservacion = (EditText) rootView.findViewById(R.id.inputObservacion);
        mInputVolumen = (EditText) rootView.findViewById(R.id.inputVolumen);*/
              //Guarda las respuestas en un bundle
      /*

        String[] data = coordenadas.replace("(","").replace(")","").split(",");
        latitud = (Double.valueOf(data[0].trim()));
        longitud = (Double.valueOf(data[1].trim()));*/

      /*  mImageButtonFechaControlA.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDateDialog(DATE_MX);
            }
        });

        mImageButtonHoraControlA.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createTimeDialog(TIME_MX);
            }
        });*/




        mSaveView = (Button) getActivity().findViewById(R.id.save_button);
        mSaveView.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                try {
                    if(validarEntrada()){
                        new SaveDataTask().execute();
                    }
                } catch (Exception e) {
                    showToast(getActivity().getString(R.string.procesando_espere));
                 //   e.printStackTrace();
                }
            }
        });




        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void createDateDialog(int dialog) {
        final DatePickerDialog dpD;
        DateMidnight minDate;
        DateMidnight maxDate;
        switch(dialog){
            case DATE_MX:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        fechaAsistencia = (picker != null) ? String.valueOf(dayOfMonth<10? "0"+dayOfMonth:dayOfMonth)+"/"+String.valueOf((monthOfYear+1)<10? "0"+(monthOfYear+1):(monthOfYear+1))+"/"+String.valueOf(year) : null;
                        mInputFechaAsistencia.setText(fechaAsistencia);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String salida = df.format(maxDate.getMillis());
                mInputFechaAsistencia.setText(salida);
              //  mInputFechaAsistencia.setText(dpD);
               // dpD.show();
                break;
            case DATE_FIS:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        fis = (picker != null) ? String.valueOf(dayOfMonth<10? "0"+dayOfMonth:dayOfMonth)+"/"+String.valueOf((monthOfYear+1)<10? "0"+(monthOfYear+1):(monthOfYear+1))+"/"+String.valueOf(year) : null;
                    //    mInputFIS.setText(fis);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                maxDate = new DateMidnight(new Date());

                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
              //  dpD.show();
                break;
            case DATE_FIF:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        fif = (picker != null) ? String.valueOf(dayOfMonth<10? "0"+dayOfMonth:dayOfMonth)+"/"+String.valueOf((monthOfYear+1)<10? "0"+(monthOfYear+1):(monthOfYear+1))+"/"+String.valueOf(year) : null;
                      //  mInputFIF.setText(fif);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                maxDate = new DateMidnight(new Date());

                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
              //  dpD.show();
                break;
            default:
                break;
        }
    }

    private void createTimeDialog(int dialog) {
        switch(dialog){
        case TIME_MX:
        final TimePickerDialog tmD = new TimePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker picker, int hourOfDay, int minute) {
                horaAsistencia = (picker != null) ? String.valueOf(hourOfDay<10? "0"+hourOfDay:hourOfDay)+":"+String.valueOf(minute<10?"0"+minute:minute) : null;
                mInputHoraAsistencia.setText(horaAsistencia);
                horaDefectoTimer = hourOfDay;
                minutoDefectoTimer = minute;
            }
        }, horaDefectoTimer, minutoDefectoTimer,true);

            Calendar calendar = Calendar.getInstance();
            int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
            int hour12hrs = calendar.get(Calendar.HOUR);
            int minutes = calendar.get(Calendar.MINUTE);
            int seconds = calendar.get(Calendar.SECOND);

            String AM_PM = "";
            if (hour24hrs == 12)
            {
                hour12hrs = 12;
            }
            if (hour24hrs < 12) {
                AM_PM = "a.m.";
                if (minutes < 10) {
                    horaentrada = (hour12hrs + ":0" + minutes + ":" + seconds + " " + AM_PM);
                }else {
                    horaentrada = (hour12hrs + ":" + minutes + ":" + seconds + " " + AM_PM);
                }
            } else {
                AM_PM = "p.m.";
                if (minutes < 10) {
                    horasalida = (hour12hrs + ":0" + minutes + ":" + seconds + " " + AM_PM);
                }else {
                    horasalida = (hour12hrs + ":" + minutes + ":" + seconds + " " + AM_PM);
                }
            }
            if (minutes < 10)
            {
                mInputHoraAsistencia.setText(hour12hrs + ":0" + minutes + ":" + seconds + " " + AM_PM);
            }else {
                mInputHoraAsistencia.setText(hour12hrs + ":" + minutes + ":" + seconds + " " + AM_PM);
            }
       // tmD.show();
            default:
                break;
        }

      /*  Intent i = new Intent(getActivity().getApplicationContext(),
                CoordenadasOnlyActivity.class);
        i.putExtra(Constants.COD_PARTICIPANTE, "0000");
        i.putExtra(Constants.MENU_ENTO, 0);
        MapaBarriosPage page = (MapaBarriosPage) mPage;
        startActivityForResult(i, PART_CAPTURE);*/
    }
    private boolean validarEntrada() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dFecha = null;
        Date dFIS = null;
        Date dFIF = null;
        String strDate = mInputFechaAsistencia.getText().toString();
        Date date_asistencia = null;
        try {
            date_asistencia = new Date(formatter.parse(strDate).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
       Integer numero = (int) (Math.random() * 100) + 1;
        String latitudS = ((MyIcsApplication) this.getActivity().getApplication()).getLatitudapp();
        String longitudS =  ((MyIcsApplication) this.getActivity().getApplication()).getLongitudapp();
        nuevoControlAsistencia = new ControlAsistencia();
            nuevoControlAsistencia.setFechaasistencia(date_asistencia);
        UUID deviceUuid = new UUID(infoMovil.getDeviceId().hashCode(), new Date().hashCode());

        nuevoControlAsistencia.setId(numero);
       // nuevoControlAsistencia.setUsuarioregistro(username) ;
        nuevoControlAsistencia.setHoraentrada(horaentrada);
        nuevoControlAsistencia.setHorasalida(horasalida) ;
        nuevoControlAsistencia.setLatitud(Double.valueOf(latitudS));
        nuevoControlAsistencia.setLongitud(Double.valueOf(longitudS));

        //metadata
        nuevoControlAsistencia.setRecordDate(new Date());
        nuevoControlAsistencia.setRecordUser(username);
        nuevoControlAsistencia.setDeviceid(infoMovil.getDeviceId());
        nuevoControlAsistencia.setEstado(Constants.STATUS_NOT_SUBMITTED);
        nuevoControlAsistencia.setPasive(Constants.STATUS_NOT_PASIVE);


        return true;
    }

    @Override
    public void onActivityCreated(Bundle savedState){
        super.onActivityCreated(savedState);
         latitudP= (EditText) getView().findViewById(R.id.latitud);

        // latitud = Double.valueOf(latitudP.getText().toString());
        //String cadena = getActivity().;
        // ahora puedes trabajar con el dato leido de un campo de la actividad

    }

    private class FetchCatalogosTask extends AsyncTask<String, Void, String> {
        private ProgressDialog nDialog;
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.setMessage(getActivity().getString(R.string.pleasewait));
            nDialog.setTitle(getActivity().getString(R.string.loading));
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected String doInBackground(String... values) {
            try {
                estudiosAdapter.open();
                mCatCategoria = estudiosAdapter.getMessageResources(MainDBConstants.catRoot + "='CAT_CATEGORIA'", MainDBConstants.order);
                mCatTipoOrden = estudiosAdapter.getMessageResources(MainDBConstants.catRoot + "='CAT_TIPO_ORDEN'", MainDBConstants.order);
                mCatTipoMuestra = estudiosAdapter.getMessageResources(MainDBConstants.catRoot + "='CAT_FASE_MX'", MainDBConstants.order);
                mCatConsulta = estudiosAdapter.getMessageResources(MainDBConstants.catRoot + "='CAT_TIPO_CONSULTA'", MainDBConstants.order);
                estudiosAdapter.close();
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return "error";
            }
            return "exito";
        }

        protected void onPostExecute(String resultado) {
            // after the request completes, hide the progress indicator
            nDialog.dismiss();

            mCatCategoria.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatCategoria);

            mCatTipoOrden.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatTipoOrden);

            mCatTipoMuestra.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatTipoMuestra);

            mCatConsulta.add(new MessageResource("",0,getActivity().getString(R.string.select)));
            Collections.sort(mCatConsulta);

            ArrayAdapter<MessageResource> dataAdapterCate = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatCategoria);
            dataAdapterCate.setDropDownViewResource(R.layout.spinner_item);

            ArrayAdapter<MessageResource> dataAdapterTipoOrden = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatTipoOrden);
            dataAdapterTipoOrden.setDropDownViewResource(R.layout.spinner_item);

            ArrayAdapter<MessageResource> dataAdapterTipoMuestra = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatTipoMuestra);
            dataAdapterTipoMuestra.setDropDownViewResource(R.layout.spinner_item);

            ArrayAdapter<MessageResource> dataAdapterConsulta = new ArrayAdapter<MessageResource>(getActivity(), android.R.layout.simple_spinner_item, mCatConsulta);
            dataAdapterConsulta.setDropDownViewResource(R.layout.spinner_item);

            spinCategoria.setAdapter(dataAdapterCate);
            spinTipoTubo.setAdapter(dataAdapterTipoOrden);
            spinConsulta.setAdapter(dataAdapterConsulta);
            spinTipoMuestra.setAdapter(dataAdapterTipoMuestra);
        }
    }


    private class SaveDataTask extends AsyncTask<String, Void, String> {
        private ProgressDialog nDialog;
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.setMessage(getActivity().getString(R.string.pleasewait));
            nDialog.setTitle(getActivity().getString(R.string.loading));
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(false);
            nDialog.show();
            showToast(getActivity().getString(R.string.enter_guardado));
        }

        @Override
        protected String doInBackground(String... values) {
            try {
                estudiosAdapter.open();
                estudiosAdapter.crearControlAsistencia(nuevoControlAsistencia);
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
            nDialog.dismiss();
            Bundle arguments = new Bundle();
            Intent i = new Intent(getActivity(),
                    MainActivity.class);
           // arguments.putSerializable(Constants.PARTICIPANTE, mParticipante);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtras(arguments);
            startActivity(i);
            getActivity().finish();
        }
    }

    public void showToast(String mensaje){
        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.toast,
                (ViewGroup) rootView.findViewById(R.id.toast_layout_root));

        ImageView imageView = (ImageView) layout.findViewById(R.id.image);
        imageView.setImageResource(R.mipmap.ic_help);

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(mensaje);

        Toast toast = new Toast(getContext());
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

}
