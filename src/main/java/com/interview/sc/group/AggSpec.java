package com.interview.sc.group;

import lombok.Data;

/**
 * @author yangkuo
 * @date 2025/7/23
 * @description
 */
@Data
public class AggSpec {
    private final String property;
    private final AggType aggType;

    public AggSpec(String property, AggType aggType) {
        this.property = property;
        this.aggType = aggType;
    }

    // Getters...
}
