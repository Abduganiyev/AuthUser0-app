package uz.scripteone.userauth.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.scripteone.userauth.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
