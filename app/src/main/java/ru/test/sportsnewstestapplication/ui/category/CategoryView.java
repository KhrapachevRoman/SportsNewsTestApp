package ru.test.sportsnewstestapplication.ui.category;

import ru.test.sportsnewstestapplication.models.network.CategoryResponse;
import ru.test.sportsnewstestapplication.ui.mvp.MvpView;

/**
 * Created by khrapachev on 04.09.2018.
 */


public interface CategoryView extends MvpView {

    String getCategoryName();
    int getCategoryNameRes();

    void getCategorySuccess(CategoryResponse categoryResponse, int categoryNameRes);

}
