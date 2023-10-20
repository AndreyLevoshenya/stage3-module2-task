package com.mjc.school.repository.model.data;

import com.mjc.school.repository.model.AuthorModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mjc.school.repository.utils.Utils.getRandomContentByFilePath;
import static com.mjc.school.repository.utils.Utils.getRandomDate;

public class AuthorData {
    private static final String AUTHORS_FILENAME = "authors";
    private List<AuthorModel> authorModelList;
    private static AuthorData authorData;

    private AuthorData() {
        init();
    }

    public static AuthorData getAuthorData() {
        if(authorData == null) {
            authorData = new AuthorData();
        }
        return authorData;
    }

    private void init() {
        authorModelList = new ArrayList<>();
        for (long i = 1; i <= 20; i++) {
            LocalDateTime date = getRandomDate();
            authorModelList.add(new AuthorModel(i,
                    getRandomContentByFilePath(AUTHORS_FILENAME),
                    date,
                    date));
        }
    }

    public List<AuthorModel> getAuthorModelList() {
        return authorModelList;
    }
}
