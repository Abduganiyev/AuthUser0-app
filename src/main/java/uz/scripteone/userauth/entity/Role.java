package uz.scripteone.userauth.entity;


import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.GrantedAuthority;
import uz.scripteone.userauth.entity.template.AbcEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Role extends AbcEntity implements GrantedAuthority {

    @Column(nullable = false, unique = true)
    private String name;

    @Override
    public String getAuthority() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return getId() != null && Objects.equals(getId(), role.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
