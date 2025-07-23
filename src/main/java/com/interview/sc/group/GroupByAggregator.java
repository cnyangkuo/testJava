package com.interview.sc.group;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author yangkuo
 * @date 2025/7/23
 * @description
 */
public class GroupByAggregator<T> {
    private final Collection<T> data;
    private final List<String> groupProperties = new ArrayList<>();
    private final List<AggSpec> aggSpecs = new ArrayList<>();
    private final Function<T, Map<String, Object>> propertyExtractor;

    public GroupByAggregator(Collection<T> data, Function<T, Map<String, Object>> propertyExtractor) {
        this.data = data;
        this.propertyExtractor = propertyExtractor;
    }

    public GroupByAggregator<T> groupBy(String... properties) {
        groupProperties.addAll(Arrays.asList(properties));
        return this;
    }

    public GroupByAggregator<T> aggregate(String property, AggType aggType) {
        aggSpecs.add(new AggSpec(property, aggType));
        return this;
    }

    public Map<GroupKey, AggResult> execute() {
        // 分组阶段
        Map<GroupKey, List<T>> groupedData = data.stream()
                .collect(Collectors.groupingBy(
                        item -> createGroupKey(item)
                ));

        // 聚合阶段
        Map<GroupKey, AggResult> result = new HashMap<>();

        for (Map.Entry<GroupKey, List<T>> entry : groupedData.entrySet()) {
            GroupKey groupKey = entry.getKey();
            List<T> groupItems = entry.getValue();
            AggResult aggResult = new AggResult();

            // 对每个聚合规范进行计算
            for (AggSpec spec : aggSpecs) {
                String property = spec.getProperty();
                AggType aggType = spec.getAggType();

                switch (aggType) {
                    case SUM:
                        double sum = groupItems.stream()
                                .mapToDouble(item -> getNumericValue(item, property))
                                .sum();
                        aggResult.put(property + "_SUM", sum);
                        break;

                    case AVG:
                        double avg = groupItems.stream()
                                .mapToDouble(item -> getNumericValue(item, property))
                                .average()
                                .orElse(0);
                        aggResult.put(property + "_AVG", avg);
                        break;

                    case MAX:
                        double max = groupItems.stream()
                                .mapToDouble(item -> getNumericValue(item, property))
                                .max()
                                .orElse(0);
                        aggResult.put(property + "_MAX", max);
                        break;

                    case MIN:
                        double min = groupItems.stream()
                                .mapToDouble(item -> getNumericValue(item, property))
                                .min()
                                .orElse(0);
                        aggResult.put(property + "_MIN", min);
                        break;

                    case COUNT:
                        long count = groupItems.stream()
                                .map(item -> getPropertyValue(item, property))
                                .filter(Objects::nonNull)
                                .count();
                        aggResult.put(property + "_COUNT", count);
                        break;
                }
            }

            result.put(groupKey, aggResult);
        }

        return result;
    }

    private GroupKey createGroupKey(T item) {
        Map<String, Object> keyValues = new HashMap<>();
        Map<String, Object> allProperties = propertyExtractor.apply(item);

        for (String prop : groupProperties) {
            keyValues.put(prop, allProperties.get(prop));
        }

        return new GroupKey(keyValues);
    }

    private double getNumericValue(T item, String property) {
        Object value = getPropertyValue(item, property);
        if (value == null) return 0;

        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }

        try {
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private Object getPropertyValue(T item, String property) {
        return propertyExtractor.apply(item).get(property);
    }
}