package com.adnd.bakingapp;


import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.adnd.bakingapp.IdlingResource.SimpleIdlingResource;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.BundleMatchers.hasKey;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> mActivityTestRule = new IntentsTestRule<>(MainActivity.class);

    private SimpleIdlingResource idlingResource;

    @Before
    public void registerIdlingResource() {
        idlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Test
    public void recyclerViewLoadsSuccessfully() {
        final String NUTELLA_PIE = "Nutella Pie";

        Matcher<View> recyclerViewMatcher = withId(R.id.rv_recipes);

        onView(recyclerViewMatcher)
                .check(matches(isDisplayed()));

        Matcher<View> childOneMatcher = childAtPosition(recyclerViewMatcher, 0);
        onView(allOf(withId(R.id.tv_recipe_name), isDescendantOfA(childOneMatcher)))
                .check(matches(withText(NUTELLA_PIE)));
    }

    @Test
    public void clickItem_SendsRecipeDataViaIntent() {
        Matcher<View> recyclerViewMatcher = withId(R.id.rv_recipes);

        onView(recyclerViewMatcher)
                .check(matches(isDisplayed()));

        Matcher<View> childOneMatcher = childAtPosition(recyclerViewMatcher, 0);

        onView(childOneMatcher).perform(click());

        intended(allOf(IntentMatchers.hasExtras(hasKey(RecipeDetailsActivity.RECIPE_JSON_EXTRA_KEY))));
    }

    @Test
    public void clickItem_opensRecipeDetailsActivity() {
        Matcher<View> recyclerViewMatcher = withId(R.id.rv_recipes);

        onView(recyclerViewMatcher)
                .check(matches(isDisplayed()));

        Matcher<View> childOneMatcher = childAtPosition(recyclerViewMatcher, 0);

        onView(childOneMatcher).perform(click());

        onView(withId(R.id.recipe_details_fragment))
                .check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }

    private static Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position) {

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
