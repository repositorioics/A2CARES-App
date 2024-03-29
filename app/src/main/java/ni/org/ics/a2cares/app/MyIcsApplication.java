package ni.org.ics.a2cares.app;

import android.app.Application;
import android.content.Context;

public class MyIcsApplication extends Application{
	
	private String passApp;
	private static Context mContext;
	private String latitudapp;
	private String longitudapp;
	
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

	public String getLatitudapp() {
		return latitudapp;
	}

	public void setLatitudapp(String latitudapp) {
		this.latitudapp = latitudapp;
	}

	public String getLongitudapp() {
		return longitudapp;
	}

	public void setLongitudapp(String longitudapp) {
		this.longitudapp = longitudapp;
	}

}
