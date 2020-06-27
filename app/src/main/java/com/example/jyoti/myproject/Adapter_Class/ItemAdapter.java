package com.example.jyoti.myproject.Adapter_Class;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.jyoti.myproject.Bean_Class.ItemBean;
import com.example.jyoti.myproject.R;

import java.util.List;

/**
 * Created by Jyoti on 7/17/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {


    List<ItemBean> mylist;
    Context context;

    public ItemAdapter(Context context, List<ItemBean> items) {

        this.mylist = items;
        this.context = context;
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        CheckBox check;

        public MyViewHolder(View view) {
            super(view);
            //  this.view=view;
            title = (TextView) view.findViewById(R.id.tvitem);
            check = (CheckBox) view.findViewById(R.id.checkitem);


        }

    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    @Override
    public void onBindViewHolder(final ItemAdapter.MyViewHolder holder, int position) {
        final ItemBean rowItem = mylist.get(position);
        holder.title.setText(""+rowItem.getItem());
        if(rowItem.getStatus()==1)
        {
            holder.check.setChecked(true);
            holder.title.setPaintFlags(holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else
        {
            holder.check.setChecked(false);
        }

        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //database db=new database(ReminderAdapter.this);
                if(holder.check.isChecked())
                {
                    rowItem.setStatus(1);
                    holder.title.setPaintFlags(holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                }
                else
                {

                    rowItem.setStatus(0);
                    holder.title.setPaintFlags(holder.title.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                    //db.updateReminderStatus
                }
            }
        });

    }

    @Override
    public ItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_items, parent, false);
        return new ItemAdapter.MyViewHolder(itemView);
    }

}

