package pabmocpl.dadm.labs.myquote;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    @SuppressLint("NonConstantResourceId")
    public void onDashboardButtonClick(View view) {

        Class activity = null;

        switch (view.getId()){
            case R.id.bGetQuotes:
                activity = QuotationActivity.class;
                break;
            case R.id.bFavoriteQuotes:
                activity = FavouriteActivity.class;
                break;
            case R.id.bSettings:
                activity = SettingsActivity.class;
                break;
            case R.id.bAbout:
                activity = AboutActivity.class;
                break;
        }

        if(activity != null) {
            Intent intent = new Intent(this, activity);
            startActivity(intent);
        }

    }
}