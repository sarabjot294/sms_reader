package com.example.payo.helperClasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.payo.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.ViewHolder> {

    Context context;
    LinearLayout mlay;

    public SmsAdapter() {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sms_text, tags;
        Button add_tags;
        LinearLayout container;

        public ViewHolder(View itemView) {
            super(itemView);

            sms_text = itemView.findViewById(R.id.sms_content);
            tags = itemView.findViewById(R.id.tags);
            add_tags = itemView.findViewById(R.id.addTagBtn);
            container = itemView.findViewById(R.id.container);
        }
    }

    @Override
    public SmsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sms_view_adapter, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

}
