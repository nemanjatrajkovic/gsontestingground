package com.example.gsontestingground.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.util.Log;

import com.example.gsontestingground.R;
import com.example.gsontestingground.databinding.ActivityMainBinding;
import com.example.gsontestingground.json.io.TranslationsViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding bnd;
    private TranslationsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        initViewModel();

        /*bnd.startFragment.setOnClickListener( v -> {
            Log.d("MA", ":: replace fragment");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainHostContainer, new LanguageContentFragment())
                    .commit();
        });*/

        final NavHostFragment host = ((NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.mainHostContainer));
//        host.getNavController().navigate(R.id.lang_content_dest);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(TranslationsViewModel.class);
        viewModel.setTranslatorIOContext(getApplicationContext());
    }
}