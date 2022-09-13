package denisleonov.fit.bstu.by.lab3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private User user;
    private List<User> users;
    private ArrayAdapter<User> adapter;
    private ListView listView;
    private String img;

    static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getDisplayMetrics().widthPixels>getResources().getDisplayMetrics().
                heightPixels)
        {
            Toast.makeText(this,"Screen switched to Landscape mode",Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_main_land);
        }
        else {
            Toast.makeText(this, "Screen switched to Portrait mode", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_main);
        }

        users = JSONHelper.importFromJSON(this);
        if(users==null){
            users = new ArrayList<>();
        }
        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                user = users.get(position); // по позиции получаем выбранный элемент
                loadUserInfo();
            }
        });
    }

    public void loadUserInfo(){
        try{
            Intent intent1 = new Intent(this, UserInfo.class);
            intent1.putExtra("user",  user);
            startActivity(intent1);
        }
        catch (Exception e){
            Log.d("lab_04",e.getMessage());
        }
    }

    public void firstInfoBtn(View view) {
        EditText edit_name = findViewById(R.id.name);
        EditText edit_surname = findViewById(R.id.surname);

        String name = edit_name.getText().toString();
        String surname = edit_surname.getText().toString();

        Intent intent = new Intent(this,second_info.class);
        intent.putExtra("name",name);
        intent.putExtra("surname",surname);
        intent.putExtra("img",img);

        startActivity(intent);
    }

    public void newItemBtn(View view) {
        if(getResources().getDisplayMetrics().widthPixels>getResources().getDisplayMetrics().
                heightPixels)
        {
            Toast.makeText(this,"Screen switched to Landscape mode",Toast.LENGTH_SHORT).show();
            setContentView(R.layout.first_info_land);
        }
        else
        {
            Toast.makeText(this,"Screen switched to Portrait mode",Toast.LENGTH_SHORT).show();
            setContentView(R.layout.first_info);
        }
    }

    public void loadImgBtn(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Bitmap bitmap = null;
        ImageView imageView = (ImageView) findViewById(R.id.avatar);

        switch(requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    img = selectedImage.toString();
                    Toast.makeText(this, img, Toast.LENGTH_SHORT).show();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageView.setImageBitmap(bitmap);
                }
        }
    }
}