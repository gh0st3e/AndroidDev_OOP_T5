package denisleonov.fit.bstu.by.lab5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LessonsList.OnFragmentSendDataListener {

    public static final int IDM_OPEN = 101;
    public static final int IDM_SAVE = 102;

    String[] Weeks = {"Неделя 1","Неделя 2"};
    private String currentWeek;
    private int currentNumWeek;
    private List<Lesson> lessons;
    private ArrayAdapter<Lesson> adapter;
    private String dayOfTheWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Student Helper");

        if(getResources().getDisplayMetrics().widthPixels>getResources().getDisplayMetrics().
                heightPixels)
        {
            Toast.makeText(this,"Screen switched to Landscape mode",Toast.LENGTH_SHORT).show();
            setContentView(R.layout.fch_land);

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            Date d = new Date();
            dayOfTheWeek = sdf.format(d);

            currentNumWeek = 1;

            loadFragments();
        }
        else
        {
            Spinner week = findViewById(R.id.chooseWeek);
            ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Weeks);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            week.setAdapter(adapter);

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            Date d = new Date();
            dayOfTheWeek = sdf.format(d);
            TextView CurrentDay = findViewById(R.id.CurrentDay);
            CurrentDay.setText(dayOfTheWeek);

            currentNumWeek = 1;

            LoadShedule();



            AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // Получаем выбранный объект
                    currentWeek = (String)parent.getItemAtPosition(position);

                    if(currentWeek==Weeks[0]){
                        currentNumWeek=1;
                    }else{
                        currentNumWeek=2;
                    }
                    LoadShedule();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            };
            week.setOnItemSelectedListener(itemSelectedListener);
        }




    }

    private void loadFragments(){
        Fragment LessonList = new LessonsList();
        Fragment LessonDirection = new LessonDirection();

        Bundle bundle = new Bundle();
        bundle.putString("day", dayOfTheWeek);
        bundle.putInt("week",currentNumWeek);
        LessonList.setArguments(bundle);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.lessonList, LessonList);
        ft.add(R.id.lessonDirection, LessonDirection);
        ft.commit();
    }

    @Override
    public void onSendData(Lesson lesson) {
        LessonDirection fragment = (LessonDirection) getSupportFragmentManager()
                .findFragmentById(R.id.lessonDirection);
        if (fragment != null)
            fragment.setSelectedItem(lesson);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(Menu.NONE, IDM_OPEN, Menu.NONE, "Открыть");
        menu.add(Menu.NONE, IDM_SAVE, Menu.NONE, "Сохранить");
    }



    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        CharSequence message;
        switch (item.getItemId())
        {
            case IDM_OPEN:
                message = "Выбран пункт Открыть";
                break;
            case IDM_SAVE:
                message = "Выбран пункт Сохранить";
                break;
            default:
                return super.onContextItemSelected(item);
        }
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.addItem:
                Intent CreateItem = new Intent(this, NewItem.class);
                startActivity(CreateItem);
                return true;
            case R.id.Schedule:
                Intent FullSchedule = new Intent(this, FullSchedule.class);
                startActivity(FullSchedule);
                return true;
            case R.id.Up:
                FullSchedule = new Intent(this, FullSchedule.class);
                startActivity(FullSchedule);
                return true;
        }
        //headerView.setText(item.getTitle());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

    public void LoadShedule(){

        List<Lesson> todayLessons = new ArrayList<>();


        lessons = JSONHelper.importFromJSON(this);
        if(lessons == null){
            lessons = new ArrayList<>();
        }



        for(Lesson lesson:lessons){
            if(lesson.Day.equals(dayOfTheWeek) && lesson.Week==currentNumWeek){
                todayLessons.add(lesson);
            }
        }
        ArrayAdapter<Lesson> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todayLessons);
        ListView listView = findViewById(R.id.Shedule);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

}