package com.yxys365.smartglasses.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.clj.fastble.BleManager;
import com.clj.fastble.data.BleDevice;
import com.yxys365.smartglasses.R;

import java.util.List;


/**
 * Created by MaRufei
 * on 2018/5/27.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class LvAdapter extends BaseAdapter {
    private Context context;
    private List<BleDevice> list;
    private BleListenner bleListenner;

    public void setBleListenner(BleListenner bleListenner) {
        this.bleListenner = bleListenner;
    }

    public LvAdapter(Context context, List<BleDevice> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            view=View.inflate(context, R.layout.lv_item,null);
            viewHolder.item_name=view.findViewById(R.id.item_name);
            viewHolder.item_address=view.findViewById(R.id.item_address);
            viewHolder.item_qiangdu=view.findViewById(R.id.item_qiangdu);
            viewHolder.item_connect=view.findViewById(R.id.item_connect);
            viewHolder.item_disconnect=view.findViewById(R.id.item_disconnect);
            viewHolder.item_operate=view.findViewById(R.id.item_operate);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }
        boolean isConnected = BleManager.getInstance().isConnected(list.get(i));
        if(isConnected){
            viewHolder.item_disconnect.setVisibility(View.VISIBLE);
            viewHolder.item_operate.setVisibility(View.VISIBLE);
            viewHolder.item_connect.setVisibility(View.GONE);
        }else {
            viewHolder.item_disconnect.setVisibility(View.GONE);
            viewHolder.item_operate.setVisibility(View.GONE);
            viewHolder.item_connect.setVisibility(View.VISIBLE);
        }
        viewHolder.item_name.setText(list.get(i).getName());
        viewHolder.item_address.setText(list.get(i).getMac());
        viewHolder.item_qiangdu.setText("信号强度："+list.get(i).getRssi());
        viewHolder.item_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bleListenner.connect(list.get(i));
            }
        });
        viewHolder.item_disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bleListenner.disconnet(list.get(i));
            }
        });
        viewHolder.item_operate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bleListenner.oprate(list.get(i));
            }
        });
        return view;
    }
    class ViewHolder{
        TextView item_name,item_address,item_qiangdu;
        Button item_connect,item_disconnect,item_operate;
    }
    public interface BleListenner{
        void connect(BleDevice bleDevice);
        void disconnet(BleDevice bleDevice);
        void oprate(BleDevice bleDevice);
    }
}
