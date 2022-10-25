package ni.org.ics.a2cares.app.wizard.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.ui.activities.maps.CoordenadasMapActivity;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.wizard.model.MapaBarriosPage;
import ni.org.ics.a2cares.app.wizard.model.Page;

public class BarriosMapFragment extends Fragment {
	protected static final String ARG_KEY = "key";
	protected static final int PART_CAPTURE = 2;

	private PageFragmentCallbacks mCallbacks;
	private String mKey;
	private Page mPage;

	protected TextView mTitleTextInput;
	protected TextView mHintTextInput;
	protected EditText mEditTextInput;
	protected ImageButton mButtonBarcode;

	public static BarriosMapFragment create(String key) {
		Bundle args = new Bundle();
		args.putString(ARG_KEY, key);

		BarriosMapFragment f = new BarriosMapFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle args = getArguments();
		mKey = args.getString(ARG_KEY);
		mPage = mCallbacks.onGetPage(mKey);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_page_coordenates,
				container, false);
		
		mTitleTextInput = (TextView) rootView.findViewById(android.R.id.title);
		mTitleTextInput.setText(mPage.getTitle());
		mTitleTextInput.setTextColor(Color.parseColor(mPage.getTextColor()));
		
		mHintTextInput = (TextView) rootView.findViewById(R.id.label_hint);
		mHintTextInput.setText(mPage.getHint());
		mHintTextInput.setTextColor(Color.parseColor(mPage.getmHintTextColor()));
		
		mEditTextInput = (EditText) rootView.findViewById(R.id.editTextInput);
		mEditTextInput.setText(mPage.getData().getString(Page.SIMPLE_DATA_KEY));
		mEditTextInput.setEnabled(false);
		mButtonBarcode = (ImageButton) rootView.findViewById(R.id.select_button);
		mButtonBarcode.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity().getApplicationContext(),
						CoordenadasMapActivity.class);
				i.putExtra(Constants.COD_PARTICIPANTE, mPage.getCodigoParticipante());
				i.putExtra(Constants.MENU_ENTO, mPage.isEsEntomologia());
				MapaBarriosPage page = (MapaBarriosPage) mPage;
				i.putExtra(Constants.UBICACION, page.getmUnidadSalud());
				startActivityForResult(i, PART_CAPTURE);
			}
		});
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (!(activity instanceof PageFragmentCallbacks)) {
			throw new ClassCastException(
					"Activity must implement PageFragmentCallbacks");
		}

		mCallbacks = (PageFragmentCallbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == PART_CAPTURE && intent != null) {
			String codigo = intent.getStringExtra("CODE_RESULT");
			mEditTextInput.setText(codigo);
			mPage.getData().putString(Page.SIMPLE_DATA_KEY,codigo);
			mPage.notifyDataChanged();
		}
		super.onActivityResult(requestCode, resultCode, intent);

	}
}
