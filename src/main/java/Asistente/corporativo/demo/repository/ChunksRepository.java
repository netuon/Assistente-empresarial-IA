package Asistente.corporativo.demo.repository;

import Asistente.corporativo.demo.model.Chunks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChunksRepository extends JpaRepository<Chunks, Long> {
    List<Chunks> id(Long id);
}
