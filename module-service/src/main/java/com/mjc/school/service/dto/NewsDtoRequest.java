package com.mjc.school.service.dto;

import com.mjc.school.service.annotations.Id;
import com.mjc.school.service.annotations.StringField;

import java.util.Objects;

public class NewsDtoRequest {
    private Long id;

    @StringField(min = 5, max = 30)
    private String title;

    @StringField(min = 5, max = 255)
    private String content;

    @Id
    private Long authorId;

    public NewsDtoRequest() {
    }

    public NewsDtoRequest(Long id, String title, String content, Long authorId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "NewsDtoRequest{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", authorId=" + authorId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsDtoRequest that = (NewsDtoRequest) o;
        return id.equals(that.id) && title.equals(that.title) && content.equals(that.content) && Objects.equals(authorId, that.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, authorId);
    }
}
