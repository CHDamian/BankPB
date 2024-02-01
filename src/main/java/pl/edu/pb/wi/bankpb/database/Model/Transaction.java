package pl.edu.pb.wi.bankpb.database.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String receiverNumber;
    public String receiverName;
    public String title;
    public int totalSum;
    public String transactionDate;
}
