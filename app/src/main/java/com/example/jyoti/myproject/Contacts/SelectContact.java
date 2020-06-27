package com.example.jyoti.myproject.Contacts;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jyoti.myproject.Adapter_Class.SelectContactAdapter;
import com.example.jyoti.myproject.Bean_Class.ContactBean;
import com.example.jyoti.myproject.R;
import com.example.jyoti.myproject.Reminder.CreateMessage;
import com.example.jyoti.myproject.recyclerview_functions.DividerItemDecoration;
import com.example.jyoti.myproject.recyclerview_functions.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class SelectContact extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    ArrayList<ContactBean> selectUsers;
    List<ContactBean> temp;
    // Cursor to load contacts list
    Cursor phones, email;
    ArrayList<String> numbers;
  //  SearchView searchView;
    // Pop up
    ContentResolver resolver;
    SelectContactAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contact);
        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        fab=(FloatingActionButton) findViewById(R.id.fab);
    //    searchView=(SearchView)findViewById(R.id.searchView);
        selectUsers = new ArrayList<ContactBean>();
        resolver = this.getContentResolver();
        phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        LoadContact loadContact = new LoadContact();
        loadContact.execute();
      //  search = (SearchView) findViewById(R.id.searchView);
        numbers=new ArrayList<String>();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener()
        {


            @Override
            public void onClick(View view, int position) {

                if(selectUsers.get(position).isCheckbox()==false) {
                    selectUsers.get(position).setCheckbox(true);
                    selectUsers.get(position).isCheckbox();
                    adapter.notifyDataSetChanged();
                    numbers.add(selectUsers.get(position).getPhone());

                }
                else
                {
                    selectUsers.get(position).setCheckbox(false);
                    selectUsers.get(position).isCheckbox();
                    adapter.notifyDataSetChanged();
                    numbers.remove(selectUsers.get(position).getPhone());

                }
            }

            @Override
            public void onLongClick(View view, final int position) {
                }
        }
        ));
              //Search view chceks the string
       /* searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                adapter.notifyDataSetChanged();
                return true;  }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("New Text",""+newText );
                adapter.getFilter().filter(newText);
                return true;              }
        });


*/
        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       Log.e("list",""+numbers);
                                       Intent intent=new Intent(SelectContact.this, CreateMessage.class);
                                       intent.putExtra("contacts",numbers.toString().replace("[", "").replace("]", ""));
                                       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                       startActivity(intent);
                                       finish();
                                   }
                               });


    }

    //Filters the list according to search
    void filter(String text) {
        List<ContactBean> temp = new ArrayList();
        for (ContactBean c : selectUsers) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (c.getName().toLowerCase().contains(text)) {
                temp.add(c);
            }
        }
        //update recyclerview
        adapter.updateList(temp);
        adapter.notifyDataSetChanged();
    }

    // Load data on background
    class LoadContact extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Get Contact list from Phone

            if (phones != null) {
                Log.e("count", "" + phones.getCount());
                if (phones.getCount() == 0) {
                    Toast.makeText(SelectContact.this, "No contacts in your contact list.", Toast.LENGTH_LONG).show();
                }

                while (phones.moveToNext()) {
                    String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    ContactBean selectUser = new ContactBean(name,phoneNumber,false);

     /*               selectUser.setName(name);
                    selectUser.setPhone(phoneNumber);
                    selectUser.setEmail(id);
                    selectUser.setCheckedBox(false);
     */               selectUsers.add(selectUser);
                }
            } else {
                Log.e("Cursor close 1", "----------------");
            }
            //phones.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter=new SelectContactAdapter(selectUsers,SelectContact.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(SelectContact.this, LinearLayoutManager.VERTICAL));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);



            // Select item on listclick
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        phones.close();
    }
}
