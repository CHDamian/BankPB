package pl.edu.pb.wi.bankpb.database.DaoInterface;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import pl.edu.pb.wi.bankpb.database.Model.Card;
import pl.edu.pb.wi.bankpb.database.Model.Transaction;

@Dao
public interface CardDao {
    @Insert
    void insertCard(Card card);

    @Update
    void updateCard(Card card);

    @Delete
    void deleteCard(Card card);

    @Query("SELECT * FROM cards")
    LiveData<List<Card>> getAllCards();
}
