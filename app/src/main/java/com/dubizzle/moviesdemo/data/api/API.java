package com.dubizzle.moviesdemo.data.api;


import java.util.HashMap;
import java.util.Map;

/**
 * Developed by Hasham.Tahir on 10/6/2016.
 */

public class API {


    public static final String ACTION_DISCOVER = "discover/movie";

    public static Map<String, String> getParams(int page, String maxDate, String minDate) {

        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("include_video", "false");
        params.put("include_adult", "false");
        params.put("sort_by", "popularity.desc");
        params.put("language", "en-US");
        params.put("primary_release_date.lte", maxDate);
        params.put("primary_release_date.gte", minDate);

        return params;
    }
}
