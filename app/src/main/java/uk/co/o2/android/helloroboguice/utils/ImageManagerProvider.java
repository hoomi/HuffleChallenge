package uk.co.o2.android.helloroboguice.utils;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.novoda.imageloader.core.ImageManager;
import com.novoda.imageloader.core.LoaderSettings;
import com.novoda.imageloader.core.cache.LruBitmapCache;

/**
 * Created by hostova1 on 09/08/2014.
 */
public class ImageManagerProvider implements Provider<ImageManager> {
    private static final int READ_TIMEOUT = 30000;
    private static final int CONNECTION_TIMEOUT = 20000;
    private static final long EXPIRY_TIME = 24 * 60 * 60 * 1000;

    public ImageManagerProvider() {
    }

    @Inject
    private Application app;

    @Inject
    public ImageManager get() {

            LoaderSettings.SettingsBuilder settingsBuilder = new LoaderSettings.SettingsBuilder();

            //Force the urlConnection to disconnect after every call.
            settingsBuilder.withDisconnectOnEveryCall(true);

            //Enable concurrent image loading
            settingsBuilder.withAsyncTasks(true);

            // The default cache uses soft references.
            // With a memory-constrained system like Android, space can be reclaimed too often,
            // limiting the performance of the cache. The LRU cache is intended to solve this problem.
            // It’s particularly useful if your app displays many small images.
            int cachePercentage = 20;
            long memoryClass = ((ActivityManager) app.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
            Logger.d(this, "Memory class: " + memoryClass);
            if (memoryClass >= 128) {
                cachePercentage = 50;
            } else if (memoryClass >= 64 && memoryClass < 128) {
                cachePercentage = 30;
            }
            settingsBuilder.withCacheManager(new LruBitmapCache(app, cachePercentage));

            //Set a specific read timeout
            settingsBuilder.withReadTimeout(READ_TIMEOUT);

            //Set a specific connection timeout
            settingsBuilder.withConnectionTimeout(CONNECTION_TIMEOUT);

            //Enable the multi-threading ability to download image
            settingsBuilder.withAsyncTasks(true);

            //You can setBool a specific directory for caching files on the sdcard
            //settingsBuilder.withCacheDir(new File("/something"));
            return new ImageManager(app, settingsBuilder.build(app));
    }
}
