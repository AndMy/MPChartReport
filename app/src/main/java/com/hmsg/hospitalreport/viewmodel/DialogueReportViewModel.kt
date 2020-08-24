package com.hmsg.hospitalreport.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hmsg.hospitalreport.bean.*
import com.hmsg.hospitalreport.chart.barchart.IBarData
import com.hmsg.hospitalreport.chart.linechart.ILineChartData
import com.hmsg.hospitalreport.utils.*
import java.util.*


class DialogueReportViewModel(application: Application) : AndroidViewModel(application) {
    var timeListTime = mutableListOf("0:00","4:00","7:00","8:00","9:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","19:00","23:00","24:00")
    /*var timeList = mutableListOf(
        (-3).todayBeforeAfterTime(),
        (-2).todayBeforeAfterTime(),
        (-1).todayBeforeAfterTime(),
        0.todayBeforeAfterTime(),
        1.todayBeforeAfterTime(),
        2.todayBeforeAfterTime(),
        3.todayBeforeAfterTime(),
        4.todayBeforeAfterTime())*/
    var doctorIdList  : MutableList<Int> = mutableListOf()
    var timeOutList  : MutableList<Int> = mutableListOf()
    var excelList : MutableLiveData<UpdateExcelState<MutableList<RowTitle>, MutableList<ColTitle>,MutableList<MutableList<Cell>>,MutableList<MutableList<ILineChartData>>>> = MutableLiveData()
    var excelBarList : MutableLiveData<UpdateExcelState<MutableList<RowTitle>, MutableList<ColTitle>,MutableList<MutableList<Cell>>,MutableList<MutableList<IBarData>>>> = MutableLiveData()
    var excelSingleBarList : MutableLiveData<UpdateExcelState<MutableList<RowTitle>, MutableList<ColTitle>,MutableList<MutableList<Cell>>,MutableList<IBarData>>> = MutableLiveData()
    var excellineList : MutableLiveData<UpdateExcelState1<MutableList<RowTitle>, MutableList<ColTitle>,MutableList<MutableList<Cell>>,MutableList<ILineChartData>,MutableList<ILineChartData>>> = MutableLiveData()

