package denisleonov.fit.bstu.by.lab9_new;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private VisitCardViewModel visitCardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final VisitCardListAdapter adapter = new VisitCardListAdapter(new VisitCardListAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        visitCardViewModel = new ViewModelProvider(this).get(VisitCardViewModel.class);

        visitCardViewModel.getAllWords().observe(this, words -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(words);
        });
    }


    public void newItemBtn(View view) {
        Intent intent = new Intent(this,NewVisitCardActivity.class);
        startActivity(intent);
    }
}