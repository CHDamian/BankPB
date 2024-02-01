package pl.edu.pb.wi.bankpb.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import pl.edu.pb.wi.bankpb.database.Model.Card;
import pl.edu.pb.wi.bankpb.repository.CardRepository;

public class CardViewModel extends AndroidViewModel {

    private final CardRepository cardRepository;
    private final LiveData<List<Card>> cards;

    public CardViewModel(@NonNull Application application)
    {
        super(application);
        cardRepository = new CardRepository(application);
        cards = cardRepository.get();
    }

    public LiveData<List<Card>> get()
    {
        return cards;
    }

    public void insert(Card card)
    {
        cardRepository.insert(card);
    }

    public void update(Card card)
    {
        cardRepository.update(card);
    }

    public void delete(Card card)
    {
        cardRepository.delete(card);
    }
}