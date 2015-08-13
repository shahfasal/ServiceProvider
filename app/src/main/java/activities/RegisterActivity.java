package activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import asyncTasks.RegisterAsyncTask;
import fragments.SpecialityFragment;
import quadvision.serviceprovider.R;
import quadvision.serviceprovider.Speciality;

/**
 * Created by fasal on 10-08-2015.
 */
public class RegisterActivity extends ActionBarActivity {
    EditText name, email, mobile, address, city, password, confirmPassword;
    public static EditText specialityEt;
    Button male, female, register;
    ImageView profileImage;
    String gender = new String("male");
    String speciality;
    Bitmap profileImageBitmap;
    Spinner s1, s2, s3, spagency;
    boolean flagMale = false, flagFemale = false;
    public static ArrayList<String> specialities = new ArrayList<>();

    ImageView backButton;
    final Integer COMPLAINT_IMAGE_ONE_REQUEST_CODE_IMAGE = 0;
    final Integer COMPLAINT_IMAGE_ONE_REQUEST_CODE_GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        setup();
        backButton = (ImageView) findViewById(R.id.icon_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void open() {
        Intent intent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 || requestCode == 2) {
            if (profileImageBitmap != null) {
                profileImage.setBackground(new BitmapDrawable(profileImageBitmap));
            } else {
                if (resultCode != 0 && requestCode == COMPLAINT_IMAGE_ONE_REQUEST_CODE_IMAGE) {
                    profileImageBitmap = (Bitmap) data.getExtras().get("data");
                    profileImage.setBackground(new BitmapDrawable(profileImageBitmap));
                } else if (resultCode != 0 && requestCode == COMPLAINT_IMAGE_ONE_REQUEST_CODE_GALLERY) {
                    Uri selectedImage = data.getData();

                    Log.i("image_gallery", data.toString());

            /*
            start processing
             */
                    // Will return "image:x*"
                    String wholeID = DocumentsContract.getDocumentId(selectedImage);

// Split at colon, use second item in the array
                    String id = wholeID.split(":")[1];

                    String[] column = {MediaStore.Images.Media.DATA};

// where id is equal to
                    String sel = MediaStore.Images.Media._ID + "=?";

                    Cursor cursor = getContentResolver().
                            query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    column, sel, new String[]{id}, null);

                    String filePath = "";

                    int columnIndex = cursor.getColumnIndex(column[0]);

                    if (cursor.moveToFirst()) {
                        filePath = cursor.getString(columnIndex);
                    }

                    cursor.close();

            /*
            end processing
             */

                    profileImageBitmap = BitmapFactory.decodeFile(filePath);
                    profileImage.setBackground(new BitmapDrawable(profileImageBitmap));
                }
            }
        }

