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
import android.widget.ImageButton;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ni.org.ics.a2cares.app.AbstractAsyncActivity;
import ni.org.ics.a2cares.app.MainActivity;
import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.medico.InformeFindeDiaMedicos;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.preferences.PreferencesActivity;
import ni.org.ics.a2cares.app.ui.forms.AdmisionPacientesForm;
import ni.org.ics.a2cares.app.ui.forms.AdmisionPacientesFormLabels;
import ni.org.ics.a2cares.app.ui.forms.FinDiaMedicosFormLabels;
import ni.org.ics.a2cares.app.ui.forms.FindeDiaMedicosForm;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.utils.DeviceInfo;
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

/**
 * Created by Everts Morales 18/12/2023.
 * V1.0
 */
public class NuevoInformeFinDiaMedicoActivity extends AbstractAsyncActivity implements
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
    private Participante mAddParticipante;
    private ImageButton mBarcodeButton;
    public static final int BARCODE_CAPTURE = 2;

    private List<Page> mCurrentPageSequence;
    private StepPagerStrip mStepPagerStrip;

    private FinDiaMedicosFormLabels labels;
    private EstudioDBAdapter estudiosAdapter;
    private DeviceInfo infoMovil;
    private static Participante participante = new Participante();
    private String username;
    private SharedPreferences settings;
    private static final int EXIT = 1;
    private AlertDialog alertDialog;
    private boolean notificarCambios = true;
    private boolean visitaExitosa = false;
    int atendidoscohort = 0;
    int atendidosNOcohort = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_enter);
        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);
        infoMovil = new DeviceInfo(NuevoInformeFinDiaMedicoActivity.this);
       // participante = (Participante) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
      //  visitaExitosa = getIntent().getBooleanExtra(Constants.VISITA_EXITOSA, false);

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new FindeDiaMedicosForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        mWizardModel.registerListener(this);
        labels = new FinDiaMedicosFormLabels();

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
        }catch (Exception ex){
            ex.printStackTrace();
        }
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
        try {
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
                                MainActivity.class);
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
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void updateBottomBar() {
        try {
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
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private boolean recalculateCutOffPage() {
        // Cut off the pager adapter at first required page that isn't completed
        try {
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
                    if ((np.ismValRange() && (np.getmGreaterOrEqualsThan() > Double.valueOf(valor) || np.getmLowerOrEqualsThan() < Double.valueOf(valor)))
                            || (np.ismValPattern() && !valor.matches(np.getmPattern()))) {
                        cutOffPage = i;
                        break;
                    }
                }
                if (!page.getData().isEmpty() && clase.equals("class ni.org.ics.a2cares.app.wizard.model.TextPage")) {
                    TextPage tp = (TextPage) page;
                    if (tp.ismValPattern()) {
                        String valor = tp.getData().getString(TextPage.SIMPLE_DATA_KEY);
                        if (!valor.matches(tp.getmPattern())) {
                            cutOffPage = i;
                            break;
                        }
                    }
                }
                if (!page.getData().isEmpty() && clase.equals("class ni.org.ics.a2cares.app.wizard.model.BarcodePage")) {
                    BarcodePage tp = (BarcodePage) page;
                    if (tp.ismValPattern()) {
                        String valor = tp.getData().getString(BarcodePage.SIMPLE_DATA_KEY);
                        if (valor != null) {
                            if (valor.length() > 4) {
                                valor = valor.substring(3, 7);
                            }
                        }
                        //tp.setValue(valor) ;
                        if (!valor.matches(tp.getmPattern())) {
                            showToast(this.getString(R.string.error1CodigoMx));
                            cutOffPage = i;
                            break;
                        } else {
                            String codigoTmp = valor;
                        /*if (tp.getTitle().equals(labels.getCodigoRojo())) {
                            String[] partes = valor.split("\\."); //A2.80001.21SER
                            codigoTmp = partes[1];
                        }//la bhc solo lleva el codigo del participante
*/
                            if (!codigoTmp.equalsIgnoreCase(participante.getCodigo().toString())) {
                                showToast(this.getString(R.string.error2CodigoMx, participante.getCodigo().toString()));
                                cutOffPage = i;
                                break;
                            }
                        }
                    }
                }
            }

            if (mPagerAdapter.getCutOffPage() != cutOffPage) {
                mPagerAdapter.setCutOffPage(cutOffPage);
                return true;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return false;
    }

    public void updateConstrains(){

    }

    public void updateModel(Page page){

        if(page.getTitle().equals(labels.getNumeroPartAtendidos())){
            int cantPartAtend = 0;
            if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && !page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals(""))
                cantPartAtend = Integer.parseInt(page.getData().getString(TextPage.SIMPLE_DATA_KEY));
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend1()), cantPartAtend >= 1);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend1Diagnostico()), cantPartAtend >= 1);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend2()), cantPartAtend >= 2);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend2Diagnostico()), cantPartAtend >= 2);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend3()), cantPartAtend >= 3);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend3Diagnostico()), cantPartAtend >= 3);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend4()), cantPartAtend >= 4);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend4Diagnostico()), cantPartAtend >= 5);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend5()), cantPartAtend >= 5);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend5Diagnostico()), cantPartAtend >= 5);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend6()), cantPartAtend >= 6);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend6Diagnostico()), cantPartAtend >= 6);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend7()), cantPartAtend >= 7);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend7Diagnostico()), cantPartAtend >= 7);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend8()), cantPartAtend >= 8);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend8Diagnostico()), cantPartAtend >= 8);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend9()), cantPartAtend >= 9);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend9Diagnostico()), cantPartAtend >= 9);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend10()), cantPartAtend >= 10);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend10Diagnostico()), cantPartAtend >= 10);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend11()), cantPartAtend >= 11);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend11Diagnostico()), cantPartAtend >= 11);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend12()), cantPartAtend >= 12);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend12Diagnostico()), cantPartAtend >= 12);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend13()), cantPartAtend >= 13);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend13Diagnostico()), cantPartAtend >= 13);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend14()), cantPartAtend >= 14);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend14Diagnostico()), cantPartAtend >= 14);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend15()), cantPartAtend >= 15);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend15Diagnostico()), cantPartAtend >= 15);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend16()), cantPartAtend >= 16);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend16Diagnostico()), cantPartAtend >= 16);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend17()), cantPartAtend >= 17);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend17Diagnostico()), cantPartAtend >= 17);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend18()), cantPartAtend >= 18);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend18Diagnostico()), cantPartAtend >= 18);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend19()), cantPartAtend >= 19);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend19Diagnostico()), cantPartAtend >= 19);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend20()), cantPartAtend == 20);
            changeStatus(mWizardModel.findByKey(labels.getCodPartAtend20Diagnostico()), cantPartAtend == 20);
            onPageTreeChanged();
        }
        if(page.getTitle().equals(labels.getNumeroPartAtendidos())) {
            if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && !page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals(""))
              atendidoscohort = Integer.parseInt(page.getData().getString(TextPage.SIMPLE_DATA_KEY));
        }
        if(page.getTitle().equals(labels.getNumPartNoCohorteAtendidos())) {
            if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && !page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals("")){
                atendidosNOcohort = Integer.parseInt(page.getData().getString(TextPage.SIMPLE_DATA_KEY));
            NumberPage totalatendidos = (NumberPage) mWizardModel.findByKey(labels.getTotalPartAtendidos());
            String total = String.valueOf(atendidoscohort + atendidosNOcohort);
            totalatendidos.setValue(total);

              }
            onPageTreeChanged();
        }
        if(page.getTitle().equals(labels.getNumPartTrasladados())){
            int cantPartTraslado = 0;
            if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && !page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals(""))
                cantPartTraslado = Integer.parseInt(page.getData().getString(TextPage.SIMPLE_DATA_KEY));
            changeStatus(mWizardModel.findByKey(labels.getCodPartTraslado1()), cantPartTraslado >= 1);
            changeStatus(mWizardModel.findByKey(labels.getCodPartTraslado1Diagnostico()), cantPartTraslado >= 1);
            changeStatus(mWizardModel.findByKey(labels.getCodPartTraslado2()), cantPartTraslado >= 2);
            changeStatus(mWizardModel.findByKey(labels.getCodPartTraslado2Diagnostico()), cantPartTraslado >= 2);
            changeStatus(mWizardModel.findByKey(labels.getCodPartTraslado3()), cantPartTraslado >= 3);
            changeStatus(mWizardModel.findByKey(labels.getCodPartTraslado3Diagnostico()), cantPartTraslado >= 3);
            changeStatus(mWizardModel.findByKey(labels.getCodPartTraslado4()), cantPartTraslado >= 4);
            changeStatus(mWizardModel.findByKey(labels.getCodPartTraslado4Diagnostico()), cantPartTraslado >= 4);
            changeStatus(mWizardModel.findByKey(labels.getCodPartTraslado5()), cantPartTraslado >= 5);
            changeStatus(mWizardModel.findByKey(labels.getCodPartTraslado5Diagnostico()), cantPartTraslado >= 5);
            changeStatus(mWizardModel.findByKey(labels.getCodPartTraslado6()), cantPartTraslado >= 6);
            changeStatus(mWizardModel.findByKey(labels.getCodPartTraslado6Diagnostico()), cantPartTraslado >= 6);
            changeStatus(mWizardModel.findByKey(labels.getCodPartTraslado7()), cantPartTraslado >= 7);
            changeStatus(mWizardModel.findByKey(labels.getCodPartTraslado7Diagnostico()), cantPartTraslado >= 7);
            changeStatus(mWizardModel.findByKey(labels.getCodPartTraslado8()), cantPartTraslado >= 8);
            changeStatus(mWizardModel.findByKey(labels.getCodPartTraslado8Diagnostico()), cantPartTraslado >= 8);
            changeStatus(mWizardModel.findByKey(labels.getCodPartTraslado9()), cantPartTraslado >= 9);
            changeStatus(mWizardModel.findByKey(labels.getCodPartTraslado9Diagnostico()), cantPartTraslado >= 9);
            changeStatus(mWizardModel.findByKey(labels.getCodPartTraslado10()), cantPartTraslado == 10);
            changeStatus(mWizardModel.findByKey(labels.getCodPartTraslado10Diagnostico()), cantPartTraslado == 10);

            onPageTreeChanged();
        }
        if(page.getTitle().equals(labels.getNumPartNegatTraslado())){
            int cantNegatPartTraslado = 0;
            if (page.getData().getString(TextPage.SIMPLE_DATA_KEY) !=null && !page.getData().getString(TextPage.SIMPLE_DATA_KEY).equals(""))
                cantNegatPartTraslado = Integer.parseInt(page.getData().getString(TextPage.SIMPLE_DATA_KEY));
            changeStatus(mWizardModel.findByKey(labels.getCodNegatPartTraslado1()), cantNegatPartTraslado >= 1);
            changeStatus(mWizardModel.findByKey(labels.getCodNegaPartTraslado1Diagnostico()), cantNegatPartTraslado >= 1);
            changeStatus(mWizardModel.findByKey(labels.getCodNegatPartTraslado2()), cantNegatPartTraslado >= 2);
            changeStatus(mWizardModel.findByKey(labels.getCodNegaPartTraslado2Diagnostico()), cantNegatPartTraslado >= 2);
            changeStatus(mWizardModel.findByKey(labels.getCodNegatPartTraslado3()), cantNegatPartTraslado >= 3);
            changeStatus(mWizardModel.findByKey(labels.getCodNegaPartTraslado3Diagnostico()), cantNegatPartTraslado >= 3);
            changeStatus(mWizardModel.findByKey(labels.getCodNegatPartTraslado4()), cantNegatPartTraslado >= 4);
            changeStatus(mWizardModel.findByKey(labels.getCodNegaPartTraslado4Diagnostico()), cantNegatPartTraslado >= 4);
            changeStatus(mWizardModel.findByKey(labels.getCodNegatPartTraslado5()), cantNegatPartTraslado >= 5);
            changeStatus(mWizardModel.findByKey(labels.getCodNegaPartTraslado5Diagnostico()), cantNegatPartTraslado >= 5);
            changeStatus(mWizardModel.findByKey(labels.getCodNegatPartTraslado6()), cantNegatPartTraslado >= 6);
            changeStatus(mWizardModel.findByKey(labels.getCodNegaPartTraslado6Diagnostico()), cantNegatPartTraslado >= 6);
            changeStatus(mWizardModel.findByKey(labels.getCodNegatPartTraslado7()), cantNegatPartTraslado >= 7);
            changeStatus(mWizardModel.findByKey(labels.getCodNegaPartTraslado7Diagnostico()), cantNegatPartTraslado >= 7);
            changeStatus(mWizardModel.findByKey(labels.getCodNegatPartTraslado8()), cantNegatPartTraslado >= 8);
            changeStatus(mWizardModel.findByKey(labels.getCodNegaPartTraslado8Diagnostico()), cantNegatPartTraslado >= 8);
            changeStatus(mWizardModel.findByKey(labels.getCodNegatPartTraslado9()), cantNegatPartTraslado >= 9);
            changeStatus(mWizardModel.findByKey(labels.getCodNegaPartTraslado9Diagnostico()), cantNegatPartTraslado >= 9);
            changeStatus(mWizardModel.findByKey(labels.getCodNegatPartTraslado10()), cantNegatPartTraslado == 10);
            changeStatus(mWizardModel.findByKey(labels.getCodNegaPartTraslado10Diagnostico()), cantNegatPartTraslado == 10);

            onPageTreeChanged();
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
            Map<String, String> mapa = mWizardModel.getAnswers();
            Bundle datos = new Bundle();
            for (Map.Entry<String, String> entry : mapa.entrySet()) {
                datos.putString(entry.getKey(), entry.getValue());
            }

            String catSiNo = "CAT_SINO";
            String catSinMuestra = "CAT_RAZON_NO_MX";
            String catPinchazos = "CAT_PINCHAZOS";
            String catObservacion = "CAT_OBSERV_MX";
            String catSexo = "CAT_SEXO";

            String puestoSalud = datos.getString(this.getString(R.string.puestoSalud));
            String numeroPartAtendidos = datos.getString(this.getString(R.string.numPartAtendidos));
            String codPartAtend1 = datos.getString(this.getString(R.string.codPartAtend1));
            String codPartAtend2 = datos.getString(this.getString(R.string.codPartAtend2));
            String codPartAtend3 = datos.getString(this.getString(R.string.codPartAtend3));
            String codPartAtend4 = datos.getString(this.getString(R.string.codPartAtend4));
            String codPartAtend5 = datos.getString(this.getString(R.string.codPartAtend5));
            String codPartAtend6 = datos.getString(this.getString(R.string.codPartAtend6));
            String codPartAtend7 = datos.getString(this.getString(R.string.codPartAtend7));
            String codPartAtend8 = datos.getString(this.getString(R.string.codPartAtend8));
            String codPartAtend9 = datos.getString(this.getString(R.string.codPartAtend9));
            String codPartAtend10 = datos.getString(this.getString(R.string.codPartAtend10));
            String codPartAtend11 = datos.getString(this.getString(R.string.codPartAtend11));
            String codPartAtend12 = datos.getString(this.getString(R.string.codPartAtend12));
            String codPartAtend13 = datos.getString(this.getString(R.string.codPartAtend13));
            String codPartAtend14 = datos.getString(this.getString(R.string.codPartAtend14));
            String codPartAtend15 = datos.getString(this.getString(R.string.codPartAtend15));
            String codPartAtend16 = datos.getString(this.getString(R.string.codPartAtend16));
            String codPartAtend17 = datos.getString(this.getString(R.string.codPartAtend17));
            String codPartAtend18 = datos.getString(this.getString(R.string.codPartAtend18));
            String codPartAtend19 = datos.getString(this.getString(R.string.codPartAtend19));
            String codPartAtend20 = datos.getString(this.getString(R.string.codPartAtend20));

            String codPartAtend1Diagnostico = datos.getString(this.getString(R.string.codPartAtend1Diagnostico));
            String codPartAtend2Diagnostico = datos.getString(this.getString(R.string.codPartAtend2Diagnostico));
            String codPartAtend3Diagnostico = datos.getString(this.getString(R.string.codPartAtend3Diagnostico));
            String codPartAtend4Diagnostico = datos.getString(this.getString(R.string.codPartAtend4Diagnostico));
            String codPartAtend5Diagnostico = datos.getString(this.getString(R.string.codPartAtend5Diagnostico));
            String codPartAtend6Diagnostico = datos.getString(this.getString(R.string.codPartAtend6Diagnostico));
            String codPartAtend7Diagnostico = datos.getString(this.getString(R.string.codPartAtend7Diagnostico));
            String codPartAtend8Diagnostico = datos.getString(this.getString(R.string.codPartAtend8Diagnostico));
            String codPartAtend9Diagnostico = datos.getString(this.getString(R.string.codPartAtend9Diagnostico));
            String codPartAtend10Diagnostico = datos.getString(this.getString(R.string.codPartAtend10Diagnostico));
            String codPartAtend11Diagnostico = datos.getString(this.getString(R.string.codPartAtend11Diagnostico));
            String codPartAtend12Diagnostico = datos.getString(this.getString(R.string.codPartAtend12Diagnostico));
            String codPartAtend13Diagnostico = datos.getString(this.getString(R.string.codPartAtend13Diagnostico));
            String codPartAtend14Diagnostico = datos.getString(this.getString(R.string.codPartAtend14Diagnostico));
            String codPartAtend15Diagnostico = datos.getString(this.getString(R.string.codPartAtend15Diagnostico));
            String codPartAtend16Diagnostico = datos.getString(this.getString(R.string.codPartAtend16Diagnostico));
            String codPartAtend17Diagnostico = datos.getString(this.getString(R.string.codPartAtend17Diagnostico));
            String codPartAtend18Diagnostico = datos.getString(this.getString(R.string.codPartAtend18Diagnostico));
            String codPartAtend19Diagnostico = datos.getString(this.getString(R.string.codPartAtend19Diagnostico));
            String codPartAtend20Diagnostico = datos.getString(this.getString(R.string.codPartAtend20Diagnostico));

            String numPartNoCohorteAtendidos = datos.getString(this.getString(R.string.numPartNoCohorteAtendidos));
            String totalPartAtendidos = datos.getString(this.getString(R.string.totalPartAtendidos));
            String numPacienFebrilAtendidos = datos.getString(this.getString(R.string.numPacienFebrilAtendidos));
            String numInfeccionRespAguda = datos.getString(this.getString(R.string.numInfeccionRespAguda));

            String numEnfermedadDiarrAguda = datos.getString(this.getString(R.string.numEnfermedadDiarrAguda));
            String numEti = datos.getString(this.getString(R.string.numEti));
            String numRag = datos.getString(this.getString(R.string.numRag));
            String numConjuntivitis = datos.getString(this.getString(R.string.numConjuntivitis));
            String numControlParental = datos.getString(this.getString(R.string.numControlParental));
            String numNeumonia = datos.getString(this.getString(R.string.numNeumonia));
            String numPap = datos.getString(this.getString(R.string.numPap));

            String numPlanifFamiliar = datos.getString(this.getString(R.string.numPlanifFamiliar));
            String numGotaGruesa = datos.getString(this.getString(R.string.numGotaGruesa));
            String numPacientesCronicos = datos.getString(this.getString(R.string.numPacientesCronicos));
            String numTraslados = datos.getString(this.getString(R.string.numTraslados));
            String numCaptacionA = datos.getString(this.getString(R.string.numCaptacionA));
            String numCaptacionB = datos.getString(this.getString(R.string.numCaptacionB));
            String numCaptacionC = datos.getString(this.getString(R.string.numCaptacionC));

            String numCaptacionD = datos.getString(this.getString(R.string.numCaptacionD));
            String numSeguimientoA = datos.getString(this.getString(R.string.numSeguimientoA));
            String numSeguimientoB = datos.getString(this.getString(R.string.numSeguimientoB));
            String numSeguimientoD = datos.getString(this.getString(R.string.numSeguimientoD));
            String numConvaPendientes = datos.getString(this.getString(R.string.numConvaPendientes));
            String numVisitaTerreno = datos.getString(this.getString(R.string.numVisitaTerreno));
            String numPartTrasladados = datos.getString(this.getString(R.string.numPartTrasladados));
            String codPartTraslado1 = datos.getString(this.getString(R.string.codPartTraslado1));
            String codPartTraslado2 = datos.getString(this.getString(R.string.codPartTraslado2));
            String codPartTraslado3 = datos.getString(this.getString(R.string.codPartTraslado3));
            String codPartTraslado4 = datos.getString(this.getString(R.string.codPartTraslado4));
            String codPartTraslado5 = datos.getString(this.getString(R.string.codPartTraslado5));
            String codPartTraslado6 = datos.getString(this.getString(R.string.codPartTraslado6));
            String codPartTraslado7 = datos.getString(this.getString(R.string.codPartTraslado7));
            String codPartTraslado8 = datos.getString(this.getString(R.string.codPartTraslado8));
            String codPartTraslado9 = datos.getString(this.getString(R.string.codPartTraslado9));
            String codPartTraslado10 = datos.getString(this.getString(R.string.codPartTraslado10));

            String codPartTraslado1Diagnostico = datos.getString(this.getString(R.string.codPartTraslado1Diagnostico));
            String codPartTraslado2Diagnostico = datos.getString(this.getString(R.string.codPartTraslado2Diagnostico));
            String codPartTraslado3Diagnostico = datos.getString(this.getString(R.string.codPartTraslado3Diagnostico));
            String codPartTraslado4Diagnostico = datos.getString(this.getString(R.string.codPartTraslado4Diagnostico));
            String codPartTraslado5Diagnostico = datos.getString(this.getString(R.string.codPartTraslado5Diagnostico));
            String codPartTraslado6Diagnostico = datos.getString(this.getString(R.string.codPartTraslado6Diagnostico));
            String codPartTraslado7Diagnostico = datos.getString(this.getString(R.string.codPartTraslado7Diagnostico));
            String codPartTraslado8Diagnostico = datos.getString(this.getString(R.string.codPartTraslado8Diagnostico));
            String codPartTraslado9Diagnostico = datos.getString(this.getString(R.string.codPartTraslado9Diagnostico));
            String codPartTraslado10Diagnostico = datos.getString(this.getString(R.string.codPartTraslado10Diagnostico));

            String numPartNegatTraslado = datos.getString(this.getString(R.string.numPartNegatTraslado));
            String codNegatPartTraslado1 = datos.getString(this.getString(R.string.codNegatPartTraslado1));
            String codNegatPartTraslado2 = datos.getString(this.getString(R.string.codNegatPartTraslado2));
            String codNegatPartTraslado3 = datos.getString(this.getString(R.string.codNegatPartTraslado3));
            String codNegatPartTraslado4 = datos.getString(this.getString(R.string.codNegatPartTraslado4));
            String codNegatPartTraslado5 = datos.getString(this.getString(R.string.codNegatPartTraslado5));
            String codNegatPartTraslado6 = datos.getString(this.getString(R.string.codNegatPartTraslado6));
            String codNegatPartTraslado7 = datos.getString(this.getString(R.string.codNegatPartTraslado7));
            String codNegatPartTraslado8 = datos.getString(this.getString(R.string.codNegatPartTraslado8));
            String codNegatPartTraslado9 = datos.getString(this.getString(R.string.codNegatPartTraslado9));
            String codNegatPartTraslado10 = datos.getString(this.getString(R.string.codNegatPartTraslado10));

            String codNegaPartTraslado1Diagnostico = datos.getString(this.getString(R.string.codNegaPartTraslado1Diagnostico));
            String codNegaPartTraslado2Diagnostico = datos.getString(this.getString(R.string.codNegaPartTraslado2Diagnostico));
            String codNegaPartTraslado3Diagnostico = datos.getString(this.getString(R.string.codNegaPartTraslado3Diagnostico));
            String codNegaPartTraslado4Diagnostico = datos.getString(this.getString(R.string.codNegaPartTraslado4Diagnostico));
            String codNegaPartTraslado5Diagnostico = datos.getString(this.getString(R.string.codNegaPartTraslado5Diagnostico));
            String codNegaPartTraslado6Diagnostico = datos.getString(this.getString(R.string.codNegaPartTraslado6Diagnostico));
            String codNegaPartTraslado7Diagnostico = datos.getString(this.getString(R.string.codNegaPartTraslado7Diagnostico));
            String codNegaPartTraslado8Diagnostico = datos.getString(this.getString(R.string.codNegaPartTraslado8Diagnostico));
            String codNegaPartTraslado9Diagnostico = datos.getString(this.getString(R.string.codNegaPartTraslado9Diagnostico));
            String codNegaPartTraslado10Diagnostico = datos.getString(this.getString(R.string.codNegaPartTraslado10Diagnostico));




            String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
            estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(), mPass, false, false);
            estudiosAdapter.open();

            Integer numero = (int) (Math.random() * 100) + 1;
            InformeFindeDiaMedicos informeFDM = new InformeFindeDiaMedicos();
            informeFDM.setId(numero);
            informeFDM.setFechaConsulta(new Date());
            informeFDM.setPuestoSalud(puestoSalud.toUpperCase());
            informeFDM.setNumPartCohorte(Integer.parseInt(numeroPartAtendidos));
            if(codPartAtend1 == null){
                codPartAtend1 = "null";
            }
            informeFDM.setCodPartAtend1((codPartAtend1));
            informeFDM.setCodPartAtend2((codPartAtend2));
            informeFDM.setCodPartAtend3((codPartAtend3));
            informeFDM.setCodPartAtend4((codPartAtend4));
            informeFDM.setCodPartAtend5((codPartAtend5));
            informeFDM.setCodPartAtend6((codPartAtend6));
            informeFDM.setCodPartAtend7((codPartAtend7));
            informeFDM.setCodPartAtend8((codPartAtend8));
            informeFDM.setCodPartAtend9((codPartAtend9));
            informeFDM.setCodPartAtend10((codPartAtend10));
            informeFDM.setCodPartAtend11((codPartAtend11));
            informeFDM.setCodPartAtend12((codPartAtend12));
            informeFDM.setCodPartAtend13((codPartAtend13));
            informeFDM.setCodPartAtend14((codPartAtend14));
            informeFDM.setCodPartAtend15((codPartAtend15));
            informeFDM.setCodPartAtend16((codPartAtend16));
            informeFDM.setCodPartAtend17((codPartAtend17));
            informeFDM.setCodPartAtend18((codPartAtend18));
            informeFDM.setCodPartAtend19((codPartAtend19));
            informeFDM.setCodPartAtend20((codPartAtend20));

            informeFDM.setCodPartAtend1Diagnostico((codPartAtend1Diagnostico)) ;
            informeFDM.setCodPartAtend2Diagnostico((codPartAtend2Diagnostico)) ;
            informeFDM.setCodPartAtend3Diagnostico((codPartAtend3Diagnostico)) ;
            informeFDM.setCodPartAtend4Diagnostico((codPartAtend4Diagnostico)) ;
            informeFDM.setCodPartAtend5Diagnostico((codPartAtend5Diagnostico)) ;
            informeFDM.setCodPartAtend6Diagnostico((codPartAtend6Diagnostico)) ;
            informeFDM.setCodPartAtend7Diagnostico((codPartAtend7Diagnostico)) ;
            informeFDM.setCodPartAtend8Diagnostico((codPartAtend8Diagnostico)) ;
            informeFDM.setCodPartAtend9Diagnostico((codPartAtend9Diagnostico)) ;
            informeFDM.setCodPartAtend10Diagnostico((codPartAtend10Diagnostico)) ;
            informeFDM.setCodPartAtend11Diagnostico((codPartAtend11Diagnostico)) ;
            informeFDM.setCodPartAtend12Diagnostico((codPartAtend12Diagnostico)) ;
            informeFDM.setCodPartAtend13Diagnostico((codPartAtend13Diagnostico)) ;
            informeFDM.setCodPartAtend14Diagnostico((codPartAtend14Diagnostico)) ;
            informeFDM.setCodPartAtend15Diagnostico((codPartAtend15Diagnostico)) ;
            informeFDM.setCodPartAtend16Diagnostico((codPartAtend16Diagnostico)) ;
            informeFDM.setCodPartAtend17Diagnostico((codPartAtend17Diagnostico)) ;
            informeFDM.setCodPartAtend18Diagnostico((codPartAtend18Diagnostico)) ;
            informeFDM.setCodPartAtend19Diagnostico((codPartAtend19Diagnostico)) ;
            informeFDM.setCodPartAtend20Diagnostico((codPartAtend20Diagnostico)) ;


            informeFDM.setNumPartNoCohorte(Integer.parseInt(numPartNoCohorteAtendidos));
            informeFDM.setNumTotalAtendidos(Integer.parseInt(totalPartAtendidos));
            informeFDM.setNumFebrilA(Integer.parseInt(numPacienFebrilAtendidos));
            informeFDM.setNumInRespAgudaA(Integer.parseInt(numInfeccionRespAguda));
            informeFDM.setNumEnfDiarreaAgudaA(Integer.parseInt(numEnfermedadDiarrAguda));
            informeFDM.setNumETI(Integer.parseInt(numEti));
            informeFDM.setNumRAG(Integer.parseInt(numRag));
            informeFDM.setNumPap(Integer.parseInt(numPap));
            informeFDM.setNumConjuntivitis(Integer.parseInt(numConjuntivitis));
            informeFDM.setNumControlPrenatal(Integer.parseInt(numControlParental));
            informeFDM.setNumNeumonia(Integer.parseInt(numNeumonia));
            informeFDM.setNumNeumonia(Integer.parseInt(numNeumonia));
            informeFDM.setNumPlanificacionFam(Integer.parseInt(numPlanifFamiliar));
            informeFDM.setNumGotaGruesa(Integer.parseInt(numGotaGruesa));
            informeFDM.setNumCronicos(Integer.parseInt(numPacientesCronicos));
            informeFDM.setNumTraslados(Integer.parseInt(numTraslados));
            informeFDM.setNumCaptacionA(Integer.parseInt(numCaptacionA));
            informeFDM.setNumCaptacionB(Integer.parseInt(numCaptacionB));
            informeFDM.setNumCaptacionC(Integer.parseInt(numCaptacionC));
            informeFDM.setNumCaptacionD(Integer.parseInt(numCaptacionD));
            informeFDM.setNumSeguimientoA(Integer.parseInt(numSeguimientoA));
            informeFDM.setNumSeguimientoB(Integer.parseInt(numSeguimientoB));
            informeFDM.setNumSeguimientoD(Integer.parseInt(numSeguimientoD));
            informeFDM.setNumConvPendPuesto(Integer.parseInt(numConvaPendientes));
            informeFDM.setNumVisitaAterreno(Integer.parseInt(numVisitaTerreno));
            informeFDM.setNumTrasladosDengue(Integer.parseInt(numPartTrasladados));
            if(codPartTraslado1  == null){
                codPartTraslado1 = "null";
            }
            informeFDM.setCodPartTraslado1((codPartTraslado1));
            informeFDM.setCodPartTraslado2((codPartTraslado2));
            informeFDM.setCodPartTraslado3((codPartTraslado3));
            informeFDM.setCodPartTraslado4((codPartTraslado4));
            informeFDM.setCodPartTraslado5((codPartTraslado5));
            informeFDM.setCodPartTraslado6((codPartTraslado6));
            informeFDM.setCodPartTraslado7((codPartTraslado7));
            informeFDM.setCodPartTraslado8((codPartTraslado8));
            informeFDM.setCodPartTraslado9((codPartTraslado9));
            informeFDM.setCodPartTraslado10((codPartTraslado10));

            informeFDM.setCodPartTraslado1Diagnostico((codPartTraslado1Diagnostico));
            informeFDM.setCodPartTraslado2Diagnostico((codPartTraslado2Diagnostico));
            informeFDM.setCodPartTraslado3Diagnostico((codPartTraslado3Diagnostico));
            informeFDM.setCodPartTraslado4Diagnostico((codPartTraslado4Diagnostico));
            informeFDM.setCodPartTraslado5Diagnostico((codPartTraslado5Diagnostico));
            informeFDM.setCodPartTraslado6Diagnostico((codPartTraslado6Diagnostico));
            informeFDM.setCodPartTraslado7Diagnostico((codPartTraslado7Diagnostico));
            informeFDM.setCodPartTraslado8Diagnostico((codPartTraslado8Diagnostico));
            informeFDM.setCodPartTraslado9Diagnostico((codPartTraslado9Diagnostico));
            informeFDM.setCodPartTraslado10Diagnostico((codPartTraslado10Diagnostico));

            informeFDM.setNumNegatTrasladosDengue(Integer.parseInt(numPartNegatTraslado));
            if(codNegatPartTraslado1 == null){
                codNegatPartTraslado1 = "null";
            }
            informeFDM.setCodNegatPartTraslado1((codNegatPartTraslado1));
            informeFDM.setCodNegatPartTraslado2((codNegatPartTraslado2));
            informeFDM.setCodNegatPartTraslado3((codNegatPartTraslado3));
            informeFDM.setCodNegatPartTraslado4((codNegatPartTraslado4));
            informeFDM.setCodNegatPartTraslado5((codNegatPartTraslado5));
            informeFDM.setCodNegatPartTraslado6((codNegatPartTraslado6));
            informeFDM.setCodNegatPartTraslado7((codNegatPartTraslado7));
            informeFDM.setCodNegatPartTraslado8((codNegatPartTraslado8));
            informeFDM.setCodNegatPartTraslado9((codNegatPartTraslado9));
            informeFDM.setCodNegatPartTraslado10((codNegatPartTraslado10));

            informeFDM.setCodNegatPartTraslado1Diagnostico((codNegaPartTraslado1Diagnostico));
            informeFDM.setCodNegatPartTraslado2Diagnostico((codNegaPartTraslado2Diagnostico));
            informeFDM.setCodNegatPartTraslado3Diagnostico((codNegaPartTraslado3Diagnostico));
            informeFDM.setCodNegatPartTraslado4Diagnostico((codNegaPartTraslado4Diagnostico));
            informeFDM.setCodNegatPartTraslado5Diagnostico((codNegaPartTraslado5Diagnostico));
            informeFDM.setCodNegatPartTraslado6Diagnostico((codNegaPartTraslado6Diagnostico));
            informeFDM.setCodNegatPartTraslado7Diagnostico((codNegaPartTraslado7Diagnostico));
            informeFDM.setCodNegatPartTraslado8Diagnostico((codNegaPartTraslado8Diagnostico));
            informeFDM.setCodNegatPartTraslado9Diagnostico((codNegaPartTraslado9Diagnostico));
            informeFDM.setCodNegatPartTraslado10Diagnostico((codNegaPartTraslado10Diagnostico));

            informeFDM.setNomMedico(username);
            //Metadata
            informeFDM.setRecordDate(new Date());
            informeFDM.setRecordUser(username);
            informeFDM.setDeviceid(infoMovil.getDeviceId());
            informeFDM.setEstado('0');
            informeFDM.setPasive('0');

            estudiosAdapter.crearInformeFinDiaMedicos(informeFDM);


            showToast(getString(R.string.success));
            Bundle arguments = new Bundle();
           /* arguments.putSerializable(Constants.PARTICIPANTE, participante);
            Intent i = new Intent(getApplicationContext(),
                    ListaMuestrasParticipanteActivity.class);
            i.putExtras(arguments);
            i.putExtra(Constants.VISITA_EXITOSA, visitaExitosa);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);*/
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
                                    ((NuevoInformeFinDiaMedicoActivity) Objects.requireNonNull(getActivity())).saveData();
                                }
                            }
                    )
                    .setNegativeButton(R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((NuevoInformeFinDiaMedicoActivity) Objects.requireNonNull(getActivity())).createDialog(EXIT);;
                                }
                            }
                    )
                    .create();
        }
    }

}
