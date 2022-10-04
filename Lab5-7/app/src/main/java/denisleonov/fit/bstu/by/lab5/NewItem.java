package denisleonov.fit.bstu.by.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewItem extends AppCompatActivity {

    String[] Days = { "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота","Воскресенье"};
    private String day;
    private int week;
    private List<Lesson> lessons;

    long rowId;

    SQLiteDatabase db;
    DatabaseHelper sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        Spinner days = findViewById(R.id.day);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Days);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        days.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                day = (String)parent.getItemAtPosition(position);
                ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        days.setOnItemSelectedListener(itemSelectedListener);



    }

    public void ChangeLan(){
        switch(day){
            case "Понедельник":{
                day = "Monday";
                break;
            }
            case "Вторник":{
                day = "Tuesday";
                break;
            }
            case "Среда":{
                day = "Wednesday";
                break;
            }
            case "Четверг":{
                day = "Thursday";
                break;
            }
            case "Пятница":{
                day = "Friday";
                break;
            }
            case "Суббота":{
                day = "Saturday";
                break;
            }
            case "Воскресенье": {
                day = "Sunday";
                break;
            }
        }
    }

    public void saveBtn(View view) {
        EditText edit_name = findViewById(R.id.name);
        EditText edit_time = findViewById(R.id.time);
        EditText edit_room = findViewById(R.id.room);
        EditText edit_teach = findViewById(R.id.teacher);

        String name = edit_name.getText().toString();
        if(name.length()<2){
            Toast.makeText(this, "Недостаточная длина названия", Toast.LENGTH_SHORT).show();
            edit_name.setText("");
            return;
        }
        String time = edit_time.getText().toString();
        if(time.length()<1){
            Toast.makeText(this, "Недостаточная длина времени", Toast.LENGTH_SHORT).show();
            edit_time.setText("");
            return;
        }
        String room = edit_room.getText().toString();
        if(room.length()<1){
            Toast.makeText(this, "Недостаточная длина номера аудитории", Toast.LENGTH_SHORT).show();
            edit_room.setText("");
            return;
        }
        String teach = edit_teach.getText().toString();
        if(teach.length()<2){
            Toast.makeText(this, "Недостаточная длина имени преподователя", Toast.LENGTH_SHORT).show();
            edit_teach.setText("");
            return;
        }

        ChangeLan();


        Switch bdSwitch = findViewById(R.id.bdSwitch);
        if(bdSwitch.isChecked()){
            ContentValues values = new ContentValues();

            values.put("name",name);
            values.put("time",time);
            values.put("room",room);
            values.put("teach",teach);
            values.put("day",day);
            values.put("week",week);

            sqlHelper = new DatabaseHelper(this);
            db = sqlHelper.getWritableDatabase();
            rowId = db.insert(DatabaseHelper.TABLE, null, values);


        }
        else{
            Lesson lesson = new Lesson(name,time,room,teach,day,week);
            lessons = JSONHelper.importFromJSON(this);
            if(lessons == null){
                lessons = new ArrayList<>();
            }
            lessons.add(lesson);
            JSONHelper.exportToJSON(this,lessons);


        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);

    }

    public void onRadioButtonClicked(View view) {
        // если переключатель отмечен
        boolean checked = ((RadioButton) view).isChecked();
        // Получаем нажатый переключатель
        switch(view.getId()) {
            case R.id.week1:
                if (checked){
                    week = 1;
                }
                break;
            case R.id.week2:
                if (checked){
                    week = 2;
                }
                break;
            default:
                week = 1;
        }
    }

    public void delBtn(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}