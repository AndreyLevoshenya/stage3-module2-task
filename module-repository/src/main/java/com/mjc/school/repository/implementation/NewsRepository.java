package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.utils.DataSource;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class NewsRepository implements BaseRepository<NewsModel, Long> {
    private final DataSource dataSource;

    public NewsRepository() {
        this.dataSource = DataSource.getInstance();
    }

    @Override
    public List<NewsModel> readAll() {
        return dataSource.getNewsModelList();
    }

    @Override
    public Optional<NewsModel> readById(Long id) {
        return readAll().stream().filter(news -> id.equals(news.getId())).findAny();
    }

    @Override
    public NewsModel create(NewsModel entity) {
        List<NewsModel> newsModelList = dataSource.getNewsModelList();
        newsModelList.sort(Comparator.comparing(NewsModel::getId));
        if (!newsModelList.isEmpty()) {
            entity.setId(newsModelList.get(newsModelList.size() - 1).getId() + 1);
        } else {
            entity.setId(1L);
        }
        newsModelList.add(entity);
        return entity;
    }

    @Override
    public NewsModel update(NewsModel entity) {
        NewsModel model = readById(entity.getId()).get();
        model.setTitle(entity.getTitle());
        model.setContent(entity.getContent());
        model.setLastUpdatedDate(entity.getLastUpdateDate());
        model.setAuthorId(entity.getAuthorId());
        return model;
    }

    @Override
    public boolean deleteById(Long id) {
        NewsModel model = readById(id).get();
        return dataSource.getNewsModelList().remove(model);
    }

    @Override
    public boolean existById(Long id) {
        return dataSource.getNewsModelList().stream().anyMatch(news -> id.equals(news.getId()));
    }
}
