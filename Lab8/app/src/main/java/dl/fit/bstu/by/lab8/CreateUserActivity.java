package dl.fit.bstu.by.lab8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import dl.fit.bstu.by.lab8.ui.home.StateAdapter;

public class CreateUserActivity extends AppCompatActivity {

    private List<User> users;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        verifyStoragePermissions(this);

        RecyclerView rv = findViewById(R.id.list);
        try{
            users = JSONHelper.importFromJSON(this);
            if(users == null){
                users = new ArrayList<>();
            }
        }
        catch (Exception e){

        }
        int spanCount = 2; // 2 columns
        int spacing = 5; // 50px
        boolean includeEdge = true;
        StateAdapter adapter = new StateAdapter(this, users);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);

        // at last set adapter to recycler view.
        rv.setLayoutManager(layoutManager);
        rv.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        rv.setAdapter(adapter);
    }


}