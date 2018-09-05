package ru.test.sportsnewstestapplication.networking;

import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.test.sportsnewstestapplication.models.network.ArticleResponse;
import ru.test.sportsnewstestapplication.models.network.CategoryResponse;
import rx.Observable;

/**
 * Created by khrapachev on 04.09.2018.
 */


public interface NetworkService {
    String LIST_API="list.php";
    String POST_API="post.php";

    @GET(LIST_API)
    Observable<CategoryResponse> getCategory(@Query("category") String category);

    @GET(POST_API)
    Observable<ArticleResponse> getArticle(@Query("article") String postString);


}
