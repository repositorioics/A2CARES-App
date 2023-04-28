package ni.org.ics.a2cares.app.ui.activities.cambioDomicilio;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import ni.org.ics.a2cares.app.AbstractAsyncActivity;
import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.Barrio;
import ni.org.ics.a2cares.app.domain.core.CambioDomicilio;
import ni.org.ics.a2cares.app.domain.core.Casa;
import ni.org.ics.a2cares.app.domain.core.DatosCoordenadas;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.domain.core.TelefonoContacto;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.domain.users.UserPermissions;
import ni.org.ics.a2cares.app.preferences.PreferencesActivity;
import ni.org.ics.a2cares.app.ui.activities.enterdata.NuevoTamizajeActivity;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuParticipanteActivity;
import ni.org.ics.a2cares.app.ui.forms.CambioDomicilioForm;
import ni.org.ics.a2cares.app.ui.forms.CambioDomicilioFormLabels;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.utils.DeviceInfo;
import ni.org.ics.a2cares.app.utils.FileUtils;
import ni.org.ics.a2cares.app.utils.StringUtil;
import ni.org.ics.a2cares.app.wizard.model.AbstractWizardModel;
import ni.org.ics.a2cares.app.wizard.model.BarcodePage;
import ni.org.ics.a2cares.app.wizard.model.DatePage;
import ni.org.ics.a2cares.app.wizard.model.LabelPage;
import ni.org.ics.a2cares.app.wizard.model.MapaBarriosPage;
import ni.org.ics.a2cares.app.wizard.model.ModelCallbacks;
import ni.org.ics.a2cares.app.wizard.model.MultipleFixedChoicePage;
import ni.org.ics.a2cares.app.wizard.model.NumberPage;
import ni.org.ics.a2cares.app.wizard.model.Page;
import ni.org.ics.a2cares.app.wizard.model.SelectCasaPage;
import ni.org.ics.a2cares.app.wizard.model.SingleFixedChoicePage;
import ni.org.ics.a2cares.app.wizard.model.TextPage;
import ni.org.ics.a2cares.app.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.a2cares.app.wizard.ui.ReviewFragment;
import ni.org.ics.a2cares.app.wizard.ui.StepPagerStrip;

