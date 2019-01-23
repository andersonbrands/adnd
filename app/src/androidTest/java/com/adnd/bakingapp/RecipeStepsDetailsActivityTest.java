package com.adnd.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.AssertionFailedError;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeStepsDetailsActivityTest {

    private final int START_POSITION = 0;

    @Rule
    public ActivityTestRule<RecipeStepsDetailsActivity> mActivityTestRule = new ActivityTestRule<RecipeStepsDetailsActivity>(RecipeStepsDetailsActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent(InstrumentationRegistry.getTargetContext(), RecipeStepsDetailsActivity.class);
            intent.putExtra(RecipeStepsDetailsActivity.RECIPE_JSON_EXTRA_KEY, TestUtil.NUTELLA_PIE_RECIPE.toJSONString());
            intent.putExtra(RecipeStepsDetailsActivity.RECIPE_STEP_POSITION_EXTRA_KEY, START_POSITION);
            return intent;
        }
    };

    @Test
    public void leftNavigationButtonIsInvisible() {
        if (isPortrait()) {
            onView(withId(R.id.btn_previous_step))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        }
    }

    @Test
    public void rightNavigationButtonIsVisible() {
        if (isPortrait()) {
            onView(withId(R.id.btn_next_step))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        }
    }

    @Test
    public void firstStepDetailsIsCorrect() {
        if (isPortrait()) {
            String firstRecipeShortDescription =
                    TestUtil.NUTELLA_PIE_RECIPE.getSteps().get(START_POSITION).getShortDescription();

            onView(withText(firstRecipeShortDescription))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void clickRightNavigationButton_ChangesStepDetails() {
        if (isPortrait()) {
            String secondRecipeShortDescription =
                    TestUtil.NUTELLA_PIE_RECIPE.getSteps().get(START_POSITION + 1).getShortDescription();

            onView(withId(R.id.btn_next_step))
                    .perform(click());

            onView(withText(secondRecipeShortDescription))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void VideoOrNoVideoIsDisplayed() {
        try {
            onView(withId(R.id.iv_no_video))
                    .check(matches(isDisplayed()));
        } catch (AssertionFailedError e) {
            onView(withId(R.id.player_view))
                    .check(matches(isDisplayed()));
        }
    }


    private boolean isPortrait() {
        return mActivityTestRule.getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}
