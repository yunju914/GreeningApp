package com.example.greeningapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHorder> {

    private ArrayList<Product> arrayList;
    private Context context;

    public CategoryAdapter(ArrayList<Product> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHorder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        CategoryViewHorder holder = new CategoryViewHorder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHorder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getPimg())
                .into(holder.p_pic);
        holder.p_name.setText(arrayList.get(position).getPname());
        holder.p_say.setText(arrayList.get(position).getPsay());
        holder.p_price.setText(String.valueOf(arrayList.get(position).getPprice()));

    }

    @Override
    public int getItemCount() {
        // 삼항연산자 if 참 array~ 거짓 0
        return (arrayList !=null ? arrayList.size() : 0);
    }

    public class CategoryViewHorder extends RecyclerView.ViewHolder {
        ImageView p_pic;
        TextView p_name;
        TextView p_say;
        TextView p_price;
        public CategoryViewHorder(@NonNull View itemView) {
            super(itemView);
            this.p_pic= itemView.findViewById(R.id.pimg);
            this.p_name= itemView.findViewById(R.id.pname);
            this.p_say= itemView.findViewById(R.id.psay);
            this.p_price= itemView.findViewById(R.id.pprice);
        }
    }
}
