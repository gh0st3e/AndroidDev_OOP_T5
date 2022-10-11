package denisleonov.fit.bstu.by.lab9_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewVisitCardActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_visit_card);



    }

    public void saveBtn(View view) {
        EditText eId = findViewById(R.id.newId);
        EditText eName = findViewById(R.id.newName);
        EditText eWorkPlace = findViewById(R.id.newWorkPlace);
        EditText ePhone = findViewById(R.id.newPhone);
        EditText eLink = findViewById(R.id.newLink);

        VisitCard vc = new VisitCard();
        vc.id = Long.parseLong(eId.getText().toString());
        vc.name = eName.getText().toString();
        vc.workPlace = eWorkPlace.getText().toString();
        vc.phone = ePhone.getText().toString();
        vc.link = eLink.getText().toString();

        AppDatabase db = AppDatabase.getDatabase(this);
        vcDao vccDao = db.visitCardDao();

        vccDao.insert(vc);

        this.finish();
    }
}