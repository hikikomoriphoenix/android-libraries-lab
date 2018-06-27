package marabillas.loremar.androidlibrarieslab.datahandlingtests.gsontests;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import marabillas.loremar.androidlibrarieslab.datahandling.gson.GsonActivity;

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
        assertThat(test.fruits[3].name, is("pineapple"));
        assertThat(test.fruits[3].color, is("yellow"));
        assertThat(test.fruits[3].shape, is("oval"));
    }

    @Test
    public void testGsonFromUrlSingleField() {
        JsonArray json = activity.gsonFromUrlSingleField("fruits").getAsJsonArray();
        JsonObject fruit = json.get(3).getAsJsonObject();
        assertThat(fruit.get("name").getAsString(), is("pineapple"));
        assertThat(fruit.get("color").getAsString(), is("yellow"));
        assertThat(fruit.get("shape").getAsString(), is("oval"));
    }

    @Test
    public void testSaveJsonFile() {
        assertThat(activity.saveJsonFile().exists(), is(true));
    }

    @Test
    public void testGsonFromFile() {
        GsonActivity.Test test = activity.gsonFromFile();
        assertThat(test.fruits[3].name, is("pineapple"));
        assertThat(test.fruits[3].color, is("yellow"));
        assertThat(test.fruits[3].shape, is("oval"));
    }

    @Test
    public void testGsonMakeJson() {
        Gson gson = new Gson();
        GsonActivity.Test test = gson.fromJson(activity.gsonMakeJson(), GsonActivity.Test
                .class);
        assertThat(test.fruits[1].name, is("eggplant"));
        assertThat(test.fruits[1].color, is("purple"));
        assertThat(test.fruits[1].shape, is("elongated"));
    }

    @Test
    public void testGsonEdit() {
        String json1 = activity.gsonMakeJson();
        String json2 = activity.gsonEdit(json1, "name", "tomato");
        String json3 = activity.gsonEdit(json2, "color", "red");

        JsonObject jsonObject = new JsonParser().parse(json3).getAsJsonObject();
        JsonArray fruits = jsonObject.getAsJsonArray("fruits");
        JsonObject fruit = fruits.get(0).getAsJsonObject();
        assertThat(fruit.get("name").getAsString(), is("tomato"));
        assertThat(fruit.get("color").getAsString(), is("red"));
    }
}
