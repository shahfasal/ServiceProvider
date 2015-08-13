package asyncTasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Random;

import quadvision.serviceprovider.R;

public class ComplaintStatusAyncTask extends AsyncTask<String, Integer, String> {
    ProgressDialog progress = null;
    Bitmap image;
    Activity activity;

    public ComplaintStatusAyncTask(Activity activity) {
        // TODO Auto-generated constructor stub
        this.activity = activity;
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
        progress = new ProgressDialog(activity);
        progress.setMessage("updating complaint..... ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

    }

    @Override
    protected String doInBackground(String... params) {
        publishProgress(0);
        try {
            HttpClient client = new DefaultHttpClient();

            String profile = activity.getSharedPreferences("cache", Context.MODE_PRIVATE).getString("profile", null);
            String url = activity.getResources().getString(R.string.complaint_status_chnge);
            HttpPost httpPost = new HttpPost(url);
            JSONObject loginObject = new JSONObject();
            loginObject.put("complaint_id", params[0]);
            loginObject.put("complaint_status", params[1]);
            loginObject.put("profile", profile);

            httpPost.setEntity(new StringEntity(loginObject.toString()));


            HttpResponse response = client.execute(httpPost);

            HttpEntity httpEntity = response.getEntity();

            String result = EntityUtils.toString(httpEntity);

            Log.d("contact upload", result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        progress.hide();
        Toast.makeText(activity, "uploaded complaint", Toast.LENGTH_LONG).show();
        JSONObject obj;
        try {
            obj = new JSONObject(result);
            String status = obj.getString("code");

            if (status.contentEquals("200")) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(
                        activity);
                builder1.setMessage("Status changed sucessfully!");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
//                                FragmentTransaction ft=((ActionBarActivity)activity).getSupportFragmentManager().beginTransaction();
//                                ft.replace(R.id.fragment_container,new HomeTabFragment());
//                                ft.commit();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();


            } else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(
                        activity);
                builder1.setMessage("Couldn't Change!.Try again!");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            progress.hide();
        }

    }

}
