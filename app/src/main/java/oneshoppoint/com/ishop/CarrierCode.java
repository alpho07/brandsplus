package oneshoppoint.com.ishop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by stephineosoro on 07/06/16.
 */
public class CarrierCode extends AppCompatActivity {

    @Bind(R.id.retailercode)
    EditText retailer_code;
    @Bind(R.id.btn_signup)
    Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_retailer_code);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getIntent().getStringExtra("name"));
        final String id = getIntent().getStringExtra("id");
        String paybill = getIntent().getStringExtra("paybill");
        final String carrier_name= getIntent().getStringExtra("name");
        final String plan_name= getIntent().getStringExtra("planname");


        TextView tv= (TextView) findViewById(R.id.r_paybill);
        tv.setText(paybill);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = retailer_code.getText().toString();
                MyShortcuts.setDefaults("carriercode", code, getBaseContext());
                MyShortcuts.setDefaults("carrierId", id, getBaseContext());
                MyShortcuts.setDefaults("carriername", carrier_name, getBaseContext());
                MyShortcuts.setDefaults("planname", plan_name, getBaseContext());
                MyShortcuts.showToast("submitted successfully!", getBaseContext());
                MyShortcuts.setDefaults("carrierservice", "true", getBaseContext());
//                Intent intent = new Intent(getBaseContext(), CustomerInfo.class);
                Intent intent = new Intent(getBaseContext(), CustomerInfo.class);
                startActivity(intent);
            }
        });


    }
}
