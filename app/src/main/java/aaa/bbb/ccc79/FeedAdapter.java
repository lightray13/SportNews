package aaa.bbb.ccc79;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tomer.fadingtextview.FadingTextView;

class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView  tvPubDate, tvContent;
    FadingTextView tvTitle;
    CardView cardView;
    private ItemClickListener itemClickListener;

    public FeedViewHolder(View itemView) {
        super(itemView);


        tvTitle = (FadingTextView) itemView.findViewById(R.id.tv_title);
        tvPubDate = (TextView) itemView.findViewById(R.id.tv_pub_date);
        cardView = (CardView) itemView.findViewById(R.id.card_view);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), true);
        return false;
    }
}

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder>{

    private  RSSObject rssObject;
    private Context mContext;
    private LayoutInflater inflater;

    public FeedAdapter(RSSObject rssObject, Context mContext) {
        this.rssObject = rssObject;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.row, parent, false);
        return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final FeedViewHolder holder, final int position) {

        String[] texts = {rssObject.getItems().get(position).getTitle(), rssObject.getItems().get(position).getContent()};

        holder.tvTitle.setTexts(texts);
        holder.tvPubDate.setText(rssObject.getItems().get(position).getPubDate());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, InfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("url", rssObject.getItems().get(position).getLink());
                intent.putExtra("title", rssObject.getItems().get(position).getTitle());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rssObject.items.size();
    }
}
