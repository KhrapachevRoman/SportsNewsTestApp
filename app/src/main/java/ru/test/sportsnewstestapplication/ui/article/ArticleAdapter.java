package ru.test.sportsnewstestapplication.ui.article;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.test.sportsnewstestapplication.R;
import ru.test.sportsnewstestapplication.models.network.ArticleResponse;

/**
 * Created by khrapachev on 04.09.2018.
 */


public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private List<ArticleResponse.Article> data;

    ArticleAdapter(List<ArticleResponse.Article> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_item, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ViewHolder holder, int position) {

        //не выводим хидер, если пустой
        if (!data.get(position).getHeader().isEmpty()){
            holder.tvHeader.setVisibility(View.VISIBLE);
        }else{
            holder.tvHeader.setVisibility(View.GONE);
        }
        holder.tvHeader.setText(data.get(position).getHeader());
        holder.tvText.setText(data.get(position).getText());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHeader, tvText;


        ViewHolder(View itemView) {
            super(itemView);
            tvHeader = itemView.findViewById(R.id.article_list_item_tv_header);
            tvText = itemView.findViewById(R.id.article_list_item_tv_text);

        }

    }
}
