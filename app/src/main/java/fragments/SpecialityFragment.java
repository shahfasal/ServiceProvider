package fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;

import adapters.SpecialityAdapter;
import quadvision.serviceprovider.R;
import quadvision.serviceprovider.Speciality;

/**
 * Created by abhishek on 02-07-2015.
 */
public class SpecialityFragment extends Fragment {
    Activity activity;
    ListView lv;
    EditText inputSearch;
    ArrayAdapter adapter;
    Fragment fragment;
    private Speciality[]services={new Speciality("Road",false),
            new Speciality("footpath",false),
            new Speciality("garbage",false),
            new Speciality("drainage",false),
            new Speciality("dead bird",false),
            new Speciality("dead animal",false),
            new Speciality("mosquitoes",false),
            new Speciality("Road Damage",false),
            new Speciality("Storm water drainage",false),
            new Speciality("Potholes",false),
            new Speciality("Site Cleaning",false),
            new Speciality("Road asphalt",false),
            new Speciality("incomplete road repairs",false),
            new Speciality("Pavement stone slab",false),
            new Speciality("Debris of buildings",false),
            new Speciality("Pavement is broken",false),
            new Speciality("play ground maintenance",false),
            new Speciality("Road",false),
            new Speciality("footpath",false),
            new Speciality("Garbage",false),
            new Speciality("drainage",false),
            new Speciality("Road Damage",false),
            new Speciality("Storm water drainage",false),
            new Speciality("Potholes",false),
            new Speciality("Site Cleaning",false),
            new Speciality("Road asphalt",false),
            new Speciality("incomplete road repairs",false),
            new Speciality("Pavement stone slab",false),
            new Speciality("Debris of buildings",false),
            new Speciality("Pavement is broken",false),
            new Speciality("play ground maintenance",false),
            new Speciality("Road",false),
            new Speciality("footpath",false),
            new Speciality("Garbage",false),
            new Speciality("drainage",false),
            new Speciality("Road Damage",false),
            new Speciality("Storm water drainage",false),
            new Speciality("Potholes",false),
            new Speciality("Site Cleaning",false),
            new Speciality("Road asphalt",false),
            new Speciality("incomplete road repairs",false),
            new Speciality("Pavement stone slab",false),
            new Speciality("Debris of buildings",false),
            new Speciality("Pavement is broken",false),
            new Speciality("play ground maintenance",false),
            new Speciality("Food adulteration",false),
            new Speciality("Shops Licenses",false),
            new Speciality("Street Vendor Management",false),
            new Speciality("Food vendor on footpath",false),
            new Speciality("dog menace",false),
            new Speciality("stray animals",false),
            new Speciality("dead animals",false),
            new Speciality("dead bird",false),
            new Speciality("Mosquitoes",false),
            new Speciality("garbage",false),
            new Speciality("Remove tree litter",false),
            new Speciality("illegal advertisments",false),
            new Speciality("Property tax",false),
            new Speciality("voter id",false),
            new Speciality("khatha transfer",false),
            new Speciality("banner permissions",false),
            new Speciality("illegal banners", false),
            new Speciality("illegal advertisments",false),
            new Speciality("Property tax",false),
            new Speciality("voter id",false),
            new Speciality("khatha transfer",false),
            new Speciality("banner permissions",false),
            new Speciality("illegal banners",false),
            new Speciality("Park Maintainence", false),
            new Speciality("Divider plants maintainence",false),
            new Speciality("Street Light Maintainence",false),
            new Speciality("switch for street light",false),
            new Speciality("Timer for streetlight",false),
            new Speciality("Tree cutting",false),
            new Speciality("Tree pruning",false),
            new Speciality("Tree saplings plantation",false),
            new Speciality("Tree Maintainence",false),
            new Speciality("broken tree",false),
            new Speciality("Cremation of Bodies",false),
            new Speciality("Mortuary",false),
            new Speciality("Fire",false),
            new Speciality("Eve teasing",false),
            new Speciality("murder",false),
            new Speciality("theft",false),
            new Speciality("neighbour conflicts",false),
            new Speciality("Snake Rescue",false),
            new Speciality("Blocked Storm Water drain",false)};




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.service_list,container,false);
    }
    @Override
    public void onAttach(Activity activity) {
// TODO Auto-generated method stub
        super.onAttach(activity);
        this.activity = activity;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragment=this;
        lv = (ListView)activity.findViewById(R.id.list_view);

        inputSearch = (EditText) activity.findViewById(R.id.inputSearch);
        //adapter = new ArrayAdapter<String>(activity, R.layout.service_list_item, R.id.service_name,services);
        adapter=new SpecialityAdapter(activity,R.layout.service_list_item, Arrays.asList(services));
        lv.setAdapter(adapter);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ((Button)activity.findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ((ActionBarActivity)activity).getSupportFragmentManager().popBackStack("register", FragmentManager.POP_BACK_STACK_INCLUSIVE);//.beginTransaction();


            }
        });

    }
}
