/*
 * Copyright 2012 Roman Nurik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ni.org.ics.a2cares.app.wizard.model;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * Represents a single page in the wizard.
 */
public abstract class Page implements PageTreeNode {
    /**
     * The key into {@link #getData()} used for wizards with simple (single) values.
     */
    public static final String SIMPLE_DATA_KEY = "_";

    protected ModelCallbacks mCallbacks;

    /**
     * Current wizard values/selections.
     */
    protected Bundle mData = new Bundle();
    protected String mTitle;
    protected String mHint;
    protected String mTextColor;
    protected String mHintTextColor;
    protected boolean mRequired = false;
    protected String mParentKey;
    protected boolean mVisible = false;
    protected boolean mEnabled = true;
    private String codigoParticipante;
    private int mCodePosicion; //para el fragment de codigo de barra, si es -1 toma el codigo completo, sino toma el segmento de codigo indicado en la posicion
    private boolean esEntomologia = false;

    protected Page(ModelCallbacks callbacks, String title, String hintText, String textColor, boolean visible, boolean enabled) {
        mCallbacks = callbacks;
        mTitle = title;
        mHint= hintText;
        mTextColor=textColor;
        mHintTextColor="#66000000";
        mVisible = visible;
        mEnabled = enabled;
        mCodePosicion = -1;
    }

    public Bundle getData() {
        return mData;
    }

    public String getTitle() {
        return mTitle;
    }

    public boolean isRequired() {
        return mRequired;
    }

    void setParentKey(String parentKey) {
        mParentKey = parentKey;
    }

    @Override
    public Page findByKey(String key) {
        return getKey().equals(key) ? this : null;
    }

    @Override
    public void flattenCurrentPageSequence(ArrayList<Page> dest) {
        dest.add(this);
    }

    public abstract Fragment createFragment();

    public String getKey() {
        return (mParentKey != null) ? mParentKey + ":" + mTitle : mTitle;
    }

    public abstract void getReviewItems(ArrayList<ReviewItem> dest);

    public boolean isCompleted() {
        return true;
    }

    public void resetData(Bundle data) {
        mData = data;
        notifyDataChanged();
    }

    public void notifyDataChanged() {
        mCallbacks.onPageDataChanged(this);
    }

    public Page setRequired(boolean required) {
        mRequired = required;
        return this;
    }

	public String getHint() {
		return mHint;
	}

	public void setHint(String mHint) {
		this.mHint = mHint;
	}

	public String getTextColor() {
		return mTextColor;
	}

	public void setTextColor(String mTextColor) {
		this.mTextColor = mTextColor;
	}

	public boolean ismVisible() {
		return mVisible;
	}

	public void setmVisible(boolean mVisible) {
		this.mVisible = mVisible;
	}

	public String getmHintTextColor() {
		return mHintTextColor;
	}

	public void setmHintTextColor(String mHintTextColor) {
		this.mHintTextColor = mHintTextColor;
	}

    public boolean ismEnabled() {
        return mEnabled;
    }

    public void setmEnabled(boolean mEnabled) {
        this.mEnabled = mEnabled;
    }

	public String getCodigoParticipante() {
		return codigoParticipante;
	}

	public void setCodigoParticipante(String codigoParticipante) {
		this.codigoParticipante = codigoParticipante;
	}

    public int getmCodePosicion() {
        return mCodePosicion;
    }

    public void setmCodePosicion(int mCodePosicion) {
        this.mCodePosicion = mCodePosicion;
    }

    public boolean isEsEntomologia() {
        return esEntomologia;
    }

    public void setEsEntomologia(boolean esEntomologia) {
        this.esEntomologia = esEntomologia;
    }
}
