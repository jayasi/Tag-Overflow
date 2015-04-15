package controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import helpers.Callback;
import models.Discussion;
import models.Reply;
import models.SORequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import services.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import services.RedisService;
import views.View;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Apoorv Singh on 4/2/2015.
 */

@RequestMapping("/discussions")
@RestController
public class DiscussionController {

    @Autowired
    private DiscussionService discussionService;

    @Autowired
    private RedisService redisService;

    @JsonView(View.Minimal.class)
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Discussion> getDiscussion() throws IOException {
        return redisService.getFromRedis("spring:discussions", null, new Callback<List<Discussion>>() {
            @Override
            public List<Discussion> onKeyNotFound() {
                return discussionService.getAll();
            }
        });
    }

    @RequestMapping(value = "{discussionID}", method = RequestMethod.GET)
    public @ResponseBody Discussion getDiscussion(@PathVariable final String discussionID) throws IOException {
        return redisService.getFromRedis("spring:discussions:"+discussionID, Discussion.class, new Callback<Discussion>() {
            @Override
            public Discussion onKeyNotFound() {
                return discussionService.get(discussionID);
            }
        });
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public @ResponseBody Discussion addDiscussion(@RequestBody SORequest<Discussion> discussion) {
        return discussionService.addQuestion(discussion);
    }

    @RequestMapping(value = "add/reply", method = RequestMethod.POST)
    public @ResponseBody Reply addReply(@RequestBody SORequest<Reply> reply) {
        return discussionService.addAnswer(reply);
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public @ResponseBody List<Discussion> searchByTitle(@RequestParam("title") String title) {
        return discussionService.searchByTitle(title);
    }

    @RequestMapping(value = "search/tag", method = RequestMethod.POST)
    public @ResponseBody List<Discussion> searchByTag(@RequestParam("tag") String tag) {
        return discussionService.searchByTag(tag);
    }
}