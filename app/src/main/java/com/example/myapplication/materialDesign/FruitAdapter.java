package com.example.myapplication.materialDesign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.FruitViewHolder> {
    private Context mContext;
    private List<Fruit> mFruitList;
    private OnItemClickListener mOnItemClickListener;
    public FruitAdapter(Context context, List<Fruit> fruitList,OnItemClickListener onClickListener){
        this.mContext=context;
        this.mFruitList=fruitList;
        this.mOnItemClickListener=onClickListener;
    }
    public static class FruitViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;
        public FruitViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=(CardView) itemView;
            fruitImage=itemView.findViewById(R.id.fruit_image);
            fruitName=itemView.findViewById(R.id.fruit_name);
        }
    }
    @NonNull
    @Override
    public FruitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.fruit_item,parent,false);
        return new FruitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FruitViewHolder holder, final int position) {
        Fruit fruit=mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        Glide.with(mContext).load(fruit.getImageId()).into(holder.fruitImage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.OnClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
    public interface OnItemClickListener{
        void OnClick(int i);
    }

}
