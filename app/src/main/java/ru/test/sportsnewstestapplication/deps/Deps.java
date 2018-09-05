package ru.test.sportsnewstestapplication.deps;

import javax.inject.Singleton;

import dagger.Component;
import ru.test.sportsnewstestapplication.networking.NetworkModule;
import ru.test.sportsnewstestapplication.ui.article.ArticleActivity;
import ru.test.sportsnewstestapplication.ui.category.CategoryActivity;

/**
 * Created by khrapachev on 04.09.2018.
 */


@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
    void inject(CategoryActivity categoryActivity);
    void inject(ArticleActivity articleActivity);
}