public class CambioDomicilioParticipanteActivity extends AbstractAsyncActivity implements
        PageFragmentCallbacks,
        ReviewFragment.Callbacks,
        ModelCallbacks {
    private ViewPager mPager;
    private MyPagerAdapter mPagerAdapter;
    private boolean mEditingAfterReview;
    private AbstractWizardModel mWizardModel;
    private boolean mConsumePageSelectedEvent;
    private Button mNextButton;
    private Button mPrevButton;
    private List<Page> mCurrentPageSequence;
    private StepPagerStrip mStepPagerStrip;
    private CambioDomicilioFormLabels labels = new CambioDomicilioFormLabels();
    int index = 0;
    private EstudioDBAdapter estudiosAdapter;
    private DeviceInfo infoMovil;
    //private GPSTracker gps;
    private String username;
    private SharedPreferences settings;
    private static final int EXIT = 1;
    private AlertDialog alertDialog;
    private boolean notificarCambios = true;

    private Participante participante;
    private String[] participantesCasas;
    private Casa casa;
    private static UserPermissions mUser = new UserPermissions();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!FileUtils.storageReady()) {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.error, getString(R.string.storage_error)),Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
        setContentView(R.layout.activity_data_enter);

        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);
        infoMovil = new DeviceInfo(CambioDomicilioParticipanteActivity.this);
        //gps = new GPSTracker(NewTamizajeActivity.this);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(),mPass,false,false);

        participante = (Participante) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);

        mWizardModel = new CambioDomicilioForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        mWizardModel.registerListener(this);

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        mStepPagerStrip = (StepPagerStrip) findViewById(R.id.strip);
        mStepPagerStrip.setOnPageSelectedListener(new StepPagerStrip.OnPageSelectedListener() {
            @Override
            public void onPageStripSelected(int position) {
                position = Math.min(mPagerAdapter.getCount() - 1, position);
                if (mPager.getCurrentItem() != position) {
                    mPager.setCurrentItem(position);
                }
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mPrevButton = (Button) findViewById(R.id.prev_button);

        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mStepPagerStrip.setCurrentPage(position);

                if (mConsumePageSelectedEvent) {
                    mConsumePageSelectedEvent = false;
                    return;
                }

                mEditingAfterReview = false;
                updateBottomBar();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPager.getCurrentItem() == mCurrentPageSequence.size()) {
                    //MyAlertDialogFragment dg = new MyAlertDialogFragment();
                    MyAlertDialogFragment.newInstance(R.string.submit_confirm_message).show(getSupportFragmentManager(), "guardar_dialog");
                } else {
                    if (mEditingAfterReview) {
                        mPager.setCurrentItem(mPagerAdapter.getCount() - 1);
                    } else {
                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    }
                }
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        });

        Page pageTmp = mWizardModel.findByKey(labels.getCoordenadas());
        if (pageTmp!=null) pageTmp.setCodigoParticipante("00000");

        onPageTreeChanged();
        updateBottomBar();

        new FetchDataTask().execute();
    }

    @Override
    public AbstractWizardModel onGetModel() {
        return mWizardModel;
    }

    @Override
    public void onEditScreenAfterReview(String key) {
        for (int i = mCurrentPageSequence.size() - 1; i >= 0; i--) {
            if (mCurrentPageSequence.get(i).getKey().equals(key)) {
                mConsumePageSelectedEvent = true;
                mEditingAfterReview = true;
                mPager.setCurrentItem(i);
                updateBottomBar();
                break;
            }
        }
    }

    @Override
    public void onPageDataChanged(Page page) {
        updateModel(page);
        updateConstrains();
        if (recalculateCutOffPage()) {
            if (notificarCambios) mPagerAdapter.notifyDataSetChanged();
            updateBottomBar();
        }
        notificarCambios = true;
    }

    @Override
    public void onPageTreeChanged() {
        mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
        mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 = review step
        mPagerAdapter.notifyDataSetChanged();
        updateBottomBar();
    }

    @Override
    public Page onGetPage(String key) {
        return mWizardModel.findByKey(key);
    }

    @Override
    public void onBackPressed (){
        createDialog(EXIT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWizardModel.unregisterListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("model", mWizardModel.save());
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
            default:
                break;
        }
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void updateBottomBar() {
        int position = mPager.getCurrentItem();
        if (position == mCurrentPageSequence.size()) {
            mNextButton.setText(R.string.finish);
            mNextButton.setBackgroundResource(R.drawable.finish_background);
            mNextButton.setTextAppearance(this, R.style.TextAppearanceFinish);
        } else {
            mNextButton.setText(mEditingAfterReview
                    ? R.string.review
                    : R.string.next);
            mNextButton.setBackgroundResource(R.drawable.selectable_item_background);
            TypedValue v = new TypedValue();
            getTheme().resolveAttribute(android.R.attr.textAppearanceButton, v, true);
            mNextButton.setTextAppearance(this, v.resourceId);
            mNextButton.setEnabled(position != mPagerAdapter.getCutOffPage());
        }
        TypedValue v = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.textAppearanceButton, v, true);
        mPrevButton.setTextAppearance(this, v.resourceId);
        mPrevButton.setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
    }

    private boolean recalculateCutOffPage() {
        // Cut off the pager adapter at first required page that isn't completed
        int cutOffPage = mCurrentPageSequence.size() + 1;
        for (int i = 0; i < mCurrentPageSequence.size(); i++) {
            Page page = mCurrentPageSequence.get(i);
            String clase = page.getClass().toString();
            if (page.isRequired() && !page.isCompleted()) {
                cutOffPage = i;
                break;
            }
            if (!page.getData().isEmpty() && clase.equals("class ni.org.ics.a2cares.app.wizard.model.NumberPage")) {
                NumberPage np = (NumberPage) page;
                String valor = np.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                if((np.ismValRange() && (np.getmGreaterOrEqualsThan() > Double.valueOf(valor) || np.getmLowerOrEqualsThan() < Double.valueOf(valor)))
                        || (np.ismValPattern() && !valor.matches(np.getmPattern()))){
                    cutOffPage = i;
                    break;
                }
            }
            if (!page.getData().isEmpty() && clase.equals("class ni.org.ics.a2cares.app.wizard.model.TextPage")) {
                TextPage tp = (TextPage) page;
                if (tp.ismValPattern()) {
                    String valor = tp.getData().getString(TextPage.SIMPLE_DATA_KEY);
                    if(!valor.matches(tp.getmPattern())){
                        cutOffPage = i;
                        break;
                    }
                }
            }
            if (!page.getData().isEmpty() && clase.equals("class ni.org.ics.a2cares.app.wizard.model.BarcodePage")) {
                BarcodePage bp = (BarcodePage) page;
                String valor = "";
                if (bp.ismValRange() || bp.ismValPattern()) {
                    valor = bp.getData().getString(TextPage.SIMPLE_DATA_KEY);
                    if ((bp.ismValRange() && (bp.getmGreaterOrEqualsThan() > Double.valueOf(valor) || bp.getmLowerOrEqualsThan() < Double.valueOf(valor)))
                            || (bp.ismValPattern() && !valor.matches(bp.getmPattern()))) {
                        cutOffPage = i;
                        break;
                    }
                }
            }

            /*if (!page.getData().isEmpty() && clase.equals("class ni.org.ics.a2cares.app.wizard.model.MultipleFixedChoicePage")) {
                ArrayList<String> test = page.getData().getStringArrayList(Page.SIMPLE_DATA_KEY);
                //validación solo para la pregunta de verificación del tutor
                if (page.getTitle().equalsIgnoreCase(this.getString(R.string.cd_participante)) && test.size() != totalVerifTutor) {
                    cutOffPage = i;
                    break;
                }
            }*/
        }

        if (mPagerAdapter.getCutOffPage() != cutOffPage) {
            mPagerAdapter.setCutOffPage(cutOffPage);
            return true;
        }

        return false;
    }

    public void updateConstrains(){ }

    public void updateModel(Page page) {
        try {
            boolean visible = false;
            if(page.getTitle().equals(labels.getCambioDomicilio())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCodigoNuevaCasaCohorte()), visible);

                changeStatus(mWizardModel.findByKey(labels.getMoverOtroParticipante()), visible);

                MultipleFixedChoicePage pagetmp = (MultipleFixedChoicePage)mWizardModel.findByKey(labels.getMoverOtroParticipante());
                pagetmp.setChoices(participantesCasas);
                //totalVerifTutor = visible?catVerifTutNoAlf.length:catVerifTutAlf.length;

                changeStatus(mWizardModel.findByKey(labels.getNombre1JefeFamilia()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNombre2JefeFamilia()), visible);
                changeStatus(mWizardModel.findByKey(labels.getApellido1JefeFamilia()), visible);
                changeStatus(mWizardModel.findByKey(labels.getApellido2JefeFamilia()), visible);
                changeStatus(mWizardModel.findByKey(labels.getBarrio()), visible);
                changeStatus(mWizardModel.findByKey(labels.getDireccion()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCoordenadas()), visible);

                changeStatus(mWizardModel.findByKey(labels.getTieneTelefonoCel()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTieneTelefonoConv()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNumTelefono3()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono3()), visible);

                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getBarrio())) {
                MapaBarriosPage mapaPage = (MapaBarriosPage)mWizardModel.findByKey(labels.getCoordenadas());
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).contains("Nejapa");
                if (visible) mapaPage.setmUnidadSalud(Constants.UBICACION_NJ);
                else mapaPage.setmUnidadSalud(Constants.UBICACION_CO);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getTieneTelefonoCel())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumTelefono1()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono1()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTieneOtroTelefonoCel()), visible);
                //changeStatus(mWizardModel.findByKey(labels.getNumTelefono2()), false);
                //changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono2()), false);
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getTieneTelefonoConv())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumTelefono3()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono3()), visible);
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getTieneOtroTelefonoCel())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono2()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNumTelefono2()), visible);
                onPageTreeChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        try {
            //Guarda las respuestas en un bundle
            Map<String, String> mapa = mWizardModel.getAnswers();
            Bundle datos = new Bundle();
            DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            for (Map.Entry<String, String> entry : mapa.entrySet()) {
                datos.putString(entry.getKey(), entry.getValue());
            }
            //Abre la base de datos
            String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
            estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(), mPass, false, false);
            estudiosAdapter.open();

            //Obtener datos del bundle
            String codigoNuevaCasaCohorte = datos.getString(this.getString(R.string.cd_codigoCasaCohorte));
            String nombre1JefeFamilia = datos.getString(this.getString(R.string.cd_nombre1JefeFamilia));
            String nombre2JefeFamilia = datos.getString(this.getString(R.string.cd_nombre2JefeFamilia));
            String apellido1JefeFamilia = datos.getString(this.getString(R.string.cd_apellido1JefeFamilia));
            String apellido2JefeFamilia = datos.getString(this.getString(R.string.cd_apellido2JefeFamilia));
            String barrio = datos.getString(this.getString(R.string.cd_barrio));
            String direccion = datos.getString(this.getString(R.string.cd_direccion));
            String coordenadas = datos.getString(this.getString(R.string.cd_coordenadas));

            String moverOtroParticipante = datos.getString(this.getString(R.string.cd_participante));

            String tieneTelefonoCel = datos.getString(this.getString(R.string.tieneTelefonoCel));
            String numTelefono1 = datos.getString(this.getString(R.string.numTelefono1));
            String operadoraTelefono1 = datos.getString(this.getString(R.string.operadoraTelefono1));
            String tieneOtroTelefonoCel = datos.getString(this.getString(R.string.tieneOtroTelefonoCel));
            String numTelefono2 = datos.getString(this.getString(R.string.numTelefono2));
            String operadoraTelefono2 = datos.getString(this.getString(R.string.operadoraTelefono2));
            String tieneTelefonoConv = datos.getString(this.getString(R.string.tieneTelefonoConv));
            String numTelefono3 = datos.getString(this.getString(R.string.numTelefono3));
            String operadoraTelefono3 = datos.getString(this.getString(R.string.operadoraTelefono3));

            String tipoTeleCel = Constants.TIPO_TEL_CEL;
            String tipoTeleConv = Constants.TIPO_TEL_CON;

            //validar si ya existe la casa
            casa = estudiosAdapter.getCasa(MainDBConstants.codigo + " = " +codigoNuevaCasaCohorte, null);
            if (casa != null && casa.getCodigo() != null) {
                //Solo registrar el cambio de domicilio a casa existente
                guardarCambioDomicilio( codigoNuevaCasaCohorte, nombre1JefeFamilia, nombre2JefeFamilia, apellido1JefeFamilia,
                        apellido2JefeFamilia, direccion, barrio, coordenadas, numTelefono1, operadoraTelefono1, numTelefono2,
                        operadoraTelefono2, numTelefono3, operadoraTelefono3, moverOtroParticipante);

            } else {
                //se creará casa
                casa = new Casa();
                casa.setCodigo(Integer.valueOf(codigoNuevaCasaCohorte));
                Barrio barrio1 = estudiosAdapter.getBarrio(MainDBConstants.nombre + "= '" + barrio + "'", null);
                casa.setBarrio(barrio1);
                //casaCohorte.setManzana(manzana);
                casa.setDireccion(direccion);
                casa.setNombre1JefeFamilia(nombre1JefeFamilia);
                casa.setNombre2JefeFamilia(nombre2JefeFamilia);
                casa.setApellido1JefeFamilia(apellido1JefeFamilia);
                casa.setApellido2JefeFamilia(apellido2JefeFamilia);
                casa.setRecordDate(new Date());
                casa.setRecordUser(username);
                casa.setDeviceid(infoMovil.getDeviceId());
                casa.setEstado('0');
                casa.setPasive('0');
                if (coordenadas!=null){
                    String[] data = coordenadas.replace("(","").replace(")","").split(",");
                    casa.setLatitud(Double.valueOf(data[0].trim()));
                    casa.setLongitud(Double.valueOf(data[1].trim()));                            }
                else{
                    casa.setLatitud(null);
                    casa.setLongitud(null);
                }
                estudiosAdapter.crearCasa(casa);

                //Registra los telefonos
                if (tieneValor(tieneTelefonoCel) && tieneTelefonoCel.equalsIgnoreCase(Constants.YES)) {
                    guardarTelefonoContacto(numTelefono1, tipoTeleCel, operadoraTelefono1, moverOtroParticipante, codigoNuevaCasaCohorte);
                }
                if (tieneValor(tieneOtroTelefonoCel) && tieneOtroTelefonoCel.equalsIgnoreCase(Constants.YES)) {
                    guardarTelefonoContacto(numTelefono2, tipoTeleCel, operadoraTelefono2, moverOtroParticipante, codigoNuevaCasaCohorte);
                }
                if (tieneValor(tieneTelefonoConv) && tieneTelefonoConv.equalsIgnoreCase(Constants.YES)) {
                    guardarTelefonoContacto(numTelefono3, tipoTeleConv, operadoraTelefono3, moverOtroParticipante, codigoNuevaCasaCohorte);
                }

                //Registrar las coordenadas
                guardarDatosCoordenadas(coordenadas, barrio, direccion, moverOtroParticipante, codigoNuevaCasaCohorte);

                //Registrar cambio de domicilio
                guardarCambioDomicilio( codigoNuevaCasaCohorte, nombre1JefeFamilia, nombre2JefeFamilia, apellido1JefeFamilia,
                        apellido2JefeFamilia, direccion, barrio, coordenadas, numTelefono1, operadoraTelefono1, numTelefono2,
                        operadoraTelefono2, numTelefono3, operadoraTelefono3, moverOtroParticipante);

                /*Intent i = new Intent(getApplicationContext(),
                        CambioDomicilioMainActivity.class);
                startActivity(i);
                showToast(getString(R.string.success));*/
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showToast(ex.getLocalizedMessage());
        } finally {
            //Cierra la base de datos
            if (estudiosAdapter!=null)
                estudiosAdapter.close();
        }
    }


    public void changeStatus(Page page, boolean visible) {
        String clase = page.getClass().toString();
        if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.SingleFixedChoicePage")){
            SingleFixedChoicePage modifPage = (SingleFixedChoicePage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.BarcodePage")){
            BarcodePage modifPage = (BarcodePage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.LabelPage")){
            LabelPage modifPage = (LabelPage) page; modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.TextPage")){
            TextPage modifPage = (TextPage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible); //modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.NumberPage")){
            NumberPage modifPage = (NumberPage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible); //modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.MultipleFixedChoicePage")){
            MultipleFixedChoicePage modifPage = (MultipleFixedChoicePage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.DatePage")){
            DatePage modifPage = (DatePage) page; modifPage.setValue("").setmVisible(visible);
        }
        /*else if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.SelectParticipantPage")){
            SelectParticipantPage modifPage = (SelectParticipantPage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.NewDatePage")){
            NewDatePage modifPage = (NewDatePage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
        }*/
        else if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.SelectCasaPage")){
            SelectCasaPage modifPage = (SelectCasaPage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.MapaBarriosPage")){
            MapaBarriosPage modifPage = (MapaBarriosPage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
        }
    }

    private boolean tieneValor(String entrada) {
        return (entrada != null && !entrada.isEmpty());
    }

    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        private int mCutOffPage;
        private Fragment mPrimaryItem;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if (i >= mCurrentPageSequence.size()) {
                return new ReviewFragment();
            }

            return mCurrentPageSequence.get(i).createFragment();
        }

        @Override
        public int getItemPosition(Object object) {
            if (object == mPrimaryItem) {
                // Re-use the current fragment (its position never changes)
                return POSITION_UNCHANGED;
            }

            return POSITION_NONE;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            mPrimaryItem = (Fragment) object;
        }

        @Override
        public int getCount() {
            return Math.min(mCutOffPage + 1, (mCurrentPageSequence != null ? mCurrentPageSequence.size() : 0) + 1);
        }

        public void setCutOffPage(int cutOffPage) {
            if (cutOffPage < 0) {
                cutOffPage = Integer.MAX_VALUE;
            }
            mCutOffPage = cutOffPage;
        }

        public int getCutOffPage() {
            return mCutOffPage;
        }
    }

    public static class MyAlertDialogFragment extends DialogFragment {

        public static MyAlertDialogFragment newInstance(int title) {
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putInt("title", title);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int title = getArguments().getInt("title");

            return new AlertDialog.Builder(getActivity())
                    //.setIcon(R.drawable.alert_dialog_icon)
                    .setTitle(title)
                    .setPositiveButton(R.string.submit_confirm_button,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((CambioDomicilioParticipanteActivity) Objects.requireNonNull(getActivity())).saveData();
                                }
                            }
                    )
                    .setNegativeButton(R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((CambioDomicilioParticipanteActivity) Objects.requireNonNull(getActivity())).createDialog(EXIT);;
                                }
                            }
                    )
                    .create();
        }
    }

    private class FetchDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
            try {
                estudiosAdapter.open();
                mUser = estudiosAdapter.getPermisosUsuario(MainDBConstants.USERNAME + "='" +username+"'", null);
                fillParticipantesMismaCasa();
                estudiosAdapter.close();
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return "error";
            }
            return "exito";
        }

        protected void onPostExecute(String resultado) {
            dismissProgressDialog();
        }
    }

    private String[] fillParticipantesMismaCasa() {
        List<Participante> part = estudiosAdapter.getParticipantes(MainDBConstants.casa+" = " +participante.getCasa().getCodigo() +
                " AND " + MainDBConstants.codigo+"!='"+participante.getCodigo()+"'", null);
        participantesCasas = new String[part.size()];
        index = 0;
        for (Participante message: part){
            String value = "";
            value = message.getCodigo() + " - " +
                    (message.getNombre1() != "" ? message.getNombre1() : "")  + " " +
                    (message.getApellido1() != "" ? message.getApellido1() : "");
            participantesCasas[index] = String.valueOf(value);
            index++;
        }
        return participantesCasas;
    }

    private void guardarCambioDomicilio(String codigoNuevaCasaCohorte, String nombre1JefeFamilia, String nombre2JefeFamilia,
                                        String apellido1JefeFamilia, String apellido2JefeFamilia, String direccion, String barrio,
                                        String coordenadas, String numTelefono1, String operadoraTelefono1, String numTelefono2,
                                        String operadoraTelefono2, String numTelefono3, String operadoraTelefono3, String moverOtroParticipante) {
        try {
            CambioDomicilio cambioDomicilio = new CambioDomicilio();
            cambioDomicilio.setCodigoParticipante(participante.getCodigo());
            cambioDomicilio.setCodigoNuevaCasaCohorte(codigoNuevaCasaCohorte);
            cambioDomicilio.setCodigoCasa(participante.getCasa().getCodigo().toString());
            cambioDomicilio.setNombre1JefeFamilia(nombre1JefeFamilia);
            cambioDomicilio.setNombre2JefeFamilia(nombre2JefeFamilia);
            cambioDomicilio.setApellido1JefeFamilia(apellido1JefeFamilia);
            cambioDomicilio.setApellido2JefeFamilia(apellido2JefeFamilia);
            cambioDomicilio.setDireccion(direccion);
            cambioDomicilio.setActual(true);
            Barrio barrio1 = estudiosAdapter.getBarrio(MainDBConstants.nombre + "= '" + barrio + "'", null);
            cambioDomicilio.setBarrio(barrio1);
            cambioDomicilio.setCoordenadas(coordenadas);
            cambioDomicilio.setNumTelefono1(numTelefono1);
            if (tieneValor(numTelefono1)) {
                MessageResource catOperadora = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + operadoraTelefono1 + "' and " + MainDBConstants.catRoot + "='CAT_OPER_TEL'", null);
                cambioDomicilio.setOperadoraTelefono1(catOperadora.getCatKey());
            }
            cambioDomicilio.setNumTelefono2(numTelefono2);
            if (tieneValor(numTelefono2)) {
                MessageResource catOperadora = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + operadoraTelefono2 + "' and " + MainDBConstants.catRoot + "='CAT_OPER_TEL'", null);
                cambioDomicilio.setOperadoraTelefono2(catOperadora.getCatKey());
            }
            cambioDomicilio.setNumTelefono3(numTelefono3);
            if (tieneValor(numTelefono3)) {
                MessageResource catOperadora = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + operadoraTelefono3 + "' and " + MainDBConstants.catRoot + "='CAT_OPER_TEL'", null);
                cambioDomicilio.setOperadoraTelefono3(catOperadora.getCatKey());
            }
            cambioDomicilio.setCodigoMovimiento("");
            cambioDomicilio.setIdentificadoEquipo(infoMovil.getDeviceId());
            cambioDomicilio.setEstado('0');
            cambioDomicilio.setPasivo('0');
            cambioDomicilio.setFechaRegistro(null);

            Date dateCreado = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String changedDate = dateFormat.format(dateCreado);
            cambioDomicilio.setCreado(changedDate);
            cambioDomicilio.setUsuarioRegistro(username);

            if (coordenadas != null) {
                String[] data = coordenadas.replace("(", "").replace(")", "").split(",");
                cambioDomicilio.setLatitud(Double.valueOf(data[0].trim()));
                cambioDomicilio.setLongitud(Double.valueOf(data[1].trim()));
            } else {
                cambioDomicilio.setLatitud(null);
                cambioDomicilio.setLongitud(null);
            }

            estudiosAdapter.crearCambioDomicilio(cambioDomicilio);

            //Actualizar la casa del participante selecciondo en la busqueda
            participante.getCasa().setCodigo(Integer.valueOf(codigoNuevaCasaCohorte));
            participante.setEstado('0');
            estudiosAdapter.editarParticipante(participante);

            if (!StringUtil.isNullOrEmpty(moverOtroParticipante)) {
                //Agregar los otros participantes a la tabla cambio de domicilio
                String replace = moverOtroParticipante.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", ", ",");
                String[] result = replace.split(",");

                for (int i = 0; i < result.length; i++) {
                    String cod = "";
                    String[] value = result[i].split("-");
                    cod = value[0].trim();
                    cambioDomicilio.setCodigoParticipante(cod);
                    estudiosAdapter.crearCambioDomicilio(cambioDomicilio);

                    //Obteniendo el participante
                    Participante part =  estudiosAdapter.getParticipante(MainDBConstants.codigo + "='" + cod + "'", null);
                    if (part != null) {
                        part.getCasa().setCodigo(Integer.valueOf(codigoNuevaCasaCohorte));
                        part.setEstado('0');
                        //Actualizar la casa del participante
                        estudiosAdapter.editarParticipante(part);
                    }
                }
            }
            showToast(getString(R.string.success));
            Intent i = new Intent(getApplicationContext(),
                    CambioDomicilioMainActivity.class);
            startActivity(i);
            finish();
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
    }

    private void guardarTelefonoContacto(String numero, String tipo, String operadora, String moverOtroParticipante, String codigoNuevaCasaCohorte){
        TelefonoContacto telefonoContacto =new TelefonoContacto();
        UUID deviceUuid = new UUID(infoMovil.getDeviceId().hashCode(), new Date().hashCode());
        telefonoContacto.setId(deviceUuid.toString());
        telefonoContacto.setRecordDate(new Date());
        telefonoContacto.setRecordUser(username);
        telefonoContacto.setDeviceid(infoMovil.getDeviceId());
        telefonoContacto.setEstado('0');
        telefonoContacto.setPasive('0');
        telefonoContacto.setParticipante(participante);
        telefonoContacto.setCasa(casa);
        telefonoContacto.setNumero(numero);
        telefonoContacto.setTipo(tipo);
        if (tieneValor(operadora)) {
            MessageResource catOperadora = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + operadora + "' and " + MainDBConstants.catRoot + "='CAT_OPER_TEL'", null);
            telefonoContacto.setOperadora(catOperadora.getCatKey());
        }
        estudiosAdapter.crearTelefonoContacto(telefonoContacto);

        if (!StringUtil.isNullOrEmpty(moverOtroParticipante)) {
            //Agregar los otros participantes a la tabla telefono contacto
            String replace = moverOtroParticipante.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", ", ",");
            String[] result = replace.split(",");
            for (int i = 0; i < result.length; i++) {
                String cod = "";
                String[] value = result[i].split("-");
                cod = value[0].trim();
                //Obteniendo el participante
                Participante part =  estudiosAdapter.getParticipante(MainDBConstants.codigo + "='" + cod + "'", null);
                if (part != null) {
                    UUID deviceUuid2 = new UUID(infoMovil.getDeviceId().hashCode(), new Date().hashCode());
                    telefonoContacto.setId(deviceUuid2.toString());
                    //part.getCasa().setCodigo(Integer.valueOf(codigoNuevaCasaCohorte));
                    telefonoContacto.getCasa().setCodigo(Integer.valueOf(codigoNuevaCasaCohorte));
                    telefonoContacto.setParticipante(part);

                    //Actualizar la casa del participante
                    estudiosAdapter.crearTelefonoContacto(telefonoContacto);
                }
            }
        }
    }

    private void guardarDatosCoordenadas(String coordenadas, String barrio, String direccion, String moverOtroParticipante, String codigoNuevaCasaCohorte) {
        DatosCoordenadas mCoordenadas = new DatosCoordenadas();
        UUID deviceUuid = new UUID(infoMovil.getDeviceId().hashCode(), new Date().hashCode());
        mCoordenadas.setCodigo(deviceUuid.toString());
        mCoordenadas.setRecordDate(new Date());
        mCoordenadas.setRecordUser(username);
        mCoordenadas.setDeviceid(infoMovil.getDeviceId());
        mCoordenadas.setEstado(Constants.STATUS_NOT_SUBMITTED);
        mCoordenadas.setPasive(Constants.STATUS_NOT_PASIVE);
        mCoordenadas.setParticipante(participante);
        mCoordenadas.setCodigoCasa(Integer.valueOf(codigoNuevaCasaCohorte));
        mCoordenadas.setEstudios("A2CARES");
        mCoordenadas.setMotivo("1");
        //se toman los datos actuales del participante como validos
        Barrio barrio1 = estudiosAdapter.getBarrio(MainDBConstants.nombre + "= '" + barrio + "'", null);
        mCoordenadas.setBarrio(barrio1);
        mCoordenadas.setManzana(0);
        mCoordenadas.setDireccion(direccion);
        mCoordenadas.setRazonNoGeoref(null);
        mCoordenadas.setOtraRazonNoGeoref(null);
        mCoordenadas.setPuntoGps(coordenadas);
        mCoordenadas.setActual(true);
        if (coordenadas != null) {
            String[] data = coordenadas.replace("(", "").replace(")", "").split(",");
            mCoordenadas.setLatitud(Double.valueOf(data[0].trim()));
            mCoordenadas.setLongitud(Double.valueOf(data[1].trim()));
            mCoordenadas.setConpunto(Constants.YESKEYSND);
        } else {
            mCoordenadas.setLatitud(null);
            mCoordenadas.setLongitud(null);
            mCoordenadas.setConpunto(Constants.NOKEYSND);
        }
        estudiosAdapter.crearDatosCoordenadas(mCoordenadas);

        if (!StringUtil.isNullOrEmpty(moverOtroParticipante)) {
            //Agregar los otros participantes a la tabla cambio de domicilio
            String replace = moverOtroParticipante.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", ", ",");
            String[] result = replace.split(",");
            for (int i = 0; i < result.length; i++) {
                String cod = "";
                String[] value = result[i].split("-");
                cod = value[0].trim();
                //Obteniendo el participante
                Participante part =  estudiosAdapter.getParticipante(MainDBConstants.codigo + "='" + cod + "'", null);
                if (part != null) {
                    UUID deviceUuid2 = new UUID(infoMovil.getDeviceId().hashCode(), new Date().hashCode());
                    mCoordenadas.setCodigo(deviceUuid2.toString());
                    part.getCasa().setCodigo(Integer.valueOf(codigoNuevaCasaCohorte));
                    part.setEstado('0');
                    //Actualizar el participante en las coordenadas
                    mCoordenadas.setParticipante(part);
                    estudiosAdapter.crearDatosCoordenadas(mCoordenadas);
                }
            }
        }
    }
}
