package com.hmsg.hospitalreport.bean;

import com.hmsg.hospitalreport.chart.barchart.IBarData;

public class BarDataBean implements IBarData {
    private String name;
    private double valueData;
    public BarDataBean(double valueData, String name) {
        this.name = name;
        this.valueData = valueData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * 值
     */
    @Override
    public float getValue() {
        return (float) valueData;
    }

    /**
     * 对应名字
     */
    @Override
    public String getLabelName() {
        return name;
    }

    public void setValue(double value) {
        this.valueData = value;
    }
}
