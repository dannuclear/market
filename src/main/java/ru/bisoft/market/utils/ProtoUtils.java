package ru.bisoft.market.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ProtoUtils {
    public static LocalDateTime fromTimestamp(com.google.protobuf.Timestamp timestamp) {
        if (timestamp == null || timestamp.getNanos() == 0)
            return null;
        return LocalDateTime.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos(), ZoneOffset.UTC);
    }
}
