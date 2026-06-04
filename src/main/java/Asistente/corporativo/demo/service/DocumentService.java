package Asistente.corporativo.demo.service;

import Asistente.corporativo.demo.model.Chunks;
import Asistente.corporativo.demo.model.Document;
import Asistente.corporativo.demo.repository.ChunksRepository;
import Asistente.corporativo.demo.repository.DocumentRepository;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.annotation.handlers.PDAbstractAppearanceHandler;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentService {
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    ChunksRepository chunksRepository;

    public List<Document> listarTodos() {
        return documentRepository.findAll();
    }

    public String processaPdf(MultipartFile file) throws IOException {
        PDDocument document = Loader.loadPDF(file.getInputStream().readAllBytes());//carregar o conteúdo do arquivo

        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);

        Document doc = new Document(null, file.getOriginalFilename(), text, LocalDateTime.now());
        documentRepository.save(doc);

        dividirEmChunks(text, doc);
        return "documento salvo!!";
    }

    public void dividirEmChunks(String texto, Document document) {
        String[] palavras = texto.split(" ");
        for(int i = 0; i < palavras.length; i+=500){
            StringBuilder str = new StringBuilder();
            for(int j = i; j < i + 500 && j < palavras.length; j++) {
                str.append(palavras[j]).append(" ");
            }
            chunksRepository.save(new Chunks(null, str.toString(), document));
        }
    }
}
