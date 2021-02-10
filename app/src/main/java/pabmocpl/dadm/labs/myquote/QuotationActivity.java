package pabmocpl.dadm.labs.myquote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class QuotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        TextView tvQuotation = findViewById(R.id.tvQuotation);
        String data = tvQuotation.getText().toString().replaceAll("%1s", getString(R.string.sample_name));
        tvQuotation.setText(data);

        TextView tvAuthor = findViewById(R.id.tvAuthor);
        ImageButton refreshButton = findViewById(R.id.ibRefresh);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvQuotation.setText(R.string.sample_quote);
                tvAuthor.setText(R.string.sample_author);
            }
        });
    }
}