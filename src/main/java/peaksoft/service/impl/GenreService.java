package peaksoft.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import peaksoft.model.Genre;
import peaksoft.service.ModelService;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class GenreService implements ModelService <Genre> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Genre genre) {
        genre.setCreatDate(LocalDate.now());
        entityManager.persist(genre);

    }

    @Override
    public Genre findById(Long id) {
        Genre genre = entityManager.find(Genre.class,id);
        return genre;
    }

    @Override
    public List<Genre> findAll() {
        List <Genre> genreList = entityManager.createQuery("from Genre").getResultList();
        return genreList;
    }

    @Override
    public void update(Long id, Genre genre) {
    Genre oldgenre = findById(id);
    oldgenre.setName(genre.getName());
    oldgenre.setDescription(genre.getDescription());
    entityManager.persist(oldgenre);
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }
}
