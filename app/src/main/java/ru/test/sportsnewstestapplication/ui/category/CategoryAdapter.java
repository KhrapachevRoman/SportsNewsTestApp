package ru.test.sportsnewstestapplication.ui.category;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ru.test.sportsnewstestapplication.R;
import ru.test.sportsnewstestapplication.models.network.CategoryResponse;

/**
 * Created by khrapachev on 04.09.2018.
 */


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final OnItemClickListener listener;
    private List<CategoryResponse.Event> data;

    CategoryAdapter(List<CategoryResponse.Event> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        holder.itemClick(data.get(position), listener);
        holder.tvTitle.setText(data.get(position).getTitle());
        holder.tvTime.setText(data.get(position).getTime());
        holder.tvCoefficient.setText(data.get(position).getCoefficient());
        holder.tvPlace.setText(data.get(position).getPlace());
        holder.tvPreview.setText(data.get(position).getPreview());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickListener {
        void onItemClick(CategoryResponse.Event Item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvTime,tvCoefficient,tvPlace,tvPreview;
        RelativeLayout rlReviewItem;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.category_list_item_tv_title);
            tvTime = itemView.findViewById(R.id.category_list_item_tv_time);
            tvCoefficient = itemView.findViewById(R.id.category_list_item_tv_coefficient);
            rlReviewItem = itemView.findViewById(R.id.category_list_item_rl);
            tvPlace = itemView.findViewById(R.id.category_list_item_tv_place);
            tvPreview = itemView.findViewById(R.id.category_list_item_tv_preview);
        }

        void itemClick(final CategoryResponse.Event Item, final OnItemClickListener listener) {
            rlReviewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(Item);
                }
            });
        }

    }
}
