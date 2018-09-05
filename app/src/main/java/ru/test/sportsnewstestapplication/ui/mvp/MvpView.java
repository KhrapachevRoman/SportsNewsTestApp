package ru.test.sportsnewstestapplication.ui.mvp;

/**
 * Created by khrapachev on 04.09.2018.
 */


public interface MvpView  {

    void showWait();

    void removeWait();

    void refreshView();

    void onFailure(String appErrorMessage);
}
