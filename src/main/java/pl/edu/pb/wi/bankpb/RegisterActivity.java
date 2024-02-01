package pl.edu.pb.wi.bankpb;

// RegisterActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import pl.edu.pb.wi.bankpb.database.Model.Account;
import pl.edu.pb.wi.bankpb.viewModels.AccountViewModel;
import pl.edu.pb.wi.bankpb.viewModels.TransactionViewModel;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etAccountNumber, etPIN;
    private AccountViewModel accountViewModel;

    private List<Account> accounts;

    private TransactionViewModel transactionViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etAccountNumber = findViewById(R.id.etAccountNumber);
        etPIN = findViewById(R.id.etPIN);

        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.get().observe(this, this::setAccounts);

        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        Button btnCreateAccount = findViewById(R.id.btnCreateAccount);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Walidacja danych
                if (validateData()) {
                    Account account = new Account();
                    account.name = etFirstName.getText().toString().trim();
                    account.surname = etLastName.getText().toString().trim();
                    account.pin = etPIN.getText().toString().trim();
                    account.balance = 5000;

                    if(accountViewModel.get().getValue() != null)
                    {
                        List<Account> items = accountViewModel.get().getValue();
                        if(!items.isEmpty())
                        {
                            Account item = items.get(items.size() - 1);
                            accountViewModel.delete(item);
                            transactionViewModel.nuke();
                        }
                    }

                    accountViewModel.insert(account);

                    // Przejście do MainActivity
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);

                    finish();
                }
            }
        });
    }

    private boolean validateData() {
        // Walidacja danych (np. sprawdzanie czy pola są wypełnione)
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String accountNumber = etAccountNumber.getText().toString().trim();
        String pin = etPIN.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || accountNumber.isEmpty() || pin.isEmpty()) {
            Toast.makeText(this, "Wypełnij wszystkie pola", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Dodatkowe warunki walidacji (np. sprawdzanie poprawności numeru konta, PIN itp.)
        if (accountNumber.length() != 26) {
            Toast.makeText(this, "Numer konta powinien zawierać 26 cyfr", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (pin.length() != 4) {
            Toast.makeText(this, "PIN powinien zawierać 4 cyfry", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    void setAccounts(List<Account> accounts)
    {
        this.accounts = accounts;
    }
}
