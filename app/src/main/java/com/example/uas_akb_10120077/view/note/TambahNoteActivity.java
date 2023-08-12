package com.example.uas_akb_10120077.view.note;

/**
 * NAMA    : Mohammad Noor Ihsan Akbar
 * NIM     : 10120077
 * Kelas   : IF-2
 * MatKul  : Aplikasi Komputasi Bergerak
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.uas_akb_10120077.databinding.ActivityTambahNoteBinding;
import com.example.uas_akb_10120077.entity.Notes;
import com.example.uas_akb_10120077.view.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TambahNoteActivity extends AppCompatActivity {

    private ActivityTambahNoteBinding binding;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTambahNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Note");

        setupUser();
        binding.btnSubmit.setOnClickListener(v -> addNoteToDatabase());

    }


    private void addNoteToDatabase() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String title = binding.edtTitle.getText().toString().trim();
            String category = binding.edtCategory.getText().toString().trim();
            String content = binding.edtContent.getText().toString().trim();

            if (TextUtils.isEmpty(title)) {
                Toast.makeText(this, "Title tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(category)) {
                Toast.makeText(this, "Category tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(content)) {
                Toast.makeText(this, "Message tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            } else {
                String userId = currentUser.getUid();
                DatabaseReference userNotesRef = databaseReference.child(userId);

                Notes notes = new Notes(getCurrentDate(), title, category, content);

                userNotesRef.push().setValue(notes, (error, ref) -> {
                    if (error != null) {
                        Toast.makeText(this, "Data Created Failed!" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Data Created Successfully!", Toast.LENGTH_SHORT).show();

                        finish();
                    }
                });
            }
        }
    }

    private void setupUser() {
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        }
    }

    private String getCurrentDate() {

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);

        return formattedDate;
    }
}