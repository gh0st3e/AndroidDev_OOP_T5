package denisleonov.fit.bstu.by.lab5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LessonInfo extends AppCompatActivity {

    private int week;
    private Lesson Lesson;
    private List<Lesson> lessons;

    private boolean isDB;

    SQLiteDatabase db;
    DatabaseHelper sqlHelper;

    long rowId;

    String[] Days = { "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота","Воскресенье"};
    private String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_info);

        Bundle arguments = getIntent().getExtras();
        Lesson = (Lesson) arguments.get("lesson");
        isDB = (Boolean) arguments.get("isDB");
        week = Lesson.Week;
        loadInfo();

        Spinner days = findViewById(R.id.dayEdit);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                day = (String)parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        days.setOnItemSelectedListener(itemSelectedListener);
    }

    private void loadInfo(){
        EditText nameEdit = findViewById(R.id.nameEdit);
        EditText timeEdit = findViewById(R.id.timeEdit);
        EditText roomEdit = findViewById(R.id.roomEdit);
        EditText teachEdit = findViewById(R.id.teacherEdit);

        nameEdit.setText(Lesson.Name);
        timeEdit.setText(Lesson.Time);
        roomEdit.setText(Lesson.Room);
        teachEdit.setText(Lesson.Teacher);

            Spinner dayEdit = findViewById(R.id.dayEdit);
            ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Days);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dayEdit.setAdapter(adapter);

        ChangeLanRev();
        for(int i=0;i<Days.length;i++){
            if(day.equals(Days[i])){
                dayEdit.setSelection(i);
                dayEdit.setEnabled(false);
                return;
            }
        }

    }

    public void ChangeLanRev(){
        switch(Lesson.Day){
            case "Monday":{
                day = "Понедельник";
                break;
            }
            case "Tuesday":{
                day = "Вторник";
                break;
            }
            case "Wednesday":{
                day = "Среда";
                break;
            }
            case "Thursday":{
                day = "Четверг";
                break;
            }
            case "Friday":{
                day = "Пятница";
                break;
            }
            case "Saturday":{
                day = "Суббота";
                break;
            }
            case "Sunday": {
                day = "Воскресенье";
                break;
            }
        }
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

    public void onRadioButtonClicked(View view) {
        // если переключатель отмечен
        boolean checked = ((RadioButton) view).isChecked();
        // Получаем нажатый переключатель
        switch(view.getId()) {
            case R.id.week1Edit:
                if (checked){
                    week = 1;
                }
                break;
            case R.id.week2Edit:
                if (checked){
                    week = 2;
                }
                break;
            default:
                week = 1;
        }
    }

    public void delBtn(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Вы уверены что хотите удалить элемент?");
        alertDialog.setMessage("Восстановить элемент будет невозможно");

        alertDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                delItem();
                backToSchedule();

            }
        });

        alertDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    private void delItem(){
        if(isDB){
            try {
                sqlHelper = new DatabaseHelper(this);
                db = sqlHelper.getReadableDatabase();
                db.execSQL("DELETE FROM " + DatabaseHelper.TABLE + " WHERE name='" + Lesson.Name + "' AND day='" + Lesson.Day + "' AND week=" + Lesson.Week);
//                String queryParams = DatabaseHelper.COLUMN_NAME + " LIKE ? AND" ;
//                String[] selectParams = {Lesson.Name};
//                int deletedRows = db.delete(DatabaseHelper.TABLE, queryParams,
//                        selectParams);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        }
        else{
            lessons = JSONHelper.importFromJSON(this);
            if(lessons == null){
                lessons = new ArrayList<>();
            }
            for(Lesson lesson : lessons){
                if(lesson.equals(Lesson)){
                    lessons.remove(lesson);
                    break;
                }
            }
            JSONHelper.exportToJSON(this,lessons);
        }

    }

    private void backToSchedule(){
        Intent intent = new Intent(this, FullSchedule.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void editBtn(View view) {
        EditText nameEdit = findViewById(R.id.nameEdit);
        EditText timeEdit = findViewById(R.id.timeEdit);
        EditText roomEdit = findViewById(R.id.roomEdit);
        EditText teachEdit = findViewById(R.id.teacherEdit);
        Spinner dayEdit = findViewById(R.id.dayEdit);
        RadioButton r1 = findViewById(R.id.week1Edit);
        RadioButton r2 = findViewById(R.id.week2Edit);

        nameEdit.setEnabled(true);
        timeEdit.setEnabled(true);
        roomEdit.setEnabled(true);
        teachEdit.setEnabled(true);
        dayEdit.setEnabled(true);
        r1.setEnabled(true);
        r2.setEnabled(true);
        if(Lesson.Week==1){
            r1.setChecked(true);
        }else{
            r2.setChecked(true);
        }
    }

    public void saveBtn(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Вы уверены что хотите измениьб элемент?");
        alertDialog.setMessage("Восстановить элемент будет невозможно");

        alertDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                delItem();
                addEditItem();
                backToSchedule();

            }
        });

        alertDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    private void addEditItem(){
        EditText nameEdit = findViewById(R.id.nameEdit);
        EditText timeEdit = findViewById(R.id.timeEdit);
        EditText roomEdit = findViewById(R.id.roomEdit);
        EditText teachEdit = findViewById(R.id.teacherEdit);

        String name = nameEdit.getText().toString();
        String time = timeEdit.getText().toString();
        String room = roomEdit.getText().toString();
        String teach = teachEdit.getText().toString();
        ChangeLan();

        if(isDB){
            sqlHelper = new DatabaseHelper(this);
            db = sqlHelper.getReadableDatabase();
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


    }

    public void backBtn(View view) {
        Intent intent = new Intent(this, FullSchedule.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}