package com.example.greeningapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class SearchAdapter extends FirebaseRecyclerAdapter<Product,SearchAdapter.myviewholder> {
    private ArrayList<Product> arrayList;
    private Context context;
    public SearchAdapter(@NonNull FirebaseRecyclerOptions<Product> options) {
        super(options);
    }

@Override
protected void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position, @NonNull Product product) {
    holder.pname.setText(getItem(position).getPname());
    holder.psay.setText(getItem(position).getPsay());
    holder.pprice.setText(String.valueOf(getItem(position).getPprice()));
    Glide.with(holder.pimg.getContext()).load(getItem(position).getPimg()).into(holder.pimg);

    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 클릭된 상품의 정보를 가져와서 새 액티비티에 전달
            Product selectedProduct = getItem(position);
            Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
            intent.putExtra("detail", selectedProduct);
            v.getContext().startActivity(intent);
        }
    });
}


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder {
        ImageView pimg;
        TextView pname, psay, pprice;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            this.pimg = (ImageView) itemView.findViewById(R.id.pimg);
            this.pname = (TextView) itemView.findViewById(R.id.pname);
            this.psay = (TextView) itemView.findViewById(R.id.psay);
            this.pprice = (TextView) itemView.findViewById(R.id.pprice);

        }

    }
}



