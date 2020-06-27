package com.example.jyoti.myproject.Adapter_Class;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jyoti.myproject.Bean_Class.ReminderBean;
import com.example.jyoti.myproject.R;

import java.util.List;
import java.util.Random;

/**
 * Created by Jyoti on 7/12/2017.
 */

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.MyViewHolder> {


    List<ReminderBean> mylist;
    Context context;

    public ReminderAdapter(Context context, List<ReminderBean> items) {

        this.mylist = items;
        this.context = context;
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView timehr,timemin, title,date;
//        public ImageView thumbnail, overflow;
        public CardView cardView;
        LinearLayout linearLayout;
        public MyViewHolder(View view) {
            super(view);
            timehr= (TextView) view.findViewById(R.id.reminder_timehr);
            timemin= (TextView) view.findViewById(R.id.reminder_timemin);
            title = (TextView) view.findViewById(R.id.reminder_title);
            date = (TextView) view.findViewById(R.id.reminder_date);
            cardView = (CardView) itemView.findViewById(R.id.reminder_cardview);
            linearLayout=(LinearLayout)view.findViewById(R.id.linearLayout2);

        }

    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ReminderBean rowItem = mylist.get(position);
        holder.title.setText(""+rowItem.getTitle());
        String[] reminder_time=rowItem.getTime().split(":");
        holder.timehr.setText("" + reminder_time[0]);
        holder.timemin.setText(""+reminder_time[1]);
        holder.date.setText(""+rowItem.getDate());

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        ((GradientDrawable)holder.linearLayout.getBackground()).setColor(color);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_reminder, parent, false);
        return new ReminderAdapter.MyViewHolder(itemView);
    }

}
