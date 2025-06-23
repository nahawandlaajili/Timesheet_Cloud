package tn.esprit.spring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public void addDepartement(Departement departement) {
        departement.setEntreprise(this);
        this.departements.add(departement);
    }
}
