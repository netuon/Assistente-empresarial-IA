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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentService {
    @Value("${spring.ai.google.genai.api-key}")
    private String apiKey;
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

    public String responder(String pergunta){
        List<Chunks> chunks = chunksRepository.findAll();
        StringBuilder contexto = new StringBuilder();

        for(Chunks   chunk : chunks){
            contexto.append(chunk.getConteudo()).append("\n");
        }

        String prompt = "Com base nos docs abaixo, responda a pergunta. \n\n"
                + "Documentos:\n" + contexto.toString()
                + "\n\nPergunta: " + pergunta;

        RestClient restClient = RestClient.create();
        String prompLimpo = prompt
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");

        String corpo = """
            {
                "contents" : [{
                    "parts":[{ "text" : "%s"}]
                }]
            }""".formatted(prompLimpo);

        String resposta = restClient.post()
                .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey)
                .header("content-type", "application/json")
                .body(corpo)
                .retrieve()
                .body(String.class);
            return resposta;
    }

}
