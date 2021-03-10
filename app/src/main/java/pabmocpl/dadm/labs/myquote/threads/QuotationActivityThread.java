package pabmocpl.dadm.labs.myquote.threads;

import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import pabmocpl.dadm.labs.myquote.R;
import pabmocpl.dadm.labs.myquote.activities.QuotationActivity;
import pabmocpl.dadm.labs.myquote.objects.Quotation;

public class QuotationActivityThread extends Thread {
    private WeakReference<QuotationActivity> reference;

    public QuotationActivityThread(QuotationActivity quotationActivity) {
        this.reference = new WeakReference<>(quotationActivity);
    }

    @Override
    public void run() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(reference.get());
        String lang = preferences.getString(reference.get().getString(R.string.pref_language_key), "en");
        String method = preferences.getString(reference.get().getString(R.string.pref_method_key), "GET");
        Log.i("webservice", lang);
        Log.i("webservice", method);

        Uri.Builder builder = new Uri.Builder()
                .scheme("https")
                .authority("api.forismatic.com")
                .appendPath("api")
                .appendPath("1.0")
                .appendPath("");

        if (method.equals("GET")) {
            builder.appendQueryParameter("method", "getQuote")
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("lang", lang);
        }

        try {
            URL url = new URL(builder.toString());
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod(method);

            if (method.equals("POST")) {
                String postQuery = "method=getQuote&format=json&lang=" + lang;
                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(postQuery);
                writer.flush();
                writer.close();
            }

            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                Gson gson = new Gson();
                Quotation quotation = gson.fromJson(reader, Quotation.class);
                reader.close();
                if (reference.get() != null)
                    reference.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            reference.get().onQuotationReceived(quotation);
                        }
                    });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
