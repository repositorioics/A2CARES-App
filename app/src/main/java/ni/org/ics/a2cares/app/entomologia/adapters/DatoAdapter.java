package ni.org.ics.a2cares.app.entomologia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.entomologia.domain.Dato;

import java.util.List;

public class DatoAdapter extends ArrayAdapter<Dato> {

	public DatoAdapter(Context context, int textViewResourceId,
                       List<Dato> items) {
		super(context, textViewResourceId, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.list_item_review , null);
		}
		Dato dato = getItem(position);
		if (dato != null) {

			TextView textView = (TextView) v.findViewById(R.id.text1);
			if (textView != null) {
				textView.setText(dato.getNombre());
			}

			textView = (TextView) v.findViewById(R.id.text2);
			if (textView != null) {
				textView.setText(dato.getValor());
			}
		}
		return v;
	}
}
