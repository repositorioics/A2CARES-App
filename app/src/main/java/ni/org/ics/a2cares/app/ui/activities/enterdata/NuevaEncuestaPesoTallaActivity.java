package ni.org.ics.a2cares.app.ui.activities.enterdata;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ni.org.ics.a2cares.app.AbstractAsyncActivity;
import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.domain.core.ParticipanteProcesos;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.domain.survey.EncuestaPesoTalla;
import ni.org.ics.a2cares.app.preferences.PreferencesActivity;
import ni.org.ics.a2cares.app.database.constants.*;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuParticipanteActivity;
import ni.org.ics.a2cares.app.ui.forms.EncuestaPesoTallaForm;
import ni.org.ics.a2cares.app.ui.forms.EncuestaPesoTallaFormLabels;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.utils.DeviceInfo;
import ni.org.ics.a2cares.app.wizard.model.*;
import ni.org.ics.a2cares.app.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.a2cares.app.wizard.ui.ReviewFragment;
import ni.org.ics.a2cares.app.wizard.ui.StepPagerStrip;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Miguel Salinas on 5/17/2017.
 * V1.0
 */
public class NuevaEncuestaPesoTallaActivity extends AbstractAsyncActivity implements
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

    private EncuestaPesoTallaFormLabels labels;
    private EstudioDBAdapter estudiosAdapter;
    private DeviceInfo infoMovil;
    private static Participante participante = new Participante();
    private String username;
    private SharedPreferences settings;
    private static final int EXIT = 1;
    private AlertDialog alertDialog;
    private boolean notificarCambios = true;
    private boolean visitaExitosa = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_enter);
        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);
        infoMovil = new DeviceInfo(NuevaEncuestaPesoTallaActivity.this);
        participante = (Participante) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
        visitaExitosa = getIntent().getBooleanExtra(Constants.VISITA_EXITOSA, false);

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new EncuestaPesoTallaForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        mWizardModel.registerListener(this);
        labels = new EncuestaPesoTallaFormLabels();

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
        try {
            boolean visible = false;
            if (page.getTitle().equals(labels.getTomoMedidaSn())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getPeso1()), visible, null);
                changeStatus(mWizardModel.findByKey(labels.getTalla1()), visible, null);
                changeStatus(mWizardModel.findByKey(labels.getImc1()), visible, null);
                changeStatus(mWizardModel.findByKey(labels.getPeso2()), visible, null);
                changeStatus(mWizardModel.findByKey(labels.getTalla2()), visible, null);
                changeStatus(mWizardModel.findByKey(labels.getImc2()), visible, null);
                changeStatus(mWizardModel.findByKey(labels.getPeso3()), false, null);
                changeStatus(mWizardModel.findByKey(labels.getTalla3()), false, null);
                changeStatus(mWizardModel.findByKey(labels.getImc3()), false, null);
                changeStatus(mWizardModel.findByKey(labels.getRazonNoTomoMedidas()), !visible, null);
                //notificarCambios = false;
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getPeso1())) {

                Double imc = 0D;
                Page talla = mWizardModel.findByKey(labels.getTalla1());
                String valorTalla = talla.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                String valorPeso = page.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                if (valorTalla != null && !valorTalla.isEmpty()) {
                    Double dPeso = Double.valueOf(valorPeso);
                    if (valorPeso != null && !valorPeso.isEmpty()) {
                        Double dTalla = Double.parseDouble(valorTalla) / 100; // se convierte a metro
                        imc = dPeso / Math.pow(dTalla, 2);
                        visible = true;
                    }
                }
                changeStatus(mWizardModel.findByKey(labels.getImc1()), visible, String.valueOf(imc));
                visible = diferenciaMedidadValida();
                changeStatus(mWizardModel.findByKey(labels.getDifMediciones()), visible, null);
                changeStatus(mWizardModel.findByKey(labels.getPeso3()), visible, null);
                changeStatus(mWizardModel.findByKey(labels.getTalla3()), visible, null);
                changeStatus(mWizardModel.findByKey(labels.getImc3()), false, null);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTalla1())) {

                Double imc = 0D;
                Page peso = mWizardModel.findByKey(labels.getPeso1());
                String valorPeso = peso.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                String valorTalla = page.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                if (valorPeso != null && !valorPeso.isEmpty()) {
                    Double dPeso = Double.valueOf(valorPeso);
                    if (valorTalla != null && !valorTalla.isEmpty()) {
                        Double dTalla = Double.parseDouble(valorTalla) / 100; // se convierte a metro
                        imc = dPeso / Math.pow(dTalla, 2);
                        visible = true;
                    }
                }
                changeStatus(mWizardModel.findByKey(labels.getImc1()), visible, String.valueOf(imc));
                visible = diferenciaMedidadValida();
                changeStatus(mWizardModel.findByKey(labels.getDifMediciones()), visible, null);
                changeStatus(mWizardModel.findByKey(labels.getPeso3()), visible, null);
                changeStatus(mWizardModel.findByKey(labels.getTalla3()), visible, null);
                changeStatus(mWizardModel.findByKey(labels.getImc3()), false, null);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getPeso2())) {

                Double imc = 0D;
                Page talla = mWizardModel.findByKey(labels.getTalla2());
                String valorTalla = talla.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                String valorPeso = page.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                if (valorTalla != null && !valorTalla.isEmpty()) {
                    Double dPeso = Double.valueOf(valorPeso);
                    if (valorPeso != null && !valorPeso.isEmpty()) {
                        Double dTalla = Double.parseDouble(valorTalla) / 100; // se convierte a metro
                        imc = dPeso / Math.pow(dTalla, 2);
                        visible = true;
                    }
                }
                changeStatus(mWizardModel.findByKey(labels.getImc2()), visible, String.valueOf(imc));
                visible = diferenciaMedidadValida();
                changeStatus(mWizardModel.findByKey(labels.getDifMediciones()), visible, null);
                changeStatus(mWizardModel.findByKey(labels.getPeso3()), visible, null);
                changeStatus(mWizardModel.findByKey(labels.getTalla3()), visible, null);
                changeStatus(mWizardModel.findByKey(labels.getImc3()), false, null);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTalla2())) {

                Double imc = 0D;
                Page peso = mWizardModel.findByKey(labels.getPeso2());
                String valorPeso = peso.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                String valorTalla = page.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                if (valorPeso != null && !valorPeso.isEmpty()) {
                    Double dPeso = Double.valueOf(valorPeso);
                    if (valorTalla != null && !valorTalla.isEmpty()) {
                        Double dTalla = Double.parseDouble(valorTalla) / 100; // se convierte a metro
                        imc = dPeso / Math.pow(dTalla, 2);
                        visible = true;
                    }
                }
                changeStatus(mWizardModel.findByKey(labels.getImc2()), visible, String.valueOf(imc));
                visible = diferenciaMedidadValida();
                changeStatus(mWizardModel.findByKey(labels.getDifMediciones()), visible, null);
                changeStatus(mWizardModel.findByKey(labels.getPeso3()), visible, null);
                changeStatus(mWizardModel.findByKey(labels.getTalla3()), visible, null);
                changeStatus(mWizardModel.findByKey(labels.getImc3()), false, null);
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getPeso3())) {

                Double imc = 0D;
                Page talla = mWizardModel.findByKey(labels.getTalla3());
                String valorTalla = talla.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                String valorPeso = page.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                if (valorTalla != null && !valorTalla.isEmpty()) {
                    Double dPeso = Double.valueOf(valorPeso);
                    if (valorPeso != null && !valorPeso.isEmpty()) {
                        Double dTalla = Double.parseDouble(valorTalla) / 100; // se convierte a metro
                        imc = dPeso / Math.pow(dTalla, 2);
                        visible = true;
                    }
                }
                changeStatus(mWizardModel.findByKey(labels.getImc3()), visible, String.valueOf(imc));
                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTalla3())) {
                Page peso = mWizardModel.findByKey(labels.getPeso3());
                String valorPeso = peso.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                String valorTalla = page.getData().getString(NumberPage.SIMPLE_DATA_KEY);
                Double imc = 0D;
                if (valorPeso != null && !valorPeso.isEmpty()) {
                    Double dPeso = Double.valueOf(valorPeso);
                    if (valorTalla != null && !valorTalla.isEmpty()) {
                        Double dTalla = Double.valueOf(valorTalla)/100; // se convierte a metro
                        imc = dPeso/Math.pow(dTalla, 2);
                        visible = true;
                    }
                }
                changeStatus(mWizardModel.findByKey(labels.getImc3()), visible, String.valueOf(imc));
                onPageTreeChanged();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public boolean diferenciaMedidadValida() {
        Page peso1 = mWizardModel.findByKey(labels.getPeso1());
        Page peso2 = mWizardModel.findByKey(labels.getPeso2());
        Page talla1 = mWizardModel.findByKey(labels.getTalla1());
        Page talla2 = mWizardModel.findByKey(labels.getTalla2());

        double difTalla = 0D;
        double difPeso = 0D;
        String valorPeso1 = peso1.getData().getString(NumberPage.SIMPLE_DATA_KEY);
        String valorTalla1 = talla1.getData().getString(NumberPage.SIMPLE_DATA_KEY);
        String valorPeso2 = peso2.getData().getString(NumberPage.SIMPLE_DATA_KEY);
        String valorTalla2 = talla2.getData().getString(NumberPage.SIMPLE_DATA_KEY);
        if (valorPeso2 != null && !valorPeso2.isEmpty()) {
            Double dPeso2 = Double.valueOf(valorPeso2);
            if (valorTalla2 != null && !valorTalla2.isEmpty()) {
                //calcular diferencia de mediciones
                Double dPeso1 = Double.valueOf(valorPeso1);
                Double dTalla1 = Double.valueOf(valorTalla1);
                Double dTalla2 = Double.valueOf(valorTalla2);
                if (dPeso1 - dPeso2 > 0)
                    difPeso = ((((dPeso1 - dPeso2) * 10 + 0.5) / 10) / dPeso1 * 100);
                else
                    difPeso = ((((dPeso2 - dPeso1) * 10 - 0.5) / 10) / dPeso1 * 100);
                if (dTalla1 - dTalla2 > 0)
                    difTalla = ((((dTalla1 - dTalla2) * 10 + 0.5) / 10) / dTalla1 * 100);
                else
                    difTalla = ((((dTalla2 - dTalla1) * 10 - 0.5) / 10) / dTalla1 * 100);
                return difPeso >= 5 || difTalla >= 5;
            } return true;
        } return true;
    }

    public void changeStatus(Page page, boolean visible, String hint){
        String clase = page.getClass().toString();
        if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.SingleFixedChoicePage")){
            SingleFixedChoicePage modifPage = (SingleFixedChoicePage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.BarcodePage")){
            BarcodePage modifPage = (BarcodePage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.LabelPage")){
            LabelPage modifPage = (LabelPage) page;
            if (hint!=null)
                modifPage.setHint(hint);
            modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.TextPage")){
            TextPage modifPage = (TextPage) page; modifPage.setValue("").setmVisible(visible); //modifPage.resetData(new Bundle()); modifPage.setmVisible(visible); //
        }
        else if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.NumberPage")){
            NumberPage modifPage = (NumberPage) page; modifPage.setValue("").setmVisible(visible); //modifPage.resetData(new Bundle()); modifPage.setmVisible(visible); //
        }
        else if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.MultipleFixedChoicePage")){
            MultipleFixedChoicePage modifPage = (MultipleFixedChoicePage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.DatePage")){
            DatePage modifPage = (DatePage) page; modifPage.setValue("").setmVisible(visible);
        }
    }

    private boolean tieneValor(String entrada){
        return (entrada != null && !entrada.isEmpty());
    }


    public void saveData() {
        try {
            Map<String, String> mapa = mWizardModel.getAnswers();
            Bundle datos = new Bundle();
            for (Map.Entry<String, String> entry : mapa.entrySet()) {
                datos.putString(entry.getKey(), entry.getValue());
            }

            String tomoMedidaSn = datos.getString(this.getString(R.string.tomoMedidaSn));
            String razonNoTomoMedidas = datos.getString(this.getString(R.string.razonNoTomoMedidas));
            String peso1 = datos.getString(this.getString(R.string.peso1));
            String peso2 = datos.getString(this.getString(R.string.peso2));
            String peso3 = datos.getString(this.getString(R.string.peso3));
            String talla1 = datos.getString(this.getString(R.string.talla1));
            String talla2 = datos.getString(this.getString(R.string.talla2));
            String talla3 = datos.getString(this.getString(R.string.talla3));

            String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
            estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(), mPass, false, false);
            estudiosAdapter.open();
            EncuestaPesoTalla encuesta = new EncuestaPesoTalla();
            encuesta.setCodigo(infoMovil.getId());
            encuesta.setParticipante(participante);

            if (tieneValor(tomoMedidaSn)){
                MessageResource mstomoMedidaSn = estudiosAdapter.getMessageResource(MainDBConstants.spanish + "='" + tomoMedidaSn + "' and "
                        + MainDBConstants.catRoot + "='CAT_SINO'", null);
                if (mstomoMedidaSn != null) encuesta.setTomoMedidaSn(mstomoMedidaSn.getCatKey());
            }
            encuesta.setRazonNoTomoMedidas(razonNoTomoMedidas);
            if (tieneValor(peso1)) encuesta.setPeso1(Double.valueOf(peso1));
            if (tieneValor(peso2)) encuesta.setPeso2(Double.valueOf(peso2));
            if (tieneValor(peso3)) encuesta.setPeso3(Double.valueOf(peso3));
            if (tieneValor(talla1)) encuesta.setTalla1(Double.valueOf(talla1));
            if (tieneValor(talla2)) encuesta.setTalla2(Double.valueOf(talla2));
            if (tieneValor(talla3)) encuesta.setTalla3(Double.valueOf(talla3));

            if (tieneValor(peso1) && tieneValor(talla1)){
                Double dPeso = Double.valueOf(peso1);
                Double dTalla = Double.parseDouble(talla1)/100; // se convierte a metro
                Double imc = dPeso/Math.pow(dTalla, 2);
                encuesta.setImc1(imc);
            }
            if (tieneValor(peso2) && tieneValor(talla2)){
                Double dPeso = Double.valueOf(peso2);
                Double dTalla = Double.parseDouble(talla2)/100; // se convierte a metro
                Double imc = dPeso/Math.pow(dTalla, 2);
                encuesta.setImc2(imc);
            }
            if (tieneValor(peso3) && tieneValor(talla3)){
                Double dPeso = Double.valueOf(peso3);
                Double dTalla = Double.parseDouble(talla3)/100; // se convierte a metro
                Double imc = dPeso/Math.pow(dTalla, 2);
                encuesta.setImc3(imc);
            }
            if (tieneValor(peso1) && tieneValor(peso2)) {
                Double dPeso1 = Double.valueOf(peso1);
                Double dPeso2 = Double.valueOf(peso2);
                Double difPeso;
                if (dPeso1 - dPeso2 > 0)
                    difPeso = ((((dPeso1 - dPeso2) * 10 + 0.5) / 10) / dPeso1 * 100);
                else
                    difPeso = ((((dPeso2 - dPeso1) * 10 - 0.5) / 10) / dPeso1 * 100);
                encuesta.setDifPeso(difPeso);
            }
            if (tieneValor(talla1) && tieneValor(talla2)) {
                Double dTalla1 = Double.valueOf(talla1);
                Double dTalla2 = Double.valueOf(talla2);
                Double difTalla;
                if (dTalla1 - dTalla2 > 0)
                    difTalla = ((((dTalla1 - dTalla2) * 10 + 0.5) / 10) / dTalla1 * 100);
                else
                    difTalla = ((((dTalla2 - dTalla1) * 10 - 0.5) / 10) / dTalla1 * 100);
                encuesta.setDifTalla(difTalla);
            }

            //Metadata
            encuesta.setRecordDate(new Date());
            encuesta.setRecordUser(username);
            encuesta.setDeviceid(infoMovil.getDeviceId());
            encuesta.setEstado('0');
            encuesta.setPasive('0');
            /*boolean actualizada = false;
            EncuestaPesoTalla encuestaExiste = estudiosAdapter.getEncuestasPesoTalla(EncuestasDBConstants.participante + "=" + participante.getCodigo(), EncuestasDBConstants.participante);
            if (encuestaExiste != null && encuestaExiste.getParticipante() != null)
                actualizada = estudiosAdapter.editarEncuestasPesoTalla(encuesta);
            else */
            estudiosAdapter.crearEncuestasPesoTalla(encuesta);

            participante.getProcesos().setPendientePyT(Constants.NOKEYSND);
            participante.getProcesos().setRecordDate(new Date());
            participante.getProcesos().setRecordUser(username);
            participante.getProcesos().setDeviceid(infoMovil.getDeviceId());
            participante.getProcesos().setEstado('0');
            participante.getProcesos().setPasive('0');
            estudiosAdapter.editarParticipanteProcesos(participante.getProcesos());

            showToast(getString(R.string.success));
            Bundle arguments = new Bundle();
            arguments.putSerializable(Constants.PARTICIPANTE, participante);
            Intent i = new Intent(getApplicationContext(),
                    MenuParticipanteActivity.class);
            i.putExtras(arguments);
            i.putExtra(Constants.VISITA_EXITOSA, visitaExitosa);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        } catch (Exception ex) {
            ex.printStackTrace();
            showToast(getString(R.string.error, ex.getLocalizedMessage()));
        } finally {
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
                                    ((NuevaEncuestaPesoTallaActivity) Objects.requireNonNull(getActivity())).saveData();
                                }
                            }
                    )
                    .setNegativeButton(R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((NuevaEncuestaPesoTallaActivity) Objects.requireNonNull(getActivity())).createDialog(EXIT);;
                                }
                            }
                    )
                    .create();
        }
    }

}
