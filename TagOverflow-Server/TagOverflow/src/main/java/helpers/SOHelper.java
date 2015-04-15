package helpers;

import models.Answer;
import models.Discussion;
import models.Post;
import models.Reply;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Apoorv Singh on 4/2/2015.
 */
public class SOHelper {
    private static String key = "HtY8bMld7du)7cX*VkZ5yg((";

    public static String getKey() {
        return key;
    }

    public static Reply mapAnswer(Answer a) {
        Reply reply = new Reply();
        reply.setBody(a.getBody());
        reply.setUsername(a.getOwner().getDisplay_name());
        return reply;
    }

    public static ArrayList<Reply> mapReplies(List<Answer> answers) {
        ArrayList<Reply> replies = new ArrayList<Reply>();
        for(Answer a : answers) {
            Reply reply = mapAnswer(a);
            replies.add(reply);
        }
        return replies;
    }

    public static Discussion mapPost(Post p) {
        Discussion discussion = new Discussion();
        discussion.setUsername(p.getOwner().getDisplay_name());
        discussion.setAvatar(p.getOwner().getProfile_image());
        discussion.setTitle(p.getTitle());
        discussion.setID(String.valueOf(p.getQuestion_id()));
        discussion.setTags(p.getTags());
        if(p.getBody() != null) {
            discussion.setBody(p.getBody());
        }
        if(p.getAnswers() != null) {
            discussion.setReplies(mapReplies(p.getAnswers()));
        }
        return discussion;
    }

    public static ArrayList<Discussion> mapPosts(List<Post> posts) {
        ArrayList<Discussion> discussions = new ArrayList<Discussion>();
        for(Post p : posts) {
            Discussion discussion = mapPost(p);
            discussions.add(discussion);
        }
        return discussions;
    }
}