package com.openclassrooms.entrevoisins.ui.neighbour_list;

import static com.openclassrooms.entrevoisins.NeighbourExtras.KEY_NEIGHBOUR_ABOUT_ME;
import static com.openclassrooms.entrevoisins.NeighbourExtras.KEY_NEIGHBOUR_ADDRESS;
import static com.openclassrooms.entrevoisins.NeighbourExtras.KEY_NEIGHBOUR_AVATAR_URL;
import static com.openclassrooms.entrevoisins.NeighbourExtras.KEY_NEIGHBOUR_ID;
import static com.openclassrooms.entrevoisins.NeighbourExtras.KEY_NEIGHBOUR_IS_FAVORITE;
import static com.openclassrooms.entrevoisins.NeighbourExtras.KEY_NEIGHBOUR_NAME;
import static com.openclassrooms.entrevoisins.NeighbourExtras.KEY_NEIGHBOUR_PHONE_NUMBER;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<MyNeighbourRecyclerViewAdapter.ViewHolder> {

    private final List<Neighbour> mNeighbours;
    private final Context mContext;

    public MyNeighbourRecyclerViewAdapter(List<Neighbour> items, Context context) {
        mNeighbours = items;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_neighbour, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Neighbour neighbour = mNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());
        Glide.with(holder.mNeighbourAvatar.getContext()).load(neighbour.getAvatarUrl()).apply(RequestOptions.circleCropTransform()).into(holder.mNeighbourAvatar);

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteNeighbourEvent(neighbour));
            }
        });
        holder.parentLayout.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, DetailsNeighbourActivity.class);
            intent.putExtra(KEY_NEIGHBOUR_ID, neighbour.getId());
            intent.putExtra(KEY_NEIGHBOUR_NAME, neighbour.getName());
            intent.putExtra(KEY_NEIGHBOUR_AVATAR_URL, neighbour.getAvatarUrl());
            intent.putExtra(KEY_NEIGHBOUR_ABOUT_ME, neighbour.getAboutMe());
            intent.putExtra(KEY_NEIGHBOUR_PHONE_NUMBER, neighbour.getPhoneNumber());
            intent.putExtra(KEY_NEIGHBOUR_ADDRESS, neighbour.getAddress());
            intent.putExtra(KEY_NEIGHBOUR_IS_FAVORITE, neighbour.getIsFavorite());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        ConstraintLayout parentLayout;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            parentLayout = view.findViewById(R.id.fragment_neighbor);
        }
    }
}
