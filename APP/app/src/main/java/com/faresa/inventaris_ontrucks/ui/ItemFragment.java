package com.faresa.inventaris_ontrucks.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.faresa.inventaris_ontrucks.R;

public class ItemFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_item, container, false);

        View laptop = v.findViewById(R.id.cardLaptop);
        laptop.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_item_to_nav_laptop, null));


        View printer = v.findViewById(R.id.cardPrinter);
        printer.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_home_to_nav_printer, null));
        return v;
    }
}
