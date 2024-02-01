package pl.edu.pb.wi.bankpb.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import pl.edu.pb.wi.bankpb.database.AppDatabase;
import pl.edu.pb.wi.bankpb.database.DaoInterface.CardDao;
import pl.edu.pb.wi.bankpb.database.DatabaseInitializer;
import pl.edu.pb.wi.bankpb.database.Model.Account;
import pl.edu.pb.wi.bankpb.database.Model.Card;

public class CardRepository {
    private final CardDao cardDao;
    private final LiveData<List<Card>> cards;

    public CardRepository(Application application)
    {
        AppDatabase database = AppDatabase.getInstance(application);
        cardDao = database.cardDao();
        cards = cardDao.getAllCards();
    }

    public LiveData<List<Card>> get()
    {
        return cards;
    }

    public void insert(Card card)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> cardDao.insertCard(card));
    }

    public void update(Card card)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> cardDao.updateCard(card));
    }

    public void delete(Card card)
    {
        AppDatabase.databaseWriteExecutor.execute(() -> cardDao.deleteCard(card));
    }
}
