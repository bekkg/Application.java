package peaksoft.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.model.Role;

import java.util.List;

@Service
@Transactional
public class RoleService {
    @PersistenceContext
    private EntityManager entityManager;

    public void creat ( String roleName){
        Role role = new Role();
        role.setRoleName(roleName);
        entityManager.persist(role);
    }

    public Role findByName (String name){
        return entityManager.createQuery("select r from Role r where r.roleName=:name",Role.class).setParameter("name",name).getSingleResult();
    }
    public List<Role> findAll(){
        List<Role> roles = entityManager.createQuery("from Role ").getResultList();
        return roles;
    }
}
