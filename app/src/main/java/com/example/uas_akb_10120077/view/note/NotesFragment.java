package com.example.uas_akb_10120077.view.note;

/**
 * NAMA    : Mohammad Noor Ihsan Akbar
 * NIM     : 10120077
 * Kelas   : IF-2
 * MatKul  : Aplikasi Komputasi Bergerak
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.uas_akb_10120077.adapter.NoteAdapter;
import com.example.uas_akb_10120077.databinding.FragmentNotesBinding;
import com.example.uas_akb_10120077.entity.Notes;
import com.example.uas_akb_10120077.view.login.LoginActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class NotesFragment extends Fragment {
    private FragmentNotesBinding binding;
    private FirebaseAuth auth;

    private DatabaseReference databaseReference;
    private NoteAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNotesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Note");

        binding.loading.setVisibility(View.VISIBLE);
        binding.tvNoData.setVisibility(View.VISIBLE);

        setupUser();
        loadData();

        binding.btnAdd.setOnClickListener(v -> gotoAdd());

    }

    private void loadData() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userNotesRef = databaseReference.child(userId);

            LinearLayoutManager manager = new LinearLayoutManager(requireContext());
            binding.rvNote.setLayoutManager(manager);

            FirebaseRecyclerOptions<Notes> options =
                    new FirebaseRecyclerOptions.Builder<Notes>()
                            .setQuery(userNotesRef, Notes.class)
                            .build();
            adapter = new NoteAdapter(options, (item, noteKey) -> {
                showDetailActivity(item, noteKey);
            });
            binding.rvNote.setAdapter(adapter);

        }
        binding.loading.setVisibility(View.INVISIBLE);
    }

    private void showDetailActivity(Notes item, String noteKey) {
        Intent intent = new Intent(requireContext(), NotesActivity.class);
        intent.putExtra("notes",item);
        intent.putExtra("note_key", noteKey);
        startActivity(intent);
    }

    private void gotoAdd() {
        Intent intent = new Intent(requireContext(), TambahNoteActivity.class);
        startActivity(intent);
    }


    private void setupUser() {
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(requireContext(), LoginActivity.class));
            requireActivity().finish();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        adapter.startListening();

    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.stopListening();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}