package models;

import java.util.List;

/**
 * Created by Apoorv Singh on 4/12/2015.
 */
public class Post {
    private List<String> tags;
    private List<Answer> answers;
    private Owner owner;
    private boolean is_answered;
    private Integer view_count;
    private Integer answer_count;
    private Integer score;
    private long last_activity_date;
    private long creation_data;
    private long last_edit_data;
    private long question_id;
    private String link;
    private String title;
    private String body;


    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public boolean isIs_answered() {
        return is_answered;
    }

    public void setIs_answered(boolean is_answered) {
        this.is_answered = is_answered;
    }

    public Integer getView_count() {
        return view_count;
    }

    public void setView_count(Integer view_count) {
        this.view_count = view_count;
    }

    public Integer getAnswer_count() {
        return answer_count;
    }

    public void setAnswer_count(Integer answer_count) {
        this.answer_count = answer_count;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public long getLast_activity_date() {
        return last_activity_date;
    }

    public void setLast_activity_date(long last_activity_date) {
        this.last_activity_date = last_activity_date;
    }

    public long getCreation_data() {
        return creation_data;
    }

    public void setCreation_data(long creation_data) {
        this.creation_data = creation_data;
    }

    public long getLast_edit_data() {
        return last_edit_data;
    }

    public void setLast_edit_data(long last_edit_data) {
        this.last_edit_data = last_edit_data;
    }

    public long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(long question_id) {
        this.question_id = question_id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

