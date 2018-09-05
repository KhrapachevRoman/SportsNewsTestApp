package ru.test.sportsnewstestapplication.ui.mvp;

/**
 * Created by khrapachev on 04.09.2018.
 */


public interface MvpPresenter <V extends MvpView> {
    void attachView(V mvpView);

    void detachView();
}
