package com.hmsg.hospitalreport.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hmsg.hospitalreport.R
import kotlinx.android.synthetic.main.main_item_layout.view.*

class HomeAdapter(var dataList : List<String>,var context: Context) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() , View.OnClickListener{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.main_item_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return if (dataList.isEmpty()) 0 else dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder?.itemView!!){
            main_item_tv.setText(dataList.get(position))
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
}