package pl.edu.pb.wi.bankpb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import pl.edu.pb.wi.bankpb.database.Model.Transaction;
import pl.edu.pb.wi.bankpb.viewModels.TransactionViewModel;

public class FragmentListHistory extends Fragment {

    private RecyclerView recyclerView;
    private HistoryListAdapter historyListAdapter;
    private TransactionViewModel transactionViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_history, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        historyListAdapter = new HistoryListAdapter(getActivity());
        recyclerView.setAdapter(historyListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        transactionViewModel.get().observe(getViewLifecycleOwner(), this::setHistoryListAdapter);
    }

    public void setHistoryListAdapter(List<Transaction> transactions)
    {
        historyListAdapter.setHistoryList(transactions);
    }
}
