package fragments;



import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;



import asyncTasks.LoginAsyncTask;
import quadvision.serviceprovider.R;

public class LoginFragment extends Fragment {

    Activity activity;
    EditText email,password;
    Button login,register;


    @Override
    public View onCreateView(LayoutInflater inflater,
                              ViewGroup container,  Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View v = inflater.inflate(R.layout.login, container, false);
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
        email=(EditText)activity.findViewById(R.id.login_email);
        password=(EditText)activity.findViewById(R.id.login_password);
        login=(Button)activity.findViewById(R.id.login_button);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailText=email.getText().toString();
                String passwordText=password.getText().toString();
                new LoginAsyncTask(activity).execute(new String[]{emailText,passwordText});
//                FragmentTransaction ft=((ActionBarActivity)activity).getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment_container,new HomeTabFragment());
//                ft.commit();
            }
        });
        register=(Button)activity.findViewById(R.id.login_register_button);
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=((ActionBarActivity)activity).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container,new RegisterUserFragmentNew(),"register");
                ft.commit();
            }
        });


    }


}
