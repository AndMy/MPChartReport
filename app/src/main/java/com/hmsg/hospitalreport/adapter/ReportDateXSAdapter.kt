package com.hmsg.hospitalreport.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hmsg.hospitalreport.R
import kotlinx.android.synthetic.main.time_select_item_layout.view.*

class ReportDateXSAdapter(var dataList : List<String>, var context: Context) : RecyclerView.Adapter<ReportDateXSAdapter.ViewHolder>() , View.OnClickListener{
    private var selectPosition : Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.time_select_item_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return if (dataList.isEmpty()) 0 else dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder?.itemView!!){
            report_datexs_item_tv.setText(dataList.get(position))
            if (selectPosition==holder.getAdapterPosition()){
                report_datexs_item_ll.setBackgroundColor(Color.parseColor("#009A00"))
                report_datexs_item_tv.setTextColor(Color.parseColor("#ffffff"))
            }else {
                report_datexs_item_ll.setBackgroundColor(Color.parseColor("#F1F1F1"))
                report_datexs_item_tv.setTextColor(Color.parseColor("#666666"))
            }
        }
        with(onItemClick!=null){
            holder.itemView.setOnClickListener {
                onItemClick.setOnItemClick(it,position)
            }
        }
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onClick(v: View?) {
        v?.id?.let { onItemClick.setOnItemClick(v, it) }
    }
    interface OnItemClick {
        fun setOnItemClick(view: View?, position: Int)
    }
    private lateinit var onItemClick : OnItemClick
    fun setOnItemClick(onItemClick : OnItemClick) {
        this.onItemClick = onItemClick
    }
    fun setSelectPosition(position: Int){
        this.selectPosition = position
        notifyDataSetChanged()
    }
}