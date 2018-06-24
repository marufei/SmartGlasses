package com.yxys365.smartglasses.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.adapter.LyLvAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by MaRufei
 * on 2018/6/6.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class LyFragment extends BaseFragment {
    private View view;
    private ListView ly_lv;
    private LyLvAdapter adapter;
    private LineChart ly_line;
    /**
     * 左边Y轴
     */
    private YAxis leftAxis;
    /**
     * X轴
     */
    private XAxis xAxis;
    /**
     *   LineDataSet每一个对象就是一条连接线
     */
    LineDataSet set1;

    @Override
    protected void lazyLoad() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=View.inflate(getActivity(), R.layout.fragment_ly,null);
        initViews();
        initDatas();
        return view;
    }

    private void initDatas() {
        initLines();
    }

    private void initLines() {

        //设置X轴属性
        //获取此图表的x轴
        XAxis xAxis = ly_line.getXAxis();
        xAxis.setEnabled(true);//设置轴启用或禁用 如果禁用以下的设置全部不生效
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(true);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        //xAxis.setTextSize(20f);//设置字体
        //xAxis.setTextColor(Color.BLACK);//设置字体颜色
        //设置竖线的显示样式为虚线
        //lineLength控制虚线段的长度
        //spaceLength控制线之间的空间
        xAxis.enableGridDashedLine(10f, 10f, 0f);
//        xAxis.setAxisMinimum(0f);//设置x轴的最小值
//        xAxis.setAxisMaximum(10f);//设置最大值
        xAxis.setAvoidFirstLastClipping(true);//图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        xAxis.setLabelRotationAngle(10f);//设置x轴标签的旋转角度
//        设置x轴显示标签数量  还有一个重载方法第二个参数为布尔值强制设置数量 如果启用会导致绘制点出现偏差
//        xAxis.setLabelCount(10);
//        xAxis.setTextColor(Color.BLUE);//设置轴标签的颜色
//        xAxis.setTextSize(24f);//设置轴标签的大小
//        xAxis.setGridLineWidth(10f);//设置竖线大小
//        xAxis.setGridColor(Color.RED);//设置竖线颜色
//        xAxis.setAxisLineColor(Color.GREEN);//设置x轴线颜色
//        xAxis.setAxisLineWidth(5f);//设置x轴线宽度
//        xAxis.setValueFormatter();//格式化x轴标签显示字符

        /**
         * Y轴默认显示左右两个轴线
         */
        //获取右边的轴线
        YAxis rightAxis=ly_line.getAxisRight();
        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
        //获取左边的轴线
        YAxis leftAxis = ly_line.getAxisLeft();
        //设置网格线为虚线效果
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        //是否绘制0所在的网格线
        leftAxis.setDrawZeroLine(false);

        //创建描述信息
        Description description =new Description();
        description.setText("");
        description.setTextColor(Color.RED);
        description.setTextSize(20);
        ly_line.setDescription(description);//设置图表描述信息
        ly_line.setNoDataText("没有数据");//没有数据时显示的文字
        ly_line.setNoDataTextColor(Color.BLUE);//没有数据时显示文字的颜色
        ly_line.setDrawGridBackground(false);//chart 绘图区后面的背景矩形将绘制
        ly_line.setDrawBorders(false);//禁止绘制图表边框的线
        //lineChart.setBorderColor(); //设置 chart 边框线的颜色。
        //lineChart.setBorderWidth(); //设置 chart 边界线的宽度，单位 dp。
        //lineChart.setLogEnabled(true);//打印日志
        //lineChart.notifyDataSetChanged();//刷新数据
        //lineChart.invalidate();//重绘 «

        bindDatas();





    }

    /**
     * 折线数据源
     */
    private void bindDatas() {
        ArrayList<Entry> values1 = new ArrayList<>();
        values1.add(new Entry(4,12));
        values1.add(new Entry(6,19));
        values1.add(new Entry(9,25));
        values1.add(new Entry(12,3));
        values1.add(new Entry(15,37));

        if(ly_line.getData()!=null&& ly_line.getData().getDataSetCount() > 0){
            //获取数据
            set1 = (LineDataSet) ly_line.getData().getDataSetByIndex(0);
            set1.setValues(values1);
            //刷新数据
            ly_line.getData().notifyDataChanged();
            ly_line.notifyDataSetChanged();
        }else {
            //设置数据1  参数1：数据源 参数2：图例名称
            set1 = new LineDataSet(values1, "测试数据");
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);//设置线宽
            set1.setCircleRadius(2f);//设置焦点圆心的大小
            set1.enableDashedHighlightLine(10f, 5f, 0f);//点击后的高亮线的显示样式
            set1.setHighlightLineWidth(2f);//设置点击交点后显示高亮线宽
            set1.setHighlightEnabled(true);//是否禁用点击高亮线
            set1.setHighLightColor(Color.RED);//设置点击交点后显示交高亮线的颜色
            set1.setValueTextSize(9f);//设置显示值的文字大小
            set1.setDrawFilled(true);//设置禁用范围背景填充
            // 填充透明度
            set1.setFillAlpha(45);

            // 设置曲线允许平滑 决定是曲线true还是直线false 若为曲线相邻两点连线会发生弯曲

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            // 设置曲线平滑度

            set1.setCubicIntensity(0.1f);

            //格式化显示数据
            final DecimalFormat mFormat = new DecimalFormat("###,###,##0");
            set1.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return mFormat.format(value);
                }
            });
            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.line_chat);
                set1.setFillDrawable(drawable);//设置范围背景填充
            } else {
                set1.setFillColor(Color.BLACK);
            }
        }

        //保存LineDataSet集合
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the datasets
        //创建LineData对象 属于LineChart折线图的数据集合
        LineData data = new LineData(dataSets);
        // 添加到图表中
        ly_line.setData(data);
        //绘制图表
        ly_line.invalidate();




    }

    private void initViews() {
        ly_lv=view.findViewById(R.id.ly_lv);
        adapter=new LyLvAdapter(getActivity());
        ly_lv.setAdapter(adapter);

        ly_line=view.findViewById(R.id.ly_line);
    }
}
