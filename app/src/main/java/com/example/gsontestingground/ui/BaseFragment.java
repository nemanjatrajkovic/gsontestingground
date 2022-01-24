package com.example.gsontestingground.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.viewbinding.ViewBinding;

import com.example.gsontestingground.R;

import java.util.function.Consumer;

public class BaseFragment<BND extends ViewBinding> extends Fragment {

    protected BND bnd;

    protected BND onCreateViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final BND bnd = onCreateViewBinding(inflater, container, savedInstanceState);
        this.bnd = bnd; final View root;
        if (bnd != null) {
            root = bnd.getRoot();
        } else {
            Log.w("BF", ":: bnd == null !!");
            root = super.onCreateView(inflater, container, savedInstanceState);
        }

        return root;
    }

    protected void withBnd(@NonNull final Consumer<BND> consumer) {
        final BND bnd = this.bnd;
        if (bnd != null) consumer.accept(bnd);
    }

    protected NavController navController() {
        return Navigation.findNavController(bnd.getRoot());
    }

    protected void replace(final int navId) {
        final NavOptions navOptions = new NavOptions.Builder()
                .setLaunchSingleTop(true)
                .build();
       navigate(navId, null, navOptions);
    }

    protected void replace(final int navId, Bundle args) {
        final NavOptions navOptions = new NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setPopUpTo(R.id.home_dest, false)
                .build();
       navigate(navId, args, navOptions);
    }

    protected void navigate(final int navId, Bundle args, NavOptions navOptions) {
        navController().navigate(navId, args, navOptions);
    }

    protected <T extends ViewModel> T activityViewModel(Class<T> modelClass) {
        return (new ViewModelProvider(requireActivity()).get(modelClass));
    }

    protected <T extends ViewModel> T fragmentViewModel(Class<T> modelClass) {
        return (new ViewModelProvider(this).get(modelClass));
    }
}
