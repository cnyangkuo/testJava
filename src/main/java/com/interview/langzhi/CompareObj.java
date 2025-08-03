package com.interview.langzhi;

import java.lang.reflect.Field;
import java.util.*;
import java.util.Date;
import java.util.Objects;

/**
 * 对象比较工具类
 * 用于比较两个不同类的对象，检查它们公共属性的值是否一致
 * 支持驼峰命名和下划线命名的属性映射
 * 使用示例
 * CompareObj.of(obj1, obj2)
 * .ignore("id", "createTime")
 * .map("create_at", "create_Time")
 * .compare();
 *
 * @author yangkuo
 * @date 2025/8/3
 * @description
 */
public class CompareObj {
    private final Object obj1;
    private final Object obj2;
    private final Set<String> ignoredFields;
    private final Map<String, String> fieldMappings;

    private CompareObj(Object obj1, Object obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
        this.ignoredFields = new HashSet<>();
        this.fieldMappings = new HashMap<>();
    }

    /**
     * 创建比较器实例
     *
     * @param obj1 第一个对象
     * @param obj2 第二个对象
     * @return CompareObj实例
     */
    public static CompareObj of(Object obj1, Object obj2) {
        return new CompareObj(obj1, obj2);
    }

    /**
     * 添加需要忽略的字段
     *
     * @param fieldNames 字段名
     * @return CompareObj实例
     */
    public CompareObj ignore(String... fieldNames) {
        Collections.addAll(ignoredFields, fieldNames);
        return this;
    }

    /**
     * 添加字段映射关系
     *
     * @param field1 第一个对象的字段名
     * @param field2 第二个对象的字段名
     * @return CompareObj实例
     */
    public CompareObj map(String field1, String field2) {
        // 创建标准化字段名作为键
        String normalizedKey = normalizeFieldName(field1) + "#" + normalizeFieldName(field2);

        fieldMappings.put(field1, field2);
        return this;
    }

    /**
     * 执行比较操作
     *
     * @return 比较结果
     */
    public ComparisonResult compare() {
        if (obj1 == null || obj2 == null) {
            return new ComparisonResult(false, Collections.emptyList(), Collections.emptyList());
        }

        Map<String, Field> obj1Fields = getAllFields(obj1.getClass());
        Map<String, Field> obj2Fields = getAllFields(obj2.getClass());

        List<String> commonFields = new ArrayList<>();
        List<FieldComparison> differences = new ArrayList<>();

        // 处理显式映射的字段
        for (Map.Entry<String, String> mapping : fieldMappings.entrySet()) {
            String field1Name = mapping.getKey();
            String field2Name = mapping.getValue();

            Field field1 = obj1Fields.get(field1Name);
            Field field2 = obj2Fields.get(field2Name);

            if (field1 != null && field2 != null) {
                // 检查是否在忽略列表中
                if (!ignoredFields.contains(field1Name) && !ignoredFields.contains(field2Name)) {
                    commonFields.add(field1Name + " <-> " + field2Name);
                    compareFields(field1Name, field2Name, field1, field2, differences);
                }
            }
        }

        // 查找公共属性并比较值（排除已处理的映射字段）
        Set<String> processedFields = fieldMappings.keySet();
        for (Map.Entry<String, Field> entry1 : obj1Fields.entrySet()) {
            String fieldName1 = entry1.getKey();
            
            // 跳过已处理的字段和忽略的字段
            if (processedFields.contains(fieldName1) || ignoredFields.contains(fieldName1)) {
                continue;
            }

            Field field1 = entry1.getValue();

            // 查找对应的字段（考虑驼峰和下划线命名）
            String normalizedFieldName1 = normalizeFieldName(fieldName1);
            Field field2 = findMatchingField(normalizedFieldName1, obj2Fields);

            if (field2 != null && !ignoredFields.contains(field2.getName())) {
                commonFields.add(fieldName1 + " <-> " + field2.getName());
                compareFields(fieldName1, field2.getName(), field1, field2, differences);
            }
        }

        return new ComparisonResult(differences.isEmpty(), commonFields, differences);
    }

