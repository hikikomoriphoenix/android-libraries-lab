package marabillas.loremar.androidlibrarieslab.datahandling.gson;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class GsonActivity extends Activity {
    private final String testJsonUrl = "https://raw.githubusercontent" +
            ".com/hikikomoriphoenix/android-libraries-lab/master/test.json";
    private TextView view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new TextView(this);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String json1 = gsonMakeJson();
        String json2 = gsonEdit(json1, "name", "tomato");
        String json3 = gsonEdit(json2, "color", "red");
        String jsonAll = json1 + "\n\n" + json2 + "\n\n" + json3;
        view.setText(jsonAll);
    }

    public static class Test {
        public Fruit[] fruits;

        public static class Fruit {
            public String name, color, shape;
        }
    }

    public Test gsonFromUrl() {
        try {
            Gson gson = new Gson();
            InputStream in = new URL(testJsonUrl).openStream();
            InputStreamReader reader = new InputStreamReader(in);
            return gson.fromJson(reader, Test.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Especially useful when there is no dedicated class to store field values
    public JsonElement gsonFromUrlSingleField(String field) {
        JsonParser parser = new JsonParser();
        try {
            InputStream in = new URL(testJsonUrl).openStream();
            InputStreamReader reader = new InputStreamReader(in);
            JsonObject json = parser.parse(reader).getAsJsonObject();
            return json.get(field);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public File saveJsonFile() {
        try {
            Gson gson = new Gson();
            InputStream in = new URL(testJsonUrl).openStream();
            InputStreamReader reader = new InputStreamReader(in);
            Test test = gson.fromJson(reader, Test.class);
            File testFile = new File(getFilesDir(), "test.json");
            FileWriter writer = new FileWriter(testFile);
            gson.toJson(test, Test.class, writer);
            writer.close();
            return testFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Test gsonFromFile() {
        File testFile = new File(getFilesDir(), "test.json");
        try {
            FileReader fReader = new FileReader(testFile);
            return new Gson().fromJson(fReader, Test.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String gsonMakeJson() {
        Test test = new Test();
        test.fruits = new Test.Fruit[2];
        for (int i = 0; i < 2; i++) {
            test.fruits[i] = new Test.Fruit();
        }
        test.fruits[0].name = "orange";
        test.fruits[0].color = "orange";
        test.fruits[0].shape = "round";
        test.fruits[1].name = "eggplant";
        test.fruits[1].color = "purple";
        test.fruits[1].shape = "elongated";
        Gson gson = new Gson();
        return gson.toJson(test, Test.class);
    }

    public TextView getView() {
        return view;
    }

    public String gsonEdit(String json, String key, String value) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        JsonArray fruits = jsonObject.getAsJsonArray("fruits");
        JsonObject fruit = fruits.get(0).getAsJsonObject();
        fruit.addProperty(key, value);
        return new Gson().toJson(jsonObject);
    }
}
