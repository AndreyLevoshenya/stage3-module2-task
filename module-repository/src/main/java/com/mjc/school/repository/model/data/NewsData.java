package com.mjc.school.repository.model.data;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mjc.school.repository.utils.Utils.getRandomContentByFilePath;
import static com.mjc.school.repository.utils.Utils.getRandomDate;

public class NewsData {
    private static final String NEWS_FILENAME = "news";
    private static final String CONTENT_FILENAME = "content";
    private List<NewsModel> newsModelList;
    private static NewsData newsData;

    private NewsData(List<AuthorModel> authorModelList) {
        init(authorModelList);
    }

    private void init(List<AuthorModel> authorModelList) {
        newsModelList = new ArrayList<>();
        newsModelList = new ArrayList<>();
        Random random = new Random();
        for (long i = 1; i <= 20; i++) {
            LocalDateTime date = getRandomDate();
            newsModelList.add(
                    new NewsModel(
                            i,
                            getRandomContentByFilePath(NEWS_FILENAME),
                            getRandomContentByFilePath(CONTENT_FILENAME),
                            date,
                            date,
                            authorModelList.get(random.nextInt(authorModelList.size())).getId()));
        }
    }

    public static NewsData getNewsData(List<AuthorModel> authorModelList) {
        if(newsData == null) {
            newsData = new NewsData(authorModelList);
        }
        return newsData;
    }

    public List<NewsModel> getNewsModelList() {
        return newsModelList;
    }
}
