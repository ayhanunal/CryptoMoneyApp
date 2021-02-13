package com.ayhanunal.retrofitkriptopara.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayhanunal.retrofitkriptopara.R;
import com.ayhanunal.retrofitkriptopara.model.KriptoModel;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {

    private ArrayList<KriptoModel> kriptoList;

    private String[] colors = {"#5B0415","#fde5ee","#2e4756","#f0005d","#b48031","#373a27"};


    public RecyclerViewAdapter(ArrayList<KriptoModel> kriptoList) {
        this.kriptoList = kriptoList;
    }


    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_layout,parent,false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {

        holder.bind(kriptoList.get(position),colors,position);

    }

    @Override
    public int getItemCount() {
        return kriptoList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {

        TextView textName;
        TextView textPrice;

        public RowHolder(@NonNull View itemView) {
            super(itemView);


        }

        public void bind(KriptoModel kriptoModel, String[] colors, Integer position){

            itemView.setBackgroundColor(Color.parseColor(colors[position % 6]));

            textName = itemView.findViewById(R.id.text_name);
            textPrice = itemView.findViewById(R.id.text_price);

            textName.setText(kriptoModel.currency);
            textPrice.setText(kriptoModel.price);
        }
    }
}
