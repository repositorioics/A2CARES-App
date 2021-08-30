package ni.org.ics.a2cares.app.utils;

import java.util.Date;
import java.util.UUID;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

public class DeviceInfo {
	
	private final Context mContext;
	String deviceId;
	
	
    public DeviceInfo(Context context) {
        this.mContext = context;
        getDeviceId();
    }
	
	/*public String getDeviceId(){
	    TelephonyManager mTelephonyMgr;
	    WifiManager wifi;
	    mTelephonyMgr = (TelephonyManager)mContext.
	        getSystemService(Context.TELEPHONY_SERVICE); 
	    
	    wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
	
	    deviceId = mTelephonyMgr.getDeviceId();
	    
	    if (deviceId == null){
	    	deviceId = wifi.getConnectionInfo().getMacAddress();
	    }
	    return deviceId;
	}*/

	public String getDeviceId() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
		{
			deviceId = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
		} else {
			final TelephonyManager mTelephony = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				if (mContext.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
					return "";
				}
			}
			assert mTelephony != null;
			if (mTelephony.getDeviceId() != null)
			{
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
				{
					deviceId = mTelephony.getImei();
				}else {
					deviceId = mTelephony.getDeviceId();
				}
			} else {
				deviceId = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
			}
		}
		return deviceId;
	}

	public String getId(){    
	    Date fecha = new Date();
	    UUID deviceUuid = new UUID(deviceId.hashCode(),fecha.hashCode());
	    return deviceUuid.toString();
	}

}
