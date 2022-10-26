package ni.org.ics.a2cares.app.entomologia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.entomologia.domain.CuestionarioHogar;

import java.text.DateFormat;
import java.util.List;

public class CuestionarioHogarAdapter extends ArrayAdapter<CuestionarioHogar> {

	DateFormat mediumDf = DateFormat.getDateInstance(DateFormat.MEDIUM);


	public CuestionarioHogarAdapter(Context context, int textViewResourceId,
                                    List<CuestionarioHogar> items) {
		super(context, textViewResourceId, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.complex_list_item , null);
		}
		CuestionarioHogar encuesta = getItem(position);
		if (encuesta != null) {

			TextView textView = (TextView) v.findViewById(R.id.name_text);
			if (textView != null) {
                String text = "";
                if (encuesta.getCodigoVivienda()!= null)
                    text = "Casa "+encuesta.getCodigoVivienda();

                textView.setText(text);
			}

			textView = (TextView) v.findViewById(R.id.identifier_text);
			if (textView != null) {
				textView.setText(mediumDf.format(encuesta.getFechaCuestionario()));
			}
		}
		return v;
	}
}
