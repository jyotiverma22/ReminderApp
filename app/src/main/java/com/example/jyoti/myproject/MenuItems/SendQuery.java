package com.example.jyoti.myproject.MenuItems;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jyoti.myproject.API_classes.APIService;
import com.example.jyoti.myproject.API_classes.ApiClient;
import com.example.jyoti.myproject.API_classes.MailClass;
import com.example.jyoti.myproject.Preference.ReminderPreference;
import com.example.jyoti.myproject.R;
import com.example.jyoti.myproject.networkConnection.NetworkConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendQuery extends AppCompatActivity {
    EditText etname,etnumber,etmail,etmsg;
    Button bsubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_query);
        etname=(EditText)findViewById(R.id.etname);
        etnumber=(EditText)findViewById(R.id.etnumber);
        etmail=(EditText)findViewById(R.id.etmail);
        etmsg=(EditText)findViewById(R.id.etmsg);
        bsubmit=(Button)findViewById(R.id.bsubmit);

        etname.setText(ReminderPreference.getAppUserName());
        etmail.setText(ReminderPreference.getAppEmail());

        bsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((etname.getText().length()<1)||(etmsg.getText().length()<1)||(etnumber.getText().length()<1)||(etmail.getText().length()<1))
                {
                    Toast.makeText(SendQuery.this, "Please Fill all Fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(NetworkConnection.isNetworkAvailable(getApplicationContext())) {

                        APIService service = ApiClient.getClient().create(APIService.class);
                        Call<MailClass> userCall = service.getEnquiryDetails("enquiry", etmail.getText().toString(), etname.getText().toString(), etnumber.getText().toString(), "Enquiry", etmsg.getText().toString());
                        userCall.enqueue(new Callback<MailClass>() {
                            @Override
                            public void onResponse(Call<MailClass> call, Response<MailClass> response) {

                                if (response.isSuccessful()) {
                                    if (response.body().getStatus() == true) {
                                        Toast.makeText(SendQuery.this, "Query or Suggestion sent", Toast.LENGTH_SHORT).show();
                                    } else {
//                        Toast.makeText(testActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<MailClass> call, Throwable t) {
                                //hidepDialog();
                                Log.d("onFailure", t.toString());
                            }
                        });
                    }
                    else{
                        Toast.makeText(SendQuery.this, "Check your network connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
