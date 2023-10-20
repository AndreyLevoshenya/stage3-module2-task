package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.utils.DataSource;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepository implements BaseRepository<AuthorModel, Long> {
    private final DataSource dataSource;

    public AuthorRepository() {
        this.dataSource = DataSource.getInstance();
    }


    @Override
    public List<AuthorModel> readAll() {
        return dataSource.getAuthorModelList();
    }

    @Override
    public Optional<AuthorModel> readById(Long id) {
        return readAll().stream().filter(author -> id.equals(author.getId())).findAny();
    }

    @Override
    public AuthorModel create(AuthorModel entity) {
        List<AuthorModel> authorModelList = dataSource.getAuthorModelList();
        authorModelList.sort(Comparator.comparing(AuthorModel::getId));
        if(!authorModelList.isEmpty()) {
            entity.setId(authorModelList.get(authorModelList.size() - 1).getId() + 1);
        }
        else {
            entity.setId(1L);
        }
        authorModelList.add(entity);
        return entity;
    }

    @Override
    public AuthorModel update(AuthorModel entity) {
        AuthorModel model = readById(entity.getId()).get();
        model.setName(entity.getName());
        model.setLastUpdateDate(entity.getLastUpdateDate());
        return model;
    }

    @Override
    public boolean deleteById(Long id) {
        AuthorModel model = readById(id).get();
        return dataSource.getAuthorModelList().remove(model);
    }

    @Override
    public boolean existById(Long id) {
        return dataSource.getAuthorModelList().stream().anyMatch(author -> id.equals(author.getId()));
    }
}
