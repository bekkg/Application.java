package peaksoft.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import peaksoft.model.enums.SubscriptionStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private int age;
    private String password;
    private LocalDate createDate;
    private boolean subscribeToTheNewsletter;
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;

    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST})
    @JoinTable(name = "users_applications",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "applications_id"))
    private List<Application> applications;

    @ManyToMany (cascade = {CascadeType.DETACH,
                             CascadeType.MERGE,
                             CascadeType.PERSIST,
                            CascadeType.REFRESH},fetch = FetchType.EAGER )
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role: roles){
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
