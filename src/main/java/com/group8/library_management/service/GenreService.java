package com.group8.library_management.service;

import com.group8.library_management.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    long countGenres();
    void createGenre(String name, Integer parentGenreId);
    void deleteGenre(Integer id);
    void editGenre(Integer id, String name, Integer parentGenreId);

    List<Genre> getTopLevelGenres();
    List<Genre> getAllGenres();
    List<Genre> getChildGenres(Integer parentId);
    Optional<Genre> getGenreById(Integer id);
    boolean hasChildren(Genre genre, List<Genre> allGenres);
}
