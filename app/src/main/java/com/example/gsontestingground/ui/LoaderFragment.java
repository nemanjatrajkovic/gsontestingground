package com.example.gsontestingground.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gsontestingground.R;
import com.example.gsontestingground.databinding.FragmentLoaderBinding;
import com.example.gsontestingground.json.io.TranslationsViewModel;

public class LoaderFragment extends BaseFragment<FragmentLoaderBinding> {

    private TranslationsViewModel viewModel;
    private int selectedPos = -1;

    @Override
    protected FragmentLoaderBinding onCreateViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = activityViewModel(TranslationsViewModel.class);
        return FragmentLoaderBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        withBnd(bnd -> {
            bnd.goTo.setOnClickListener(v -> {
                navController().navigate(R.id.json_activity);
            });
            viewModel.selectedPositionLive().observe(getViewLifecycleOwner(), position -> {
                if (position == null || position == -1) return;

                setInfoLoading(false);
                replace(R.id.home_dest);
            });
            loadTranslation();
        });
    }

    private void loadTranslation() {
        setInfoLoading(true);
        selectedPos = viewModel.getSelectedLangPos();
        viewModel.updateLanguageSelection(-1);
    }

    private void setInfoLoading(final boolean isLoading) {
        withBnd(bnd -> {
            if (isLoading) {
                bnd.loadingText.setText("Loading translation");
                bnd.progress.setVisibility(View.VISIBLE);
                bnd.goTo.setVisibility(View.INVISIBLE);
            } else {
                bnd.loadingText.setText("Loading completed");
                bnd.progress.setVisibility(View.INVISIBLE);
                bnd.goTo.setVisibility(View.VISIBLE);
            }
        });
    }
}
