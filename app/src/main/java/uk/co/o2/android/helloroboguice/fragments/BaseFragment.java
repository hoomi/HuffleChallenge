package uk.co.o2.android.helloroboguice.fragments;

import android.os.Bundle;

import roboguice.fragment.RoboFragment;


/**
 * Created by hostova1 on 07/08/2014.
 */
public abstract class BaseFragment<T extends RoboFragment> extends RoboFragment {
    public static BaseFragment newInstance(Class<? extends BaseFragment> klass, Bundle arguments)
            throws java.lang.InstantiationException, IllegalAccessException {
        BaseFragment fragment = klass.newInstance();
        if (arguments != null) {
            fragment.setArguments(arguments);
        }
        return fragment;
    }
}
