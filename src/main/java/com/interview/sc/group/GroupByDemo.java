package com.interview.sc.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author yangkuo
 * @date 2025/7/23
 * @description
 */
// 测试数据类
@Data
@AllArgsConstructor
@NoArgsConstructor
class Person {
    String name;
    int age;
    String sex;
    String city;
    double salary;

    // 构造函数、getters、setters...
}

public class GroupByDemo {
    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
                new Person("Alice", 28, "F", "New York", 85000),
                new Person("Bob", 32, "M", "Boston", 95000),
                new Person("Charlie", 28, "M", "New York", 78000),
                new Person("Diana", 32, "F", "Boston", 110000)
        );

        // 创建属性提取器
        Function<Person, Map<String, Object>> propertyExtractor = person -> {
            Map<String, Object> props = new HashMap<>();
            props.put("name", person.getName());
            props.put("age", person.getAge());
            props.put("sex", person.getSex());
            props.put("city", person.getCity());
            props.put("salary", person.getSalary());
            return props;
        };

        // 分组聚合：按年龄和性别分组，计算平均工资和最大工资
        GroupByAggregator<Person> aggregator = new GroupByAggregator<>(people, propertyExtractor);

        Map<GroupKey, AggResult> result = aggregator
                .groupBy("age", "sex")
                .aggregate("salary", AggType.AVG)
                .aggregate("salary", AggType.MAX)
                .execute();

        // 打印结果
        result.forEach((key, agg) -> {
            System.out.println("分组: " + key);
            System.out.printf("  平均工资: %.2f%n", agg.get("salary_AVG"));
            System.out.printf("  最高工资: %.2f%n", agg.get("salary_MAX"));
            System.out.println();
        });
    }
}