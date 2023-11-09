package peaksoft.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import peaksoft.model.Application;
import peaksoft.model.User;
import peaksoft.model.enums.Role;
import peaksoft.service.ModelService;
import java.time.LocalDate;
import java.util.List;


@Service
@Transactional
public class UserService implements ModelService<User> {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void save(User user) {
        if (!findAll().isEmpty()) {
            user.setRole(Role.USER);
        } else {
            user.setRole(Role.ADMIN);
        }
        user.setCreateDate(LocalDate.now());
        entityManager.persist(user);
    }

    @Override
    public User findById(Long id) {
        User user = entityManager.find(User.class, id);
        System.out.println("success findById User Service");
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> userList = entityManager.createQuery("from User").getResultList();
        return userList;
    }

    @Override
    public void update(Long id, User user) {
        User oldUser = findById(id);
        oldUser.setName(user.getName());
        oldUser.setAge(user.getAge());
        oldUser.setGmail(user.getGmail());
        oldUser.setPassword(user.getPassword());
        oldUser.setRole(user.getRole());
        oldUser.setSubscribeToTheNewsletter(user.isSubscribeToTheNewsletter());
        entityManager.persist(oldUser);
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }

    public User getUserByGmailName(String gmail, String password) {
        return (User) entityManager.createQuery("SELECT u FROM User u WHERE u.gmail =:gmail and u.password=:password")
                .setParameter("gmail", gmail)
                .setParameter("password", password);
    }

    public void searchUserByName(String userName) {
        entityManager.createQuery("SELECT u FROM User u WHERE u.name =:name")
                .setParameter("name", userName);
    }

    public void addApplicationByUser(Long userid, Long appid){
        User user = findById(userid);
        Application application = entityManager.find(Application.class, appid);
        if ( user != null && application != null){
            List<Application> myApplications = user.getApplications();
            if (!myApplications.contains(application)){
                myApplications.add(application);
                entityManager.persist(user);
            }
        }
    }


}












