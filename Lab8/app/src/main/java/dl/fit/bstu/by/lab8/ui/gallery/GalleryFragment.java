package dl.fit.bstu.by.lab8.ui.gallery;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import dl.fit.bstu.by.lab8.User;
import dl.fit.bstu.by.lab8.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    private EditText nameField;
    private EditText emailField;
    private EditText phoneField;
    private String ImgPath;
    private Button loadImage;
    private Button saveUser;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        nameField = binding.nameField;
        emailField = binding.emailField;
        phoneField = binding.phoneField;

        loadImage = binding.loadImage;
        loadImage.setOnClickListener(loadImageBtn);

        saveUser = binding.save;
        saveUser.setOnClickListener(saveUserBtn);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private View.OnClickListener loadImageBtn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try{
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
            }
            catch (Exception ex){
                verifyStoragePermissions(getActivity());
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 1:
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    ((Cursor) cursor).moveToFirst();
                    int columnIndex;
                    columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    ImgPath = picturePath;
                    cursor.close();
                    Toast toast = Toast.makeText(getContext(),
                            ImgPath, Toast.LENGTH_SHORT);
                    toast.show();
                    break;
            }
        }
    }

    private View.OnClickListener saveUserBtn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try{
                String name = nameField.getText().toString();
                String email = emailField.getText().toString();
                String phone = phoneField.getText().toString();

                User user = new User(name,email,phone,ImgPath);
                users = JSONHelper.importFromJSON(getContext());
                if(users == null){
                    users = new ArrayList<>();
                }
                users.add(user);
                JSONHelper.exportToJSON(getContext(),users);
            }
            catch (Exception ex){
            }
        }
    };

}