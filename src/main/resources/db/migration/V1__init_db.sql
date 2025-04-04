CREATE SCHEMA IF NOT EXISTS netflix;

CREATE TABLE IF NOT EXISTS netflix.category (
                                                id SERIAL PRIMARY KEY,
                                                name VARCHAR(250)
    );

CREATE TABLE IF NOT EXISTS netflix.tvshow (
                                              id SERIAL PRIMARY KEY,
                                              name VARCHAR(250),
    short_description VARCHAR(500),
    long_description VARCHAR(1000),
    year INT,
    recommended_age INT,
    advertising VARCHAR(800),
    category_ref INT,
    FOREIGN KEY (category_ref) REFERENCES netflix.category (id) ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE TABLE IF NOT EXISTS netflix.season (
                                              id SERIAL PRIMARY KEY,
                                              number INT,
                                              name VARCHAR(250),
    tvshow_ref INT,
    FOREIGN KEY (tvshow_ref) REFERENCES netflix.tvshow (id) ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE TABLE IF NOT EXISTS netflix.chapter (
                                               id SERIAL PRIMARY KEY,
                                               number INT,
                                               name VARCHAR(250),
    duration DOUBLE PRECISION,
    season_ref INT,
    FOREIGN KEY (season_ref) REFERENCES netflix.season (id) ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE TABLE IF NOT EXISTS netflix.actor (
                                             id SERIAL PRIMARY KEY,
                                             name VARCHAR(250),
    description VARCHAR(800)
    );

CREATE TABLE IF NOT EXISTS netflix.rel_chapter_actor (
                                                         id_chapter INT,
                                                         id_actor INT,
                                                         PRIMARY KEY (id_chapter, id_actor),
    FOREIGN KEY (id_chapter) REFERENCES netflix.chapter (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_actor) REFERENCES netflix.actor (id) ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE TABLE IF NOT EXISTS netflix.rel_category_tvshows (
                                                            id_category INT,
                                                            id_tvshow INT,
                                                            PRIMARY KEY (id_category, id_tvshow),
    FOREIGN KEY (id_category) REFERENCES netflix.category (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_tvshow) REFERENCES netflix.tvshow (id) ON DELETE CASCADE ON UPDATE CASCADE
    );

