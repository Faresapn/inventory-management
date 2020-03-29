package com.faresa.inventaris_ontrucks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.faresa.inventaris_ontrucks.R;
import com.faresa.inventaris_ontrucks.SharedPrefManager;
import com.faresa.inventaris_ontrucks.connection.Client;
import com.faresa.inventaris_ontrucks.connection.Service;
import com.faresa.inventaris_ontrucks.pojo.admin.AdminResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPrefManager = new SharedPrefManager(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        try {
            sharedPrefManager.getSpToken();
        } catch (Exception e) {
            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
            startActivity(new Intent(MainActivity.this, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        final TextView userEmail = headerView.findViewById(R.id.emailku);
        Service service = Client.getClient().create(Service.class);
        String token = sharedPrefManager.getSpToken();
        Call<AdminResponse> eventCall = service.getUser("Bearer " + token);
        eventCall.enqueue(new Callback<AdminResponse>() {
            @Override
            public void onResponse(@NotNull Call<AdminResponse> call, @NotNull Response<AdminResponse> response) {
                AdminResponse adminResponse = response.body();
                assert adminResponse != null;
                userEmail.setText(String.valueOf(adminResponse.getUser().getEmail()));

            }

            @Override
            public void onFailure(Call<AdminResponse> call, Throwable t) {

            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_divisi, R.id.nav_printer,
                R.id.nav_item, R.id.nav_profile, R.id.nav_info, R.id.nav_pegawai,
                R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
