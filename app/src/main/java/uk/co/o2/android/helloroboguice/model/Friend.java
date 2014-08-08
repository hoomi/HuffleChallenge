package uk.co.o2.android.helloroboguice.model;

/**
 * Created by hostova1 on 08/08/2014.
 */
public class Friend {
    public String id;
    public String image;
    public String name;
    public int primary;
    public boolean isSelected = false;

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Friend)) {
            return false;
        }
        Friend f = (Friend) o;

        return f.id != null && f.id.equals(id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}