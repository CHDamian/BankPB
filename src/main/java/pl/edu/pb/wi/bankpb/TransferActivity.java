package pl.edu.pb.wi.bankpb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pl.edu.pb.wi.bankpb.database.Model.Account;
import pl.edu.pb.wi.bankpb.database.Model.Transaction;
import pl.edu.pb.wi.bankpb.viewModels.AccountViewModel;
import pl.edu.pb.wi.bankpb.viewModels.TransactionViewModel;

public class TransferActivity extends AppCompatActivity {

    private EditText editTextAccountNumber, editTextRecipient, editTextTitle, editTextAmount;
    private TextView textViewInfo;

    private AccountViewModel accountViewModel;
    private List<Account> accounts;

    private TransactionViewModel transactionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.get().observe(this, this::setAccounts);

        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        // Inicjalizacja widoków
        editTextAccountNumber = findViewById(R.id.editTextAccountNumber);
        editTextRecipient = findViewById(R.id.editTextRecipient);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextAmount = findViewById(R.id.editTextAmount);
        textViewInfo = findViewById(R.id.textViewInfo);

        Button buttonSend = findViewById(R.id.buttonSend);

        // Ustawienie grawitacji dla kontenera
        LinearLayout container = findViewById(R.id.container);
        container.setGravity(Gravity.CENTER);

        // Przypisanie akcji do przycisku
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Walidacja danych
                if (validateData()) {

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String currentDate = dateFormat.format(calendar.getTime());

                    String accountNumber = editTextAccountNumber.getText().toString().trim();
                    String recipient = editTextRecipient.getText().toString().trim();
                    String title = editTextTitle.getText().toString().trim();
                    String amountStr = editTextAmount.getText().toString().trim();

                    Transaction transaction = new Transaction();
                    transaction.receiverNumber = accountNumber;
                    transaction.receiverName = recipient;
                    transaction.title = title;
                    transaction.totalSum = Integer.parseInt(amountStr) * -1;
                    transaction.transactionDate = currentDate;

                    Account account = accounts.get(0);
                    account.balance += transaction.totalSum;
                    accountViewModel.update(account);
                    transactionViewModel.insert(transaction);

                    // Przejście do MainActivity
                    Intent intent = new Intent(TransferActivity.this, CockpitActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    // Metoda do walidacji danych
    private boolean validateData() {

        String accountNumber = editTextAccountNumber.getText().toString().trim();
        String recipient = editTextRecipient.getText().toString().trim();
        String title = editTextTitle.getText().toString().trim();
        String amountStr = editTextAmount.getText().toString().trim();

        // Sprawdzenie, czy wszystkie pola są uzupełnione
        if (TextUtils.isEmpty(accountNumber) || TextUtils.isEmpty(recipient) ||
                TextUtils.isEmpty(title) || TextUtils.isEmpty(amountStr)) {
            showToast("Wszystkie pola są wymagane");
            return false;
        }

        // Sprawdzenie, czy numer konta ma 26 cyfr
        if (accountNumber.length() != 26) {
            showToast("Numer konta powinien zawierać 26 cyfr");
            return false;
        }

        int amount = Integer.parseInt(amountStr);

        if(amount <= 0)
        {
            showToast("Transakcja musi zawierac dodatnia wartosc!");
            return false;
        }

        if(accounts == null || accounts.isEmpty())
        {
            showToast("Nie mozemy znalezc twojego konta w bazie!");
            return false;
        }

        if(amount > accounts.get(0).balance)
        {
            showToast("Za mało pieniędzy na koncie!");
            return false;
        }

        // Dodatkowe walidacje (np. sprawdzenie poprawności daty, kwoty)
        // ...

        return true;
    }

    // Metoda do wyświetlania komunikatu Toast
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    void setAccounts(List<Account> accounts)
    {
        this.accounts = accounts;
    }
}
