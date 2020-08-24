package com.hmsg.hospitalreport.bean;

import com.hmsg.hospitalreport.chart.linechart.ILineChartData;

public class LineDataBean implements ILineChartData {
    private String name;
    private double valueData;
    public LineDataBean(double valueData, String name) {
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
