package com.interview.sc.group;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author yangkuo
 * @date 2025/7/23
 * @description
 */
public class GroupKey {
    private final Map<String, Object> values = new HashMap<>();

    public GroupKey(Map<String, Object> values) {
        this.values.putAll(values);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupKey groupKey = (GroupKey) o;
        return values.equals(groupKey.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    @Override
    public String toString() {
        return values.toString();
    }
}
