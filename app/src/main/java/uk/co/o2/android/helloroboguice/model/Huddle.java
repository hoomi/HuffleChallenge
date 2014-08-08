package uk.co.o2.android.helloroboguice.model;

import java.util.List;

/**
 * Created by hostova1 on 08/08/2014.
 */
public class Huddle {
    public String uid;
    public String version;
    public List<Friend> recommended;
    public List<Friend> other;
    public List<Friend> all;
}

class Friend {
    public String id;
    public String image;
    public String name;
    public int primary;
}
