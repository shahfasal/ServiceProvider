package asyncTasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import quadvision.serviceprovider.R;


/**
 * Created by abhishek on 25-06-2015.
 */
public class VerifyUserAsyncTask extends AsyncTask<String, Integer, String> {

    ProgressDialog progress = null;
    Activity activity;

    public VerifyUserAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
        progress = new ProgressDialog(activity);
        progress.setMessage("verifying..... ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

    }
    @Override
    protected String doInBackground(String... parameters) {
        publishProgress(0);
        //Log.d("register-hash-data", parameters[0].toString());
        try {
            HttpClient client = new DefaultHttpClient();

            HttpGet get = new HttpGet(activity.getResources().getString(R.string.verify_user) + parameters[0]);


            HttpResponse response = client.execute(get);

            HttpEntity httpEntity = response.getEntity();

            String result = EntityUtils.toString(httpEntity);

            Log.d("registration", result);
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

        try {
            JSONObject resultJson = new JSONObject(result);
            if (resultJson.getString("code").trim().contentEquals("200")) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(
                        activity);
                builder1.setTitle("Mobile Verification");
                builder1.setMessage("Verification sucessfull.PLease login");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Okay",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                                activity.finish();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            } else if (resultJson.getString("code").trim().contentEquals("505")) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(
                        activity);
                builder1.setMessage("User already exists!");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                                activity.finish();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            } else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(
                        activity);
                builder1.setMessage("Registration failed.Try Again");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                                activity.finish();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();


    }


}
