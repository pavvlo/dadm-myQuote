package pabmocpl.dadm.labs.myquote.databases;

import android.provider.BaseColumns;

public class QuotationContract {

    private QuotationContract(){}

    public static class QuotationBaseColumns implements BaseColumns{
        //Tambien heredan _ID y _COUNT
        static final String TABLE_NAME = "quotation_table";
        static final String COLUMN_AUTHOR = "author";
        static final String COLUMN_QUOTATION = "quotation";
    }
}
