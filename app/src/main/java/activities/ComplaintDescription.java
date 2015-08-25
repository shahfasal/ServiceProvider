package activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import asyncTasks.ComplaintStatusAyncTask;
import asyncTasks.ImageFetcherAsyncTask;
import quadvision.serviceprovider.CircleImageView;
import quadvision.serviceprovider.R;

/**
 * Created by fasal on 13-08-2015.
 */
public class ComplaintDescription extends ActionBarActivity {
    CircleImageView img1,img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_desc);
      //  img1 = (CircleImageView) findViewById(R.id.img1);
        //img2 = (CircleImageView) findViewById(R.id.img2);
        Button btn_accepted = (Button) findViewById(R.id.btn_accepted);
        Button btn_setonjob = (Button) findViewById(R.id.btn_setonjob);
        Button btn_setcompleted = (Button) findViewById(R.id.btn_setcompleted);
        Intent intent = getIntent();
       final String complaintId = intent.getStringExtra("id");
//        Log.e("id", name1);
        try {
            JSONObject data = new JSONObject(intent.getStringExtra("json"));
//            Picasso.with(this)
//                    .load(data.getString("complaint_image_1"))
//                    .resize(50, 50)
//                    .centerCrop()
//                    .into(img1);
            if (!data.getString("complaint_image_1").contentEquals("")) {
                String url = data.getString("complaint_image_1");
                views.CircleImageView circleImageView = (views.CircleImageView) findViewById(R.id.img1);
                Picasso.with(this).load(url).resize(150, 150).into(circleImageView);

            }else{
                views.CircleImageView circleImageView = (views.CircleImageView) findViewById(R.id.img1);
                circleImageView.setImageResource(R.drawable.image);
            }

            if (!data.getString("complaint_image_2").contentEquals("")) {
                String url = data.getString("complaint_image_2");
                views.CircleImageView circleImageView = (views.CircleImageView) findViewById(R.id.img2);
                Picasso.with(this).load(url).resize(150, 150).into(circleImageView);

            }else{
                views.CircleImageView circleImageView = (views.CircleImageView) findViewById(R.id.img2);
                circleImageView.setImageResource(R.drawable.image);
            }

//            new ImageFetcherAsyncTask(
//                    ((ImageView) findViewById(R.id.img1)))
//                    .execute(new String[]{data.getString("complaint_image_1")});
            ((TextView) findViewById(R.id.title))
                    .setText(data.getString("complaint_title"));
            ((TextView) findViewById(R.id.description))
                    .setText(data.getString("description"));

            btn_accepted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ComplaintStatusAyncTask(ComplaintDescription.this).execute(


                            new String[]{
                                    complaintId,
                                    "accepted"
                            }
                    );
                }
            });
            btn_setcompleted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ComplaintStatusAyncTask(ComplaintDescription.this).execute(


                            new String[]{
                                    complaintId,
                                    "completed"
                            }
                    );
                }
            });

            btn_setonjob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ComplaintStatusAyncTask(ComplaintDescription.this).execute(


                            new String[]{
                                    complaintId,
                                    "onjob"
                            }
                    );
                }
            });









            Log.e("json",data+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
