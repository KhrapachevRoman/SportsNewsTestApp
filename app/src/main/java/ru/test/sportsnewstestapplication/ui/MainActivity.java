package ru.test.sportsnewstestapplication.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.test.sportsnewstestapplication.R;
import ru.test.sportsnewstestapplication.ui.category.CategoryActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
        finish();
    }
}
