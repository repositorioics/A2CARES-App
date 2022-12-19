package ni.org.ics.a2cares.app.ui.fragments.enterdata;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import org.joda.time.DateMidnight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.bluetooth.common.logger.Log;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.domain.medico.OrdenLaboratorio;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.preferences.PreferencesActivity;
import ni.org.ics.a2cares.app.ui.activities.list.ListaOrdenesLaboratorioActivity;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.utils.DeviceInfo;

/**
 * Created by Miguel Salinas on 26/1/2022.
 */
public class NuevaOrdenLaboratorioFragment  extends Fragment {
    private static final String TAG = "NuevaOrdenLaboratorioFragment";
    //base de datos
    private EstudioDBAdapter estudiosAdapter;
    private Participante mParticipante;
    private List<MessageResource> mCatTipoOrden;
    private List<MessageResource> mCatConsulta;
    private List<MessageResource> mCatCategoria;
    private List<MessageResource> mCatTipoMuestra;

    private OrdenLaboratorio nuevaOrdenLaboratorio;

    private String fechaOrden;
    private String tipoOrden; //Serogologia, BHC, etc
    private String fis;
    private String fif;
    private String categoria; //A,B,C
    private String consulta; //Inicial, Seguimiento, Convaleciente
    private String tipoMuestra; //Aguda, Convaleciente
    private String observacion;

    protected static final int DATE_ORDEN = 101;
    protected static final int DATE_FIS = 102;
    protected static final int DATE_FIF = 103;

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
    private EditText mInputFechaOrden;
    private EditText mInputFIS;
    private EditText mInputFIF;
    private EditText mInputObservacion;

    private ImageButton mImageButtonFis;
    private ImageButton mImageButtonFechaOrden;
    private ImageButton mImageButtonFif;

    private Spinner spinConsulta;
    private Spinner spinCategoria;
    private Spinner spinTipoMuestra;
    private Spinner spinTipoOrden;

    public static NuevaOrdenLaboratorioFragment create() {
        NuevaOrdenLaboratorioFragment fragment = new NuevaOrdenLaboratorioFragment();
        return fragment;
    }

    public NuevaOrdenLaboratorioFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mPass = ((MyIcsApplication) this.getActivity().getApplication()).getPassApp();
        estudiosAdapter = new EstudioDBAdapter(this.getActivity(),mPass,false,false);
        mParticipante = (Participante) getActivity().getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
        infoMovil = new DeviceInfo(getActivity());
        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        username = settings.getString(PreferencesActivity.KEY_USERNAME,null);

