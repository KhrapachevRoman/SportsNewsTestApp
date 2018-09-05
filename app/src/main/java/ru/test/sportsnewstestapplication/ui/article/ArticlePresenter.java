package ru.test.sportsnewstestapplication.ui.article;

import android.util.Log;

import ru.test.sportsnewstestapplication.models.network.ArticleResponse;
import ru.test.sportsnewstestapplication.models.network.CategoryResponse;
import ru.test.sportsnewstestapplication.networking.NetworkError;
import ru.test.sportsnewstestapplication.networking.NewsService;
import ru.test.sportsnewstestapplication.ui.category.CategoryView;
import ru.test.sportsnewstestapplication.ui.mvp.MvpPresenter;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by khrapachev on 04.09.2018.
 */

public class ArticlePresenter implements MvpPresenter<ArticleView> {

    private final static String TAG = "ArticlePresenter";
    private final NewsService newsService;
    private ArticleView view;
    private CompositeSubscription subscriptions;
    private ArticleResponse mArticleResponse;

    ArticlePresenter(NewsService newsService) {
        this.subscriptions = new CompositeSubscription();
        this.newsService = newsService;
        this.mArticleResponse = null;
    }

    @Override
    public void attachView(ArticleView mvpView) {
        this.view = mvpView;
        updateView();
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    private void updateView() {
        if (view != null) {

            if (mArticleResponse != null) {
                view.getArticleSuccess(mArticleResponse);
            } else {
                getArticle();
            }

        }

    }

    private void getArticle() {

        view.showWait();
        Subscription subscription = newsService.getArticle(new NewsService.ArticleDataCallback() {
            @Override
            public void onSuccess(ArticleResponse articleResponse) {
                if (view != null) {
                    view.removeWait();
                    mArticleResponse = articleResponse;
                    view.getArticleSuccess(articleResponse);
                }

            }

            @Override
            public void onError(NetworkError networkError) {
                if (view != null) {
                    view.removeWait();
                    view.onFailure(networkError.getAppErrorMessage());
                }
            }

        }, view.getArticleValue());

        subscriptions.add(subscription);

    }

}
