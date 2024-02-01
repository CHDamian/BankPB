package pl.edu.pb.wi.bankpb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import pl.edu.pb.wi.bankpb.APIHandler.CurrencyViewModel;

public class RatesActivity extends AppCompatActivity {

    private CurrencyViewModel currencyViewModel;

    private Double rate;

    private EditText editTextValue;
    private Button buttonConvert;
    private TextView textViewValueUSD, textViewValueEUR, textViewValueJPY;
    private TextView textViewRateUSD, textViewRateEUR, textViewRateJPY;

    private int userValue;
    private double USDValue, EURValue, JPYValue;
    private double USDCalc, EURCalc, JPYCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rates);

        currencyViewModel = new ViewModelProvider(this).get(CurrencyViewModel.class);
        currencyViewModel.getCurrencyValueLiveUSD().observe(this, this::changeRateUSD);
        currencyViewModel.getCurrencyValueLiveEUR().observe(this, this::changeRateEUR);
        currencyViewModel.getCurrencyValueLiveJPY().observe(this, this::changeRateJPY);

        // Initialize views
        editTextValue = findViewById(R.id.editTextValue);
        buttonConvert = findViewById(R.id.buttonConvert);
        textViewValueUSD = findViewById(R.id.textViewValueUSD);
        textViewValueEUR = findViewById(R.id.textViewValueEUR);
        textViewValueJPY = findViewById(R.id.textViewValueJPY);

        textViewRateUSD = findViewById(R.id.textViewRateUSD);
        textViewRateEUR = findViewById(R.id.textViewRateEUR);
        textViewRateJPY = findViewById(R.id.textViewRateJPY);


        // Set listeners
        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userValue = Integer.parseInt(editTextValue.getText().toString());
                CalcUSD();
                CalcEUR();
                CalcJPY();
            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save variables to the bundle
        outState.putInt("userValue", userValue);
        outState.putDouble("USDValue", USDValue);
        outState.putDouble("EURValue", EURValue);
        outState.putDouble("JPYValue", JPYValue);
        outState.putDouble("USDCalc", USDCalc);
        outState.putDouble("EURCalc", EURCalc);
        outState.putDouble("JPYCalc", JPYCalc);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore variables from the bundle
        userValue = savedInstanceState.getInt("userValue");
        USDValue = savedInstanceState.getDouble("USDValue");
        EURValue = savedInstanceState.getDouble("EURValue");
        JPYValue = savedInstanceState.getDouble("JPYValue");
        USDCalc = savedInstanceState.getDouble("USDCalc");
        EURCalc = savedInstanceState.getDouble("EURCalc");
        JPYCalc = savedInstanceState.getDouble("JPYCalc");

        // Update views with restored values
        editTextValue.setText(String.valueOf(userValue));
        textViewValueUSD.setText(String.valueOf(USDCalc));
        textViewValueEUR.setText(String.valueOf(EURCalc));
        textViewValueJPY.setText(String.valueOf(JPYCalc));
        textViewRateUSD.setText(String.valueOf(USDValue));
        textViewRateEUR.setText(String.valueOf(EURValue));
        textViewRateJPY.setText(String.valueOf(JPYValue));
    }


    public void changeRateUSD(Double rate)
    {
        USDValue = rate;
        textViewRateUSD.setText(String.valueOf(USDValue));
        CalcUSD();
    }
    public void changeRateEUR(Double rate)
    {
        EURValue = rate;
        textViewRateEUR.setText(String.valueOf(EURValue));
        CalcEUR();
    }
    public void changeRateJPY(Double rate)
    {
        JPYValue = rate;
        textViewRateJPY.setText(String.valueOf(JPYValue));
        CalcJPY();
    }

    public void CalcUSD()
    {
        USDCalc = USDValue * userValue;
        textViewValueUSD.setText(String.valueOf(USDCalc));
    }
    public void CalcEUR()
    {
        EURCalc = EURValue * userValue;
        textViewValueEUR.setText(String.valueOf(EURCalc));
    }
    public void CalcJPY()
    {
        JPYCalc = JPYValue * userValue;
        textViewValueJPY.setText(String.valueOf(JPYCalc));
    }

}