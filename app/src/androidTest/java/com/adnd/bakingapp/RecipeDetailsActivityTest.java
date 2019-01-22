package com.adnd.bakingapp;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import junit.framework.AssertionFailedError;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.BundleMatchers.hasKey;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailsActivityTest {

    @Rule
    public IntentsTestRule<RecipeDetailsActivity> mActivityTestRule = new IntentsTestRule<RecipeDetailsActivity>(RecipeDetailsActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent(InstrumentationRegistry.getTargetContext(), RecipeDetailsActivity.class);
            intent.putExtra(RecipeDetailsActivity.RECIPE_JSON_EXTRA_KEY, TestUtil.NUTELLA_PIE_RECIPE.toJSONString());
            return intent;
        }
    };

    @Test
    public void ingredientsAndStepsListsAreDisplayed() {
        onView(withId(R.id.tv_recipe_ingredients_details))
                .check(matches(isDisplayed()));

        onView(withId(R.id.rv_recipe_steps))
                .check(matches(isDisplayed()));
    }

    @Test
    public void recipeStepDetailsAreDisplayed() {
        if (mActivityTestRule.getActivity().isTwoPane()) {
            onView(withId(R.id.tv_step_number_label))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void clickItem_SendsRecipeDataAndStepPositionViaIntent() {
        if (!mActivityTestRule.getActivity().isTwoPane()) {
            Matcher<View> recyclerViewMatcher = withId(R.id.rv_recipe_steps);

            Matcher<View> childOneMatcher = TestUtil.childAtPosition(recyclerViewMatcher, 0);

            onView(childOneMatcher).perform(click());

            intended(
                    allOf(IntentMatchers.hasExtras(hasKey(RecipeStepsDetailsActivity.RECIPE_JSON_EXTRA_KEY)),
                            IntentMatchers.hasExtras(hasKey(RecipeStepsDetailsActivity.RECIPE_STEP_POSITION_EXTRA_KEY)))
            );
        }
    }

    @Test
    public void clickItem_ShowsRecipeStepsDetails() {
        Matcher<View> recyclerViewMatcher = withId(R.id.rv_recipe_steps);

        Matcher<View> childOneMatcher = TestUtil.childAtPosition(recyclerViewMatcher, 0);

        onView(childOneMatcher).perform(click());

        try {
            onView(withId(R.id.iv_no_video))
                    .check(matches(isDisplayed()));
        } catch (AssertionFailedError e) {
            onView(withId(R.id.player_view))
                    .check(matches(isDisplayed()));
        }
    }

}
