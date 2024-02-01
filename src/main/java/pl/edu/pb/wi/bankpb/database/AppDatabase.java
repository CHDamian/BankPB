package pl.edu.pb.wi.bankpb.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pl.edu.pb.wi.bankpb.database.DaoInterface.AccountDao;
import pl.edu.pb.wi.bankpb.database.DaoInterface.CardDao;
import pl.edu.pb.wi.bankpb.database.DaoInterface.TransactionDao;
import pl.edu.pb.wi.bankpb.database.Model.Account;
import pl.edu.pb.wi.bankpb.database.Model.Card;
import pl.edu.pb.wi.bankpb.database.Model.Transaction;

@Database(entities = {Account.class, Card.class, Transaction.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AccountDao accountDao();
    public abstract CardDao cardDao();
    public abstract TransactionDao transactionDao();

    private static AppDatabase databaseInstance;
    public static final ExecutorService databaseWriteExecutor = Executors.newSingleThreadExecutor();

    public static AppDatabase getInstance(final Context context)
    {
        if(databaseInstance == null)
        {
            databaseInstance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "app_database"
            ).build();
        }
        return databaseInstance;
    }

}
