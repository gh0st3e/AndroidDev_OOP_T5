package dl.fit.bstu.by.lab8;

import android.content.Context;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class JSONHelper {
    private static final String FILE_NAME = "json.json";

    static boolean exportToJSON(Context context, List<User> dataList) {

        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setUsers(dataList);
        String jsonString = gson.toJson(dataItems);

        try(FileOutputStream fileOutputStream =
                    context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    static List<User> importFromJSON(Context context) {

        try(FileInputStream fileInputStream = context.openFileInput(FILE_NAME);

            InputStreamReader streamReader = new InputStreamReader(fileInputStream)){

            Gson gson = new Gson();
            DataItems dataItems = gson.fromJson(streamReader, DataItems.class);

            return  dataItems.getUsers();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        return null;
    }

    private static class DataItems {
        private List<User> users;

        List<User> getUsers() {
            return users;
        }
        void setUsers(List<User> users) {
            this.users = users;
        }
    }
}
