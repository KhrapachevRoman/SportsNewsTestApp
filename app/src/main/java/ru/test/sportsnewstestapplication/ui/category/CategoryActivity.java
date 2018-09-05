package ru.test.sportsnewstestapplication.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.test.sportsnewstestapplication.R;
import ru.test.sportsnewstestapplication.deps.Deps;
import ru.test.sportsnewstestapplication.models.network.CategoryResponse;
import ru.test.sportsnewstestapplication.networking.NewsService;
import ru.test.sportsnewstestapplication.ui.App;
import ru.test.sportsnewstestapplication.ui.article.ArticleActivity;
import ru.test.sportsnewstestapplication.ui.components.DotsProgressBar;

/**
 * Created by khrapachev on 04.09.2018.
 */

public class CategoryActivity extends App implements CategoryView {

    static final String CATEGORY_NAME = "category_name";
    static final String CATEGORY_NAME_RES = "category_name_res";

    static final String FOOTBALL = "football";
    static final String HOCKEY = "hockey";
    static final String TENNIS = "tennis";
    static final String BASKETBALL = "basketball";
    static final String VOLLEYBALL = "volleyball";
    static final String CYBERSPORT = "cybersport";

    @Inject
    public NewsService newsService;
    private Deps deps;
    private CategoryPresenter categoryPresenter;
    private DotsProgressBar dotsProgressBar;
    private LinearLayout llDotsPB;
    private List<CategoryResponse.Event> mCategoryData = new ArrayList<>();
    private RecyclerView mCategoryRV;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RelativeLayout rlErrorFailure;
    private TextView tvFailure;
    private CategoryAdapter mCategoryAdapter;
    private Button btnRefresh;
    private NavigationView mNavigationView;
    private String mCategoryName;
    private int mCategoryNameRes;
    private DrawerLayout mDrawerLayout;

    private CategoryAdapter.OnItemClickListener onItemClickListener = (new CategoryAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(CategoryResponse.Event Item) {
            Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);
            intent.putExtra(ARTICLE, Item.getArticle());
            startActivity(intent);
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        deps = getDeps();
        deps.inject(this);
        init();
        attachPresenter();


    }

    private void init() {
        renderView();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            //подставим значения после refresh
            mCategoryName = bundle.getString(CATEGORY_NAME, FOOTBALL);
            mCategoryNameRes = bundle.getInt(CATEGORY_NAME_RES, R.string.football);
        } else {
            //установим значения по умолчанию, самая популярная категория
            mCategoryName = FOOTBALL;
            mCategoryNameRes = R.string.football;
        }

        //настроим кастомный toolbar
        Toolbar toolbar = findViewById(R.id.activity_category_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
            //getSupportActionBar().setTitle(R.string.football);
        }

        //Инициализируем список
        mCategoryRV.setLayoutManager(new LinearLayoutManager(this));
        mCategoryAdapter = new CategoryAdapter(mCategoryData, onItemClickListener);
        mCategoryRV.setAdapter(mCategoryAdapter);

        //обновление страницы по верхнему свайпу
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshView();
            }
        });

        //замена в верхнем свайпе черного цвета на разноцвет
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorPrimaryDark);

        //обновление страницы после ошибки
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshView();
            }
        });

        // Настроить view drawer'а
        setupDrawerContent(mNavigationView);


    }


    private void renderView() {
        mNavigationView = findViewById(R.id.activity_category_nav_view);
        mDrawerLayout = findViewById(R.id.activity_category_drawer_layout);
        mCategoryRV = findViewById(R.id.activity_category_rv);
        dotsProgressBar = findViewById(R.id.activity_category_dots_pb);
        llDotsPB = findViewById(R.id.activity_category_dots_pb_ll);
        mSwipeRefreshLayout = findViewById(R.id.activity_category_swipe_container);
        rlErrorFailure = findViewById(R.id.activity_category_error_rl_failure);
        tvFailure = findViewById(R.id.activity_category_error_tv_failure);
        btnRefresh = findViewById(R.id.activity_category_error_btn_failure_refresh);
    }

    //Если presenter уже создан используем его, иначе создаем новый
    private void attachPresenter() {

        categoryPresenter = (CategoryPresenter) getLastCustomNonConfigurationInstance();
        if (categoryPresenter == null) {
            categoryPresenter = new CategoryPresenter(newsService);
        }
        categoryPresenter.attachView(this);

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_football:

                mCategoryName = FOOTBALL;
                mCategoryNameRes = R.string.football;
                categoryPresenter.getCategoryByName();

                break;
            case R.id.nav_basketball:
                mCategoryName = BASKETBALL;
                mCategoryNameRes = R.string.basketball;
                categoryPresenter.getCategoryByName();

                break;
            case R.id.nav_cybersport:
                mCategoryName = CYBERSPORT;
                mCategoryNameRes = R.string.cybersport;
                categoryPresenter.getCategoryByName();

                break;
            case R.id.nav_tennis:
                mCategoryName = TENNIS;
                mCategoryNameRes = R.string.tennis;
                categoryPresenter.getCategoryByName();

                break;
            case R.id.nav_volleyball:
                mCategoryName = VOLLEYBALL;
                mCategoryNameRes = R.string.volleyball;
                categoryPresenter.getCategoryByName();

                break;
            case R.id.nav_hockey:
                mCategoryName = HOCKEY;
                mCategoryNameRes = R.string.hockey;
                categoryPresenter.getCategoryByName();

                break;
            default:
                mCategoryName = FOOTBALL;
                mCategoryNameRes = R.string.football;
                categoryPresenter.getCategoryByName();
        }

        //промотаем список в начало
        mCategoryRV.smoothScrollToPosition(0);

        // выделим визуально выбранный пункт меню, но выглядит не очень
        //menuItem.setChecked(true);
        // закроем drawer после выбора
        mDrawerLayout.closeDrawers();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        categoryPresenter.detachView();
        super.onDestroy();

    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return categoryPresenter;
    }


    @Override
    public String getCategoryName() {
        return mCategoryName;
    }

    @Override
    public int getCategoryNameRes() {
        return mCategoryNameRes;
    }

    @Override
    public void getCategorySuccess(CategoryResponse categoryResponse, int titleRes) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleRes);
        }
        mCategoryData.clear();
        mCategoryData.addAll(categoryResponse.getEvents());
        mCategoryAdapter.notifyDataSetChanged();
    }


    @Override
    public void showWait() {
        if (llDotsPB.getVisibility() == View.GONE) {
            mCategoryRV.setVisibility(View.GONE);
            llDotsPB.setVisibility(View.VISIBLE);
            dotsProgressBar.start();
        }
    }

    @Override
    public void removeWait() {
        if (llDotsPB.getVisibility() == View.VISIBLE) {
            mCategoryRV.setVisibility(View.VISIBLE);
            llDotsPB.setVisibility(View.GONE);
            dotsProgressBar.stop();
        }
    }

    @Override
    public void refreshView() {
        finish();
        Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
        intent.putExtra(CATEGORY_NAME, mCategoryName);
        intent.putExtra(CATEGORY_NAME_RES, mCategoryNameRes);
        startActivity(intent);

    }

    @Override
    public void onFailure(String appErrorMessage) {
        mCategoryRV.setVisibility(View.GONE);
        rlErrorFailure.setVisibility(View.VISIBLE);
        tvFailure.setText(appErrorMessage);
        Toast.makeText(getBaseContext(), appErrorMessage, Toast.LENGTH_LONG).show();
    }
}