        new FetchCatalogosTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_nueva_orden_lab, container, false);
        mTitleView =  ((TextView) rootView.findViewById(android.R.id.title));
        mParticipanteView =  ((TextView) rootView.findViewById(android.R.id.primary));
        mParticipanteView.setText(mParticipante.getCodigoNombreCompleto());

        spinConsulta = (Spinner) rootView.findViewById(R.id.spinConsulta);
        spinCategoria = (Spinner) rootView.findViewById(R.id.spinCategoria);
        spinTipoMuestra = (Spinner) rootView.findViewById(R.id.spinTipoMuestra);
        spinTipoOrden = (Spinner) rootView.findViewById(R.id.spinTipoOrden);

        mImageButtonFis = (ImageButton) rootView.findViewById(R.id.imageButtonFis);
        mImageButtonFechaOrden = (ImageButton) rootView.findViewById(R.id.imageButtonFechaOrden);
        mImageButtonFif = (ImageButton) rootView.findViewById(R.id.imageButtonFif);

        mInputFechaOrden = (EditText) rootView.findViewById(R.id.inputFechaOrden);
        mInputFIS = (EditText) rootView.findViewById(R.id.inputFIS);
        mInputFIF = (EditText) rootView.findViewById(R.id.inputFIF);
        mInputObservacion = (EditText) rootView.findViewById(R.id.inputObservacion);

        mImageButtonFechaOrden.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDateDialog(DATE_ORDEN);
            }
        });

        mImageButtonFis.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDateDialog(DATE_FIS);
            }
        });

        mImageButtonFif.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                createDateDialog(DATE_FIF);
            }
        });

        spinConsulta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                consulta = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                categoria = mr.getCatKey();
                if (categoria.equalsIgnoreCase("D")){
                    //aqui se hacen invisible el FIF

                    mInputFIF.setVisibility(View.INVISIBLE);
                    mImageButtonFif.setVisibility(View.INVISIBLE);
                } else {
                    //aqui se hacen invinvisible el FIF
                    mInputFIF.setVisibility(View.VISIBLE);
                    mImageButtonFif.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinTipoMuestra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                tipoMuestra = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinTipoOrden.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                MessageResource mr = (MessageResource) spinner.getSelectedItem();
                tipoOrden = mr.getCatKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        mInputObservacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                observacion = mInputObservacion.getText().toString();
            }
        });

        mSaveView = (Button) getActivity().findViewById(R.id.save_button);
        mSaveView.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                try {
                    if(validarEntrada()){
                        new SaveDataTask().execute();
                    }
                } catch (Exception e) {
                    showToast(e.toString());
                    e.printStackTrace();
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
            case DATE_ORDEN:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        fechaOrden = (picker != null) ? String.valueOf(dayOfMonth<10? "0"+dayOfMonth:dayOfMonth)+"/"+String.valueOf((monthOfYear+1)<10? "0"+(monthOfYear+1):(monthOfYear+1))+"/"+String.valueOf(year) : null;
                        mInputFechaOrden.setText(fechaOrden);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                if (mParticipante.getFechaNac()!=null){
                    minDate = new DateMidnight(mParticipante.getFechaNac());
                }else {
                    minDate = new DateMidnight(new Date());
                }
                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMinDate(minDate.getMillis());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                dpD.show();
                break;
            case DATE_FIS:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        fis = (picker != null) ? String.valueOf(dayOfMonth<10? "0"+dayOfMonth:dayOfMonth)+"/"+String.valueOf((monthOfYear+1)<10? "0"+(monthOfYear+1):(monthOfYear+1))+"/"+String.valueOf(year) : null;
                        mInputFIS.setText(fis);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                if (mParticipante.getFechaNac()!=null){
                    minDate = new DateMidnight(mParticipante.getFechaNac());
                }else {
                    minDate = new DateMidnight(new Date());
                }
                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMinDate(minDate.getMillis());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                dpD.show();
                break;
            case DATE_FIF:
                dpD = new DatePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                        fif = (picker != null) ? String.valueOf(dayOfMonth<10? "0"+dayOfMonth:dayOfMonth)+"/"+String.valueOf((monthOfYear+1)<10? "0"+(monthOfYear+1):(monthOfYear+1))+"/"+String.valueOf(year) : null;
                            mInputFIF.setText(fif);

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                if (mParticipante.getFechaNac()!=null){
                    minDate = new DateMidnight(mParticipante.getFechaNac());
                }else {
                    minDate = new DateMidnight(new Date());
                }
                maxDate = new DateMidnight(new Date());
                dpD.getDatePicker().setMinDate(minDate.getMillis());
                dpD.getDatePicker().setMaxDate(maxDate.getMillis());
                dpD.show();
                break;
            default:
                break;
        }
    }

    private boolean validarEntrada() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dFechaOrden = null;
        Date dFIS = null;
        Date dFIF = null;
        try {
            if (fechaOrden != null && !fechaOrden.isEmpty())
                dFechaOrden = formatter.parse(fechaOrden);
            if (fis != null && !fis.isEmpty())
                dFIS = formatter.parse(fis);
            if (fif != null && !fif.isEmpty())
                dFIF = formatter.parse(fif);

        } catch (ParseException e) {
            showToast(e.toString() );
            e.printStackTrace();
            return false;
        }
        if (fechaOrden == null || fechaOrden.equals("")){
            //showToast(getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fecha_order)));
            showToast(getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fecha_order)));
            return false;
        } else if (tipoOrden == null || tipoOrden.equals("")){
            showToast(getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.tipoOrden)));
            return false;
        } else if (fis == null || fis.equals("")){
            showToast(getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.fis)));
            return false;
        } else if  (categoria.equalsIgnoreCase("D") != true && ( fif == null || fif.equals(""))){
                showToast(getActivity().getString(R.string.wrongSelect, getActivity().getString(R.string.fif)));
                return false;
        } else if (consulta == null || consulta.equals("")){
            showToast(getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.tipoConsulta)));
            return false;
        } else if (categoria == null || categoria.equals("")){
            showToast(getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.categoria)));
            return false;
        } else if (tipoMuestra == null || tipoMuestra.equals("")){
            showToast(getActivity().getString(R.string.wrongSelect,getActivity().getString(R.string.tipoMuestra)));
            return false;
        } else if (dFIS.after(dFechaOrden)){//si la fecha de sintonmas es posterior a la fecha de la orden no permitir registro
            showToast(getActivity().getString(R.string.wrong_fis));
            return false;
        } else if ( dFIF != null && dFIF.after(dFechaOrden)){//si la fecha de sintonmas es posterior a la fecha de la orden no permitir registro
            showToast(getActivity().getString(R.string.wrong_fif));
            return false;
        } else if (dFIF != null && dFIS.after(dFIF)){//si la fecha de sintonmas es posterior a la fif no permitir registro
            showToast(getActivity().getString(R.string.wrong_fis_3));
            return false;
        } else {
            nuevaOrdenLaboratorio = new OrdenLaboratorio();
            nuevaOrdenLaboratorio.setIdOrden(infoMovil.getId());
            nuevaOrdenLaboratorio.setParticipante(mParticipante);
            nuevaOrdenLaboratorio.setFechaOrden(dFechaOrden);
            nuevaOrdenLaboratorio.setFis(dFIS);
            nuevaOrdenLaboratorio.setFif(dFIF);
            nuevaOrdenLaboratorio.setConsulta(consulta);
            nuevaOrdenLaboratorio.setTipoMuestra(tipoMuestra);
            nuevaOrdenLaboratorio.setTipoOrden(tipoOrden);
            nuevaOrdenLaboratorio.setCategoria(categoria);
            nuevaOrdenLaboratorio.setObservacion(observacion);
            nuevaOrdenLaboratorio.setEstudiosAct(Constants.ESTUDIO);

            //metadata
            nuevaOrdenLaboratorio.setRecordDate(new Date());
            nuevaOrdenLaboratorio.setRecordUser(username);
            nuevaOrdenLaboratorio.setDeviceid(infoMovil.getDeviceId());
            nuevaOrdenLaboratorio.setEstado(Constants.STATUS_NOT_SUBMITTED);
            nuevaOrdenLaboratorio.setPasive(Constants.STATUS_NOT_PASIVE);
        }
        return true;
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
            spinTipoOrden.setAdapter(dataAdapterTipoOrden);
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
        }

        @Override
        protected String doInBackground(String... values) {
            try {
                estudiosAdapter.open();
                estudiosAdapter.crearOrdenLaboratorio(nuevaOrdenLaboratorio);
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
                    ListaOrdenesLaboratorioActivity.class);
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
