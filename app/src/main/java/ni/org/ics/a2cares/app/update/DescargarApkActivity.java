package ni.org.ics.a2cares.app.update;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.dto.LogUpdateApk;
import ni.org.ics.a2cares.app.preferences.PreferencesActivity;
import ni.org.ics.a2cares.app.utils.DeviceInfo;

public class DescargarApkActivity extends Activity {
    public ProgressDialog PD_CREATE;
    private Context CONTEXT;
    private SharedPreferences settings;
    private String strUrlContext;
    private String username;
    private String password;
    private String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.CONTEXT = this;
        settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        strUrlContext =
                settings.getString(PreferencesActivity.KEY_SERVER_URL, this.getString(R.string.default_server_url));
        UpdateApk downloadAndInstall = new UpdateApk();
        downloadAndInstall.setContext(getApplicationContext());
        downloadAndInstall.execute(getResources().getString(R.string.update_app_preferences));
        url =
                settings.getString(PreferencesActivity.KEY_SERVER_URL, this.getString(R.string.default_server_url));
        username =
                settings.getString(PreferencesActivity.KEY_USERNAME,
                        null);

        password =
                ((MyIcsApplication) this.getApplication()).getPassApp();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }




    /***************************************************/
    /********************* LogUpdateApk ************************/
    /***************************************************/
    // url, username, password
    protected String enviarLogActualizacion(String url, String username,
                                            String password) {
        try {
            DeviceInfo infoMovil = new DeviceInfo(this);
            List<LogUpdateApk> logs = new ArrayList<>();
            LogUpdateApk log = new LogUpdateApk();
            log.setCodigo(infoMovil.getId());
            log.setDispositivo(infoMovil.getDeviceId());
            log.setUsuarioActualiza(username);
            log.setFechaActualizacion(new Date());
            logs.add(log);
            // La URL de la solicitud POST
            final String urlRequest = url + "/movil/logUpdateApk";
            LogUpdateApk[] envio = logs.toArray(new LogUpdateApk[logs.size()]);
            HttpHeaders requestHeaders = new HttpHeaders();
            HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            requestHeaders.setAuthorization(authHeader);
            HttpEntity<LogUpdateApk[]> requestEntity =
                    new HttpEntity<LogUpdateApk[]>(envio, requestHeaders);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
            // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
            ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                    String.class);
            return response.getBody();

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public class UpdateApk extends AsyncTask<String,Void,Void> {
        int status = 0;

        private Context context;
        public void setContext(Context context){
            this.context = context;
        }

        public void onPreExecute() {
            PD_CREATE = new ProgressDialog(CONTEXT);
            PD_CREATE.setTitle(getResources().getString(R.string.downloadingApp));
            PD_CREATE.setMessage(getResources().getString(R.string.pleasewait));
            PD_CREATE.setCancelable(false);
            PD_CREATE.setIndeterminate(true);
            PD_CREATE.show();
        }

        @Override
        protected Void doInBackground(String... arg0) {
            try {
                URL url = new URL(strUrlContext+getString(R.string.url_apk));
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.connect();

                String PATH = Objects.requireNonNull(context.getExternalFilesDir(null)).getAbsolutePath();
                File file = new File(PATH);
                boolean isCreate = file.mkdirs();
                File outputFile = new File(file, context.getString(R.string.apk_name));
                if (outputFile.exists()) {
                    boolean isDelete = outputFile.delete();
                }
                FileOutputStream fos = new FileOutputStream(outputFile);

                InputStream is = c.getInputStream();

                byte[] buffer = new byte[1024];
                int len1 = 0;
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                }
                fos.flush();
                fos.close();
                is.close();

                //Llamando a la funcion que instala el APK
                ApkInstaller.installApplication(CONTEXT);

            } catch (FileNotFoundException fnfe) {
                status = 1;
                Log.e("File", "FileNotFoundException! " + fnfe);
            } catch (ConnectException fnfe) {
                status = 2;
                Log.e("Error", "ConnectException! " + fnfe);
            }
            catch(Exception e)
            {
                Log.e("UpdateAPP", "Exception " + e);
            }
            finally {
                enviarLogActualizacion(url, username, password);
            }
            return null;
        }

        public void onPostExecute(Void unused) {
            //progressDialog.dismiss();
            PD_CREATE.dismiss();
            if(status == 1)
                Toast.makeText(CONTEXT,"Apk no disponible",Toast.LENGTH_LONG).show();

            if(status == 2)
                Toast.makeText(CONTEXT,"No se pudo conectar con el servidor",Toast.LENGTH_LONG).show();

            finish();
        }
    }
}
