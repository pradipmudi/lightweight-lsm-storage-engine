package com.sstable.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Represents a key-value pair
public class KeyValuePair {
    private static final Logger logger = LoggerFactory.getLogger(KeyValuePair.class);
    String key;
    String value;

    public KeyValuePair(String key, String value) {
        this.key = key;
        this.value = value;
        logger.info("Created KeyValuePair with key: {}, value: {}", key, value);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "KeyValuePair{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
