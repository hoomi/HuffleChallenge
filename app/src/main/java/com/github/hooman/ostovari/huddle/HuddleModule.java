package com.github.hooman.ostovari.huddle;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.novoda.imageloader.core.ImageManager;
import com.novoda.imageloader.core.model.ImageTagFactory;

import com.github.hooman.ostovari.huddle.ui.ProfileImageProvider;
import com.github.hooman.ostovari.huddle.ui.ProfileLoader;
import com.github.hooman.ostovari.huddle.utils.GsonProvider;
import com.github.hooman.ostovari.huddle.utils.ImageManagerProvider;

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
