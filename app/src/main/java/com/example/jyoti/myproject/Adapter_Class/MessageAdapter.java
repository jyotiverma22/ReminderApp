package com.example.jyoti.myproject.Adapter_Class;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jyoti.myproject.Bean_Class.ReminderBean;
import com.example.jyoti.myproject.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jyoti on 7/22/2017.
 */


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {


    List<ReminderBean> mylist;
    Context context;

    public MessageAdapter(Context context, List<ReminderBean> items) {

        this.mylist = items;
        this.context = context;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvdate, tvtime, tvtitle;
        //        public ImageView thumbnail, overflow;
        public CircleImageView cimage;

        public MyViewHolder(View view) {
            super(view);
            tvdate = (TextView) view.findViewById(R.id.tvdate);
            tvtime = (TextView) view.findViewById(R.id.tvtime);
            tvtitle = (TextView) view.findViewById(R.id.tvtitle);
            cimage = (CircleImageView) view.findViewById(R.id.cimage);
//        cardView = (CardView) itemView.findViewById(R.id.reminder_cardview);


        }
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    @Override
    public void onBindViewHolder(final MessageAdapter.MyViewHolder holder, int position) {
        final ReminderBean rowItem = mylist.get(position);
        holder.tvtitle.setText("" + rowItem.getTitle());
        //   String[] reminder_time=rowItem.getTime().split(":");
        holder.tvtime.setText("" + rowItem.getTime());
//        holder.tvdate.setText(""+reminder_time[1]);
        holder.tvdate.setText("" + rowItem.getDate());
        if(rowItem.getType()==0)
        {
            holder.cimage.setImageResource(R.drawable.sms2icon);
        }
        else
        {
            holder.cimage.setImageResource(R.drawable.mail2icon);
        }
/*
        if(rowItem.getStatus()==1)
        {
            holder.enable.setChecked(true);
        }
        else
        {
            holder.enable.setChecked(false);
        }

        holder.enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //database db=new database(ReminderAdapter.this);
                if(holder.enable.isChecked())
                {
                    rowItem.setStatus(1);
                }
                else
                {

                    rowItem.setStatus(0);
                    //db.updateReminderStatus
                }
            }
        });
*/


    }

    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_message, parent, false);
        return new MessageAdapter.MyViewHolder(itemView);
    }

}

