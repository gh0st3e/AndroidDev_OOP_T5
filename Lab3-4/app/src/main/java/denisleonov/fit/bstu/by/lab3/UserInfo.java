package denisleonov.fit.bstu.by.lab3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class UserInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        Bundle arguments = getIntent().getExtras();
        User user = (User) arguments.get("user");

        SetInfo(user);
    }

    private void SetInfo(User user){
        TextView uSurname = findViewById(R.id.uSurname);
        TextView uName = findViewById(R.id.uName);
        TextView uEmail = findViewById(R.id.uEmail);
        TextView uPhone = findViewById(R.id.uPhone);
        TextView uLink = findViewById(R.id.uLink);
        TextView uDirection = findViewById(R.id.uDirection);
        TextView uBirth = findViewById(R.id.uBirth);
        ImageView uImg = findViewById(R.id.uImg);

        uSurname.setText(user.Surname);
        uName.setText(user.Name);
        uEmail.setText(user.Email);
        uPhone.setText(user.Phone);
        uDirection.setText(user.Direction);
        uBirth.setText(user.Date);
        uLink.setText(user.Link);

        Bitmap bitmap = null;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(user.Img));
            uImg.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.d("lab_04",e.getMessage());
        }
    }

    public void callBtn(View view) {
        TextView uPhone = findViewById(R.id.uPhone);
        String phoneNo = uPhone.getText().toString();
        if (!TextUtils.isEmpty(phoneNo)) {
            String dial = "tel:" + phoneNo;
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
        }
    }

    @SuppressLint("IntentReset")
    public void sendMailBtn(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"Recipient"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT   , "Message Body");
        startActivity(emailIntent);
    }

    public void openWebBtn(View view) {
        TextView uLink = findViewById(R.id.uLink);
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse( "https://" + uLink.getText().toString()));
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        startActivity(intent);
    }
}

