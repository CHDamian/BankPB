package pl.edu.pb.wi.bankpb.database.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "accounts")
public class Account {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String surname;
    public String pin;
    public String accountNumber;
    public int balance;
}
