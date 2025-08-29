package com.group8.library_management.service.impl;

import com.group8.library_management.entity.Genre;
import com.group8.library_management.exception.DuplicateResourceException;
import com.group8.library_management.repository.GenreRepository;
import com.group8.library_management.service.GenreService;
import jakarta.transaction.Transactional;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final MessageSource messageSource;

    public GenreServiceImpl(GenreRepository genreRepository, MessageSource messageSource) {
        this.genreRepository = genreRepository;
        this.messageSource = messageSource;
    }

    private String msg(String key, Object... args) {
        return messageSource.getMessage(key, args, key, LocaleContextHolder.getLocale());
    }

    @Override
    public long countGenres() {
        return genreRepository.countAllByDeletedAtIsNull();
    }

    @Override
    @Transactional
    public void createGenre(String name, Integer parentGenreId) {
        if (genreRepository.existsByNameAndDeletedAtIsNull(name)) {
            throw new DuplicateResourceException(msg("genre.duplicate"));
        }
        Genre newGenre = new Genre();
        newGenre.setName(name);
        if (parentGenreId != null) {
            Genre parent = genreRepository.findGenreByIdAndDeletedAtIsNull(parentGenreId);
            if (parent == null) {
                throw new IllegalArgumentException(msg("genre.parentNotFound", parentGenreId));
            }
            newGenre.setParent(parent);
        } else {
            newGenre.setParent(null);
        }
        genreRepository.save(newGenre);
    }

    @Override
    public void deleteGenre(Integer id) {
        Genre deletedGenre = genreRepository.findGenreByIdAndDeletedAtIsNull(id);
        if (deletedGenre == null) {
            throw new IllegalArgumentException(msg("genre.notFound", id));
        }

        if (!getChildGenres(id).isEmpty()) {
            throw new IllegalArgumentException(msg("genre.hasChildren", id));
        }
        deletedGenre.setDeletedAt(LocalDateTime.now());
        genreRepository.save(deletedGenre);
    }

    @Override
    @Transactional
    public void editGenre(Integer id, String name, Integer parentGenreId) {
        Genre editedGenre = genreRepository.findGenreByIdAndDeletedAtIsNull(id);
        if (editedGenre == null) {
            throw new IllegalArgumentException(msg("genre.notFound", id));
        }
        boolean alreadyExist = genreRepository.existsByNameAndDeletedAtIsNull(name);

        // check duplicate name
        if (alreadyExist && !editedGenre.getName().equals(name)) {
            throw new DuplicateResourceException(msg("genre.duplicate"));
        }
        editedGenre.setName(name);

        // check parent genre id
        if (parentGenreId != null) {
            Genre parent = genreRepository.findGenreByIdAndDeletedAtIsNull(parentGenreId);
            if (parent == null) {
                throw new IllegalArgumentException(msg("genre.parentNotFound", parentGenreId));
            }
            editedGenre.setParent(parent);
        } else {
            editedGenre.setParent(null);
        }
        genreRepository.save(editedGenre);
    }


    @Override
    public List<Genre> getTopLevelGenres() {
        List<Genre> genres = genreRepository.findAllByDeletedAtIsNull();
        return genreRepository.findAllByDeletedAtIsNull()
                .stream()
                .filter(g -> g.getParent() == null)
                .peek(genre -> genre.setHasChildren(hasChildren(genre, genres)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Genre> getAllGenres() {
        List<Genre> genres = genreRepository.findAllByDeletedAtIsNull();
        genres.forEach(genre -> genre.setHasChildren(hasChildren(genre, genres)));
        return genres;
    }

    @Override
    public boolean hasChildren(Genre genre, List<Genre> allGenres) {
        return allGenres.stream().anyMatch(g -> g.getParent() != null && g.getParent().getId().equals(genre.getId()));
    }

    @Override
    public List<Genre> getChildGenres(Integer parentId) {
        List<Genre> allGenres = genreRepository.findAllByDeletedAtIsNull();
        return allGenres.stream()
                .filter(g -> g.getParent() != null && g.getParent().getId().equals(parentId))
                .peek(genre -> genre.setHasChildren(hasChildren(genre, allGenres)))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Genre> getGenreById(Integer id) {
        return genreRepository.findById(id);
    }
}
