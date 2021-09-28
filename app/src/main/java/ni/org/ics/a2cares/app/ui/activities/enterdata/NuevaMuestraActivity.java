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

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ni.org.ics.a2cares.app.AbstractAsyncActivity;
import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.Muestra;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.domain.core.ParticipanteProcesos;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.domain.survey.EncuestaPesoTalla;
import ni.org.ics.a2cares.app.preferences.PreferencesActivity;
import ni.org.ics.a2cares.app.ui.activities.list.ListaMuestrasParticipanteActivity;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuParticipanteActivity;
import ni.org.ics.a2cares.app.ui.forms.EncuestaPesoTallaForm;
import ni.org.ics.a2cares.app.ui.forms.EncuestaPesoTallaFormLabels;
import ni.org.ics.a2cares.app.ui.forms.MuestraForm;
import ni.org.ics.a2cares.app.ui.forms.MuestraFormLabels;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.utils.DateUtil;
import ni.org.ics.a2cares.app.utils.DeviceInfo;
import ni.org.ics.a2cares.app.wizard.model.AbstractWizardModel;
import ni.org.ics.a2cares.app.wizard.model.BarcodePage;
import ni.org.ics.a2cares.app.wizard.model.DatePage;
import ni.org.ics.a2cares.app.wizard.model.LabelPage;
import ni.org.ics.a2cares.app.wizard.model.ModelCallbacks;
import ni.org.ics.a2cares.app.wizard.model.MultipleFixedChoicePage;
import ni.org.ics.a2cares.app.wizard.model.NumberPage;
import ni.org.ics.a2cares.app.wizard.model.Page;
import ni.org.ics.a2cares.app.wizard.model.SingleFixedChoicePage;
import ni.org.ics.a2cares.app.wizard.model.TextPage;
import ni.org.ics.a2cares.app.wizard.model.TimePage;
import ni.org.ics.a2cares.app.wizard.ui.PageFragmentCallbacks;
import ni.org.ics.a2cares.app.wizard.ui.ReviewFragment;
import ni.org.ics.a2cares.app.wizard.ui.StepPagerStrip;

/**
 * Created by Miguel Salinas on 8/7/2021.
 * V1.0
 */
