package services;

import models.*;
import retrofit.Callback;
import retrofit.http.*;

import java.util.List;

/**
 * Created by Apoorv Singh on 4/12/2015.
 */
public interface SO {

    @GET("/questions")
    SOResponse<Post> getQuestions(
        @Query("order") String order,
        @Query("sort") String sort,
        @Query("site") String site
    );

    @GET("/questions/{id}")
    SOResponse<Post> getQuestion(
            @Path("id") String id,
            @Query("order") String order,
            @Query("sort") String sort,
            @Query("site") String site,
            @Query("filter") String filter
    );

    @FormUrlEncoded
    @Headers({
            "Content-type: application/x-www-form-urlencoded"
    })
    @POST("/questions/add")
    SOResponse<Post> addQuestion(
            @Field("title") String title,
            @Field("body") String body,
            @Field("tags") List<String> tags,
            @Field("key") String key,
            @Field("access_token") String access_token,
            @Field("site") String site,
            @Field("preview") Boolean preview
    );


    @FormUrlEncoded
    @Headers({
            "Content-type: application/x-www-form-urlencoded"
    })
    @POST("/questions/{id}/answers/add")
    SOResponse<Answer> addAnswer(
            @Path("id") String id,
            @Field("body") String body,
            @Field("key") String key,
            @Field("access_token") String access_token,
            @Field("site") String site,
            @Field("preview") Boolean preview
    );

    @GET("/search")
    SOResponse<Post> getQuestionsByTags(
        @Query("order") String order,
        @Query("sort") String sort,
        @Query("site") String site,
        @Query("tagged") String tag
    );

    @GET("/similar")
    SOResponse<Post> getQuestionsByTitle(
            @Query("order") String order,
            @Query("sort") String sort,
            @Query("site") String site,
            @Query("title") String title
    );
}
