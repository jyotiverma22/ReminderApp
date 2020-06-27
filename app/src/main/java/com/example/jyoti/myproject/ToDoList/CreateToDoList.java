package com.example.jyoti.myproject.ToDoList;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class CreateToDoList extends AppCompatActivity {
    EditText ettitle;
    AlertDialog.Builder additem;
    RecyclerView recyclerView;
    Button badd;
    database db;
    List<ItemBean> items;
    ItemAdapter adapter;
    TextView tvtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_to_do_list);
        ettitle=(EditText)findViewById(R.id.ettitle);
        tvtitle=(TextView)findViewById(R.id.tvtitle);

        badd=(Button)findViewById(R.id.badd);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);

        db=new database(getApplicationContext());

        items=new ArrayList<ItemBean>();
        adapter=new ItemAdapter(getApplicationContext(),items);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView. setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        additem = new AlertDialog.Builder(CreateToDoList.this,R.style.MyDialogTheme);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        additem.setView(dialogView);
        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);




        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ettitle.getText().length()<1)
                {
                    Toast.makeText(CreateToDoList.this, "Please fill ToDoList Title", Toast.LENGTH_SHORT).show();
                }
                else if(tvtitle.getText().length()<1)
                {
                    db.createToDoList(ettitle.getText().toString());
                    ettitle.setVisibility(View.INVISIBLE);
                    tvtitle.setText(""+ettitle.getText().toString());
                    badd.setText("Add Item");

                }
                else{

                    if(dialogView.getParent()!=null)
                        ((ViewGroup)dialogView.getParent()).removeView(dialogView); // <- fix
                    edt.setText("");
                    additem.setView(dialogView);
                    additem.show();

                }
            }
        });

        additem.setTitle("To Do list").setMessage("Enter item").setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(edt.getText().length()<1)
                {
                    Toast.makeText(CreateToDoList.this, "Must Enter item name", Toast.LENGTH_SHORT).show();
                }
                else {
                    int idtodolist = db.getLastTodolistId();
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
}
