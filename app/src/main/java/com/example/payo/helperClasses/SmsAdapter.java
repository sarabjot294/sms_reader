package com.example.payo.helperClasses;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.payo.R;
import com.example.payo.helperClasses.model.SmsDetails;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.ViewHolder> {

    Context ctx;
    List<SmsDetails> smsDetailsList = new ArrayList();
    LinearLayout mlay;
    String m_Text = "";
    List<String> smsTags = new ArrayList<>();

    public List<SmsDetails> getSmsDetailsList() {
        return smsDetailsList;
    }

    public List<String> getSmsTags() {
        return smsTags;
    }

    public SmsAdapter(Context context, List<SmsDetails> smsDetails) {
        ctx = context;
        smsDetailsList = smsDetails;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sms_text, tags, no;
        Button add_tags;
        LinearLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            no = itemView.findViewById(R.id.no);
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d("TAG", "onBindViewHolder: Postion : " + position);
        holder.no.setText(String.valueOf(position+1));
       try{
           SmsDetails smsDetails = smsDetailsList.get(position);
           holder.sms_text.setText(smsDetails.getSmsText());
           if(smsDetails.getTagsList().size() > 0)
           {
               String tags = "";
               for(int i=0;i<smsDetails.getTagsList().size();i++)
               {
                   tags += smsDetails.getTagsList().get(i) + "; ";
               }
               holder.tags.setText(tags);
           }
       }
       catch (Exception e)
       {
           Log.e("TAG", "onBindViewHolder: Exception " + e.toString() );
           holder.sms_text.setText("-");
       }

       holder.add_tags.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               inputTags(position);
           }
       });
    }

    void inputTags(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Add Tags");

        final EditText input = new EditText(ctx);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString().toLowerCase();
                if(smsDetailsList.get(position).getTagsList().contains(m_Text))
                {
                    Toast.makeText(ctx,"Sorry, this tag already exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("TAG", "onClick: Added in " + position + " object");
                smsDetailsList.get(position).getTagsList().add(m_Text);

                if(!smsTags.contains(m_Text))
                    smsTags.add(m_Text);

                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }



    @Override
    public int getItemCount() {
        return smsDetailsList.size();
    }

}
