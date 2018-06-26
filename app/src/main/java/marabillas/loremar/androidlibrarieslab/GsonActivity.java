package marabillas.loremar.androidlibrarieslab;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
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
    private String testJsonUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView view = new TextView(this);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(view);
        testJsonUrl = "https://raw.githubusercontent" +
                ".com/hikikomoriphoenix/android-libraries-lab/master/test.json";
    }

    protected class Test {
        String[] fruits;
    }

    protected Test gsonFromUrl() {
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

    protected JsonObject gsonFromUrlSingleField() {
        JsonParser parser = new JsonParser();
        try {
            InputStream in = new URL(testJsonUrl).openStream();
            InputStreamReader reader = new InputStreamReader(in);
            return parser.parse(reader).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected File saveJsonFile() {
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

    protected Test gsonFromFile() {
        File testFile = new File(getFilesDir(), "test.json");
        try {
            FileReader fReader = new FileReader(testFile);
            return new Gson().fromJson(fReader, Test.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
