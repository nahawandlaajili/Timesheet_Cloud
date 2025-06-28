package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.Data;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data  // Lombok generates getters/setters for all fields (except final ones)
@NoArgsConstructor
@AllArgsConstructor
public class Departement implements Serializable {

    private static final long serialVersionUID = -357738161698377833L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;   // Lombok will generate getId() and setId() (you can suppress setId if needed)
    private String name;

       // Getter and Setter for id
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

      // Getter and Setter for name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Employe> employes;

    @OneToMany(mappedBy = "departement")
    private List<Mission> missions;

    @ManyToOne
    private Entreprise entreprise;

    public void setEntreprise(Entreprise entreprise) {
    this.entreprise = entreprise;
}
    // If you want to disallow setting ID manually, add this:
    // @Setter(AccessLevel.NONE)
    // private int id;
}
