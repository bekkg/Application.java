package peaksoft.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import peaksoft.model.Application;
import peaksoft.model.Genre;
import peaksoft.service.ModelService;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ApplicationService implements ModelService<Application>   {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Application application) {
        Genre  genre = entityManager.find(Genre.class, application.getGenreId());
        application.setGenre(genre);
        application.setGenreName(genre.getName());
        application.setCreateDate(LocalDate.now());
        entityManager.persist(application);
    }

    @Override
    public List<Application> findAll() {
        List<Application> applicationList = entityManager.createQuery("from Application ").getResultList();
        return applicationList;
    }

    @Override
    public Application findById(Long id) {
        Application application = entityManager.find(Application.class, id);
        return application;
    }

    @Override
    public void update(Long id, Application application) {
        Application oldapplication = findById(id);
        oldapplication.setDescription(application.getDescription());
        oldapplication.setName(application.getName());
        oldapplication.setDeveloper(application.getDeveloper());
        oldapplication.setVersion(application.getVersion());
        oldapplication.setAppStatus(application.getAppStatus());
        oldapplication.setGenreName(application.getGenreName());
        entityManager.persist(oldapplication);
    }

    @Override
    public void deleteById(Long id) {
    entityManager.remove(findById(id));
    }

    public List<Application> getApplicationByUser(Long userId){
        return entityManager.createQuery("select app from Application app join app.users u where u.id=:id", Application.class)
                .setParameter("id",userId).getResultList();
    }

    public List<Application> searchApplicationByName(String name){
        return entityManager.createQuery("SELECT app FROM Application app WHERE app.name =:name", Application.class)
                .setParameter("name",name).getResultList();
    }

    public List<Application> findApplicationByUser(String name){
        return entityManager.createQuery("select app from Application app where app.name =: name", Application.class)
                .setParameter("name",name).getResultList();
    }

}
