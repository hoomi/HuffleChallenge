package uk.co.o2.android.helloroboguice.utils;

import com.google.gson.Gson;
import com.google.inject.Provider;

/**
 * Created by hostova1 on 08/08/2014.
 */
public class GsonProvider implements Provider<Gson> {

    private static Gson GSON;
    @Override
    public synchronized Gson get() {
        if (GSON == null) {
            GSON = new Gson();
        }
        return GSON;
    }
}
