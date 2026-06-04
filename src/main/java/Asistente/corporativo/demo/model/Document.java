package Asistente.corporativo.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.mockito.internal.stubbing.answers.ThrowsExceptionForClassType;
import org.w3c.dom.Text;

import java.time.LocalDateTime;

@Entity

@AllArgsConstructor
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(columnDefinition = "Text")//diz para o bd usr o tipo texto, e não tipo varchar
    private String conteudo;
    private LocalDateTime dataUpload;
}
