package com.arno.demo.life.conflict;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arno.demo.life.R;
import com.blankj.utilcode.util.ToastUtils;

import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<String> data;

    public SimpleAdapter(List<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_text, null);
        return new TextHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TextHolder) holder).textView.setText(data.get(position));
        ((TextHolder) holder).textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Adapter", "onClick: " + holder.getAdapterPosition());
//                ToastUtils.showShort(holder.getAdapterPosition());
            }
        });
    }

    private static class TextHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public TextHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvTitle);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
