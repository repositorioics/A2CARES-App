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
import ni.org.ics.a2cares.app.domain.survey.EncuestaCasa;
import ni.org.ics.a2cares.app.domain.visita.VisitaTerrenoParticipante;
import ni.org.ics.a2cares.app.preferences.PreferencesActivity;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuParticipanteActivity;
import ni.org.ics.a2cares.app.ui.forms.EncuestaCasaForm;
import ni.org.ics.a2cares.app.ui.forms.EncuestaCasaFormLabels;
import ni.org.ics.a2cares.app.ui.forms.VisitaTerrenoForm;
import ni.org.ics.a2cares.app.ui.forms.VisitaTerrenoFormLabels;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.utils.DeviceInfo;
import ni.org.ics.a2cares.app.utils.FileUtils;
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


public class NuevaEncuestaCasaActivity extends AbstractAsyncActivity implements
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
    private EncuestaCasaFormLabels labels = new EncuestaCasaFormLabels();
    private EstudioDBAdapter estudiosAdapter;
    private DeviceInfo infoMovil;
    private String username;
    private SharedPreferences settings;
    private static final int EXIT = 1;
    private AlertDialog alertDialog;
    private boolean notificarCambios = true;
    private Participante participante;
    private boolean visitaExitosa = false;
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
        infoMovil = new DeviceInfo(NuevaEncuestaCasaActivity.this);
        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(),mPass,false,false);

        participante = (Participante) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
        visitaExitosa = getIntent().getBooleanExtra(Constants.VISITA_EXITOSA, false);

        mWizardModel = new EncuestaCasaForm(this,mPass);
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
            if(page.getTitle().equals(labels.getCuantasMujeres())){
                int cantMujeres = 0;
                if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null)
                    cantMujeres = Integer.parseInt(page.getData().getString(TextPage.SIMPLE_DATA_KEY));
                changeStatus(mWizardModel.findByKey(labels.getEdadMujer1()), cantMujeres >= 1);
                changeStatus(mWizardModel.findByKey(labels.getEdadMujer2()), cantMujeres >= 2);
                changeStatus(mWizardModel.findByKey(labels.getEdadMujer3()), cantMujeres >= 3);
                changeStatus(mWizardModel.findByKey(labels.getEdadMujer4()), cantMujeres >= 4);
                changeStatus(mWizardModel.findByKey(labels.getEdadMujer5()), cantMujeres >= 5);
                changeStatus(mWizardModel.findByKey(labels.getEdadMujer6()), cantMujeres >= 6);
                changeStatus(mWizardModel.findByKey(labels.getEdadMujer7()), cantMujeres >= 7);
                changeStatus(mWizardModel.findByKey(labels.getEdadMujer8()), cantMujeres >= 8);
                changeStatus(mWizardModel.findByKey(labels.getEdadMujer9()), cantMujeres >= 9);
                changeStatus(mWizardModel.findByKey(labels.getEdadMujer10()), cantMujeres >= 10);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getCuantosHombres())){
                int cantHombres = 0;
                if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null)
                    cantHombres = Integer.parseInt(page.getData().getString(TextPage.SIMPLE_DATA_KEY));
                changeStatus(mWizardModel.findByKey(labels.getEdadHombre1()), cantHombres >= 1);
                changeStatus(mWizardModel.findByKey(labels.getEdadHombre2()), cantHombres >= 2);
                changeStatus(mWizardModel.findByKey(labels.getEdadHombre3()), cantHombres >= 3);
                changeStatus(mWizardModel.findByKey(labels.getEdadHombre4()), cantHombres >= 4);
                changeStatus(mWizardModel.findByKey(labels.getEdadHombre5()), cantHombres >= 5);
                changeStatus(mWizardModel.findByKey(labels.getEdadHombre6()), cantHombres >= 6);
                changeStatus(mWizardModel.findByKey(labels.getEdadHombre7()), cantHombres >= 7);
                changeStatus(mWizardModel.findByKey(labels.getEdadHombre8()), cantHombres >= 8);
                changeStatus(mWizardModel.findByKey(labels.getEdadHombre9()), cantHombres >= 9);
                changeStatus(mWizardModel.findByKey(labels.getEdadHombre10()), cantHombres >= 10);
                onPageTreeChanged();
            }

            if(page.getTitle().equals(labels.getCuantoCuartos())){
                NumberPage tmp = (NumberPage) mWizardModel.findByKey(labels.getCuartosDormir());
                int cantCuartos = 0;
                if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null)
                    cantCuartos = Integer.parseInt(page.getData().getString(TextPage.SIMPLE_DATA_KEY));
                tmp.setRangeValidation(true, 0, cantCuartos);
                onPageTreeChanged();
            }
            if(page.getTitle().equals(labels.getProblemaAgua())){
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getHorasSinAgua()), visible);
                changeStatus(mWizardModel.findByKey(labels.getFrecuenciaSeVaAgua()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOtraFrecuenciaSeVaAgua()), false);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getFrecuenciaSeVaAgua())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Otros");
                changeStatus(mWizardModel.findByKey(labels.getOtraFrecuenciaSeVaAgua()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getLlaveAgua())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Fuera");
                changeStatus(mWizardModel.findByKey(labels.getLlaveCompartida()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAlmacenaAgua())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAlmacenaEnBarriles()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNumBarriles()), false);
                changeStatus(mWizardModel.findByKey(labels.getBarrilesTapados()), false);
                changeStatus(mWizardModel.findByKey(labels.getAlmacenaEnPilas()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNumPilas()), false);
                changeStatus(mWizardModel.findByKey(labels.getPilasTapadas()), false);
                changeStatus(mWizardModel.findByKey(labels.getCepillaPilas()), false);
                changeStatus(mWizardModel.findByKey(labels.getFrecCepillaPilas()), false);
                changeStatus(mWizardModel.findByKey(labels.getAlmacenaEnTanques()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNumTanques()), false);
                changeStatus(mWizardModel.findByKey(labels.getTanquesTapados()), false);
                changeStatus(mWizardModel.findByKey(labels.getAlmacenaOtrosRecipientes()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOtrosRecipientes()), false);
                changeStatus(mWizardModel.findByKey(labels.getNumOtrosRecipientes()), false);
                changeStatus(mWizardModel.findByKey(labels.getOtrosRecipientesTapados()), false);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAlmacenaEnBarriles())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumBarriles()), visible);
                changeStatus(mWizardModel.findByKey(labels.getBarrilesTapados()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAlmacenaEnTanques())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumTanques()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTanquesTapados()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAlmacenaEnPilas())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumPilas()), visible);
                changeStatus(mWizardModel.findByKey(labels.getPilasTapadas()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCepillaPilas()), visible);
                changeStatus(mWizardModel.findByKey(labels.getFrecCepillaPilas()), false);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getCepillaPilas())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getFrecCepillaPilas()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getAlmacenaOtrosRecipientes())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getOtrosRecipientes()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNumOtrosRecipientes()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOtrosRecipientesTapados()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getMaterialParedes())) {
                visible = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY).contains(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtroMaterialParedes()), visible);
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getMaterialPiso())) {
                visible = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY).contains(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtroMaterialPiso()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getMaterialTecho())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOtroMaterialTecho()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneAbanico())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumAbanicos()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneTelevisor())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumTelevisores()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneRefrigerador())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumRefrigeradores()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneAireAcondicionado())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getNumAireAcondicionado()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAireAcondicionadoFuncionando()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneInodoro())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && (page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Inodoro") || page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Ambos") );
                changeStatus(mWizardModel.findByKey(labels.getCantInodoro()), visible);

                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && (page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Letrina") || page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Ambos") );
                changeStatus(mWizardModel.findByKey(labels.getCantLetrina()), visible);

                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneServicioEnergia())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTieneMedidorEnergia()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneVehiculo())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTieneMoto()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAnioFabMoto()), false);
                changeStatus(mWizardModel.findByKey(labels.getMarcaMoto()), false);
                changeStatus(mWizardModel.findByKey(labels.getTieneCarro()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAnioFabCarro()), false);
                changeStatus(mWizardModel.findByKey(labels.getMarcaCarro()), false);
                changeStatus(mWizardModel.findByKey(labels.getTieneMicrobus()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAnioFabMicrobus()), false);
                changeStatus(mWizardModel.findByKey(labels.getMarcaMicrobus()), false);
                changeStatus(mWizardModel.findByKey(labels.getTieneCamioneta()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAnioFabCamioneta()), false);
                changeStatus(mWizardModel.findByKey(labels.getMarcaCamioneta()), false);
                changeStatus(mWizardModel.findByKey(labels.getTieneCamion()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAnioFabCamion()), false);
                changeStatus(mWizardModel.findByKey(labels.getMarcaCamion()), false);
                changeStatus(mWizardModel.findByKey(labels.getTieneOtroMedioTransAuto()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOtroMedioTransAuto()), false);
                changeStatus(mWizardModel.findByKey(labels.getAnioFabOtroMedioTrans()), false);
                changeStatus(mWizardModel.findByKey(labels.getMarcaOtroMedioTrans()), false);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneMoto())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAnioFabMoto()), visible);
                changeStatus(mWizardModel.findByKey(labels.getMarcaMoto()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneCarro())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAnioFabCarro()), visible);
                changeStatus(mWizardModel.findByKey(labels.getMarcaCarro()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneMicrobus())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAnioFabMicrobus()), visible);
                changeStatus(mWizardModel.findByKey(labels.getMarcaMicrobus()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneCamioneta())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAnioFabCamioneta()), visible);
                changeStatus(mWizardModel.findByKey(labels.getMarcaCamioneta()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneCamion())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getAnioFabCamion()), visible);
                changeStatus(mWizardModel.findByKey(labels.getMarcaCamion()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneOtroMedioTransAuto())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getOtroMedioTransAuto()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAnioFabOtroMedioTrans()), visible);
                changeStatus(mWizardModel.findByKey(labels.getMarcaOtroMedioTrans()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getCocinaConLenia())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getUbicacionCocinaLenia()), visible);
                changeStatus(mWizardModel.findByKey(labels.getPeriodicidadCocinaLenia()), visible);
                changeStatus(mWizardModel.findByKey(labels.getNumDiarioCocinaLenia()), false);
                changeStatus(mWizardModel.findByKey(labels.getNumSemanalCocinaLenia()), false);
                changeStatus(mWizardModel.findByKey(labels.getNumQuincenalCocinaLenia()), false);
                changeStatus(mWizardModel.findByKey(labels.getNumMensualCocinaLenia()), false);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getPeriodicidadCocinaLenia())) {
                changeStatus(mWizardModel.findByKey(labels.getNumDiarioCocinaLenia()), (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Diario")));
                changeStatus(mWizardModel.findByKey(labels.getNumSemanalCocinaLenia()), (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Semanal")));
                changeStatus(mWizardModel.findByKey(labels.getNumQuincenalCocinaLenia()), (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Quincenal")));
                changeStatus(mWizardModel.findByKey(labels.getNumMensualCocinaLenia()), (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches("Mensual")));
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneAnimales())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getTieneGallinas()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTienePatos()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTienePerros()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTieneGatos()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTieneCerdos()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTieneVacas()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTieneCabras()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTieneCaballos()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTienePelibueys()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTieneAves()), visible);
                changeStatus(mWizardModel.findByKey(labels.getTieneOtrosAnimales()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCantidadGallinas()), false);
                changeStatus(mWizardModel.findByKey(labels.getGallinasDentroCasa()), false);
                changeStatus(mWizardModel.findByKey(labels.getCantidadPatos()), false);
                changeStatus(mWizardModel.findByKey(labels.getPatosDentroCasa()), false);
                changeStatus(mWizardModel.findByKey(labels.getCantidadGatos()), false);
                changeStatus(mWizardModel.findByKey(labels.getGatosDentroCasa()), false);
                changeStatus(mWizardModel.findByKey(labels.getCantidadPerros()), false);
                changeStatus(mWizardModel.findByKey(labels.getPerrosDentroCasa()), false);
                changeStatus(mWizardModel.findByKey(labels.getCantidadCerdos()), false);
                changeStatus(mWizardModel.findByKey(labels.getCerdosDentroCasa()), false);
                changeStatus(mWizardModel.findByKey(labels.getCantidadVacas()), false);
                changeStatus(mWizardModel.findByKey(labels.getVacasDentroCasa()), false);
                changeStatus(mWizardModel.findByKey(labels.getCantidadCabras()), false);
                changeStatus(mWizardModel.findByKey(labels.getCabrasDentroCasa()), false);
                changeStatus(mWizardModel.findByKey(labels.getCantidadCaballos()), false);
                changeStatus(mWizardModel.findByKey(labels.getCaballosDentroCasa()), false);
                changeStatus(mWizardModel.findByKey(labels.getCantidadPelibueys()), false);
                changeStatus(mWizardModel.findByKey(labels.getPelibueysDentroCasa()), false);
                changeStatus(mWizardModel.findByKey(labels.getCantidadAves()), false);
                changeStatus(mWizardModel.findByKey(labels.getAvesDentroCasa()), false);
                changeStatus(mWizardModel.findByKey(labels.getCantidadOtrosAnimales()), false);
                changeStatus(mWizardModel.findByKey(labels.getOtrosAnimalesDentroCasa()), false);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneGallinas())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadGallinas()), visible);
                changeStatus(mWizardModel.findByKey(labels.getGallinasDentroCasa()), visible);
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getTienePatos())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadPatos()), visible);
                changeStatus(mWizardModel.findByKey(labels.getPatosDentroCasa()), visible);
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getTieneGatos())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadGatos()), visible);
                changeStatus(mWizardModel.findByKey(labels.getGatosDentroCasa()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTienePerros())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadPerros()), visible);
                changeStatus(mWizardModel.findByKey(labels.getPerrosDentroCasa()), visible);
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getTieneCerdos())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadCerdos()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCerdosDentroCasa()), visible);
                onPageTreeChanged();
            }

            if (page.getTitle().equals(labels.getTieneVacas())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadVacas()), visible);
                changeStatus(mWizardModel.findByKey(labels.getVacasDentroCasa()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneCabras())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadCabras()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCabrasDentroCasa()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneCaballos())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadCaballos()), visible);
                changeStatus(mWizardModel.findByKey(labels.getCaballosDentroCasa()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTienePelibueys())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadPelibueys()), visible);
                changeStatus(mWizardModel.findByKey(labels.getPelibueysDentroCasa()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneAves())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadAves()), visible);
                changeStatus(mWizardModel.findByKey(labels.getAvesDentroCasa()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneOtrosAnimales())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCantidadOtrosAnimales()), visible);
                changeStatus(mWizardModel.findByKey(labels.getOtrosAnimalesDentroCasa()), visible);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTieneRecoleccionBasura())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCuantasVecesRecBasura()), visible);
                changeStatus(mWizardModel.findByKey(labels.getDondePasaRecBasura()), visible);
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
            TextPage modifPage = (TextPage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.NumberPage")){
            NumberPage modifPage = (NumberPage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
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

    public void saveData() {
        try {
            //Guarda las respuestas en un bundle
            Map<String, String> mapa = mWizardModel.getAnswers();
            Bundle datos = new Bundle();
            for (Map.Entry<String, String> entry : mapa.entrySet()) {
                datos.putString(entry.getKey(), entry.getValue());
            }
            //Abre la base de datos
            String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
            estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(), mPass, false, false);
            estudiosAdapter.open();

            String cuantasPersonas = datos.getString(this.getString(R.string.cuantasPersonas));
            String cuantasMujeres = datos.getString(this.getString(R.string.cuantasMujeres));
            String edadMujer1 = datos.getString(this.getString(R.string.edadMujer1));
            String edadMujer2 = datos.getString(this.getString(R.string.edadMujer2));
            String edadMujer3 = datos.getString(this.getString(R.string.edadMujer3));
            String edadMujer4 = datos.getString(this.getString(R.string.edadMujer4));
            String edadMujer5 = datos.getString(this.getString(R.string.edadMujer5));
            String edadMujer6 = datos.getString(this.getString(R.string.edadMujer6));
            String edadMujer7 = datos.getString(this.getString(R.string.edadMujer7));
            String edadMujer8 = datos.getString(this.getString(R.string.edadMujer8));
            String edadMujer9 = datos.getString(this.getString(R.string.edadMujer9));
            String edadMujer10 = datos.getString(this.getString(R.string.edadMujer10));
            String cuantosHombres = datos.getString(this.getString(R.string.cuantosHombres));
            String edadHombre1 = datos.getString(this.getString(R.string.edadHombre1));
            String edadHombre2 = datos.getString(this.getString(R.string.edadHombre2));
            String edadHombre3 = datos.getString(this.getString(R.string.edadHombre3));
            String edadHombre4 = datos.getString(this.getString(R.string.edadHombre4));
            String edadHombre5 = datos.getString(this.getString(R.string.edadHombre5));
            String edadHombre6 = datos.getString(this.getString(R.string.edadHombre6));
            String edadHombre7 = datos.getString(this.getString(R.string.edadHombre7));
            String edadHombre8 = datos.getString(this.getString(R.string.edadHombre8));
            String edadHombre9 = datos.getString(this.getString(R.string.edadHombre9));
            String edadHombre10 = datos.getString(this.getString(R.string.edadHombre10));
            String cuantoCuartos = datos.getString(this.getString(R.string.cuantoCuartos));
            String cuartosDormir = datos.getString(this.getString(R.string.cuartosDormir));
            String problemaAgua = datos.getString(this.getString(R.string.problemaAgua));
            String horasSinAgua = datos.getString(this.getString(R.string.horasSinAgua));
            String frecuenciaSeVaAgua = datos.getString(this.getString(R.string.frecuenciaSeVaAgua));
            String otraFrecuenciaSeVaAgua = datos.getString(this.getString(R.string.otraFrecuenciaSeVaAgua));
            String tienePozo = datos.getString(this.getString(R.string.tienePozo));
            String tieneMedidorAgua = datos.getString(this.getString(R.string.tieneMedidorAgua));
            String tieneTanque = datos.getString(this.getString(R.string.tieneTanque));
            String llaveAgua = datos.getString(this.getString(R.string.llaveAgua));
            String llaveCompartida = datos.getString(this.getString(R.string.llaveCompartida));
            String almacenaAgua = datos.getString(this.getString(R.string.almacenaAgua));
            String almacenaEnBarriles = datos.getString(this.getString(R.string.almacenaEnBarriles));
            String almacenaEnTanques = datos.getString(this.getString(R.string.almacenaEnTanques));
            String almacenaEnPilas = datos.getString(this.getString(R.string.almacenaEnPilas));
            String almacenaOtrosRecipientes = datos.getString(this.getString(R.string.almacenaOtrosRecipientes));
            String otrosRecipientes = datos.getString(this.getString(R.string.otrosRecipientes));
            String numBarriles = datos.getString(this.getString(R.string.numBarriles));
            String numTanques = datos.getString(this.getString(R.string.numTanques));
            String numPilas = datos.getString(this.getString(R.string.numPilas));
            String numOtrosRecipientes = datos.getString(this.getString(R.string.numOtrosRecipientes));
            String barrilesTapados = datos.getString(this.getString(R.string.barrilesTapados));
            String tanquesTapados = datos.getString(this.getString(R.string.tanquesTapados));
            String pilasTapadas = datos.getString(this.getString(R.string.pilasTapadas));
            String otrosRecipientesTapados = datos.getString(this.getString(R.string.otrosRecipientesTapados));
            String cepillaPilas = datos.getString(this.getString(R.string.cepillaPilas));
            String frecCepillaPilas = datos.getString(this.getString(R.string.frecCepillaPilas));
            String cambiadoCasa = datos.getString(this.getString(R.string.cambiadoCasa));
            String remodeladoCasa = datos.getString(this.getString(R.string.remodeladoCasa));
            String ubicacionLavandero = datos.getString(this.getString(R.string.ubicacionLavandero));
            String materialParedes = datos.getString(this.getString(R.string.materialParedes));
            String materialPiso = datos.getString(this.getString(R.string.materialPiso));
            String materialTecho = datos.getString(this.getString(R.string.materialTecho));
            String otroMaterialParedes = datos.getString(this.getString(R.string.otroMaterialParedes));
            String otroMaterialPiso = datos.getString(this.getString(R.string.otroMaterialPiso));
            String otroMaterialTecho = datos.getString(this.getString(R.string.otroMaterialTecho));
            String casaPropia = datos.getString(this.getString(R.string.casaPropia));
            String tieneAbanico = datos.getString(this.getString(R.string.tieneAbanico));
            String tieneTelevisor = datos.getString(this.getString(R.string.tieneTelevisor));
            String tieneRefrigerador = datos.getString(this.getString(R.string.tieneRefrigerador));
            String tieneAireAcondicionado = datos.getString(this.getString(R.string.tieneAireAcondicionado));
            String numAbanicos = datos.getString(this.getString(R.string.numAbanicos));
            String numTelevisores = datos.getString(this.getString(R.string.numTelevisores));
            String numRefrigeradores = datos.getString(this.getString(R.string.numRefrigeradores));
            String numAireAcondicionado = datos.getString(this.getString(R.string.numAireAcondicionado));
            String aireAcondicionadoFuncionando = datos.getString(this.getString(R.string.aireAcondicionadoFuncionando));
            String lavadoraFuncionando = datos.getString(this.getString(R.string.lavadoraFuncionando));
            String tieneMuro = datos.getString(this.getString(R.string.tieneMuro));
            String tieneInternet = datos.getString(this.getString(R.string.tieneInternet));
            String tieneInodoro = datos.getString(this.getString(R.string.tieneInodoro));
            String cantInodoro = datos.getString(this.getString(R.string.cantInodoro));
            String cantLetrina = datos.getString(this.getString(R.string.cantLetrina));
            String tieneServicioEnergia = datos.getString(this.getString(R.string.tieneServicioEnergia));
            String tieneMedidorEnergia = datos.getString(this.getString(R.string.tieneMedidorEnergia));
            String casaDosPisos = datos.getString(this.getString(R.string.casaDosPisos));
            String tieneOtrosServicios = datos.getString(this.getString(R.string.tieneOtrosServicios));
            String tieneVehiculo = datos.getString(this.getString(R.string.tieneVehiculo));
            String tieneMoto = datos.getString(this.getString(R.string.tieneMoto));
            String tieneCarro = datos.getString(this.getString(R.string.tieneCarro));
            String tieneMicrobus = datos.getString(this.getString(R.string.tieneMicrobus));
            String tieneCamioneta = datos.getString(this.getString(R.string.tieneCamioneta));
            String tieneCamion = datos.getString(this.getString(R.string.tieneCamion));
            String tieneOtroMedioTransAuto = datos.getString(this.getString(R.string.tieneOtroMedioTransAuto));
            String otroMedioTransAuto = datos.getString(this.getString(R.string.otroMedioTransAuto));
            String anioFabMoto = datos.getString(this.getString(R.string.anioFabMoto));
            String anioFabCarro = datos.getString(this.getString(R.string.anioFabCarro));
            String anioFabMicrobus = datos.getString(this.getString(R.string.anioFabMicrobus));
            String anioFabCamioneta = datos.getString(this.getString(R.string.anioFabCamioneta));
            String anioFabCamion = datos.getString(this.getString(R.string.anioFabCamion));
            String anioFabOtroMedioTrans = datos.getString(this.getString(R.string.anioFabOtroMedioTrans));
            String marcaMoto = datos.getString(this.getString(R.string.marcaMoto));
            String marcaCarro = datos.getString(this.getString(R.string.marcaCarro));
            String marcaMicrobus = datos.getString(this.getString(R.string.marcaMicrobus));
            String marcaCamioneta = datos.getString(this.getString(R.string.marcaCamioneta));
            String marcaCamion = datos.getString(this.getString(R.string.marcaCamion));
            String marcaOtroMedioTrans = datos.getString(this.getString(R.string.marcaOtroMedioTrans));
            String tipoCocina = datos.getString(this.getString(R.string.tipoCocina));
            String cuantosQuemadores = datos.getString(this.getString(R.string.cuantosQuemadores));
            String tieneHorno = datos.getString(this.getString(R.string.tieneHorno));
            String cocinaConLenia = datos.getString(this.getString(R.string.cocinaConLenia));
            String ubicacionCocinaLenia = datos.getString(this.getString(R.string.ubicacionCocinaLenia));
            String periodicidadCocinaLenia = datos.getString(this.getString(R.string.periodicidadCocinaLenia));
            String numDiarioCocinaLenia = datos.getString(this.getString(R.string.numDiarioCocinaLenia));
            String numSemanalCocinaLenia = datos.getString(this.getString(R.string.numSemanalCocinaLenia));
            String numQuincenalCocinaLenia = datos.getString(this.getString(R.string.numQuincenalCocinaLenia));
            String numMensualCocinaLenia = datos.getString(this.getString(R.string.numMensualCocinaLenia));
            String tieneAnimales = datos.getString(this.getString(R.string.tieneAnimales));
            String tieneGallinas = datos.getString(this.getString(R.string.tieneGallinas));
            String tienePatos = datos.getString(this.getString(R.string.tienePatos));
            String tienePerros = datos.getString(this.getString(R.string.tienePerros));
            String tieneGatos = datos.getString(this.getString(R.string.tieneGatos));
            String tieneCerdos = datos.getString(this.getString(R.string.tieneCerdos));
            String tieneVacas = datos.getString(this.getString(R.string.tieneVacas));
            String tieneCabras = datos.getString(this.getString(R.string.tieneCabras));
            String tieneCaballos = datos.getString(this.getString(R.string.tieneCaballos));
            String tienePelibueys = datos.getString(this.getString(R.string.tienePelibueys));
            String tieneAves = datos.getString(this.getString(R.string.tieneAves));
            String tieneOtrosAnimales = datos.getString(this.getString(R.string.tieneOtrosAnimales));
            String cantidadGallinas = datos.getString(this.getString(R.string.cantidadGallinas));
            String cantidadPatos = datos.getString(this.getString(R.string.cantidadPatos));
            String cantidadPerros = datos.getString(this.getString(R.string.cantidadPerros));
            String cantidadGatos = datos.getString(this.getString(R.string.cantidadGatos));
            String cantidadCerdos = datos.getString(this.getString(R.string.cantidadCerdos));
            String cantidadVacas = datos.getString(this.getString(R.string.cantidadVacas));
            String cantidadCabras = datos.getString(this.getString(R.string.cantidadCabras));
            String cantidadCaballos = datos.getString(this.getString(R.string.cantidadCaballos));
            String cantidadPelibueys = datos.getString(this.getString(R.string.cantidadPelibueys));
            String cantidadAves = datos.getString(this.getString(R.string.cantidadAves));
            String cantidadOtrosAnimales = datos.getString(this.getString(R.string.cantidadOtrosAnimales));
            String gallinasDentroCasa = datos.getString(this.getString(R.string.gallinasDentroCasa));
            String patosDentroCasa = datos.getString(this.getString(R.string.patosDentroCasa));
            String perrosDentroCasa = datos.getString(this.getString(R.string.perrosDentroCasa));
            String gatosDentroCasa = datos.getString(this.getString(R.string.gatosDentroCasa));
            String cerdosDentroCasa = datos.getString(this.getString(R.string.cerdosDentroCasa));
            String vacasDentroCasa = datos.getString(this.getString(R.string.vacasDentroCasa));
            String cabrasDentroCasa = datos.getString(this.getString(R.string.cabrasDentroCasa));
            String caballosDentroCasa = datos.getString(this.getString(R.string.caballosDentroCasa));
            String pelibueysDentroCasa = datos.getString(this.getString(R.string.pelibueysDentroCasa));
            String avesDentroCasa = datos.getString(this.getString(R.string.avesDentroCasa));
            String otrosAnimalesDentroCasa = datos.getString(this.getString(R.string.otrosAnimalesDentroCasa));
            String personaFumaDentroCasa = datos.getString(this.getString(R.string.personaFumaDentroCasa));
            String tieneRecoleccionBasura = datos.getString(this.getString(R.string.tieneRecoleccionBasura));
            String cuantasVecesRecBasura = datos.getString(this.getString(R.string.cuantasVecesRecBasura));
            String dondePasaRecBasura = datos.getString(this.getString(R.string.dondePasaRecBasura));

            EncuestaCasa encuestaCasa = new EncuestaCasa();
            encuestaCasa.setCodigo(infoMovil.getId());
            encuestaCasa.setCasa(participante.getCasa());
            encuestaCasa.setParticipante(participante.getCodigo());
            encuestaCasa.setRecordDate(new Date());
            encuestaCasa.setRecordUser(username);
            encuestaCasa.setDeviceid(infoMovil.getDeviceId());
            encuestaCasa.setEstado(Constants.STATUS_NOT_SUBMITTED);
            encuestaCasa.setPasive(Constants.STATUS_NOT_PASIVE);

            if (tieneValor(problemaAgua)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + problemaAgua + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null) encuestaCasa.setProblemaAgua(resource.getCatKey());
            }
            if (tieneValor(tienePozo)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tienePozo + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null) encuestaCasa.setTienePozo(resource.getCatKey());
            }
            if (tieneValor(tieneMedidorAgua)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneMedidorAgua + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null) encuestaCasa.setTieneMedidorAgua(resource.getCatKey());
            }
            if (tieneValor(tieneTanque)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneTanque + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null) encuestaCasa.setTieneTanque(resource.getCatKey());
            }
            if (tieneValor(llaveAgua)) {
                MessageResource msLlaveAgua = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + llaveAgua + "' and "
                        + MainDBConstants.catRoot + "='CAT_DF'", null);
                if (msLlaveAgua != null) encuestaCasa.setUbicacionLlaveAgua(msLlaveAgua.getCatKey());
            }
            if (tieneValor(llaveCompartida)) {
                MessageResource msLLaveCompartida = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + llaveCompartida + "' and "
                        + MainDBConstants.catRoot + "='CAT_COMPARTIDO'", null);
                if (msLLaveCompartida != null) encuestaCasa.setLlaveCompartida(msLLaveCompartida.getCatKey());
            }
            if (tieneValor(almacenaAgua)) {
                MessageResource msAlmacenaAgua = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + almacenaAgua + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msAlmacenaAgua != null) encuestaCasa.setAlmacenaAgua(msAlmacenaAgua.getCatKey());
            }
            if (tieneValor(almacenaEnBarriles)) {
                MessageResource msEnBarriles = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + almacenaEnBarriles + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msEnBarriles != null) encuestaCasa.setAlmacenaEnBarriles(msEnBarriles.getCatKey());
            }
            if (tieneValor(barrilesTapados)) {
                MessageResource msBarrilesTapados = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + barrilesTapados + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msBarrilesTapados != null) encuestaCasa.setBarrilesTapados(msBarrilesTapados.getCatKey());
            }
            if (tieneValor(almacenaEnTanques)) {
                MessageResource msEnTanques = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + almacenaEnTanques + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msEnTanques != null) encuestaCasa.setAlmacenaEnTanques(msEnTanques.getCatKey());
            }
            if (tieneValor(tanquesTapados)) {
                MessageResource msTanquesTapados = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tanquesTapados + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msTanquesTapados != null) encuestaCasa.setTanquesTapados(msTanquesTapados.getCatKey());
            }
            if (tieneValor(almacenaEnPilas)) {
                MessageResource msEnPilas = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + almacenaEnPilas + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msEnPilas != null) encuestaCasa.setAlmacenaEnPilas(msEnPilas.getCatKey());
            }
            if (tieneValor(pilasTapadas)) {
                MessageResource msPilasTapadas = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + pilasTapadas + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msPilasTapadas != null) encuestaCasa.setPilasTapadas(msPilasTapadas.getCatKey());
            }
            if (tieneValor(cepillaPilas)) {
                MessageResource msCepillaPilas = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + pilasTapadas + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msCepillaPilas != null) encuestaCasa.setCepillaPilas(msCepillaPilas.getCatKey());
            }
            if (tieneValor(almacenaOtrosRecipientes)) {
                MessageResource msEnOtrosRec = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + almacenaOtrosRecipientes + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msEnOtrosRec != null) encuestaCasa.setAlmacenaOtrosRecipientes(msEnOtrosRec.getCatKey());
            }
            if (tieneValor(otrosRecipientesTapados)) {
                MessageResource msOtrosRecTapados = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + otrosRecipientesTapados + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msOtrosRecTapados != null)
                    encuestaCasa.setOtrosRecipientesTapados(msOtrosRecTapados.getCatKey());
            }
            if (tieneValor(cambiadoCasa)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + cambiadoCasa + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setCambiadoCasa(resource.getCatKey());
            }
            if (tieneValor(remodeladoCasa)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + remodeladoCasa + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setRemodeladoCasa(resource.getCatKey());
            }
            if (tieneValor(materialParedes)) {
                String keysMaterial = "";
                materialParedes = materialParedes.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msParedes = estudiosAdapter.getMessageResources(MainDBConstants.spanish + " in ('" + materialParedes + "') and "
                        + MainDBConstants.catRoot + "='CAT_MAT_PARED'", null);
                for(MessageResource ms : msParedes) {
                    keysMaterial += ms.getCatKey() + ",";
                }
                if (!keysMaterial.isEmpty())
                    keysMaterial = keysMaterial.substring(0, keysMaterial.length() - 1);
                encuestaCasa.setMaterialParedes(keysMaterial);
            }
            if (tieneValor(materialPiso)) {
                String keysMaterial = "";
                materialPiso = materialPiso.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msPiso = estudiosAdapter.getMessageResources(MainDBConstants.spanish + " in ('" + materialPiso + "') and "
                        + MainDBConstants.catRoot + "='CAT_MAT_PISO'", null);
                for(MessageResource ms : msPiso) {
                    keysMaterial += ms.getCatKey() + ",";
                }
                if (!keysMaterial.isEmpty())
                    keysMaterial = keysMaterial.substring(0, keysMaterial.length() - 1);
                encuestaCasa.setMaterialPiso(keysMaterial);
            }
            if (tieneValor(materialTecho)) {
                MessageResource msTecho = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + materialTecho + "' and "
                        + MainDBConstants.catRoot + "='CAT_MAT_TECHO'", null);
                if (msTecho != null) encuestaCasa.setMaterialTecho(msTecho.getCatKey());
            }
            if (tieneValor(ubicacionLavandero)) {
                MessageResource msLavandero = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + ubicacionLavandero + "' and "
                        + MainDBConstants.catRoot + "='CAT_DF'", null);
                if (msLavandero != null) encuestaCasa.setUbicacionLavandero(msLavandero.getCatKey());
            }
            if (tieneValor(casaPropia)) {
                MessageResource msCasaPropia = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + casaPropia + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msCasaPropia != null) encuestaCasa.setCasaPropia(msCasaPropia.getCatKey());
            }
            if (tieneValor(tieneTelevisor)) {
                MessageResource msTelevisor = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneTelevisor + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msTelevisor != null) encuestaCasa.setTieneTelevisor(msTelevisor.getCatKey());
            }
            if (tieneValor(tieneAbanico)) {
                MessageResource msAbanico = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneAbanico + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msAbanico != null) encuestaCasa.setTieneAbanico(msAbanico.getCatKey());
            }
            if (tieneValor(tieneRefrigerador)) {
                MessageResource msRefrigerador = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneRefrigerador + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msRefrigerador != null) encuestaCasa.setTieneRefrigerador(msRefrigerador.getCatKey());
            }
            if (tieneValor(tieneAireAcondicionado)) {
                MessageResource msAireAcondicionado = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneAireAcondicionado + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msAireAcondicionado != null)
                    encuestaCasa.setTienAireAcondicionado(msAireAcondicionado.getCatKey());
            }
            if (tieneValor(aireAcondicionadoFuncionando)) {
                MessageResource msAireAcondicionadoFun = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + aireAcondicionadoFuncionando + "' and "
                        + MainDBConstants.catRoot + "='CAT_FUN_AIRE'", null);
                if (msAireAcondicionadoFun != null)
                    encuestaCasa.setAireAcondicionadoFuncionando(msAireAcondicionadoFun.getCatKey());

            }
            if (tieneValor(lavadoraFuncionando)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + lavadoraFuncionando + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setLavadoraFuncionando(resource.getCatKey());
            }
            if (tieneValor(tieneMuro)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneMuro + "' and "
                        + MainDBConstants.catRoot + "='CAT_MUROCERCO'", null);
                if (resource != null)
                    encuestaCasa.setTieneMuro(resource.getCatKey());
            }
            if (tieneValor(tieneInternet)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneInternet + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setTieneInternet(resource.getCatKey());
            }
            if (tieneValor(tieneInodoro)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneInodoro + "' and "
                        + MainDBConstants.catRoot + "='CAT_INODOROLET'", null);
                if (resource != null)
                    encuestaCasa.setTieneInodoro(resource.getCatKey());
            }
            if (tieneValor(tieneServicioEnergia)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneServicioEnergia + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setTieneServicioEnergia(resource.getCatKey());
            }
            if (tieneValor(tieneMedidorEnergia)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneMedidorEnergia + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setTieneMedidorEnergia(resource.getCatKey());
            }
            if (tieneValor(casaDosPisos)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + casaDosPisos + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setCasaDosPisos(resource.getCatKey());
            }
            if (tieneValor(tieneOtrosServicios)) {
                String keyServicios = "";
                tieneOtrosServicios = tieneOtrosServicios.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msPiso = estudiosAdapter.getMessageResources(MainDBConstants.spanish + " in ('" + tieneOtrosServicios + "') and "
                        + MainDBConstants.catRoot + "='CAT_OTROS_SERVICIOS'", null);
                for(MessageResource ms : msPiso) {
                    keyServicios += ms.getCatKey() + ",";
                }
                if (!keyServicios.isEmpty())
                    keyServicios = keyServicios.substring(0, keyServicios.length() - 1);
                encuestaCasa.setTieneOtrosServicios(keyServicios);
            }
            if (tieneValor(tieneVehiculo)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneVehiculo + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setTieneVehiculo(resource.getCatKey());
            }
            if (tieneValor(tieneMoto)) {
                MessageResource msMTMoto = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneMoto + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msMTMoto != null) encuestaCasa.setTieneMoto(msMTMoto.getCatKey());
            }
            if (tieneValor(tieneCarro)) {
                MessageResource msMTCarro = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneCarro + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msMTCarro != null) encuestaCasa.setTieneCarro(msMTCarro.getCatKey());
            }
            if (tieneValor(tieneMicrobus)) {
                MessageResource msMTMicrobus = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneMicrobus + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msMTMicrobus != null) encuestaCasa.setTieneMicrobus(msMTMicrobus.getCatKey());
            }
            if (tieneValor(tieneCamioneta)) {
                MessageResource msMTCamioneta = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneCamioneta + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msMTCamioneta != null) encuestaCasa.setTieneCamioneta(msMTCamioneta.getCatKey());
            }
            if (tieneValor(tieneCamion)) {
                MessageResource msMTCamion = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneCamion + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msMTCamion != null) encuestaCasa.setTieneCamion(msMTCamion.getCatKey());
            }
            if (tieneValor(tieneOtroMedioTransAuto)) {
                MessageResource msMTOtro = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneOtroMedioTransAuto + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msMTOtro != null) encuestaCasa.setTieneOtroMedioTransAuto(msMTOtro.getCatKey());
            }
            if (tieneValor(tipoCocina)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tipoCocina + "' and "
                        + MainDBConstants.catRoot + "='CAT_TIPO_COCINA'", null);
                if (resource != null)
                    encuestaCasa.setTipoCocina(resource.getCatKey());
            }
            if (tieneValor(tieneHorno)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneHorno + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setTieneHorno(resource.getCatKey());
            }
            if (tieneValor(cocinaConLenia)) {
                MessageResource msCocina = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + cocinaConLenia + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msCocina != null) encuestaCasa.setCocinaConLenia(msCocina.getCatKey());
            }
            if (tieneValor(ubicacionCocinaLenia)) {
                MessageResource msCocinaUbicacion = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + ubicacionCocinaLenia + "' and "
                        + MainDBConstants.catRoot + "='CAT_DF'", null);
                if (msCocinaUbicacion != null) encuestaCasa.setUbicacionCocinaLenia(msCocinaUbicacion.getCatKey());
            }
            if (tieneValor(periodicidadCocinaLenia)) {
                MessageResource msCocinaPeriodicidad = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + periodicidadCocinaLenia + "' and "
                        + MainDBConstants.catRoot + "='CAT_FREC_COCINA'", null);
                if (msCocinaPeriodicidad != null)
                    encuestaCasa.setPeriodicidadCocinaLenia(msCocinaPeriodicidad.getCatKey());
            }
            if (tieneValor(tieneAnimales)) {
                MessageResource msAnimales = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneAnimales + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msAnimales != null) encuestaCasa.setTieneAnimales(msAnimales.getCatKey());
            }
            if (tieneValor(tieneGallinas)) {
                MessageResource msAnimalesGallinas = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneGallinas + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msAnimalesGallinas != null) encuestaCasa.setTieneGallinas(msAnimalesGallinas.getCatKey());
            }
            if (tieneValor(gallinasDentroCasa)) {
                MessageResource msGallinasDC = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + gallinasDentroCasa + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msGallinasDC != null) encuestaCasa.setGallinasDentroCasa(msGallinasDC.getCatKey());
            }
            if (tieneValor(tienePatos)) {
                MessageResource msAnimalesPatos = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tienePatos + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msAnimalesPatos != null) encuestaCasa.setTienePatos(msAnimalesPatos.getCatKey());
            }
            if (tieneValor(patosDentroCasa)) {
                MessageResource msPatosDC = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + patosDentroCasa + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msPatosDC != null) encuestaCasa.setPatosDentroCasa(msPatosDC.getCatKey());
            }
            if (tieneValor(tieneCerdos)) {
                MessageResource msAnimalesCerdos = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneCerdos + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msAnimalesCerdos != null) encuestaCasa.setTieneCerdos(msAnimalesCerdos.getCatKey());
            }
            if (tieneValor(cerdosDentroCasa)) {
                MessageResource msCerdosDC = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + cerdosDentroCasa + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msCerdosDC != null) encuestaCasa.setCerdosDentroCasa(msCerdosDC.getCatKey());
            }
            if (tieneValor(tienePerros)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tienePerros + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setTienePerros(resource.getCatKey());
            }
            if (tieneValor(tieneGatos)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneGatos + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setTieneGatos(resource.getCatKey());
            }
            if (tieneValor(tieneVacas)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneVacas + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setTieneVacas(resource.getCatKey());
            }
            if (tieneValor(tieneCabras)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneCabras + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setTieneCabras(resource.getCatKey());
            }
            if (tieneValor(tieneCaballos)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneCaballos + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setTieneCaballos(resource.getCatKey());
            }
            if (tieneValor(tienePelibueys)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tienePelibueys + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setTienePelibueys(resource.getCatKey());
            }
            if (tieneValor(tieneAves)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneAves + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setTieneAves(resource.getCatKey());
            }
            if (tieneValor(tieneOtrosAnimales)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneOtrosAnimales + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setTieneOtrosAnimales(resource.getCatKey());
            }
            if (tieneValor(perrosDentroCasa)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + perrosDentroCasa + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setPerrosDentroCasa(resource.getCatKey());
            }
            if (tieneValor(gatosDentroCasa)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + gatosDentroCasa + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setGatosDentroCasa(resource.getCatKey());
            }
            if (tieneValor(vacasDentroCasa)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + vacasDentroCasa + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setVacasDentroCasa(resource.getCatKey());
            }
            if (tieneValor(cabrasDentroCasa)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + cabrasDentroCasa + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setCabrasDentroCasa(resource.getCatKey());
            }
            if (tieneValor(caballosDentroCasa)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + caballosDentroCasa + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setCaballosDentroCasa(resource.getCatKey());
            }
            if (tieneValor(pelibueysDentroCasa)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + pelibueysDentroCasa + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setPelibueysDentroCasa(resource.getCatKey());
            }
            if (tieneValor(avesDentroCasa)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + avesDentroCasa + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setAvesDentroCasa(resource.getCatKey());
            }
            if (tieneValor(otrosAnimalesDentroCasa)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + otrosAnimalesDentroCasa + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null)
                    encuestaCasa.setOtrosAnimalesDentroCasa(resource.getCatKey());
            }
            if (tieneValor(personaFumaDentroCasa)) {
                MessageResource msFuman = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + personaFumaDentroCasa + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (msFuman != null) encuestaCasa.setPersonaFumaDentroCasa(msFuman.getCatKey());
            }
            if (tieneValor(tieneRecoleccionBasura)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tieneRecoleccionBasura + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (resource != null) encuestaCasa.setTieneRecoleccionBasura(resource.getCatKey());
            }

            if (tieneValor(frecuenciaSeVaAgua)) {
                MessageResource resource = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + frecuenciaSeVaAgua + "' and "
                        + MainDBConstants.catRoot + "='CAT_FREC_VA_AGUA'", null);
                if (resource != null) encuestaCasa.setFrecuenciaSeVaAgua(resource.getCatKey());
            }

            encuestaCasa.setEdadMujer1(edadMujer1);
            encuestaCasa.setEdadMujer2(edadMujer2);
            encuestaCasa.setEdadMujer3(edadMujer3);
            encuestaCasa.setEdadMujer4(edadMujer4);
            encuestaCasa.setEdadMujer5(edadMujer5);
            encuestaCasa.setEdadMujer6(edadMujer6);
            encuestaCasa.setEdadMujer7(edadMujer7);
            encuestaCasa.setEdadMujer8(edadMujer8);
            encuestaCasa.setEdadMujer9(edadMujer9);
            encuestaCasa.setEdadMujer10(edadMujer10);
            encuestaCasa.setEdadHombre1(edadHombre1);
            encuestaCasa.setEdadHombre2(edadHombre2);
            encuestaCasa.setEdadHombre3(edadHombre3);
            encuestaCasa.setEdadHombre4(edadHombre4);
            encuestaCasa.setEdadHombre5(edadHombre5);
            encuestaCasa.setEdadHombre6(edadHombre6);
            encuestaCasa.setEdadHombre7(edadHombre7);
            encuestaCasa.setEdadHombre8(edadHombre8);
            encuestaCasa.setEdadHombre9(edadHombre9);
            encuestaCasa.setEdadHombre10(edadHombre10);
            encuestaCasa.setOtrosRecipientes(otrosRecipientes);
            encuestaCasa.setOtroMaterialParedes(otroMaterialParedes);
            encuestaCasa.setOtroMaterialPiso(otroMaterialPiso);
            encuestaCasa.setOtroMaterialTecho(otroMaterialTecho);
            encuestaCasa.setOtroMedioTransAuto(otroMedioTransAuto);
            encuestaCasa.setMarcaMoto(marcaMoto);
            encuestaCasa.setMarcaCarro(marcaCarro);
            encuestaCasa.setMarcaMicrobus(marcaMicrobus);
            encuestaCasa.setMarcaCamioneta(marcaCamioneta);
            encuestaCasa.setMarcaCamion(marcaCamion);
            encuestaCasa.setMarcaOtroMedioTrans(marcaOtroMedioTrans);
            encuestaCasa.setDondePasaRecBasura(dondePasaRecBasura);
            encuestaCasa.setFrecCepillaPilas(frecCepillaPilas);
            encuestaCasa.setOtraFrecuenciaSeVaAgua(otraFrecuenciaSeVaAgua);

            if (tieneValor(cuantasPersonas)) encuestaCasa.setCuantasPersonas(Integer.parseInt(cuantasPersonas));
            if (tieneValor(cuantasMujeres)) encuestaCasa.setCuantasMujeres(Integer.parseInt(cuantasMujeres));
            if (tieneValor(cuantosHombres)) encuestaCasa.setCuantosHombres(Integer.parseInt(cuantosHombres));
            if (tieneValor(cuantoCuartos)) encuestaCasa.setCantidadCuartos(Integer.parseInt(cuantoCuartos));
            if (tieneValor(cuartosDormir)) encuestaCasa.setCantidadCuartosDormir(Integer.parseInt(cuartosDormir));
            if (tieneValor(horasSinAgua)) encuestaCasa.setHrsSinServicioAgua(Integer.parseInt(horasSinAgua));
            if (tieneValor(numBarriles)) encuestaCasa.setNumBarriles(Integer.valueOf(numBarriles));
            if (tieneValor(numTanques)) encuestaCasa.setNumTanques(Integer.valueOf(numTanques));
            if (tieneValor(numPilas)) encuestaCasa.setNumPilas(Integer.valueOf(numPilas));
            if (tieneValor(numOtrosRecipientes)) encuestaCasa.setNumOtrosRecipientes(Integer.valueOf(numOtrosRecipientes));
            if (tieneValor(numTelevisores)) encuestaCasa.setNumTelevisores(Integer.valueOf(numTelevisores));
            if (tieneValor(numAbanicos)) encuestaCasa.setNumAbanicos(Integer.valueOf(numAbanicos));
            if (tieneValor(numRefrigeradores)) encuestaCasa.setNumRefrigeradores(Integer.valueOf(numRefrigeradores));
            if (tieneValor(numAireAcondicionado)) encuestaCasa.setNumAireAcondicionado(Integer.valueOf(numAireAcondicionado));
            if (tieneValor(anioFabMoto)) encuestaCasa.setAnioFabMoto(Integer.valueOf(anioFabMoto));
            if (tieneValor(anioFabCarro)) encuestaCasa.setAnioFabCarro(Integer.valueOf(anioFabCarro));
            if (tieneValor(anioFabMicrobus)) encuestaCasa.setAnioFabMicrobus(Integer.valueOf(anioFabMicrobus));
            if (tieneValor(anioFabCamioneta)) encuestaCasa.setAnioFabCamioneta(Integer.valueOf(anioFabCamioneta));
            if (tieneValor(anioFabCamion)) encuestaCasa.setAnioFabCamion(Integer.valueOf(anioFabCamion));
            if (tieneValor(anioFabOtroMedioTrans)) encuestaCasa.setAnioFabOtroMedioTrans(Integer.valueOf(anioFabOtroMedioTrans));
            if (tieneValor(cuantosQuemadores)) encuestaCasa.setCuantosQuemadores(Integer.valueOf(cuantosQuemadores));
            if (tieneValor(numDiarioCocinaLenia)) encuestaCasa.setNumDiarioCocinaLenia(Integer.valueOf(numDiarioCocinaLenia));
            if (tieneValor(numSemanalCocinaLenia)) encuestaCasa.setNumSemanalCocinaLenia(Integer.valueOf(numSemanalCocinaLenia));
            if (tieneValor(numQuincenalCocinaLenia)) encuestaCasa.setNumQuincenalCocinaLenia(Integer.valueOf(numQuincenalCocinaLenia));
            if (tieneValor(numMensualCocinaLenia)) encuestaCasa.setNumMensualCocinaLenia(Integer.valueOf(numMensualCocinaLenia));
            if (tieneValor(cantidadGallinas)) encuestaCasa.setCantidadGallinas(Integer.valueOf(cantidadGallinas));
            if (tieneValor(cantidadPatos)) encuestaCasa.setCantidadPatos(Integer.valueOf(cantidadPatos));
            if (tieneValor(cantidadCerdos)) encuestaCasa.setCantidadCerdos(Integer.valueOf(cantidadCerdos));
            if (tieneValor(cantidadPerros)) encuestaCasa.setCantidadPerros(Integer.valueOf(cantidadPerros));
            if (tieneValor(cantidadGatos)) encuestaCasa.setCantidadGatos(Integer.valueOf(cantidadGatos));
            if (tieneValor(cantidadVacas)) encuestaCasa.setCantidadVacas(Integer.valueOf(cantidadVacas));
            if (tieneValor(cantidadCabras)) encuestaCasa.setCantidadCabras(Integer.valueOf(cantidadCabras));
            if (tieneValor(cantidadCaballos)) encuestaCasa.setCantidadCaballos(Integer.valueOf(cantidadCaballos));
            if (tieneValor(cantidadPelibueys)) encuestaCasa.setCantidadPelibueys(Integer.valueOf(cantidadPelibueys));
            if (tieneValor(cantidadAves)) encuestaCasa.setCantidadAves(Integer.valueOf(cantidadAves));
            if (tieneValor(cantidadOtrosAnimales)) encuestaCasa.setCantidadOtrosAnimales(Integer.valueOf(cantidadOtrosAnimales));
            if (tieneValor(cuantasVecesRecBasura)) encuestaCasa.setCuantasVecesRecBasura(Integer.valueOf(cuantasVecesRecBasura));
            if (tieneValor(cantInodoro)) encuestaCasa.setCantInodoro(Integer.valueOf(cantInodoro));
            if (tieneValor(cantLetrina)) encuestaCasa.setCantLetrina(Integer.valueOf(cantLetrina));

            estudiosAdapter.crearEncuestaCasa(encuestaCasa);

            List<Participante> participantes = estudiosAdapter.getParticipantes(MainDBConstants.casa + "=" + participante.getCasa().getCodigo(), null);
            for (Participante part : participantes) {
                ParticipanteProcesos procesos = part.getProcesos();
                procesos.setPendienteEnCasa(Constants.NOKEYSND);
                procesos.setRecordDate(new Date());
                procesos.setRecordUser(username);
                procesos.setDeviceid(infoMovil.getDeviceId());
                procesos.setEstado('0');
                procesos.setPasive('0');
                estudiosAdapter.editarParticipanteProcesos(procesos);
            }

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
            showToast(ex.getLocalizedMessage());
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
                                    ((NuevaEncuestaCasaActivity) Objects.requireNonNull(getActivity())).saveData();
                                }
                            }
                    )
                    .setNegativeButton(R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((NuevaEncuestaCasaActivity) Objects.requireNonNull(getActivity())).createDialog(EXIT);;
                                }
                            }
                    )
                    .create();
        }
    }
}
