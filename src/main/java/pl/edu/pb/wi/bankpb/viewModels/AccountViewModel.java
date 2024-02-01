package pl.edu.pb.wi.bankpb.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import pl.edu.pb.wi.bankpb.database.Model.Account;
import pl.edu.pb.wi.bankpb.repository.AccountRepository;

public class AccountViewModel extends AndroidViewModel {

    private final AccountRepository accountRepository;
    private final LiveData<List<Account>> accounts;

    public AccountViewModel(@NonNull Application application)
    {
        super(application);
        accountRepository = new AccountRepository(application);
        accounts = accountRepository.get();
    }

    public LiveData<List<Account>> get()
    {
        return accounts;
    }

    public void insert(Account account)
    {
        accountRepository.insert(account);
    }

    public void update(Account account)
    {
        accountRepository.update(account);
    }

    public void delete(Account account)
    {
        accountRepository.delete(account);
    }

}
