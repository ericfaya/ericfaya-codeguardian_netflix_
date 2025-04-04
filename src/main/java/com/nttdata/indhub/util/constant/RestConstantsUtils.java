package com.nttdata.indhub.util.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RestConstantsUtils {

    public static final String APPLICATION_NAME = "/netflix";
    public static final String API_VERSION_1 = "/v1";

    public static final String SUCCESS = "Success";
    public static final String OK = "OK";

    public  static final String ADD = "/add";
    public  static final String DELETE = "/delete";


    public static final String RESOURCE_ACTOR = "/actor";
    public static final String RESOURCE_ACTORS = "/actors";
    public static final String RESOURCE_ACTOR_ID = "/{actorID}";
    public static final String RESOURCE_ACTORS_LARGE = "/actorsShowsChapters";

    public static final String RESOURCE_CATEGORY = "/category";
    public static final String RESOURCE_CATEGORIES = "/categories";
    public static final String RESOURCE_CATEGORY_ID = "/{categoryID}";

    public static final String RESOURCE_CHAPTER = "/chapter";
    public static final String RESOURCE_CHAPTERS = "/chapters";
    public static final String RESOURCE_CHAPTER_ID = "/{chapterID}";

    public static final String RESOURCE_SEASON = "/season";
    public static final String RESOURCE_SEASONS = "/seasons";
    public static final String RESOURCE_SEASON_ID = "/{seasonID}";

    public static final String RESOURCE_TVSHOW = "/tvshow";
    public static final String RESOURCE_TVSHOWS = "/tvshows";
    public static final String RESOURCE_TVSHOW_ID = "/{tvshowID}";
}
