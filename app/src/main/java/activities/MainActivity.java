package activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import asyncTasks.LoginAsyncTask;
import butterknife.Bind;
import butterknife.ButterKnife;
import fragments.RegisterUserFragmentNew;
import quadvision.serviceprovider.R;

/**
 * Created by fasal on 10-08-2015.
 */
public class MainActivity extends ActionBarActivity {
    @Bind(R.id.login_email)
    EditText email;
    @Bind(R.id.login_password)
    EditText password;
    @Bind(R.id.login_button)
    Button login;
    @Bind(R.id.login_register_button)
    Button register;

    public static Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                new LoginAsyncTask(MainActivity.this).execute(new String[]{emailText, passwordText});

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });

    }
}
