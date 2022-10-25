package dl.fit.bstu.by.lab8.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dl.fit.bstu.by.lab8.R;
import dl.fit.bstu.by.lab8.User;

public class StateAdapter  extends RecyclerView.Adapter<StateAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<User> users;

    public StateAdapter(Context context, List<User> states) {
        this.users = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public StateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.card_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StateAdapter.ViewHolder holder, int position) {
        User user = users.get(position);

        holder.imgView.setImageBitmap(BitmapFactory.decodeFile(user.getImg()));
        holder.nameView.setText(user.getName());
        holder.emailView.setText(user.getEmail());
        holder.phoneView.setText(user.getPhone());

    }

    @Override
    public int getItemCount() {
       return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgView;
        final TextView nameView, emailView,phoneView;
        ViewHolder(View view){
            super(view);
            imgView = view.findViewById(R.id.img);
            nameView = view.findViewById(R.id.name);
            emailView = view.findViewById(R.id.email);
            phoneView = view.findViewById(R.id.phone);
        }
    }
}
