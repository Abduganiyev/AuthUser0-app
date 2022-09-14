package uz.scripteone.userauth.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.scripteone.userauth.entity.Attachment;
import uz.scripteone.userauth.exception.FileNotFoundException;
import uz.scripteone.userauth.exception.FileStorageException;
import uz.scripteone.userauth.repository.AttachmentRepository;
import uz.scripteone.userauth.service.FileStorageService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FileStorageServiceImp implements FileStorageService {
    private final AttachmentRepository attachmentRepository;

    public FileStorageServiceImp(AttachmentRepository attachmentRepository) throws IOException {
        this.attachmentRepository = attachmentRepository;
    }
    @Override
    public Attachment uploadFileToDB(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename.contains(".."))
            throw new FileStorageException("Uzur"+originalFilename);

        String uniqueFilename = System.currentTimeMillis() + "_" + originalFilename;

        Attachment attachment = new Attachment(
                uniqueFilename,
                originalFilename,
                multipartFile.getBytes(),
                multipartFile.getSize(),
                multipartFile.getContentType()
        );

        Attachment savedAttachment = attachmentRepository.save(attachment);

        return savedAttachment;
    }

    @Override
    public Attachment downloadFileById(Long fileId) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(fileId);
        if (!optionalAttachment.isPresent())
            throw new FileNotFoundException("File Not Found");

        Attachment attachment = optionalAttachment.get();

        return attachment;
    }

    @Override
    public String deleteById(Long id) {
        if (!attachmentRepository.existsById(id))
            throw new FileNotFoundException("File Not Found");

        attachmentRepository.deleteById(id);
        return "Successfully deleted";
    }

    @Override
    public List<Attachment> findAll() {
        return attachmentRepository.findAll();
    }
}
