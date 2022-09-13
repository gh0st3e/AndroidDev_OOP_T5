package denisleonov.fit.bstu.by.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class third_info extends AppCompatActivity {

    private String choosedItem;
    private List<User> Users;
    private ArrayAdapter<User> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getResources().getDisplayMetrics().widthPixels>getResources().getDisplayMetrics().
                heightPixels)
        {
            Toast.makeText(this,"Screen switched to Landscape mode",Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_third_info_land);
        }
        else
        {
            Toast.makeText(this,"Screen switched to Portrait mode",Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_third_info);
        }
    }


    public void onRadioButtonClicked(View view) {
        // если переключатель отмечен
        boolean checked = ((RadioButton) view).isChecked();
        TextView selection = findViewById(R.id.choose);
        // Получаем нажатый переключатель
        switch(view.getId()) {
            case R.id.work:
                if (checked){
                    selection.setText("Работа");
                    choosedItem = "Работа";
                }
                break;
            case R.id.study:
                if (checked){
                    selection.setText("Стажировка");
                    choosedItem = "Стажировка";
                }
                break;
            case R.id.cowork:
                if (checked){
                    selection.setText("Сотрудничество");
                    choosedItem = "Сотрудничество";
                }
                break;
        }
    }

    public void saveBtn(View view) {
        DatePicker birth_dp = findViewById(R.id.birth);

        String birthDate = birth_dp.getDayOfMonth() + "." + birth_dp.getMonth() + "." + birth_dp.getYear();
        String name = getIntent().getStringExtra("name");
        String surname = getIntent().getStringExtra("surname");
        String email = getIntent().getStringExtra("email");
        String phone = getIntent().getStringExtra("phone");
        String link = getIntent().getStringExtra("link");
        String img = getIntent().getStringExtra("img");

        User user = new User(surname,name,email,phone,link, choosedItem,birthDate,img);

        Users = JSONHelper.importFromJSON(this);
        if(Users==null){
            Users = new ArrayList<>();
        }
        Users.add(user);

        SaveJSON();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        ComponentName cn = intent.getComponent();
        Intent.makeRestartActivityTask(cn);
        startActivity(intent);
    }

    public void backToSecondBtn(View view) {
        this.finish();
    }

    private void SaveJSON(){
        boolean result = JSONHelper.exportToJSON(this, Users);
        if(result){
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
        }
    }
}