    var chartData :  MutableList<MutableList<ILineChartData>> = mutableListOf()
    var lineData :  MutableList<ILineChartData> = mutableListOf()
    var lineData1 :  MutableList<ILineChartData> = mutableListOf()
    var chartBarData :  MutableList<MutableList<IBarData>> = mutableListOf()
    var chartSingleBarData :  MutableList<IBarData> = mutableListOf()
    fun getExcelData(mutableListOf: MutableList<String>,dateList : MutableList<Date>) {

        var rowTitles: MutableList<RowTitle> = mutableListOf()
        var colTitles: MutableList<ColTitle> = mutableListOf()
        var cells: MutableList<MutableList<Cell>> = mutableListOf()

        //表格的列
        mutableListOf.forEach {
            var rowTitle = RowTitle()
            rowTitle.dateString = it
            rowTitles.add(rowTitle)
        }
        //曲线图的数据
        if (chartData.isNotEmpty())chartData.clear()
        mutableListOf.forEach {
            var entries : MutableList<ILineChartData> = mutableListOf()
            dateList.forEachIndexed { index, l ->
                entries.add(LineDataBean((50..999).random().toDouble(),l.time.transToString()))
            }
            chartData.add(entries)
        }

        dateList.forEach {
            var colTitle = ColTitle()
            colTitle.roomNumber = it.time.transToString()
            colTitles.add(colTitle)
        }

        colTitles.forEachIndexed { index, colTitle ->
            var cellList : MutableList<Cell> = mutableListOf()
            cells.add(cellList)
            rowTitles.forEachIndexed { index1, rowTitle ->
                var cell = Cell()
                cell.channelName = chartData.get(index1).get(index).value.toString()
                cellList.add(cell)
            }
        }

        val updateExcelState = UpdateExcelState(
            isSuccess = true,
            data = rowTitles,
            data1 = colTitles,
            data2 = cells,
            data3 = chartData
        )
        excelList.postValue(updateExcelState)
    }
    fun getExcelBarData(mutableListOf: MutableList<String>,dateList : MutableList<Date>) {
        var rowTitles: MutableList<RowTitle> = mutableListOf()
        var colTitles: MutableList<ColTitle> = mutableListOf()
        var cells: MutableList<MutableList<Cell>> = mutableListOf()

        //表格的列
        mutableListOf.forEach {
            var rowTitle = RowTitle()
            rowTitle.dateString = it
            rowTitles.add(rowTitle)
        }
        //曲线图的数据
        if (chartBarData.isNotEmpty())chartBarData.clear()
        mutableListOf.forEach {
            var entries : MutableList<IBarData> = mutableListOf()
            dateList.forEachIndexed { index, l ->
                entries.add(BarDataBean((50..999).random().toDouble(),l.time.transToString()))
            }
            chartBarData.add(entries)
        }

        dateList.forEach {
            var colTitle = ColTitle()
            colTitle.roomNumber = it.time.transToString()
            colTitles.add(colTitle)
        }

        colTitles.forEachIndexed { index, colTitle ->
            var cellList : MutableList<Cell> = mutableListOf()
            cells.add(cellList)
            rowTitles.forEachIndexed { index1, rowTitle ->
                var cell = Cell()
                cell.channelName = chartBarData.get(index1).get(index).value.toString()
                cellList.add(cell)
            }
        }

        val updateExcelState = UpdateExcelState(
            isSuccess = true,
            data = rowTitles,
            data1 = colTitles,
            data2 = cells,
            data3 = chartBarData
        )
        excelBarList.postValue(updateExcelState)
    }
    fun getSingleExcelBarData(mutableListOf: MutableList<String>) {
        var rowTitles: MutableList<RowTitle> = mutableListOf()
        var colTitles: MutableList<ColTitle> = mutableListOf()
        var cells: MutableList<MutableList<Cell>> = mutableListOf()

        //表格的列
        mutableListOf.forEach {
            var rowTitle = RowTitle()
            rowTitle.dateString = it
            rowTitles.add(rowTitle)
        }
        //曲线图的数据
        if (chartSingleBarData.isNotEmpty())chartSingleBarData.clear()
        timeListTime.forEachIndexed { index, l ->
            chartSingleBarData.add(BarDataBean((50..999).random().toDouble(),l))
        }

        timeListTime.forEach {
            var colTitle = ColTitle()
            colTitle.roomNumber = it
            colTitles.add(colTitle)
        }

        colTitles.forEachIndexed { index, colTitle ->
            var cellList : MutableList<Cell> = mutableListOf()
            cells.add(cellList)
            rowTitles.forEachIndexed { index1, rowTitle ->
                var cell = Cell()
                cell.channelName = chartSingleBarData.get(index).value.toString()
                cellList.add(cell)
            }
        }

        val updateExcelState = UpdateExcelState(
            isSuccess = true,
            data = rowTitles,
            data1 = colTitles,
            data2 = cells,
            data3 = chartSingleBarData
        )
        excelSingleBarList.postValue(updateExcelState)
    }
    fun doctorListData(mutableListOf: MutableList<String>){
        for(index in 1..25){
            doctorIdList.add((1000..5000).random())
        }
        var rowTitles: MutableList<RowTitle> = mutableListOf()
        var colTitles: MutableList<ColTitle> = mutableListOf()
        var cells: MutableList<MutableList<Cell>> = mutableListOf()

        //表格的列
        mutableListOf.forEach {
            var rowTitle = RowTitle()
            rowTitle.dateString = it
            rowTitles.add(rowTitle)
        }
        doctorIdList.forEach {
            var colTitle = ColTitle()
            colTitle.roomNumber = it.toString()
            colTitles.add(colTitle)
        }

        colTitles.forEachIndexed { index, colTitle ->
            var cellList : MutableList<Cell> = mutableListOf()
            cells.add(cellList)
            rowTitles.forEachIndexed { index1, rowTitle ->
                var cell = Cell()
                if (rowTitles.get(index1).dateString.equals("医生姓名")){
                    cell.channelName = "医生"+(1..2000).random().toString()
                }else{
                    cell.channelName = (1..2000).random().toString()
                }
                cellList.add(cell)
            }
        }

        val updateExcelState = UpdateExcelState(
            isSuccess = true,
            data = rowTitles,
            data1 = colTitles,
            data2 = cells,
            data3 = chartData
        )
        excelList.postValue(updateExcelState)
    }
    fun timeOutListData(mutableListOf: MutableList<String>,dateList : MutableList<Date>){
        for(index in 1..25){
            doctorIdList.add((1000..5000).random())
            timeOutList.add((2..30).random())
        }
        var rowTitles: MutableList<RowTitle> = mutableListOf()
        var colTitles: MutableList<ColTitle> = mutableListOf()
        var cells: MutableList<MutableList<Cell>> = mutableListOf()

        //表格的列
        mutableListOf.forEach {
            var rowTitle = RowTitle()
            rowTitle.dateString = it
            rowTitles.add(rowTitle)
        }
        timeOutList.forEach {
            var colTitle = ColTitle()
            colTitle.roomNumber = it.toString()
            colTitles.add(colTitle)
        }

        colTitles.forEachIndexed { index, colTitle ->
            var cellList : MutableList<Cell> = mutableListOf()
            cells.add(cellList)
            rowTitles.forEachIndexed { index1, rowTitle ->
                var cell = Cell()
                if (rowTitles.get(index1).dateString.equals("接诊医生姓名")){
                    cell.channelName = "医生"+(1..2000).random().toString()
                }else if (rowTitles.get(index1).dateString.equals("患者姓名")){
                    cell.channelName = "患者"+(1..2000).random().toString()
                }else if (rowTitles.get(index1).dateString.equals("最后问诊时间")){
                    cell.channelName = dateList.get((0..(dateList.size-1)).random()).time.transToString1()
                }else{
                    cell.channelName = (1..2000).random().toString()
                }
                cellList.add(cell)
            }
        }

        val updateExcelState = UpdateExcelState(
            isSuccess = true,
            data = rowTitles,
            data1 = colTitles,
            data2 = cells,
            data3 = chartData
        )
        excelList.postValue(updateExcelState)
    }

