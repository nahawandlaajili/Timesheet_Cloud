package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contrat implements Serializable {

    private static final long serialVersionUID = 6191889143079517027L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reference;

    private LocalDate dateDebut;

    @Enumerated(EnumType.STRING)
    private ContratType typeContrat;

    private float salaire;

    @JsonIgnore
    @OneToOne(mappedBy = "contrat")
    private Employe employe;
}
