package pl.edu.pb.wi.bankpb.database.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "cards")
public class Card {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String cardDistribution;
    public String cardNumber;
    public String expire;
    public String codeCVV;
}
