package asyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;

import quadvision.serviceprovider.R;

/**
 * Created by abhishek on 25-06-2015.
 */
public class GcmKeyRegisterAsyncTask extends AsyncTask<HashMap<String,String>, Integer, String> {

    ProgressDialog progress = null;

    Activity activity;
    public GcmKeyRegisterAsyncTask(Activity activity) {

        this.activity=activity;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
        progress = new ProgressDialog(activity);
        progress.setMessage("please wait... ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

    }

    @Override
    protected String doInBackground(HashMap<String,String>... parameters) {
        publishProgress(0);
        Log.d("register-hash-data",parameters[0].toString());
        try
        {
            HttpClient client = new DefaultHttpClient();

            String url=activity.getResources().getString(R.string.gcm_key_register)+"?profile_id="+parameters[0].get("profile_id");
            HttpPost post = new HttpPost(url);

            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            entityBuilder.addTextBody("key", parameters[0].get("key"));


            HttpEntity entity = entityBuilder.build();

            post.setEntity(entity);


            HttpResponse response = client.execute(post);

            HttpEntity httpEntity = response.getEntity();

            String result = EntityUtils.toString(httpEntity);

            Log.d("registration", result);
            return result;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        progress.hide();
        Log.d("GcmKeyRegisterAsyncTask",result);

    }


}
