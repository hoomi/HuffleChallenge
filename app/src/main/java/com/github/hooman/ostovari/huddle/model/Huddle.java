package com.github.hooman.ostovari.huddle.model;

import java.util.List;

/**
 * Created by hostova1 on 08/08/2014.
 */
public class Huddle {
    public String uid;
    public String version;
    public String likeString;
    public List<Friend> recommended;
    public List<Friend> other;
    public List<Friend> all;
}