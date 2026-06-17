import com.google.gson.*;
import java.lang.reflect.Type;

public class EventAdapter implements JsonDeserializer<Event>, JsonSerializer<Event> {
    @Override
    public JsonElement serialize(Event src, Type type, JsonSerializationContext ctx) {
        JsonObject obj = ctx.serialize(src, src.getClass()).getAsJsonObject();
        obj.addProperty("eventClass", src.getClass().getSimpleName());
        return obj;
    }
    @Override
    public Event deserialize(JsonElement json, Type type, JsonDeserializationContext ctx) {
        JsonObject obj = json.getAsJsonObject();
        String eventClass = obj.get("eventClass").getAsString();
        if (eventClass.equals("Practice")) {
            return ctx.deserialize(json, Practice.class);
        } else {
            return ctx.deserialize(json, Competition.class);
        }
    }
}
