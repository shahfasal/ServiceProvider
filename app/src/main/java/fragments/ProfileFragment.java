package fragments;



import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;



import org.json.JSONObject;

import asyncTasks.ImageFetcherAsyncTask;
import quadvision.serviceprovider.R;

public class ProfileFragment extends Fragment {

    Activity activity;
    EditText email,password;
    Button login,register;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View v = inflater.inflate(R.layout.fragment_my_profile, container, false);
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        this.activity = activity;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        super.onActivityCreated(savedInstanceState);
        SharedPreferences prefs=activity.getSharedPreferences("cache", Context.MODE_PRIVATE);
        try
        {
            String profileJson=prefs.getString("profile",null);
            JSONObject profile=new JSONObject(profileJson);
            String name=profile.getString("name");
            String agency=profile.getString("agency");
            String zone=profile.getString("zone");
            String designation=profile.getString("designation");
            String skills=profile.getString("speciality");
            ((TextView)activity.findViewById(R.id.name)).setText(name);
            ((TextView)activity.findViewById(R.id.designation)).setText(agency+" >"+designation);
            ((TextView)activity.findViewById(R.id.speciality)).setText(skills);
            ((TextView)activity.findViewById(R.id.zone)).setText(zone);
            new ImageFetcherAsyncTask(((ImageView)activity.findViewById(R.id.register_profile_image))).execute(profile.getString("profile_image"));

        }
        catch(Exception e)
        {

        }
        finally
        {

        }



    }


}
