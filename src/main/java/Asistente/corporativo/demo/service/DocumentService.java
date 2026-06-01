package Asistente.corporativo.demo.service;

import Asistente.corporativo.demo.model.Document;
import Asistente.corporativo.demo.repository.DocumentRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {
    @Autowired
    DocumentRepository documentRepository;

    public List<Document> listarTodos() {
        return documentRepository.findAll();
    }
}
