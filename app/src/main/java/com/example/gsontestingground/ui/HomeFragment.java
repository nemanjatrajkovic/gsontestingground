package com.example.gsontestingground.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gsontestingground.R;
import com.example.gsontestingground.databinding.FragmentHomeBinding;
import com.example.gsontestingground.ui.language.LanguageSelectionFragmentArgs;
import com.example.gsontestingground.utils.Utils;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {

    @Override
    protected FragmentHomeBinding onCreateViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return FragmentHomeBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Utils.apply(getArguments(), args -> {
            final int selectedPos = LanguageSelectionFragmentArgs.fromBundle(args).getSelectedPos();
            Log.d("HM", "selectedPos: " +selectedPos);
        });
    }
}
