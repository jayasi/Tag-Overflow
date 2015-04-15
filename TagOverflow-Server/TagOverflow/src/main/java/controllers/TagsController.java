package controllers;

import helpers.HTTPHelper;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import services.DiscussionService;
import services.TagService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Apoorv Singh on 3/28/2015.
 */

@RequestMapping("/tags")
@RestController
public class TagsController {

    @Autowired
    private TagService tagService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody List<String> getTags(@RequestParam("title") String title, @RequestParam("body") String body) {
        return tagService.getTags(title, body);
    }

}