    /**
     * 比较两个字段的值
     *
     * @param fieldName1 第一个字段名
     * @param fieldName2 第二个字段名
     * @param field1 第一个字段
     * @param field2 第二个字段
     * @param differences 差异列表
     */
    private void compareFields(String fieldName1, String fieldName2, Field field1, Field field2, List<FieldComparison> differences) {
        try {
            field1.setAccessible(true);
            field2.setAccessible(true);

            Object value1 = field1.get(obj1);
            Object value2 = field2.get(obj2);

            // 比较值是否相等（考虑类型转换）
            if (!areValuesEqual(value1, value2)) {
                differences.add(new FieldComparison(fieldName1, fieldName2, value1, value2));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("无法访问字段: " + fieldName1, e);
        }
    }

    /**
     * 比较两个不同对象的公共属性值是否一致（静态方法）
     * 兼容驼峰命名和下划线命名的属性映射
     *
     * @param obj1 第一个对象
     * @param obj2 第二个对象
     * @return 比较结果，包含相同属性名和不同值的属性列表
     */
    public static ComparisonResult compare(Object obj1, Object obj2) {
        return CompareObj.of(obj1, obj2).compare();
    }

    /**
     * 比较两个值是否相等，考虑类型转换的情况
     *
     * @param value1 第一个值
     * @param value2 第二个值
     * @return 如果值相等返回true，否则返回false
     */
    private static boolean areValuesEqual(Object value1, Object value2) {
        // 使用Objects.equals进行基本比较
        if (Objects.equals(value1, value2)) {
            return true;
        }

        // 如果其中一个为null，则不相等
        if (value1 == null || value2 == null) {
            return false;
        }

        // 尝试进行类型转换比较
        return convertAndCompare(value1, value2);
    }

    /**
     * 尝试进行类型转换后比较
     *
     * @param value1 第一个值
     * @param value2 第二个值
     * @return 如果转换后相等返回true，否则返回false
     */
    private static boolean convertAndCompare(Object value1, Object value2) {
        // 处理Date与时间戳的比较
        if (isDate(value1) && isTimestamp(value2)) {
            return dateToMillis(value1) == toLong(value2);
        }
        
        if (isDate(value2) && isTimestamp(value1)) {
            return toLong(value1) == dateToMillis(value2);
        }

        // 处理布尔值与数字的比较
        if (isBoolean(value1) && isNumeric(value2)) {
            return booleanToDouble(value1) == toDouble(value2);
        }
        
        if (isBoolean(value2) && isNumeric(value1)) {
            return toDouble(value1) == booleanToDouble(value2);
        }

        // 处理数字类型之间的比较
        if (isNumeric(value1) && isNumeric(value2)) {
            try {
                double num1 = toDouble(value1);
                double num2 = toDouble(value2);
                return Double.compare(num1, num2) == 0;
            } catch (NumberFormatException e) {
                // 转换失败，无法比较
                return false;
            }
        }

        // 处理字符串与数字的比较
        if (value1 instanceof String && isNumeric(value2)) {
            try {
                double num1 = Double.parseDouble((String) value1);
                double num2 = toDouble(value2);
                return Double.compare(num1, num2) == 0;
            } catch (NumberFormatException e) {
                // 转换失败，尝试直接比较字符串
                return value1.toString().equals(value2.toString());
            }
        }

        if (value2 instanceof String && isNumeric(value1)) {
            try {
                double num1 = toDouble(value1);
                double num2 = Double.parseDouble((String) value2);
                return Double.compare(num1, num2) == 0;
            } catch (NumberFormatException e) {
                // 转换失败，尝试直接比较字符串
                return value1.toString().equals(value2.toString());
            }
        }

        // 处理字符串与布尔值的比较
        if (value1 instanceof String && isBoolean(value2)) {
            try {
                boolean bool1 = Boolean.parseBoolean((String) value1);
                boolean bool2 = (Boolean) value2;
                return bool1 == bool2;
            } catch (Exception e) {
                // 转换失败，尝试直接比较字符串
                return value1.toString().equals(value2.toString());
            }
        }

        if (value2 instanceof String && isBoolean(value1)) {
            try {
                boolean bool1 = (Boolean) value1;
                boolean bool2 = Boolean.parseBoolean((String) value2);
                return bool1 == bool2;
            } catch (Exception e) {
                // 转换失败，尝试直接比较字符串
                return value1.toString().equals(value2.toString());
            }
        }

        // 其他情况，尝试转换为字符串比较
        return value1.toString().equals(value2.toString());
    }

    /**
     * 判断对象是否为Date类型
     *
     * @param obj 对象
     * @return 如果是Date类型返回true，否则返回false
     */
    private static boolean isDate(Object obj) {
        return obj instanceof Date || 
               (obj instanceof String && isDateString((String) obj));
    }

    /**
     * 判断字符串是否表示日期（简单判断）
     *
     * @param str 字符串
     * @return 如果可能表示日期返回true，否则返回false
     */
    private static boolean isDateString(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        // 简单判断是否包含日期特征（如连字符或斜杠）
        return str.contains("-") || str.contains("/") || str.contains(":");
    }

    /**
     * 将Date转换为毫秒时间戳
     *
     * @param obj Date对象
     * @return 毫秒时间戳
     */
    private static long dateToMillis(Object obj) {
        if (obj instanceof Date) {
            return ((Date) obj).getTime();
        } else if (obj instanceof String) {
            // 简单处理，实际应用中可能需要使用SimpleDateFormat解析
            try {
                return Date.parse((String) obj);
            } catch (Exception e) {
                throw new IllegalArgumentException("无法将字符串解析为日期: " + obj);
            }
        } else {
            throw new IllegalArgumentException("无法将对象转换为日期: " + obj);
        }
    }

    /**
     * 判断对象是否为时间戳类型
     *
     * @param obj 对象
     * @return 如果是时间戳类型返回true，否则返回false
     */
    private static boolean isTimestamp(Object obj) {
        // 时间戳通常为Long类型或可转换为Long的字符串
        return obj instanceof Long || obj instanceof Integer ||
               (obj instanceof String && isTimestampString((String) obj));
    }

    /**
     * 判断字符串是否表示时间戳
     *
     * @param str 字符串
     * @return 如果表示时间戳返回true，否则返回false
     */
    private static boolean isTimestampString(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        
        try {
            Long.parseLong(str);
            // 时间戳通常为10位或13位数字
            return str.length() == 10 || str.length() == 13;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 将对象转换为long类型
     *
     * @param obj 对象
     * @return long值
     * @throws NumberFormatException 如果无法转换为数字
     */
    private static long toLong(Object obj) throws NumberFormatException {
        if (obj instanceof Long) {
            return (Long) obj;
        } else if (obj instanceof Integer) {
            return (Integer) obj;
        } else if (obj instanceof String) {
            return Long.parseLong((String) obj);
        } else {
            throw new NumberFormatException("无法将对象转换为Long: " + obj);
        }
    }

    /**
     * 判断对象是否为布尔类型
     *
     * @param obj 对象
     * @return 如果是布尔类型返回true，否则返回false
     */
    private static boolean isBoolean(Object obj) {
        return obj instanceof Boolean || 
               (obj instanceof String && isBooleanString((String) obj));
    }

    /**
     * 判断字符串是否表示布尔值
     *
     * @param str 字符串
     * @return 如果表示布尔值返回true，否则返回false
     */
    private static boolean isBooleanString(String str) {
        if (str == null) {
            return false;
        }
        return "true".equalsIgnoreCase(str) || "false".equalsIgnoreCase(str);
    }

    /**
     * 将布尔值转换为double类型 (true=1.0, false=0.0)
     *
     * @param obj 布尔值对象
     * @return double值
     */
    private static double booleanToDouble(Object obj) {
        if (obj instanceof Boolean) {
            return ((Boolean) obj) ? 1.0 : 0.0;
        } else if (obj instanceof String) {
            return Boolean.parseBoolean((String) obj) ? 1.0 : 0.0;
        } else {
            throw new IllegalArgumentException("无法将对象转换为布尔值: " + obj);
        }
    }

    /**
     * 判断对象是否为数字类型
     *
     * @param obj 对象
     * @return 如果是数字类型返回true，否则返回false
     */
    private static boolean isNumeric(Object obj) {
        return obj instanceof Number || 
               obj instanceof Character ||
               (obj instanceof String && isNumericString((String) obj));
    }

    /**
     * 判断字符串是否表示数字
     *
     * @param str 字符串
     * @return 如果表示数字返回true，否则返回false
     */
    private static boolean isNumericString(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 将对象转换为double类型
     *
     * @param obj 对象
     * @return double值
     * @throws NumberFormatException 如果无法转换为数字
     */
    private static double toDouble(Object obj) throws NumberFormatException {
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        } else if (obj instanceof Character) {
            return (double) ((Character) obj).charValue();
        } else if (obj instanceof String) {
            return Double.parseDouble((String) obj);
        } else {
            throw new NumberFormatException("无法将对象转换为数字: " + obj);
        }
    }

    /**
     * 获取类的所有字段（包括父类）
     *
     * @param clazz 类对象
     * @return 字段映射，键为字段名，值为字段对象
     */
    private static Map<String, Field> getAllFields(Class<?> clazz) {
        Map<String, Field> fields = new HashMap<>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                fields.put(field.getName(), field);
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    /**
     * 标准化字段名（转换为小写，移除下划线）
     *
     * @param fieldName 字段名
     * @return 标准化后的字段名
     */
    private static String normalizeFieldName(String fieldName) {
        return fieldName.toLowerCase().replace("_", "");
    }

    /**
     * 查找匹配的字段
     *
     * @param normalizedFieldName 标准化后的字段名
     * @param fields 字段映射
     * @return 匹配的字段，如果未找到返回null
     */
    private static Field findMatchingField(String normalizedFieldName, Map<String, Field> fields) {
        for (Map.Entry<String, Field> entry : fields.entrySet()) {
            String fieldName = entry.getKey();
            if (normalizedFieldName.equals(normalizeFieldName(fieldName))) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * 标准化字段名（转换为驼峰命名）
     * 
     * @param underscoreName 下划线命名的字段名
     * @return 驼峰命名的字段名
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

    /**
     * 比较结果类
     */
    public static class ComparisonResult {
        private final boolean identical;
        private final List<String> commonFields;
        private final List<FieldComparison> differences;

        public ComparisonResult(boolean identical, List<String> commonFields, List<FieldComparison> differences) {
            this.identical = identical;
            this.commonFields = commonFields;
            this.differences = differences;
        }

        public boolean isIdentical() {
            return identical;
        }

        public List<String> getCommonFields() {
            return commonFields;
        }

        public List<FieldComparison> getDifferences() {
            return differences;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("比较结果:\n");
            sb.append("  是否完全一致: ").append(identical).append("\n");
            sb.append("  公共属性数量: ").append(commonFields.size()).append("\n");
            sb.append("  公共属性: ").append(String.join(", ", commonFields)).append("\n");

            if (!differences.isEmpty()) {
                sb.append("  不同值的属性:\n");
                for (FieldComparison diff : differences) {
                    sb.append("    ").append(diff.getFieldName1())
                            .append(" <-> ").append(diff.getFieldName2())
                            .append(": ").append(diff.getValue1())
                            .append(" != ").append(diff.getValue2()).append("\n");
                }
            }

            return sb.toString();
        }
    }

    /**
     * 字段比较结果类
     */
    public static class FieldComparison {
        private final String fieldName1;
        private final String fieldName2;
        private final Object value1;
        private final Object value2;

        public FieldComparison(String fieldName1, String fieldName2, Object value1, Object value2) {
            this.fieldName1 = fieldName1;
            this.fieldName2 = fieldName2;
            this.value1 = value1;
            this.value2 = value2;
        }

        public String getFieldName1() {
            return fieldName1;
        }

        public String getFieldName2() {
            return fieldName2;
        }

        public Object getValue1() {
            return value1;
        }

        public Object getValue2() {
            return value2;
        }
    }

    /**
     * 测试示例
     */
    public static void main(String[] args) {
        // 测试类1：使用下划线命名
        class PersonDto {
            private String work_item_id;
            private String user_name;
            private String age; // 字符串类型
            private double salary;
            private Date update_at; // Date类型
            private String is_active; // 字符串布尔值

            public PersonDto(String work_item_id, String user_name, String age, double salary, Date update_at, String is_active) {
                this.work_item_id = work_item_id;
                this.user_name = user_name;
                this.age = age;
                this.salary = salary;
                this.update_at = update_at;
                this.is_active = is_active;
            }
        }

        // 测试类2：使用驼峰命名
        class PersonEntity {
            private String workItemId;
            private String userName;
            private int age; // 整数类型
            private double salary;
            private String updateTime; // 时间戳类型
            private boolean isActive; // 布尔类型

            public PersonEntity(String workItemId, String userName, int age, double salary, String updateTime, boolean isActive) {
                this.workItemId = workItemId;
                this.userName = userName;
                this.age = age;
                this.salary = salary;
                this.updateTime = updateTime;
                this.isActive = isActive;
            }
        }

        // 创建测试对象 (使用相同的时间值)
        Date now = new Date();
        long nowMillis = now.getTime();
        
        PersonDto personDto = new PersonDto("12345", "John Doe", "30", 50000.0, now, "1");
        PersonEntity personEntity = new PersonEntity("12345", "John Doe", 30, 50000.0, nowMillis + "", true);

        // 使用流式API进行比较
        System.out.println("=== 使用流式API进行比较 ===");
        ComparisonResult result1 = CompareObj.of(personDto, personEntity)
                .ignore("salary")  // 忽略salary字段
                .map("update_at", "updateTime")  // 映射update_at和updateTime字段
                .map("is_active", "isActive")    // 映射is_active和isActive字段
                .compare();
        System.out.println(result1);
    }
}