package dwr.MiniaturowaTablica.api.ZTM;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;


public class ztmRepository {

    public static String convertToJson(Object o){
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.excludeFieldsWithModifiers(Modifier.TRANSIENT).create();
        return gson.toJson(o);
    }


}
