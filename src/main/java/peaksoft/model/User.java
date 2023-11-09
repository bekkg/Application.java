package peaksoft.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import peaksoft.model.enums.Role;
import peaksoft.model.enums.SubscriptionStatus;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private String gmail;
    private String password;
    private LocalDate createDate;
    private boolean subscribeToTheNewsletter;
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;
    @Enumerated(EnumType.STRING)
    private Role role;

    @ToString.Exclude
    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST})
    @JoinTable(
            name = "users_and_applications",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "applications_id"))
    private List<Application> applications;

}
