package com.example.jyoti.myproject.Adapter_Class;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jyoti.myproject.Bean_Class.TodoListBean;
import com.example.jyoti.myproject.R;

import java.util.List;
import java.util.Random;

/**
 * Created by Jyoti on 7/18/2017.
 */

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.MyViewHolder> {

        List<TodoListBean> mylist;
        Context context;

public TodoListAdapter(Context context, List<TodoListBean> items) {

        this.mylist = items;
        this.context = context;
        }


class MyViewHolder extends RecyclerView.ViewHolder
{
    TextView title;
    LinearLayout linearLayout;

    public MyViewHolder(View view) {
        super(view);
        //  this.view=view;
        title = (TextView) view.findViewById(R.id.tvtitle);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout2);

    }

}

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    @Override
    public void onBindViewHolder(final TodoListAdapter.MyViewHolder holder, int position) {
        final TodoListBean rowItem = mylist.get(position);
        holder.title.setText(""+rowItem.getTitle());
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        ((GradientDrawable)holder.linearLayout.getBackground()).setColor(color);

    }


    @Override
    public TodoListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_todolist, parent, false);
        return new TodoListAdapter.MyViewHolder(itemView);
    }

}

