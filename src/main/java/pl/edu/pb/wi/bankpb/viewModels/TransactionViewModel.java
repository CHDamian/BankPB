package pl.edu.pb.wi.bankpb.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import pl.edu.pb.wi.bankpb.database.Model.Transaction;
import pl.edu.pb.wi.bankpb.repository.TransactionRepository;

public class TransactionViewModel extends AndroidViewModel {

    private final TransactionRepository transactionRepository;
    private final LiveData<List<Transaction>> transactions;

    public TransactionViewModel(@NonNull Application application)
    {
        super(application);
        transactionRepository = new TransactionRepository(application);
        transactions = transactionRepository.get();
    }

    public LiveData<List<Transaction>> get()
    {
        return transactions;
    }

    public void insert(Transaction transaction)
    {
        transactionRepository.insert(transaction);
    }

    public void update(Transaction transaction)
    {
        transactionRepository.update(transaction);
    }

    public void delete(Transaction transaction)
    {
        transactionRepository.delete(transaction);
    }

    public void nuke(){transactionRepository.nuke();}
}
