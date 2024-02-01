package pl.edu.pb.wi.bankpb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import pl.edu.pb.wi.bankpb.database.Model.Account;
import pl.edu.pb.wi.bankpb.database.Model.Transaction;
import pl.edu.pb.wi.bankpb.viewModels.AccountViewModel;
import pl.edu.pb.wi.bankpb.viewModels.TransactionViewModel;

public class DownloadActivity extends AppCompatActivity {

    private Button downloadButton;
    private TextView noteTextView;

    private String message;

    private AccountViewModel accountViewModel;
    private List<Account> accounts;

    private TransactionViewModel transactionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        downloadButton = findViewById(R.id.downloadButton);
        noteTextView = findViewById(R.id.noteTextView);
        noteTextView.setVisibility(View.INVISIBLE);

        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.get().observe(this, this::setAccounts);

        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Rozpocznij skanowanie kodu QR
                new IntentIntegrator(DownloadActivity.this).initiateScan();
            }
        });
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save variables to the bundle
        if(message != null) {
            outState.putString("mess", message);
            outState.putBoolean("visible", true);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState.containsKey("visible"))
        {
            String mess = savedInstanceState.getString("mess");
            showNote(mess);
        }
    }


    // Metoda wywoływana po zeskanowaniu kodu QR
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                // Kod QR został zeskanowany
                String qrCode = result.getContents();

                try {

                    JSONObject json = new JSONObject(qrCode.toString());
                    int amount = json.getInt("Amount");
                    String name = json.getString("Person");
                    String num = json.getString("Number");
                    String title = json.getString("Title");
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String currentDate = dateFormat.format(calendar.getTime());

                    Transaction transaction = new Transaction();

                    transaction.receiverNumber = num;
                    transaction.receiverName = name;
                    transaction.title = title;
                    transaction.totalSum = amount;
                    transaction.transactionDate = currentDate;

                    if(accounts == null)
                    {
                        showNote("Nie mozna pobrac konta.");
                    }
                    Account account = accounts.get(0);
                    account.balance += amount;
                    accountViewModel.update(account);

                    transactionViewModel.insert(transaction);

                    showNote("Pobrano " + amount + " PLN");

                }catch (Exception e)
                {
                    showNote("JSON sie popsul");
                }

            }
        }
    }

    private void showNote(String note) {
        noteTextView.setText(note);
        noteTextView.setVisibility(View.VISIBLE);
        message = note;
    }

    void setAccounts(List<Account> accounts)
    {
        this.accounts = accounts;
    }
}
