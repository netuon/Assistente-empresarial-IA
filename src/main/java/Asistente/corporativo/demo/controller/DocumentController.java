package Asistente.corporativo.demo.controller;

import Asistente.corporativo.demo.model.Document;
import Asistente.corporativo.demo.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@RequestMapping("/documentos")
@RestController
public class DocumentController {
    @Autowired
    DocumentService documentService;

    @GetMapping
    public List<Document> listarTodos() {
        return documentService.listarTodos();
        
    }
    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(documentService.processaPdf(file));
    }
}
