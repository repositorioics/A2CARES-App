package ni.org.ics.a2cares.app.entomologia.adapters;


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

public class MenuEntomologiaAdapter extends ArrayAdapter<String> {

	private final String[] values;
	private final int totalCuestionarios;
	private final int totalCuestionariosPC;
	public MenuEntomologiaAdapter(Context context, int textViewResourceId,
                                  String[] values, int totalCuestionarios, int totalCuestionariosPC) {
		super(context, textViewResourceId, values);
		this.values = values;
		this.totalCuestionarios = totalCuestionarios;
		this.totalCuestionariosPC = totalCuestionariosPC;
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
		switch (position) {
			case 0:
				img = getContext().getResources().getDrawable(R.mipmap.ic_gps);
				textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
				break;
			case 1:
				img = getContext().getResources().getDrawable(R.mipmap.ic_review);
				textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
				textView.setText(values[position]+" ("+ totalCuestionarios+")");
				break;
			case 2:
				img = getContext().getResources().getDrawable(R.mipmap.ic_gps);
				textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
				break;
			case 3:
				img = getContext().getResources().getDrawable(R.mipmap.ic_review);
				textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
				textView.setText(values[position]+" ("+ totalCuestionariosPC+")");
				break;
			case 4:
				img = getContext().getResources().getDrawable(R.mipmap.ic_download);
				textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
				break;
			case 5:
				img = getContext().getResources().getDrawable(R.mipmap.ic_upload);
				textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
				break;
			case 6:
				img = getContext().getResources().getDrawable(R.mipmap.ic_house);
				textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
				break;
			case 7:
				img = getContext().getResources().getDrawable(R.mipmap.icon_asistencia1);
				textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
				break;
			default:
				img = getContext().getResources().getDrawable(R.mipmap.ic_menu_back);
				textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
				break;
		}
		return v;
	}
}
