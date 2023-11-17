package ni.org.ics.a2cares.app;

import android.app.Application;
import android.content.Context;

public class ControAsistenciaGPSApplication extends Application{
	
	private String latitudapp;
	private String longitudapp;
	private static Context mContext;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
	}
	
	public static Context getContext(){
        return mContext;
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
