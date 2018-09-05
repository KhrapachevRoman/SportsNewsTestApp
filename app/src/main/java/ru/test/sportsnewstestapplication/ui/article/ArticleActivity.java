package ru.test.sportsnewstestapplication.ui.article;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import ru.test.sportsnewstestapplication.models.network.ArticleResponse;
import ru.test.sportsnewstestapplication.networking.NewsService;
import ru.test.sportsnewstestapplication.ui.App;
import ru.test.sportsnewstestapplication.ui.components.DotsProgressBar;

/**
 * Created by khrapachev on 04.09.2018.
 */

public class ArticleActivity extends App implements ArticleView {

    @Inject
    public NewsService newsService;
    private Deps deps;
    private ArticlePresenter mArticlePresenter;
    private String mArticle;
    private DotsProgressBar dotsProgressBar;
    private LinearLayout llDotsPB, llMain;
    private List<ArticleResponse.Article> mArticleData = new ArrayList<>();
    private RecyclerView mArticleRV;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RelativeLayout rlErrorFailure;
    private TextView tvFailure, tvTeam1, tvTeam2, tvTime, tvTournament, tvPlace, tvPrediction;
    private ArticleAdapter mArticleAdapter;
    private Button btnRefresh;
    private CardView cvPrediction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        deps = getDeps();
        deps.inject(this);
        init();
        attachPresenter();
    }

    private void init() {
        renderView();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mArticle = bundle.getString(ARTICLE, "");
        }

        //Инициализируем список
        mArticleRV.setLayoutManager(new LinearLayoutManager(this));
        mArticleAdapter = new ArticleAdapter(mArticleData);
        mArticleRV.setAdapter(mArticleAdapter);

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
    }

    private void attachPresenter() {

        mArticlePresenter = (ArticlePresenter) getLastCustomNonConfigurationInstance();
        if (mArticlePresenter == null) {
            mArticlePresenter = new ArticlePresenter(newsService);
        }
        mArticlePresenter.attachView(this);

    }

    private void renderView() {

        mArticleRV = findViewById(R.id.activity_article_rv);
        dotsProgressBar = findViewById(R.id.activity_article_dots_pb);
        llDotsPB = findViewById(R.id.activity_article_dots_pb_ll);
        mSwipeRefreshLayout = findViewById(R.id.activity_article_swipe_container);
        rlErrorFailure = findViewById(R.id.activity_article_error_rl_failure);
        tvFailure = findViewById(R.id.activity_article_error_tv_failure);
        btnRefresh = findViewById(R.id.activity_article_error_btn_failure_refresh);
        llMain = findViewById(R.id.activity_article_ll_main);
        tvTime = findViewById(R.id.activity_article_tv_time);
        tvTournament = findViewById(R.id.activity_article_tv_tournament);
        tvPlace = findViewById(R.id.activity_article_tv_place);
        tvPrediction = findViewById(R.id.activity_article_tv_prediction);
        cvPrediction = findViewById(R.id.activity_article_cv_prediction);
    }

    @Override
    protected void onDestroy() {
        mArticlePresenter.detachView();
        super.onDestroy();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mArticlePresenter;
    }

    @Override
    public String getArticleValue() {
        return mArticle;
    }

    @Override
    public void getArticleSuccess(ArticleResponse articleResponse) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(articleResponse.getTeam1() + " и " + articleResponse.getTeam2());
        }

        if (articleResponse.getTime()==null | articleResponse.getTime().isEmpty() ) {
            tvTime.setVisibility(View.GONE);
        } else {
            tvTime.setText("Когда: " + articleResponse.getTime());
        }

        if (articleResponse.getTournament()==null | articleResponse.getTournament().isEmpty()) {
            tvTournament.setVisibility(View.GONE);

        } else {
            tvTournament.setText("Чемпионат: " + articleResponse.getTournament());
        }
        if (articleResponse.getPlace()==null | articleResponse.getPlace().isEmpty()) {
            tvPlace.setVisibility(View.GONE);
        } else {
            tvPlace.setText("Где: " + articleResponse.getPlace());
        }

        if (articleResponse.getPrediction().isEmpty()){
            cvPrediction.setVisibility(View.GONE);
        }else{
            tvPrediction.setText(articleResponse.getPrediction());
        }

        mArticleData.clear();
        mArticleData.addAll(articleResponse.getArticle());
        if (mArticleData.isEmpty()) {
            mArticleRV.setVisibility(View.GONE);
        }
        mArticleAdapter.notifyDataSetChanged();
    }

    @Override
    public void showWait() {
        if (llDotsPB.getVisibility() == View.GONE) {
            llMain.setVisibility(View.GONE);
            llDotsPB.setVisibility(View.VISIBLE);
            dotsProgressBar.start();
        }
    }

    @Override
    public void removeWait() {
        if (llDotsPB.getVisibility() == View.VISIBLE) {
            llMain.setVisibility(View.VISIBLE);
            llDotsPB.setVisibility(View.GONE);
            dotsProgressBar.stop();
        }
    }

    @Override
    public void refreshView() {
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onFailure(String appErrorMessage) {
        llMain.setVisibility(View.GONE);
        rlErrorFailure.setVisibility(View.VISIBLE);
        tvFailure.setText(appErrorMessage);
        Toast.makeText(getBaseContext(), appErrorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
