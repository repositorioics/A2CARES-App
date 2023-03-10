package ni.org.ics.a2cares.app.ui.task;

import android.os.AsyncTask;

import ni.org.ics.a2cares.app.listeners.UploadListener;

public abstract class UploadTask extends
AsyncTask<String, String, String> {

	protected static final String TAG = UploadTask.class.getSimpleName();

	protected UploadListener mStateListener;
	@Override
	protected void onProgressUpdate(String... values) {
		synchronized (this) {
			if (mStateListener != null) {
				// update progress and total
				mStateListener.progressUpdate(values[0], Integer.valueOf(values[1]), Integer.valueOf(values[2]));
			}
		}

	}

	@Override
	protected void onPostExecute(String result) {
		synchronized (this) {
			if (mStateListener != null)
				mStateListener.uploadComplete(result);
		}
	}

	public void setUploadListener(UploadListener sl) {
		synchronized (this) {
			mStateListener = sl;
		}
	}
}

