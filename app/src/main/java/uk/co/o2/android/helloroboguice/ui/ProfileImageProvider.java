package uk.co.o2.android.helloroboguice.ui;

import android.app.Application;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.novoda.imageloader.core.model.ImageTagFactory;

import uk.co.o2.android.helloroboguice.R;

/**
 * Created by hostova1 on 09/08/2014.
 */
public class ProfileImageProvider implements Provider<ImageTagFactory> {
    public ProfileImageProvider() {
    }

    @Inject
    private Application app;

    @Inject
    public ImageTagFactory get() {
        int photoSize = app.getResources().getDimensionPixelSize(R.dimen.profile_image_size);
        ImageTagFactory imageTagFactory = ImageTagFactory.newInstance(photoSize, photoSize, R.drawable.ic_launcher);
        imageTagFactory.setSaveThumbnail(true);
        imageTagFactory.setAnimation(android.R.anim.fade_in);
        return imageTagFactory;
    }
}
