package com.footballtv.tv.adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;


import com.footballtv.tv.Middle22;
import com.footballtv.tv.R;
import com.footballtv.tv.model.ParseItemModel22;
import com.squareup.picasso.Picasso;


import java.util.List;

public class ParseItemAdapter22 extends RecyclerView.Adapter<ParseItemAdapter22.ViewHolder> {

    private final List<ParseItemModel22> ParseItemModel22ArrayList;
    private final Context context;

    public ParseItemAdapter22(List<ParseItemModel22> ParseItemModel22ArrayList, Context context) {
        this.ParseItemModel22ArrayList = ParseItemModel22ArrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parse_item1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ParseItemModel22 ParseItemModel22 = ParseItemModel22ArrayList.get(position);
        holder.title.setText(ParseItemModel22.getTitle());
        Picasso.get().load(ParseItemModel22.getImageLink()).into(holder.imageView);

        Log.e("TAG", "onBindViewHolder: " + ParseItemModel22ArrayList);

        /*if (ParseItemModel22.getGif().startsWith("http")) {
            Glide.with(context)
                    .load(ParseItemModel22.getGif())
                    .into(holder.imageView3);
            holder.imageView3.setVisibility(View.VISIBLE);
        } else {
            holder.imageView3.setVisibility(View.GONE);
        }*/
    }

    @Override
    public int getItemCount() {
        return ParseItemModel22ArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_id);
            title = itemView.findViewById(R.id.title_goal);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            ParseItemModel22 parseItem = ParseItemModel22ArrayList.get(position);
            String postLink = parseItem.getPostLink();
            Log.e("TAG", "postmiddle: " + parseItem.getPostLink());
            if (postLink == null || postLink.isEmpty()) {
                Toast.makeText(context, "Link will be available on match time", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(context, Middle22.class);
                intent.putExtra("title", parseItem.getTitle());
                intent.putExtra("postLink", parseItem.getPostLink());
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        }
    }
}

