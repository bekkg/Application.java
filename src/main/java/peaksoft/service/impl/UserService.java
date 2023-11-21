package peaksoft.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.model.Application;
import peaksoft.model.Role;
import peaksoft.model.User;
import peaksoft.service.ModelService;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;


@Service
@Transactional
public class UserService implements ModelService<User> {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void save(User user) {
        if ( roleService.findAll().isEmpty()){
            roleService.creat("ADMIN");
            roleService.creat("USER");
        }
        if (findAll().isEmpty()) {
            Role adminRole = roleService.findByName("ADMIN");
            adminRole.setUsers(Collections.singletonList(user));
            user.setRoles(Collections.singletonList(adminRole));
        } else {
            Role userRole = roleService.findByName("USER");
            userRole.setUsers(Collections.singletonList(user));
            user.setRoles(Collections.singletonList(userRole));
        }
        user.setCreateDate(LocalDate.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
    }

    public User findByEmail(String email){
        return entityManager.createQuery("select u from User u where u.email=:email", User.class)
                .setParameter("email",email).getSingleResult();
    }
    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("from User",User.class).getResultList();
    }

    @Override
    public void update(Long id, User user) {
        User oldUser = findById(id);
        oldUser.setName(user.getName());
        oldUser.setAge(user.getAge());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(user.getPassword());
        oldUser.setSubscribeToTheNewsletter(user.isSubscribeToTheNewsletter());
        entityManager.persist(oldUser);
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }


    public User findByName (String email){
        return  entityManager.createQuery("select u from User u where u.email=:email",User.class)
                .setParameter("email",email).getSingleResult();
    }


    public User getUserByGmailName(String email, String password) {
        return (User) entityManager.createQuery("SELECT u FROM User u WHERE u.email =:email and u.password=:password")
                .setParameter("email", email)
                .setParameter("password", password).getSingleResult();
    }


    public User searchUserByName(String userName) {
       return entityManager.createQuery("SELECT u FROM User u WHERE u.email =:email", User.class)
                .setParameter("email", userName).getSingleResult();
    }

    public void addApplicationByUser(Long userid, Long appid) {
        User user = findById(userid);
        Application application = entityManager.find(Application.class, appid);
        if (user != null && application != null) {
            List<Application> myApplications = user.getApplications();
            if (!myApplications.contains(application)) {
                myApplications.add(application);
                entityManager.persist(user);
            }
        }
    }

    public void addRoleByUser ( Long userid, Long roleId){
        User user = findById(userid);
        Role role = entityManager.find(Role.class,roleId);
        if (user != null && role != null){
            List <Role> myRole = user.getRoles();
            if(!myRole.contains(role)){
                myRole.add(role);
                entityManager.persist(user);
            }
        }
    }



}












