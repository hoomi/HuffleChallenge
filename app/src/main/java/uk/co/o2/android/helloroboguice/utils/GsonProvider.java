package uk.co.o2.android.helloroboguice.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.inject.Provider;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.co.o2.android.helloroboguice.model.Friend;
import uk.co.o2.android.helloroboguice.model.Huddle;

/**
 * Created by hostova1 on 08/08/2014.
 */
public class GsonProvider implements Provider<Gson> {


    @Override
    public synchronized Gson get() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Huddle.class, new HuddleAdapter());
        return gsonBuilder.create();
    }

    public class HuddleAdapter implements JsonDeserializer<Huddle> {

        @Override
        public Huddle deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            Huddle huddle = new Huddle();
            JsonObject jsonObject = json.getAsJsonObject();
            huddle.uid = jsonObject.get("uid").getAsString();
            huddle.version = jsonObject.get("version").getAsString();
            JsonObject response = jsonObject.getAsJsonObject("response");
            Set<Map.Entry<String, JsonElement>> entries = response.entrySet();
            int i = 0;
            for (Map.Entry<String, JsonElement> m : entries) {
                if (i == 0) {
                    huddle.likeString = m.getKey();
                    huddle.recommended = parseFriends(context, m.getValue());
                } else if (i == 1) {
                    huddle.other = parseFriends(context, m.getValue());
                } else if (i == 2) {
                    huddle.all = parseFriends(context, m.getValue());
                } else {
                    break;
                }
                i++;
            }

            return huddle;
        }

        private List<Friend> parseFriends(JsonDeserializationContext context, JsonElement jsonElement) {
            if (jsonElement == null || !jsonElement.isJsonArray()) {
                return null;
            }
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            ArrayList<Friend> returnList = new ArrayList<Friend>(jsonArray.size());
            Friend friend;
            for (JsonElement je : jsonArray) {
                friend = context.deserialize(je, Friend.class);
                returnList.add(friend);
            }
            return returnList;
        }
    }
}


