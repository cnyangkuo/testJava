package com.interview.sc.group;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangkuo
 * @date 2025/7/23
 * @description
 */
public class AggResult {
    private final Map<String, Object> values = new HashMap<>();

    public void put(String key, Object value) {
        values.put(key, value);
    }

    public <T> T get(String key) {
        return (T) values.get(key);
    }

    @Override
    public String toString() {
        return values.toString();
    }
}
