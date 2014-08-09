package uk.co.o2.android.helloroboguice;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Singleton;
import com.novoda.imageloader.core.ImageManager;
import com.novoda.imageloader.core.model.ImageTagFactory;

import uk.co.o2.android.helloroboguice.ui.ProfileImageProvider;
import uk.co.o2.android.helloroboguice.ui.ProfileLoader;
import uk.co.o2.android.helloroboguice.utils.GsonProvider;
import uk.co.o2.android.helloroboguice.utils.ImageManagerProvider;

/**
 * Created by hostova1 on 08/08/2014.
 */
public class HuddleModule extends AbstractModule {

    @Override
    public void configure() {
        bind(Gson.class).toProvider(GsonProvider.class);
        // Bind ImageManager as a singleton
        bind(ImageManager.class)
                .toProvider(new ImageManagerProvider())
                .in(Singleton.class);// Bind ImageTagFactory as a singleton to use for profile images
        bind(ImageTagFactory.class)
                .annotatedWith(ProfileLoader.class)
                .toProvider(new ProfileImageProvider())
                .in(Singleton.class);
    }
}
