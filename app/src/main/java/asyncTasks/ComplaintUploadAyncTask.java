package asyncTasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
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

public class ComplaintUploadAyncTask extends AsyncTask<HashMap<String, String>, Integer, String> {
    ProgressDialog progress = null;
    Bitmap image;
    Activity activity;

    public ComplaintUploadAyncTask(Activity activity) {
        // TODO Auto-generated constructor stub
        this.activity = activity;
    }

    public ComplaintUploadAyncTask(Activity activity, Bitmap image) {
        this.activity = activity;
        this.image = image;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
        progress = new ProgressDialog(activity);
        progress.setMessage("uploading complaint..... ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

    }

    @Override
    protected String doInBackground(HashMap<String, String>... params) {
        publishProgress(0);
        try {
            HttpClient client = new DefaultHttpClient();

            HttpPost post = new HttpPost(activity.getResources().getString(R.string.post_complaint));

            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            entityBuilder.addTextBody("complaint_type", params[0].get("complaint_type"));
            entityBuilder.addTextBody("complaint_title", params[0].get("complaint_title"));
            entityBuilder.addTextBody("description", params[0].get("complaint_decription"));
            entityBuilder.addTextBody("landmark", params[0].get("complaint_landmark"));
            entityBuilder.addTextBody("location", params[0].get("complaint_address"));
            entityBuilder.addTextBody("profile_id", params[0].get("profile_id"));
            entityBuilder.addTextBody("zone", params[0].get("zone"));
            entityBuilder.addTextBody("locality", params[0].get("locality"));
            entityBuilder.addTextBody("status", "open");
            entityBuilder.addTextBody("profile_user", params[0].get("profile_user"));

            if (image != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                Random r = new Random();
                entityBuilder.addBinaryBody("complaint_image", byteArray, ContentType.create("image/png"), "image_" + r.nextInt() + ".png");
            } else {
                entityBuilder.addBinaryBody("complaint_image", new byte[]{});
            }

            HttpEntity entity = entityBuilder.build();

            post.setEntity(entity);

            HttpResponse response = client.execute(post);

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
                builder1.setMessage("Uploaded sucessfully!");
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


            }
            else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(
                        activity);
                builder1.setMessage("Couldn't upload.Try again!");
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
