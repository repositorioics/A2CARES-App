package ni.org.ics.a2cares.app.wizard.ui;

import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.wizard.model.Page;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class LabelFragment extends Fragment {
	protected static final String ARG_KEY = "key";

	private PageFragmentCallbacks mCallbacks;
	private String mKey;
	private Page mPage;

	protected TextView mTitleTextInput;
	protected TextView mHintTextInput;


	public static LabelFragment create(String key) {
		Bundle args = new Bundle();
		args.putString(ARG_KEY, key);

		LabelFragment f = new LabelFragment();
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
		View rootView = inflater.inflate(R.layout.fragment_page_label,
				container, false);

		mTitleTextInput = (TextView) rootView.findViewById(android.R.id.title);
		mTitleTextInput.setText(Html.fromHtml(mPage.getTitle()));
		mTitleTextInput.setTextColor(Color.parseColor(mPage.getTextColor()));
		
		mHintTextInput = (TextView) rootView.findViewById(R.id.label_hint);
		mHintTextInput.setText(mPage.getHint());
        mHintTextInput.setTextSize(35);
		mHintTextInput.setTextColor(Color.parseColor(mPage.getmHintTextColor()));
		
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
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}
}
