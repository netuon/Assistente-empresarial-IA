package Asistente.corporativo.demo.repository;

import Asistente.corporativo.demo.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByNome(String nomeDocumento);
}
