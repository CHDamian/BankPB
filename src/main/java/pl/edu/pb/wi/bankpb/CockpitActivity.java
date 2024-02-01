package pl.edu.pb.wi.bankpb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import pl.edu.pb.wi.bankpb.database.Model.Account;
import pl.edu.pb.wi.bankpb.viewModels.AccountViewModel;


public class CockpitActivity extends AppCompatActivity {

    private TextView balanceText;
    private TextView textViewName;

    private AccountViewModel accountViewModel;

    private List<Account> accounts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cockpit);

        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.get().observe(this, this::setAccounts);

        // Inicjalizacja TextView
        balanceText = findViewById(R.id.BalanceText);
        textViewName = findViewById(R.id.textViewName);

        // Inicjalizacja przycisków
        Button transferButton = findViewById(R.id.przelewButton);
        Button collectButton = findViewById(R.id.odbiorButton);
        Button historyButton = findViewById(R.id.historiaButton);
        Button ratesButton = findViewById(R.id.kursyButton);
        Button logoutButton = findViewById(R.id.wylogujButton);

        // Ustawienie onClickListener dla przycisków
        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CockpitActivity.this, TransferActivity.class));
            }
        });

        collectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CockpitActivity.this, DownloadActivity.class));
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CockpitActivity.this, HistoryActivity.class));
            }
        });

        ratesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CockpitActivity.this, RatesActivity.class));
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CockpitActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    // Funkcja do ustawiania salda na widoku
    private void setBalanceView(int balance) {
        balanceText.setText("Saldo: " + balance + " PLN");
    }
    private void setNameView(String name, String surname) {
        textViewName.setText("Witaj " + name + " " + surname);
    }

    void setAccounts(List<Account> accounts)
    {
        this.accounts = accounts;
        if(this.accounts != null)
        {
            setBalanceView(this.accounts.get(0).balance);
            setNameView(accounts.get(0).name, accounts.get(0).surname);
        }
    }
}
