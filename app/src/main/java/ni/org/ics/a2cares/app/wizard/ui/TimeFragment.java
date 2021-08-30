package ni.org.ics.a2cares.app.wizard.ui;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;

import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.wizard.model.Page;


public class TimeFragment extends Fragment {
	protected static final String ARG_KEY = "key";
	protected static final int TIME_DIALOG_ID = 99;

	private PageFragmentCallbacks mCallbacks;
	private String mKey;
	private Page mPage;
	
	protected TextView mTitleTextInput;
	protected TextView mHintTextInput;
	protected EditText mEditTextInput;
	protected ImageButton mButtonBarcode;
	
	private int hora;
	private int minuto;

	public static TimeFragment create(String key) {
		Bundle args = new Bundle();
		args.putString(ARG_KEY, key);
		TimeFragment f = new TimeFragment();
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
		View rootView = inflater.inflate(R.layout.fragment_page_newdate,
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
		mButtonBarcode = (ImageButton) rootView.findViewById(R.id.changedate_button);
		mButtonBarcode.setImageResource(R.mipmap.ic_time);
		mButtonBarcode.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(TIME_DIALOG_ID);
			}
		});
		return rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mEditTextInput.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && editable.length() >= 0 && isResumed()) {
                    mPage.getData().putString(Page.SIMPLE_DATA_KEY, editable.toString());
                    mPage.notifyDataChanged();
                }
            }

		});
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

	private void createDialog(int dialog) {
        try {
            switch (dialog) {
                case TIME_DIALOG_ID:
                    Calendar calendar = Calendar.getInstance();
                    //Corrección bug no mostraba modal para seleccionar fecha en moviles  J5 pro con Android 8 (Oreo)
                    hora = calendar.get(Calendar.HOUR_OF_DAY);
                    minuto = calendar.get(Calendar.MINUTE);

                    final TimePickerDialog tpD = new TimePickerDialog(this.getActivity(), android.R.style.Theme_Holo_Dialog, timePickerListener, hora, minuto, true);
					tpD.show();

                default:
                    break;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
	}
	
	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
			hora = hourOfDay;
			minuto = minute;
			String horaSeleccionada = (timePicker != null) ? String.valueOf(hourOfDay < 10 ? "0" + hourOfDay : hourOfDay) + ":" + String.valueOf(minute < 10 ? "0" + minute : minute) : null;
			mEditTextInput.setText(horaSeleccionada);
			mPage.getData().putString(Page.SIMPLE_DATA_KEY,mEditTextInput.getText().toString());
			mPage.notifyDataChanged();
		}
	};
}
