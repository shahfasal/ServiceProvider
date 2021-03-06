package fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import asyncTasks.RegisterAsyncTask;
import quadvision.serviceprovider.R;

public class RegisterUserFragmentNew extends Fragment {

    Activity activity;
    EditText name, email, mobile, address, city, password, confirmPassword;
    public static EditText specialityEt;
    Button male, female, register;
    ImageView profileImage;
    String gender = new String("male");
    Bitmap profileImageBitmap;
    Spinner s1, s2, s3,  spagency;
    boolean flagMale = false, flagFemale = false;
    public static ArrayList<String> specialities=new ArrayList<>();
    public void open() {
        Intent intent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        profileImageBitmap = (Bitmap) data.getExtras().get("data");
        profileImage.setBackground(new BitmapDrawable(profileImageBitmap));
        profileImage.setImageBitmap(profileImageBitmap);
    }

    private void toggleButton(Button button, boolean flag) {
        if (flag) {
            button.setBackground(getResources().getDrawable(R.drawable.gray_border));
            button.setTextColor(activity.getResources().getColor(R.color.primaryColor));

        } else {
            button.setBackgroundColor(activity.getResources().getColor(R.color.primaryColor));
            button.setTextColor(Color.WHITE);

        }
    }

//    private void setup() {
//
//        profileImage = (ImageView) activity.findViewById(R.id.register_profile_image);
//        profileImage.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                open();
//            }
//        });
//        name = (EditText) activity.findViewById(R.id.editTextName);
//        email = (EditText) activity.findViewById(R.id.editTextEmail);
//        male = (Button) activity.findViewById(R.id.gender_male);
//        specialityEt = (EditText) activity.findViewById(R.id.editTextSpeciality);
//        Log.d("specialities", specialities.toString());
//        Log.d("specialities", specialities.size()+"");
//        //specialityEt.setText("hey");
//
//        /*
//        deafault making male button active
//         */
//        toggleButton(male, flagMale);
//        flagMale = !flagMale;
//        male.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (flagFemale) {
//                    toggleButton(female, flagFemale);
//                    flagFemale = !flagFemale;
//                }
//                toggleButton((Button) v, flagMale);
//                flagMale = !flagMale;
//
//
//            }
//        });
//
//        female = (Button) activity.findViewById(R.id.gender_female);
//        female.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (flagMale) {
//                    toggleButton(male, flagMale);
//                    flagMale = !flagFemale;
//                }
//
//                toggleButton((Button) v, flagFemale);
//                flagFemale = !flagFemale;
//
//
//            }
//        });
//
//        s1 = (Spinner) activity.findViewById(R.id.spinner1);
//        s2 = (Spinner) activity.findViewById(R.id.spinner2);
//        s3 = (Spinner) activity.findViewById(R.id.spinner3);
//        spagency = (Spinner) activity.findViewById(R.id.spAgency);
//
//        mobile = (EditText) activity.findViewById(R.id.editMobile);
//        address = (EditText) activity.findViewById(R.id.editAddress);
//        city = (EditText) activity.findViewById(R.id.editCity);
//        password = (EditText) activity.findViewById(R.id.editTextPassword);
//        confirmPassword = (EditText) activity.findViewById(R.id.editTextConfirm);
//        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//
//                String sp1 = String.valueOf(s1.getSelectedItem());
//
//
//                if (sp1.contentEquals("North")) {
//                    List<String> list = Arrays.asList("Vidyaranyapura", "Yelahanka", "WhiteField");
//
//                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity,
//                            android.R.layout.simple_spinner_item, list);
//                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter.notifyDataSetChanged();
//                    s2.setAdapter(dataAdapter);
//                }
//                if (sp1.contentEquals("South")) {
//
//                    List<String> list = Arrays.asList("Jayanagar", "J.P Nagar", "WhiteField");
//
//
//                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, list);
//
//                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter2.notifyDataSetChanged();
//                    s2.setAdapter(dataAdapter2);
//                }
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        spagency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selected = String.valueOf(spagency.getSelectedItem());
//                List<String> list = null;// = Arrays.asList("Vidyaranyapura", "Yelahanka", "WhiteField");
//                switch (selected) {
//                    case "BBMP":
//                        list = Arrays.asList("Commissioner", "Joint commissioner", "Range Forest Officer",
//                                "Assistant Engineer Street Light", "Horticulture Incharge", " Assistant Revenue Officer",
//                                "Revenue Officer", "Assistant Engineer", "Health Inspector",
//                                "Animal Husbandry Engineer", "Medical Officer of Health", "Assistant Executive Engineer",
//                                "Executive Engineer"," Chief Engineer"
//                                );
//                        break;
//                    case "Mortuary":
//
//
//                        list = Arrays.asList("Electric cremation Incharge", "Mortuary Van Personnel");
//                        break;
//                    case "Emergency":
//                        list = Arrays.asList("Fire Emergency Personnel");
//                        break;
//                    case "Law and Order":
//                        list = Arrays.asList("Police Inspector");
//                        break;
//                    case "Snake Rescuer":
//                        list = Arrays.asList("Snake Rescuer");
//                        break;
//                    case "BWSSB":
//                        list = Arrays.asList("Assistant Executive Engineer", "Assistant Engineer", "Assistant Executive Engineer",
//                                "Assistant Engineer");
//                        break;
//                    case "Pollution control":
//                        list = Arrays.asList("Pollution control Personnel");
//                        break;
//                    case "BESCOM":
//                        list = Arrays.asList("Assistant Executive Engineer");
//                        break;
//                }
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, list);
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                adapter.notifyDataSetChanged();
//                s3.setAdapter(adapter);
//                s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//
//        specialityEt.setClickable(true);
//        specialityEt.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                FragmentTransaction ft=((ActionBarActivity)activity).getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment_container,new SpecialityFragment());
//                ft.addToBackStack("register");
//                ft.commit();
//            }
//        });
//
//        register = (Button) activity.findViewById(R.id.register_submit_button);
//        register.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String nameText, emailText, mobileText, addressText, landmarkText, cityText, pincodeText, passwordText, confirmPasswordText, zoneText, localityText;
//                nameText = name.getText().toString().trim();
//                emailText = email.getText().toString().trim();
//                mobileText = mobile.getText().toString().trim();
//                addressText = address.getText().toString().trim();
//                //landmarkText = landmark.getText().toString().trim();
//                cityText = city.getText().toString().trim();
////                pincodeText = pincode.getText().toString().trim();
//                passwordText = password.getText().toString().trim();
//                confirmPasswordText = confirmPassword.getText().toString().trim();
//                zoneText = (String) s1.getSelectedItem();
//                localityText = (String) s2.getSelectedItem();
//
//
//
//
//                /*
//                check if all the fields are filled
//                 */
//                if (nameText.contentEquals("") ||
//                        emailText.contentEquals("") ||
//                        mobileText.contentEquals("") ||
//                        addressText.contentEquals("") ||
//                        cityText.contentEquals("") ||
//                        passwordText.contentEquals("") ||
//                        confirmPasswordText.contentEquals("")
//                        ) {
//                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
//                            activity);
//                    builder1.setMessage("Please fill all the fields!");
//                    builder1.setCancelable(true);
//                    builder1.setPositiveButton("Ok",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog,
//                                                    int id) {
//                                    dialog.cancel();
//                                }
//                            });
//                    AlertDialog alert11 = builder1.create();
//                    alert11.show();
//                }
//
//                /*
//                check for password and confirm password
//                 */
//                else if (!passwordText.contentEquals(confirmPasswordText)) {
//                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
//                            activity);
//                    builder1.setMessage("Passwords do not match");
//                    builder1.setCancelable(true);
//                    builder1.setPositiveButton("Ok",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog,
//                                                    int id) {
//                                    dialog.cancel();
//                                }
//                            });
//                    AlertDialog alert11 = builder1.create();
//                    alert11.show();
//                }
//
//                /*
//                call the async task
//                 */
//                else {
//                    /*
//                    build the hashmap
//                     */
//
//                    HashMap<String, String> data = new HashMap<String, String>();
//                    data.put("name", nameText);
//                    data.put("password", passwordText);
//                    data.put("address", addressText);
//                    data.put("zone", zoneText);
//                    data.put("locality", localityText);
//                    if (flagMale) {
//                        data.put("gender", "male");
//                    } else {
//                        data.put("gender", "female");
//                    }
//                    data.put("city", cityText);
//                    data.put("mobile", mobileText);
//                    data.put("email", emailText);
//                    data.put("designation", (String) s3.getSelectedItem());
//                    data.put("agency", (String) spagency.getSelectedItem());
//
//                    data.put("speciality",specialityEt.getText().toString());
//
//                    /*
//                    call the registe async task
//                     */
//                    new RegisterAsyncTask(activity, profileImageBitmap).execute(data);
//                }
//
//
//            }
//        });
//
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater,
//                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        // TODO Auto-generated method stub
//
//        View v = inflater.inflate(R.layout.register, container, false);
//        return v;
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        // TODO Auto-generated method stub
//        super.onAttach(activity);
//        this.activity = activity;
//
//    }
//
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        // TODO Auto-generated method stub
//
//        super.onActivityCreated(savedInstanceState);
//        setup();
//        ((ActionBarActivity)activity).getSupportFragmentManager().addOnBackStackChangedListener(
//                new FragmentManager.OnBackStackChangedListener() {
//                    public void onBackStackChanged() {
//                        // Update your UI here.
//                        RegisterUserFragmentNew fragment=(RegisterUserFragmentNew)((ActionBarActivity)activity).getSupportFragmentManager().findFragmentByTag("register");
//                        if(fragment!=null && fragment.isVisible())
//                        {
//                            if(specialities.size()>0)
//                            {
//                                String speciality=new String();
//                                for(int j=0;j<specialities.size();j++)
//                                {
//                                    if(j==specialities.size()-1)
//                                    {
//                                        speciality += specialities.get(j);
//                                    }
//                                    else {
//                                        speciality += specialities.get(j) + ",";
//                                    }
//                                }
//                                Log.d("specialities", speciality);
//                                specialityEt.setText(speciality);
//                                Log.d("specialities", specialityEt.getText().toString());
//                            }
//                        }
//                    }
//                });
//
//
//    }
//

}
