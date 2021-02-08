package com.zonebecreations.a2021codefiest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    TextView name, email;
    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        logOut = findViewById(R.id.button2);

        if (intent != null) {
            name.setText(intent.getStringExtra("name") + "");
            email.setText(intent.getStringExtra("email") + "");
        }

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent1 = new Intent(HomeActivity.this, MainActivity.class);
//                startActivity(intent1);
//                finish();

                AuthUI.getInstance()
                        .signOut(HomeActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent1 = new Intent(HomeActivity.this, MainActivity.class);
                                startActivity(intent1);
                                finish();
                            }
                        });
            }
        });

    }
}