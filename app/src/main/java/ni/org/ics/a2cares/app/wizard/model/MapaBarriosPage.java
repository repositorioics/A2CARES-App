package ni.org.ics.a2cares.app.wizard.model;


import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.util.ArrayList;

import ni.org.ics.a2cares.app.wizard.ui.BarriosMapFragment;

public class MapaBarriosPage extends Page {

	protected int mUnidadSalud;

	public MapaBarriosPage(ModelCallbacks callbacks, String title, String hintText, String textColor, boolean isVisible) {
		super(callbacks, title, hintText, textColor, isVisible, true);
	}

	@Override
	public Fragment createFragment() {
		return BarriosMapFragment.create(getKey());
	}
	

	@Override
	public void getReviewItems(ArrayList<ReviewItem> dest) {
		dest.add(new ReviewItem(getTitle(), mData.getString(SIMPLE_DATA_KEY),
				getKey()));

	}


	@Override
	public boolean isCompleted() {
		return !TextUtils.isEmpty(mData.getString(SIMPLE_DATA_KEY));
	}

	public MapaBarriosPage setValue(String value) {
		mData.putString(SIMPLE_DATA_KEY, value);
		return this;
	}

	public int getmUnidadSalud() {
		return mUnidadSalud;
	}

	public void setmUnidadSalud(int mUnidadSalud) {
		this.mUnidadSalud = mUnidadSalud;
	}
}
