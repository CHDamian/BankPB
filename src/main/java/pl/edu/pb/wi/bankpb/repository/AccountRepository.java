package pl.edu.pb.wi.bankpb.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import pl.edu.pb.wi.bankpb.database.AppDatabase;
import pl.edu.pb.wi.bankpb.database.DaoInterface.AccountDao;
import pl.edu.pb.wi.bankpb.database.DatabaseInitializer;
import pl.edu.pb.wi.bankpb.database.Model.Account;

public class AccountRepository {
    private final AccountDao accountDao;
    private final LiveData<List<Account>> accounts;

    public AccountRepository(Application application)
    {
        AppDatabase database = AppDatabase.getInstance(application);
        accountDao = database.accountDao();
        accounts = accountDao.getAllAccounts();
    }

    public LiveData<List<Account>> get()
    {
        return accounts;
    }

    public void insert(Account account)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> accountDao.insertAccount(account));
    }

    public void update(Account account)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> accountDao.updateAccount(account));
    }

    public void delete(Account account)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> accountDao.deleteAccount(account));
    }

}
