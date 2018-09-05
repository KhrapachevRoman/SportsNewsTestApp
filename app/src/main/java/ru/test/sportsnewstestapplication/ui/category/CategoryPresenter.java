package ru.test.sportsnewstestapplication.ui.category;

import android.util.Log;

import ru.test.sportsnewstestapplication.R;
import ru.test.sportsnewstestapplication.models.network.CategoryResponse;
import ru.test.sportsnewstestapplication.networking.NetworkError;
import ru.test.sportsnewstestapplication.networking.NewsService;
import ru.test.sportsnewstestapplication.ui.mvp.MvpPresenter;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static ru.test.sportsnewstestapplication.ui.category.CategoryActivity.FOOTBALL;

/**
 * Created by khrapachev on 04.09.2018.
 */

public class CategoryPresenter implements MvpPresenter<CategoryView> {

    private final static String TAG = "CategoryPresenter";
    private final NewsService newsService;
    private CategoryView view;
    private CompositeSubscription subscriptions;
    private CategoryResponse mCategoryResponse;
    private int mCategoryNameRes;

    CategoryPresenter(NewsService newsService) {
        this.subscriptions = new CompositeSubscription();
        this.newsService = newsService;
        this.mCategoryResponse = null;
    }

    @Override
    public void attachView(CategoryView mvpView) {
        this.view = mvpView;
        updateView();
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    private void updateView() {
        if (view != null) {

            if (mCategoryResponse != null) {
                view.getCategorySuccess(mCategoryResponse, mCategoryNameRes);
            } else {
                getCategoryByName();
            }

        }

    }


    public void getCategoryByName() {

        view.showWait();
        Subscription subscription = newsService.getCategories(new NewsService.CategoryDataCallback() {
            @Override
            public void onSuccess(CategoryResponse categoryResponse) {
                if (view != null) {
                    view.removeWait();
                    mCategoryResponse = categoryResponse;
                    mCategoryNameRes = view.getCategoryNameRes();
                    view.getCategorySuccess(categoryResponse,view.getCategoryNameRes());
                }

            }

            @Override
            public void onError(NetworkError networkError) {
                Log.d(TAG, "onError");
                if (view != null) {
                    view.removeWait();
                    view.onFailure(networkError.getAppErrorMessage());
                }
            }

        }, view.getCategoryName());

        subscriptions.add(subscription);

    }

}
