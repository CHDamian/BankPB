package pl.edu.pb.wi.bankpb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import pl.edu.pb.wi.bankpb.database.Model.Transaction;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryViewHolder> {

    private final LayoutInflater inflater;
    private List<Transaction> historyList;

    public HistoryListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.history_list_item, parent, false);
        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        if (historyList != null) {
            Transaction current = historyList.get(position);
            holder.bind(current);
        }
    }

    void setHistoryList(List<Transaction> historyList) {
        this.historyList = historyList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (historyList != null)
            return historyList.size();
        else return 0;
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView typeTextView;
        private final TextView titleTextView;
        private final TextView amountTextView;

        private HistoryViewHolder(View itemView) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
        }

        private void bind(Transaction transactions) {
            typeTextView.setText((transactions.totalSum > 0)? "Odbior" : "Przelew");
            titleTextView.setText(transactions.title);
            Integer amount = transactions.totalSum;
            String amountStr = amount.toString();
            amountTextView.setText(amountStr);
        }
    }
}