public class NuevaMuestraActivity extends AbstractAsyncActivity implements
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

    private MuestraFormLabels labels;
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
        infoMovil = new DeviceInfo(NuevaMuestraActivity.this);
        participante = (Participante) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
        visitaExitosa = getIntent().getBooleanExtra(Constants.VISITA_EXITOSA, false);

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        mWizardModel = new MuestraForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        mWizardModel.registerListener(this);
        labels = new MuestraFormLabels();

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

        BarcodePage pagetmp = (BarcodePage) mWizardModel.findByKey(labels.getCodigoBHC());
        pagetmp.setPatternValidation(true, "^8\\d{4}$");

        pagetmp = (BarcodePage) mWizardModel.findByKey(labels.getCodigoRojo());
        pagetmp.setPatternValidation(true, "^A2.8\\d{4}.\\d{2}SER$");

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
            if (!page.getData().isEmpty() && clase.equals("class ni.org.ics.a2cares.app.wizard.model.BarcodePage")) {
                BarcodePage tp = (BarcodePage) page;
                if (tp.ismValPattern()) {
                    String valor = tp.getData().getString(BarcodePage.SIMPLE_DATA_KEY);
                    if(!valor.matches(tp.getmPattern())){
                        showToast(this.getString(R.string.error1CodigoMx));
                        cutOffPage = i;
                        break;
                    } else {
                        String codigoTmp = valor;
                        if (tp.getTitle().equals(labels.getCodigoRojo())) {
                            String[] partes = valor.split("\\."); //A2.80001.21SER
                            codigoTmp = partes[1];
                        }//la bhc solo lleva el codigo del participante

                        if (!codigoTmp.equalsIgnoreCase(participante.getCodigo().toString())){
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

        return false;
    }

    public void updateConstrains(){

    }

    public void updateModel(Page page){
        try {
            boolean visible = false;
            if (page.getTitle().equals(labels.getTuboRojo())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCodigoRojo()), visible, null);
                changeStatus(mWizardModel.findByKey(labels.getVolumenRojo()), visible, null);
                changeStatus(mWizardModel.findByKey(labels.getRazonNoRojo()), !visible, null);
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoRojo()), false, null);

                Page tmp = mWizardModel.findByKey(labels.getTuboBHC());
                boolean tomoBHC = tmp.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && tmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);

                changeStatus(mWizardModel.findByKey(labels.getPinchazos()), visible || tomoBHC, null);

                onPageTreeChanged();
            }
            if (page.getTitle().equals(labels.getTuboBHC())) {
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && page.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);
                changeStatus(mWizardModel.findByKey(labels.getCodigoBHC()), visible, null);
                changeStatus(mWizardModel.findByKey(labels.getVolumenBHC()), visible, null);
                changeStatus(mWizardModel.findByKey(labels.getRazonNoBHC()), !visible, null);
                changeStatus(mWizardModel.findByKey(labels.getOtraRazonNoBHC()), false, null);

                Page tmp = mWizardModel.findByKey(labels.getTuboRojo());
                boolean tomoRojo = tmp.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && tmp.getData().getString(TextPage.SIMPLE_DATA_KEY).matches(Constants.YES);

                changeStatus(mWizardModel.findByKey(labels.getPinchazos()), visible || tomoRojo, null);

                onPageTreeChanged();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
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
        else if (clase.equals("class ni.org.ics.a2cares.app.wizard.model.TimePage")){
            TimePage modifPage = (TimePage) page; modifPage.setValue("").setmVisible(visible);
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

            String fechaMuestra = datos.getString(this.getString(R.string.fechaMuestra));
            String tuboRojo = datos.getString(this.getString(R.string.tuboRojo));
            String codigoRojo = datos.getString(this.getString(R.string.codigoRojo));
            String volumenRojo = datos.getString(this.getString(R.string.volumenRojo));
            String razonNoRojo = datos.getString(this.getString(R.string.razonNoRojo));
            String otraRazonNoRojo = datos.getString(this.getString(R.string.otraRazonNoRojo));
            String tuboBHC = datos.getString(this.getString(R.string.tuboBHC));
            String codigoBHC = datos.getString(this.getString(R.string.codigoBHC));
            String volumenBHC = datos.getString(this.getString(R.string.volumenBHC));
            String razonNoBHC = datos.getString(this.getString(R.string.razonNoBHC));
            String otraRazonNoBHC = datos.getString(this.getString(R.string.otraRazonNoBHC));
            String pinchazos = datos.getString(this.getString(R.string.pinchazos));


            String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
            estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(), mPass, false, false);
            estudiosAdapter.open();
            Muestra muestra = new Muestra();
            muestra.setIdMuestra(infoMovil.getId());
            muestra.setParticipante(participante);
            muestra.setTuboRojo(getKeyFromCatalog(tuboRojo, catSiNo));
            muestra.setTuboBHC(getKeyFromCatalog(tuboBHC, catSiNo));
            muestra.setRazonNoBHC(getKeyFromCatalog(razonNoBHC, catSinMuestra));
            muestra.setRazonNoRojo(getKeyFromCatalog(razonNoRojo, catSinMuestra));
            muestra.setCodigoBHC(codigoBHC);
            muestra.setCodigoRojo(codigoRojo);
            muestra.setOtraRazonNoBHC(otraRazonNoBHC);
            muestra.setOtraRazonNoRojo(otraRazonNoRojo);
            if (tieneValor(volumenBHC)) muestra.setVolumenBHC(Double.valueOf(volumenBHC));
            if (tieneValor(volumenRojo)) muestra.setVolumenRojo(Double.valueOf(volumenRojo));
            muestra.setPinchazos(getKeyFromCatalog(pinchazos, catPinchazos));
            muestra.setProposito("MA");
            muestra.setEstudiosAct(Constants.ESTUDIO);
            muestra.setTerreno("1");
            muestra.setFechaMuestra(DateUtil.StringToDate(fechaMuestra, "dd/MM/yyyy"));

            //Metadata
            muestra.setRecordDate(new Date());
            muestra.setRecordUser(username);
            muestra.setDeviceid(infoMovil.getDeviceId());
            muestra.setEstado('0');
            muestra.setPasive('0');
            estudiosAdapter.crearMuestras(muestra);

            participante.getProcesos().setPendienteMxMA(Constants.NOKEYSND);
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
                    ListaMuestrasParticipanteActivity.class);
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
                                    ((NuevaMuestraActivity) Objects.requireNonNull(getActivity())).saveData();
                                }
                            }
                    )
                    .setNegativeButton(R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((NuevaMuestraActivity) Objects.requireNonNull(getActivity())).createDialog(EXIT);;
                                }
                            }
                    )
                    .create();
        }
    }

}
