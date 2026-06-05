package Asistente.corporativo.demo.repository;

import Asistente.corporativo.demo.model.Chunks;
import Asistente.corporativo.demo.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChunksRepository extends JpaRepository<Chunks, Long> {
    List<Chunks> findByDocument(Document document);
}
