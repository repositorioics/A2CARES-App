package ni.org.ics.a2cares.app.ui.adapters;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.domain.core.Participante;

public class MenuParticipanteAdapter extends ArrayAdapter<String> {

	private final String[] values;
    private boolean pendienteEncuestaCasa = false;
    private boolean pendienteEncuestaPeso = false;
    private boolean pendienteEncuestaParticipante = false;
    private boolean pendientenMuestras = false;
    private boolean visitaExitosa = false;

    //private final int OPCION_CONSULTA = 0;
    private final int OPCION_VISITA = 0;
    private final int OPCION_ENCUESTA_CASA = 1;
    private final int OPCION_ENCUESTA_PARTICIPANTE = 2;
    //private final int OPCION_ENCUESTA_DATOSPARTO = 2;
    private final int OPCION_ENCUESTA_PESOTALLA = 3;
    //private final int OPCION_ENCUESTA_LACTANCIA = 3;
    private final int OPCION_ENCUESTA_MUESTRAS = 4;
    //private final int OPCION_ENCUESTA_PARTICIPANTESA = 5;
    private final int OPCION_IR_CASA = 5;

    private final Context context;
    private final Participante participante;
	public MenuParticipanteAdapter(Context context, int textViewResourceId,
                                   String[] values, Participante participante,  boolean pendienteEncuestaCasa, boolean pendienteEncuestaParticipante, boolean pendienteEncuestaPeso, boolean pendientenMuestras, boolean visitaExitosa) {
		super(context, textViewResourceId, values);
        this.context = context;
		this.values = values;
        this.participante = participante;
        this.pendienteEncuestaCasa = pendienteEncuestaCasa;
        this.pendienteEncuestaParticipante = pendienteEncuestaParticipante;
        this.pendienteEncuestaPeso = pendienteEncuestaPeso;
        this.pendientenMuestras = pendientenMuestras;
        this.visitaExitosa = visitaExitosa;
	}


    @Override
    public boolean isEnabled(int position) {
        // Disable the first item of GridView
        //a partir del MA2018 no se deberia ingresar información en la pantalla de Familia
        boolean habilitado = true;
        switch (position){
            /*case OPCION_CONSULTA:
                habilitado = true;
                break;*/
            case OPCION_VISITA:
                habilitado = !visitaExitosa;
                break;
            case OPCION_ENCUESTA_CASA:
                habilitado = visitaExitosa && pendienteEncuestaCasa;
                break;
            case OPCION_ENCUESTA_PARTICIPANTE:
                habilitado = visitaExitosa && pendienteEncuestaParticipante;
                break;
            case OPCION_ENCUESTA_PESOTALLA:
                habilitado = visitaExitosa && pendienteEncuestaPeso;
                break;
            case OPCION_ENCUESTA_MUESTRAS:
                habilitado = visitaExitosa && pendientenMuestras;
                break;
            case OPCION_IR_CASA:
                habilitado = true;
                break;
            default: break;
        }
        return habilitado;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.menu_item_2, null);
		}
		TextView textView = (TextView) v.findViewById(R.id.label);
		textView.setTypeface(null, Typeface.BOLD);
		textView.setText(values[position]);
		textView.setTextColor(Color.BLACK);

		// Change icon based on position
		Drawable img = null;
		switch (position){
            /*// se va a ocultar mientras no se desarrolla esta funcionalidad
		    case OPCION_CONSULTA:
                img=getContext().getResources().getDrawable(R.mipmap.ic_clinical);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);

                break;*/
            case OPCION_VISITA:
                if (visitaExitosa) {
                    textView.setTextColor(Color.GRAY);
                } else {
                    textView.setTextColor(Color.RED);
                }
                img=getContext().getResources().getDrawable(R.mipmap.ic_visit);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                break;
            case OPCION_ENCUESTA_CASA:
                if (visitaExitosa) {
                    if (!pendienteEncuestaCasa) {
                        textView.setTextColor(Color.BLUE);
                        textView.setText(textView.getText() + "\n" + context.getResources().getString(R.string.done));
                    } else {
                        textView.setTextColor(Color.RED);
                        textView.setText(textView.getText() + "\n" + context.getResources().getString(R.string.pending));
                    }
                } else {
                    textView.setTextColor(Color.GRAY);
                    //textView.setText(textView.getText() + "\n" + context.getResources().getString(R.string.notavailable));
                }
                img=getContext().getResources().getDrawable(R.mipmap.ic_house_survey);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                break;
            case OPCION_ENCUESTA_PARTICIPANTE:
                //textView.setTextColor(Color.GRAY);
                //textView.setText(textView.getText()+"\n"+ context.getResources().getString(R.string.notavailable));
                if (visitaExitosa) {
                    if (!pendienteEncuestaParticipante) {
                        textView.setTextColor(Color.BLUE);
                        textView.setText(textView.getText() + "\n" + context.getResources().getString(R.string.done));
                    } else {
                        textView.setText(textView.getText() + "\n" + context.getResources().getString(R.string.pending));
                        textView.setTextColor(Color.RED);
                    }
                } else {
                    textView.setTextColor(Color.GRAY);
                    //textView.setText(textView.getText() + "\n" + context.getResources().getString(R.string.notavailable));
                }
                img=getContext().getResources().getDrawable(R.mipmap.ic_part_survey);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                break;
            case OPCION_ENCUESTA_PESOTALLA:
                if (visitaExitosa) {
                    if (!pendienteEncuestaPeso) {
                        textView.setTextColor(Color.BLUE);
                        textView.setText(textView.getText() + "\n" + context.getResources().getString(R.string.done));
                    } else {
                        textView.setText(textView.getText() + "\n" + context.getResources().getString(R.string.pending));
                        textView.setTextColor(Color.RED);
                    }
                } else {
                    textView.setTextColor(Color.GRAY);
                    //textView.setText(textView.getText() + "\n" + context.getResources().getString(R.string.notavailable));
                }
                //textView.setTextColor(Color.GRAY);
                //textView.setText(textView.getText()+"\n"+ context.getResources().getString(R.string.notavailable));
                img=getContext().getResources().getDrawable(R.mipmap.ic_weight);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                break;
            case OPCION_ENCUESTA_MUESTRAS:

                img=getContext().getResources().getDrawable(R.mipmap.ic_samples);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                if (visitaExitosa) {
                    if (!pendientenMuestras) {
                        textView.setTextColor(Color.BLUE);
                        textView.setText(textView.getText() + "\n" + context.getResources().getString(R.string.done));
                    } else {
                        textView.setText(textView.getText() + "\n" + context.getResources().getString(R.string.pending));
                        textView.setTextColor(Color.RED);
                    }
                } else {
                    textView.setTextColor(Color.GRAY);
                    //textView.setText(textView.getText() + "\n" + context.getResources().getString(R.string.notavailable));
                }
                break;
             case OPCION_IR_CASA:
                img=getContext().getResources().getDrawable(R.mipmap.ic_house);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                break;
		default:
			img=getContext().getResources().getDrawable(R.mipmap.ic_help);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
		}
		return v;
	}
}
