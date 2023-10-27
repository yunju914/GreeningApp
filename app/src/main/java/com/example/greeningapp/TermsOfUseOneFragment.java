package com.example.greeningapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class TermsOfUseOneFragment extends Fragment {
    private Button closeTab;

    public TermsOfUseOneFragment() {

    }

    public static TermsOfUseOneFragment newInstance(String param1, String param2) {
        TermsOfUseOneFragment fragment = new TermsOfUseOneFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_terms_of_use_one, container, false);
    }
}