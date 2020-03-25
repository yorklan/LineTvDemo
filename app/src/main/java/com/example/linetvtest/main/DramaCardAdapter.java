package com.example.linetvtest.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    private final int VIEW_TYPE_CARDS = 0;
    final static int VIEW_TYPE_LOADING = 1, VIEW_TYPE_ERROR_NETWORK = 2, VIEW_TYPE_ERROR_SERVER = 3, VIEW_TYPE_ERROR_NO_RESULT = 4;
    private int status;

    DramaCardAdapter() {
        status = VIEW_TYPE_LOADING;
    }

    private class DramaCardViewHolder extends RecyclerView.ViewHolder {

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

    private class ErrorViewHolder extends RecyclerView.ViewHolder {
        private TextView textError;
        private Button btnRetry;

        ErrorViewHolder(@NonNull View itemView) {
            super(itemView);
            textError = itemView.findViewById(R.id.text_card_error);
            btnRetry = itemView.findViewById(R.id.btn_card_retry);
            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onBtnRetryClick();
                }
            });
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgLoading;

        LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLoading = itemView.findViewById(R.id.img_card_loading);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_CARDS:
                return new DramaCardViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_drama, parent, false));
            case VIEW_TYPE_ERROR_NETWORK:
            case VIEW_TYPE_ERROR_SERVER:
            case VIEW_TYPE_ERROR_NO_RESULT:
                return new ErrorViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_error, parent, false));
            case VIEW_TYPE_LOADING:
            default:
                return new LoadingViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_loading, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DramaCardViewHolder) {
            setDramaCard((DramaCardViewHolder) holder, mDramaList.get(position));
        }else if (holder instanceof ErrorViewHolder) {
            setErrorView((ErrorViewHolder) holder);
        }else if(holder instanceof LoadingViewHolder){
            setLoadingView((LoadingViewHolder) holder);
        }
    }

    private void setDramaCard(DramaCardViewHolder dramaCardViewHolder, final Drama drama) {
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

    private void setErrorView(ErrorViewHolder errorViewHolder){
        switch (status){
            case VIEW_TYPE_ERROR_NETWORK:
                errorViewHolder.textError.setText(errorViewHolder.textError.getContext().getString(R.string.error_network));
                errorViewHolder.btnRetry.setVisibility(View.VISIBLE);
                break;
            case VIEW_TYPE_ERROR_SERVER:
                errorViewHolder.textError.setText(errorViewHolder.textError.getContext().getString(R.string.error_server));
                errorViewHolder.btnRetry.setVisibility(View.VISIBLE);
                break;
            case VIEW_TYPE_ERROR_NO_RESULT:
                errorViewHolder.textError.setText(errorViewHolder.textError.getContext().getString(R.string.error_no_result));
                errorViewHolder.btnRetry.setVisibility(View.GONE);
                break;
        }
    }

    private void setLoadingView(LoadingViewHolder loadingViewHolder){
        Glide.with(loadingViewHolder.itemView.getContext())
                .load(R.mipmap.ic_loading)
                .into(loadingViewHolder.imgLoading);
    }

    @Override
    public int getItemViewType(int position) {
        if (!mDramaList.isEmpty()) {
            return VIEW_TYPE_CARDS;
        } else {
            return status;
        }
    }

    @Override
    public int getItemCount() {
        if (mDramaList.isEmpty()) {
            return 1;
        } else {
            return mDramaList.size();
        }
    }

    /**
     * Public Methods
     **/
    private OnItemClickListener onItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(@NonNull Drama drama);

        void onBtnRetryClick();
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    void updateData(List<Drama> newDramaList) {
        mDramaList.clear();
        mDramaList.addAll(newDramaList);
    }

    void setStatus(int status) {
        this.status = status;
    }

}
