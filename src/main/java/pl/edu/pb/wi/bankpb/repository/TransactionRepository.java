package pl.edu.pb.wi.bankpb.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import pl.edu.pb.wi.bankpb.database.AppDatabase;
import pl.edu.pb.wi.bankpb.database.DaoInterface.TransactionDao;
import pl.edu.pb.wi.bankpb.database.DatabaseInitializer;
import pl.edu.pb.wi.bankpb.database.Model.Card;
import pl.edu.pb.wi.bankpb.database.Model.Transaction;

public class TransactionRepository {
    private final TransactionDao transactionDao;

    private final LiveData<List<Transaction>> transactions;

    public TransactionRepository(Application application)
    {
        AppDatabase database = AppDatabase.getInstance(application);
        transactionDao = database.transactionDao();
        transactions = transactionDao.getAllTransactions();
    }

    public LiveData<List<Transaction>> get()
    {
        return transactions;
    }

    public void insert(Transaction transaction)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> transactionDao.insertTransaction(transaction));
    }

    public void update(Transaction transaction)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> transactionDao.updateTransaction(transaction));
    }

    public void delete(Transaction transaction)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> transactionDao.deleteTransaction(transaction));
    }

    public void nuke()
    {
        AppDatabase.databaseWriteExecutor.execute(() -> transactionDao.nukeTable());
    }
}
