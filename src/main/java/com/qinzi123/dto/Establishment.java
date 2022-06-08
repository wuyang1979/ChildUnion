package com.qinzi123.dto;

/**
 * @title: Establishment
 * @package: com.qinzi123.dto
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public interface Establishment {

    /**
     * 机构规模枚举
     */
    public enum Scope {
        scope1("1~5", "0"),
        scope2("6~10", "1"),
        scope3("11~30", "2"),
        scope4("31~100", "3"),
        scope5("100+", "4");


        private String value;
        private String lisvalue;

        private Scope(String value, String lisvalue) {
            this.value = value;
            this.lisvalue = lisvalue;
        }

        public static String getValue(String name) {
            Scope[] scopes = values();
            for (Scope scope : scopes) {
                if (scope.lisvalue().equals(name)) {
                    return scope.value();
                }
            }
            return "";
        }


        public static String getName(String value) {
            Scope[] scopes = values();
            for (Scope scope : scopes) {
                if (scope.value().equals(value)) {
                    return scope.lisvalue();
                }
            }
            return "";
        }


        private String value() {
            return this.value;
        }

        private String lisvalue() {
            return this.lisvalue;
        }
    }

    /**
     * 所属行业枚举
     */
    public enum Industry {
        industry1("教育培训", "0"),
        industry2("亲子活动", "1"),
        industry3("生活服务", "2"),
        industry4("亲子基地", "3"),
        industry5("其他行业", "4");

        private String value;
        private String lisvalue;

        private Industry(String value, String lisvalue) {
            this.value = value;
            this.lisvalue = lisvalue;
        }

        public static String getValue(String name) {
            Industry[] industries = values();
            for (Industry industry : industries) {
                if (industry.lisvalue().equals(name)) {
                    return industry.value();
                }
            }
            return "";
        }


        public static String getName(String value) {
            Industry[] industries = values();
            for (Industry industry : industries) {
                if (industry.value().equals(value)) {
                    return industry.lisvalue();
                }
            }
            return "";
        }


        private String value() {
            return this.value;
        }

        private String lisvalue() {
            return this.lisvalue;
        }
    }
}
