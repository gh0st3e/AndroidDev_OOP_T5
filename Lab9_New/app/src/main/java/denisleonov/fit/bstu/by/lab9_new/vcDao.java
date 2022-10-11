package denisleonov.fit.bstu.by.lab9_new;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface vcDao {
    @Query("SELECT * FROM visitcard")
    LiveData<List<VisitCard>> getAll();
    @Query("SELECT * FROM visitcard WHERE id = :id")
    VisitCard getById(long id);
    @Query("DELETE FROM visitcard")
    void deleteAll();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(VisitCard visitCard);
    @Update
    void update(VisitCard visitCard);
    @Delete
    void delete(VisitCard visitCard);

}
