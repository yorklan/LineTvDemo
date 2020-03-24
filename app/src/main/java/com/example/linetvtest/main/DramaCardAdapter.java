package com.example.linetvtest.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.linetvtest.R;
import com.example.linetvtest.data.Drama;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class DramaCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Drama> mDramaList = new ArrayList<>();

    private class DramaCardViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgThumb;
        private TextView textName;
        private TextView textCreateAt;
        private TextView textRating;

        DramaCardViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumb = itemView.findViewById(R.id.img_card_thumb);
            textName = itemView.findViewById(R.id.text_card_name);
            textCreateAt = itemView.findViewById(R.id.text_card_create_at);
            textRating = itemView.findViewById(R.id.text_card_rating);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DramaCardViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_drama, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof DramaCardViewHolder){
            setDramaCard((DramaCardViewHolder) holder, mDramaList.get(position));
        }
    }

    private void setDramaCard(DramaCardViewHolder dramaCardViewHolder, final Drama drama){
        Glide.with(dramaCardViewHolder.itemView.getContext())
                .load(drama.getThumb())
                .transition(withCrossFade())
                .into(dramaCardViewHolder.imgThumb);
        dramaCardViewHolder.textName.setText(drama.getName());
        dramaCardViewHolder.textCreateAt.setText(drama.getCreatedAtStringSimple());
        dramaCardViewHolder.textRating.setText(drama.getRatingStringDF2());
        dramaCardViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(drama);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDramaList.size();
    }

    /** Public Methods **/
    private OnItemClickListener onItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(@NonNull Drama drama);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    void updateData(List<Drama> newDramaList){
        mDramaList.clear();
        mDramaList.addAll(newDramaList);
    }

}
