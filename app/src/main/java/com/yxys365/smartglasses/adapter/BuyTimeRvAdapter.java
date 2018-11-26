package com.yxys365.smartglasses.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.entity.BuyTimeEntity;
import com.yxys365.smartglasses.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MaRufei
 * on 2018/6/5.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class BuyTimeRvAdapter extends RecyclerView.Adapter<BuyTimeRvAdapter.MyViewHolder> {
    private Context mContext;
    private List<BuyTimeEntity.RecordsBean> list = new ArrayList<>();
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setList(List<BuyTimeEntity.RecordsBean> list) {
        this.list = list;
    }

    public BuyTimeRvAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_buy_time, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (list.size() > 0) {
            holder.item_buy_time_type.setText(list.get(position).getStatus());
            holder.item_buy_time_time.setText(String.valueOf(list.get(position).getDays()) + "天");
            holder.item_buy_time_date.setText(list.get(position).getCreated_at());

            if (position % 2 == 0) {
                holder.item_buy_time_bac.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            } else {
                holder.item_buy_time_bac.setBackgroundColor(mContext.getResources().getColor(R.color.gray_ed));
            }
            holder.item_buy_time_bac.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick();
                }
            });
        }


    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView item_buy_time_time, item_buy_time_date, item_buy_time_type;
        LinearLayout item_buy_time_bac;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_buy_time_time = itemView.findViewById(R.id.item_buy_time_time);
            item_buy_time_date = itemView.findViewById(R.id.item_buy_time_date);
            item_buy_time_type = itemView.findViewById(R.id.item_buy_time_type);
            item_buy_time_bac = itemView.findViewById(R.id.item_buy_time_bac);
        }
    }

    public interface OnItemClickListener {
        void onItemClick();
    }
}
