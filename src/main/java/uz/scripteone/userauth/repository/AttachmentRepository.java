package uz.scripteone.userauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.scripteone.userauth.entity.Attachment;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
