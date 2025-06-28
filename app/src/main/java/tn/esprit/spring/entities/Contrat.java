package tn.esprit.spring.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private ContractType typeContrat;

    private float salaire;

    @JsonIgnore
    @OneToOne(mappedBy = "contrat")
    private Employe employe;
}
