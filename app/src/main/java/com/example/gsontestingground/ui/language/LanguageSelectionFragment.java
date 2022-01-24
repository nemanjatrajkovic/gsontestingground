package com.example.gsontestingground.ui.language;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gsontestingground.json.StringsId;
import com.example.gsontestingground.json.io.Translation;
import com.example.gsontestingground.json.io.TranslationsViewModel;
import com.example.gsontestingground.ui.BaseFragment;
import com.example.gsontestingground.R;
import com.example.gsontestingground.databinding.FragmentLanguageSelectionBinding;
import com.example.gsontestingground.json.io.TranslatorIO;

import java.util.Locale;

public class LanguageSelectionFragment extends BaseFragment<FragmentLanguageSelectionBinding> {

    private static final String TAG = "LSF";
    private TranslationsViewModel viewModel;

    @Override
    protected FragmentLanguageSelectionBinding onCreateViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return FragmentLanguageSelectionBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUi();
    }

    protected int selectedPos() {
        return viewModel.getSelectedLangPos();
    }

    private void initUi() {
        viewModel = activityViewModel(TranslationsViewModel.class);
        withBnd(bnd -> {
            adapter = new ArrayAdapter<>(getContext(), R.layout.language_item, supportedLanguages());
            final Spinner langChoice = bnd.langChoice;
            langChoice.setAdapter(adapter);
            langChoice.setOnItemSelectedListener(null);
            langChoice.setOnItemSelectedListener(selector);
            viewModel.selectedPositionLive().observe(getViewLifecycleOwner(), position -> {
                if (position != null) langChoice.setSelection(position);
            });
            viewModel.selectedPositionLive().observe(getViewLifecycleOwner(), pos -> {
                updateTranslation();
            });
        });

    }

    private void updateTranslation() {
        bnd.selectSub.setText(Translation.getString(StringsId.selectLanguage));
    }

    private LocaleInfo[] supportedLanguages() {
        return LocaleInfo.from(TranslatorIO.supportedLocales());
    }

    private ArrayAdapter<LocaleInfo> adapter;

    private final AdapterView.OnItemSelectedListener selector = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            final Object item = parent.getItemAtPosition(position);
            if (!(item instanceof LocaleInfo)) return;

            final Locale data = ((LocaleInfo) item).locale;
            ((TextView) view).setText(item.toString());
            Log.d(TAG, String.format("::Selected language: dl: %s, l: %s", data.getDisplayLanguage(), data.getLanguage()));
            if (selectedPos() != position) updateSelection(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    protected void refreshHome() {
        replace(R.id.home_dest);
    }

    protected void refreshHome(final int selectedPos) {
        final Bundle args = null;
        replace(R.id.home_dest, args);
    }

    protected void updateSelection(final int selectedPos) {
       viewModel.updateLanguageSelection(selectedPos);
    }
}