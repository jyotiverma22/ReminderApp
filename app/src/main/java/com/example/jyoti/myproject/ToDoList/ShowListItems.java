package com.example.jyoti.myproject.ToDoList;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jyoti.myproject.Adapter_Class.ItemAdapter;
import com.example.jyoti.myproject.Bean_Class.ItemBean;
import com.example.jyoti.myproject.Database.database;
import com.example.jyoti.myproject.R;
import com.example.jyoti.myproject.recyclerview_functions.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ShowListItems extends AppCompatActivity {
    TextView tvTitle;
    Button badd;
    RecyclerView recyclerView;
    List<ItemBean> items=new ArrayList<>();;
    ItemAdapter adapter;
    database db;
    int idtodolist;
    AlertDialog.Builder additem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_items);
        db=new database(getApplicationContext());
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        tvTitle=(TextView)findViewById(R.id.tvtitle);
        badd=(Button)findViewById(R.id.badd);
        idtodolist=Integer.parseInt(getIntent().getStringExtra("id"));
        Log.e("Todolist",""+idtodolist);
        tvTitle.setText(""+db.getListTitle(idtodolist));

        items=db.showAllItems(idtodolist);

        adapter=new ItemAdapter(getApplicationContext(),items);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        additem = new AlertDialog.Builder(ShowListItems.this,R.style.MyDialogTheme);
        LayoutInflater inflater = this.getLayoutInflater();

        //Set the layout to adapter
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        additem.setView(dialogView);
        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

        setitems();

        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialogView.getParent()!=null)
                    ((ViewGroup)dialogView.getParent()).removeView(dialogView); // <- fix
                edt.setText("");
                additem.setView(dialogView);
                additem.show();

            }
        });



        additem.setTitle("To Do list").setMessage("Enter item").setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                int idtodolist=db.getLastTodolistId();
                if(edt.getText().length()<1)
                {
                    Toast.makeText(ShowListItems.this, "Must Enter item name", Toast.LENGTH_SHORT).show();
                }
                else {
                    db.AddItems(idtodolist, edt.getText().toString());

                    items = db.showAllItems(idtodolist);
                    adapter = new ItemAdapter(getApplicationContext(), items);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    dialog.cancel();
                    if (dialogView.getParent() != null)
                        ((ViewGroup) dialogView.getParent()).removeView(dialogView); // <- fix
                    edt.setText("");
                    additem.setView(dialogView);
                    additem.show();
                }

            }
        })
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(edt.getText().length()<1)
                        {
                            dialog.cancel();
                        }
                        else {
                            int idtodolist = db.getLastTodolistId();
                            db.AddItems(idtodolist, edt.getText().toString());

                            items = db.showAllItems(idtodolist);
                            adapter = new ItemAdapter(getApplicationContext(), items);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            dialog.cancel();
                        }

                    }
                }).setIcon(android.R.drawable.ic_input_add);



    }

    public void setitems()
    {
        items=db.showAllItems(idtodolist);
        adapter=new ItemAdapter(getApplicationContext(),items);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

}
