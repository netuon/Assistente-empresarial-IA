package Asistente.corporativo.demo.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerguntaRequest {
    String nomeDocumento;
    String pergunta;
}
