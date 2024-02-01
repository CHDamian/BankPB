package pl.edu.pb.wi.bankpb.APIHandler;

import android.os.AsyncTask;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyViewModel extends ViewModel {

    private static final String API_URL_USD = "https://api.nbp.pl/api/exchangerates/rates/a/usd/?format=json";
    private static final String API_URL_EUR = "https://api.nbp.pl/api/exchangerates/rates/a/eur/?format=json";
    private static final String API_URL_JPY = "https://api.nbp.pl/api/exchangerates/rates/a/jpy/?format=json";
    private static final String TAG = "CurrencyViewModel";

    public static final int CODE_USD = 0;
    public static final int CODE_EUR = 1;
    public static final int CODE_JPY = 2;

    private MutableLiveData<Double> currencyValueLiveData;
    private MutableLiveData<Double> currencyValueLiveEUR;
    private MutableLiveData<Double> currencyValueLiveJPY;

    public LiveData<Double> getCurrencyValueLiveUSD() {
        if (currencyValueLiveData == null) {
            currencyValueLiveData = new MutableLiveData<>();
            // Ustawienie wartości domyślnej
            currencyValueLiveData.setValue(1.0);
            // Rozpoczęcie pobierania danych z API w tle
            new CurrencyApiTask(0, API_URL_USD).execute();
        }
        return currencyValueLiveData;
    }

    public LiveData<Double> getCurrencyValueLiveEUR() {
        if (currencyValueLiveEUR == null) {
            currencyValueLiveEUR = new MutableLiveData<>();
            // Ustawienie wartości domyślnej
            currencyValueLiveEUR.setValue(1.0);
            // Rozpoczęcie pobierania danych z API w tle
            new CurrencyApiTask(1, API_URL_EUR).execute();
        }
        return currencyValueLiveEUR;
    }

    public LiveData<Double> getCurrencyValueLiveJPY() {
        if (currencyValueLiveJPY == null) {
            currencyValueLiveJPY = new MutableLiveData<>();
            // Ustawienie wartości domyślnej
            currencyValueLiveJPY.setValue(1.0);
            // Rozpoczęcie pobierania danych z API w tle
            new CurrencyApiTask(2, API_URL_JPY).execute();
        }
        return currencyValueLiveJPY;
    }


    private class CurrencyApiTask extends AsyncTask<Void, Void, Double> {

        private int code;
        private String API_URL;

        public CurrencyApiTask(int code, String API_URL)
        {
            super();
            this.code = code;
            this.API_URL = API_URL;
        }

        @Override
        protected Double doInBackground(Void... voids) {
            try {
                URL url = new URL(API_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                try {
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder stringBuilder = new StringBuilder();

                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }

                    JSONObject json = new JSONObject(stringBuilder.toString());
                    return json.getJSONArray("rates").getJSONObject(0).getDouble("mid");

                } finally {
                    urlConnection.disconnect();
                }

            } catch (IOException | JSONException e) {
                Log.e(TAG, "Błąd podczas pobierania danych z API: " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Double newValue) {
            // Aktualizacja LiveData po pobraniu danych z API
            if (newValue != null) {
                if(code == 0) {
                    currencyValueLiveData.setValue(newValue);
                }
                if(code == 1) {
                    currencyValueLiveEUR.setValue(newValue);
                }
                if(code == 2) {
                    currencyValueLiveJPY.setValue(newValue);
                }
            }
        }
    }
}
