package me.ikevoodoo.omnilib.serialization.providers;

import me.ikevoodoo.omnilib.serialization.Serializer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SerializerProvider {

    private static final HashMap<Class<?>, Serializer<?>> serializers = new HashMap<>();

    private SerializerProvider() {
    }

    public static <T> void register(Class<T> clazz, Serializer<T> serializer) {
        serializers.put(clazz, serializer);
    }

    public static boolean registerAuto(Class<?> clazz) {
        HashMap<Class<?>, Serializer<?>> fieldSerializers = getFieldSerializers(clazz);
        if(fieldSerializers == null) {
            return false;
        }


        return true;
    }

    @SuppressWarnings("unchecked")
    public static <T> Serializer<T> get(Class<T> clazz) {
        Serializer<?> serializer = serializers.get(clazz);
        if (serializer == null) {
            return null;
        }
        return (Serializer<T>) serializer;
    }

    public static boolean has(Class<?> clazz) {
        return serializers.containsKey(clazz);
    }

    private static HashMap<Class<?>, Serializer<?>> getFieldSerializers(Class<?> clazz) {
        HashMap<Class<?>, Serializer<?>> fieldSerializers = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            Serializer<?> serializer = get(field.getType());
            if(serializer == null) {
                return null;
            }
            fieldSerializers.put(field.getType(), serializer);
        }
        return fieldSerializers;
    }

}
