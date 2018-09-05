package ru.test.sportsnewstestapplication.ui.article;

import ru.test.sportsnewstestapplication.models.network.ArticleResponse;
import ru.test.sportsnewstestapplication.models.network.CategoryResponse;
import ru.test.sportsnewstestapplication.ui.mvp.MvpView;

/**
 * Created by khrapachev on 04.09.2018.
 */


public interface ArticleView extends MvpView {

    String getArticleValue();

    void getArticleSuccess(ArticleResponse articleResponse);

}
