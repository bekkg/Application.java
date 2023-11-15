package peaksoft.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import peaksoft.model.MailSender;
import peaksoft.model.User;
import peaksoft.service.ModelService;
import java.time.LocalDate;
import java.util.List;


@Service
@Transactional
public class MailSenderService implements ModelService<MailSender> {

    @PersistenceContext
    private EntityManager entityManager;
    private final UserService userService ;
    private final JavaMailSender javaMailSender;

    @Autowired
    public MailSenderService(UserService userService, JavaMailSender javaMailSender) {
        this.userService = userService;
        this.javaMailSender = javaMailSender;
    }


    @Override
    public void save(MailSender mailSender) {
        List<User> userList = userService.findAll();
        for (User user: userList){
            if(user.isSubscribeToTheNewsletter()){
                sendMassage(mailSender,user.getEmail());
            }
        }
        mailSender.setCreateDate(LocalDate.now());
        entityManager.persist(mailSender);
    }

    private void sendMassage(MailSender mailSender, String email){
        SimpleMailMessage message= new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("Applaza");
        message.setSubject(mailSender.getSender());
        message.setText(mailSender.getText());
        javaMailSender.send(message);
    }

    @Override
    public MailSender findById(Long id) {
        MailSender mailSender = entityManager.find(MailSender.class, id);
        return mailSender;
    }

    @Override
    public List<MailSender> findAll() {
        List<MailSender> mailSenderList = entityManager.createQuery("from MailSender").getResultList();
        return mailSenderList;
    }

    @Override
    public void update(Long id, MailSender mailSender) {
        MailSender oldMailSender = findById(id);
        oldMailSender.setSender(mailSender.getSender());
        oldMailSender.setText(mailSender.getText());
        oldMailSender.setCreateDate(mailSender.getCreateDate());
        entityManager.persist(oldMailSender);
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }




}
