package com.example.walid.project6;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder> {

    private List<Article> articles;
    private View emptyView;
    private ItemClickListener itemClickListener;

    public ArticlesAdapter(List<Article> articles) {
        this.articles = articles;
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
        if (emptyView != null)
            if (articles.size() > 0) {
                emptyView.setVisibility(View.GONE);
            } else {
                emptyView.setVisibility(View.VISIBLE);
            }
    }

    @NonNull
    @Override
    public ArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new ArticlesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesViewHolder holder, final int position) {
        if (itemClickListener != null)
            holder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClicked(articles.get(position));
                }
            });
        holder.updateViews(articles.get(position));

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ArticlesViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView trail;
        TextView author;
        TextView section;
        TextView date;
        ImageView img;
        View parent;

        public ArticlesViewHolder(View itemView) {
            super(itemView);
            this.parent = itemView;
            title = itemView.findViewById(R.id.titleTv);
            trail = itemView.findViewById(R.id.trailTv);
            author = itemView.findViewById(R.id.authorTv);
            date = itemView.findViewById(R.id.dateTv);
            section = itemView.findViewById(R.id.sectionTv);
            img = itemView.findViewById(R.id.img);
        }

        public void updateViews(Article article) {
            title.setText(article.getTitle());
            trail.setText(article.getTrailText());
            author.setText(article.getAuthor());
            date.setText(article.getDate());
            section.setText(article.getSection());
            if (!article.getThumbnail().contentEquals(""))
                Picasso.get().load(article.getThumbnail()).placeholder(R.drawable.ic_outline_image_24px).into(img);
            else
                Picasso.get().load(R.drawable.ic_outline_image_24px).into(img);
        }
    }
}
