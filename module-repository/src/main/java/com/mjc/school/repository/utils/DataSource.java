package com.mjc.school.repository.utils;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.data.AuthorData;
import com.mjc.school.repository.model.data.NewsData;

import java.util.List;

public class DataSource {
    private final List<AuthorModel> authorModelList;
    private final List<NewsModel> newsModelList;

    private DataSource() {
        authorModelList = AuthorData.getAuthorData().getAuthorModelList();
        newsModelList = NewsData.getNewsData(authorModelList).getNewsModelList();
    }

    public static DataSource getInstance() {
        return LazyDataSource.INSTANCE;
    }

    public List<AuthorModel> getAuthorModelList() {
        return authorModelList;
    }

    public List<NewsModel> getNewsModelList() {
        return newsModelList;
    }

    private static class LazyDataSource {
        static final DataSource INSTANCE = new DataSource();
    }
}
