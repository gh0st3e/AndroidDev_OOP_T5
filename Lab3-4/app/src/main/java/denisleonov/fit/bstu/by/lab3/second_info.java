package denisleonov.fit.bstu.by.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class second_info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getResources().getDisplayMetrics().widthPixels>getResources().getDisplayMetrics().
                heightPixels)
        {
            Toast.makeText(this,"Screen switched to Landscape mode",Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_second_info_land);
        }
        else
        {
            Toast.makeText(this,"Screen switched to Portrait mode",Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_second_info);
        }
    }

    public void backToFirstBtn(View view) {
            this.finish();
    }

    public void nextToThird(View view) {

        EditText edit_email = findViewById(R.id.mail);
        EditText edit_phone = findViewById(R.id.phone);
        EditText edit_link = findViewById(R.id.link);

        String email = edit_email.getText().toString();
        String phone = edit_phone.getText().toString();
        String link = edit_link.getText().toString();

        String name = getIntent().getStringExtra("name");
        String surname = getIntent().getStringExtra("surname");
        String img = getIntent().getStringExtra("img");

        Intent intent = new Intent(this,third_info.class);
        intent.putExtra("name",name);
        intent.putExtra("surname",surname);
        intent.putExtra("email",email);
        intent.putExtra("phone",phone);
        intent.putExtra("link",link);
        intent.putExtra("img",img);

        startActivity(intent);
    }

    public void finishAct(){
        this.finish();
    }
}