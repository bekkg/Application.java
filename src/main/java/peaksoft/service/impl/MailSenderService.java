package peaksoft.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import peaksoft.model.MailSender;
import peaksoft.service.ModelService;
import java.time.LocalDate;
import java.util.List;


@Service
@Transactional
public class MailSenderService implements ModelService<MailSender> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(MailSender mailSender) {
        mailSender.setCreateDate(LocalDate.now());
        entityManager.persist(mailSender);
        System.out.println("success save Mailsender Service");
    }
    @Override
    public MailSender findById(Long id) {
        MailSender mailSender = entityManager.find(MailSender.class, id);

        return mailSender;
    }

    @Override
    public List<MailSender> findAll() {
        List<MailSender> mailSenderList = entityManager.createQuery("from MailSender").getResultList();
        System.out.println("success findAll Mailsender Service");
        return mailSenderList;
    }

    @Override
    public void update(Long id, MailSender mailSender) {
        MailSender oldMailSender = findById(id);
        oldMailSender.setSender(mailSender.getSender());
        oldMailSender.setText(mailSender.getText());
        oldMailSender.setCreateDate(mailSender.getCreateDate());
        System.out.println("success update Mailsender Service");
        entityManager.persist(oldMailSender);
    }

    @Override
    public void deleteById(Long id) {
        System.out.println("success deleteById Mailsender Service");
        entityManager.remove(findById(id));
    }


}
