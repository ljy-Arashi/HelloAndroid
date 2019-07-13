package com.example.myapplication.recyclerview;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;


public class LinearAdapter extends RecyclerView.Adapter<LinearAdapter.LinearViewHolder> {

    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener monItemLongClickListener;

    //private List<String> mlist;实际开发应该传个什么列表
    public  LinearAdapter(Context context,OnItemClickListener onItemClickListener,OnItemLongClickListener onItemLongClickListener)
    {
        this.mContext=context;
        this.mOnItemClickListener=onItemClickListener;
        this.monItemLongClickListener=onItemLongClickListener;

    }
    @Override
    public LinearAdapter.LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //传入每个Item布局
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_linear_item, parent,false));

    }

    @Override
    public void onBindViewHolder(LinearAdapter.LinearViewHolder holder, final int position) {
        holder.textView.setText("Hello World! RecyclerView");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.OnClick(position);
               // Toast.makeText(mContext,"click.."+position,Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                monItemLongClickListener.OnClick(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return 30;//实际中应该是mlist的长度
    }

    public static class LinearViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        public LinearViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.tv_linearRec_title);
        }
    }

    public interface OnItemClickListener{
        void OnClick(int i);
    }

    public interface OnItemLongClickListener{
        void OnClick(int i);
    }
}
