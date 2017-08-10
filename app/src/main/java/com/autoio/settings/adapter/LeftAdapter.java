package com.autoio.settings.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.autoio.settings.R;
import com.autoio.settings.holder.LeftViewHolder;

/**
 * Created by wuhongyun on 17-8-8.
 */

public class LeftAdapter extends RecyclerView.Adapter<LeftViewHolder> {


    private Context context;
    private int[] leftPics;
    private int[] leftTexts;
    private float height;
    private int selectItem = 0;

    /**
     * adapter 构造方法
     * @param context 上下文 用于布局填充
     * @param leftPics 图片数组selectors
     * @param leftTexts list文字
     * @param height recyclerview的总高度
     */
    public LeftAdapter(Context context, int[] leftPics, int[] leftTexts, float height) {
        this.context = context;
        this.leftPics = leftPics;
        this.leftTexts = leftTexts;
        this.height = height;

    }

    @Override
    public LeftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LeftViewHolder leftViewHolder = new LeftViewHolder(View.inflate(context, R.layout.left_recycler_item, null));

        leftViewHolder.setHeight(height);

        return leftViewHolder;
    }

    @Override
    public void onBindViewHolder(final LeftViewHolder holder, final int position) {
        if (selectItem==position){
            holder.left_recycler_item_bg.setVisibility(View.VISIBLE);
            holder.left_recycler_item_pic.setSelected(true);
        }else{
            holder.left_recycler_item_bg.setVisibility(View.INVISIBLE);
            holder.left_recycler_item_pic.setSelected(false);
        }

        holder.left_recycler_item_pic.setBackgroundResource(leftPics[position]);
        holder.left_recycler_item_txt.setText(leftTexts[position]);
        holder.left_recycler_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem = position;
                //
                notifyDataSetChanged();
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(holder.itemView,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        if (leftTexts!=null){
            return leftTexts.length;
        }
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 条目点击事件
     */
    private OnItemClickListener onItemClickListener;

    /**
     * 此接口用于监听条目点击
     */
    public interface OnItemClickListener{

        /**
         * 当前条目被点击时触发
         * @param view 被点击的item
         * @param position 被点击item的位置
         */
        void onItemClick(View view,int position);
    }
}
