package activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import quadvision.serviceprovider.R;
import quadvision.serviceprovider.StringDecoder;
import views.CircleImageView;


/**
 * Created by fasal on 21-08-2015.
 */
public class CommentListActivity extends ActionBarActivity {
    ListView commentsList;JSONObject result;
    public static String  firstName,url ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_list);
        Intent intent = getIntent();
        final String complaintId = intent.getStringExtra("id");
        commentsList = (ListView)
                findViewById(R.id.listCommentView);
        new CommentListThread().execute(complaintId);
    }

    public class CommentListThread extends AsyncTask<String, Integer, String> {
        ProgressDialog progress = null;
        @Override
        protected String doInBackground(String... params) {

            publishProgress(0);
            HttpResponse response = null;
            String result = null;
            try {
                // Create http client object to send request to server
                HttpClient client = new DefaultHttpClient();
                // Create URL string
                String URL = (getResources().getString(R.string.get_comments)+params[0]);
                // Create Request to server and get response
                HttpGet httpget = new HttpGet();
                httpget.setURI(new URI(URL));
                response = client.execute(httpget);
                result = EntityUtils.toString(response.getEntity());
                Log.e("result", result);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            return result;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
            progress = new ProgressDialog(CommentListActivity.this);
            progress.setMessage("fetching data..... ");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            progress.show();
        }
        @Override
        protected void onPostExecute(String results) {

            // TODO Auto-generated method stub
            super.onPostExecute(results);
            progress.hide();

            try {
                Log.d("my complaints", results);
                result = new JSONObject(results);
                JSONArray comments = result.getJSONArray("info");
//                Log.e("profile",profile.toString());
                JSONObject profile = new JSONObject(result.getString("profile"));
                Log.e("profile",profile.toString());
//                for (int i = 0; i < profile.length(); i++) {
////
//                    JSONObject jObject = new JSONObject(profile.getString(i));

                    firstName =profile.getString("name");
              url = StringDecoder.decode(profile.getString("profile_image"));
//
                //}
                List<String> commentList = new ArrayList<String>();
//                SharedPreferences prefs = getSharedPreferences("cache", Context.MODE_PRIVATE);
//                JSONObject profile = new JSONObject(prefs.getString("profile", null));
                for (int i = 0; i < comments.length(); i++) {

                    commentList.add(comments.getString(i));

                }
                commentsList.setAdapter(new CommentListAdapter(CommentListActivity.this, R.layout.comment_new_layout, commentList));
                //commentsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, commentList));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    public class CommentListAdapter extends ArrayAdapter<String>{
        public List<String> commentsData = null;
        int itemLayout;
        public CommentListAdapter(Context context, int resource, List<String> list) {
            super(context, resource, list);
            this.commentsData = list;
            this.itemLayout = resource;

        }
        @Override
        public String getItem(int position) {
            // TODO Auto-generated method stub
            return commentsData.get(position);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return commentsData.size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            LayoutInflater inflater =
                    getLayoutInflater();


            View row = inflater.inflate(R.layout.comment_new_layout, parent,
                    false);
            Log.e("commentsData",commentsData+" ");

            String comment = commentsData.get(commentsData.size() - 1 - position);
            Log.e("comment", comment.toString());
            TextView txtComment = (TextView) row.findViewById(R.id.tv_comment);
            txtComment.setText(commentsData.get(position));

            TextView txtName =  (TextView) row.findViewById(R.id.complaint_username);
            txtName.setText(firstName);

//            CircleImageView circleImageView = (CircleImageView) row.findViewById(R.id.profile_image);
//            Picasso.with(CommentListActivity.this).load(url).into(circleImageView);
            return row;
        }

    }

}
