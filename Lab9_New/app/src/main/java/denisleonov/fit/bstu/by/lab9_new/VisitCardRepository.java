package denisleonov.fit.bstu.by.lab9_new;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class VisitCardRepository {
    private vcDao visitCardDao;
    private LiveData<List<VisitCard>> VisitCards;

    VisitCardRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        visitCardDao = db.visitCardDao();
        VisitCards = visitCardDao.getAll();
    }

    LiveData<List<VisitCard>> getAllCards() {
        return VisitCards;
    }

    void insert(VisitCard vc) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            visitCardDao.insert(vc);
        });
    }
}
