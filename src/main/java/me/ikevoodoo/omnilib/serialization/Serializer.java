package me.ikevoodoo.omnilib.serialization;

public interface Serializer<T> {

    byte[] serialize(T object);

    T deserialize(byte[] bytes);

}
