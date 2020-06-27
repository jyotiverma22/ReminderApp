package com.example.jyoti.myproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jyoti.myproject.Preference.ReminderPreference;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Context context = SplashScreen.this;
        AlertDialog.Builder dialog = new AlertDialog.Builder(context,R.style.MyDialogTheme);
        final LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        //Edit text for stoing user details
        final EditText tvusername = new EditText(context);
        tvusername.setHint("Full Name");
        layout.addView(tvusername);

        final EditText tvemail= new EditText(context);
        tvemail.setHint("Email ID");
        layout.addView(tvemail);

        final EditText tvpin= new EditText(context);
        tvpin.setHint("Secret Key");
        tvpin.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        tvpin.setTransformationMethod(PasswordTransformationMethod.getInstance());
        layout.addView(tvpin);

        final EditText tvconfirmpin= new EditText(context);
        tvconfirmpin.setHint("Confirm Secret Key");
        tvconfirmpin.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        tvconfirmpin.setTransformationMethod(PasswordTransformationMethod.getInstance());
        layout.addView(tvconfirmpin);

        dialog.setView(layout);


        if (ReminderPreference.getAppStatus(getApplicationContext())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e("Secret key",""+ReminderPreference.getAppUserPin());
                    final Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }
            }, 1000);

        }
  else{
            dialog.setTitle("Enter User Details").setPositiveButton("Submit", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    //Do nothing here because we override this button later to change the close behaviour.
                    //However, we still need this because on older versions of Android unless we
                    //pass a handler the button doesn't get instantiated
                }
            });

            final AlertDialog dialog2 = dialog.create();
                    dialog2.show();

            //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
            dialog2.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(tvusername.getText().length()<1)
                    {
                        Toast.makeText(SplashScreen.this, "Enter your full name", Toast.LENGTH_SHORT).show();
                        tvusername.requestFocus();
                    }
                    else if(tvemail.getText().length()<1)
                    {
                        Toast.makeText(SplashScreen.this, "Enter your Email Address", Toast.LENGTH_SHORT).show();
                        tvemail.requestFocus();

                    }
                    else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(tvemail.getText()).matches())
                    {
                        Toast.makeText(SplashScreen.this, "Enter valid Email Address", Toast.LENGTH_SHORT).show();
                        tvemail.requestFocus();

                    }

                    else if(tvpin.getText().length()!=4)
                    {
                        Toast.makeText(SplashScreen.this, "Secret key must be of 4 digits", Toast.LENGTH_SHORT).show();
                        tvpin.requestFocus();

                    }
                    else if(!tvpin.getText().toString().equals(tvconfirmpin.getText().toString()))
                    {
                        Toast.makeText(SplashScreen.this, "Confim key does not match", Toast.LENGTH_SHORT).show();
                        tvconfirmpin.requestFocus();

                    }
                    else {
                        ReminderPreference.setAppUser(getApplicationContext(),tvusername.getText().toString(),tvemail.getText().toString(),Integer.parseInt(tvpin.getText().toString()),true);
                          dialog2.dismiss();
                        final Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                        SplashScreen.this.startActivity(mainIntent);
                        SplashScreen.this.finish();


                    }

                }
            });


  }




    }
}
