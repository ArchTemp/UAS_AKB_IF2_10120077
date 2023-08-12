package com.example.uas_akb_10120077.view.info;

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
import androidx.viewpager2.widget.ViewPager2;

import com.example.uas_akb_10120077.R;
import com.example.uas_akb_10120077.adapter.ViewPagerItem;
import com.example.uas_akb_10120077.adapter.ViewPagerAdapter;
import com.example.uas_akb_10120077.databinding.FragmentInfoBinding;
import com.example.uas_akb_10120077.view.login.LoginActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class InfoFragment extends Fragment {

    private FragmentInfoBinding binding;
    private FirebaseAuth auth;

    ViewPager2 viewPager;

    ArrayList<ViewPagerItem> viewPagerItemArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            binding.tvWelcome.setText("Hello!");
        } else {
            startActivity(new Intent(requireContext(), LoginActivity.class));
            requireActivity().finish();
        }

        int[] images = {R.drawable.viewpager1, R.drawable.viewpager2};
        String[] headings = {"Aplikasi My Notes Yo! dibuat untuk memenuhi tugas mata kuliah Aplikasi Komputasi Bergerak (AKB)", "This My Notes Yo! is a Note app where users can create, edit, and delete notes easily."};

        viewPagerItemArrayList = new ArrayList<>();

        for (int i = 0; i < images.length; i++) {
            ViewPagerItem viewPagerItem = new ViewPagerItem(images[i], headings[i]);
            viewPagerItemArrayList.add(viewPagerItem);
        }

        ViewPagerAdapter ViewPagerAdapter = new ViewPagerAdapter(requireContext(), viewPagerItemArrayList);

        viewPager = binding.viewpager;
        viewPager.setAdapter(ViewPagerAdapter);
        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(2);
        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        TabLayout tabLayout = binding.tabLayout;
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    // Set the tab text or icon here
                    tab.setText("Halaman " + (position + 1));
                });
        tabLayoutMediator.attach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}