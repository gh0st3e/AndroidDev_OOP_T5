package denisleonov.fit.bstu.by.lab9_new;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class VisitCardViewModel extends AndroidViewModel {
    private VisitCardRepository vcRepository;

    private final LiveData<List<VisitCard>> VisitCards;

    public VisitCardViewModel (Application application) {
        super(application);
        vcRepository = new VisitCardRepository(application);
        VisitCards = vcRepository.getAllCards();
    }

    LiveData<List<VisitCard>> getAllWords() { return VisitCards; }

    public void insert(VisitCard vc) { vcRepository.insert(vc); }
}
