package dl.fit.bstu.by.lab8.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dl.fit.bstu.by.lab8.CreateUserActivity;
import dl.fit.bstu.by.lab8.User;
import dl.fit.bstu.by.lab8.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private List<User> users;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try{
            users = JSONHelper.importFromJSON(getContext());
            if(users == null){
                users = new ArrayList<>();
            }
        }
        catch (Exception e){

        }

        final RecyclerView userRV = binding.list;
        StateAdapter adapter = new StateAdapter(getContext(), users);
        userRV.setAdapter(adapter);



        Intent i = new Intent(getContext(), CreateUserActivity.class);
        startActivity(i);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}