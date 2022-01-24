package com.example.gsontestingground.json;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.gsontestingground.R;
import com.example.gsontestingground.databinding.ActivityJsonBinding;
import com.example.gsontestingground.databinding.ActivityMainBinding;
import com.example.gsontestingground.json.io.SimpleStringResult;
import com.example.gsontestingground.json.io.TranslationsViewModel;

public class JsonActivity extends AppCompatActivity {

    private TranslationsViewModel viewModel;
    private ActivityJsonBinding bnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityJsonBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        viewModel = new ViewModelProvider(this).get(TranslationsViewModel.class);
        viewModel.setTranslatorIOContext(this);
        bnd.processFiles.setOnClickListener(v -> readFiles());
        getString(R.string.stringArgs, "1");
    }

    private void readFiles() {
        viewModel.processFiles(new SimpleStringResult() {
            @Override
            public void onResult(@NonNull StringBuilder strings) {
                postTextResult(strings.toString());
            }

            @Override
            public void onComplete(@NonNull StringBuilder strings) {
                postTextResult(strings.toString());
            }
        });
    }

    private void postTextResult(@NonNull final String text) {
        bnd.getRoot().post(() -> {
            bnd.displayText.setText(text);
        });
    }
}