package activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import asyncTasks.ImageFetcherAsyncTask;
import quadvision.serviceprovider.CircleImageView;
import quadvision.serviceprovider.R;

/**
 * Created by fasal on 13-08-2015.
 */
public class ComplaintDescription extends ActionBarActivity {
    ImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_desc);
        img1 = (ImageView) findViewById(R.id.img1);

        Intent intent = getIntent();
        String name1 = intent.getStringExtra("id");
        Log.e("id", name1);
        try {
            JSONObject data = new JSONObject(intent.getStringExtra("json"));
            new ImageFetcherAsyncTask(
                    ((ImageView) findViewById(R.id.img1)))
                    .execute(new String[]{data.getString("complaint_image_1")});
            Log.e("json",data+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
