package ni.org.ics.a2cares.app.ui.activities.enterdata;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import ni.org.ics.a2cares.app.AbstractAsyncActivity;
import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.domain.core.*;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.domain.survey.EncuestaCasa;
import ni.org.ics.a2cares.app.domain.users.UserPermissions;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuParticipanteActivity;
import ni.org.ics.a2cares.app.ui.adapters.ParticipanteListAdapter;
import ni.org.ics.a2cares.app.ui.forms.TamizajeForm;
import ni.org.ics.a2cares.app.ui.forms.TamizajeFormLabels;
import ni.org.ics.a2cares.app.preferences.PreferencesActivity;
import ni.org.ics.a2cares.app.utils.*;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.wizard.model.*;
import ni.org.ics.a2cares.app.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.a2cares.app.wizard.ui.ReviewFragment;
import ni.org.ics.a2cares.app.wizard.ui.StepPagerStrip;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class NuevoTamizajeActivity extends AbstractAsyncActivity implements
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
    private TamizajeFormLabels labels = new TamizajeFormLabels();
    private EstudioDBAdapter estudiosAdapter;
    private DeviceInfo infoMovil;
    //private GPSTracker gps;
    private String username;
    private SharedPreferences settings;
    private static final int EXIT = 1;
    private AlertDialog alertDialog;
    private boolean notificarCambios = true;
    private Integer edadAnios = 0;
    private Integer edadSemanas = 0;
    private String[] catRelFamMenorEdad; //relación familiar del tutor cuando es menor de edad
    private String[] catRelFamMayorEdad; //relación familiar del tutor cuando es mayor de edad
    private String[] catVerifTutAlf; //cosas a verificar cuando tutor es alfabeto
    private String[] catVerifTutNoAlf; //cosas a verificar cuando tutor no es alfabeto
    private int totalVerifTutor = 0; //total opciones a marcar requeridas
    private Date fechaNacimiento = null;
    private final int EDAD_MIN_ASENTIMIENTO = 6;
    private final int EDAD_MAX_ASENTIMIENTO = 14;
    private final int EDAD_LIMITE_INGRESO = 80; //justo antes de cumplir 11 anios
    private final int EDAD_MINIMA_INGRESO = 2; //ANIOS
    private Participante participante;
    private Casa casa;
    private boolean pedirVisita;
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
        //casaCHF = (CasaCohorteFamilia) getIntent().getExtras().getSerializable(Constants.CASA);
        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);
        infoMovil = new DeviceInfo(NuevoTamizajeActivity.this);
        //gps = new GPSTracker(NewTamizajeActivity.this);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(),mPass,false,false);

        mWizardModel = new TamizajeForm(this,mPass);
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
            if (!page.getData().isEmpty() && clase.equals("class ni.org.ics.a2cares.app.wizard.model.MultipleFixedChoicePage")) {
                ArrayList<String> test = page.getData().getStringArrayList(Page.SIMPLE_DATA_KEY);
                //validación solo para la pregunta de verificación del tutor
                if (page.getTitle().equalsIgnoreCase(this.getString(R.string.verifTutor)) && test.size() != totalVerifTutor) {
                    cutOffPage = i;
                    break;
                }
            }
            if (!page.getData().isEmpty() && clase.equals("class ni.org.ics.a2cares.app.wizard.model.BarcodePage")) {
                BarcodePage bp = (BarcodePage) page;
                if (bp.ismValRange() || bp.ismValPattern()) {
                    String valor = bp.getData().getString(TextPage.SIMPLE_DATA_KEY);
                    if ((bp.ismValRange() && (bp.getmGreaterOrEqualsThan() > Double.valueOf(valor) || bp.getmLowerOrEqualsThan() < Double.valueOf(valor)))
                            || (bp.ismValPattern() && !valor.matches(bp.getmPattern()))){
                        cutOffPage = i;
                        break;
                    }
                }
            }
        }

        if (mPagerAdapter.getCutOffPage() != cutOffPage) {
            mPagerAdapter.setCutOffPage(cutOffPage);
            return true;
        }

        return false;
    }

    public void updateConstrains(){

    }

    public void updateModel(Page page){
        try{
            boolean visible = false;
            if (page.getTitle().equals(labels.getFechaNacimiento())) {
                DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    fechaNacimiento = mDateFormat.parse(page.getData().getString(DatePage.SIMPLE_DATA_KEY));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast toast = Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG);
                    toast.show();
                    finish();
                }
                CalcularEdad calEdad = new CalcularEdad(fechaNacimiento);
                String[] edad = calEdad.getEdad().split("/");
                edadAnios = Integer.parseInt(edad[0]);
                edadSemanas = calEdad.getEdadSemanas();
                if (!(edadAnios >= EDAD_MINIMA_INGRESO && edadAnios < EDAD_LIMITE_INGRESO)){
                    changeStatus(mWizardModel.findByKey(labels.getAceptaTamizaje()), false);
                    changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaTamizaje()), true);
                    Toast toast = Toast.makeText(getApplicationContext(), this.getString(R.string.noEsElegible) + " A2CARES", Toast.LENGTH_LONG);
                    toast.show();
                    resetForm(100);
                } else {
                    changeStatus(mWizardModel.findByKey(labels.getAceptaTamizaje()), true);
                    changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaTamizaje()), false);
                }
                SingleFixedChoicePage pagetmp = (SingleFixedChoicePage)mWizardModel.findByKey(labels.getRelacionFamiliarTutor());
                pagetmp.setChoices(edadAnios<18?catRelFamMenorEdad:catRelFamMayorEdad);

                LabelPage pageEdad = (LabelPage)mWizardModel.findByKey(labels.getEdad());
                String edadFormateada = edad[0] + " años " + edad[1] + " meses " + edad[2] + " dias";
                pageEdad.setHint(edadFormateada);
                pageEdad.setmHintTextColor("#FFDC2D2D");

                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getAceptaTamizaje())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaTamizaje()), !visible);
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaTamizaje()), false);
                changeStatus(mWizardModel.findByKey(labels.getVivienda()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTiempoResidencia()), visible);
                changeStatus(mWizardModel.findByKey(labels.getPlanesMudarse()), visible);
                if(!visible) {
                    resetForm(100);
                    Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noAceptaTamizajePersona),Toast.LENGTH_LONG);
                    toast.show();
                }
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getRazonNoAceptaTamizaje())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaTamizaje()), visible);
                onPageTreeChanged();
            }
            
            if(page.getTitle().equals(labels.getVivienda())) {
                visible = (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Alquilada"));
                Page pagetmp = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTiempoResidencia());
                if (tieneValor(pagetmp.getData().getString(TextPage.SIMPLE_DATA_KEY))) {
                   //es alquilada y tiene tiempo valido
                    if (visible) {
                        boolean tiempoValido = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Un año ó Más");
                        if (!tiempoValido) {
                            Toast toast = Toast.makeText(getApplicationContext(), R.string.noCumpleCriteriosInclusion, Toast.LENGTH_LONG);
                            toast.show();
                            resetForm(99);
                        }
                        changeStatus(mWizardModel.findByKey(labels.getAsentimientoVerbal()), tiempoValido && edadAnios>= EDAD_MIN_ASENTIMIENTO && edadAnios <= EDAD_MAX_ASENTIMIENTO);
                        changeStatus(mWizardModel.findByKey(labels.getAceptaParticipar()), tiempoValido && (edadAnios >= EDAD_MINIMA_INGRESO && edadAnios < EDAD_LIMITE_INGRESO));
                    } else {
                        changeStatus(mWizardModel.findByKey(labels.getAsentimientoVerbal()), edadAnios>= EDAD_MIN_ASENTIMIENTO && edadAnios <= EDAD_MAX_ASENTIMIENTO);
                        changeStatus(mWizardModel.findByKey(labels.getAceptaParticipar()), edadAnios >= EDAD_MINIMA_INGRESO && edadAnios < EDAD_LIMITE_INGRESO);
                    }

                }
                changeStatus(mWizardModel.findByKey(labels.getTiempoResidencia()), visible);
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getTiempoResidencia())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Un año ó Más");
                if (!visible) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.noCumpleCriteriosInclusion, Toast.LENGTH_LONG);
                    toast.show();
                    resetForm(99);
                }
                changeStatus(mWizardModel.findByKey(labels.getPlanesMudarse()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAsentimientoVerbal()), visible && edadAnios >= EDAD_MIN_ASENTIMIENTO && edadAnios <= EDAD_MAX_ASENTIMIENTO);
                changeStatus(mWizardModel.findByKey(labels.getAceptaParticipar()), visible && (edadAnios >= EDAD_MINIMA_INGRESO && edadAnios < EDAD_LIMITE_INGRESO));
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getPlanesMudarse())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                if(visible) {
                    resetForm(99);
                    Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noCumpleCriteriosInclusion),Toast.LENGTH_LONG);
                    toast.show();
                }
                changeStatus(mWizardModel.findByKey(labels.getAsentimientoVerbal()), !visible && edadAnios>= EDAD_MIN_ASENTIMIENTO && edadAnios <= EDAD_MAX_ASENTIMIENTO);
                changeStatus(mWizardModel.findByKey(labels.getAceptaParticipar()), !visible && (edadAnios >= EDAD_MINIMA_INGRESO && edadAnios < EDAD_LIMITE_INGRESO));
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getAsentimientoVerbal())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Si");
                changeStatus(mWizardModel.findByKey(labels.getAceptaParticipar()), visible && (edadAnios >= EDAD_MINIMA_INGRESO && edadAnios < EDAD_LIMITE_INGRESO));
                if(!visible) {
                    resetForm(98);
                    Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noDaAsentimiento),Toast.LENGTH_LONG);
                    toast.show();
                }
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getAceptaParticipar())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);

                changeStatus(mWizardModel.findByKey(labels.getCasaPerteneceCohorte()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCodigoCasaCohorte()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCodigoNuevaCasaCohorte()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNombre1JefeFamilia()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNombre2JefeFamilia()), visible);
                changeStatus(mWizardModel.findByKey(labels.getApellido1JefeFamilia()), visible);
                changeStatus(mWizardModel.findByKey(labels.getApellido2JefeFamilia()), visible);
                changeStatus(mWizardModel.findByKey(labels.getBarrio()), visible);
                //changeStatus(mWizardModel.findByKey(labels.getManzana()), visible);
                changeStatus(mWizardModel.findByKey(labels.getDireccion()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCoordenadas()), visible);
                changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCodigoNuevoParticipante()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNombre1()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNombre2()), visible);
                changeStatus(mWizardModel.findByKey(labels.getApellido1()), visible);
                changeStatus(mWizardModel.findByKey(labels.getApellido2()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNombre1Padre()), (visible) && edadAnios<18);
                changeStatus(mWizardModel.findByKey(labels.getNombre2Padre()), (visible) && edadAnios<18);
                changeStatus(mWizardModel.findByKey(labels.getApellido1Padre()), (visible) && edadAnios<18);
                changeStatus(mWizardModel.findByKey(labels.getApellido2Padre()), (visible) && edadAnios<18);
                changeStatus(mWizardModel.findByKey(labels.getNombre1Madre()), (visible)&& edadAnios<18);
                changeStatus(mWizardModel.findByKey(labels.getNombre2Madre()), (visible) && edadAnios<18);
                changeStatus(mWizardModel.findByKey(labels.getApellido1Madre()), (visible) && edadAnios<18);
                changeStatus(mWizardModel.findByKey(labels.getApellido2Madre()), (visible) && edadAnios<18);
                changeStatus(mWizardModel.findByKey(labels.getNombre1Tutor()), (visible) && edadAnios<18);
                changeStatus(mWizardModel.findByKey(labels.getNombre2Tutor()), (visible) && edadAnios<18);
                changeStatus(mWizardModel.findByKey(labels.getApellido1Tutor()), (visible) && edadAnios<18);
                changeStatus(mWizardModel.findByKey(labels.getApellido2Tutor()), (visible) && edadAnios<18);
                changeStatus(mWizardModel.findByKey(labels.getRelacionFamiliarTutor()), (visible) && edadAnios<18);
                changeStatus(mWizardModel.findByKey(labels.getTieneTelefonoCel()), visible);
                //changeStatus(mWizardModel.findByKey(labels.getNumTelefono1()), visible);
                //changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono1()), visible);
                //changeStatus(mWizardModel.findByKey(labels.getTieneOtroTelefonoCel()), visible);
                //changeStatus(mWizardModel.findByKey(labels.getNumTelefono2()), visible);
                //changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono2()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTieneTelefonoConv()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNumTelefono3()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono3()), visible);
                changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAceptaContactoFuturo()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteB()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAceptaParteC()), visible);

                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaParticipar()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaParticipar()), false);
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getRazonNoAceptaParticipar())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).contains(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaParticipar()), visible);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getCasaPerteneceCohorte())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCodigoCasaCohorte()), visible);
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getCodigoNuevaCasaCohorte()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNombre1JefeFamilia()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNombre2JefeFamilia()), visible);
                changeStatus(mWizardModel.findByKey(labels.getApellido1JefeFamilia()), visible);
                changeStatus(mWizardModel.findByKey(labels.getApellido2JefeFamilia()), visible);
                changeStatus(mWizardModel.findByKey(labels.getBarrio()), visible);
                //changeStatus(mWizardModel.findByKey(labels.getManzana()), visible);
                changeStatus(mWizardModel.findByKey(labels.getDireccion()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCoordenadas()), visible);
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getBarrio())) {
                MapaBarriosPage mapaPage = (MapaBarriosPage)mWizardModel.findByKey(labels.getCoordenadas());
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).contains("Nejapa");
                if (visible) mapaPage.setmUnidadSalud(Constants.UBICACION_NJ);
                else mapaPage.setmUnidadSalud(Constants.UBICACION_CO);
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getParticipanteOTutorAlfabeto())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getTestigoPresente()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNombre1Testigo()), false);
                changeStatus(mWizardModel.findByKey(labels.getNombre2Testigo()), false);
                changeStatus(mWizardModel.findByKey(labels.getApellido1Testigo()), false);
                changeStatus(mWizardModel.findByKey(labels.getApellido2Testigo()), false);
                MultipleFixedChoicePage pagetmp = (MultipleFixedChoicePage)mWizardModel.findByKey(labels.getVerifTutor());
                pagetmp.setChoices(visible?catVerifTutNoAlf:catVerifTutAlf);
                totalVerifTutor = visible?catVerifTutNoAlf.length:catVerifTutAlf.length;
                //changeStatus(mWizardModel.findByKey(labels.getAceptaContactoFuturo()), !visible);
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getTestigoPresente())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNombre1Testigo()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNombre2Testigo()), visible);
                changeStatus(mWizardModel.findByKey(labels.getApellido1Testigo()), visible);
                changeStatus(mWizardModel.findByKey(labels.getApellido2Testigo()), visible);
                changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTieneTelefonoCel()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTieneTelefonoConv()), visible);
                if(!visible) {
                    resetForm(96);
                    Toast toast = Toast.makeText(getApplicationContext(),this.getString(R.string.noEsElegible),Toast.LENGTH_LONG);
                    toast.show();
                }
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getTieneTelefonoCel())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumTelefono1()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono1()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTieneOtroTelefonoCel()), visible);
                //changeStatus(mWizardModel.findByKey(labels.getNumTelefono2()), false);
                //changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono2()), false);
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getTieneTelefonoConv())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumTelefono3()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono3()), visible);
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getTieneOtroTelefonoCel())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY)!=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono2()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNumTelefono2()), visible);
                onPageTreeChanged();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void resetForm(int preg){
        if (preg>99) changeStatus(mWizardModel.findByKey(labels.getVivienda()), false);
        if (preg>99) changeStatus(mWizardModel.findByKey(labels.getTiempoResidencia()), false);
        /*if (preg>98) changeStatus(mWizardModel.findByKey(labels.getDondeAsisteProblemasSalud()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getOtroCentroSalud()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getPuestoSalud()), false);
        if (preg>98) changeStatus(mWizardModel.findByKey(labels.getAceptaAtenderCentro()), false);
        */
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getAceptaParticipar()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getRazonNoAceptaParticipar()), false);
        if (preg>97) changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoAceptaParticipar()), false);

        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getCasaPerteneceCohorte()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getCodigoCasaCohorte()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getCodigoNuevaCasaCohorte()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre1JefeFamilia()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre2JefeFamilia()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido1JefeFamilia()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido2JefeFamilia()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getBarrio()), false);
        //if (preg>96) changeStatus(mWizardModel.findByKey(labels.getManzana()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getDireccion()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getCoordenadas()), false);

        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getCodigoNuevoParticipante()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre1()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre2()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido1()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido2()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre1Padre()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre2Padre()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido1Padre()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido2Padre()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre1Madre()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre2Madre()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido1Madre()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido2Madre()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre1Tutor()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getNombre2Tutor()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido1Tutor()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getApellido2Tutor()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getRelacionFamiliarTutor()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getParticipanteOTutorAlfabeto()), false);
        if (preg>96) changeStatus(mWizardModel.findByKey(labels.getTestigoPresente()), false);

        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getNombre1Testigo()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getNombre2Testigo()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getApellido1Testigo()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getApellido2Testigo()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getAceptaContactoFuturo()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getAceptaParteB()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getAceptaParteC()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getTieneTelefonoCel()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getTieneOtroTelefonoCel()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getTieneTelefonoConv()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getNumTelefono1()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono1()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getNumTelefono2()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono2()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getNumTelefono3()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getOperadoraTelefono3()), false);
        if (preg>95) changeStatus(mWizardModel.findByKey(labels.getVerifTutor()), false);
    }

    public void changeStatus(Page page, boolean visible){
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

    private boolean tieneValor(String entrada){
        return (entrada != null && !entrada.isEmpty());
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

            //Obtener datos del bundle para el tamizaje
            String sexo = datos.getString(this.getString(R.string.sexo));
            Date fechaNacimiento = null;
            Date fechaDengue = null;
            Date fechaHospitalizado = null;
            try {
                fechaNacimiento = mDateFormat.parse(datos.getString(this.getString(R.string.fechaNacimiento)));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast toast = Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG);
                toast.show();
                finish();
            }

            String codigoNuevoParticipante = datos.getString(this.getString(R.string.codigoNuevoParticipante));
            participante = null;

            Participante existeParti = estudiosAdapter.getParticipante(MainDBConstants.codigo + "='" + codigoNuevoParticipante + "'", null);
            if (existeParti == null || existeParti.getCodigo() == null) {

                String aceptaTamizajePersona = datos.getString(this.getString(R.string.aceptaTamizaje));
                String razonNoAceptaTamizajePersona = datos.getString(this.getString(R.string.razonNoAceptaTamizaje));
                String otraRazonNoAceptaTamizajePersona = datos.getString(this.getString(R.string.otraRazonNoAceptaTamizaje));
                //String criteriosInclusion = datos.getString(this.getString(R.string.criteriosInclusion));
                String vivienda = datos.getString(this.getString(R.string.tipoVivienda));
                String tiempoResidencia = datos.getString(this.getString(R.string.tiempoResidencia));
                String planesMudarse = datos.getString(this.getString(R.string.planesMudarse));
                String asentimientoVerbal = datos.getString(this.getString(R.string.asentimientoVerbal));
                String aceptaParticipar = datos.getString(this.getString(R.string.aceptaParticipar));
                String razonNoAceptaParticipar = datos.getString(this.getString(R.string.razonNoAceptaParticipar));
                String otraRazonNoAceptaParticipar = datos.getString(this.getString(R.string.otraRazonNoAceptaParticipar));

                String aceptaParteB = datos.getString(this.getString(R.string.aceptaParteB));
                String aceptaParteC = datos.getString(this.getString(R.string.aceptaParteC));
                String aceptaContactoFuturo = datos.getString(this.getString(R.string.aceptaContactoFuturo));

                String codigoCasaCohorte = datos.getString(this.getString(R.string.codigoCasaCohorte));
                String codigoNuevaCasaCohorte = datos.getString(this.getString(R.string.codigoNuevaCasaCohorte));
                String nombre1JefeFamilia = datos.getString(this.getString(R.string.nombre1JefeFamilia));
                String nombre2JefeFamilia = datos.getString(this.getString(R.string.nombre2JefeFamilia));
                String apellido1JefeFamilia = datos.getString(this.getString(R.string.apellido1JefeFamilia));
                String apellido2JefeFamilia = datos.getString(this.getString(R.string.apellido2JefeFamilia));
                String barrio = datos.getString(this.getString(R.string.barrio));
                //String manzana = datos.getString(this.getString(R.string.manzana));
                String direccion = datos.getString(this.getString(R.string.direccion));
                String coordenadas = datos.getString(this.getString(R.string.coordenadas));

                String nombre1 = datos.getString(this.getString(R.string.nombre1));
                String nombre2 = datos.getString(this.getString(R.string.nombre2));
                String apellido1 = datos.getString(this.getString(R.string.apellido1));
                String apellido2 = datos.getString(this.getString(R.string.apellido2));
                String nombre1Padre = datos.getString(this.getString(R.string.nombre1Padre));
                String nombre2Padre = datos.getString(this.getString(R.string.nombre2Padre));
                String apellido1Padre = datos.getString(this.getString(R.string.apellido1Padre));
                String apellido2Padre = datos.getString(this.getString(R.string.apellido2Padre));
                String nombre1Madre = datos.getString(this.getString(R.string.nombre1Madre));
                String nombre2Madre = datos.getString(this.getString(R.string.nombre2Madre));
                String apellido1Madre = datos.getString(this.getString(R.string.apellido1Madre));
                String apellido2Madre = datos.getString(this.getString(R.string.apellido2Madre));
                String nombre1Tutor = datos.getString(this.getString(R.string.nombre1Tutor));
                String nombre2Tutor = datos.getString(this.getString(R.string.nombre2Tutor));
                String apellido1Tutor = datos.getString(this.getString(R.string.apellido1Tutor));
                String apellido2Tutor = datos.getString(this.getString(R.string.apellido2Tutor));
                String relacionFamiliarTutor = datos.getString(this.getString(R.string.relacionFamiliarTutor));

                String participanteOTutorAlfabeto = datos.getString(this.getString(R.string.participanteOTutorAlfabeto));
                String testigoPresente = datos.getString(this.getString(R.string.testigoPresente));
                String nombre1Testigo = datos.getString(this.getString(R.string.nombre1Testigo));
                String nombre2Testigo = datos.getString(this.getString(R.string.nombre2Testigo));
                String apellido1Testigo = datos.getString(this.getString(R.string.apellido1Testigo));
                String apellido2Testigo = datos.getString(this.getString(R.string.apellido2Testigo));

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

                String verifTutor = datos.getString(this.getString(R.string.verifTutor));//agregar en carta

                //Crea un Nuevo Registro de tamizaje
                Tamizaje tamizaje = new Tamizaje();
                tamizaje.setCodigo(infoMovil.getId());
                if (tieneValor(sexo)) {
                    MessageResource catSexo = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + sexo + "' and " + MainDBConstants.catRoot + "='CAT_SEXO'", null);
                    if (catSexo != null) tamizaje.setSexo(catSexo.getCatKey());
                }
                tamizaje.setFechaNacimiento(fechaNacimiento);
                if (tieneValor(aceptaTamizajePersona)) {
                    MessageResource catAceptaTamizajePersona = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + aceptaTamizajePersona + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                    if (catAceptaTamizajePersona != null)
                        tamizaje.setAceptaTamizajePersona(catAceptaTamizajePersona.getCatKey());
                } else {//si no tiene valor, es porque no tiene la edad según el estudio seleccionado
                    tamizaje.setAceptaTamizajePersona(Constants.NOKEYSND);
                }
                if (tieneValor(razonNoAceptaTamizajePersona)) {
                    MessageResource catRazonNoAceptaTamizajePersona = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + razonNoAceptaTamizajePersona + "' and " + MainDBConstants.catRoot + "='CAT_NO_TAMIZAJE'", null);
                    if (catRazonNoAceptaTamizajePersona != null)
                        tamizaje.setRazonNoAceptaTamizajePersona(catRazonNoAceptaTamizajePersona.getCatKey());
                }
                if (tieneValor(vivienda)) {
                    MessageResource catVivienda = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + vivienda + "' and " + MainDBConstants.catRoot + "='CAT_TIPO_VIVIENDA'", null);
                    if (catVivienda != null) tamizaje.setTipoVivienda(catVivienda.getCatKey());
                }
                if (tieneValor(tiempoResidencia)) {
                    MessageResource cattiempoResidencia = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tiempoResidencia + "' and " + MainDBConstants.catRoot + "='CAT_TMP_RES'", null);
                    if (cattiempoResidencia != null)
                        tamizaje.setTiempoResidencia(cattiempoResidencia.getCatKey());
                }
                if (tieneValor(planesMudarse)) {
                    MessageResource catSino = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + planesMudarse + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                    if (catSino != null) tamizaje.setPlanesMudarse(catSino.getCatKey());
                }
                if (tieneValor(asentimientoVerbal)) {
                    MessageResource catAsentimientoVerbal = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + asentimientoVerbal + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                    if (catAsentimientoVerbal != null)
                        tamizaje.setAsentimientoVerbal(catAsentimientoVerbal.getCatKey());
                }
                if (tieneValor(aceptaParticipar)) {
                    MessageResource catAceptaParteA = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + aceptaParticipar + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                    if (catAceptaParteA != null) {
                        tamizaje.setAceptaParticipar(catAceptaParteA.getCatKey());
                    }
                }
                if (tieneValor(razonNoAceptaParticipar)) {
                    MessageResource noParticipa = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + razonNoAceptaParticipar + "' and " + MainDBConstants.catRoot + "='CAT_NO_PARTICIPA'", null);
                    if (noParticipa != null) {
                        tamizaje.setRazonNoAceptaParticipar(noParticipa.getCatKey());
                    }
                }
                tamizaje.setOtraRazonNoAceptaParticipar(otraRazonNoAceptaParticipar);
                tamizaje.setOtraRazonNoAceptaTamizajePersona(otraRazonNoAceptaTamizajePersona);
                tamizaje.setRecordDate(new Date());
                tamizaje.setRecordUser(username);
                tamizaje.setDeviceid(infoMovil.getDeviceId());
                tamizaje.setEstado('0');
                tamizaje.setPasive('0');
                //Registrar tamizaje dengue si aplica
                Estudio estudio = estudiosAdapter.getEstudio(MainDBConstants.codigo + "=" + Constants.CODIGO_ESTUDIO, null);
                tamizaje.setEstudio(estudio);

                boolean esElegible = ((edadAnios >= EDAD_MINIMA_INGRESO && edadAnios < EDAD_LIMITE_INGRESO)
                        && ((tieneValor(vivienda) && vivienda.matches("Propia"))
                        || ((tieneValor(vivienda) && vivienda.matches("Alquilada")) && (tieneValor(tiempoResidencia) && tiempoResidencia.matches("Un año ó Más"))))
                        && (tieneValor(planesMudarse) && planesMudarse.matches(Constants.NO))
                );

                tamizaje.setEsElegible(esElegible ? Constants.YESKEYSND : Constants.NOKEYSND);
                estudiosAdapter.crearTamizaje(tamizaje);

                //Pregunta si acepta realizar el tamizaje
                if (tamizaje.getAceptaTamizajePersona().equals(Constants.YESKEYSND)) {
                    //Registrar casa (si es nueva), participante y consentimiento sólo si acepta participar en el estudio
                    if (tieneValor(aceptaParticipar) && aceptaParticipar.equalsIgnoreCase(Constants.YES)) {
                        //Creamos un nuevo participante
                        participante = new Participante();
                        String estudios = "";
                        ParticipanteProcesos procesos = new ParticipanteProcesos();
                        CartaConsentimiento cc = new CartaConsentimiento();
                        cc.setRecordDate(new Date());
                        cc.setRecordUser(username);
                        cc.setDeviceid(infoMovil.getDeviceId());
                        cc.setEstado('0');
                        cc.setPasive('0');
                        cc.setFechaFirma(new Date());
                        if (tieneValor(nombre1Tutor)) {
                            cc.setNombre1Tutor(nombre1Tutor);
                        } else if (edadAnios >= 18) { //si es mayor de edad, seteas los datos del propio participante como tutor
                            cc.setNombre1Tutor(nombre1);
                        }
                        if (tieneValor(nombre2Tutor)) {
                            cc.setNombre2Tutor(nombre2Tutor);
                        } else if (edadAnios >= 18) { //si es mayor de edad, seteas los datos del propio participante como tutor
                            cc.setNombre2Tutor(nombre2);
                        }
                        if (tieneValor(apellido1Tutor)) {
                            cc.setApellido1Tutor(apellido1Tutor);
                        } else if (edadAnios >= 18) { //si es mayor de edad, seteas los datos del propio participante como tutor
                            cc.setApellido1Tutor(apellido1);
                        }
                        if (tieneValor(apellido2Tutor)) {
                            cc.setApellido2Tutor(apellido2Tutor);
                        } else if (edadAnios >= 18) { //si es mayor de edad, seteas los datos del propio participante como tutor
                            cc.setApellido2Tutor(apellido2);
                        }

                        if (tieneValor(relacionFamiliarTutor)) {
                            MessageResource catRelacionFamiliarTutor = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + relacionFamiliarTutor + "' and " + MainDBConstants.catRoot + "='CAT_RF_TUTOR'", null);
                            if (catRelacionFamiliarTutor != null)
                                cc.setRelacionFamiliarTutor(catRelacionFamiliarTutor.getCatKey());
                        } else if (edadAnios >= 18) { //si es mayor de edad, seteas el propio participante como tutor
                            cc.setApellido2Tutor(Constants.REL_FAM_MISMO_PART);
                        }

                        if (tieneValor(participanteOTutorAlfabeto)) {
                            MessageResource catParticipanteOTutorAlfabeto = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + participanteOTutorAlfabeto + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                            if (catParticipanteOTutorAlfabeto != null)
                                cc.setParticipanteOTutorAlfabeto(catParticipanteOTutorAlfabeto.getCatKey());
                        }
                        if (tieneValor(testigoPresente)) {
                            MessageResource catTestigoPresente = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + testigoPresente + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                            if (catTestigoPresente != null)
                                cc.setTestigoPresente(catTestigoPresente.getCatKey());
                        }

                        if (tieneValor(nombre1Testigo)) cc.setNombre1Testigo(nombre1Testigo);
                        if (tieneValor(nombre2Testigo)) cc.setNombre2Testigo(nombre2Testigo);
                        if (tieneValor(apellido1Testigo)) cc.setApellido1Testigo(apellido1Testigo);
                        if (tieneValor(apellido2Testigo)) cc.setApellido2Testigo(apellido2Testigo);

                        if (tieneValor(verifTutor)) {
                            String keysCriterios = "";
                            verifTutor = verifTutor.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", ", "','");
                            List<MessageResource> catVerificaT = estudiosAdapter.getMessageResources(MainDBConstants.spanish + " in ('" + verifTutor + "') and "
                                    + MainDBConstants.catRoot + "='CAT_VERIFICA'", null);
                            for (MessageResource ms : catVerificaT) {
                                keysCriterios += ms.getCatKey() + ",";
                            }
                            if (!keysCriterios.isEmpty())
                                keysCriterios = keysCriterios.substring(0, keysCriterios.length() - 1);
                            cc.setVerifTutor(keysCriterios);
                        }
                        if (tieneValor(aceptaContactoFuturo)) {
                            MessageResource catConFut = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + aceptaContactoFuturo + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                            if (catConFut != null) {
                                cc.setAceptaContactoFuturo(catConFut.getCatKey());
                            }
                        }
                        cc.setAceptaParteA(Constants.YESKEYSND);
                        if (tieneValor(aceptaParticipar)) {
                            MessageResource catAceptaParteA = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + aceptaParticipar + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                            if (catAceptaParteA != null) {
                                cc.setAceptaParteA(catAceptaParteA.getCatKey());
                            }
                        }
                        if (tieneValor(razonNoAceptaParticipar)) {
                            MessageResource catRazonNoAceptaParticipar = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + razonNoAceptaParticipar + "' and " + MainDBConstants.catRoot + "='CAT_NO_PARTICIPA'", null);
                            if (catRazonNoAceptaParticipar != null)
                                cc.setMotivoRechazoParteA(catRazonNoAceptaParticipar.getCatKey());
                        }
                        cc.setOtroMotivoRechazoParteA(otraRazonNoAceptaParticipar);
                        if (tieneValor(aceptaParteB)) {
                            MessageResource catAceptaParteB = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + aceptaParteB + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                            if (catAceptaParteB != null) {
                                cc.setAceptaParteB(catAceptaParteB.getCatKey());
                            }
                        }
                        if (tieneValor(aceptaParteC)) {
                            MessageResource catAceptaParteC = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + aceptaParteC + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                            if (catAceptaParteC != null) {
                                cc.setAceptaParteC(catAceptaParteC.getCatKey());
                            }
                        }
                        cc.setCodigo(infoMovil.getId());
                        cc.setTamizaje(tamizaje);
                        cc.setVersion(Constants.VERSION_CC);

                        if (tieneValor(codigoCasaCohorte)) {
                            casa = estudiosAdapter.getCasa(MainDBConstants.codigo + " = " +codigoCasaCohorte, null);
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

                        }
                        participante.setCasa(casa);
                        //crear participante
                        participante.setCodigo(codigoNuevoParticipante);
                        if (tieneValor(nombre1)) participante.setNombre1(nombre1);
                        if (tieneValor(nombre2)) participante.setNombre2(nombre2);
                        if (tieneValor(apellido1)) participante.setApellido1(apellido1);
                        if (tieneValor(apellido2)) participante.setApellido2(apellido2);
                        if (tieneValor(nombre1Padre)) {
                            if (tieneValor(nombre1Padre))
                                participante.setNombre1Padre(nombre1Padre);
                            if (tieneValor(nombre2Padre))
                                participante.setNombre2Padre(nombre2Padre);
                            if (tieneValor(apellido1Padre))
                                participante.setApellido1Padre(apellido1Padre);
                            if (tieneValor(apellido2Padre))
                                participante.setApellido2Padre(apellido2Padre);
                        } else {
                            participante.setNombre1Padre(Constants.NA);
                            participante.setNombre2Padre(Constants.NA);
                            participante.setApellido1Padre(Constants.NA);
                            participante.setApellido2Padre(Constants.NA);
                        }
                        if (tieneValor(nombre1Madre)) {
                            if (tieneValor(nombre1Madre))
                                participante.setNombre1Madre(nombre1Madre);
                            if (tieneValor(nombre2Madre))
                                participante.setNombre2Madre(nombre2Madre);
                            if (tieneValor(apellido1Madre))
                                participante.setApellido1Madre(apellido1Madre);
                            if (tieneValor(apellido2Madre))
                                participante.setApellido2Madre(apellido2Madre);
                        } else {
                            participante.setNombre1Madre(Constants.NA);
                            participante.setNombre2Madre(Constants.NA);
                            participante.setApellido1Madre(Constants.NA);
                            participante.setApellido2Madre(Constants.NA);
                        }

                        if (tieneValor(sexo)) {
                            MessageResource catSexo = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + sexo + "' and " + MainDBConstants.catRoot + "='CAT_SEXO'", null);
                            if (catSexo != null) participante.setSexo(catSexo.getCatKey());
                        }
                        participante.setFechaNac(fechaNacimiento);
                        //ahora datos de tutor en tabla participante
                        if (tieneValor(nombre1Tutor)) {
                            participante.setNombre1Tutor(nombre1Tutor);
                        }
                        if (tieneValor(nombre2Tutor)) {
                            participante.setNombre2Tutor(nombre2Tutor);
                        }
                        if (tieneValor(apellido1Tutor)) {
                            participante.setApellido1Tutor(apellido1Tutor);
                        }
                        if (tieneValor(apellido2Tutor)) {
                            participante.setApellido2Tutor(apellido2Tutor);
                        }
                        if (tieneValor(relacionFamiliarTutor)) {
                            MessageResource catRelacionFamiliarTutor = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + relacionFamiliarTutor + "' and " + MainDBConstants.catRoot + "='CAT_RF_TUTOR'", null);
                            if (catRelacionFamiliarTutor != null)
                                participante.setRelacionFamiliarTutor(catRelacionFamiliarTutor.getCatKey());
                        } else {
                            participante.setRelacionFamiliarTutor(Constants.REL_FAM_MISMO_PART);
                            participante.setNombre1Tutor(participante.getNombre1());
                            participante.setNombre2Tutor(participante.getNombre2());
                            participante.setApellido1Tutor(participante.getApellido1());
                            participante.setApellido2Tutor(participante.getApellido2());
                        }
                        //meta data
                        participante.setRecordDate(new Date());
                        participante.setRecordUser(username);
                        participante.setDeviceid(infoMovil.getDeviceId());
                        participante.setEstado('0');
                        participante.setPasive('0');
                        //Guarda nuevo participante
                        estudiosAdapter.crearParticipante(participante);
                        cc.setParticipante(participante);
                        estudiosAdapter.crearCartaConsentimiento(cc);
                        if (tieneValor(tieneTelefonoCel) && tieneTelefonoCel.equalsIgnoreCase(Constants.YES)) {
                            guardarTelefonoContacto(numTelefono1, tipoTeleCel, operadoraTelefono1);
                        }
                        if (tieneValor(tieneOtroTelefonoCel) && tieneOtroTelefonoCel.equalsIgnoreCase(Constants.YES)) {
                            guardarTelefonoContacto(numTelefono2, tipoTeleCel, operadoraTelefono2);
                        }
                        if (tieneValor(tieneTelefonoConv) && tieneTelefonoConv.equalsIgnoreCase(Constants.YES)) {
                            guardarTelefonoContacto(numTelefono3, tipoTeleConv, operadoraTelefono3);
                        }
                        if (!tieneValor(codigoCasaCohorte)) {
                            guardarDatosCoordenadas(coordenadas);
                        }

                        procesos.setCodigo(participante.getCodigo());
                        procesos.setRetirado(0);
                        //aca siempre va a marcar si, porque no hay registro de encuestas, ya que no se descargan del server
                        List<EncuestaCasa> mEncuestasCasas = estudiosAdapter.getEncuestaCasas(MainDBConstants.casa + "=" + casa.getCodigo(), null);
                        if (mEncuestasCasas.size() <= 0) {
                            procesos.setPendienteEnCasa(Constants.YESKEYSND);
                        } else {
                            procesos.setPendienteEnCasa(Constants.NOKEYSND);
                        }
                        List<ObsequioGeneral> mObsequios = estudiosAdapter.getObsequiosGenerales(MainDBConstants.casa + "='" + casa.getCodigo() + "'", null);
                        if (mObsequios.size() <= 0) {
                            procesos.setPendienteObseq(Constants.YESKEYSND);
                        } else {
                            procesos.setPendienteObseq(Constants.NOKEYSND);
                        }

                        procesos.setPendienteEncPart(Constants.YESKEYSND);
                        procesos.setPendientePyT(Constants.YESKEYSND);
                        procesos.setPendienteMxMA(Constants.YESKEYSND);
                        procesos.setPendienteMxTx(Constants.NOKEYSND);

                        //meta data
                        procesos.setRecordDate(new Date());
                        procesos.setRecordUser(username);
                        procesos.setDeviceid(infoMovil.getDeviceId());
                        procesos.setEstado('0');
                        procesos.setPasive('0');
                        estudiosAdapter.crearParticipanteProcesos(procesos);

                        Intent i = new Intent(getApplicationContext(),
                                MenuParticipanteActivity.class);
                        Bundle arguments = new Bundle();
                        arguments.putSerializable(Constants.PARTICIPANTE, participante);
                        i.putExtras(arguments);
                        i.putExtra(Constants.VISITA_EXITOSA, !mUser.getVisitas()); //si hay que pedir la visita, se envia que la visita no es exitosa
                        startActivity(i);
                        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_LONG);
                        toast.show();


                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.noRegistraIngreso), Toast.LENGTH_LONG);
                        toast.show();
                    }
                    finish();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.noRegistraIngreso), Toast.LENGTH_LONG);
                    toast.show();
                    finish();
                }
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.participanteExiste), Toast.LENGTH_LONG);
                toast.show();
                //finish();
            }

        } catch (Exception ex){
            ex.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(), ex.getLocalizedMessage(), Toast.LENGTH_LONG);
            toast.show();
            //finish();
        }finally {
            //Cierra la base de datos
            if (estudiosAdapter!=null)
                estudiosAdapter.close();

        }
    }

    private void guardarTelefonoContacto(String numero, String tipo, String operadora){
        TelefonoContacto telefonoContacto =new TelefonoContacto();
        telefonoContacto.setId(infoMovil.getId());
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
    }

    private void guardarDatosCoordenadas(String coordenadas) {
        DatosCoordenadas mCoordenadas = new DatosCoordenadas();
        UUID deviceUuid = new UUID(infoMovil.getDeviceId().hashCode(), new Date().hashCode());
        mCoordenadas.setCodigo(deviceUuid.toString());
        mCoordenadas.setRecordDate(new Date());
        mCoordenadas.setRecordUser(username);
        mCoordenadas.setDeviceid(infoMovil.getDeviceId());
        mCoordenadas.setEstado(Constants.STATUS_NOT_SUBMITTED);
        mCoordenadas.setPasive(Constants.STATUS_NOT_PASIVE);
        mCoordenadas.setParticipante(participante);
        mCoordenadas.setCodigoCasa(participante.getCasa().getCodigo());
        mCoordenadas.setEstudios("A2CARES");
        mCoordenadas.setMotivo("1");
        //se toman los datos actuales del participante como validos
        mCoordenadas.setBarrio(participante.getCasa().getBarrio());
        mCoordenadas.setManzana(participante.getCasa().getManzana() != null ? Integer.parseInt(participante.getCasa().getManzana()) : 0);
        mCoordenadas.setDireccion(participante.getCasa().getDireccion());
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
            // TODO: be smarter about this
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
                                    ((NuevoTamizajeActivity) Objects.requireNonNull(getActivity())).saveData();
                                }
                            }
                    )
                    .setNegativeButton(R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((NuevoTamizajeActivity) Objects.requireNonNull(getActivity())).createDialog(EXIT);;
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
                catRelFamMayorEdad = estudiosAdapter.getSpanishMessageResources(MainDBConstants.catRoot + "='CAT_RF_TUTOR'", MainDBConstants.order);
                catRelFamMenorEdad = estudiosAdapter.getSpanishMessageResources(MainDBConstants.catRoot + "='CAT_RF_TUTOR' and "+MainDBConstants.catKey + " != '8'", MainDBConstants.order);
                catVerifTutNoAlf = estudiosAdapter.getSpanishMessageResources(MainDBConstants.catRoot + "='CAT_VERIFICA'", MainDBConstants.order);
                catVerifTutAlf = estudiosAdapter.getSpanishMessageResources(MainDBConstants.catKey + " in ('1','2','3','6') and " + MainDBConstants.catRoot + "='CAT_VERIFICA'", MainDBConstants.order);
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
}
