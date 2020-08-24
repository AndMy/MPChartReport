package com.hmsg.hospitalreport.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hmsg.hospitalreport.R;
import com.hmsg.hospitalreport.bean.Cell;
import com.hmsg.hospitalreport.bean.ColTitle;
import com.hmsg.hospitalreport.bean.RowTitle;

import cn.zhouchaoyuan.excelpanel.BaseExcelPanelAdapter;

public class ExcelPanel2RowAdapter extends BaseExcelPanelAdapter<RowTitle, ColTitle, Cell> {

    private Context context;
    private View.OnClickListener blockListener;

    public ExcelPanel2RowAdapter(Context context, View.OnClickListener blockListener) {
        super(context);
        this.context = context;
        this.blockListener = blockListener;
    }

    //=========================================content's cell===========================================
    @Override
    public RecyclerView.ViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_status_normal_cell_2row, parent, false);
        CellHolder cellHolder = new CellHolder(layout);
        return cellHolder;
    }

    @Override
    public void onBindCellViewHolder(RecyclerView.ViewHolder holder, int verticalPosition, int horizontalPosition) {
        Cell cell = getMajorItem(verticalPosition, horizontalPosition);
        if (null == holder || !(holder instanceof CellHolder) || cell == null) {
            return;
        }
        CellHolder viewHolder = (CellHolder) holder;
        viewHolder.cellContainer.setTag(cell);
        viewHolder.cellContainer.setOnClickListener(blockListener);
        viewHolder.channelName.setText(cell.channelName);
        /*if (cell.getStatus() == 0) {
            viewHolder.bookingName.setText("");
            viewHolder.channelName.setText("");
            viewHolder.cellContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        } else {
            viewHolder.bookingName.setText(cell.getBookingName());
            viewHolder.channelName.setText(cell.getChannelName());
            if (cell.getStatus() == 1) {
                viewHolder.cellContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.left));
            } else if (cell.getStatus() == 2) {
                viewHolder.cellContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.staying));
            } else {
                viewHolder.cellContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.booking));
            }
        }*/
    }

    static class CellHolder extends RecyclerView.ViewHolder {
        public final TextView channelName;
        public final LinearLayout cellContainer;

        public CellHolder(View itemView) {
            super(itemView);
            channelName = (TextView) itemView.findViewById(R.id.channel_name);
            cellContainer = (LinearLayout) itemView.findViewById(R.id.pms_cell_container);
        }
    }


    //=========================================top cell===========================================
    @Override
    public RecyclerView.ViewHolder onCreateTopViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_status_top_header_item_2row, parent, false);
        TopHolder topHolder = new TopHolder(layout);
        return topHolder;
    }

    @Override
    public void onBindTopViewHolder(RecyclerView.ViewHolder holder, int position) {
        RowTitle rowTitle = getTopItem(position);
        if (null == holder || !(holder instanceof TopHolder) || rowTitle == null) {
            return;
        }
        TopHolder viewHolder = (TopHolder) holder;
        viewHolder.roomDate.setText(rowTitle.dateString);
    }

    static class TopHolder extends RecyclerView.ViewHolder {

        public final TextView roomDate;

        public TopHolder(View itemView) {
            super(itemView);
            roomDate = (TextView) itemView.findViewById(R.id.data_label);
        }
    }

    //=========================================left cell===========================================
    @Override
    public RecyclerView.ViewHolder onCreateLeftViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_status_left_header_item_2row, parent, false);
        LeftHolder leftHolder = new LeftHolder(layout);
        return leftHolder;
    }

    @Override
    public void onBindLeftViewHolder(RecyclerView.ViewHolder holder, int position) {
        ColTitle colTitle = getLeftItem(position);
        if (null == holder || !(holder instanceof LeftHolder) || colTitle == null) {
            return;
        }
        LeftHolder viewHolder = (LeftHolder) holder;
        viewHolder.roomNumberLabel.setText(colTitle.roomNumber);
        /*viewHolder.roomTypeLabel.setText(colTitle.getRoomTypeName());
        ViewGroup.LayoutParams lp = viewHolder.root.getLayoutParams();
        viewHolder.root.setLayoutParams(lp);*/
    }

    static class LeftHolder extends RecyclerView.ViewHolder {

        public final TextView roomNumberLabel;
        public final View root;

        public LeftHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.root);
            roomNumberLabel = (TextView) itemView.findViewById(R.id.room_number_label);
        }
    }

    //=========================================left-top cell===========================================
    @Override
    public View onCreateTopLeftView() {
        return LayoutInflater.from(context).inflate(R.layout.room_status_left_top_cell_2row, null);
    }
}
