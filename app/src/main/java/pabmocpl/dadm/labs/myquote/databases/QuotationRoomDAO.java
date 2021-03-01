package pabmocpl.dadm.labs.myquote.databases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import pabmocpl.dadm.labs.myquote.objects.Quotation;

@Dao
public interface QuotationRoomDAO {

    @Insert
    void addQuotation(Quotation quotation);

    @Delete
    void deleteQuotation(Quotation quotation);

    @Query("SELECT * FROM quotation_table")
    List<Quotation> getAllQuotations();

    @Query("SELECT * FROM quotation_table WHERE quotation = :quoteText")
    Quotation getQuotation(String quoteText);

    @Query("DELETE FROM quotation_table")
    void deleteAllQuotations();

}
