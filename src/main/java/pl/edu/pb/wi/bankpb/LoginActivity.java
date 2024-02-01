package pl.edu.pb.wi.bankpb;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import pl.edu.pb.wi.bankpb.database.Model.Account;
import pl.edu.pb.wi.bankpb.viewModels.AccountViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextPin;

    private AccountViewModel accountViewModel;

    private List<Account> accounts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.get().observe(this, this::setAccounts);

        // Restore saved state if available
        if (savedInstanceState != null) {
            editTextPin.setText(savedInstanceState.getString("pin"));
        }

        // Set up UI elements
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        editTextPin = findViewById(R.id.editTextPin);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        // Set up login button click listener
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validate PIN
                String pin = editTextPin.getText().toString();
                if (TextUtils.isEmpty(pin) || pin.length() != 4) {
                    showToast("PIN must be a 4-digit number");
                    return;
                }

                if(!validatePIN(pin))
                {
                    showToast("PIN is incorrect");
                    return;
                }

                // Navigate to CockpitActivity
                startActivity(new Intent(LoginActivity.this, CockpitActivity.class));

                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save necessary data for state restoration
        outState.putString("pin", editTextPin.getText().toString());
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean validatePIN(String pin)
    {
        if(accounts == null)return false;
        Account account = accounts.get(0);
        return account.pin.equals(pin);
    }

    void setAccounts(List<Account> accounts)
    {
        this.accounts = accounts;
    }
}
