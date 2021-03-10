package pabmocpl.dadm.labs.myquote.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import pabmocpl.dadm.labs.myquote.objects.Quotation;

@Database(version = 1, entities = {Quotation.class})
public abstract class QuotationRoomDatabase extends RoomDatabase {

    private static QuotationRoomDatabase instance;

    public static synchronized QuotationRoomDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context, QuotationRoomDatabase.class, "quotation_database")
                    .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        if (instance != null && instance.isOpen()) {
            instance.close();
            instance = null;
        }
    }

    public abstract QuotationRoomDAO quotationDAO();

}
