package quadvision.serviceprovider;

/**
 * Created by abhishek on 02-07-2015.
 */
public class Speciality {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean checked;

    public Speciality(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }

}
