package uk.co.o2.android.helloroboguice.fragments;


import android.os.Bundle;

import roboguice.fragment.RoboListFragment;

/**
 * Created by hostova1 on 08/08/2014.
 */
public abstract class BaseListFragment<T extends RoboListFragment> extends RoboListFragment {
    public static BaseListFragment newInstance(Class<? extends BaseListFragment> klass, Bundle arguments)
            throws java.lang.InstantiationException, IllegalAccessException {
        BaseListFragment fragment = klass.newInstance();
        if (arguments != null) {
            fragment.setArguments(arguments);
        }
        return fragment;
    }
}
