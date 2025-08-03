package com.interview.sc;

import lombok.Data;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yangkuo
 * @date 2025/8/1
 * @description
 */
public class LoginUsers {
    public static void main(String[] args) {
        String[][] stringArray = getStringArray();

        // 先尝试分组，分组后再计算，同一天的登录算一个，计算出连续登陆超过3天的用户ID
        List<LoginRecord> data = Arrays.stream(stringArray).map(record -> {
            LoginRecord loginRecord = new LoginRecord();
            loginRecord.setUserId(record[0]);
            loginRecord.setLoginTime(record[1].split(" ")[0]);
            return loginRecord;
        }).collect(Collectors.toList());

        int minLoginDays = 3;
        Set consecutiveLoginUsers = findConsecutiveLoginUsers(data, minLoginDays);
        System.out.println("连续登陆超过" + minLoginDays + "天的用户ID:" + consecutiveLoginUsers);

    }
    private static Set findConsecutiveLoginUsers(List<LoginRecord> data, int minLoginDays) {
        // 按照用户ID分组  收集用户登录的日期set集合
        Map<String, Set<String>> userDates = data.stream()
                .collect(Collectors.groupingBy(
                        LoginRecord::getUserId,
                        Collectors.mapping(
                                LoginRecord::getLoginTime,
                                Collectors.toSet()
                        )
                ));

        return userDates.entrySet().stream()
                .filter(entry -> hasConsecutiveDays(entry.getValue(), minLoginDays))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    private static boolean hasConsecutiveDays(Set<String> dates, int minLoginDays) {
        if (dates.size() < minLoginDays) {
            return false;
        }
        List<LocalDate> sortedDates = dates.stream()
                .map(date -> LocalDate.parse(date))
                .sorted()
                .collect(Collectors.toList());
        int consecutiveDays = 1;
        for (int i = 1; i < sortedDates.size(); i++) {
            LocalDate currentDate = sortedDates.get(i);
            LocalDate previousDate = sortedDates.get(i - 1);
            if (currentDate.isEqual(previousDate.plusDays(1))) {
                consecutiveDays++;
            } else {
                consecutiveDays = 1;
            }
            if (consecutiveDays >= minLoginDays) {
                return true;
            }
        }
        return false;
    }

    @Data
    static class LoginRecord {
        private String userId;
        private String loginTime;
    }

    private static String[][] getStringArray() {
        String[][] stringArray = new String[13][2];
        stringArray[0][0] = "001";
        stringArray[0][1] = "2023-02-01 21:10:01";
        stringArray[1][0] = "001";
        stringArray[1][1] = "2023-02-01 22:10:02";
        stringArray[2][0] = "002";
        stringArray[2][1] = "2023-02-01 21:10:01";
        stringArray[3][0] = "002";
        stringArray[3][1] = "2023-02-02 12:10:01";
        stringArray[4][0] = "002";
        stringArray[4][1] = "2023-02-02 15:10:01";
        stringArray[5][0] = "001";
        stringArray[5][1] = "2023-02-02 21:10:01";
        stringArray[6][0] = "001";
        stringArray[6][1] = "2023-02-03 21:10:01";
        stringArray[7][0] = "001";
        stringArray[7][1] = "2023-02-04 21:10:01";
        stringArray[8][0] = "002";
        stringArray[8][1] = "2023-02-04 10:10:01";
        stringArray[9][0] = "002";
        stringArray[9][1] = "2023-02-06 12:10:01";
        stringArray[10][0] = "002";
        stringArray[10][1] = "2023-02-05 11:10:01";
        stringArray[11][0] = "002";
        stringArray[11][1] = "2023-02-07 15:10:01";
        stringArray[12][0] = "002";
        stringArray[12][1] = "2023-02-08 10:10:01";
        return stringArray;
    }

}
