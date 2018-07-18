package com.abhishek.zipmap.ui.shortestPath;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.abhishek.zipmap.R;
import com.abhishek.zipmap.databinding.ActivityShortestPathBinding;

public class ShortestPathActivity extends AppCompatActivity {

    private String address1;
    private String address2;

    private ActivityShortestPathBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shortest_path);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            address1 = bundle.getString("firstLocation");
            address2 = bundle.getString("secondLocation");
        }

        binding.address1.setText(address1);
        binding.address2.setText(address2);

    }
}
