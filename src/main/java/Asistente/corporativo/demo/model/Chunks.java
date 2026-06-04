package Asistente.corporativo.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Chunks {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition = "Text")
    private String conteudo;
    @ManyToOne
    private Document document;
}