        if (requestCode == 1) {
            if (resultCode == 1) {
                Bundle bundle = data.getExtras();
                ArrayList<String> list = (ArrayList<String>) bundle.getSerializable("passwords");
                if (list.size() > 0) {
                    String speciality = new String();
                    for (int j = 0; j < list.size(); j++) {
                        if (j == list.size() - 1) {
                            speciality += list.get(j);
                        } else {
                            speciality += list.get(j) + ",";
                        }
                        
                    }
                    Log.d("specialities", speciality);
                    specialityEt.setText(speciality);


                }
                if (resultCode == RESULT_CANCELED) {
                    //Write your code if there's no result
                    Log.e("list", "no result");
                }
            }
        }

    }

    private void toggleButton(Button button, boolean flag) {
        if (flag) {
            button.setBackground(getResources().getDrawable(R.drawable.gray_border));
            button.setTextColor(getResources().getColor(R.color.primaryColor));

        } else {
            button.setBackgroundColor(getResources().getColor(R.color.primaryColor));
            button.setTextColor(Color.WHITE);

        }
    }

    private void setup() {

        profileImage = (ImageView) findViewById(R.id.register_profile_image);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open();
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("Choose Image Source");
                builder.setItems(new CharSequence[]{"Gallery", "Camera"},
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        if (Build.VERSION.SDK_INT < 19) {
                                            Intent intent = new Intent();
                                            intent.setType("image/*");
                                            intent.setAction(Intent.ACTION_GET_CONTENT);
                                            // startActivityForResult(Intent.createChooser(intent, "Select Picture",COMPLAINT_IMAGE_ONE_REQUEST_CODE_GALLERY));
                                            startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                                                    COMPLAINT_IMAGE_ONE_REQUEST_CODE_GALLERY);
                                        } else {
                                            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                                            intent.setType("image/*");
                                            //startActivityForResult(intent, GALLERY_KITKAT_INTENT_CALLED);
                                            startActivityForResult(intent, COMPLAINT_IMAGE_ONE_REQUEST_CODE_GALLERY);
                                        }
                                        break;
                                    case 1:
                                        Intent intent = new Intent(
                                                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(intent, COMPLAINT_IMAGE_ONE_REQUEST_CODE_IMAGE);
                                        break;
                                }
                            }
                        });
                builder.show();


            }
        });

        name = (EditText) findViewById(R.id.editTextName);
        email = (EditText) findViewById(R.id.editTextEmail);
        male = (Button) findViewById(R.id.gender_male);
        specialityEt = (EditText) findViewById(R.id.editTextSpeciality);

        //specialityEt.setText("hey");

        /*
        deafault making male button active
         */
        toggleButton(male, flagMale);
        flagMale = !flagMale;
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagFemale) {
                    toggleButton(female, flagFemale);
                    flagFemale = !flagFemale;
                }
                toggleButton((Button) v, flagMale);
                flagMale = !flagMale;


            }
        });

        female = (Button) findViewById(R.id.gender_female);
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagMale) {
                    toggleButton(male, flagMale);
                    flagMale = !flagFemale;
                }

                toggleButton((Button) v, flagFemale);
                flagFemale = !flagFemale;


            }
        });

        s1 = (Spinner) findViewById(R.id.spinner1);
        s2 = (Spinner) findViewById(R.id.spinner2);
        s3 = (Spinner) findViewById(R.id.spinner3);
        spagency = (Spinner) findViewById(R.id.spAgency);

        mobile = (EditText) findViewById(R.id.editMobile);
        address = (EditText) findViewById(R.id.editAddress);
        city = (EditText) findViewById(R.id.editCity);
        password = (EditText) findViewById(R.id.editTextPassword);
        confirmPassword = (EditText) findViewById(R.id.editTextConfirm);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                String sp1 = String.valueOf(s1.getSelectedItem());


                if (sp1.contentEquals("SOUTH")) {
                    List<String> list = Arrays.asList("kottegepalya",
                            "shakthiganapathi nagar",
                            "kamakshipalya",
                            "Vrushabhavathi",
                            "kaveripura",
                            "lingarajapura",
                            "Hosahalli ",
                            "marenahalli",
                            "maruthimandir",
                            "moodalpalya",
                            "atthiguppe",
                            "hampinagar",
                            "bapujinagar",
                            "Padarayanapura",
                            "Jagareevanram Nagar ",
                            "Rayapuram",
                            "Chamrajpet",
                            "Azad Nagar ",
                            "Sunkenahalli",
                            "Visvesvarapuram",
                            "Siddapura ",
                            "Hombegowda Nagar",
                            "Lakkasandra",
                            "Adugodi ",
                            "Koramangala ",
                            "Sudduguntepalya ",
                            "Jayangar",
                            "Basavangudi",
                            "Hanumantha Nagar",
                            "Sri Nagar",
                            "Gali Anjaneya Swamy Temple Ward",
                            "Deepanjali Nagar",
                            "Hosakerehalli",
                            "Giri Nagar",
                            "Katriguppe ",
                            "Vidyapeetha",
                            "Ganeshmandira Ward",
                            "Karisandra",
                            "Yadiyuru",
                            "Pattabhiram Nagar",
                            "Byrasandra ",
                            "Jayanagar East",
                            "Gurappanapalya",
                            "Madiwala",
                            "BTM Layout",
                            "J.P.Nagar",
                            "Sarakki",
                            "Shakambari Nagar",
                            "Banashankari Temple Ward",
                            "Kumaraswamy Layout",
                            "Padmanabha Nagar",
                            "Chikkalasandra ",
                            "Yelachenahalli ",
                            "Jaraganahalli"
                            );

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RegisterActivity.this,
                            android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    s2.setAdapter(dataAdapter);
                }
                if (sp1.contentEquals("EAST")) {

                    List<String> list = Arrays.asList("Vishwanathnagenahalli ",
                            "nagavara",
                            "banaswadi",
                            "kammanahalli",
                            "Kacharakanahalli",
                            "kadugondanahalli",
                            "kushal nagar",
                            "kavalbyrsandra",
                            "jayachamarajendra nagar",
                            "Devarajeevanahalli",
                            "muneshwar nagar",
                            "Bennigenahalli",
                            "c vc raman nagar",
                            "hosathippasandra",
                            "Maruthiseva Nagar",
                            "sagayapuram",
                            "s.k garden",
                            "ramaswamypalya",
                            "jayamahal",
                            "pulikeshi nagar",
                            "sarvagnanagar",
                            "hoysala nagar",
                            "jeevanbhima nagar",
                            "jogupalya",
                            "halsoor",
                            "bharathi nagar",
                            "shivajinagar",
                            "Sampangiram Nagar",
                            "shanthalanagar",
                            "dommaluru",
                            "Konena Agrahara",
                            "agaram",
                            "vannerpet",
                            "neelsandra",
                            "Shanthi Nagar",
                            "sudham nagar",
                            "Ejipura"
                            );


                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, list);

                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter2.notifyDataSetChanged();
                    s2.setAdapter(dataAdapter2);
                }
                if (sp1.contentEquals("WEST")) {

                    List<String> list = Arrays.asList("jalahalli ward",
                            "jp park",
                            "radhakrishna temple",
                            "sanjay nagar",
                            "ganganagar",
                            "hebbala",
                            "manorayanapalya",
                            "gangenhalli",
                            "aramane nagar",
                            "mathikere",
                            "yeshwanthpur",
                            "HMT",
                            "laxmidevi nagar",
                            "nandini layout",
                            "marappana palya",
                            "malleshwaram",
                            "rajamahal",
                            "kadumalleshwara",
                            "subramanyanagar",
                            "nagapura",
                            "mahalaxmipuram",
                            "laggere",
                            "shankarmatta",
                            "gayathri nagar",
                            "dattatreya temple",
                            "vasanthnagar",
                            "gandhinagar",
                            "subhash nagar",
                            "okalipuram",
                            "dayanandanagar",
                            "Prakash Nagar",
                            "rajajinagar",
                            "shivanagar",
                            "govindrajnagar",
                            "agrahara dasaralli",
                            "dr rajkumar",
                            "shivnagar",
                            "sri rammandir",
                            "chikpet",
                            "Dharmarayaswamy Temple Ward ",
                            "cottonpet",
                            "Binnipete",
                            "Kempapura Agrahara",
                            "Vijayanagar",
                            "Chalavadipalya",
                            "K.R.Market");


                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, list);

                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter2.notifyDataSetChanged();
                    s2.setAdapter(dataAdapter2);
                }
                if (sp1.contentEquals("YELAHANKA")) {

                    List<String> list = Arrays.asList("Kempegowda",
                            "Chowdeswari",
                            "Atturu",
                            "Yelahanka Satellite Town",
                            "Jakkuru",
                            "Thanisandra",
                            "Byatarayanapura",
                            "Kodigehalli",
                            "dyaranyapura",
                            "Dodda Bommasandra",
                            "kuvempu nagar",
                            "HBR layout",
                            "horamavu"

                    );


                    ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, list);

                    dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter3.notifyDataSetChanged();
                    s2.setAdapter(dataAdapter3);
                }
                if (sp1.contentEquals("MAHADEVPURA")) {

                    List<String> list = Arrays.asList("ramurthynagar",
                            "vijinapura",
                            "k r puram",
                            "basavana pura",
                            "hoodi",
                            "devasandra",
                            "A.naryanapura",
                            "varthur",
                            "vijnananagar",
                            "garudacharpalya",
                            "kadugodi",
                            "hagadooru",
                            "doddanekundi",
                            "marathalli",
                            "HAL airport"

                    );


                    ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, list);

                    dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter4.notifyDataSetChanged();
                    s2.setAdapter(dataAdapter4);
                }
                if (sp1.contentEquals("RAJARAJESHWARINAGAR")) {

                    List<String> list = Arrays.asList("nagarbhavi",
                            "jnanabharathi ward",
                            "ullalu",
                            "nayandanahalli",
                            "Kengeri",
                            "Rajarajeshwari Nagar",
                            "Uttarahalli",
                            "Hemmigepura"


                    );


                    ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, list);

                    dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter5.notifyDataSetChanged();
                    s2.setAdapter(dataAdapter5);
                }

                if (sp1.contentEquals("BOMMANAHALLI")) {

                    List<String> list = Arrays.asList("Bellandur",
                            "Jakkasandra",
                            "HSR Layout",
                            "Bommanahalli",
                            "Puttenahalli",
                            "Bilekahalli",
                            "Hongasandra",
                            "Mangammanapalya",
                            "Singasandra ",
                            "Begur",
                            "Arakere",
                            "Gottigere",
                            "Konanakunte",
                            "Anjanapur",
                            "Vasanthapura"


                    );


                    ArrayAdapter<String> dataAdapter6 = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, list);

                    dataAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter6.notifyDataSetChanged();
                    s2.setAdapter(dataAdapter6);
                }
                if (sp1.contentEquals("DASARAHALLI")) {

                    List<String> list = Arrays.asList("shettyhalli",
                            "mallasandra",
                            "bagalgunte",
                            "t dasarahalli",
                            "chokkasandra",
                            "doddbiderkallu",
                            "peenya industraial area",
                            "rajagopalanagar",
                            "hegganahalli",
                            "herohalli"



                    );


                    ArrayAdapter<String> dataAdapter7 = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, list);

                    dataAdapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter7.notifyDataSetChanged();
                    s2.setAdapter(dataAdapter7);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spagency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = String.valueOf(spagency.getSelectedItem());
                List<String> list = null;// = Arrays.asList("Vidyaranyapura", "Yelahanka", "WhiteField");
                switch (selected) {
                    case "BBMP":
                        list = Arrays.asList("Commissioner", "Joint commissioner", "Range Forest Officer",
                                "Assistant Engineer Street Light", "Horticulture Incharge", " Assistant Revenue Officer",
                                "Revenue Officer", "Assistant Engineer", "Health Inspector",
                                "Animal Husbandry Engineer", "Medical Officer of Health", "Assistant Executive Engineer",
                                "Executive Engineer", " Chief Engineer"
                        );
                        break;
                    case "Mortuary":


                        list = Arrays.asList("Electric cremation Incharge", "Mortuary Van Personnel");
                        break;
                    case "Emergency":
                        list = Arrays.asList("Fire Emergency Personnel");
                        break;
                    case "Law and Order":
                        list = Arrays.asList("Police Inspector");
                        break;
                    case "Snake Rescuer":
                        list = Arrays.asList("Snake Rescuer");
                        break;
                    case "BWSSB":
                        list = Arrays.asList("Assistant Executive Engineer", "Assistant Engineer", "Assistant Executive Engineer",
                                "Assistant Engineer");
                        break;
                    case "Pollution control":
                        list = Arrays.asList("Pollution control Personnel");
                        break;
                    case "BESCOM":
                        list = Arrays.asList("Assistant Executive Engineer");
                        break;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapter.notifyDataSetChanged();
                s3.setAdapter(adapter);
                s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        specialityEt.setClickable(true);
        specialityEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisterActivity.this, SpecialityActivity.class);
                startActivityForResult(intent, 1);
                //startActivity(intent);


            }
        });

        register = (Button) findViewById(R.id.register_submit_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameText, emailText, mobileText, addressText, landmarkText, cityText, pincodeText, passwordText, confirmPasswordText, zoneText, localityText;
                nameText = name.getText().toString().trim();
                emailText = email.getText().toString().trim();
                mobileText = mobile.getText().toString().trim();
                addressText = address.getText().toString().trim();
                //landmarkText = landmark.getText().toString().trim();
                cityText = city.getText().toString().trim();
//                pincodeText = pincode.getText().toString().trim();
                passwordText = password.getText().toString().trim();
                confirmPasswordText = confirmPassword.getText().toString().trim();
                zoneText = (String) s1.getSelectedItem();
                localityText = (String) s2.getSelectedItem();




                /*
                check if all the fields are filled
                 */
                if (nameText.contentEquals("") ||
                        emailText.contentEquals("") ||
                        mobileText.contentEquals("") ||
                        addressText.contentEquals("") ||
                        cityText.contentEquals("") ||
                        passwordText.contentEquals("") ||
                        confirmPasswordText.contentEquals("")
                        ) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            RegisterActivity.this);
                    builder1.setMessage("Please fill all the fields!");
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

                /*
                check for password and confirm password
                 */
                else if (!passwordText.contentEquals(confirmPasswordText)) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            RegisterActivity.this);
                    builder1.setMessage("Passwords do not match");
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

                /*
                call the async task
                 */
                else {
                    /*
                    build the hashmap
                     */

                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put("name", nameText);
                    data.put("password", passwordText);
                    data.put("address", addressText);
                    data.put("zone", zoneText);
                    data.put("locality", localityText);
                    if (flagMale) {
                        data.put("gender", "male");
                    } else {
                        data.put("gender", "female");
                    }
                    data.put("city", cityText);
                    data.put("mobile", mobileText);
                    data.put("email", emailText);
                    data.put("designation", (String) s3.getSelectedItem());
                    data.put("agency", (String) spagency.getSelectedItem());

                    data.put("speciality", specialityEt.getText().toString());

                    /*
                    call the registe async task
                     */
                    new RegisterAsyncTask(RegisterActivity.this, profileImageBitmap).execute(data);
                }


            }
        });


    }


//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        // TODO Auto-generated method stub
//
//        super.onActivityCreated(savedInstanceState);
//
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
////    }
//
//
}
