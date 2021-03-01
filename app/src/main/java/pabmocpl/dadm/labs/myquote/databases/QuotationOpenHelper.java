package pabmocpl.dadm.labs.myquote.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import pabmocpl.dadm.labs.myquote.objects.Quotation;
import pabmocpl.dadm.labs.myquote.databases.QuotationContract.QuotationBaseColumns;

public class QuotationOpenHelper extends SQLiteOpenHelper {

    private static QuotationOpenHelper instance;

    private QuotationOpenHelper(Context context){
        super(context, "quotation_database", null, 1);
    }

    public static synchronized QuotationOpenHelper getInstance(Context context){
        if(instance == null){
            instance = new QuotationOpenHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                String.format("CREATE TABLE %s (" +
                                "%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                "%s TEXT NOT NULL, " +
                                "%s TEXT);",
                        QuotationBaseColumns.TABLE_NAME,
                        QuotationBaseColumns._ID,
                        QuotationBaseColumns.COLUMN_QUOTATION,
                        QuotationBaseColumns.COLUMN_AUTHOR));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Quotation> getAllQuotations(){
        List<Quotation> quotationList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                QuotationBaseColumns.TABLE_NAME,
                new String[]{QuotationBaseColumns.COLUMN_QUOTATION, QuotationBaseColumns.COLUMN_AUTHOR},
                null, null, null, null, null);

        while(cursor.moveToNext()){
            quotationList.add(new Quotation(cursor.getString(0),cursor.getString(1)));
        }

        cursor.close();
        db.close();
        return quotationList;
    }

    public boolean hasQuotation(Quotation quotation){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                QuotationBaseColumns.TABLE_NAME,
                null,
                QuotationBaseColumns.COLUMN_QUOTATION+"=?",
                new String[]{quotation.getQuoteText()},
                null, null, null);
        boolean hasQuotation = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return hasQuotation;
    }

    public void addQuotation(Quotation quotation) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuotationBaseColumns.COLUMN_QUOTATION, quotation.getQuoteText());
        contentValues.put(QuotationBaseColumns.COLUMN_AUTHOR, quotation.getQuoteAuthor());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(QuotationBaseColumns.TABLE_NAME, null, contentValues);
        db.close();
    }

    public void deleteAllQuotations(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(QuotationBaseColumns.TABLE_NAME, null, null);
        db.close();
    }

    public void deleteQuotation(Quotation quotation){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(QuotationBaseColumns.TABLE_NAME,
                QuotationBaseColumns.COLUMN_QUOTATION+"=?",
                new String[]{quotation.getQuoteText()});
        db.close();
    }
}
