package com.github.hooman.ostovari.huddle.servercommunication;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.inject.Inject;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import roboguice.RoboGuice;
import com.github.hooman.ostovari.huddle.model.Friend;
import com.github.hooman.ostovari.huddle.model.Huddle;
import com.github.hooman.ostovari.huddle.utils.Constants;
import com.github.hooman.ostovari.huddle.utils.Logger;

/**
 * Created by hostova1 on 08/08/2014.
 */
public class FriendsAsyncLoader extends AsyncTaskLoader<Huddle> {

    @Inject
    private Gson gson;

    private String serverUrl;
    private String itemId;

    public FriendsAsyncLoader(Context context) {
        super(context);
        RoboGuice.getInjector(context).injectMembers(this);
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public Huddle loadInBackground() {
        Huddle huddle = null;
        if (TextUtils.isEmpty(serverUrl) || TextUtils.isEmpty(itemId)) {
            return null;
        }
        AndroidHttpClient client = AndroidHttpClient.newInstance("Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16", getContext());
        HttpGet httpGet = new HttpGet(addParamsToUrl(serverUrl));
        try {
            HttpResponse response = client.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            Logger.d(this, "Status code: " + status);
            if (status == HttpStatus.SC_OK) {
                String jsonString = EntityUtils.toString(response.getEntity());
                Logger.d(this, "json: " + jsonString);
                huddle = gson.fromJson(jsonString, Huddle.class);
            } else {
                Logger.e(this, response.getStatusLine().getReasonPhrase());
                huddle = gson.fromJson(Constants.TEST_JSON, Huddle.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
//        if (huddle == null) {
//            huddle = createTestHuddle();
//        }
        return huddle;
    }

    private Huddle createTestHuddle() {
        Huddle testHuddle = new Huddle();
        testHuddle.recommended = new ArrayList<Friend>();
        testHuddle.all = new ArrayList<Friend>();
        testHuddle.other = new ArrayList<Friend>();
        Friend friend;
        for (int i = 0; i<30 ; i ++) {
            friend = new Friend();
            friend.id = i + "";
            friend.image = "http://www.gq.com/style/blogs/the-gq-eye/Jim-Carrey-Morning-Shot-96.jpg";
            friend.name = "Jim Carrey " + i;
            friend.primary = i % 2;
            if (i % 3 == 0) {
                testHuddle.recommended.add(friend);
            } else {
                testHuddle.other.add(friend);
            }
            testHuddle.all.add(friend);
        }
        return testHuddle;
    }

    protected String addParamsToUrl(String url) {
        if (!url.endsWith("?"))
            url += "?";

        List<NameValuePair> params = new LinkedList<NameValuePair>();
        params.add(new BasicNameValuePair("app-id", "cos-iphone"));
        params.add(new BasicNameValuePair("app-version", "3.2TA"));
        params.add(new BasicNameValuePair("app-platform", "iphone"));
        params.add(new BasicNameValuePair("auth-fanatix-id", Constants.TEST_AUTH_ID));
        params.add(new BasicNameValuePair("auth-fanatix-token", Constants.TEST_AUTH_TOKEN));
        params.add(new BasicNameValuePair("itemid", itemId));
        params.add(new BasicNameValuePair("include-all", "true"));
        String paramString = URLEncodedUtils.format(params, "utf-8");
        url += paramString;
        Logger.d(this,"url is: " + url);
        return url;
    }
}
