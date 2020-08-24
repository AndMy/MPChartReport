package com.hmsg.hospitalreport.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hmsg.hospitalreport.R;
import com.hmsg.hospitalreport.adapter.ReportDateXSAdapter;

import java.util.ArrayList;
import java.util.List;

public class TimeSelectView extends LinearLayout{
    private ReportDateXSAdapter reportDateXSAdapter ;
    private RecyclerView time_recycleView ;
    private List<String> dataList = new ArrayList<>();
    private TimeSelectInterFace selectInterFace;
    public void setSelectInterFace(TimeSelectInterFace selectInterFace) {
        this.selectInterFace = selectInterFace;
    }
    public TimeSelectView(Context context) {
        this(context, null);
    }

    public TimeSelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.time_select_layout, this, true);
        time_recycleView = findViewById(R.id.time_recycleView);
        time_recycleView.setLayoutManager(new GridLayoutManager(context,3));
        reportDateXSAdapter = new ReportDateXSAdapter(dataList,context);
        time_recycleView.setAdapter(reportDateXSAdapter);

        reportDateXSAdapter.setOnItemClick((view, position) -> {
            reportDateXSAdapter.setSelectPosition(position);
            selectInterFace.onSelectTimeChange(position,dataList.get(position));
        });
    }
    public void setDataList(List<String> dataList){
        this.dataList = dataList;
        reportDateXSAdapter.setDataList(dataList);
    }
    public void setDefaultPosition(int defaultPosition){
        reportDateXSAdapter.setSelectPosition(defaultPosition);
    }
    public interface TimeSelectInterFace {
        void onSelectTimeChange(int position,String data);
    }
}
