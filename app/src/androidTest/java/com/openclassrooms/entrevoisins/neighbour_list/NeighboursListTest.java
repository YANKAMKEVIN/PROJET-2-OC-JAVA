
package com.openclassrooms.entrevoisins.neighbour_list;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertNotNull;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;
    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }
    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(ITEMS_COUNT-1));
    }

    /**
     * When we click on an item, the activity Detail is displayed
     */
    @Test
    public void myDetailActivity_shouldOpen_whenRecyclerview_isClicked() {
        // We click on the first element if the recyclerView
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(actionOnItemAtPosition(0, click()));

        // Verify that the Detail activity is displayed
        onView(withId(R.id.activity_detail_neighbour))
                .check(matches(isDisplayed()));
    }

    /**
     * When we enter is the DetailActivity the textView is not null and correctly loaded
     */
    @Test
    public void myTextView_shouldNotBeEmpty(){
        //We click on the first item of the recyclerView
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(actionOnItemAtPosition(0, click()));

        //We verify that the textViews are not null
        assertNotNull(withId(R.id.activity_details_name_first));
        assertNotNull(withId(R.id.name));

        //We verify that the name displayed is Caroline, the name of the first item/neighbor of our list
        onView(withId(R.id.activity_details_name_first)).check(matches(withText("Caroline")));
    }

    /**
     * When we click on the favorite button, the neighbor should be displayed in the Favorite list
     */
    @Test
    public void pressingFavoriteButton_shouldAddTheNeighbor_toFavoriteNeighborList(){
        //We click on the first item of the recyclerView
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(actionOnItemAtPosition(0, click()));
        // Verify that the Detail activity is displayed
        onView(withId(R.id.activity_detail_neighbour))
                .check(matches(isDisplayed()));
        // Verify that favorite button is displayed with the white star, meaning the neighbor is not favorite
        onView(allOf(withId(R.id.activity_details_favorites_button), isDisplayed()))
                .check(matches(withImageResource(R.drawable.ic_star_white_24dp)));
        //We press the favorite button
        onView(withId(R.id.activity_details_favorites_button))
                .perform(click());
        // Verify that favorite button is displayed with the yellow star, meaning the neighbor is now favorite
        onView(allOf(withId(R.id.activity_details_favorites_button), isDisplayed()))
                .check(matches(withImageResource(R.drawable.baseline_star_rate_24)));
        //We press the back button
        onView(allOf(withId(R.id.activity_details_back_button), isDisplayed()))
                .perform(click());
        //We press the Favorites Tab, to display the list of favorites neighbor
        ViewInteraction tabView = onView(
                allOf(withContentDescription("Favorites"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tabs),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());

        //We click on the first item
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(actionOnItemAtPosition(0, click()));
        //We verify that the name of the neighbor is the same as the name of the neighbor added in favorite
        onView(withId(R.id.activity_details_name_first)).check(matches(withText("Caroline")));
    }

    public static Matcher<View> withImageResource(final int resourceId) {
        return new BoundedMatcher<View, FloatingActionButton>(FloatingActionButton.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("with image resource: " + resourceId);
            }
            @Override
            protected boolean matchesSafely(FloatingActionButton fab) {
                Drawable drawable = fab.getDrawable();
                if (drawable == null) {
                    return false;
                }
                Bitmap bitmap = getBitmap(drawable);
                Bitmap expectedBitmap = getBitmap(fab.getContext().getResources().getDrawable(resourceId));
                return bitmap.sameAs(expectedBitmap);
            }
        };
    }
    private static Bitmap getBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}