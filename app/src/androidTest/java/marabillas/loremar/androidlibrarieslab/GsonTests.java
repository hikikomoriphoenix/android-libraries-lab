package marabillas.loremar.androidlibrarieslab;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class GsonTests {
    private GsonActivity activity;

    @Rule
    public ActivityTestRule<GsonActivity> activityTestRule = new ActivityTestRule<>(GsonActivity
            .class);

    @Before
    public void init() {
        activity = activityTestRule.getActivity();
    }

    @Test
    public void testGsonFromUrl() {
        GsonActivity.Test test = activity.gsonFromUrl();
        StringBuilder sb = new StringBuilder();
        for (String item :
                test.fruits) {
            sb.append(item).append(" ");
        }
        assertThat(sb.toString().trim(), is("mango apple banana pineapple"));
    }

    @Test
    public void testGsonFromUrlSingleField() {
        JsonObject json = activity.gsonFromUrlSingleField();
        StringBuilder sb = new StringBuilder();
        for (JsonElement item :
                json.getAsJsonArray("fruits")) {
            sb.append(item.getAsString()).append(" ");
        }
        assertThat(sb.toString().trim(), is("mango apple banana pineapple"));
    }

    @Test
    public void testSaveJsonFile() {
        assertThat(activity.saveJsonFile().exists(), is(true));
    }

    @Test
    public void testGsonFromFile() {
        GsonActivity.Test test = activity.gsonFromFile();
        StringBuilder sb = new StringBuilder();
        for (String item :
                test.fruits) {
            sb.append(item).append(" ");
        }
        assertThat(sb.toString().trim(), is("mango apple banana pineapple"));
    }
}
