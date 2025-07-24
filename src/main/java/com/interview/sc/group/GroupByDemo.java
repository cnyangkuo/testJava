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
        List<Person> personList = Arrays.asList(
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
        GroupByAggregator<Person> aggregator = new GroupByAggregator<>(personList, propertyExtractor);

        Map<GroupKey, AggResult> result = aggregator
//                .groupBy("age", "sex")   // or .groupBy("age").groupBy("sex")
                .groupBy("sex")
                .aggregate("salary", AggType.AVG)
                .aggregate("salary", AggType.MAX)
                .execute();

        /**
         * 1. 灵活的分组机制
         * 动态分组属性：通过groupBy()方法接收任意属性组合
         * 复合键设计：使用GroupKey封装多个属性值，正确重写实现equals/hashCode用于分组
         * 属性提取器：通过函数式接口支持任意对象结构
         *
         * 2. 可扩展的聚合运算
         * 聚合规范：AggSpec封装"属性+运算类型"组合
         * 多聚合支持：支持同时计算多种聚合结果
         * 类型安全：使用泛型确保聚合结果类型安全
         *
         * 3. 流式API设计
         *
         * 4. 性能优化考虑
         * 并行处理：可轻松改为并行流处理大数据集
         * 懒加载：只在execute()时进行计算
         * 内存效率：分组阶段仅存储分组键，不复制完整对象
         *
         */



        // 打印结果
        result.forEach((key, agg) -> {
            System.out.println("分组: " + key);
            System.out.printf("  平均工资: %.2f%n", (Double)agg.get("salary_AVG"));
            System.out.printf("  最高工资: %.2f%n", (Double)agg.get("salary_MAX"));
            System.out.println();
        });
    }
}