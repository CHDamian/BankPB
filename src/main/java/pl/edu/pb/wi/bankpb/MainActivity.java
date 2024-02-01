package pl.edu.pb.wi.bankpb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import pl.edu.pb.wi.bankpb.database.Model.Account;
import pl.edu.pb.wi.bankpb.viewModels.AccountViewModel;


public class MainActivity extends AppCompatActivity {

    private Button LoginButton;
    private Button RegisterButton;

    private AccountViewModel accountViewModel;

    private List<Account> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Log.d("database", "Acyivity");

        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.get().observe(this, this::setAccounts);

        LoginButton = findViewById(R.id.login_button);
        RegisterButton = findViewById(R.id.register_button);

        if(accounts == null)
        {
            LoginButton.setVisibility(View.GONE);
        }
        else if(accounts.isEmpty())
        {
            LoginButton.setVisibility(View.GONE);
        }
        else LoginButton.setVisibility(View.VISIBLE);

        RegisterButton.setOnClickListener((v)->{
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        LoginButton.setOnClickListener((v)->{
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    void setAccounts(List<Account> accounts)
    {
        this.accounts = accounts;
        if(this.accounts == null)
        {
            LoginButton.setVisibility(View.GONE);
        }
        else if(this.accounts.isEmpty())
        {
            LoginButton.setVisibility(View.GONE);
        }
        else LoginButton.setVisibility(View.VISIBLE);
    }
}