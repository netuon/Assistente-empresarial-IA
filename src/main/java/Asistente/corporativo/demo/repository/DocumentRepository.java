package Asistente.corporativo.demo.repository;

import Asistente.corporativo.demo.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {}
