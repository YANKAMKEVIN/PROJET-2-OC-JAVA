package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsNeighbourActivity extends AppCompatActivity {

    /** Identifier */
    private long id;

    /** Full name */
    private String name;

    /** Avatar */
    private String avatarUrl;

    /** Adress */
    private String address;

    /** Phone number */
    private String phoneNumber;

    /** About me */
    private String aboutMe;

    /**Is Favorite*/
    private Boolean isFavorite;
    private NeighbourApiService mApiService;

    // UI Components
    @BindView(R.id.activity_details_name_first)
    TextView infoNameFirst;
    @BindView(R.id.activity_details_name_second)
    TextView infoNameSecond;
    @BindView(R.id.activity_details_location)
    TextView infoLocation;
    @BindView(R.id.activity_details_number)
    TextView infoNumber;
    @BindView(R.id.activity_details_facebook_url)
    TextView infoFacebook;
    @BindView(R.id.activity_details_about)
    TextView infoAbout;
    @BindView(R.id.activity_details_avatar)
    ImageView infoImageView;
    @BindView(R.id.activity_details_back_button)
    ImageView infoBack;
    @BindView(R.id.activity_details_favorites_button)
    FloatingActionButton infoFavorisButton;

    String facebookUrl = "www.facebook.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_neighbour);
        ButterKnife.bind(this);

        getNeighbourInfo();
        Neighbour mNeighbour = new Neighbour(id,name,avatarUrl,address,phoneNumber,aboutMe);
        bind(mNeighbour);
        updateFavouriteIcon(isFavorite);


        infoBack.setOnClickListener(v -> onBackPressed());
        infoFavorisButton.setOnClickListener(v -> {
            mApiService.setFavouriteNeighbour(id);
            updateFavouriteIcon(mApiService.getFavouriteNeighbour(id));
        });

        mApiService = DI.getNeighbourApiService();
    }

    public void updateFavouriteIcon(Boolean isFavourite){
        if (isFavourite)
            infoFavorisButton.setImageResource(R.drawable.baseline_star_rate_24);
        else
            infoFavorisButton.setImageResource(R.drawable.ic_star_white_24dp);
    }

    @SuppressLint("SetTextI18n")
    private void bind(Neighbour neighbour){
        infoNameFirst.setText(neighbour.getName());
        infoNameSecond.setText(neighbour.getName());
        infoLocation.setText(neighbour.getAddress());
        infoNumber.setText(neighbour.getPhoneNumber());
        infoAbout.setText(neighbour.getAboutMe());
        infoFacebook.setText(facebookUrl + neighbour.getName());
        Glide.with(this).load(neighbour.getAvatarUrl()).into(infoImageView);
    }

    private void getNeighbourInfo(){
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        id = bundle.getLong("neighbourId");
        name = bundle.getString("neighbourName");
        avatarUrl = bundle.getString("neighbourAvatarUrl");
        aboutMe = bundle.getString("neighbourAboutMe");
        address = bundle.getString("neighbourAddress");
        phoneNumber = bundle.getString("neighbourPhoneNumber");
        isFavorite = bundle.getBoolean("neighbourIsFavorite");
    }
}