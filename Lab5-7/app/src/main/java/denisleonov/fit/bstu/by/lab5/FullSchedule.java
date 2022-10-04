package denisleonov.fit.bstu.by.lab5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FullSchedule extends AppCompatActivity  {

    String[] Days = { "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота","Воскресенье"};
    private String day;

    String[] Weeks = { "Неделя 1","Неделя 2"};
    private String currentWeek;
    private int currentNumWeek;

    private List<Lesson> lessons;
    private ArrayAdapter<Lesson> adapter;
    private ListView fullSchedule;

    private Lesson lesson;

    private List<Lesson> todayLessons;

    long rowId;

    SQLiteDatabase db;
    DatabaseHelper sqlHelper;
    Cursor lessonCursor;
    SimpleCursorAdapter lessonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_schedule);

            Toast.makeText(this,"Screen switched to Portrait mode",Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_full_schedule);

            Spinner days = findViewById(R.id.fullDay);
            ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Days);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            days.setAdapter(adapter);

            Spinner week = findViewById(R.id.fullWeek);
            adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Weeks);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            week.setAdapter(adapter);

            AdapterView.OnItemSelectedListener itemSelectedListenerDay = new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // Получаем выбранный объект
                    day = (String)parent.getItemAtPosition(position);
                    ChangeLan();
                    loadList();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            };
            days.setOnItemSelectedListener(itemSelectedListenerDay);

            AdapterView.OnItemSelectedListener itemSelectedListenerWeek = new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // Получаем выбранный объект
                    currentWeek = (String)parent.getItemAtPosition(position);
                    if(currentWeek==Weeks[0]){
                        currentNumWeek=1;
                    }else{
                        currentNumWeek=2;
                    }
                    loadList();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            };
            week.setOnItemSelectedListener(itemSelectedListenerWeek);

            fullSchedule = findViewById(R.id.fullSchedule);
            fullSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                {
                    lesson = todayLessons.get(position); // по позиции получаем выбранный элемент
                    loadLessonInfo();
                }
            });

        Switch bdSwitch = findViewById(R.id.bdShowSwitch);
        bdSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                loadList();
            }
        });
    }



    private void loadLessonInfo(){
        try{
            Switch bdSwitch = findViewById(R.id.bdShowSwitch);
            Intent intent1 = new Intent(this, LessonInfo.class);
            if(bdSwitch.isChecked()){
                intent1.putExtra("isDB",true);
            }
            else{
                intent1.putExtra("isDB",false);
            }
            intent1.putExtra("lesson", lesson);
            startActivity(intent1);
        }
        catch (Exception e){
            Log.d("lab_04",e.getMessage());
        }
    }

    private void loadList(){
        Switch bdSwitch = findViewById(R.id.bdShowSwitch);

        try{
            todayLessons.clear();
            lessons.clear();
            adapter.notifyDataSetChanged();
        }
        catch (Exception e){

        }

        if(bdSwitch.isChecked()){
            sqlHelper = new DatabaseHelper(this);
            db = sqlHelper.getReadableDatabase();
            lessonCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE, null);

            while(lessonCursor.moveToNext()){
                String name = lessonCursor.getString(lessonCursor.getColumnIndexOrThrow("name"));
                String time = lessonCursor.getString(lessonCursor.getColumnIndexOrThrow("time"));
                String room = lessonCursor.getString(lessonCursor.getColumnIndexOrThrow("room"));
                String teach = lessonCursor.getString(lessonCursor.getColumnIndexOrThrow("teach"));
                String day = lessonCursor.getString(lessonCursor.getColumnIndexOrThrow("day"));
                int week = Integer.parseInt(lessonCursor.getString(lessonCursor.getColumnIndexOrThrow("week")));

                Lesson lesson = new Lesson(name,time,room,teach,day,week);
                lessons.add(lesson);
            }


        }
        else{
            todayLessons = new ArrayList<>();
            lessons = JSONHelper.importFromJSON(this);
            if(lessons == null){
                lessons = new ArrayList<>();
            }
        }

        for(Lesson lesson:lessons){
            if(lesson.Day.equals(day) && lesson.Week==currentNumWeek){
                todayLessons.add(lesson);
            }
        }
        ArrayAdapter<Lesson> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todayLessons);
        ListView listView = findViewById(R.id.fullSchedule);
        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
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
}