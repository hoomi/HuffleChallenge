package uk.co.o2.android.helloroboguice;

import com.google.gson.Gson;
import com.google.inject.Binder;
import com.google.inject.Module;

import uk.co.o2.android.helloroboguice.utils.GsonProvider;

/**
 * Created by hostova1 on 08/08/2014.
 */
public class HuddleModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(Gson.class).toProvider(GsonProvider.class);
    }
}
