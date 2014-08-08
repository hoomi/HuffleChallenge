package uk.co.o2.android.helloroboguice.fragments;

import android.os.Bundle;

import com.google.inject.Inject;

import roboguice.event.EventManager;
import roboguice.event.Observes;
import roboguice.fragment.RoboFragment;
import uk.co.o2.android.helloroboguice.model.Huddle;
import uk.co.o2.android.helloroboguice.utils.Logger;


/**
 * Created by hostova1 on 07/08/2014.
 */
public abstract class BaseFragment<T extends RoboFragment> extends RoboFragment {

    @Inject
    protected EventManager eventManager;

    public static BaseFragment newInstance(Class<? extends BaseFragment> klass, Bundle arguments)
            throws java.lang.InstantiationException, IllegalAccessException {
        BaseFragment fragment = klass.newInstance();
        if (arguments != null) {
            fragment.setArguments(arguments);
        }
        return fragment;
    }

    protected void huddleUpdated(@Observes Huddle huddle) {
        Logger.d(this, "new Huddle arrive");
    }
}
