package com.hmsg.hospitalreport.chart.linechart;

/**
 * @description :折线图上层接口
 */
public interface ILineChartData {
    /**
     * 图表Y轴值
     */
    float getValue();

    /**
     * 对应名字
     */
    String getLabelName();
}
