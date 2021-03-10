package pabmocpl.dadm.labs.myquote.threads;

import java.lang.ref.WeakReference;
import java.util.List;

import pabmocpl.dadm.labs.myquote.activities.FavouriteActivity;
import pabmocpl.dadm.labs.myquote.databases.QuotationOpenHelper;
import pabmocpl.dadm.labs.myquote.databases.QuotationRoomDatabase;
import pabmocpl.dadm.labs.myquote.objects.Quotation;

public class FavouriteActivityThread extends Thread {

    private WeakReference<FavouriteActivity> reference;
    private int databaseAccess;

    public FavouriteActivityThread(FavouriteActivity favouriteActivity, int databaseAccess) {
        this.reference = new WeakReference<>(favouriteActivity);
        this.databaseAccess = databaseAccess;
    }

    @Override
    public void run() {
        FavouriteActivity favouriteActivity = reference.get();
        List<Quotation> quotationList;
        if (databaseAccess == 0) {
            quotationList = QuotationOpenHelper
                    .getInstance(favouriteActivity)
                    .getAllQuotations();
        } else {
            quotationList = QuotationRoomDatabase
                    .getInstance(favouriteActivity)
                    .quotationDAO()
                    .getAllQuotations();
        }
        if (reference.get() != null)
            reference.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    reference.get().addQuotationList(quotationList);
                }
            });
    }
}
