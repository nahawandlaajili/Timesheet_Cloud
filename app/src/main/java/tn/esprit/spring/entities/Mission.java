package tn.esprit.spring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;   
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mission implements Serializable {

    private static final long serialVersionUID = -5369734855993305723L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    @ManyToOne
    private Departement departement;

    @OneToMany(mappedBy = "mission")
    private List<Timesheet> timesheets;

    public Mission(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
// This class represents a Mission entity in the system, which is associated with a Departement and can have multiple Timesheets.
// It includes fields for the mission's ID, name, description, and relationships with the Departement and Timesheet entities.
// The class is annotated with JPA annotations to define its persistence behavior,
// and it uses Lombok annotations to generate boilerplate code like getters, setters, and constructors.
// The serialVersionUID is defined for serialization compatibility. The class also includes a constructor for creating a Mission with just a name and description, allowing for easier instantiation without needing to set all fields.