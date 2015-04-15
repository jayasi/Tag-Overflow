package services;

import helpers.SOHelper;
import models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Apoorv Singh on 4/2/2015.
 */

@Service
public class DiscussionService {

    @Autowired
    private StringRedisTemplate template;

    public List<Discussion> getAll() {
        ArrayList<Discussion> discussions;
        SO api = SOService.getService();
        SOResponse<Post> soResponse = api.getQuestions("desc", "activity", "stackoverflow");
        List<Post> posts = soResponse.getItems();
        discussions = SOHelper.mapPosts(posts);
        return discussions;
    }

    public List<Discussion> searchByTitle(String title) {
        ArrayList<Discussion> discussions;
        SO api = SOService.getService();
        SOResponse<Post> soResponse = api.getQuestionsByTitle("desc", "activity", "stackoverflow", title);
        List<Post> posts = soResponse.getItems();
        discussions = SOHelper.mapPosts(posts);
        return discussions;
    }

    public List<Discussion> searchByTag(String tag) {
        ArrayList<Discussion> discussions;
        SO api = SOService.getService();
        SOResponse<Post> soResponse = api.getQuestionsByTags("desc", "activity", "stackoverflow", tag);
        List<Post> posts = soResponse.getItems();
        discussions = SOHelper.mapPosts(posts);
        return discussions;
    }

    public Discussion get(String id) {
        SO api = SOService.getService();
        SOResponse<Post> soResponse = api.getQuestion(id, "desc", "activity", "stackoverflow", "!-*f(6rkuau1P");
        return SOHelper.mapPost(soResponse.getItems().get(0));
    }

    public Discussion addQuestion(SORequest<Discussion> request) {
        SO api = SOService.getService();
        Discussion discussion = request.getData();
        System.out.println("SENDING REQUEST");
        System.out.println(discussion.getTitle());
        System.out.println(discussion.getBody());
        System.out.println(discussion.getTags().get(0));
        System.out.println(SOHelper.getKey());
        System.out.println(request.getAccess_token());
        SOResponse<Post> response = api.addQuestion(discussion.getTitle(), discussion.getBody(), discussion.getTags(), SOHelper.getKey(), request.getAccess_token(), "stackoverflow", true);
        return SOHelper.mapPost(response.getItems().get(0));
    }

    public Reply addAnswer(SORequest<Reply> request) {
        SO api = SOService.getService();
        Reply reply = request.getData();
        System.out.println("SENDING REQUEST");
        System.out.println(request.getId());
        System.out.println(reply.getBody());
        System.out.println(SOHelper.getKey());
        System.out.println(request.getAccess_token());
        SOResponse<Answer> response = api.addAnswer(request.getId(), reply.getBody(), SOHelper.getKey(), request.getAccess_token(), "stackoverflow", true);
        return SOHelper.mapAnswer(response.getItems().get(0));
    }
}
