package denisleonov.fit.bstu.by.lab9_new;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class VisitCardListAdapter extends ListAdapter<VisitCard, VisitCardViewHolder> {
    public VisitCardListAdapter(@NonNull DiffUtil.ItemCallback<VisitCard> diffCallback) {
        super(diffCallback);
    }

    @Override
    public VisitCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return VisitCardViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(VisitCardViewHolder holder, int position) {
        VisitCard current = getItem(position);
        holder.bind(current.getWord());
    }

    static class WordDiff extends DiffUtil.ItemCallback<VisitCard> {

        @Override
        public boolean areItemsTheSame(@NonNull VisitCard oldItem, @NonNull VisitCard newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull VisitCard oldItem, @NonNull VisitCard newItem) {
            return oldItem.getWord().equals(newItem.getWord());
        }
    }
}
