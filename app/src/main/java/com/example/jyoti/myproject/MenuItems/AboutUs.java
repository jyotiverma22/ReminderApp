package com.example.jyoti.myproject.MenuItems;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.jyoti.myproject.R;

public class AboutUs extends AppCompatActivity {
    LinearLayout ll1,ll,ll2,ll3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ll=(LinearLayout)findViewById(R.id.linearLayout);
        ll1=(LinearLayout)findViewById(R.id.linearLayout2);
        ll2=(LinearLayout)findViewById(R.id.linearLayout3);
        ll3=(LinearLayout)findViewById(R.id.linearLayout4);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About Us");
        ll1.setSelected(true);
        ll.setSelected(true);
        ll2.setSelected(true);
        ll3.setSelected(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
// finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}


