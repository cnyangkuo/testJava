package com.interview.sc.group;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.lang.reflect.Field;

/**
 * 聚合器 按照属性灵活分组，分组后完成聚合操作
 * @author yangkuo
 * @date 2025/7/23
 * @description
 */
public class GroupByAggregator<T> {
    private final Collection<T> data;
    private final Function<T, Map<String, Object>> propertyExtractor;

    private final List<String> groupProperties = new ArrayList<>();
    private final List<AggSpec> aggSpecs = new ArrayList<>();

    public GroupByAggregator(Collection<T> data, Class<T> clazz){
        this.data = data;
        // 创建属性提取器
        this.propertyExtractor = generatePropertyExtractor(clazz);
    }

    private static <T> Function<T, Map<String, Object>> generatePropertyExtractor(Class<T> clazz) {
        return obj -> {
            Map<String, Object> props = new HashMap<>();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    String fieldName = field.getName();
                    Object fieldValue = field.get(obj);
                    
                    // 添加原始字段名
                    props.put(fieldName, fieldValue);
                    
                    // 如果字段名包含下划线，则转换为驼峰命名并添加
                    if (fieldName.contains("_")) {
                        String camelCaseName = toCamelCase(fieldName);
                        props.put(camelCaseName, fieldValue);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("字段访问失败: " + field.getName(), e);
                }
            }
            return props;
        };
    }
    
    /**
     * 将下划线命名转换为驼峰命名
     * 例如: work_item_id -> workItemId
     * @param underscoreName 下划线命名的字符串
     * @return 驼峰命名的字符串
     */
    private static String toCamelCase(String underscoreName) {
        if (underscoreName == null || !underscoreName.contains("_")) {
            return underscoreName;
        }
        
        StringBuilder result = new StringBuilder();
        String[] parts = underscoreName.split("_");
        
        // 第一个部分保持小写
        result.append(parts[0].toLowerCase());
        
        // 后续部分首字母大写
        for (int i = 1; i < parts.length; i++) {
            if (!parts[i].isEmpty()) {
                result.append(Character.toUpperCase(parts[i].charAt(0)))
                      .append(parts[i].substring(1).toLowerCase());
            }
        }
        
        return result.toString();
    }
    
//    public GroupByAggregator(Collection<T> data, Function<T, Map<String, Object>> propertyExtractor) {
//        this.data = data;
//        this.propertyExtractor = propertyExtractor;
//    }

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
        if (value == null) {
            return 0;
        }

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