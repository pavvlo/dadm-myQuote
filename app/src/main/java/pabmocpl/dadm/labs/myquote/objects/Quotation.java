package pabmocpl.dadm.labs.myquote.objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "quotation_table")
public class Quotation {

    @ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "quotation")
    private String quoteText;

    @ColumnInfo(name = "author")
    private String quoteAuthor;

    public Quotation() {
        this.quoteText = "";
        this.quoteAuthor = "";
    }

    public Quotation(@NonNull String quoteText, String quoteAuthor) {
        this.quoteText = quoteText;
        this.quoteAuthor = quoteAuthor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteText(@NonNull String quoteText) {
        this.quoteText = quoteText;
    }

    public String getQuoteAuthor() {
        return quoteAuthor;
    }

    public void setQuoteAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }
}

