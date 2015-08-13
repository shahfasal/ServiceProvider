package fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import asyncTasks.ComplaintStatusAyncTask;
import asyncTasks.ImageFetcherAsyncTask;
import quadvision.serviceprovider.CircleImageView;
import quadvision.serviceprovider.R;

public class ComplaintsFragmentNew extends Fragment {
    Activity activity;
    ComplaintListAdapter adapter;
    ListView complaintsList;

    public ComplaintsFragmentNew() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.complaints_layout, container, false);
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        this.activity = activity;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        complaintsList = (ListView) activity
                .findViewById(R.id.complaints_list);


        new ComplaintAsyncThread().execute(new Integer[]{});


    }

    public class ComplaintListAdapter extends ArrayAdapter<JSONObject> {

        public List<JSONObject> complaintsData = null;
        int itemLayout;

        public ComplaintListAdapter(Context context, int resource,
                                    List<JSONObject> objects) {
            super(context, resource, objects);
            this.complaintsData = objects;
            this.itemLayout = resource;
            // TODO Auto-generated constructor stub
        }

        @Override
        public JSONObject getItem(int position) {
            // TODO Auto-generated method stub
            return complaintsData.get(position);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return complaintsData.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            LayoutInflater inflater = activity
                    .getLayoutInflater();
            View row = inflater.inflate(R.layout.complaint_new_layout, parent,
                    false);
            try {
//                SharedPreferences prefs = activity.getSharedPreferences("cache", Context.MODE_PRIVATE);
//                JSONObject profile = new JSONObject(prefs.getString("profile", null));


                final JSONObject data = complaintsData.get(position);
                JSONObject profile = new JSONObject(data.getString("profile_user").replace("\\\"", "\""));

                final String complaintId = data.getString("complaint_id");
                ((TextView) row.findViewById(R.id.complaint_title))
                        .setText(data.getString("complaint_title"));
                ((TextView) row.findViewById(R.id.complaint_status))
                        .setText(data.getString("status"));
                ((TextView) row.findViewById(R.id.complaint_username))
                        .setText(profile.getString("name"));
                ((TextView) row.findViewById(R.id.complaint_place))
                        .setText(profile.getString("address"));
                ((TextView) row.findViewById(R.id.complaint_company))
                        .setText(data.getString("agency"));
                ((TextView) row.findViewById(R.id.complaint_domain))
                        .setText(data.getString("problem_type"));
                new ImageFetcherAsyncTask(
                        ((CircleImageView) row.findViewById(R.id.profile_image)))
                        .execute(new String[]{profile.getString("profile_image")});
                ((TextView) row.findViewById(R.id.complaint_description))
                        .setText(data.getString("description"));
                ((LinearLayout) row.findViewById(R.id.accepted))
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new ComplaintStatusAyncTask(activity).execute(


                                        new String[]{
                                                complaintId,
                                                "accepted"
                                        }
                                );
                            }
                        });
                ((LinearLayout) row.findViewById(R.id.completed))
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new ComplaintStatusAyncTask(activity).execute(


                                        new String[]{
                                                complaintId,
                                                "completed"
                                        }
                                );
                            }
                        });

                        ((LinearLayout) row.findViewById(R.id.on_job))
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new ComplaintStatusAyncTask(activity).execute(


                                        new String[]{
                                                complaintId,
                                                "onjob"
                                        }
                                );
                            }
                        });

                        ((LinearLayout) row.findViewById(R.id.escalate))
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new ComplaintStatusAyncTask(activity).execute(


                                        new String[]{
                                                complaintId,
                                                "escalated"
                                        }
                                );
                            }
                        });

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                return row;
            }


        }


    }


    public class ComplaintAsyncThread extends AsyncTask<Integer, Integer, String> {

        ProgressDialog progress = null;

        public ComplaintAsyncThread() {
            // TODO Auto-generated constructor stub

        }

        @Override
        protected String doInBackground(Integer... params) {
            publishProgress(0);
            HttpResponse response = null;
            String result = null;
            try {
                // Create http client object to send request to server
                HttpClient client = new DefaultHttpClient();
                // Create URL string
                String URL = activity.getResources().getString(R.string.get_complaints);
                // Create Request to server and get response
                HttpGet httpget = new HttpGet();
                httpget.setURI(new URI(URL));
                response = client.execute(httpget);
                result = EntityUtils.toString(response.getEntity());
                Log.e("result",result);
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
            progress = new ProgressDialog(activity);
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
                JSONObject result = new JSONObject(results);
                JSONArray complaints = result.getJSONArray("complaint");
                ArrayList<JSONObject> complaintList = new ArrayList<JSONObject>();
                SharedPreferences prefs = activity.getSharedPreferences("cache", Context.MODE_PRIVATE);
                JSONObject profile = new JSONObject(prefs.getString("profile", null));
                //int profileId = Integer.parseInt(profile.getString("profile_id"));
                String designation = null;
                String skills = profile.getString("speciality");
                String locality = profile.getString("locality");
                String agency = profile.getString("agency");

                designation = profile.getString("designation");


                if (agency.toLowerCase().contentEquals("B B M P".toLowerCase()) &&
                        designation.toLowerCase().contentEquals("Commissioner".toLowerCase())
                        ) {
                    for (int i = 0; i < complaints.length(); i++) {
                        JSONObject complaint = complaints.getJSONObject(i);
                    /*

                    compare the agency
                        compare designation(BBMP)

                 */

                        if (complaint.getString("agency").toLowerCase().contentEquals(agency.toLowerCase())
                                ) {
                            complaintList.add(complaint);

                            Log.e("list", complaintList.toString());

                        }


                    }
                } else {
                    for (int i = 0; i < complaints.length(); i++) {
                        JSONObject complaint = complaints.getJSONObject(i);
                    /*

                    compare the agency
                            compare locality
                                compare the skills

                 */

                        //Log.d("complaints_filtered",complaint.getString("agency").toLowerCase()+" ?   "+agency.toLowerCase());
                        if (complaint.getString("agency").toLowerCase().contentEquals(agency.toLowerCase()) &&
                                complaint.getString("locality").toLowerCase().contentEquals(locality.toLowerCase()) &&
                                skills.toLowerCase().contains(complaint.getString("problem_type").toLowerCase())
                                ) {
                            complaintList.add(complaint);
                            Log.e("list",complaintList.toString());
                        }


                    }
                }
                complaintsList.setAdapter(new ComplaintListAdapter(activity, R.layout.complaint_new_layout, complaintList));


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

}
