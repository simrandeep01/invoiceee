package com.example.adity.invoicemaker;

import android.*;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText email,pass;
    Button signin;
    TextView forgot;
    ProgressDialog pd;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.pass);
        signin=(Button)findViewById(R.id.signin);
        forgot=(TextView) findViewById(R.id.forgot);
        int permissionCheck1 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck1 != PackageManager.PERMISSION_GRANTED&&permissionCheck2 != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,  android.Manifest.permission.READ_EXTERNAL_STORAGE
                    },123);
        }

        ActionBar a=getSupportActionBar();
        if(a!=null)
            a.hide();

        pd=new ProgressDialog(this);
       mAuth = FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null)
        {
            startActivity(new Intent(MainActivity.this,NavigationDrawer.class));
            finish();

        }
        signin.setOnClickListener(new View.OnClickListener() {
            @Override





            public void onClick(View v) {

                final String em=email.getText().toString().trim();
                String pas=pass.getText().toString().trim();

                if((em.equals("")) || !(em.contains("@")) || !(em.contains(".")))
                {   email.setText("");
                    email.setHint("Enter  valid Email");}

                else if(pas.equals(""))
                {   pass.setText("");
                    pass.setHint("please Enter a valid Password");
                }

                else
                {
                    pd.setMessage("Logging In");
                    pd.show();

                    mAuth.signInWithEmailAndPassword(em,pas).addOnCompleteListener(MainActivity.this,new OnCompleteListener<AuthResult>(){
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                pd.hide();
                                Intent i=new Intent(MainActivity.this,NavigationDrawer.class);
                                //i.putExtra("email",em);
                                startActivity(i);
                                finish();
                            }

                            else {
                                pd.hide();

                                    Toast.makeText(MainActivity.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                            }


                        }
                    });


                }
            }
        });




        Button sup=(Button)findViewById(R.id.signup);
        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,signup.class));
            }
        });










    }
}
