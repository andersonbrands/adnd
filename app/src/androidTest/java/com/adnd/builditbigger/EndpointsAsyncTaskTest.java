package com.adnd.builditbigger;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;
import android.util.Log;

import com.adnd.builditbigger.utils.EndpointsAsyncTask;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertTrue;


@RunWith(AndroidJUnit4.class)
public class EndpointsAsyncTaskTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void asyncTestReturnsNonEmptyString() {
        String result = null;

        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(null);
        endpointsAsyncTask.execute();

        try {
            result = endpointsAsyncTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(!TextUtils.isEmpty(result));
    }

}
