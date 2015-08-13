package adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import activities.RegisterActivity;
import activities.SpecialityActivity;
import fragments.RegisterUserFragmentNew;
import quadvision.serviceprovider.R;
import quadvision.serviceprovider.Speciality;

/**
 * Created by abhishek on 02-07-2015.
 */
public class SpecialityAdapter extends ArrayAdapter<Speciality> implements Filterable {

    public List<Speciality> specialities = null;
    public List<Speciality> cacheSpecialities = null;
    public SpecialityFilter specialityFilter = new SpecialityFilter();
    int itemLayout;
    Activity activity;

    public SpecialityAdapter(Activity activity, int resource,
                             List<Speciality> objects) {
        super(activity, resource, objects);
        this.specialities = objects;
        this.cacheSpecialities = objects;
        this.itemLayout = resource;
        this.activity = activity;


        // TODO Auto-generated constructor stub
    }

    @Override
    public Speciality getItem(int position) {
        // TODO Auto-generated method stub
        return specialities.get(position);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return specialities.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = activity
                .getLayoutInflater();
        View row = inflater.inflate(R.layout.service_list_item, parent,
                false);
        try {
            Speciality speciality = specialities.get(position);
            ((TextView) row.findViewById(R.id.service_name))
                    .setText(speciality.getName());
            CheckBox checkbox = ((CheckBox) row.findViewById(R.id.checkBox));

            checkbox.setChecked(speciality.isChecked());

            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        SpecialityActivity.specialities.add(specialities.get(position).getName());
                        specialities.get(position).setChecked(true);

                    }
                    if (!isChecked) {
                        for (int i = 0; i < SpecialityActivity.specialities.size(); i++) {
                            if (specialities.get(position).getName().toLowerCase().contains(SpecialityActivity.specialities.get(i).toLowerCase())) {
                                SpecialityActivity.specialities.remove(i);
                                specialities.get(position).setChecked(false);
                            }
                        }
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return row;
        }


    }

    private class SpecialityFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            ArrayList<Speciality> values = new ArrayList<>();
            for (int i = 0; i < cacheSpecialities.size(); i++) {
                Speciality data = cacheSpecialities.get(i);
                if (data.getName().toLowerCase().contains(((String) constraint).toLowerCase()) ||
                        ((String) constraint).toLowerCase().contains(data.getName().toLowerCase())) {
                    values.add(data);
                }
            }
            results.values = values;
            results.count = values.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (((String) constraint).trim().contentEquals("")) {
                specialities = cacheSpecialities;
                notifyDataSetChanged();
            } else {
                if (results.count == 0)
                    notifyDataSetInvalidated();
                else if (results.count > 0) {
                    specialities = (List<Speciality>) results.values;
                    notifyDataSetChanged();
                }
            }
        }


    }

    @Override
    public Filter getFilter() {
        return (specialityFilter == null) ? new SpecialityFilter() : specialityFilter;
    }
}


