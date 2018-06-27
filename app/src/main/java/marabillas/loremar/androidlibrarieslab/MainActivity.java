package marabillas.loremar.androidlibrarieslab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import marabillas.loremar.androidlibrarieslab.datahandling.gson.GsonActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startActivity(new Intent(getApplicationContext(), GsonActivity.class));
    }
}
