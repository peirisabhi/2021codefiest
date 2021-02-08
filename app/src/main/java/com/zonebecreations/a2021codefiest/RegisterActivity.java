package com.zonebecreations.a2021codefiest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.zonebecreations.a2021codefiest.model.Customer;

public class RegisterActivity extends AppCompatActivity {

    EditText name, email, mobile, address;
    Button register;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = FirebaseFirestore.getInstance();

        name = findViewById(R.id.textName);
        email = findViewById(R.id.textEmail);
        mobile = findViewById(R.id.textMobile);
        address = findViewById(R.id.textAddress);
        register = findViewById(R.id.button);

        Intent intent = getIntent();

        if (intent != null) {
            name.setText(intent.getStringExtra("name") + "");
            email.setText(intent.getStringExtra("email") + "");
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Customer customer = new Customer(name.getText().toString(), email.getText().toString(), mobile.getText().toString(), address.getText().toString(), 1);

                db.collection("customers")
                        .add(customer)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("FIRE", "DocumentSnapshot added with ID: " + documentReference.getId());
                                Toast.makeText(RegisterActivity.this, "Registration Success", Toast.LENGTH_LONG).show();

                                Intent intent1 = new Intent(RegisterActivity.this, HomeActivity.class);
                                intent1.putExtra("name", name.getText().toString());
                                intent1.putExtra("email", email.getText().toString());

                                startActivity(intent1);
                                finish();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("FIRE", "Error adding document", e);
                                Toast.makeText(RegisterActivity.this, "Registration Faild", Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });

    }
}