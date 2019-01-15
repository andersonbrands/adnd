package com.adnd.bakingapp.adapters;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.adnd.bakingapp.R;
import com.adnd.bakingapp.fragments.StepDetailFragment;
import com.adnd.bakingapp.models.Step;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepsPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public RecipeStepsPagerAdapter(FragmentManager fm, List<Step> steps, Resources res) {
        super(fm);

        for (int i = 0; i < steps.size(); i++) {
            Fragment fragment = StepDetailFragment.newInstance(steps.get(i).toJSONString());
            String title = res.getString(R.string.step_number, i + 1);

            fragments.add(fragment);
            titles.add(title);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

}