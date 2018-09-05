package ru.test.sportsnewstestapplication.networking;

import android.util.Log;

import ru.test.sportsnewstestapplication.models.network.ArticleResponse;
import ru.test.sportsnewstestapplication.models.network.CategoryResponse;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by khrapachev on 04.09.2018.
 */

public class NewsService {

    private final NetworkService networkService;

    public NewsService(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Subscription getCategories(final NewsService.CategoryDataCallback callback, String string) {
        return networkService.getCategory(string)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends CategoryResponse>>() {
                    @Override
                    public Observable<? extends CategoryResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<CategoryResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(CategoryResponse categoryResponse) {
                        callback.onSuccess(categoryResponse);

                    }
                });
    }

    public Subscription getArticle(final NewsService.ArticleDataCallback callback, String string) {

        return networkService.getArticle(string)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends ArticleResponse>>() {
                    @Override
                    public Observable<? extends ArticleResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<ArticleResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(ArticleResponse articleResponse) {
                        callback.onSuccess(articleResponse);

                    }
                });
    }



    public interface CategoryDataCallback {
        void onSuccess(CategoryResponse categoryResponse);

        void onError(NetworkError networkError);
    }


    public interface ArticleDataCallback {
        void onSuccess(ArticleResponse articleResponse);

        void onError(NetworkError networkError);
    }

}
