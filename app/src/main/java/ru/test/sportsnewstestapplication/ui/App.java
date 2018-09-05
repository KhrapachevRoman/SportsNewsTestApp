package ru.test.sportsnewstestapplication.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

import ru.test.sportsnewstestapplication.deps.DaggerDeps;
import ru.test.sportsnewstestapplication.deps.Deps;
import ru.test.sportsnewstestapplication.networking.NetworkModule;

/**
 * Created by khrapachev on 04.09.2018.
 */

public class App extends AppCompatActivity {
    private Deps deps;
    public static final String ARTICLE = "article";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getCacheDir(), "responses");
        deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();

    }

    //для получения зависимостей в дочерних активити
    public Deps getDeps() {
        return deps;
    }

}
