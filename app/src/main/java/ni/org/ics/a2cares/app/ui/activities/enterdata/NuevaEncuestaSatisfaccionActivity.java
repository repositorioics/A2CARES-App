package ni.org.ics.a2cares.app.ui.activities.enterdata;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import ni.org.ics.a2cares.app.AbstractAsyncActivity;
import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.domain.core.ParticipanteProcesos;
import ni.org.ics.a2cares.app.domain.survey.EncuestaSatisfaccionUsuario;
import ni.org.ics.a2cares.app.entomologia.domain.MovilInfo;
import ni.org.ics.a2cares.app.preferences.PreferencesActivity;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuParticipanteActivity;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.utils.DeviceInfo;

public class NuevaEncuestaSatisfaccionActivity extends AbstractAsyncActivity {
    private Context CONTEXT;
    private static EncuestaSatisfaccionUsuario encuestaSatisfaccionUsuario = new EncuestaSatisfaccionUsuario();
    private DeviceInfo infoMovil;
    private Participante participante;
    private boolean visExitosa = false;
    private String username;
    private SharedPreferences settings;
    private EstudioDBAdapter estudiosAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_encuesta_satisfaccioneverts);

        participante = (Participante) getIntent().getExtras().getSerializable(Constants.PARTICIPANTE);
        visExitosa = getIntent().getBooleanExtra(Constants.VISITA_EXITOSA,false);
        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);
        infoMovil = new DeviceInfo(NuevaEncuestaSatisfaccionActivity.this);

        String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
        estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(),mPass,false,false);

        /*TextView txtvPregunta1 = (TextView)findViewById(R.id.txtvPregunta1);
        TextView txtvPregunta2 = (TextView)findViewById(R.id.txtvPregunta2);
        TextView txtvComodoHaciendoPreguntas = (TextView)findViewById(R.id.txtvComodoHaciendoPreguntas);

        txtvPregunta1.setText(stringBuilder("¿Qué le motivó a Ud. ingresar a su hijo/a en el proyecto ? ", "Puede seleccionar más de una respuesta, e indique su nivel de conformidad a cada opción."), TextView.BufferType.SPANNABLE);
        txtvPregunta2.setText(stringBuilder("¿Por qué ha decidido mantener a su hijo/a durante todo este tiempo en el proyecto? ", "Puede seleccionar más de una respuesta, e indique su acuerdo a cada frase en la escala."), TextView.BufferType.SPANNABLE);
        txtvComodoHaciendoPreguntas.setText(stringBuilder("¿Se siente cómodo/a haciendo preguntas acerca de los procedimientos del proyecto? ", "Puede seleccionar más de una respuesta."), TextView.BufferType.SPANNABLE);
*/

       // TextView txtvComodoHaciendoPreguntas = (TextView)findViewById(R.id.txtvComodoHaciendoPreguntas);
        this.CONTEXT = this;
        inicializarContorles();
    }

    public SpannableStringBuilder stringBuilder(String valor1, String valor2) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableString str1= new SpannableString(valor1);
        str1.setSpan(new ForegroundColorSpan(Color.BLACK), 0, str1.length(), 0);
        builder.append(str1);

        SpannableString str2= new SpannableString(valor2);
        str2.setSpan(new ForegroundColorSpan(Color.RED), 0, str2.length(), 0);
        builder.append(str2);

        return builder;
    }

    public void inicializarContorles() {

        /**
         * XML 6 parte 1
         * */
        //chkOtras_P1
        //chkOtrasDificultades
        View.OnClickListener onClikOtrasDificultades = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkOtrasDificultades(view);
            }
        };
        this.findViewById(R.id.chkOtrasDificultades2).setOnClickListener(onClikOtrasDificultades);

        //onChkOtrasRazonesNoRecomendaria
        View.OnClickListener onChkOtrasRazonesNoRecomendaria = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkOtrasRazonesNoRecomendaria(view);
            }
        };
        this.findViewById(R.id.chkOtrasRazonesNoRecomendaria).setOnClickListener(onChkOtrasRazonesNoRecomendaria);
        //onChkOtrasRazonesRecomendaria
        View.OnClickListener onChkOtrasRazonesRecomendaria = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkOtrasRazonesRecomendaria(view);
            }
        };
        this.findViewById(R.id.chkOtrasRazonesRecomendaria).setOnClickListener(onChkOtrasRazonesRecomendaria);

        //
        View.OnClickListener onChkOtroMotivosNoComprendioProcedimiento = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkOtroMotivosNoComprendioProcedimiento(view);
            }
        };
        this.findViewById(R.id.chkOtroMotivosNoComprendioProcedimiento).setOnClickListener(onChkOtroMotivosNoComprendioProcedimiento);

      /*  //opcionAtencionPersonalEstudio
        View.OnClickListener onRadioImportanciaInforEstudiosClicked = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioImportanciaInforEstudiosClicked(view);
            }
        };
        this.findViewById(R.id.radioGroupP01).setOnClickListener(onRadioImportanciaInforEstudiosClicked);
     /*   //chkEntiendoProcedimientos
       View.OnClickListener onEntiendoProcedimientos = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioImportanciaInforEstudiosClicked(view);
            }
        };
        this.findViewById(R.id.radioImportanciaInforEstudios02).setOnClickListener(onEntiendoProcedimientos);
        //radioImportanciaInforEstudios02
        View.OnClickListener radioImportanciaInforEstudios03 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioImportanciaInforEstudiosClicked(view);
            }
        };
        this.findViewById(R.id.radioImportanciaInforEstudios03).setOnClickListener(radioImportanciaInforEstudios03);

        //chkSatisfecho
    /*    View.OnClickListener onSatisfecho = new View.OnClickListener()s {
            @Override
            public void onClick(View view) {
                onChkSatisfecho(view);
            }
        };
        this.findViewById(R.id.chkSatisfecho).setOnClickListener(onSatisfecho);

        //chkComodoInformacion
        View.OnClickListener onComodoInformacion = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkComodoInformacion(view);
            }
        };
        this.findViewById(R.id.chkComodoInformacion).setOnClickListener(onComodoInformacion);

        //chkNoComodoPreguntas
        View.OnClickListener onNoComodoPreguntas = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkNoComodoPreguntas(view);
            }
        };
        this.findViewById(R.id.chkNoComodoPreguntas).setOnClickListener(onNoComodoPreguntas);
        /**
         * Primera Parte
         * */

        //chkAtenMedica
    /*    View.OnClickListener onClikAtenMedica = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkAtenMedica(view);
            }
        };
        this.findViewById(R.id.chkAtenMedica).setOnClickListener(onClikAtenMedica);

        //chkFamAmistades
        View.OnClickListener onClikFamAmistades = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkFamAmistades(view);
            }
        };
        this.findViewById(R.id.chkFamAmistades).setOnClickListener(onClikFamAmistades);

        //chkDecisionPropia
        View.OnClickListener onClikDecisionPropia = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkDecisionPropia(view);
            }
        };
        this.findViewById(R.id.chkDecisionPropia).setOnClickListener(onClikDecisionPropia);

        //chkObsequios
        View.OnClickListener onClikObsequios = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkObsequios(view);
            }
        };
        this.findViewById(R.id.chkObsequios).setOnClickListener(onClikObsequios);

        //chkAyudaComunidad
        View.OnClickListener onClikAyudaComunidad = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkAyudaComunidad(view);
            }
        };
        this.findViewById(R.id.chkAyudaComunidad).setOnClickListener(onClikAyudaComunidad);

        //chkExamLaboratorio
        View.OnClickListener onClikExamLaboratorio = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkExamLaboratorio(view);
            }
        };
        this.findViewById(R.id.chkExamLaboratorio).setOnClickListener(onClikExamLaboratorio);

        //chkInteresCientifico
        View.OnClickListener onClikInteresCientifico = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkInteresCientifico(view);
            }
        };
        this.findViewById(R.id.chkInteresCientifico).setOnClickListener(onClikInteresCientifico);

        //chkAyudarOtros
        View.OnClickListener onClikAyudarOtros = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkAyudarOtros(view);
            }
        };
        this.findViewById(R.id.chkAyudarOtros).setOnClickListener(onClikAyudarOtros);

        //chkOtras_P1
        View.OnClickListener onClikOtras_P1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkOtras_P1(view);
            }
        };
        this.findViewById(R.id.chkOtras_P1).setOnClickListener(onClikOtras_P1);

        /**
         * Segunda Parte
         * */
        //chkCalidadAtencionMed
       /* View.OnClickListener onClikCalidadAtencionMed = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkCalidadAtencionMed(view);
            }
        };
        this.findViewById(R.id.chkCalidadAtencionMed).setOnClickListener(onClikCalidadAtencionMed);

        //chkAtencionOportuna
        View.OnClickListener onClikAtencionOportuna = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkAtencionOportuna(view);
            }
        };
        this.findViewById(R.id.chkAtencionOportuna).setOnClickListener(onClikAtencionOportuna);

        //chkCoordinacionHosp
        View.OnClickListener onClikCoordinacionHosp = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkCoordinacionHosp(view);
            }
        };
        this.findViewById(R.id.chkCoordinacionHosp).setOnClickListener(onClikCoordinacionHosp);

        //chkInformacionEstadoSalud
        View.OnClickListener onClikInformacionEstadoSalud = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkInformacionEstadoSalud(view);
            }
        };
        this.findViewById(R.id.chkInformacionEstadoSalud).setOnClickListener(onClikInformacionEstadoSalud);

        //chkEnseniaPrevEnfer
        View.OnClickListener onClikEnseniaPrevEnfer = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkEnseniaPrevEnfer(view);
            }
        };
        this.findViewById(R.id.chkEnseniaPrevEnfer).setOnClickListener(onClikEnseniaPrevEnfer);

        //chkMejoraConoEnfer
        View.OnClickListener onClikMejoraConoEnfer = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkMejoraConoEnfer(view);
            }
        };
        this.findViewById(R.id.chkMejoraConoEnfer).setOnClickListener(onClikMejoraConoEnfer);

        //chkInteresCientificoP2
        View.OnClickListener onClikInteresCientificoP2 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkInteresCientificoP2(view);
            }
        };
        this.findViewById(R.id.chkInteresCientificoP2).setOnClickListener(onClikInteresCientificoP2);

        //chkMejorarTrataDengue
        View.OnClickListener onClikMejorarTrataDengue = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkMejorarTrataDengue(view);
            }
        };
        this.findViewById(R.id.chkMejorarTrataDengue).setOnClickListener(onClikMejorarTrataDengue);

        //chkNoQuieroQuedarmeEstudio
     /*   View.OnClickListener onClikNoQuieroQuedarmeEstudio = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkNoQuieroQuedarmeEstudio(view);
            }
        };
        this.findViewById(R.id.chkNoQuieroQuedarmeEstudio).setOnClickListener(onClikNoQuieroQuedarmeEstudio);

        //chkP2_Otras
        View.OnClickListener onClikchkP2_Otras = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkP2_Otras(view);
            }
        };
        this.findViewById(R.id.chkP2_Otras).setOnClickListener(onClikchkP2_Otras);

        /**
         * Parte 3
         * */
        //chkLejosCentroSalud
      /*  View.OnClickListener onClikLejosCentroSalud = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkLejosCentroSalud(view);
            }
        };
        this.findViewById(R.id.chkLejosCentroSalud).setOnClickListener(onClikLejosCentroSalud);

        //chkCostoElevadoTransporte
        View.OnClickListener onClikCostoElevadoTransporte = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkCostoElevadoTransporte(view);
            }
        };
        this.findViewById(R.id.chkCostoElevadoTransporte).setOnClickListener(onClikCostoElevadoTransporte);

        //chkTrabajoTiempo
        View.OnClickListener onClikTrabajoTiempo = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkTrabajoTiempo(view);
            }
        };
        this.findViewById(R.id.chkTrabajoTiempo).setOnClickListener(onClikTrabajoTiempo);

        //chkNoQueriaPasarConsultMomento
        View.OnClickListener onClikNoQueriaPasarConsultMomento = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkNoQueriaPasarConsultMomento(view);
            }
        };
        this.findViewById(R.id.chkNoQueriaPasarConsultMomento).setOnClickListener(onClikNoQueriaPasarConsultMomento);

        //chkPorHorarioClases
        View.OnClickListener onClikPorHorarioClases = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkPorHorarioClases(view);
            }
        };
        this.findViewById(R.id.chkPorHorarioClases).setOnClickListener(onClikPorHorarioClases);

        //chkTemorTomenMx
        View.OnClickListener onClikTemorTomenMx = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkTemorTomenMx(view);
            }
        };
        this.findViewById(R.id.chkTemorTomenMx).setOnClickListener(onClikTemorTomenMx);

        //chkTemorDarInformacion
        View.OnClickListener onClikTemorDarInformacion = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkTemorDarInformacion(view);
            }
        };
        this.findViewById(R.id.chkTemorDarInformacion).setOnClickListener(onClikTemorDarInformacion);

        //chkOtrasDificultades
        View.OnClickListener onClikOtrasDificultades = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkOtrasDificultades(view);
            }
        };
        this.findViewById(R.id.chkOtrasDificultades).setOnClickListener(onClikOtrasDificultades);

        /*
         * Parte 4, Pregunta 4.1
         * */

        //chkAtencionRecibidaCalidad
       /* View.OnClickListener onAtencionRecibidaCalidad = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkAtencionRecibidaCalidad(view);
            }
        };
        this.findViewById(R.id.chkAtencionRecibidaCalidad).setOnClickListener(onAtencionRecibidaCalidad);

        //chkRespuestaNecesidades
        View.OnClickListener onRespuestaNecesidades = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkRespuestaNecesidades(view);
            }
        };
        this.findViewById(R.id.chkRespuestaNecesidades).setOnClickListener(onRespuestaNecesidades);

        //chkTiempoEsperaCorto
        View.OnClickListener onTiempoEsperaCorto = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkTiempoEsperaCorto(view);
            }
        };
        this.findViewById(R.id.chkTiempoEsperaCorto).setOnClickListener(onTiempoEsperaCorto);

        //chkMejorAtencionSeguroMed
        View.OnClickListener onMejorAtencionSeguroMed = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkMejorAtencionSeguroMed(view);
            }
        };
        this.findViewById(R.id.chkMejorAtencionSeguroMed).setOnClickListener(onMejorAtencionSeguroMed);

        //chkExamLabNecesarios
        View.OnClickListener onExamLabNecesarios = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkExamLabNecesarios(view);
            }
        };
        this.findViewById(R.id.chkExamLabNecesarios).setOnClickListener(onExamLabNecesarios);

        //chkGeneraConocimiento
        View.OnClickListener onGeneraConocimiento = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkGeneraConocimiento(view);
            }
        };
        this.findViewById(R.id.chkGeneraConocimiento).setOnClickListener(onGeneraConocimiento);

        //chkPocosRequisitos
        View.OnClickListener onPocosRequisitos = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkPocosRequisitos(view);
            }
        };
        this.findViewById(R.id.chkPocosRequisitos).setOnClickListener(onPocosRequisitos);

        //chkOtrasRazonesRecomendaria
        View.OnClickListener onOtrasRazonesRecomendaria = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkOtrasRazonesRecomendaria(view);
            }
        };
        this.findViewById(R.id.chkOtrasRazonesRecomendaria).setOnClickListener(onOtrasRazonesRecomendaria);

        /*
         * Parte 4, Pregunta 4.2
         * */

        //chkAtencionRecibidaMala
   /*     View.OnClickListener onAtencionRecibidaMala = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkAtencionRecibidaMala(view);
            }
        };
        this.findViewById(R.id.chkAtencionRecibidaMala).setOnClickListener(onAtencionRecibidaMala);

        //chkNoDanRespuestaNecesidades
        View.OnClickListener onNoDanRespuestaNecesidades = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkNoDanRespuestaNecesidades(view);
            }
        };
        this.findViewById(R.id.chkNoDanRespuestaNecesidades).setOnClickListener(onNoDanRespuestaNecesidades);

        //chkTiempoEsperaLargo
        View.OnClickListener onTiempoEsperaLargo = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkTiempoEsperaLargo(view);
            }
        };
        this.findViewById(R.id.chkTiempoEsperaLargo).setOnClickListener(onTiempoEsperaLargo);

        //chkMejorAtencionOtroCentro
        View.OnClickListener onMejorAtencionOtroCentro = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkMejorAtencionOtroCentro(view);
            }
        };
        this.findViewById(R.id.chkMejorAtencionOtroCentro).setOnClickListener(onMejorAtencionOtroCentro);

        //chkDemasiadasMx
        View.OnClickListener onDemasiadasMx = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkDemasiadasMx(view);
            }
        };
        this.findViewById(R.id.chkDemasiadasMx).setOnClickListener(onDemasiadasMx);

        //chkMuchosRequisitos
        View.OnClickListener onMuchosRequisitos = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkMuchosRequisitos(view);
            }
        };
        this.findViewById(R.id.chkMuchosRequisitos).setOnClickListener(onMuchosRequisitos);

        //chkNoExplicanHacenMx
        View.OnClickListener onNoExplicanHacenMx = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkNoExplicanHacenMx(view);
            }
        };
        this.findViewById(R.id.chkNoExplicanHacenMx).setOnClickListener(onNoExplicanHacenMx);

        //chkNoConfianzaEstudios
        View.OnClickListener onNoConfianzaEstudios = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkNoConfianzaEstudios(view);
            }
        };
        this.findViewById(R.id.chkNoConfianzaEstudios).setOnClickListener(onNoConfianzaEstudios);

        //chkOtrasRazonesNoRecomendaria
        View.OnClickListener onOtrasRazonesNoRecomendaria = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkOtrasRazonesNoRecomendaria(view);
            }
        };
        this.findViewById(R.id.chkOtrasRazonesNoRecomendaria).setOnClickListener(onOtrasRazonesNoRecomendaria);

        /*
         * Parte 5
         * **/

        //chkNoEstuveComodoRealizarPreg
      /*  View.OnClickListener onNoEstuveComodoRealizarPreg = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkNoEstuveComodoRealizarPreg(view);
            }
        };
        this.findViewById(R.id.chkNoEstuveComodoRealizarPreg).setOnClickListener(onNoEstuveComodoRealizarPreg);

        //chkNoRespondieronPreguntas
        View.OnClickListener onNoRespondieronPreguntas = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkNoRespondieronPreguntas(view);
            }
        };
        this.findViewById(R.id.chkNoRespondieronPreguntas).setOnClickListener(onNoRespondieronPreguntas);

        //chkExplicaronRapido
        View.OnClickListener onExplicaronRapido = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkExplicaronRapido(view);
            }
        };
        this.findViewById(R.id.chkExplicaronRapido).setOnClickListener(onExplicaronRapido);

        //chkNoDejaronHacerPreguntas
        View.OnClickListener onNoDejaronHacerPreguntas = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkNoDejaronHacerPreguntas(view);
            }
        };
        this.findViewById(R.id.chkNoDejaronHacerPreguntas).setOnClickListener(onNoDejaronHacerPreguntas);

        //chkOtroMotivosNoComprendioProcedimiento
        View.OnClickListener onOtroMotivosNoComprendioProcedimiento = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChkOtroMotivosNoComprendioProcedimiento(view);
            }
        };
        this.findViewById(R.id.chkOtroMotivosNoComprendioProcedimiento).setOnClickListener(onOtroMotivosNoComprendioProcedimiento);

*/
    }


    // onRadioImportanciaInforEstudiosClicked
  /*  public void onRadioImportanciaInforEstudiosClicked(View view) {
        boolean chkAtenMedica = ((CheckBox) this.findViewById(R.id.chkAtenMedica)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkAtenMedica)).setChecked(chkAtenMedica);
        if (chkAtenMedica) {
            ((CheckBox) this.findViewById(R.id.chkAtenMedica)).setChecked(true);
            this.findViewById(R.id.radioGroup1).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkAtenMedica)).setChecked(false);
            this.findViewById(R.id.radioGroup1).setVisibility(view.INVISIBLE);
            RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.radioGroup1);
            radioGroup.clearCheck();
        }
    }*/


    // AtenMedica
    public void onChkAtenMedica(View view) {
        boolean chkAtenMedica = ((CheckBox) this.findViewById(R.id.chkAtenMedica)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkAtenMedica)).setChecked(chkAtenMedica);
        if (chkAtenMedica) {
            ((CheckBox) this.findViewById(R.id.chkAtenMedica)).setChecked(true);
            this.findViewById(R.id.radioGroup1).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkAtenMedica)).setChecked(false);
            this.findViewById(R.id.radioGroup1).setVisibility(view.INVISIBLE);
            RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.radioGroup1);
            radioGroup.clearCheck();
        }
    }

    //FamAmistades
    public void onChkFamAmistades(View view) {
        boolean chkFamAmistades = ((CheckBox) this.findViewById(R.id.chkFamAmistades)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkFamAmistades)).setChecked(chkFamAmistades);
        if (chkFamAmistades) {
            ((CheckBox) this.findViewById(R.id.chkFamAmistades)).setChecked(true);
            this.findViewById(R.id.radioGroup2).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkFamAmistades)).setChecked(false);
            this.findViewById(R.id.radioGroup2).setVisibility(view.INVISIBLE);
            RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.radioGroup2);
            radioGroup.clearCheck();
        }
    }

    //DecisionPropia
    public void onChkDecisionPropia(View view) {
        boolean chkDecisionPropia = ((CheckBox) this.findViewById(R.id.chkDecisionPropia)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkDecisionPropia)).setChecked(chkDecisionPropia);
        if (chkDecisionPropia) {
            ((CheckBox) this.findViewById(R.id.chkDecisionPropia)).setChecked(true);
            this.findViewById(R.id.radioGroup3).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkDecisionPropia)).setChecked(false);
            this.findViewById(R.id.radioGroup3).setVisibility(view.INVISIBLE);
            RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.radioGroup3);
            radioGroup.clearCheck();
        }
    }

    //Obsequios
    public void onChkObsequios(View view) {
        boolean chkObsequios = ((CheckBox) this.findViewById(R.id.chkObsequios)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkObsequios)).setChecked(chkObsequios);
        if (chkObsequios) {
            ((CheckBox) this.findViewById(R.id.chkObsequios)).setChecked(true);
            this.findViewById(R.id.radioGroup4).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkObsequios)).setChecked(false);
            this.findViewById(R.id.radioGroup4).setVisibility(view.INVISIBLE);
            RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.radioGroup4);
            radioGroup.clearCheck();
        }
    }

    //AyudaComunidad
    public void onChkAyudaComunidad(View view) {
        boolean chkAyudaComunidad = ((CheckBox) this.findViewById(R.id.chkAyudaComunidad)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkAyudaComunidad)).setChecked(chkAyudaComunidad);
        if (chkAyudaComunidad) {
            ((CheckBox) this.findViewById(R.id.chkAyudaComunidad)).setChecked(true);
            this.findViewById(R.id.radioGroup5).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkAyudaComunidad)).setChecked(false);
            this.findViewById(R.id.radioGroup5).setVisibility(view.INVISIBLE);
            RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.radioGroup5);
            radioGroup.clearCheck();
        }
    }

    //ExamLaboratorio
    public void onChkExamLaboratorio(View view) {
        boolean chkExamLaboratorio = ((CheckBox) this.findViewById(R.id.chkExamLaboratorio)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkExamLaboratorio)).setChecked(chkExamLaboratorio);
        if (chkExamLaboratorio) {
            ((CheckBox) this.findViewById(R.id.chkExamLaboratorio)).setChecked(true);
            this.findViewById(R.id.radioGroup6).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkExamLaboratorio)).setChecked(false);
            this.findViewById(R.id.radioGroup6).setVisibility(view.INVISIBLE);
            RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.radioGroup6);
            radioGroup.clearCheck();
        }
    }

    //InteresCientifico
    public void onChkInteresCientifico(View view) {
        boolean chkInteresCientifico = ((CheckBox) this.findViewById(R.id.chkInteresCientifico)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkInteresCientifico)).setChecked(chkInteresCientifico);
        if (chkInteresCientifico) {
            ((CheckBox) this.findViewById(R.id.chkInteresCientifico)).setChecked(true);
            this.findViewById(R.id.radioGroup7).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkInteresCientifico)).setChecked(false);
            this.findViewById(R.id.radioGroup7).setVisibility(view.INVISIBLE);
            RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.radioGroup7);
            radioGroup.clearCheck();
        }
    }

    //AyudarOtros
    public void onChkAyudarOtros(View view) {
        boolean chkAyudarOtros = ((CheckBox) this.findViewById(R.id.chkAyudarOtros)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkAyudarOtros)).setChecked(chkAyudarOtros);
        if (chkAyudarOtros) {
            ((CheckBox) this.findViewById(R.id.chkAyudarOtros)).setChecked(true);
            this.findViewById(R.id.radioGroup8).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkAyudarOtros)).setChecked(false);
            this.findViewById(R.id.radioGroup8).setVisibility(view.INVISIBLE);
            RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.radioGroup8);
            radioGroup.clearCheck();
        }
    }

    //Otras_P1
    public void onChkOtras_P1(View view) {
        boolean chkOtras_P1 = ((CheckBox) this.findViewById(R.id.chkOtras_P1)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkOtras_P1)).setChecked(chkOtras_P1);
        if (chkOtras_P1) {
            ((CheckBox) this.findViewById(R.id.chkOtras_P1)).setChecked(true);
            this.findViewById(R.id.edtxP1_Otras).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkOtras_P1)).setChecked(false);
            this.findViewById(R.id.edtxP1_Otras).setVisibility(view.INVISIBLE);
            EditText textView = (EditText) this.findViewById(R.id.edtxP1_Otras);
            textView.setText("");
        }
    }

    public void onChkOtras_9_2(View view) {
        boolean chkOtras_P1 = ((CheckBox) this.findViewById(R.id.chkOtrasDificultades2)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkOtrasDificultades2)).setChecked(chkOtras_P1);
        if (chkOtras_P1) {
            ((CheckBox) this.findViewById(R.id.chkOtrasDificultades2)).setChecked(true);
            this.findViewById(R.id.edtxOtrasDificultades).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkOtrasDificultades2)).setChecked(false);
            this.findViewById(R.id.edtxOtrasDificultades).setVisibility(view.INVISIBLE);
            EditText textView = (EditText) this.findViewById(R.id.edtxP1_Otras);
            textView.setText("");
        }
    }

    //CalidadAtencionMed
    public void onChkCalidadAtencionMed(View view) {
        boolean chkOtras_P1 = ((CheckBox) this.findViewById(R.id.chkCalidadAtencionMed)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkCalidadAtencionMed)).setChecked(chkOtras_P1);
        if (chkOtras_P1) {
            ((CheckBox) this.findViewById(R.id.chkCalidadAtencionMed)).setChecked(true);
            this.findViewById(R.id.radioGroupP2_1).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkCalidadAtencionMed)).setChecked(false);
            this.findViewById(R.id.radioGroupP2_1).setVisibility(view.INVISIBLE);
            RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.radioGroupP2_1);
            radioGroup.clearCheck();
        }
    }

    //AtencionOportuna
    public void onChkAtencionOportuna(View view) {
        boolean chkAtencionOportuna = ((CheckBox) this.findViewById(R.id.chkAtencionOportuna)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkAtencionOportuna)).setChecked(chkAtencionOportuna);
        if (chkAtencionOportuna) {
            ((CheckBox) this.findViewById(R.id.chkAtencionOportuna)).setChecked(true);
            this.findViewById(R.id.radioGroupP2_2).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkAtencionOportuna)).setChecked(false);
            this.findViewById(R.id.radioGroupP2_2).setVisibility(view.INVISIBLE);
            RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.radioGroupP2_2);
            radioGroup.clearCheck();
        }
    }

    //CoordinacionHosp
    public void onChkCoordinacionHosp(View view) {
        boolean chkCoordinacionHosp = ((CheckBox) this.findViewById(R.id.chkCoordinacionHosp)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkCoordinacionHosp)).setChecked(chkCoordinacionHosp);
        if (chkCoordinacionHosp) {
            ((CheckBox) this.findViewById(R.id.chkCoordinacionHosp)).setChecked(true);
            this.findViewById(R.id.radioGroupP2_3).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkCoordinacionHosp)).setChecked(false);
            this.findViewById(R.id.radioGroupP2_3).setVisibility(view.INVISIBLE);
            RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.radioGroupP2_3);
            radioGroup.clearCheck();
        }
    }

    //InformacionEstadoSalud
    public void onChkInformacionEstadoSalud(View view) {
        boolean chkInformacionEstadoSalud = ((CheckBox) this.findViewById(R.id.chkInformacionEstadoSalud)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkInformacionEstadoSalud)).setChecked(chkInformacionEstadoSalud);
        if (chkInformacionEstadoSalud) {
            ((CheckBox) this.findViewById(R.id.chkInformacionEstadoSalud)).setChecked(true);
            this.findViewById(R.id.radioGroupP2_4).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkInformacionEstadoSalud)).setChecked(false);
            this.findViewById(R.id.radioGroupP2_4).setVisibility(view.INVISIBLE);
            RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.radioGroupP2_4);
            radioGroup.clearCheck();
        }
    }

    //EnseniaPrevEnfer
    public void onChkEnseniaPrevEnfer(View view) {
        boolean chkEnseniaPrevEnfer = ((CheckBox) this.findViewById(R.id.chkEnseniaPrevEnfer)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkEnseniaPrevEnfer)).setChecked(chkEnseniaPrevEnfer);
        if (chkEnseniaPrevEnfer) {
            ((CheckBox) this.findViewById(R.id.chkEnseniaPrevEnfer)).setChecked(true);
            this.findViewById(R.id.radioGroupP2_5).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkEnseniaPrevEnfer)).setChecked(false);
            this.findViewById(R.id.radioGroupP2_5).setVisibility(view.INVISIBLE);
            RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.radioGroupP2_5);
            radioGroup.clearCheck();
        }
    }

    //MejoraConoEnfer
    public void onChkMejoraConoEnfer(View view) {
        boolean chkMejoraConoEnfer = ((CheckBox) this.findViewById(R.id.chkMejoraConoEnfer)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkMejoraConoEnfer)).setChecked(chkMejoraConoEnfer);
        if (chkMejoraConoEnfer) {
            ((CheckBox) this.findViewById(R.id.chkMejoraConoEnfer)).setChecked(true);
            this.findViewById(R.id.radioGroupP2_6).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkMejoraConoEnfer)).setChecked(false);
            this.findViewById(R.id.radioGroupP2_6).setVisibility(view.INVISIBLE);
            RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.radioGroupP2_6);
            radioGroup.clearCheck();
        }
    }

    //InteresCientificoP2
    public void onChkInteresCientificoP2(View view) {
        boolean chkInteresCientificoP2 = ((CheckBox) this.findViewById(R.id.chkInteresCientificoP2)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkInteresCientificoP2)).setChecked(chkInteresCientificoP2);
        if (chkInteresCientificoP2) {
            ((CheckBox) this.findViewById(R.id.chkInteresCientificoP2)).setChecked(true);
            this.findViewById(R.id.radioGroupP2_7).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkInteresCientificoP2)).setChecked(false);
            this.findViewById(R.id.radioGroupP2_7).setVisibility(view.INVISIBLE);
            RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.radioGroupP2_7);
            radioGroup.clearCheck();
        }
    }

    //MejorarTrataDengue
    public void onChkMejorarTrataDengue(View view) {
        boolean chkMejorarTrataDengue = ((CheckBox) this.findViewById(R.id.chkMejorarTrataDengue)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkMejorarTrataDengue)).setChecked(chkMejorarTrataDengue);
        if (chkMejorarTrataDengue) {
            ((CheckBox) this.findViewById(R.id.chkMejorarTrataDengue)).setChecked(true);
            this.findViewById(R.id.radioGroupP2_8).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkMejorarTrataDengue)).setChecked(false);
            this.findViewById(R.id.radioGroupP2_8).setVisibility(view.INVISIBLE);
            RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.radioGroupP2_8);
            radioGroup.clearCheck();
        }
    }

    //NoQuieroQuedarmeEstudio
    public void onChkNoQuieroQuedarmeEstudio(View view) {
        boolean chkNoQuieroQuedarmeEstudio = ((CheckBox) this.findViewById(R.id.chkNoQuieroQuedarmeEstudio)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkNoQuieroQuedarmeEstudio)).setChecked(chkNoQuieroQuedarmeEstudio);
        if (chkNoQuieroQuedarmeEstudio) {
            ((CheckBox) this.findViewById(R.id.chkNoQuieroQuedarmeEstudio)).setChecked(true);
            this.findViewById(R.id.radioGroupP2_9).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkNoQuieroQuedarmeEstudio)).setChecked(false);
            this.findViewById(R.id.radioGroupP2_9).setVisibility(view.INVISIBLE);
            RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.radioGroupP2_9);
            radioGroup.clearCheck();
        }
    }

    //P2_Otras
    public void onChkP2_Otras(View view) {
        boolean chkP2_Otras = ((CheckBox) this.findViewById(R.id.chkP2_Otras)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkP2_Otras)).setChecked(chkP2_Otras);
        if (chkP2_Otras) {
            ((CheckBox) this.findViewById(R.id.chkP2_Otras)).setChecked(true);
            this.findViewById(R.id.edtxP2_Otras).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkP2_Otras)).setChecked(false);
            this.findViewById(R.id.edtxP2_Otras).setVisibility(view.INVISIBLE);
            EditText textView = (EditText) this.findViewById(R.id.edtxP2_Otras);
            textView.setText("");
        }
    }

    //LejosCentroSalud
    public void onChkLejosCentroSalud(View view) {
        boolean chkLejosCentroSalud = ((CheckBox) this.findViewById(R.id.chkLejosCentroSalud)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkLejosCentroSalud)).setChecked(chkLejosCentroSalud);
        if (chkLejosCentroSalud) {
            ((CheckBox) this.findViewById(R.id.chkLejosCentroSalud)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkLejosCentroSalud)).setChecked(false);
        }
    }

    //CostoElevadoTransporte
    public void onChkCostoElevadoTransporte(View view) {
        boolean chkCostoElevadoTransporte = ((CheckBox) this.findViewById(R.id.chkCostoElevadoTransporte)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkCostoElevadoTransporte)).setChecked(chkCostoElevadoTransporte);
        if (chkCostoElevadoTransporte) {
            ((CheckBox) this.findViewById(R.id.chkCostoElevadoTransporte)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkCostoElevadoTransporte)).setChecked(false);
        }
    }

    //TrabajoTiempo
    public void onChkTrabajoTiempo(View view) {
        boolean chkTrabajoTiempo = ((CheckBox) this.findViewById(R.id.chkTrabajoTiempo)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkTrabajoTiempo)).setChecked(chkTrabajoTiempo);
        if (chkTrabajoTiempo) {
            ((CheckBox) this.findViewById(R.id.chkTrabajoTiempo)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkTrabajoTiempo)).setChecked(false);
        }
    }

    //NoQueriaPasarConsultMomento
    public void onChkNoQueriaPasarConsultMomento(View view) {
        boolean chkNoQueriaPasarConsultMomento = ((CheckBox) this.findViewById(R.id.chkNoQueriaPasarConsultMomento)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkNoQueriaPasarConsultMomento)).setChecked(chkNoQueriaPasarConsultMomento);
        if (chkNoQueriaPasarConsultMomento) {
            ((CheckBox) this.findViewById(R.id.chkNoQueriaPasarConsultMomento)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkNoQueriaPasarConsultMomento)).setChecked(false);
        }
    }

    //OtrasDificultades
    public void onChkOtrasDificultades(View view) {
        boolean chkOtrasDificultades = ((CheckBox) this.findViewById(R.id.chkOtrasDificultades2)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkOtrasDificultades2)).setChecked(chkOtrasDificultades);
        if (chkOtrasDificultades) {
            ((CheckBox) this.findViewById(R.id.chkOtrasDificultades2)).setChecked(true);
            this.findViewById(R.id.edtxOtrasDificultades1).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkOtrasDificultades2)).setChecked(false);
            this.findViewById(R.id.edtxOtrasDificultades1).setVisibility(view.INVISIBLE);
            EditText textView = (EditText) this.findViewById(R.id.edtxOtrasDificultades1);
            textView.setText("");
        }
    }

    //AtencionRecibidaCalidad
    public void onChkAtencionRecibidaCalidad(View view) {
        boolean chkAtencionRecibidaCalidad = ((CheckBox) this.findViewById(R.id.chkAtencionRecibidaCalidad)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkAtencionRecibidaCalidad)).setChecked(chkAtencionRecibidaCalidad);
        if (chkAtencionRecibidaCalidad) {
            ((CheckBox) this.findViewById(R.id.chkAtencionRecibidaCalidad)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkAtencionRecibidaCalidad)).setChecked(false);
        }
    }

    //RespuestaNecesidades
    public void onChkRespuestaNecesidades(View view) {
        boolean chkRespuestaNecesidades = ((CheckBox) this.findViewById(R.id.chkRespuestaNecesidades)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkRespuestaNecesidades)).setChecked(chkRespuestaNecesidades);
        if (chkRespuestaNecesidades) {
            ((CheckBox) this.findViewById(R.id.chkRespuestaNecesidades)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkRespuestaNecesidades)).setChecked(false);
        }
    }

    //TiempoEsperaCorto
    public void onChkTiempoEsperaCorto(View view) {
        boolean chkTiempoEsperaCorto = ((CheckBox) this.findViewById(R.id.chkTiempoEsperaCorto)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkTiempoEsperaCorto)).setChecked(chkTiempoEsperaCorto);
        if (chkTiempoEsperaCorto) {
            ((CheckBox) this.findViewById(R.id.chkTiempoEsperaCorto)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkTiempoEsperaCorto)).setChecked(false);
        }
    }

    //MejorAtencionSeguroMed
    public void onChkMejorAtencionSeguroMed(View view) {
        boolean chkMejorAtencionSeguroMed = ((CheckBox) this.findViewById(R.id.chkMejorAtencionSeguroMed)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkMejorAtencionSeguroMed)).setChecked(chkMejorAtencionSeguroMed);
        if (chkMejorAtencionSeguroMed) {
            ((CheckBox) this.findViewById(R.id.chkMejorAtencionSeguroMed)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkMejorAtencionSeguroMed)).setChecked(false);
        }
    }

    //ExamLabNecesarios
    public void onChkExamLabNecesarios(View view) {
        boolean chkExamLabNecesarios = ((CheckBox) this.findViewById(R.id.chkExamLabNecesarios)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkExamLabNecesarios)).setChecked(chkExamLabNecesarios);
        if (chkExamLabNecesarios) {
            ((CheckBox) this.findViewById(R.id.chkExamLabNecesarios)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkExamLabNecesarios)).setChecked(false);
        }
    }



    //PocosRequisitos
    public void onChkPocosRequisitos(View view) {
        boolean chkPocosRequisitos = ((CheckBox) this.findViewById(R.id.chkPocosRequisitos)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkPocosRequisitos)).setChecked(chkPocosRequisitos);
        if (chkPocosRequisitos) {
            ((CheckBox) this.findViewById(R.id.chkPocosRequisitos)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkPocosRequisitos)).setChecked(false);
        }
    }

    //OtrasRazonesRecomendaria
    public void onChkOtrasRazonesRecomendaria(View view) {
        boolean chkOtrasRazonesRecomendaria = ((CheckBox) this.findViewById(R.id.chkOtrasRazonesRecomendaria)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkOtrasRazonesRecomendaria)).setChecked(chkOtrasRazonesRecomendaria);
        if (chkOtrasRazonesRecomendaria) {
            ((CheckBox) this.findViewById(R.id.chkOtrasRazonesRecomendaria)).setChecked(true);
            this.findViewById(R.id.edtxOtrasRazonesRecomendaria).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkOtrasRazonesRecomendaria)).setChecked(false);
            this.findViewById(R.id.edtxOtrasRazonesRecomendaria).setVisibility(view.INVISIBLE);
            EditText textView = (EditText) this.findViewById(R.id.edtxOtrasRazonesRecomendaria);
            textView.setText("");
        }
    }

    //AtencionRecibidaMala
    public void onChkAtencionRecibidaMala(View view) {
        boolean chkAtencionRecibidaMala = ((CheckBox) this.findViewById(R.id.chkAtencionRecibidaMala)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkAtencionRecibidaMala)).setChecked(chkAtencionRecibidaMala);
        if (chkAtencionRecibidaMala) {
            ((CheckBox) this.findViewById(R.id.chkAtencionRecibidaMala)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkAtencionRecibidaMala)).setChecked(false);
        }
    }

    //NoDanRespuestaNecesidades
    public void onChkNoDanRespuestaNecesidades(View view) {
        boolean chkNoDanRespuestaNecesidades = ((CheckBox) this.findViewById(R.id.chkNoDanRespuestaNecesidades)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkNoDanRespuestaNecesidades)).setChecked(chkNoDanRespuestaNecesidades);
        if (chkNoDanRespuestaNecesidades) {
            ((CheckBox) this.findViewById(R.id.chkNoDanRespuestaNecesidades)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkNoDanRespuestaNecesidades)).setChecked(false);
        }
    }

    //TiempoEsperaLargo
    public void onChkTiempoEsperaLargo(View view) {
        boolean chkTiempoEsperaLargo = ((CheckBox) this.findViewById(R.id.chkTiempoEsperaLargo)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkTiempoEsperaLargo)).setChecked(chkTiempoEsperaLargo);
        if (chkTiempoEsperaLargo) {
            ((CheckBox) this.findViewById(R.id.chkTiempoEsperaLargo)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkTiempoEsperaLargo)).setChecked(false);
        }
    }

    //MejorAtencionOtroCentro
    public void onChkMejorAtencionOtroCentro(View view) {
        boolean chkMejorAtencionOtroCentro = ((CheckBox) this.findViewById(R.id.chkMejorAtencionOtroCentro)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkMejorAtencionOtroCentro)).setChecked(chkMejorAtencionOtroCentro);
        if (chkMejorAtencionOtroCentro) {
            ((CheckBox) this.findViewById(R.id.chkMejorAtencionOtroCentro)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkMejorAtencionOtroCentro)).setChecked(false);
        }
    }

    //DemasiadasMx
    public void onChkDemasiadasMx(View view) {
        boolean chkDemasiadasMx = ((CheckBox) this.findViewById(R.id.chkDemasiadasMx)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkDemasiadasMx)).setChecked(chkDemasiadasMx);
        if (chkDemasiadasMx) {
            ((CheckBox) this.findViewById(R.id.chkDemasiadasMx)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkDemasiadasMx)).setChecked(false);
        }
    }

    //MuchosRequisitos
    public void onChkMuchosRequisitos(View view) {
        boolean chkMuchosRequisitos = ((CheckBox) this.findViewById(R.id.chkMuchosRequisitos)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkMuchosRequisitos)).setChecked(chkMuchosRequisitos);
        if (chkMuchosRequisitos) {
            ((CheckBox) this.findViewById(R.id.chkMuchosRequisitos)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkMuchosRequisitos)).setChecked(false);
        }
    }

    //NoExplicanHacenMx
    public void onChkNoExplicanHacenMx(View view) {
        boolean chkNoExplicanHacenMx = ((CheckBox) this.findViewById(R.id.chkNoExplicanHacenMx)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkNoExplicanHacenMx)).setChecked(chkNoExplicanHacenMx);
        if (chkNoExplicanHacenMx) {
            ((CheckBox) this.findViewById(R.id.chkNoExplicanHacenMx)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkNoExplicanHacenMx)).setChecked(false);
        }
    }

    //NoConfianzaEstudios
    public void onChkNoConfianzaEstudios(View view) {
        boolean chkNoConfianzaEstudios = ((CheckBox) this.findViewById(R.id.chkNoConfianzaEstudios)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkNoConfianzaEstudios)).setChecked(chkNoConfianzaEstudios);
        if (chkNoConfianzaEstudios) {
            ((CheckBox) this.findViewById(R.id.chkNoConfianzaEstudios)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkNoConfianzaEstudios)).setChecked(false);
        }
    }

    //OtrasRazonesNoRecomendaria
    public void onChkOtrasRazonesNoRecomendaria(View view) {
        boolean chkOtrasRazonesNoRecomendaria = ((CheckBox) this.findViewById(R.id.chkOtrasRazonesNoRecomendaria)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkOtrasRazonesNoRecomendaria)).setChecked(chkOtrasRazonesNoRecomendaria);
        if (chkOtrasRazonesNoRecomendaria) {
            ((CheckBox) this.findViewById(R.id.chkOtrasRazonesNoRecomendaria)).setChecked(true);
            this.findViewById(R.id.edtxOtrasRazonesNoRecomendaria).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkOtrasRazonesNoRecomendaria)).setChecked(false);
            this.findViewById(R.id.edtxOtrasRazonesNoRecomendaria).setVisibility(view.INVISIBLE);
            EditText textView = (EditText) this.findViewById(R.id.edtxOtrasRazonesNoRecomendaria);
            textView.setText("");
        }
    }

    //NoEstuveComodoRealizarPreg
    public void onChkNoEstuveComodoRealizarPreg(View view) {
        boolean chkNoEstuveComodoRealizarPreg = ((CheckBox) this.findViewById(R.id.chkNoEstuveComodoRealizarPreg)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkNoEstuveComodoRealizarPreg)).setChecked(chkNoEstuveComodoRealizarPreg);
        if (chkNoEstuveComodoRealizarPreg) {
            ((CheckBox) this.findViewById(R.id.chkNoEstuveComodoRealizarPreg)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkNoEstuveComodoRealizarPreg)).setChecked(false);
        }
    }

    //NoRespondieronPreguntas
    public void onChkNoRespondieronPreguntas(View view) {
        boolean chkNoRespondieronPreguntas = ((CheckBox) this.findViewById(R.id.chkNoRespondieronPreguntas)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkNoRespondieronPreguntas)).setChecked(chkNoRespondieronPreguntas);
        if (chkNoRespondieronPreguntas) {
            ((CheckBox) this.findViewById(R.id.chkNoRespondieronPreguntas)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkNoRespondieronPreguntas)).setChecked(false);
        }
    }

    //ExplicaronRapido
    public void onChkExplicaronRapido(View view) {
        boolean chkExplicaronRapido = ((CheckBox) this.findViewById(R.id.chkExplicaronRapido)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkExplicaronRapido)).setChecked(chkExplicaronRapido);
        if (chkExplicaronRapido) {
            ((CheckBox) this.findViewById(R.id.chkExplicaronRapido)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkExplicaronRapido)).setChecked(false);
        }
    }

    //chkNoDejaronHacerPreguntas
    public void onChkNoDejaronHacerPreguntas(View view) {
        boolean chkNoDejaronHacerPreguntas = ((CheckBox) this.findViewById(R.id.chkNoDejaronHacerPreguntas)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkNoDejaronHacerPreguntas)).setChecked(chkNoDejaronHacerPreguntas);
        if (chkNoDejaronHacerPreguntas) {
            ((CheckBox) this.findViewById(R.id.chkNoDejaronHacerPreguntas)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkNoDejaronHacerPreguntas)).setChecked(false);
        }
    }

    //OtroMotivosNoComprendioProcedimiento
    public void onChkOtroMotivosNoComprendioProcedimiento(View view) {
        boolean chkOtrasRazonesNoRecomendaria = ((CheckBox) this.findViewById(R.id.chkOtroMotivosNoComprendioProcedimiento)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkOtroMotivosNoComprendioProcedimiento)).setChecked(chkOtrasRazonesNoRecomendaria);
        if (chkOtrasRazonesNoRecomendaria) {
            ((CheckBox) this.findViewById(R.id.chkOtroMotivosNoComprendioProcedimiento)).setChecked(true);
            this.findViewById(R.id.edtxOtroMotivosNoComprendioProcedimiento).setVisibility(view.VISIBLE);
        } else {
            ((CheckBox) this.findViewById(R.id.chkOtroMotivosNoComprendioProcedimiento)).setChecked(false);
            this.findViewById(R.id.edtxOtroMotivosNoComprendioProcedimiento).setVisibility(view.INVISIBLE);
            EditText textView = (EditText) this.findViewById(R.id.edtxOtroMotivosNoComprendioProcedimiento);
            textView.setText("");
        }
    }

    /*//EntiendoProcedimientos
    public void onChkEntiendoProcedimientos(View view) {
        boolean chkEntiendoProcedimientos = ((CheckBox) this.findViewById(R.id.chkEntiendoProcedimientos)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkEntiendoProcedimientos)).setChecked(chkEntiendoProcedimientos);
        if (chkEntiendoProcedimientos) {
            ((CheckBox) this.findViewById(R.id.chkEntiendoProcedimientos)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkEntiendoProcedimientos)).setChecked(false);
        }
    }

    //Satisfecho
    public void onChkSatisfecho(View view) {
        boolean chkSatisfecho = ((CheckBox) this.findViewById(R.id.chkSatisfecho)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkSatisfecho)).setChecked(chkSatisfecho);
        if (chkSatisfecho) {
            ((CheckBox) this.findViewById(R.id.chkSatisfecho)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkSatisfecho)).setChecked(false);
        }
    }

    //ComodoInformacion
    public void onChkComodoInformacion(View view) {
        boolean chkComodoInformacion = ((CheckBox) this.findViewById(R.id.chkComodoInformacion)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkComodoInformacion)).setChecked(chkComodoInformacion);
        if (chkComodoInformacion) {
            ((CheckBox) this.findViewById(R.id.chkComodoInformacion)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkComodoInformacion)).setChecked(false);
        }
    }

    //NoComodoPreguntas
    public void onChkNoComodoPreguntas(View view) {
        boolean chkNoComodoPreguntas = ((CheckBox) this.findViewById(R.id.chkNoComodoPreguntas)).isChecked();
        ((CheckBox) this.findViewById(R.id.chkNoComodoPreguntas)).setChecked(chkNoComodoPreguntas);
        if (chkNoComodoPreguntas) {
            ((CheckBox) this.findViewById(R.id.chkNoComodoPreguntas)).setChecked(true);
        } else {
            ((CheckBox) this.findViewById(R.id.chkNoComodoPreguntas)).setChecked(false);
        }
    }*/

    public void clearFocus() {
        EditText edtxP1_Otras = (EditText) this.findViewById(R.id.edtxP1_Otras);
        EditText edtxP2_Otras = (EditText) this.findViewById(R.id.edtxP2_Otras);
        EditText edtxOtrasDificultades = (EditText) this.findViewById(R.id.edtxOtrasDificultades);
        EditText edtxOtrasRazonesRecomendaria = (EditText) this.findViewById(R.id.edtxOtrasRazonesRecomendaria);
        EditText edtxOtroMotivosNoComprendioProcedimiento = (EditText) this.findViewById(R.id.edtxOtroMotivosNoComprendioProcedimiento);

     //   edtxP1_Otras.clearFocus();
       // edtxP2_Otras.clearFocus();
    //   edtxOtrasDificultades.clearFocus();
    //    edtxOtrasRazonesRecomendaria.clearFocus();
   //     edtxOtroMotivosNoComprendioProcedimiento.clearFocus();
    }

    //Primera Parte
    public void onRadioButtonP1Clicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        clearFocus();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioAtenMedica0:
                if (checked)
                    break;
            case R.id.radioAtenMedica1:
                if (checked)
                    break;
            case R.id.radioAtenMedica2:
                if (checked)
                    break;
            case R.id.radioAtenMedica3:
                if (checked)
                    break;
            case R.id.radioAtenMedica4:
                if (checked)
                    break;
            case R.id.radioAtenMedica5:
                if (checked)
                    break;
        }
    }

    public void onRadioButtonP2Clicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
            case R.id.radioFamAmistadesP2_0:
                if (checked)
                    break;
            case R.id.radioFamAmistadesP2_1:
                if (checked)
                    break;
            case R.id.radioFamAmistadesP2_2:
                if (checked)
                    break;
            case R.id.radioFamAmistadesP2_3:
                if (checked)
                    break;
            case R.id.radioFamAmistadesP2_4:
                if (checked)
                    break;
            case R.id.radioFamAmistadesP2_5:
                if (checked)
                    break;
        }
    }

    public void onRadioButtonP3Clicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
            case R.id.radioDesicionPropiaP3_0:
                if (checked)
                    break;
            case R.id.radioDesicionPropiaP3_1:
                if (checked)
                    break;
            case R.id.radioDesicionPropiaP3_2:
                if (checked)
                    break;
            case R.id.radioDesicionPropiaP3_3:
                if (checked)
                    break;
            case R.id.radioDesicionPropiaP3_4:
                if (checked)
                    break;
            case R.id.radioDesicionPropiaP3_5:
                if (checked)
                    break;
        }
    }

    public void onRadioButtonP4Clicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
            case R.id.radioObsequiosP4_0:
                if (checked)
                    break;
            case R.id.radioObsequiosP4_1:
                if (checked)
                    break;
            case R.id.radioObsequiosP4_2:
                if (checked)
                    break;
            case R.id.radioObsequiosP4_3:
                if (checked)
                    break;
            case R.id.radioObsequiosP4_4:
                if (checked)
                    break;
            case R.id.radioObsequiosP4_5:
                if (checked)
                    break;
        }
    }

    public void onRadioButtonP5Clicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
            case R.id.radioAyudaComunidadP5_0:
                if (checked)
                    break;
            case R.id.radioAyudaComunidadP5_1:
                if (checked)
                    break;
            case R.id.radioAyudaComunidadP5_2:
                if (checked)
                    break;
            case R.id.radioAyudaComunidadP5_3:
                if (checked)
                    break;
            case R.id.radioAyudaComunidadP5_4:
                if (checked)
                    break;
            case R.id.radioAyudaComunidadP5_5:
                if (checked)
                    break;
        }
    }

    public void onRadioButtonP6Clicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
            case R.id.radioExaLabP6_0:
                if (checked)
                    break;
            case R.id.radioExaLabP6_1:
                if (checked)
                    break;
            case R.id.radioExaLabP6_2:
                if (checked)
                    break;
            case R.id.radioExaLabP6_3:
                if (checked)
                    break;
            case R.id.radioExaLabP6_4:
                if (checked)
                    break;
            case R.id.radioExaLabP6_5:
                if (checked)
                    break;
        }
    }

    public void onRadioButtonP7Clicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
            case R.id.radioInteresCientificoP7_0:
                if (checked)
                    break;
            case R.id.radioInteresCientificoP7_1:
                if (checked)
                    break;
            case R.id.radioInteresCientificoP7_2:
                if (checked)
                    break;
            case R.id.radioInteresCientificoP7_3:
                if (checked)
                    break;
            case R.id.radioInteresCientificoP7_4:
                if (checked)
                    break;
            case R.id.radioInteresCientificoP7_5:
                if (checked)
                    break;
        }
    }

    public void onRadioButtonP8Clicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
            case R.id.radioAyudarOtrosP8_0:
                if (checked)
                    break;
            case R.id.radioAyudarOtrosP8_1:
                if (checked)
                    break;
            case R.id.radioAyudarOtrosP8_2:
                if (checked)
                    break;
            case R.id.radioAyudarOtrosP8_3:
                if (checked)
                    break;
            case R.id.radioAyudarOtrosP8_4:
                if (checked)
                    break;
            case R.id.radioAyudarOtrosP8_5:
                if (checked)
                    break;
        }
    }

    //Segunda Parte
    public void onRadioButtonPart2P1Clicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
            case R.id.radioCalidadAtenMedica0:
                if (checked)
                    break;
            case R.id.radioCalidadAtenMedica1:
                if (checked)
                    break;
            case R.id.radioCalidadAtenMedica2:
                if (checked)
                    break;
            case R.id.radioCalidadAtenMedica3:
                if (checked)
                    break;
            case R.id.radioCalidadAtenMedica4:
                if (checked)
                    break;
            case R.id.radioCalidadAtenMedica5:
                if (checked)
                    break;
        }
    }

    public void onRadioButtonPart2P2Clicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
            case R.id.radioAtencionOportuna0:
                if (checked)
                    break;
            case R.id.radioAtencionOportuna1:
                if (checked)
                    break;
            case R.id.radioAtencionOportuna2:
                if (checked)
                    break;
            case R.id.radioAtencionOportuna3:
                if (checked)
                    break;
            case R.id.radioAtencionOportuna4:
                if (checked)
                    break;
            case R.id.radioAtencionOportuna5:
                if (checked)
                    break;
        }
    }

    public void onRadioButtonPart2P3Clicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
            case R.id.radioCoordinacionHosp0:
                if (checked)
                    break;
            case R.id.radioCoordinacionHosp1:
                if (checked)
                    break;
            case R.id.radioCoordinacionHosp2:
                if (checked)
                    break;
            case R.id.radioCoordinacionHosp3:
                if (checked)
                    break;
            case R.id.radioCoordinacionHosp4:
                if (checked)
                    break;
            case R.id.radioCoordinacionHosp5:
                if (checked)
                    break;
        }
    }

    public void onRadioButtonPart2P4Clicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
            case R.id.radioInformacionEstadoSalud0:
                if (checked)
                    break;
            case R.id.radioInformacionEstadoSalud1:
                if (checked)
                    break;
            case R.id.radioInformacionEstadoSalud2:
                if (checked)
                    break;
            case R.id.radioInformacionEstadoSalud3:
                if (checked)
                    break;
            case R.id.radioInformacionEstadoSalud4:
                if (checked)
                    break;
            case R.id.radioInformacionEstadoSalud5:
                if (checked)
                    break;
        }
    }

    public void onRadioButtonPart2P5Clicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
            case R.id.radioEnseniaPrevEnfer0:
                if (checked)
                    break;
            case R.id.radioEnseniaPrevEnfer1:
                if (checked)
                    break;
            case R.id.radioEnseniaPrevEnfer2:
                if (checked)
                    break;
            case R.id.radioEnseniaPrevEnfer3:
                if (checked)
                    break;
            case R.id.radioEnseniaPrevEnfer4:
                if (checked)
                    break;
            case R.id.radioEnseniaPrevEnfer5:
                if (checked)
                    break;
        }
    }

    public void onRadioButtonPart2P6Clicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
            case R.id.radioMejoraConoEnfer0:
                if (checked)
                    break;
            case R.id.radioMejoraConoEnfer1:
                if (checked)
                    break;
            case R.id.radioMejoraConoEnfer2:
                if (checked)
                    break;
            case R.id.radioMejoraConoEnfer3:
                if (checked)
                    break;
            case R.id.radioMejoraConoEnfer4:
                if (checked)
                    break;
            case R.id.radioMejoraConoEnfer5:
                if (checked)
                    break;
        }
    }

    public void onRadioButtonPart2P7Clicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
            case R.id.radioInteresCientificoP2_0:
                if (checked)
                    break;
            case R.id.radioInteresCientificoP2_1:
                if (checked)
                    break;
            case R.id.radioInteresCientificoP2_2:
                if (checked)
                    break;
            case R.id.radioInteresCientificoP2_3:
                if (checked)
                    break;
            case R.id.radioInteresCientificoP2_4:
                if (checked)
                    break;
            case R.id.radioInteresCientificoP2_5:
                if (checked)
                    break;
        }
    }

    public void onRadioButtonPart2P8Clicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
            case R.id.radioMejorarTrataDengue0:
                if (checked)
                    break;
            case R.id.radioMejorarTrataDengue1:
                if (checked)
                    break;
            case R.id.radioMejorarTrataDengue2:
                if (checked)
                    break;
            case R.id.radioMejorarTrataDengue3:
                if (checked)
                    break;
            case R.id.radioMejorarTrataDengue4:
                if (checked)
                    break;
            case R.id.radioMejorarTrataDengue5:
                if (checked)
                    break;
        }
    }

    public void onRadioButtonPart2P9Clicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
            case R.id.radioNoQuieroQuedarmeEstudio0:
                if (checked)
                    break;
            case R.id.radioNoQuieroQuedarmeEstudio1:
                if (checked)
                    break;
            case R.id.radioNoQuieroQuedarmeEstudio2:
                if (checked)
                    break;
            case R.id.radioNoQuieroQuedarmeEstudio3:
                if (checked)
                    break;
            case R.id.radioNoQuieroQuedarmeEstudio4:
                if (checked)
                    break;
            case R.id.radioNoQuieroQuedarmeEstudio5:
                if (checked)
                    break;
        }
    }

    public void onRadioDificultadBuscarAtencionClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
            case R.id.radioDificultadBuscarAtencionS:
                if (checked)
                    activarPreguntas3_11(view);
                break;
            case R.id.radioDificultadBuscarAtencionN:
                if (checked)
                    desactivarPreguntas3_11 (view);
                break;
        }
    }

    public void activarPreguntas3_1(View view) {
        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.pregunta8_1);
        linearLayout.setVisibility(view.VISIBLE);
    }

    public void desactivarPreguntas3_1(View view) {
        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.pregunta8_1);
        linearLayout.setVisibility(view.GONE);

        ((CheckBox) this.findViewById(R.id.chkNoQueriaPasarConsultMomento)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkTrabajoTiempo)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkCostoElevadoTransporte)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkLejosCentroSalud)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkOtrasDificultades2)).setChecked(false);
        this.findViewById(R.id.edtxOtrasDificultades1).setVisibility(view.INVISIBLE);
        EditText textView = (EditText) this.findViewById(R.id.edtxOtrasDificultades1);
        textView.setText("");
    }

    public void activarPreguntas3_11(View view) {
        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.pregunta3_1);
        linearLayout.setVisibility(view.VISIBLE);
    }

    public void desactivarPreguntas3_11(View view) {
        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.pregunta3_1);
        linearLayout.setVisibility(view.GONE);

        ((CheckBox) this.findViewById(R.id.chkNoQueriaPasarConsultMomento)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkTrabajoTiempo)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkCostoElevadoTransporte)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkLejosCentroSalud)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkOtrasDificultades2)).setChecked(false);
        this.findViewById(R.id.edtxOtrasDificultades1).setVisibility(view.INVISIBLE);
        EditText textView = (EditText) this.findViewById(R.id.edtxOtrasDificultades1);
        textView.setText("");
    }

    public void onRadioRecomendariaOtraPersonaClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
            case R.id.radioDificultadBuscarAtencionP4S:
                if (checked)
                    activarPreguntas4_1(view);
                break;
            case R.id.radioDificultadBuscarAtencionP4N:
                if (checked)
                    activarPreguntas4_2(view);
                break;
        }
    }

    public void activarPreguntas4_1(View view) {
        LinearLayout linearLayout4_1 = (LinearLayout) this.findViewById(R.id.pregunta10_1);
        linearLayout4_1.setVisibility(view.VISIBLE);
        LinearLayout linearLayout4_2 = (LinearLayout) this.findViewById(R.id.pregunta4_2);
        linearLayout4_2.setVisibility(view.GONE);
        //Desmarcar Todo de la prgunta 4.2
        ((CheckBox) this.findViewById(R.id.chkOtrasRazonesNoRecomendaria)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkNoConfianzaEstudios)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkNoExplicanHacenMx)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkMuchosRequisitos)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkDemasiadasMx)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkMejorAtencionOtroCentro)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkTiempoEsperaLargo)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkNoDanRespuestaNecesidades)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkAtencionRecibidaMala)).setChecked(false);
        this.findViewById(R.id.edtxOtrasRazonesNoRecomendaria).setVisibility(view.INVISIBLE);
        EditText textView = (EditText) this.findViewById(R.id.edtxOtrasRazonesNoRecomendaria);
        textView.setText("");
    }

    public void activarPreguntas4_2(View view) {
        LinearLayout linearLayout4_1 = (LinearLayout) this.findViewById(R.id.pregunta10_1);
        linearLayout4_1.setVisibility(view.GONE);
        LinearLayout linearLayout4_2 = (LinearLayout) this.findViewById(R.id.pregunta4_2);
        linearLayout4_2.setVisibility(view.VISIBLE);
        //Desmarcar Todo de la prgunta 4.1
        ((CheckBox) this.findViewById(R.id.chkOtrasRazonesRecomendaria)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkPocosRequisitos)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkExamLabNecesarios)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkMejorAtencionSeguroMed)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkTiempoEsperaCorto)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkRespuestaNecesidades)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkAtencionRecibidaCalidad)).setChecked(false);
        this.findViewById(R.id.edtxOtrasRazonesRecomendaria).setVisibility(view.INVISIBLE);
        EditText textView = (EditText) this.findViewById(R.id.edtxOtrasRazonesRecomendaria);
        textView.setText("");
    }

    public void onRadioComprendeProcedimientosClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
            case R.id.radioComprendeProcedimientosS:
                if (checked)
                    limpiarDatos5_1(view);
                break;
            case R.id.radioComprendeProcedimientosN:
                if (checked)
                    activarPreguntas5_1(view);
                break;
        }
    }

    public void limpiarDatos5_1(View view) {
        LinearLayout linearLayout5_1 = (LinearLayout) this.findViewById(R.id.pregunta5_1);
        linearLayout5_1.setVisibility(view.GONE);
        ((CheckBox) this.findViewById(R.id.chkOtroMotivosNoComprendioProcedimiento)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkNoDejaronHacerPreguntas)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkExplicaronRapido)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkNoRespondieronPreguntas)).setChecked(false);
        ((CheckBox) this.findViewById(R.id.chkNoEstuveComodoRealizarPreg)).setChecked(false);
        this.findViewById(R.id.edtxOtroMotivosNoComprendioProcedimiento).setVisibility(view.INVISIBLE);
        EditText textView = (EditText) this.findViewById(R.id.edtxOtroMotivosNoComprendioProcedimiento);
        textView.setText("");
    }

    public void activarPreguntas5_1(View view) {
        LinearLayout linearLayout5_1 = (LinearLayout) this.findViewById(R.id.pregunta5_1);
        linearLayout5_1.setVisibility(view.VISIBLE);
    }

    public void onRadioPersonalBrindaConsejosClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
       /* switch(view.getId()) {
            case R.id.radioPersonalBrindaConsejosS:
                if (checked)
                    break;
            case R.id.radioPersonalBrindaConsejosN:
                if (checked)
                    break;
        }*/
    }


    public void onRadioImportanciaInforEstudiosClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
          /*  case R.id.radioImportanciaInforEstudios0:
                if (checked)
                    break;*/
            case R.id.radioImportanciaInforEstudios02:
                if (checked)
                    break;
            case R.id.radioImportanciaInforEstudios03:
                if (checked)
                    break;
            case R.id.radioImportanciaInforEstudios04:
                if (checked)
                    break;
            case R.id.radioImportanciaInforEstudios05:
                if (checked)
                    break;
            case R.id.radioImportanciaInforEstudios06:
                if (checked)
                    break;
        }
    }
    public void onRadioopcionTiempoAtencionRecibido1Clicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
          /*  case R.id.radioImportanciaInforEstudios0:
                if (checked)
                    break;*/
            case R.id.radioopcionTiempoAtencionRecibido1:
                if (checked)
                    break;
            case R.id.radioopcionTiempoAtencionRecibido2:
                if (checked)
                    break;
            case R.id.radioopcionTiempoAtencionRecibido3:
                if (checked)
                    break;
            case R.id.radioopcionTiempoAtencionRecibido4:
                if (checked)
                    break;
            case R.id.radioopcionTiempoAtencionRecibido5:
                if (checked)
                    break;
        }
    }
    public void onRadioopcionAtencionEnfermeriaClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
          /*  case R.id.radioImportanciaInforEstudios0:
                if (checked)
                    break;*/
            case R.id.radioopcionAtencionEnfermeria1:
                if (checked)
                    break;
            case R.id.radioopcionAtencionEnfermeria2:
                if (checked)
                    break;
            case R.id.radioopcionAtencionEnfermeria3:
                if (checked)
                    break;
            case R.id.radioopcionAtencionEnfermeria4:
                if (checked)
                    break;
            case R.id.radioopcionAtencionEnfermeria5:
                if (checked)
                    break;
        }
    }
    public void onRadioAtencionDoctorClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
          /*  case R.id.radioImportanciaInforEstudios0:
                if (checked)
                    break;*/
            case R.id.radioAtencionDoctor1:
                if (checked)
                    break;
            case R.id.radioAtencionDoctor2:
                if (checked)
                    break;
            case R.id.radioAtencionDoctor3:
                if (checked)
                    break;
            case R.id.radioAtencionDoctor4:
                if (checked)
                    break;
            case R.id.radioAtencionDoctor5:
                if (checked)
                    break;
        }
    }
    public void onRadioAmbienteAtiendeClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
          /*  case R.id.radioImportanciaInforEstudios0:
                if (checked)
                    break;*/
            case R.id.radioAmbienteAtiende1:
                if (checked)
                    break;
            case R.id.radioAmbienteAtiende2:
                if (checked)
                    break;
            case R.id.radioAmbienteAtiende3:
                if (checked)
                    break;
            case R.id.radioAmbienteAtiende4:
                if (checked)
                    break;
            case R.id.radioAmbienteAtiende5:
                if (checked)
                    break;
        }
    }
    public void onRadioExplicaroDiagnosticoClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
          /*  case R.id.radioImportanciaInforEstudios0:
                if (checked)
                    break;*/
            case R.id.radioExplicaroDiagnostico1:
                if (checked)
                    break;
            case R.id.radioExplicaroDiagnostico2:
                if (checked)
                    break;
            case R.id.radioExplicaroDiagnostico3:
                if (checked)
                    break;
            case R.id.radioExplicaroDiagnostico4:
                if (checked)
                    break;
            case R.id.radioExplicaroDiagnostico5:
                if (checked)
                    break;
        }
    }
    public void onRadioExplicaronTratamientoClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
          /*  case R.id.radioImportanciaInforEstudios0:
                if (checked)
                    break;*/
            case R.id.radioExplicaronTratamiento1:
                if (checked)
                    break;
            case R.id.radioExplicaronTratamiento2:
                if (checked)
                    break;
            case R.id.radioExplicaronTratamiento3:
                if (checked)
                    break;
            case R.id.radioExplicaronTratamiento4:
                if (checked)
                    break;
            case R.id.radioExplicaronTratamiento5:
                if (checked)
                    break;
        }
    }
    public void onRadioHaTenidoArbovirusClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        clearFocus();
        switch(view.getId()) {
          /*  case R.id.radioImportanciaInforEstudios0:
                if (checked)
                    break;*/
            case R.id.radioHaTenidoArbovirusS:
                if (checked)
                    activarPreguntas3_1(view);
                    break;
            case R.id.radioHaTenidoArbovirusN:
                if (checked)
                    desactivarPreguntas3_1(view);
                    break;
            case R.id.radioNoAplicaNoTieneAbovirusNA:
                if (checked)
                    break;

        }
    }
    public void onClickGuardar(View view) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Encuesta de satisfacción de usuario");
            //builder.setMessage("");
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.finish, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    /*Toast.makeText(getApplicationContext(), R.string.save, Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();*/
                    validarDatos(dialogInterface);
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Se a cancelado el guardado de la información", Toast.LENGTH_LONG);
                    toast.show();
                    //Toast.makeText(getApplicationContext(), R.string.cancel, Toast.LENGTH_SHORT).show();
                    //dialogInterface.dismiss();
                }
            });
            builder.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onClickCancelar(View view) {
        Intent i = new Intent(getApplicationContext(),
                MenuParticipanteActivity.class);
        i.putExtra(Constants.COD_PARTICIPANTE, participante.getCodigo());
        i.putExtra(Constants.VISITA_EXITOSA, visExitosa);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.err_cancel), Toast.LENGTH_LONG);
        toast.show();
        finish();
    }

    //Validar Datos
    public void validarDatos(DialogInterface dialogInterface) {

        try {
            //Abre la base de datos
            String mPass = ((MyIcsApplication) this.getApplication()).getPassApp();
            estudiosAdapter = new EstudioDBAdapter(this.getApplicationContext(), mPass, false, false);
            estudiosAdapter.open();

         /*   boolean chkAtenMedica = ((CheckBox) this.findViewById(R.id.chkAtenMedica)).isChecked();
            boolean chkFamAmistades = ((CheckBox) this.findViewById(R.id.chkFamAmistades)).isChecked();
            boolean chkDecisionPropia = ((CheckBox) this.findViewById(R.id.chkDecisionPropia)).isChecked();
            boolean chkObsequios = ((CheckBox) this.findViewById(R.id.chkObsequios)).isChecked();
            boolean chkAyudaComunidad = ((CheckBox) this.findViewById(R.id.chkAyudaComunidad)).isChecked();
            boolean chkExamLaboratorio = ((CheckBox) this.findViewById(R.id.chkExamLaboratorio)).isChecked();
            boolean chkInteresCientifico = ((CheckBox) this.findViewById(R.id.chkInteresCientifico)).isChecked();
            boolean chkAyudarOtros = ((CheckBox) this.findViewById(R.id.chkAyudarOtros)).isChecked();
            boolean chkOtras_P1 = ((CheckBox) this.findViewById(R.id.chkOtras_P1)).isChecked();
*/
            //Parte2
            /*boolean chkCalidadAtencionMed = ((CheckBox) this.findViewById(R.id.chkAtencionRecibidaCalidad)).isChecked();
            boolean chkAtencionOportuna = ((CheckBox) this.findViewById(R.id.chkAtencionOportuna)).isChecked();
            boolean chkCoordinacionHosp = ((CheckBox) this.findViewById(R.id.chkCoordinacionHosp)).isChecked();
            boolean chkInformacionEstadoSalud = ((CheckBox) this.findViewById(R.id.chkInformacionEstadoSalud)).isChecked();
            boolean chkEnseniaPrevEnfer = ((CheckBox) this.findViewById(R.id.chkEnseniaPrevEnfer)).isChecked();*/
//            boolean chkMejoraConoEnfer = ((CheckBox) this.findViewById(R.id.chkMejoraConoEnfer)).isChecked();
       //     boolean chkInteresCientificoP2 = ((CheckBox) this.findViewById(R.id.chkInteresCientificoP2)).isChecked();
       //     boolean chkMejorarTrataDengue = ((CheckBox) this.findViewById(R.id.chkMejorarTrataDengue)).isChecked();
        //    boolean chkNoQuieroQuedarmeEstudio = ((CheckBox) this.findViewById(R.id.chkNoQuieroQuedarmeEstudio)).isChecked();
      //      boolean chkP2_Otras = ((CheckBox) this.findViewById(R.id.chkP2_Otras)).isChecked();

            //Parte3
            boolean chkLejosCentroSalud = ((CheckBox) this.findViewById(R.id.chkLejosCentroSalud)).isChecked();
            boolean chkCostoElevadoTransporte = ((CheckBox) this.findViewById(R.id.chkCostoElevadoTransporte)).isChecked();
            boolean chkTrabajoTiempo = ((CheckBox) this.findViewById(R.id.chkTrabajoTiempo)).isChecked();
            boolean chkNoQueriaPasarConsultMomento = ((CheckBox) this.findViewById(R.id.chkNoQueriaPasarConsultMomento)).isChecked();

            boolean chkOtrasDificultades = ((CheckBox) this.findViewById(R.id.chkOtrasDificultades2)).isChecked();

            //Parte4
            boolean chkAtencionRecibidaCalidad = ((CheckBox) this.findViewById(R.id.chkAtencionRecibidaCalidad)).isChecked();
            boolean chkRespuestaNecesidades = ((CheckBox) this.findViewById(R.id.chkRespuestaNecesidades)).isChecked();
            boolean chkTiempoEsperaCorto = ((CheckBox) this.findViewById(R.id.chkTiempoEsperaCorto)).isChecked();
            boolean chkMejorAtencionSeguroMed = ((CheckBox) this.findViewById(R.id.chkMejorAtencionSeguroMed)).isChecked();
            boolean chkExamLabNecesarios = ((CheckBox) this.findViewById(R.id.chkExamLabNecesarios)).isChecked();
            boolean chkPocosRequisitos = ((CheckBox) this.findViewById(R.id.chkPocosRequisitos)).isChecked();
            boolean chkOtrasRazonesRecomendaria = ((CheckBox) this.findViewById(R.id.chkOtrasRazonesRecomendaria)).isChecked();

            boolean chkAtencionRecibidaMala = ((CheckBox) this.findViewById(R.id.chkAtencionRecibidaMala)).isChecked();
            boolean chkNoDanRespuestaNecesidades = ((CheckBox) this.findViewById(R.id.chkNoDanRespuestaNecesidades)).isChecked();
            boolean chkTiempoEsperaLargo = ((CheckBox) this.findViewById(R.id.chkTiempoEsperaLargo)).isChecked();
            boolean chkMejorAtencionOtroCentro = ((CheckBox) this.findViewById(R.id.chkMejorAtencionOtroCentro)).isChecked();
            boolean chkDemasiadasMx = ((CheckBox) this.findViewById(R.id.chkDemasiadasMx)).isChecked();
            boolean chkMuchosRequisitos = ((CheckBox) this.findViewById(R.id.chkMuchosRequisitos)).isChecked();
            boolean chkNoExplicanHacenMx = ((CheckBox) this.findViewById(R.id.chkNoExplicanHacenMx)).isChecked();
            boolean chkNoConfianzaEstudios = ((CheckBox) this.findViewById(R.id.chkNoConfianzaEstudios)).isChecked();
            boolean chkOtrasRazonesNoRecomendaria = ((CheckBox) this.findViewById(R.id.chkOtrasRazonesNoRecomendaria)).isChecked();

            //PARTE 5
            boolean chkNoEstuveComodoRealizarPreg = ((CheckBox) this.findViewById(R.id.chkNoEstuveComodoRealizarPreg)).isChecked();
            boolean chkNoRespondieronPreguntas = ((CheckBox) this.findViewById(R.id.chkNoRespondieronPreguntas)).isChecked();
            boolean chkExplicaronRapido = ((CheckBox) this.findViewById(R.id.chkExplicaronRapido)).isChecked();
            boolean chkNoDejaronHacerPreguntas = ((CheckBox) this.findViewById(R.id.chkNoDejaronHacerPreguntas)).isChecked();
            boolean chkOtroMotivosNoComprendioProcedimiento = ((CheckBox) this.findViewById(R.id.chkOtroMotivosNoComprendioProcedimiento)).isChecked();

            //PARTE 6
        /*    boolean chkEntiendoProcedimientos = ((CheckBox) this.findViewById(R.id.chkEntiendoProcedimientos)).isChecked();
            boolean chkSatisfecho = ((CheckBox) this.findViewById(R.id.chkSatisfecho)).isChecked();
            boolean chkComodoInformacion = ((CheckBox) this.findViewById(R.id.chkComodoInformacion)).isChecked();
            boolean chkNoComodoPreguntas = ((CheckBox) this.findViewById(R.id.chkNoComodoPreguntas)).isChecked();*/


           /* EncuestaSatisfaccionUsuarioId esId = new EncuestaSatisfaccionUsuarioId();
            esId.setCodigo(participante.getCodigo());
            esId.setFecha(new Date());
            encuestaSatisfaccionUsuario.setEsId(esId);*/

            //Parte 1
         /*   if (!chkAtenMedica
                    && !chkFamAmistades
                    && !chkDecisionPropia
                    && !chkObsequios
                    && !chkAyudaComunidad
                    && !chkExamLaboratorio
                    && !chkInteresCientifico
                    && !chkAyudarOtros
                    & !chkOtras_P1) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar al menos una opción en la pregunta 1", Toast.LENGTH_LONG).show();
                return;
            }*/

            //Validando la pregunta 1
         //   if (chkAtenMedica) {
         /*       RadioGroup radioGroupP01 = (RadioGroup) findViewById(R.id.radioGroupP01);
                int selected = radioGroupP01.getCheckedRadioButtonId();
                RadioButton selectedRadioGroup1 = (RadioButton) findViewById(selected);
                if (selectedRadioGroup1 == null) {
                    //Retornar mensaje
                    dialogInterface.dismiss();
                    Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en atencion médica", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                    encuestaSatisfaccionUsuario.setAtencionMedica(selectedRadioGroup1.getText().toString());
                }
    //        }
            //Validando la pregunta 2
          //  if (chkFamAmistades) {
                RadioGroup radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
                int selected2 = radioGroup2.getCheckedRadioButtonId();
                RadioButton selectedRadioGroup2 = (RadioButton) findViewById(selected2);
                if (selectedRadioGroup2 == null) {
                    //Retornar mensaje
                    dialogInterface.dismiss();
                    Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en famila y amistades", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                    encuestaSatisfaccionUsuario.setFamilaAmistades(selectedRadioGroup2.getText().toString());
                }
        //    }
            //Validando la pregunta 3
          //  if (chkDecisionPropia) {
                RadioGroup radioGroup3 = (RadioGroup) findViewById(R.id.radioGroup3);
                int selected3 = radioGroup3.getCheckedRadioButtonId();
                RadioButton selectedRadioGroup3 = (RadioButton) findViewById(selected3);
                if (selectedRadioGroup3 == null) {
                    //Retornar mensaje
                    dialogInterface.dismiss();
                    Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en decisión propia", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                    encuestaSatisfaccionUsuario.setDesicionPropia(selectedRadioGroup3.getText().toString());
                }
        //    }
            //Validando la pregunta 4
          //  if (chkObsequios) {
                RadioGroup radioGroup4 = (RadioGroup) findViewById(R.id.radioGroup4);
                int selected4 = radioGroup4.getCheckedRadioButtonId();
                RadioButton selectedRadioGroup4 = (RadioButton) findViewById(selected4);
                if (selectedRadioGroup4 == null) {
                    //Retornar mensaje
                    dialogInterface.dismiss();
                    Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en obsequios", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                    encuestaSatisfaccionUsuario.setObsequiosOfreceEstudio(selectedRadioGroup4.getText().toString());
                }
          //  }
            //Validando la pregunta 5
           // if (chkAyudaComunidad) {
                RadioGroup radioGroup5 = (RadioGroup) findViewById(R.id.radioGroup5);
                int selected5 = radioGroup5.getCheckedRadioButtonId();
                RadioButton selectedRadioGroup5 = (RadioButton) findViewById(selected5);
                if (selectedRadioGroup5 == null) {
                    //Retornar mensaje
                    dialogInterface.dismiss();
                    Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en ayuda comunidad", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                    encuestaSatisfaccionUsuario.setAyudaRecibeComunidad(selectedRadioGroup5.getText().toString());
                }
          //  }
            //Validando la pregunta 6
           // if (chkExamLaboratorio) {
                RadioGroup radioGroup6 = (RadioGroup) findViewById(R.id.radioGroup6);
                int selected6 = radioGroup6.getCheckedRadioButtonId();
                RadioButton selectedRadioGroup6 = (RadioButton) findViewById(selected6);
                if (selectedRadioGroup6 == null) {
                    //Retornar mensaje
                    dialogInterface.dismiss();
                    Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en examen laboratorio", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                    encuestaSatisfaccionUsuario.setExamenesLaboratorio(selectedRadioGroup6.getText().toString());
                }
           // }
            //Validando la pregunta 7
          //  if (chkInteresCientifico) {
                RadioGroup radioGroup7 = (RadioGroup) findViewById(R.id.radioGroup7);
                int selected7 = radioGroup7.getCheckedRadioButtonId();
                RadioButton selectedRadioGroup7 = (RadioButton) findViewById(selected7);
                if (selectedRadioGroup7 == null) {
                    //Retornar mensaje
                    dialogInterface.dismiss();
                    Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en interes cientifico", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                    encuestaSatisfaccionUsuario.setInteresCientificoPersonalP1(selectedRadioGroup7.getText().toString());
                }
          //  }
            //Validando la pregunta 8
          //  if (chkAyudarOtros) {
                RadioGroup radioGroup8 = (RadioGroup) findViewById(R.id.radioGroup8);
                int selected8 = radioGroup8.getCheckedRadioButtonId();
                RadioButton selectedRadioGroup8 = (RadioButton) findViewById(selected8);
                if (selectedRadioGroup8 == null) {
                    //Retornar mensaje
                    dialogInterface.dismiss();
                    Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en ayuda a otros", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                    encuestaSatisfaccionUsuario.setInformacionAyudaOtros(selectedRadioGroup8.getText().toString());
                }
           // }
            //Validando la pregunta 9
         //   if (chkOtras_P1) {
                EditText edtxP1_Otras = (EditText) this.findViewById(R.id.edtxP1_Otras);
                if (edtxP1_Otras.getText().toString().equals("") || edtxP1_Otras.getText() == null) {
                    Toast.makeText(getApplicationContext(), "Debe ingresar la informacion", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    encuestaSatisfaccionUsuario.setOtra(edtxP1_Otras.getText().toString());
                }*/
        //    }

            //PARTE 2
         /*   if (!chkCalidadAtencionMed
                    && !chkAtencionOportuna
                    && !chkCoordinacionHosp
                    && !chkInformacionEstadoSalud
                    && !chkEnseniaPrevEnfer
                    && !chkMejoraConoEnfer

                    && !chkMejorarTrataDengue
                    && !chkNoQuieroQuedarmeEstudio
                    && !chkP2_Otras) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar al menos una opción en la pregunta 10.2", Toast.LENGTH_LONG).show();                return;
            }*/
            //Validando la pregunta 10
           /* if (chkCalidadAtencionMed) {
                RadioGroup radioGroupP01 = (RadioGroup) findViewById(R.id.radioGroupP01);
                int selected21 = radioGroupP01.getCheckedRadioButtonId();
                RadioButton selectedRadioGroupP2_1 = (RadioButton) findViewById(selected21);
                if (selectedRadioGroupP2_1 == null) {
                    //Retornar mensaje
                    dialogInterface.dismiss();
                    Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en calidad atencion médica", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                    encuestaSatisfaccionUsuario.setCalidadAtencionMedica(selectedRadioGroupP2_1.getText().toString());
                }
            }*/
            //Validando la pregunta 11
          /*  if (chkAtencionOportuna) {
                RadioGroup radioGroupP2_2 = (RadioGroup) findViewById(R.id.radioGroupP2_2);
                int selected22= radioGroupP2_2.getCheckedRadioButtonId();
                RadioButton selectedRadioGroupP2_2 = (RadioButton) findViewById(selected22);
                if (selectedRadioGroupP2_2 == null) {
                    //Retornar mensaje
                    dialogInterface.dismiss();
                    Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en atencion oportuna", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                    encuestaSatisfaccionUsuario.setAtencionOportuna(selectedRadioGroupP2_2.getText().toString());
                }
            }
            //Validando la pregunta 12
            if (chkCoordinacionHosp) {
                RadioGroup radioGroupP2_3 = (RadioGroup) findViewById(R.id.radioGroupP2_3);
                int selected23 = radioGroupP2_3.getCheckedRadioButtonId();
                RadioButton selectedRadioGroupP2_3 = (RadioButton) findViewById(selected23);
                if (selectedRadioGroupP2_3 == null) {
                    //Retornar mensaje
                    dialogInterface.dismiss();
                    Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en coordinación hospital", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                    encuestaSatisfaccionUsuario.setBuenaCoordinacionhosp(selectedRadioGroupP2_3.getText().toString());
                }
            }
            //Validando la pregunta 13
            if (chkInformacionEstadoSalud) {
                RadioGroup radioGroupP2_4 = (RadioGroup) findViewById(R.id.radioGroupP2_4);
                int selected24 = radioGroupP2_4.getCheckedRadioButtonId();
                RadioButton selectedRadioGroupP2_4 = (RadioButton) findViewById(selected24);
                if (selectedRadioGroupP2_4 == null) {
                    //Retornar mensaje
                    dialogInterface.dismiss();
                    Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en información estado salud", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                    encuestaSatisfaccionUsuario.setInfoEstadoSalud(selectedRadioGroupP2_4.getText().toString());
                }
            }
            //Validando la pregunta 14
            if (chkEnseniaPrevEnfer) {
                RadioGroup radioGroupP2_5 = (RadioGroup) findViewById(R.id.radioGroupP2_5);
                int selected25 = radioGroupP2_5.getCheckedRadioButtonId();
                RadioButton selectedRadioGroupP2_5 = (RadioButton) findViewById(selected25);
                if (selectedRadioGroupP2_5 == null) {
                    //Retornar mensaje
                    dialogInterface.dismiss();
                    Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en Enseña prevenir enfermedades", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                    encuestaSatisfaccionUsuario.setEnseniaPrevEnfermedades(selectedRadioGroupP2_5.getText().toString());
                }
            }
            //Validando la pregunta 15
            if (chkMejoraConoEnfer) {
                RadioGroup radioGroupP2_6 = (RadioGroup) findViewById(R.id.radioGroupP2_6);
                int selected26 = radioGroupP2_6.getCheckedRadioButtonId();
                RadioButton selectedRadioGroupP2_6 = (RadioButton) findViewById(selected26);
                if (selectedRadioGroupP2_6 == null) {
                    //Retornar mensaje
                    dialogInterface.dismiss();
                    Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en información mejora los conocimientos de las enfermedades", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                    encuestaSatisfaccionUsuario.setInfoConsMejoraConocimientos(selectedRadioGroupP2_6.getText().toString());
                }
            }*/
            //Validando la pregunta 16
         /*   if (chkInteresCientificoP2) {
                RadioGroup radioGroupP2_7 = (RadioGroup) findViewById(R.id.radioGroupP2_7);
                int selected27 = radioGroupP2_7.getCheckedRadioButtonId();
                RadioButton selectedRadioGroupP2_7 = (RadioButton) findViewById(selected27);
                if (selectedRadioGroupP2_7 == null) {
                    //Retornar mensaje
                    dialogInterface.dismiss();
                    Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en interes cientifico pregunta 2", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                    encuestaSatisfaccionUsuario.setInteresCientificoPersonalP2(selectedRadioGroupP2_7.getText().toString());
                }
            }*/
            //Validando la pregunta 17
           /* if (chkMejorarTrataDengue) {
                RadioGroup radioGroupP2_8 = (RadioGroup) findViewById(R.id.radioGroupP2_8);
                int selected28 = radioGroupP2_8.getCheckedRadioButtonId();
                RadioButton selectedRadioGroupP2_8 = (RadioButton) findViewById(selected28);
                if (selectedRadioGroupP2_8 == null) {
                    //Retornar mensaje
                    dialogInterface.dismiss();
                    Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en interes cientifico pregunta 2", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                    encuestaSatisfaccionUsuario.setMejorarTratamientoDengue(selectedRadioGroupP2_8.getText().toString());
                }
            }*/
            //Validando la pregunta 18
          /*  if (chkNoQuieroQuedarmeEstudio) {
                RadioGroup radioGroupP2_9 = (RadioGroup) findViewById(R.id.radioGroupP2_9);
                int selected29 = radioGroupP2_9.getCheckedRadioButtonId();
                RadioButton selectedRadioGroupP2_9 = (RadioButton) findViewById(selected29);
                if (selectedRadioGroupP2_9 == null) {
                    //Retornar mensaje
                    dialogInterface.dismiss();
                    Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en interes cientifico pregunta 2", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                    encuestaSatisfaccionUsuario.setUnicaManeraRecibirAtencionMed(selectedRadioGroupP2_9.getText().toString());
                }
            }
            //Validando la pregunta 19
            if (chkP2_Otras) {
                EditText edtxP2_Otras = (EditText) this.findViewById(R.id.edtxP2_Otras);
                if (edtxP2_Otras.getText().toString().equals("") || edtxP2_Otras.getText() == null) {
                    Toast.makeText(getApplicationContext(), "Debe ingresar la informacion", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    encuestaSatisfaccionUsuario.setOtra(edtxP2_Otras.getText().toString());
                }
            }*/

            //PARTE 3
            //Validando la pregunta 20
            RadioGroup radioGroupP3_1 = (RadioGroup) findViewById(R.id.radioGroupP3_1);
            int selected31 = radioGroupP3_1.getCheckedRadioButtonId();
            RadioButton selectedRadioGroupP3_1 = (RadioButton) findViewById(selected31);
            if (selectedRadioGroupP3_1 == null) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una opción en la pregunta 9", Toast.LENGTH_LONG).show();
                return;
            } else {
                if (selectedRadioGroupP3_1.getText().toString().equals("Si")) {
                    encuestaSatisfaccionUsuario.setDificultadAtencion_P9("1");
                } else {
                    encuestaSatisfaccionUsuario.setDificultadAtencion_P9("0");
                }
            }

           /* if (selectedRadioGroupP3_1.getText().toString().equals("Si")
                    && !chkLejosCentroSalud
                    && !chkCostoElevadoTransporte
                    && !chkTrabajoTiempo
                    && !chkNoQueriaPasarConsultMomento
  
                    && !chkOtrasDificultades) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una opción en la pregunta 11.1", Toast.LENGTH_LONG).show();
                return;
            }*/

            // 3.1 Se activan cuando la pregunta 3 es 'Si'
            if (chkLejosCentroSalud) {
                encuestaSatisfaccionUsuario.setCentroSaludLejos_P9_1("1");
            }
            if (chkCostoElevadoTransporte) {
                encuestaSatisfaccionUsuario.setCostoTrasnporteElevado_P9_2("1");
            }
            if (chkTrabajoTiempo) {
                encuestaSatisfaccionUsuario.setTrabajoTiempo_P9_3("1");
            }
            if (chkNoQueriaPasarConsultMomento) {
                encuestaSatisfaccionUsuario.setNoQueriapasarConsulta_P9_4("1");
            }

            if (chkOtrasDificultades) {
                EditText edtxOtrasDificultades = (EditText) this.findViewById(R.id.edtxOtrasDificultades1);
                encuestaSatisfaccionUsuario.setOtrasEspecifique_P9_5(edtxOtrasDificultades.getText().toString());
            }

            //PARTE4
            //Pregunta 4
            RadioGroup radioGroupP4_1 = (RadioGroup) findViewById(R.id.radioGroupP4_1);
            int selectedP4_1 = radioGroupP4_1.getCheckedRadioButtonId();
            RadioButton selectedRadioGroupP4_1 = (RadioButton) findViewById(selectedP4_1);
            if (selectedRadioGroupP4_1 == null) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una opción en la pregunta 10", Toast.LENGTH_LONG).show();
                return;
            } else {
                if (selectedRadioGroupP4_1.getText().toString().equals("Si")) {
                    encuestaSatisfaccionUsuario.setRecomendariaAmigoFamiliar_P10("1");
                } else {
                    encuestaSatisfaccionUsuario.setRecomendariaAmigoFamiliar_P10("0");
                }
            }

            if (selectedRadioGroupP4_1.getText().toString().equals("Si")
                    && !chkAtencionRecibidaCalidad
                    && !chkRespuestaNecesidades
                    && !chkTiempoEsperaCorto
                    && !chkMejorAtencionSeguroMed
                    && !chkExamLabNecesarios
                    && !chkPocosRequisitos
                    && !chkOtrasRazonesRecomendaria) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una opción en la pregunta 10.1", Toast.LENGTH_LONG).show();
                return;
            }

            if (selectedRadioGroupP4_1.getText().toString().equals("No")
                    && !chkAtencionRecibidaMala
                    && !chkNoDanRespuestaNecesidades
                    && !chkTiempoEsperaLargo
                    && !chkMejorAtencionOtroCentro
                    && !chkDemasiadasMx
                    && !chkMuchosRequisitos
                    && !chkNoExplicanHacenMx
                    && !chkNoConfianzaEstudios
                    && !chkOtrasRazonesNoRecomendaria) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una opción en la pregunta 10.1", Toast.LENGTH_LONG).show();
                return;
            }

            //4.1 se activa cuando la respuesta de la pregunta 4 es Si
            if (chkAtencionRecibidaCalidad) {
                encuestaSatisfaccionUsuario.setAtencionPersonalMala_P10_2("1");
            }
            if (chkRespuestaNecesidades) {
                encuestaSatisfaccionUsuario.setRespNecesidadesSaludOportuna_P10_1("1");
            }
            if (chkTiempoEsperaCorto) {
                encuestaSatisfaccionUsuario.setTiempoEsperaCorto_P10_1("1");
            }
            if (chkMejorAtencionSeguroMed) {
                encuestaSatisfaccionUsuario.setMejorAtencionQueSeguro_P10_1("1");
            }
            if (chkExamLabNecesarios) {
                encuestaSatisfaccionUsuario.setExamenLabNecesarios_P10_1("1");
            }

            if (chkPocosRequisitos) {
                encuestaSatisfaccionUsuario.setPocosRequisitos_P10_1("1");
            }
            if (chkOtrasRazonesRecomendaria) {
                EditText edtxOtrasRazonesRecomendaria = (EditText) this.findViewById(R.id.edtxOtrasRazonesRecomendaria);
                encuestaSatisfaccionUsuario.setOtraP_10_1(edtxOtrasRazonesRecomendaria.getText().toString());
            }

            if (selectedRadioGroupP4_1.getText().toString().equals("No")) {}
            //4.2 se activa cuando la respuesta de la pregunta 4 es No
            if (chkAtencionRecibidaMala) {
                encuestaSatisfaccionUsuario.setAtencionPersonalMala_P10_2("1");
            }
            if (chkNoDanRespuestaNecesidades) {
                encuestaSatisfaccionUsuario.setNoDanRespNecesidades_P10_2("1");
            }
            if (chkTiempoEsperaLargo) {
                encuestaSatisfaccionUsuario.setTiempoEsperaLargo_P10_2("1");
            }
            if (chkMejorAtencionOtroCentro) {
                encuestaSatisfaccionUsuario.setMejorAtencionOtraUnidadSalud_P10_2("1");
            }
            if (chkDemasiadasMx) {
                encuestaSatisfaccionUsuario.setSolicitaDemasiadaMx_P10_2("1");
            }
            if (chkMuchosRequisitos) {
                encuestaSatisfaccionUsuario.setMuchosRequisitos_P10_2("1");
            }
            if (chkNoExplicanHacenMx) {
                encuestaSatisfaccionUsuario.setNoExplicanHacenMx_P10_2("1");
            }
            if (chkNoConfianzaEstudios) {
                encuestaSatisfaccionUsuario.setNoConfianza_P10_2("1");
            }
            if (chkOtrasRazonesNoRecomendaria) {
                EditText edtxOtrasRazonesNoRecomendaria = (EditText) this.findViewById(R.id.edtxOtrasRazonesNoRecomendaria);
                encuestaSatisfaccionUsuario.setOtraP_10_2(edtxOtrasRazonesNoRecomendaria.getText().toString());
            }

            //PARTE 5
            //Pregunta 5
            RadioGroup radioGroupP5_1 = (RadioGroup) findViewById(R.id.radioGroupP5_1);
            int selectedP5_1 = radioGroupP5_1.getCheckedRadioButtonId();
            RadioButton selectedRadioGroupP5_1 = (RadioButton) findViewById(selectedP5_1);
            if (selectedRadioGroupP5_1 == null) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una opción en la pregunta 11", Toast.LENGTH_LONG).show();
                return;
            } else {
                if (selectedRadioGroupP5_1.getText().toString().equals("Si")) {
                    encuestaSatisfaccionUsuario.setComprendeProcedimientos_P11("1");
                } else {
                    encuestaSatisfaccionUsuario.setComprendeProcedimientos_P11("0");
                }
            }

            if (selectedRadioGroupP5_1.getText().toString().equals("No")
                    && !chkNoEstuveComodoRealizarPreg
                    && !chkNoRespondieronPreguntas
                    && !chkExplicaronRapido
                    && !chkNoDejaronHacerPreguntas
                    && !chkOtroMotivosNoComprendioProcedimiento) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una opción en la pregunta 11.1", Toast.LENGTH_LONG).show();
                return;
            }
            if (chkNoEstuveComodoRealizarPreg) {
                encuestaSatisfaccionUsuario.setNoComodoRealizarPreg_P11_1("1");
            }
            if (chkNoRespondieronPreguntas) {
                encuestaSatisfaccionUsuario.setNoRespondieronPreg_P11_1("1");
            }
            if (chkExplicaronRapido) {
                encuestaSatisfaccionUsuario.setExplicacionRapida_P11_1("1");
            }
            if (chkNoDejaronHacerPreguntas) {
                encuestaSatisfaccionUsuario.setNoDejaronHacerPreg_P11_1("1");
            }
            if (chkOtroMotivosNoComprendioProcedimiento) {
                EditText edtxOtroMotivosNoComprendioProcedimiento = (EditText) this.findViewById(R.id.edtxOtroMotivosNoComprendioProcedimiento);
                encuestaSatisfaccionUsuario.setOtraP_11_1(edtxOtroMotivosNoComprendioProcedimiento.getText().toString());
            }

            //XML 6
            //Pregunta 1
            RadioGroup radioGroupP1 = (RadioGroup) findViewById(R.id.radioGroupP01);
            int selectedP1 = radioGroupP1.getCheckedRadioButtonId();
            RadioButton selectedRadioGroupP1 = (RadioButton) findViewById(selectedP1);
            if (selectedRadioGroupP1 == null) {
                //Retornar mensaje
                dialogInterface.dismiss();
                Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en la pregunta 1", Toast.LENGTH_LONG).show();
                return;
            } else {
                //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                encuestaSatisfaccionUsuario.setAtencionPersonalEstudio_P1(selectedRadioGroupP1.getText().toString());
            }

            //Pregunta 2
            RadioGroup radioGroupP2 = (RadioGroup) findViewById(R.id.radioGroupP02);
            int selectedP2 = radioGroupP2.getCheckedRadioButtonId();
            RadioButton selectedRadioGroupP2 = (RadioButton) findViewById(selectedP2);
            if (selectedRadioGroupP2 == null) {
                //Retornar mensaje
                dialogInterface.dismiss();
                Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en la pregunta 2", Toast.LENGTH_LONG).show();
                return;
            } else {
                //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                encuestaSatisfaccionUsuario.setTiempoAtencionRecibido_P2(selectedRadioGroupP2.getText().toString());
            }
            //Pregunta 3
            RadioGroup radioGroupP3 = (RadioGroup) findViewById(R.id.radioGroupP03);
            int selectedP3 = radioGroupP3.getCheckedRadioButtonId();
            RadioButton selectedRadioGroupP3 = (RadioButton) findViewById(selectedP3);
            if (selectedRadioGroupP3 == null) {
                //Retornar mensaje
                dialogInterface.dismiss();
                Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en la pregunta 3", Toast.LENGTH_LONG).show();
                return;
            } else {
                //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                encuestaSatisfaccionUsuario.setAtencionRecibidaEnfermeria_P3(selectedRadioGroupP3.getText().toString());
            }
            //Pregunta 4
            RadioGroup radioGroupP4 = (RadioGroup) findViewById(R.id.radioGroupP04);
            int selectedP4 = radioGroupP4.getCheckedRadioButtonId();
            RadioButton selectedRadioGroupP4 = (RadioButton) findViewById(selectedP4);
            if (selectedRadioGroupP4 == null) {
                //Retornar mensaje
                dialogInterface.dismiss();
                Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en la pregunta 4", Toast.LENGTH_LONG).show();
                return;
            } else {
                //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                encuestaSatisfaccionUsuario.setAtencionRecibidaDoctores_P4(selectedRadioGroupP4.getText().toString());
            }
            //Pregunta 5
            RadioGroup radioGroupP5 = (RadioGroup) findViewById(R.id.radioGroupP05);
            int selectedP5 = radioGroupP5.getCheckedRadioButtonId();
            RadioButton selectedRadioGroupP5 = (RadioButton) findViewById(selectedP5);
            if (selectedRadioGroupP5 == null) {
                //Retornar mensaje
                dialogInterface.dismiss();
                Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en la pregunta 5", Toast.LENGTH_LONG).show();
                return;
            } else {
                //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                encuestaSatisfaccionUsuario.setAmbienteDondeRecibeAtencion_P5(selectedRadioGroupP5.getText().toString());
            }
            //Pregunta 6
            RadioGroup radioGroupP6 = (RadioGroup) findViewById(R.id.radioGroupP06);
            int selectedP6 = radioGroupP6.getCheckedRadioButtonId();
            RadioButton selectedRadioGroupP6 = (RadioButton) findViewById(selectedP6);
            if (selectedRadioGroupP6 == null) {
                //Retornar mensaje
                dialogInterface.dismiss();
                Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en la pregunta 6", Toast.LENGTH_LONG).show();
                return;
            } else {
                //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                encuestaSatisfaccionUsuario.setExplicaronDiagnostico_P6(selectedRadioGroupP6.getText().toString());
            }
            //Pregunta 7
            RadioGroup radioGroupP7 = (RadioGroup) findViewById(R.id.radioGroupP07);
            int selectedP7 = radioGroupP7.getCheckedRadioButtonId();
            RadioButton selectedRadioGroupP7 = (RadioButton) findViewById(selectedP7);
            if (selectedRadioGroupP7 == null) {
                //Retornar mensaje
                dialogInterface.dismiss();
                Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en la pregunta 7", Toast.LENGTH_LONG).show();
                return;
            } else {
                //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                encuestaSatisfaccionUsuario.setExplicaronTratamiento_P7(selectedRadioGroupP7.getText().toString());
            }
         //Pregunta 8
            RadioGroup radioGroupP8 = (RadioGroup) findViewById(R.id.radioGroupP3_1);
            int selectedP8 = radioGroupP8.getCheckedRadioButtonId();
            RadioButton selectedRadioGroupP8 = (RadioButton) findViewById(selectedP8);
            if (selectedRadioGroupP8 == null) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una opción en la pregunta 8", Toast.LENGTH_LONG).show();
                return;
            } else {
                if (selectedRadioGroupP8.getText().toString().equals("Si")) {
                    encuestaSatisfaccionUsuario.setTieneArbovirusImportanciaSeg_P8("1");

                    //Pregunta 8.1
                    RadioGroup radioGroupP8_1 = (RadioGroup) findViewById(R.id.radioGroupP3_2);
                    int selectedP8_1 = radioGroupP8_1.getCheckedRadioButtonId();
                    RadioButton selectedRadioGroupP8_1 = (RadioButton) findViewById(selectedP8_1);
                    if (selectedRadioGroupP8_1 == null) {
                        Toast.makeText(getApplicationContext(), "Debe seleccionar una opción en la pregunta 8.1", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        if (selectedRadioGroupP8_1.getText().toString().equals("Si")) {
                            encuestaSatisfaccionUsuario.setExplicaronPeligrosArbovirus_P8_1("1");
                        } else {
                            encuestaSatisfaccionUsuario.setExplicaronPeligrosArbovirus_P8_1("0");
                        }
                    }
                    //Pregunta 8.2
                    RadioGroup radioGroupP8_2 = (RadioGroup) findViewById(R.id.radioGroupP3_3);
                    int selectedP8_2 = radioGroupP8_2.getCheckedRadioButtonId();
                    RadioButton selectedRadioGroupP8_2 = (RadioButton) findViewById(selectedP8_2);
                    if (selectedRadioGroupP8_2 == null) {
                        Toast.makeText(getApplicationContext(), "Debe seleccionar una opción en la pregunta 8.2", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        if (selectedRadioGroupP8_2.getText().toString().equals("Si")) {
                            encuestaSatisfaccionUsuario.setMedicoDijoCuidados_P8_2("1");
                        } else {
                            encuestaSatisfaccionUsuario.setMedicoDijoCuidados_P8_2("0");
                        }
                    }
                } else {
                    if (selectedRadioGroupP8.getText().toString().equals("No Aplica")) {
                        encuestaSatisfaccionUsuario.setTieneArbovirusImportanciaSeg_P8("2");
                    } else {
                        encuestaSatisfaccionUsuario.setTieneArbovirusImportanciaSeg_P8("0");

                    }
                }
            }/*
            //Pregunta 3
            RadioGroup radioGroupP03 = (RadioGroup) findViewById(R.id.radioGroupP03);
            int selectedP3 = radioGroupP03.getCheckedRadioButtonId();
            RadioButton selectedRadioGroupP3 = (RadioButton) findViewById(selectedP3);
            if (selectedRadioGroupP3 == null) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una opción en la pregunta 3", Toast.LENGTH_LONG).show();
                return;
            } else {
                if (selectedRadioGroupP3.getText().toString().equals("Si")) {
                    encuestaSatisfaccionUsuario.setAtencionRecibidaEnfermeria_P3("1");
                } else {
                    encuestaSatisfaccionUsuario.setAtencionRecibidaEnfermeria_P3("0");
                }
            }*/
            //Pregunta 7
          /*  if (!chkEntiendoProcedimientos
                    && !chkSatisfecho
                    && !chkComodoInformacion
                    && !chkNoComodoPreguntas) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar al menos una opción en la pregunta 7", Toast.LENGTH_LONG).show();
                return;
            }

            if (chkEntiendoProcedimientos) {
                encuestaSatisfaccionUsuario.setEntiendoProcedimientosEstudios("1");
            }
            if (chkSatisfecho) {
                encuestaSatisfaccionUsuario.setSatisfecho("1");
            }
            if (chkComodoInformacion) {
                encuestaSatisfaccionUsuario.setComodoInfoRecolectada("1");
            }
            if (chkNoComodoPreguntas) {
                encuestaSatisfaccionUsuario.setNoComodoPreguntas("1");
            }

            //Pregunta 8
            EditText edtxBrindarRecomendacion = (EditText) this.findViewById(R.id.edtxBrindarRecomendacion);
            if (!edtxBrindarRecomendacion.getText().toString().equals("") || !(edtxBrindarRecomendacion.getText() == null)) {
                encuestaSatisfaccionUsuario.setRecomendacionMejorarAtencion(edtxBrindarRecomendacion.getText().toString());
            }

            //Pregunta 9
            RadioGroup radioGroupP9 = (RadioGroup) findViewById(R.id.radioGroupP9);
            int selectedP9 = radioGroupP9.getCheckedRadioButtonId();
            RadioButton selectedRadioGroupP9 = (RadioButton) findViewById(selectedP9);
            if (selectedRadioGroupP9 == null) {
                //Retornar mensaje
                dialogInterface.dismiss();
                Toast.makeText(getApplicationContext(), "Debe seleccionar la intensidad en la pregunta 9", Toast.LENGTH_LONG).show();
                return;
            } else {
                //Setear el valor de la  variable seleccionada en el radio button al modelo a guardar
                encuestaSatisfaccionUsuario.setImportanciaRecibirInformacion(selectedRadioGroupP9.getText().toString());
            }*/

            //Caprutar el codigo del participante
            encuestaSatisfaccionUsuario.setCodigoParticipante(participante.getCodigo());
            encuestaSatisfaccionUsuario.setIdentificadoEquipo(infoMovil.getDeviceId());
            encuestaSatisfaccionUsuario.setEstado('0');
            encuestaSatisfaccionUsuario.setPasivo('0');
            Date dateCreado = new Date();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String changedDate = dateFormat.format(dateCreado);

            encuestaSatisfaccionUsuario.setCreado(changedDate);
            encuestaSatisfaccionUsuario.setUsuarioRegistro(username);

            //MovilInfo movilInfo = new MovilInfo();
            //movilInfo.setDeviceid(infoMovil.getDeviceId());
            //movilInfo.setEstado(String.valueOf(Constants.STATUS_NOT_SUBMITTED));

            /*movilInfo.setUsername(username);
            movilInfo.setToday(new Date());*/
            //encuestaSatisfaccionUsuario.setMovilInfo(movilInfo);

            encuestaSatisfaccionUsuario.setNombre1Tutor(participante.getNombre1Tutor());
            encuestaSatisfaccionUsuario.setNombre2Tutor(participante.getNombre2Tutor());
            encuestaSatisfaccionUsuario.setApellido1Tutor(participante.getApellido1Tutor());
            encuestaSatisfaccionUsuario.setApellido2Tutor(participante.getApellido2Tutor());
            encuestaSatisfaccionUsuario.setCodigoCasa(participante.getCasa().getCodigo());
            //encuestaSatisfaccionUsuario.setCasaChf(participante.getProcesos().getCasaCHF());
            encuestaSatisfaccionUsuario.setEstudio("A2CARES");

            encuestaSatisfaccionUsuario.setEstado(Constants.STATUS_NOT_SUBMITTED);
            encuestaSatisfaccionUsuario.setUsuarioRegistro(username);
            encuestaSatisfaccionUsuario.setFechaRegistro(null);
            encuestaSatisfaccionUsuario.setIdentificadoEquipo(infoMovil.getDeviceId());

            estudiosAdapter.crearEncuestaSatisfaccionUsuarioValues(encuestaSatisfaccionUsuario);

            participante.getProcesos().setEsatUsuario(Constants.NOKEYSND);
            participante.getProcesos().setRecordDate(new Date());
            participante.getProcesos().setRecordUser(username);
            participante.getProcesos().setDeviceid(infoMovil.getDeviceId());
            participante.getProcesos().setEstado('0');
            participante.getProcesos().setPasive('0');
            estudiosAdapter.editarParticipanteProcesos(participante.getProcesos());

            showToast(getString(R.string.success));

            //ParticipanteProcesos procesos = participante.getProcesos();
            //procesos.setEsatUsuario(Constants.NOKEYSND);
            //procesos.setMovilInfo(movilInfo);
            //estudiosAdapter.editarParticipanteProcesos(procesos);

            Bundle arguments = new Bundle();
            arguments.putSerializable(Constants.PARTICIPANTE , participante);
            Intent i = new Intent(getApplicationContext(),
                    MenuParticipanteActivity.class);
            i.putExtra(Constants.VISITA_EXITOSA, visExitosa);
            i.putExtras(arguments);
            startActivity(i);
            finish();

            /*Intent i = new Intent(getApplicationContext(),
                    MenuParticipanteActivity.class);
            i.putExtra(Constants.COD_PARTICIPANTE, participante.getCodigo());
            i.putExtra(Constants.VISITA_EXITOSA, visExitosa);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_LONG);
            toast.show();
            finish();*/

        } catch (Exception ex) {
            ex.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(), ex.getLocalizedMessage(), Toast.LENGTH_LONG);
            toast.show();
        } finally {
            //Cierra la base de datos
            if (estudiosAdapter!=null)
                estudiosAdapter.close();
        }
    }
}
