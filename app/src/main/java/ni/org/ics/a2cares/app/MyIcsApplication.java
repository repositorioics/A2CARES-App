package ni.org.ics.a2cares.app;

import android.app.Application;
import android.content.Context;

public class MyIcsApplication extends Application{
	
	private String passApp;
	private static Context mContext;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
	}
	
	public static Context getContext(){
        return mContext;
    }

	public String getPassApp() {
		return passApp;
	}

	protected void setPassApp(String passApp) {
		this.passApp = passApp;
	}

}
