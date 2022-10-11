package denisleonov.fit.bstu.by.lab9_new;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class VisitCard {
    @PrimaryKey
    public long id;

    public String name;
    public String workPlace;
    public String phone;
    public String link;


    public String getWord(){
        return "Name: " + this.name + "\n"
            + "WorkPlace: " + this.workPlace +"\n"
                + "Phone: " + this.phone+"\n"
                    +"Link: " + this.link;
    }
}
