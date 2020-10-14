package com.rhy.easypoidemo.config;

public class ExcelColumnNum {
    /**
     * column数量
     * 线程内可见
     */
    private static ThreadLocal<Integer> columnNum = new ThreadLocal<>();

    /**
     * get column数量
     * @return
     */
    public static Integer get() {
        return ExcelColumnNum.columnNum.get();
    }

    public static void set(Integer columnNum) {
        ExcelColumnNum.columnNum.set(columnNum);
    }
}