    fun getSingleExcelData(mutableListOf: MutableList<String>,dateList : MutableList<Date>) {

        var rowTitles: MutableList<RowTitle> = mutableListOf()
        var colTitles: MutableList<ColTitle> = mutableListOf()
        var cells: MutableList<MutableList<Cell>> = mutableListOf()
        var bgsList : MutableList<String> = mutableListOf()
        var bgs1List : MutableList<String> = mutableListOf()

        //表格的列
        mutableListOf.forEach {
            var rowTitle = RowTitle()
            rowTitle.dateString = it
            rowTitles.add(rowTitle)
        }

        dateList.forEach {
            var colTitle = ColTitle()
            colTitle.roomNumber = it.time.transToString()
            colTitles.add(colTitle)
            bgsList.add((20..389).random().toString())
            bgs1List.add((20..389).random().toString())
        }
        //曲线图的数据
        if (lineData.isNotEmpty())lineData.clear()
        if (lineData1.isNotEmpty())lineData1.clear()

        lateinit var entries :LineDataBean
        lateinit var entries1 :LineDataBean
        dateList.forEachIndexed { index, l ->
            entries = LineDataBean(bgsList.get(index).toDouble(),l.time.transToString())
            lineData.add(entries)
            entries1 = LineDataBean(bgs1List.get(index).toDouble(),l.time.transToString())
            lineData1.add(entries1)
        }
        colTitles.forEachIndexed { index, colTitle ->
            var cellList : MutableList<Cell> = mutableListOf()
            cells.add(cellList)
            rowTitles.forEachIndexed { index1, rowTitle ->
                var cell = Cell()
                if (rowTitles.get(index1).dateString.equals("包裹数")){
                    cell.channelName = bgsList.get(index)
                }else if (rowTitles.get(index1).dateString.equals("含税订单金额")){
                    cell.channelName = bgs1List.get(index)
                }else{
                    cell.channelName = (1..2000).random().toString()
                }
                cellList.add(cell)
            }
        }

        val updateExcelState = UpdateExcelState1(
            isSuccess = true,
            data = rowTitles,
            data1 = colTitles,
            data2 = cells,
            data3 = lineData,
            data4 = lineData1
        )
        excellineList.postValue(updateExcelState)
    }
}
