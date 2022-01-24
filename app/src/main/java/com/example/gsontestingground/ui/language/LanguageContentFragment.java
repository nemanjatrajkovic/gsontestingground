package com.example.gsontestingground.ui.language;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gsontestingground.databinding.FragmentLanguageContentBinding;
import com.example.gsontestingground.json.StringsId;
import com.example.gsontestingground.json.io.Translation;
import com.example.gsontestingground.json.io.TranslationsViewModel;
import com.example.gsontestingground.json.io.TranslatorIO;
import com.example.gsontestingground.ui.BaseFragment;

public class LanguageContentFragment extends BaseFragment<FragmentLanguageContentBinding> {

    private TranslationsViewModel viewModel;

    @Override
    protected FragmentLanguageContentBinding onCreateViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return FragmentLanguageContentBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();
    }

    private void init() {
        viewModel = activityViewModel(TranslationsViewModel.class);
        withBnd(bnd -> {
            updateTranslation();
            viewModel.selectedPositionLive().observe(getViewLifecycleOwner(), position -> {
                updateTranslation();
            });
        });
    }

    private void updateTranslation() {
        withBnd(bnd -> {
            bnd.helloText.setText(Translation.getString(StringsId.hello));
            bnd.welcomeText.setText(Translation.getString(StringsId.welcome));
            bnd.descriptionTxt.setText(Translation.getString(StringsId.description));
            bnd.previousLanguage.setText(Translation.getString(StringsId.previousButton));
            bnd.nextLanguage.setText(Translation.getString(StringsId.nextButton));
            bnd.previousLanguage.setOnClickListener(v -> {
                viewModel.updateLanguageSelection(viewModel.getOther());
            });
            bnd.nextLanguage.setOnClickListener(v -> {
                viewModel.updateLanguageSelection(viewModel.getOther());
            });
        });
    }
}
