package com.faresa.inventaris_ontrucks.ui.logout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.faresa.inventaris_ontrucks.R;
import com.faresa.inventaris_ontrucks.SharedPrefManager;

public class LogoutFragment extends Fragment {

    SharedPrefManager sharedPrefManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_send, container, false);
        NavController navController = Navigation.findNavController(v);

        navController.navigate(R.id.act_login);

        return v;
    }
}