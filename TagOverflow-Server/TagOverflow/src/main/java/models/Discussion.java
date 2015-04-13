package models;
import com.fasterxml.jackson.annotation.JsonView;
import views.View;

import java.util.List;

/**
 * Created by Apoorv Singh on 4/2/2015.
 */
public class Discussion {

    @JsonView(View.Minimal.class)
    private String ID;

    @JsonView(View.Minimal.class)
    private String title;

    @JsonView(View.Minimal.class)
    private List<String> tags;

    @JsonView(View.Minimal.class)
    private String avatar;

    @JsonView(View.Ignored.class)
    private String body;

    @JsonView(View.Minimal.class)
    private String username;

    @JsonView(View.Ignored.class)
    private List<Reply> replies;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

}