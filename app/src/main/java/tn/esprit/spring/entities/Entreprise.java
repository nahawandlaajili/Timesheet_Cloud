package tn.esprit.spring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;           
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.spring.entities.Departement;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entreprise implements Serializable {

    private static final long serialVersionUID = 3152690779535828408L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String raisonSocial;

    @OneToMany(mappedBy = "entreprise",
               cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
               fetch = FetchType.EAGER)
    private List<Departement> departements = new ArrayList<>();

    public Entreprise(String name, String raisonSocial) {
        this.name = name;
        this.raisonSocial = raisonSocial;
    }

    public void addDepartement(Departement department) {
        department.setEntreprise(this);
        this.departements.add(department);
    }
}
