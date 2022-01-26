package ni.org.ics.a2cares.app.ui.activities.enterdata;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ni.org.ics.a2cares.app.AbstractAsyncActivity;
import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.domain.core.ParticipanteProcesos;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.domain.survey.EncuestaParticipante;
import ni.org.ics.a2cares.app.preferences.PreferencesActivity;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuParticipanteActivity;
import ni.org.ics.a2cares.app.ui.forms.EncuestaParticipanteForm;
import ni.org.ics.a2cares.app.ui.forms.EncuestaParticipanteFormLabels;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.utils.DateUtil;
import ni.org.ics.a2cares.app.utils.DeviceInfo;
import ni.org.ics.a2cares.app.utils.FileUtils;
import ni.org.ics.a2cares.app.utils.StringUtil;
import ni.org.ics.a2cares.app.wizard.model.AbstractWizardModel;
import ni.org.ics.a2cares.app.wizard.model.BarcodePage;
import ni.org.ics.a2cares.app.wizard.model.DatePage;
import ni.org.ics.a2cares.app.wizard.model.LabelPage;
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


public class NuevaEncuestaParticipanteActivity extends AbstractAsyncActivity implements
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
    private EncuestaParticipanteFormLabels labels = new EncuestaParticipanteFormLabels();
    private EstudioDBAdapter estudiosAdapter;
    private DeviceInfo infoMovil;
    private String username;
    private SharedPreferences settings;
    private static final int EXIT = 1;
    private AlertDialog alertDialog;
    private boolean notificarCambios = true;
    private Participante participante;
    private boolean visitaExitosa = false;
    private int anios = 0;

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
        infoMovil = new DeviceInfo(NuevaEncuestaParticipanteActivity.this);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(),mPass,false,false);

        participante = (Participante) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
        visitaExitosa = getIntent().getBooleanExtra(Constants.VISITA_EXITOSA, false);

        mWizardModel = new EncuestaParticipanteForm(this,mPass);
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

        String[] edad = participante.getEdad().split("/");
        if (edad.length > 0) {
            anios = Integer.parseInt(edad[0]);
        }

        int anioNacimiento = DateUtil.getYear(participante.getFechaNac());
        int anioActual = DateUtil.getActualYear();
        boolean esMujer = participante.getSexo().equalsIgnoreCase("F");

        //Solo para mayores o iguales de 13 y menores de 18.
        if (anios >= 13 && anios < 18 ){
            changeStatus(mWizardModel.findByKey(labels.getEmancipado()), true);
            changeStatus(mWizardModel.findByKey(labels.getDescEmancipado()), true);
            changeStatus(mWizardModel.findByKey(labels.getOtraDescEmanc()), true);

            onPageTreeChanged();
        }
        //Solo para mujeres mayores o iguales a 18 años y menores o iguales a 50
        if (esMujer && anios >= 18 && anios <= 50 ){
            changeStatus(mWizardModel.findByKey(labels.getEmbarazada()), true);
            changeStatus(mWizardModel.findByKey(labels.getConoceFUR()), true);
            changeStatus(mWizardModel.findByKey(labels.getFur()), true);

            onPageTreeChanged();
        }
        //Solo para menores de 18 años
        if (anios < 18 ){
            changeStatus(mWizardModel.findByKey(labels.getAsisteEscuela()), true);
            changeStatus(mWizardModel.findByKey(labels.getGradoEstudia()), true);
            changeStatus(mWizardModel.findByKey(labels.getNombreEscuela()), true);
            changeStatus(mWizardModel.findByKey(labels.getTurno()), true);
            changeStatus(mWizardModel.findByKey(labels.getCuidan()), true);
            changeStatus(mWizardModel.findByKey(labels.getCuantosCuidan()), true);
            changeStatus(mWizardModel.findByKey(labels.getNombreCDI()), false);
            changeStatus(mWizardModel.findByKey(labels.getDireccionCDI()), false);
            changeStatus(mWizardModel.findByKey(labels.getOtroLugarCuidan()), false);

            onPageTreeChanged();

            changeStatus(mWizardModel.findByKey(labels.getPersonaVive()), true);
            changeStatus(mWizardModel.findByKey(labels.getOrdenNac()), true);
            changeStatus(mWizardModel.findByKey(labels.getPadreAlfabeto()), true);
            changeStatus(mWizardModel.findByKey(labels.getNivelEscolarPadre()), true);
            changeStatus(mWizardModel.findByKey(labels.getTrabajaPadre()), true);
            changeStatus(mWizardModel.findByKey(labels.getTipoTrabajoPadre()), true);
            changeStatus(mWizardModel.findByKey(labels.getMadreAlfabeta()), true);
            changeStatus(mWizardModel.findByKey(labels.getNivelEscolarMadre()), true);
            changeStatus(mWizardModel.findByKey(labels.getTrabajaMadre()), true);
            changeStatus(mWizardModel.findByKey(labels.getTipoTrabajoMadre()), true);

            onPageTreeChanged();

        } else { //para 18 a'os o mas
            changeStatus(mWizardModel.findByKey(labels.getFuma()), true);
            changeStatus(mWizardModel.findByKey(labels.getPeriodicidadFuma()), true);
            changeStatus(mWizardModel.findByKey(labels.getCantidadCigarrillos()), true);
            changeStatus(mWizardModel.findByKey(labels.getFumaDentroCasa()), true);

            onPageTreeChanged();

            /*
            changeStatus(mWizardModel.findByKey(labels.getVacunaCovid()), true);
            changeStatus(mWizardModel.findByKey(labels.getAnioVacunaCovid()), true);
            changeStatus(mWizardModel.findByKey(labels.getMesVacunaCovid()), true);
            changeStatus(mWizardModel.findByKey(labels.getTomarFotoTarjetaCovid()), true);

            onPageTreeChanged();*/
        }
        //Para mayores de 14 años
        if (anios >= 14 ) {
            changeStatus(mWizardModel.findByKey(labels.getTrabaja()), true);
            changeStatus(mWizardModel.findByKey(labels.getTipoTrabajo()), true);
            changeStatus(mWizardModel.findByKey(labels.getOcupacionActual()), true);

            onPageTreeChanged();
        }

        //Solo para menores o igual de 6 años
        if (anios <= 6 ) {
            changeStatus(mWizardModel.findByKey(labels.getTieneTarjetaVacuna()), true);
            changeStatus(mWizardModel.findByKey(labels.getTomarFotoTarjeta()), false);

            onPageTreeChanged();
        }

        NumberPage pageTmp = (NumberPage) mWizardModel.findByKey(labels.getAnioDiagTubpulActual());
        if (pageTmp!=null) pageTmp.setRangeValidation(true, anioNacimiento, anioActual);

        pageTmp = (NumberPage) mWizardModel.findByKey(labels.getAnioDiagTubpulPasado());
        if (pageTmp!=null) pageTmp.setRangeValidation(true, anioNacimiento, anioActual);

        pageTmp = (NumberPage) mWizardModel.findByKey(labels.getAnioAlergiaRespiratoria());
        if (pageTmp!=null) pageTmp.setRangeValidation(true, anioNacimiento, anioActual);

        pageTmp = (NumberPage) mWizardModel.findByKey(labels.getAnioAsma());
        if (pageTmp!=null) pageTmp.setRangeValidation(true, anioNacimiento, anioActual);

        pageTmp = (NumberPage) mWizardModel.findByKey(labels.getAnioCardiopatia());
        if (pageTmp!=null) pageTmp.setRangeValidation(true, anioNacimiento, anioActual);

        pageTmp = (NumberPage) mWizardModel.findByKey(labels.getAnioEnfermPulmonarObstCronica());
        if (pageTmp!=null) pageTmp.setRangeValidation(true, anioNacimiento, anioActual);

        pageTmp = (NumberPage) mWizardModel.findByKey(labels.getAnioDiabetes());
        if (pageTmp!=null) pageTmp.setRangeValidation(true, anioNacimiento, anioActual);

        pageTmp = (NumberPage) mWizardModel.findByKey(labels.getAnioPresionAlta());
        if (pageTmp!=null) pageTmp.setRangeValidation(true, anioNacimiento, anioActual);

        pageTmp = (NumberPage) mWizardModel.findByKey(labels.getAnioDengue());
        if (pageTmp!=null) pageTmp.setRangeValidation(true, anioNacimiento, anioActual);

        pageTmp = (NumberPage) mWizardModel.findByKey(labels.getAnioZika());
        if (pageTmp!=null) pageTmp.setRangeValidation(true, anioNacimiento, anioActual);

        pageTmp = (NumberPage) mWizardModel.findByKey(labels.getAnioChik());
        if (pageTmp!=null) pageTmp.setRangeValidation(true, anioNacimiento, anioActual);

        pageTmp = (NumberPage) mWizardModel.findByKey(labels.getAnioVacunaFiebreAma());
        if (pageTmp!=null) pageTmp.setRangeValidation(true, anioNacimiento, anioActual);

        pageTmp = (NumberPage) mWizardModel.findByKey(labels.getAnioVacunaCovid());
        if (pageTmp!=null) pageTmp.setRangeValidation(true, 2020, anioActual);

        onPageTreeChanged();
        updateBottomBar();
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
                        Bundle arguments = new Bundle();
                        arguments.putSerializable(Constants.PARTICIPANTE , participante);
                        Intent i = new Intent(getApplicationContext(),
                                MenuParticipanteActivity.class);
                        i.putExtra(Constants.VISITA_EXITOSA, visitaExitosa);
                        i.putExtras(arguments);
                        startActivity(i);
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
                if((StringUtil.isNumeric(valor)) && (np.ismValRange() && (np.getmGreaterOrEqualsThan() > Double.parseDouble(valor) || np.getmLowerOrEqualsThan() < Double.parseDouble(valor)))
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
            if(page.getTitle().equals(labels.getEmancipado())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getDescEmancipado()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOtraDescEmanc()), visible);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getDescEmancipado())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otro");
                changeStatus(mWizardModel.findByKey(labels.getOtraDescEmanc()), visible);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getEmbarazada())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getConoceFUR()), visible);
                changeStatus(mWizardModel.findByKey(labels.getFur()), visible);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getConoceFUR())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getFur()), visible);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getAsisteEscuela())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getGradoEstudia()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNombreEscuela()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTurno()), visible);

                changeStatus(mWizardModel.findByKey(labels.getCuidan()), !visible);
                changeStatus(mWizardModel.findByKey(labels.getOtroLugarCuidan()), false);
                changeStatus(mWizardModel.findByKey(labels.getNombreCDI()), !visible);
                changeStatus(mWizardModel.findByKey(labels.getDireccionCDI()), !visible);
                changeStatus(mWizardModel.findByKey(labels.getCuantosCuidan()), !visible);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getGradoEstudia())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null &&
                        (page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Preescolar") || page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Primaria"));
                changeStatus(mWizardModel.findByKey(labels.getAlfabeto()), visible);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getCuidan())){
                boolean otro  = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otro lugar");
                boolean cdi = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("CDI");
                changeStatus(mWizardModel.findByKey(labels.getNombreCDI()), cdi);
                changeStatus(mWizardModel.findByKey(labels.getDireccionCDI()), cdi);
                changeStatus(mWizardModel.findByKey(labels.getOtroLugarCuidan()), otro);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getTrabaja())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTipoTrabajo()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOcupacionActual()), visible);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getTrabajaPadre())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTipoTrabajoPadre()), visible);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getTrabajaMadre())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTipoTrabajoMadre()), visible);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getComparteHab())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getHab1()), visible);
                changeStatus(mWizardModel.findByKey(labels.getHab2()), visible);
                changeStatus(mWizardModel.findByKey(labels.getHab3()), visible);
                changeStatus(mWizardModel.findByKey(labels.getHab4()), visible);
                changeStatus(mWizardModel.findByKey(labels.getHab5()), visible);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getHab1())){
                String valor = "0";
                if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null) valor = page.getData().getString(TextPage.SIMPLE_DATA_KEY);

                NumberPage pageTmp = (NumberPage) mWizardModel.findByKey(labels.getCama1());
                if (pageTmp!=null) pageTmp.setRangeValidation(true, 0, Integer.parseInt(valor));

                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getHab2())){
                String valor = "0";
                if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null) valor = page.getData().getString(TextPage.SIMPLE_DATA_KEY);

                NumberPage pageTmp = (NumberPage) mWizardModel.findByKey(labels.getCama2());
                if (pageTmp!=null) pageTmp.setRangeValidation(true, 0, Integer.parseInt(valor));

                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getHab3())){
                String valor = "0";
                if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null) valor = page.getData().getString(TextPage.SIMPLE_DATA_KEY);

                NumberPage pageTmp = (NumberPage) mWizardModel.findByKey(labels.getCama3());
                if (pageTmp!=null) pageTmp.setRangeValidation(true, 0, Integer.parseInt(valor));

                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getHab4())){
                String valor = "0";
                if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null) valor = page.getData().getString(TextPage.SIMPLE_DATA_KEY);

                NumberPage pageTmp = (NumberPage) mWizardModel.findByKey(labels.getCama4());
                if (pageTmp!=null) pageTmp.setRangeValidation(true, 0, Integer.parseInt(valor));

                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getHab5())){
                String valor = "0";
                if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null) valor = page.getData().getString(TextPage.SIMPLE_DATA_KEY);

                NumberPage pageTmp = (NumberPage) mWizardModel.findByKey(labels.getCama5());
                if (pageTmp!=null) pageTmp.setRangeValidation(true, 0, Integer.parseInt(valor));

                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getComparteCama())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCama1()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCama2()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCama3()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCama4()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCama5()), visible);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getFuma())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getPeriodicidadFuma()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCantidadCigarrillos()), visible);
                changeStatus(mWizardModel.findByKey(labels.getFumaDentroCasa()), visible);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getTuberculosisPulmonarActual())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAnioDiagTubpulActual()), visible);
                changeStatus(mWizardModel.findByKey(labels.getMesDiagTubpulActual()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTratamientoTubpulActual()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCompletoTratamientoTubpulActual()), false);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getTratamientoTubpulActual())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getCompletoTratamientoTubpulActual()), visible);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getTuberculosisPulmonarPasado())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAnioDiagTubpulPasado()), visible);
                changeStatus(mWizardModel.findByKey(labels.getMesDiagTubpulPasado()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTratamientoTubpulPasado()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCompletoTratamientoTubpulPas()), false);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getTratamientoTubpulPasado())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.NO);
                changeStatus(mWizardModel.findByKey(labels.getCompletoTratamientoTubpulPas()), visible);
                onPageTreeChanged();
            }
            //22.	¿Al participante le han diagnosticado algún tipo de alergia respiratoria?
            if(page.getTitle().equals(labels.getAlergiaRespiratoria())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAnioAlergiaRespiratoria()), visible);
                onPageTreeChanged();
            }
            //23.	¿El médico le ha diagnosticado asma al participante?
            if(page.getTitle().equals(labels.getAsma())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAnioAsma()), visible);
                onPageTreeChanged();
            }
            //24.	El participante padece alguna enfermedad crónica?
            if(page.getTitle().equals(labels.getEnfermedadCronica())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCardiopatia()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAnioCardiopatia()), false);
                changeStatus(mWizardModel.findByKey(labels.getEnfermPulmonarObstCronica()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAnioEnfermPulmonarObstCronica()), false);
                changeStatus(mWizardModel.findByKey(labels.getDiabetes()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAnioDiabetes()), false);
                changeStatus(mWizardModel.findByKey(labels.getPresionAlta()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAnioPresionAlta()), false);
                changeStatus(mWizardModel.findByKey(labels.getOtrasEnfermedades()), visible);
                changeStatus(mWizardModel.findByKey(labels.getDescOtrasEnfermedades()), false);
                onPageTreeChanged();
            }
            //24.1	¿Al participante le han diagnosticado Cardiopatía?
            if(page.getTitle().equals(labels.getCardiopatia())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAnioCardiopatia()), visible);
                onPageTreeChanged();
            }
            //24.2	¿Al participante le han diagnosticado enfermedad pulmonar obstructiva crónica?
            if(page.getTitle().equals(labels.getEnfermPulmonarObstCronica())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAnioEnfermPulmonarObstCronica()), visible);
                onPageTreeChanged();
            }
            //24.3	¿Al participante le han diagnosticado diabetes?
            if(page.getTitle().equals(labels.getDiabetes())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAnioDiabetes()), visible);
                onPageTreeChanged();
            }
            //24.4	¿Al participante le han diagnosticado presión alta?
            if(page.getTitle().equals(labels.getPresionAlta())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAnioPresionAlta()), visible);
                onPageTreeChanged();
            }
            //24.5	¿Al participante le han diagnosticado otras enfermedades?
            if(page.getTitle().equals(labels.getOtrasEnfermedades())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getDescOtrasEnfermedades()), visible);
                onPageTreeChanged();
            }
            //25.	¿El participante ha tenido dengue en algún momento en la vida?
            if(page.getTitle().equals(labels.getTenidoDengue())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAnioDengue()), visible);
                changeStatus(mWizardModel.findByKey(labels.getDiagMedicoDengue()), visible);
                changeStatus(mWizardModel.findByKey(labels.getDondeDengue()), visible);
                onPageTreeChanged();
            }
            //26.	¿El participante ha tenido Zika en algún momento en la vida?
            if(page.getTitle().equals(labels.getTenidoZika())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAnioZika()), visible);
                changeStatus(mWizardModel.findByKey(labels.getDiagMedicoZika()), visible);
                changeStatus(mWizardModel.findByKey(labels.getDondeZika()), visible);
                onPageTreeChanged();
            }
            //27.	¿El participante ha tenido Chikungunya en algún momento en la vida?
            if(page.getTitle().equals(labels.getTenidoChik())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAnioChik()), visible);
                changeStatus(mWizardModel.findByKey(labels.getDiagMedicoChik()), visible);
                changeStatus(mWizardModel.findByKey(labels.getDondeChik()), visible);
                onPageTreeChanged();
            }
            //28.	¿El participante fue vacunado contra la fiebre amarilla?
            if(page.getTitle().equals(labels.getVacunaFiebreAma())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAnioVacunaFiebreAma()), visible);
                onPageTreeChanged();
            }
            //29.	¿El participante fue vacunado contra Covid?
            if(page.getTitle().equals(labels.getVacunaCovid())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAnioVacunaCovid()), visible);
                changeStatus(mWizardModel.findByKey(labels.getMesVacunaCovid()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTomarFotoTarjetaCovid()), visible);
                onPageTreeChanged();
            }
            //30.	Tiene tarjeta de vacunaciónón
            if(page.getTitle().equals(labels.getTieneTarjetaVacuna())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTomarFotoTarjeta()), visible);
                onPageTreeChanged();
            }
            //31.	¿El participante ha tenido fiebre en los últimos 2 meses?
            if(page.getTitle().equals(labels.getFiebre())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTiempoFiebre()), visible);
                changeStatus(mWizardModel.findByKey(labels.getLugarConsFiebre()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAutomedicoFiebre()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNoAcudioCs()), false);
                onPageTreeChanged();
            }
            //31.1.1	¿Dónde acudió a consulta el participante cuándo tuvo fiebre?
            if(page.getTitle().equals(labels.getLugarConsFiebre())){
                boolean cs = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("CS Edgar Lang");
                boolean ps = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Puesto Médico");
                changeStatus(mWizardModel.findByKey(labels.getNoAcudioCs()), !cs && !ps);
                onPageTreeChanged();
            }
            //32.	¿El participante ha tenido dengue durante el último año?
            if(page.getTitle().equals(labels.getTenidoDengueUA())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getUnidadSaludDengue()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCentroSaludDengue()), false);
                changeStatus(mWizardModel.findByKey(labels.getOtroCentroSaludDengue()), false);
                changeStatus(mWizardModel.findByKey(labels.getPuestoSaludDengue()), false);
                changeStatus(mWizardModel.findByKey(labels.getOtroPuestoSaludDengue()), false);
                changeStatus(mWizardModel.findByKey(labels.getHospitalDengue()), false);
                changeStatus(mWizardModel.findByKey(labels.getOtroHospitalDengue()), false);
                changeStatus(mWizardModel.findByKey(labels.getAmbulatorioDengue()), false);
                changeStatus(mWizardModel.findByKey(labels.getHospitalizadoDengue()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAmbulatorioDengue()), false);
                changeStatus(mWizardModel.findByKey(labels.getDiagnosticoDengue()), visible);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getUnidadSaludDengue())){
                boolean cs = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Centro de Salud");
                boolean ps = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Puesto de Salud");
                boolean hp = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).contains("Hospital");
                changeStatus(mWizardModel.findByKey(labels.getCentroSaludDengue()), cs);
                changeStatus(mWizardModel.findByKey(labels.getOtroCentroSaludDengue()), false);
                changeStatus(mWizardModel.findByKey(labels.getPuestoSaludDengue()), ps);
                changeStatus(mWizardModel.findByKey(labels.getOtroPuestoSaludDengue()), false);
                changeStatus(mWizardModel.findByKey(labels.getHospitalDengue()), hp);
                changeStatus(mWizardModel.findByKey(labels.getOtroHospitalDengue()), false);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getCentroSaludDengue())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otro");
                changeStatus(mWizardModel.findByKey(labels.getOtroCentroSaludDengue()), visible);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getPuestoSaludDengue())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otro");
                changeStatus(mWizardModel.findByKey(labels.getOtroPuestoSaludDengue()), visible);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getHospitalDengue())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otro");
                changeStatus(mWizardModel.findByKey(labels.getOtroHospitalDengue()), visible);
                onPageTreeChanged();
            }

            //33.	¿El participante ha presentado (rash) enrojecimiento de la piel similar a alergia durante el último año?
            if(page.getTitle().equals(labels.getRashUA())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getRecuerdaMesRash()), visible);
                changeStatus(mWizardModel.findByKey(labels.getRashMes()), false);
                changeStatus(mWizardModel.findByKey(labels.getRashCara()), visible);
                changeStatus(mWizardModel.findByKey(labels.getRashMiembrosSup()), visible);
                changeStatus(mWizardModel.findByKey(labels.getRashTorax()), visible);
                changeStatus(mWizardModel.findByKey(labels.getRashAbdomen()), visible);
                changeStatus(mWizardModel.findByKey(labels.getRashMiembrosInf()), visible);
                changeStatus(mWizardModel.findByKey(labels.getRashDias()), visible);
                changeStatus(mWizardModel.findByKey(labels.getConsultaRashUA()), visible);
                changeStatus(mWizardModel.findByKey(labels.getUnidadSaludRash()), false);
                changeStatus(mWizardModel.findByKey(labels.getCentroSaludRash()), false);
                changeStatus(mWizardModel.findByKey(labels.getOtroCentroSaludRash()), false);
                changeStatus(mWizardModel.findByKey(labels.getPuestoSaludRash()), false);
                changeStatus(mWizardModel.findByKey(labels.getOtroPuestoSaludRash()), false);
                changeStatus(mWizardModel.findByKey(labels.getDiagnosticoRash()), false);
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getRecuerdaMesRash())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getRashMes()), visible);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getConsultaRashUA())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getUnidadSaludRash()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCentroSaludRash()), false);
                changeStatus(mWizardModel.findByKey(labels.getOtroCentroSaludRash()), false);
                changeStatus(mWizardModel.findByKey(labels.getPuestoSaludRash()), false);
                changeStatus(mWizardModel.findByKey(labels.getOtroPuestoSaludRash()), false);
                changeStatus(mWizardModel.findByKey(labels.getDiagnosticoRash()), visible);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getUnidadSaludRash())){
                boolean cs = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Centro de Salud");
                boolean ps = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Puesto de Salud");
                changeStatus(mWizardModel.findByKey(labels.getCentroSaludRash()), cs);
                changeStatus(mWizardModel.findByKey(labels.getOtroCentroSaludRash()), false);
                changeStatus(mWizardModel.findByKey(labels.getPuestoSaludRash()), ps);
                changeStatus(mWizardModel.findByKey(labels.getOtroPuestoSaludRash()), false);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getCentroSaludRash())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otro");
                changeStatus(mWizardModel.findByKey(labels.getOtroCentroSaludRash()), visible);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getPuestoSaludRash())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otro");
                changeStatus(mWizardModel.findByKey(labels.getOtroPuestoSaludRash()), visible);
                onPageTreeChanged();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

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
            TextPage modifPage = (TextPage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.NumberPage")){
            NumberPage modifPage = (NumberPage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.MultipleFixedChoicePage")){
            MultipleFixedChoicePage modifPage = (MultipleFixedChoicePage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.DatePage")){
            DatePage modifPage = (DatePage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.SelectCasaPage")){
            SelectCasaPage modifPage = (SelectCasaPage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
        }
    }

    private boolean tieneValor(String entrada){
        return (entrada != null && !entrada.isEmpty());
    }

    private String getKeyFromCatalog(String campo, String catalogo) {
        if (tieneValor(campo)) {
            MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + campo + "' and " + MainDBConstants.catRoot + "='"+ catalogo +"'", null);
            if (messageResource != null) return messageResource.getCatKey();
            else return null;
        } else return null;
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

            String emancipado = datos.getString(this.getString(R.string.emancipado));
            String descEmancipado = datos.getString(this.getString(R.string.descEmancipado));
            String otraDescEmanc = datos.getString(this.getString(R.string.otraDescEmanc));
            String embarazada = datos.getString(this.getString(R.string.embarazada));
            String conoceFUR = datos.getString(this.getString(R.string.conoceFUR));
            String fur = datos.getString(this.getString(R.string.fur));
            String asisteEscuela = datos.getString(this.getString(R.string.asisteEscuela));
            String gradoEstudia = datos.getString(this.getString(R.string.gradoEstudia));
            String nombreEscuela = datos.getString(this.getString(R.string.nombreEscuela));
            String turno = datos.getString(this.getString(R.string.turno));
            String cuidan = datos.getString(this.getString(R.string.cuidan));
            String cuantosCuidan = datos.getString(this.getString(R.string.cuantosCuidan));
            String nombreCDI = datos.getString(this.getString(R.string.nombreCDI));
            String direccionCDI = datos.getString(this.getString(R.string.direccionCDI));
            String otroLugarCuidan = datos.getString(this.getString(R.string.otroLugarCuidan));
            String alfabeto = datos.getString(this.getString(R.string.alfabeto));
            String nivelEducacion = datos.getString(this.getString(R.string.nivelEducacion));
            String trabaja = datos.getString(this.getString(R.string.trabaja));
            String tipoTrabajo = datos.getString(this.getString(R.string.tipoTrabajo));
            String ocupacionActual = datos.getString(this.getString(R.string.ocupacionActual));
            String personaVive = datos.getString(this.getString(R.string.personaVive));
            String ordenNac = datos.getString(this.getString(R.string.ordenNac));
            String padreAlfabeto = datos.getString(this.getString(R.string.padreAlfabeto));
            String papaNivel = datos.getString(this.getString(R.string.papaNivel));
            String trabajaPadre = datos.getString(this.getString(R.string.trabajaPadre));
            String papaTipoTra = datos.getString(this.getString(R.string.papaTipoTra));
            String madreAlfabeta = datos.getString(this.getString(R.string.madreAlfabeta));
            String mamaNivel = datos.getString(this.getString(R.string.mamaNivel));
            String trabajaMadre = datos.getString(this.getString(R.string.trabajaMadre));
            String mamaTipoTra = datos.getString(this.getString(R.string.mamaTipoTra));
            String comparteHab = datos.getString(this.getString(R.string.comparteHab));
            String hab1 = datos.getString(this.getString(R.string.hab1));
            String hab2 = datos.getString(this.getString(R.string.hab2));
            String hab3 = datos.getString(this.getString(R.string.hab3));
            String hab4 = datos.getString(this.getString(R.string.hab4));
            String hab5 = datos.getString(this.getString(R.string.hab5));
            String comparteCama = datos.getString(this.getString(R.string.comparteCama));
            String cama1 = datos.getString(this.getString(R.string.cama1));
            String cama2 = datos.getString(this.getString(R.string.cama2));
            String cama3 = datos.getString(this.getString(R.string.cama3));
            String cama4 = datos.getString(this.getString(R.string.cama4));
            String cama5 = datos.getString(this.getString(R.string.cama5));
            String fuma = datos.getString(this.getString(R.string.fuma));
            String periodicidadFuma = datos.getString(this.getString(R.string.periodicidadFuma));
            String cantidadCigarrillos = datos.getString(this.getString(R.string.cantidadCigarrillos));
            String fumaDentroCasa = datos.getString(this.getString(R.string.fumaDentroCasa));
            String tuberculosisPulmonarActual = datos.getString(this.getString(R.string.tuberculosisPulmonarActual));
            String anioDiagTubpulActual = datos.getString(this.getString(R.string.anioDiagTubpulActual));
            String mesDiagTubpulActual = datos.getString(this.getString(R.string.mesDiagTubpulActual));
            String tratamientoTubpulActual = datos.getString(this.getString(R.string.tratamientoTubpulActual));
            String completoTratamientoTubpulActual = datos.getString(this.getString(R.string.completoTratamientoTubpulActual));
            String tuberculosisPulmonarPasado = datos.getString(this.getString(R.string.tuberculosisPulmonarPasado));
            String fechaDiagTubpulPasadoDes = datos.getString(this.getString(R.string.fechaDiagTubpulPasadoDes));
            String anioDiagTubpulPasado = datos.getString(this.getString(R.string.anioDiagTubpulPasado));
            String mesDiagTubpulPasado = datos.getString(this.getString(R.string.mesDiagTubpulPasado));
            String tratamientoTubpulPasado = datos.getString(this.getString(R.string.tratamientoTubpulPasado));
            String completoTratamientoTubpulPas = datos.getString(this.getString(R.string.completoTratamientoTubpulPas));
            String alergiaRespiratoria = datos.getString(this.getString(R.string.alergiaRespiratoria));
            String anioAlergiaRespiratoria = datos.getString(this.getString(R.string.alergiaRespiratoriaAnio));
            String asma = datos.getString(this.getString(R.string.asma));
            String anioAsma = datos.getString(this.getString(R.string.asmaAnio));
            String enfermedadCronica = datos.getString(this.getString(R.string.enfermedadCronica));
            String cardiopatia = datos.getString(this.getString(R.string.cardiopatia));
            String anioCardiopatia = datos.getString(this.getString(R.string.cardiopatiaAnio));
            String enfermPulmonarObstCronica = datos.getString(this.getString(R.string.enfermPulmonarObstCronica));
            String anioEnfermPulmonarObstCronica = datos.getString(this.getString(R.string.enfermPulmonarObstCronicaAnio));
            String diabetes = datos.getString(this.getString(R.string.diabetes));
            String anioDiabetes = datos.getString(this.getString(R.string.diabetesAnio));
            String presionAlta = datos.getString(this.getString(R.string.presionAlta));
            String anioPresionAlta = datos.getString(this.getString(R.string.presionAltaAnio));
            String otrasEnfermedades = datos.getString(this.getString(R.string.otrasEnfermedades));
            String descOtrasEnfermedades = datos.getString(this.getString(R.string.descOtrasEnfermedades));
            String tenidoDengue = datos.getString(this.getString(R.string.tenidoDengue));
            String anioDengue = datos.getString(this.getString(R.string.anioDengue));
            String diagMedicoDengue = datos.getString(this.getString(R.string.diagMedicoDengue));
            String dondeDengue = datos.getString(this.getString(R.string.dondeDengue));
            String tenidoZika = datos.getString(this.getString(R.string.tenidoZika));
            String anioZika = datos.getString(this.getString(R.string.anioZika));
            String diagMedicoZika = datos.getString(this.getString(R.string.diagMedicoZika));
            String dondeZika = datos.getString(this.getString(R.string.dondeZika));
            String tenidoChik = datos.getString(this.getString(R.string.tenidoChik));
            String anioChik = datos.getString(this.getString(R.string.anioChik));
            String diagMedicoChik = datos.getString(this.getString(R.string.diagMedicoChik));
            String dondeChik = datos.getString(this.getString(R.string.dondeChik));
            String vacunaFiebreAma = datos.getString(this.getString(R.string.vacunaFiebreAma));
            String anioVacunaFiebreAma = datos.getString(this.getString(R.string.anioVacunaFiebreAma));
            String vacunaCovid = datos.getString(this.getString(R.string.vacunaCovid));
            String anioVacunaCovid = datos.getString(this.getString(R.string.anioVacunaCovid));
            String mesVacunaCovid = datos.getString(this.getString(R.string.mesVacunaCovid));
            String tieneTarjetaVacuna = datos.getString(this.getString(R.string.tieneTarjetaVacuna));
            String tomarFotoTarjeta = datos.getString(this.getString(R.string.tomarFotoTarjeta));
            String tomarFotoTarjetaCovid = datos.getString(this.getString(R.string.tomarFotoTarjetaCovid));
            String fiebre = datos.getString(this.getString(R.string.fiebre));
            String tiempoFiebre = datos.getString(this.getString(R.string.tiempoFiebre));
            String lugarConsFiebre = datos.getString(this.getString(R.string.lugarConsFiebre));
            String noAcudioCs = datos.getString(this.getString(R.string.noAcudioCs));
            String automedicoFiebre = datos.getString(this.getString(R.string.automedicoFiebre));
            String tenidoDengueUA = datos.getString(this.getString(R.string.tenidoDengueUA));
            String unidadSaludDengue = datos.getString(this.getString(R.string.unidadSaludDengue));
            String centroSaludDengue = datos.getString(this.getString(R.string.centroSaludDengue));
            String otroCentroSaludDengue = datos.getString(this.getString(R.string.otroCentroSaludDengue));
            String puestoSaludDengue = datos.getString(this.getString(R.string.puestoSaludDengue));
            String otroPuestoSaludDengue = datos.getString(this.getString(R.string.otroPuestoSaludDengue));
            String hospitalDengue = datos.getString(this.getString(R.string.hospitalDengue));
            String otroHospitalDengue = datos.getString(this.getString(R.string.otroHospitalDengue));
            String hospitalizadoDengue = datos.getString(this.getString(R.string.hospitalizadoDengue));
            String ambulatorioDengue = datos.getString(this.getString(R.string.ambulatorioDengue));
            String diagnosticoDengue = datos.getString(this.getString(R.string.diagnosticoDengue));
            String rashUA = datos.getString(this.getString(R.string.rashUA));
            String recuerdaMesRash = datos.getString(this.getString(R.string.recuerdaMesRash));
            String rashMes = datos.getString(this.getString(R.string.rashMes));
            String rashCara = datos.getString(this.getString(R.string.rashCara));
            String rashMiembrosSup = datos.getString(this.getString(R.string.rashMiembrosSup));
            String rashTorax = datos.getString(this.getString(R.string.rashTorax));
            String rashAbdomen = datos.getString(this.getString(R.string.rashAbdomen));
            String rashMiembrosInf = datos.getString(this.getString(R.string.rashMiembrosInf));
            String rashDias = datos.getString(this.getString(R.string.rashDias));
            String consultaRashUA = datos.getString(this.getString(R.string.consultaRashUA));
            String unidadSaludRash = datos.getString(this.getString(R.string.unidadSaludRash));
            String centroSaludRash = datos.getString(this.getString(R.string.centroSaludRash));
            String otroCentroSaludRash = datos.getString(this.getString(R.string.otroCentroSaludRash));
            String puestoSaludRash = datos.getString(this.getString(R.string.puestoSaludRash));
            String otroPuestoSaludRash = datos.getString(this.getString(R.string.otroPuestoSaludRash));
            String diagnosticoRash = datos.getString(this.getString(R.string.diagnosticoRash));


            EncuestaParticipante encuestaParticipante = new EncuestaParticipante();
            encuestaParticipante.setCodigo(infoMovil.getId());
            encuestaParticipante.setRecordDate(new Date());
            encuestaParticipante.setRecordUser(username);
            encuestaParticipante.setDeviceid(infoMovil.getDeviceId());
            encuestaParticipante.setEstado(Constants.STATUS_NOT_SUBMITTED);
            encuestaParticipante.setPasive(Constants.STATUS_NOT_PASIVE);
            encuestaParticipante.setParticipante(participante);
            encuestaParticipante.setFechaEncuesta(new Date());
            encuestaParticipante.setEstudiosAct(Constants.ESTUDIO);

            String catSiNo = "CAT_SINO";
            String catGradoEdu = "CAT_GRD_EDU";
            String catCuidanNino = "CAT_CUIDAN_NINO";
            String catTurno = "CAT_TURNO";
            String catConQuien = "CAT_VIVE_NINO";
            String catSiNoDes = "CAT_SND";
            String catTipoTrab = "CAT_TIP_TRABAJO";
            String catFrecFuma = "CAT_FREC_FUMA";
            String catTmpFiebre = "CAT_TMP_FIEBRE";
            String catLugarCons = "CAT_LUGAR_CONSULTA";
            String catAutomed = "CAT_AUTOMED_FIEBRE";
            String catNoCS = "CAT_NO_ACUDIO_CS";
            String catCS = "CAT_CENTRO_SALUD";
            String catDondeAsiste = "CAT_DONDEASISTE";
            String catMeses = "CAT_MESES";
            String catEnroje = "CAT_DURO_ENROJECI";
            String catRazonEman = "CAT_RAZON_EMAN";
            String catNivelEdu = "CAT_NIV_EDU";
            String catPuesto = "CAT_PUESTO_SALUD";
            String catLugarDx = "CAT_LUGAR_DIAG";
            String catHospital = "CAT_HOSPITAL";

            /*if (tieneValor(emancipado)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + emancipado + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setEmancipado(messageResource.getCatKey());
            }*/
            encuestaParticipante.setEmancipado(getKeyFromCatalog(emancipado, catSiNoDes));

            if (tieneValor(descEmancipado)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + descEmancipado + "' and " + MainDBConstants.catRoot + "='"+ catRazonEman +"'", null);
                if (messageResource != null) encuestaParticipante.setDescEmancipado(messageResource.getCatKey());
            }

            if (tieneValor(embarazada)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + embarazada + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setEmbarazada(messageResource.getCatKey());
            }

            if (tieneValor(conoceFUR)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + conoceFUR + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setConoceFUR(messageResource.getCatKey());
            }

            /*if (tieneValor(asisteEscuela)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + asisteEscuela + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setAsisteEscuela(messageResource.getCatKey());
            }*/
            encuestaParticipante.setAsisteEscuela(getKeyFromCatalog(asisteEscuela, catSiNo));

            /*if (tieneValor(gradoEstudia)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + gradoEstudia + "' and " + MainDBConstants.catRoot + "='"+ catGradoEdu +"'", null);
                if (messageResource != null) encuestaParticipante.setGradoEstudia(messageResource.getCatKey());
            }*/

            encuestaParticipante.setGradoEstudia(getKeyFromCatalog(gradoEstudia, catGradoEdu));

            if (tieneValor(turno)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + turno + "' and " + MainDBConstants.catRoot + "='"+ catTurno +"'", null);
                if (messageResource != null) encuestaParticipante.setTurno(messageResource.getCatKey());
            }

            if (tieneValor(cuidan)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + cuidan + "' and " + MainDBConstants.catRoot + "='"+ catCuidanNino +"'", null);
                if (messageResource != null) encuestaParticipante.setCuidan(messageResource.getCatKey());
            }

            if (tieneValor(alfabeto)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + alfabeto + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setAlfabeto(messageResource.getCatKey());
            }

            if (tieneValor(nivelEducacion)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + nivelEducacion + "' and " + MainDBConstants.catRoot + "='"+ catNivelEdu +"'", null);
                if (messageResource != null) encuestaParticipante.setNivelEducacion(messageResource.getCatKey());
            }

            if (tieneValor(trabaja)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + trabaja + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setTrabaja(messageResource.getCatKey());
            }

            if (tieneValor(tipoTrabajo)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tipoTrabajo + "' and " + MainDBConstants.catRoot + "='"+ catTipoTrab +"'", null);
                if (messageResource != null) encuestaParticipante.setTipoTrabajo(messageResource.getCatKey());
            }

            if (tieneValor(personaVive)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + personaVive + "' and " + MainDBConstants.catRoot + "='"+ catConQuien +"'", null);
                if (messageResource != null) encuestaParticipante.setPersonaVive(messageResource.getCatKey());
            }

            if (tieneValor(padreAlfabeto)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + padreAlfabeto + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setPadreAlfabeto(messageResource.getCatKey());
            }

            if (tieneValor(papaNivel)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + papaNivel + "' and " + MainDBConstants.catRoot + "='"+ catNivelEdu +"'", null);
                if (messageResource != null) encuestaParticipante.setNivelEscolarPadre(messageResource.getCatKey());
            }

            if (tieneValor(trabajaPadre)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + trabajaPadre + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setTrabajaPadre(messageResource.getCatKey());
            }

            if (tieneValor(papaTipoTra)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + papaTipoTra + "' and " + MainDBConstants.catRoot + "='"+ catTipoTrab +"'", null);
                if (messageResource != null) encuestaParticipante.setTipoTrabajoPadre(messageResource.getCatKey());
            }

            if (tieneValor(madreAlfabeta)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + madreAlfabeta + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setMadreAlfabeta(messageResource.getCatKey());
            }

            if (tieneValor(mamaNivel)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + mamaNivel + "' and " + MainDBConstants.catRoot + "='"+ catNivelEdu +"'", null);
                if (messageResource != null) encuestaParticipante.setNivelEscolarMadre(messageResource.getCatKey());
            }

            if (tieneValor(trabajaMadre)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + trabajaMadre + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setTrabajaMadre(messageResource.getCatKey());
            }

            if (tieneValor(mamaTipoTra)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + mamaTipoTra + "' and " + MainDBConstants.catRoot + "='"+ catTipoTrab +"'", null);
                if (messageResource != null) encuestaParticipante.setTipoTrabajoMadre(messageResource.getCatKey());
            }

            if (tieneValor(comparteHab)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + comparteHab + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setComparteHab(messageResource.getCatKey());
            }

            if (tieneValor(comparteCama)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + comparteCama + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setComparteCama(messageResource.getCatKey());
            }

            if (tieneValor(fuma)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + fuma + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setFuma(messageResource.getCatKey());
            }

            if (tieneValor(periodicidadFuma)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + periodicidadFuma + "' and " + MainDBConstants.catRoot + "='"+ catFrecFuma +"'", null);
                if (messageResource != null) encuestaParticipante.setPeriodicidadFuma(messageResource.getCatKey());
            }

            if (tieneValor(fumaDentroCasa)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + fumaDentroCasa + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setFumaDentroCasa(messageResource.getCatKey());
            }

            if (tieneValor(tuberculosisPulmonarActual)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tuberculosisPulmonarActual + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setTuberculosisPulmonarActual(messageResource.getCatKey());
            }

            if (tieneValor(mesDiagTubpulActual)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + mesDiagTubpulActual + "' and " + MainDBConstants.catRoot + "='"+ catMeses +"'", null);
                if (messageResource != null) encuestaParticipante.setMesDiagTubpulActual(messageResource.getCatKey());
            }

            if (tieneValor(tratamientoTubpulActual)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tratamientoTubpulActual + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setTratamientoTubpulActual(messageResource.getCatKey());
            }

            if (tieneValor(completoTratamientoTubpulActual)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + completoTratamientoTubpulActual + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setCompletoTratamientoTubpulActual(messageResource.getCatKey());
            }

            if (tieneValor(tuberculosisPulmonarPasado)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tuberculosisPulmonarPasado + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setTuberculosisPulmonarPasado(messageResource.getCatKey());
            }

            if (tieneValor(fechaDiagTubpulPasadoDes)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + fechaDiagTubpulPasadoDes + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setFechaDiagTubpulPasadoDes(messageResource.getCatKey());
            }
            if (tieneValor(mesDiagTubpulPasado)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + mesDiagTubpulPasado + "' and " + MainDBConstants.catRoot + "='"+ catMeses +"'", null);
                if (messageResource != null) encuestaParticipante.setMesDiagTubpulPasado(messageResource.getCatKey());
            }

            if (tieneValor(tratamientoTubpulPasado)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tratamientoTubpulPasado + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setTratamientoTubpulPasado(messageResource.getCatKey());
            }

            if (tieneValor(completoTratamientoTubpulPas)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + completoTratamientoTubpulPas + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setCompletoTratamientoTubpulPas(messageResource.getCatKey());
            }

            if (tieneValor(alergiaRespiratoria)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + alergiaRespiratoria + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setAlergiaRespiratoria(messageResource.getCatKey());
            }

            if (tieneValor(asma)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + asma + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setAsma(messageResource.getCatKey());
            }

            if (tieneValor(enfermedadCronica)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + enfermedadCronica + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setEnfermedadCronica(messageResource.getCatKey());
            }

            if (tieneValor(cardiopatia)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + cardiopatia + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setCardiopatia(messageResource.getCatKey());
            }

            if (tieneValor(enfermPulmonarObstCronica)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + enfermPulmonarObstCronica + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setEnfermPulmonarObstCronica(messageResource.getCatKey());
            }

            if (tieneValor(diabetes)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + diabetes + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setDiabetes(messageResource.getCatKey());
            }

            if (tieneValor(presionAlta)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + presionAlta + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setPresionAlta(messageResource.getCatKey());
            }

            if (tieneValor(otrasEnfermedades)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + otrasEnfermedades + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setOtrasEnfermedades(messageResource.getCatKey());
            }

            if (tieneValor(tenidoDengue)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tenidoDengue + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setTenidoDengue(messageResource.getCatKey());
            }

            if (tieneValor(diagMedicoDengue)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + diagMedicoDengue + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setDiagMedicoDengue(messageResource.getCatKey());
            }

            if (tieneValor(dondeDengue)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + dondeDengue + "' and " + MainDBConstants.catRoot + "='"+ catLugarDx +"'", null);
                if (messageResource != null) encuestaParticipante.setDondeDengue(messageResource.getCatKey());
            }

            if (tieneValor(tenidoZika)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tenidoZika + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setTenidoZika(messageResource.getCatKey());
            }

            if (tieneValor(diagMedicoZika)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + diagMedicoZika + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setDiagMedicoZika(messageResource.getCatKey());
            }

            if (tieneValor(dondeZika)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + dondeZika + "' and " + MainDBConstants.catRoot + "='"+ catLugarDx +"'", null);
                if (messageResource != null) encuestaParticipante.setDondeZika(messageResource.getCatKey());
            }

            if (tieneValor(tenidoChik)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tenidoChik + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setTenidoChik(messageResource.getCatKey());
            }

            if (tieneValor(diagMedicoChik)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + diagMedicoChik + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setDiagMedicoChik(messageResource.getCatKey());
            }

            if (tieneValor(dondeChik)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + dondeChik + "' and " + MainDBConstants.catRoot + "='"+ catLugarDx +"'", null);
                if (messageResource != null) encuestaParticipante.setDondeChik(messageResource.getCatKey());
            }

            if (tieneValor(vacunaFiebreAma)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + vacunaFiebreAma + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setVacunaFiebreAma(messageResource.getCatKey());
            }

            if (tieneValor(vacunaCovid)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + vacunaCovid + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setVacunaCovid(messageResource.getCatKey());
            }

            if (tieneValor(mesVacunaCovid)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + mesVacunaCovid + "' and " + MainDBConstants.catRoot + "='"+ catMeses +"'", null);
                if (messageResource != null) encuestaParticipante.setMesVacunaCovid(messageResource.getCatKey());
            }

            if (tieneValor(tieneTarjetaVacuna)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneTarjetaVacuna + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setTieneTarjetaVacuna(messageResource.getCatKey());
            }

            if (tieneValor(fiebre)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + fiebre + "' and " + MainDBConstants.catRoot + "='"+ catSiNoDes +"'", null);
                if (messageResource != null) encuestaParticipante.setFiebre(messageResource.getCatKey());
            }

            if (tieneValor(tiempoFiebre)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tiempoFiebre + "' and " + MainDBConstants.catRoot + "='"+ catTmpFiebre +"'", null);
                if (messageResource != null) encuestaParticipante.setTiempoFiebre(messageResource.getCatKey());
            }

            if (tieneValor(lugarConsFiebre)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + lugarConsFiebre + "' and " + MainDBConstants.catRoot + "='"+ catLugarCons +"'", null);
                if (messageResource != null) encuestaParticipante.setLugarConsFiebre(messageResource.getCatKey());
            }

            if (tieneValor(noAcudioCs)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + noAcudioCs + "' and " + MainDBConstants.catRoot + "='"+ catNoCS +"'", null);
                if (messageResource != null) encuestaParticipante.setNoAcudioCs(messageResource.getCatKey());
            }

            if (tieneValor(tenidoDengueUA)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tenidoDengueUA + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setTenidoDengueUA(messageResource.getCatKey());
            }

            if (tieneValor(unidadSaludDengue)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + unidadSaludDengue + "' and " + MainDBConstants.catRoot + "='"+ catDondeAsiste +"'", null);
                if (messageResource != null) encuestaParticipante.setUnidadSaludDengue(messageResource.getCatKey());
            }

            if (tieneValor(centroSaludDengue)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + centroSaludDengue + "' and " + MainDBConstants.catRoot + "='"+ catCS +"'", null);
                if (messageResource != null) encuestaParticipante.setCentroSaludDengue(messageResource.getCatKey());
            }

            if (tieneValor(puestoSaludDengue)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + puestoSaludDengue + "' and " + MainDBConstants.catRoot + "='"+ catPuesto +"'", null);
                if (messageResource != null) encuestaParticipante.setPuestoSaludDengue(messageResource.getCatKey());
            }

            if (tieneValor(hospitalDengue)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + hospitalDengue + "' and " + MainDBConstants.catRoot + "='"+ catHospital +"'", null);
                if (messageResource != null) encuestaParticipante.setHospitalDengue(messageResource.getCatKey());
            }

            if (tieneValor(hospitalizadoDengue)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + hospitalizadoDengue + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setHospitalizadoDengue(messageResource.getCatKey());
            }

            if (tieneValor(ambulatorioDengue)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + ambulatorioDengue + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setAmbulatorioDengue(messageResource.getCatKey());
            }

            if (tieneValor(rashUA)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + rashUA + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setRashUA(messageResource.getCatKey());
            }

            if (tieneValor(recuerdaMesRash)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + recuerdaMesRash + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setRecuerdaMesRash(messageResource.getCatKey());
            }

            if (tieneValor(rashMes)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + rashMes + "' and " + MainDBConstants.catRoot + "='"+ catMeses +"'", null);
                if (messageResource != null) encuestaParticipante.setMesRash(messageResource.getCatKey());
            }

            if (tieneValor(rashCara)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + rashCara + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setCaraRash(messageResource.getCatKey());
            }

            if (tieneValor(rashMiembrosSup)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + rashMiembrosSup + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setMiembrosSupRash(messageResource.getCatKey());
            }

            if (tieneValor(rashTorax)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + rashTorax + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setToraxRash(messageResource.getCatKey());
            }

            if (tieneValor(rashAbdomen)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + rashAbdomen + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setAbdomenRash(messageResource.getCatKey());
            }

            if (tieneValor(rashMiembrosInf)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + rashMiembrosInf + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setMiembrosInfRash(messageResource.getCatKey());
            }

            if (tieneValor(rashDias)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + rashDias + "' and " + MainDBConstants.catRoot + "='"+ catEnroje +"'", null);
                if (messageResource != null) encuestaParticipante.setDiasRash(messageResource.getCatKey());
            }

            if (tieneValor(consultaRashUA)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + consultaRashUA + "' and " + MainDBConstants.catRoot + "='"+ catSiNo +"'", null);
                if (messageResource != null) encuestaParticipante.setConsultaRashUA(messageResource.getCatKey());
            }

            if (tieneValor(unidadSaludRash)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + unidadSaludRash + "' and " + MainDBConstants.catRoot + "='"+ catDondeAsiste +"'", null);
                if (messageResource != null) encuestaParticipante.setUnidadSaludRash(messageResource.getCatKey());
            }

            if (tieneValor(centroSaludRash)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + centroSaludRash + "' and " + MainDBConstants.catRoot + "='"+ catCS +"'", null);
                if (messageResource != null) encuestaParticipante.setCentroSaludRash(messageResource.getCatKey());
            }

            if (tieneValor(puestoSaludRash)) {
                MessageResource messageResource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + puestoSaludRash + "' and " + MainDBConstants.catRoot + "='"+ catPuesto +"'", null);
                if (messageResource != null) encuestaParticipante.setRecuerdaMesRash(messageResource.getCatKey());
            }

            if (tieneValor(automedicoFiebre)) {
                String keys = "";
                automedicoFiebre = automedicoFiebre.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msParedes = estudiosAdapter.getMessageResources(MainDBConstants.spanish + " in ('" + automedicoFiebre + "') and "
                        + MainDBConstants.catRoot + "='"+catAutomed+"'", null);
                for(MessageResource ms : msParedes) {
                    keys += ms.getCatKey() + ",";
                }
                if (!keys.isEmpty())
                    keys = keys.substring(0, keys.length() - 1);
                encuestaParticipante.setAutomedicoFiebre(keys);
            }

            encuestaParticipante.setOtraDescEmanc(otraDescEmanc);
            encuestaParticipante.setNombreEscuela(nombreEscuela);
            encuestaParticipante.setNombreCDI(nombreCDI);
            encuestaParticipante.setDireccionCDI(direccionCDI);
            encuestaParticipante.setOtroLugarCuidan(otroLugarCuidan);
            encuestaParticipante.setOcupacionActual(ocupacionActual);
            encuestaParticipante.setDescOtrasEnfermedades(descOtrasEnfermedades);
            encuestaParticipante.setOtroCentroSaludDengue(otroCentroSaludDengue);
            encuestaParticipante.setOtroPuestoSaludDengue(otroPuestoSaludDengue);
            encuestaParticipante.setOtroHospitalDengue(otroHospitalDengue);
            encuestaParticipante.setDiagnosticoDengue(diagnosticoDengue);
            encuestaParticipante.setOtroCentroSaludRash(otroCentroSaludRash);
            encuestaParticipante.setOtroPuestoSaludRash(otroPuestoSaludRash);
            encuestaParticipante.setOtroPuestoSaludRash(otroPuestoSaludRash);
            encuestaParticipante.setDiagnosticoRash(diagnosticoRash);

            if (tieneValor(fur)) encuestaParticipante.setFur(DateUtil.StringToDate(fur, "dd/MM/yyyy"));
            if (tieneValor(cuantosCuidan)) encuestaParticipante.setCuantosCuidan(Integer.parseInt(cuantosCuidan));
            if (tieneValor(ordenNac)) encuestaParticipante.setOrdenNac(Integer.parseInt(ordenNac));
            if (tieneValor(hab1)) encuestaParticipante.setHab1(Integer.parseInt(hab1));
            if (tieneValor(hab2)) encuestaParticipante.setHab2(Integer.parseInt(hab2));
            if (tieneValor(hab3)) encuestaParticipante.setHab3(Integer.parseInt(hab3));
            if (tieneValor(hab4)) encuestaParticipante.setHab4(Integer.parseInt(hab4));
            if (tieneValor(hab5)) encuestaParticipante.setHab5(Integer.parseInt(hab5));
            if (tieneValor(cama1)) encuestaParticipante.setCama1(Integer.parseInt(cama1));
            if (tieneValor(cama2)) encuestaParticipante.setCama2(Integer.parseInt(cama2));
            if (tieneValor(cama3)) encuestaParticipante.setCama3(Integer.parseInt(cama3));
            if (tieneValor(cama4)) encuestaParticipante.setCama4(Integer.parseInt(cama4));
            if (tieneValor(cama5)) encuestaParticipante.setCama5(Integer.parseInt(cama5));
            if (tieneValor(cantidadCigarrillos)) encuestaParticipante.setCantidadCigarrillos(Integer.parseInt(cantidadCigarrillos));
            if (tieneValor(anioDiagTubpulActual)) encuestaParticipante.setAnioDiagTubpulActual(Integer.parseInt(anioDiagTubpulActual));
            if (tieneValor(anioDiagTubpulPasado)) encuestaParticipante.setAnioDiagTubpulPasado(Integer.parseInt(anioDiagTubpulPasado));
            if (tieneValor(anioAlergiaRespiratoria)) encuestaParticipante.setAnioAlergiaRespiratoria(Integer.parseInt(anioAlergiaRespiratoria));
            if (tieneValor(anioAsma)) encuestaParticipante.setAnioAsma(Integer.parseInt(anioAsma));
            if (tieneValor(anioCardiopatia)) encuestaParticipante.setAnioCardiopatia(Integer.parseInt(anioCardiopatia));
            if (tieneValor(anioEnfermPulmonarObstCronica)) encuestaParticipante.setAnioEnfermPulmonarObstCronica(Integer.parseInt(anioEnfermPulmonarObstCronica));
            if (tieneValor(anioDiabetes)) encuestaParticipante.setAnioDiabetes(Integer.parseInt(anioDiabetes));
            if (tieneValor(anioPresionAlta)) encuestaParticipante.setAnioPresionAlta(Integer.parseInt(anioPresionAlta));
            if (tieneValor(anioDengue)) encuestaParticipante.setAnioDengue(Integer.parseInt(anioDengue));
            if (tieneValor(anioZika)) encuestaParticipante.setAnioZika(Integer.parseInt(anioZika));
            if (tieneValor(anioChik)) encuestaParticipante.setAnioChik(Integer.parseInt(anioChik));
            if (tieneValor(anioVacunaFiebreAma)) encuestaParticipante.setAnioVacunaFiebreAma(Integer.parseInt(anioVacunaFiebreAma));
            if (tieneValor(anioVacunaCovid)) encuestaParticipante.setAnioVacunaCovid(Integer.parseInt(anioVacunaCovid));

            estudiosAdapter.crearEncuestasParticipante(encuestaParticipante);

            participante.getProcesos().setPendienteEncPart(Constants.NOKEYSND);
            participante.getProcesos().setRecordDate(new Date());
            participante.getProcesos().setRecordUser(username);
            participante.getProcesos().setDeviceid(infoMovil.getDeviceId());
            participante.getProcesos().setEstado('0');
            participante.getProcesos().setPasive('0');
            estudiosAdapter.editarParticipanteProcesos(participante.getProcesos());

            showToast(getString(R.string.success));

            Intent i = new Intent(getApplicationContext(),
                    MenuParticipanteActivity.class);
            Bundle arguments = new Bundle();
            arguments.putSerializable(Constants.PARTICIPANTE, participante);
            i.putExtras(arguments);
            i.putExtra(Constants.VISITA_EXITOSA, this.visitaExitosa);
            startActivity(i);
            finish();

        } catch (Exception ex){
            ex.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(), ex.getLocalizedMessage(), Toast.LENGTH_LONG);
            toast.show();
        }finally {
            //Cierra la base de datos
            if (estudiosAdapter!=null)
                estudiosAdapter.close();

        }
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
                                    ((NuevaEncuestaParticipanteActivity) Objects.requireNonNull(getActivity())).saveData();
                                }
                            }
                    )
                    .setNegativeButton(R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((NuevaEncuestaParticipanteActivity) Objects.requireNonNull(getActivity())).createDialog(EXIT);;
                                }
                            }
                    )
                    .create();
        }
    }
}
