package lt.sciil.eLPA.database;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;


public class JsonObjectClassPersister extends StringType {

    private static final JsonObjectClassPersister INSTANCE = new JsonObjectClassPersister();
    private final JsonParser jsonParser;

    private JsonObjectClassPersister() {
        super(SqlType.STRING, new Class<?>[]{JsonObject.class});
        jsonParser = new JsonParser();
    }

    public static JsonObjectClassPersister getSingleton() {
        return INSTANCE;
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        JsonObject myFieldClass = (JsonObject) javaObject;
        return myFieldClass != null ? getJsonFromJsonObject(myFieldClass) : null;
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        return sqlArg != null ? getJsonObjectFromJson((String) sqlArg) : null;
    }

    private String getJsonFromJsonObject(JsonObject jsonObject) {
        return jsonObject.toString();
    }

    private JsonObject getJsonObjectFromJson(String json) {
        return jsonParser.parse(json).getAsJsonObject();
    }
}