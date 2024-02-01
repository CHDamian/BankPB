package pl.edu.pb.wi.bankpb.database.DaoInterface;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import pl.edu.pb.wi.bankpb.database.Model.Transaction;

@Dao
public interface TransactionDao {
    @Insert
    void insertTransaction(Transaction transaction);

    @Update
    void updateTransaction(Transaction transaction);

    @Delete
    void deleteTransaction(Transaction transaction);

    @Query("SELECT * FROM transactions")
    LiveData<List<Transaction>> getAllTransactions();

    @Query("DELETE FROM transactions")
    void nukeTable();
}
