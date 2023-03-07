package com.openclassrooms.entrevoisins.ui.neighbour_list;

import static com.openclassrooms.entrevoisins.NeighbourExtras.KEY_NEIGHBOUR_ABOUT_ME;
import static com.openclassrooms.entrevoisins.NeighbourExtras.KEY_NEIGHBOUR_ADDRESS;
import static com.openclassrooms.entrevoisins.NeighbourExtras.KEY_NEIGHBOUR_AVATAR_URL;
import static com.openclassrooms.entrevoisins.NeighbourExtras.KEY_NEIGHBOUR_ID;
import static com.openclassrooms.entrevoisins.NeighbourExtras.KEY_NEIGHBOUR_IS_FAVORITE;
import static com.openclassrooms.entrevoisins.NeighbourExtras.KEY_NEIGHBOUR_NAME;
import static com.openclassrooms.entrevoisins.NeighbourExtras.KEY_NEIGHBOUR_PHONE_NUMBER;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    /**
     * Identifier
     */
    private long id;

    /**
     * Full name
     */
    private String name;

    /**
     * Avatar
     */
    private String avatarUrl;

    /**
     * Adress
     */
    private String address;

    /**
     * Phone number
     */
    private String phoneNumber;

    /**
     * About me
     */
    private String aboutMe;

    /**
     * Is Favorite
     */
    private Boolean isFavorite;
    private NeighbourApiService mApiService;

    // UI Components
    @BindView(R.id.activity_details_name_first)
    TextView detailFirstName;
    @BindView(R.id.activity_details_name_second)
    TextView detailSecondName;
    @BindView(R.id.activity_details_location)
    TextView detailLocation;
    @BindView(R.id.activity_details_phone_number)
    TextView detailPhoneNumber;
    @BindView(R.id.activity_details_facebook_url)
    TextView detailFacebookUrl;
    @BindView(R.id.activity_details_about)
    TextView detailAbout;
    @BindView(R.id.activity_details_avatar)
    ImageView detailAvatar;
    @BindView(R.id.activity_details_back_button)
    ImageView detailBackButton;
    @BindView(R.id.activity_details_favorites_button)
    FloatingActionButton detailFavoriteButton;

    String facebookUrl = "www.facebook.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_neighbour);
        ButterKnife.bind(this);
        mApiService = DI.getNeighbourApiService();

        //retrieves neighbor information
        getNeighbourInfo();

        //creating a new neighbor
        Neighbour mNeighbour = new Neighbour(id, name, avatarUrl, address, phoneNumber, aboutMe);

        //bind the view with data collected
        bind(mNeighbour);

        //update the favorite icon
        updateFavoriteIcon(isFavorite);

        //back to the previous activity if the backButton is pressed
        detailBackButton.setOnClickListener(v -> onBackPressed());

        //update the favorite status of the current neighbor when the favorite button is pressed
        detailFavoriteButton.setOnClickListener(v -> {
            mApiService.setFavoriteById(id);
            updateFavoriteIcon(mApiService.getFavoriteById(id));
        });

    }

    /**
     * Set the right imageResource to the favorite button from a boolean information
     *
     * @param isFavorite
     */
    public void updateFavoriteIcon(Boolean isFavorite) {

        if (isFavorite) {
            detailFavoriteButton.setImageResource(R.drawable.baseline_star_rate_24);
        } else {
            detailFavoriteButton.setImageResource(R.drawable.ic_star_white_24dp);
        }
    }

    /**
     * Bind the detail layout views with the data of a neighbor
     *
     * @param neighbour
     */
    @SuppressLint("SetTextI18n")
    private void bind(Neighbour neighbour) {
        detailFirstName.setText(neighbour.getName());
        detailSecondName.setText(neighbour.getName());
        detailLocation.setText(neighbour.getAddress());
        detailPhoneNumber.setText(neighbour.getPhoneNumber());
        detailAbout.setText(neighbour.getAboutMe());
        detailFacebookUrl.setText(facebookUrl + neighbour.getName().toLowerCase());
        Glide.with(this).load(neighbour.getAvatarUrl()).into(detailAvatar);
    }

    /**
     * Retrieves neighbor information/data from the bundle
     */
    private void getNeighbourInfo() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        id = bundle.getLong(KEY_NEIGHBOUR_ID);
        name = bundle.getString(KEY_NEIGHBOUR_NAME);
        avatarUrl = bundle.getString(KEY_NEIGHBOUR_AVATAR_URL);
        aboutMe = bundle.getString(KEY_NEIGHBOUR_ABOUT_ME);
        address = bundle.getString(KEY_NEIGHBOUR_ADDRESS);
        phoneNumber = bundle.getString(KEY_NEIGHBOUR_PHONE_NUMBER);
        isFavorite = bundle.getBoolean(KEY_NEIGHBOUR_IS_FAVORITE);
    }
}