package tn.esprit.spring.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.EmbeddedId;  
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Employe;   
/**
 * Composite primary key for Timesheet entity.
 */
@Embeddable
public class TimesheetPK implements Serializable {

    private static final long serialVersionUID = 5377539445871317492L;

    @Column(name = "idMission", nullable = false)
    private int idMission;

    @Column(name = "idEmploye", nullable = false)
    private int idEmploye;

    @Column(name = "dateDebut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "dateFin", nullable = false)
    private LocalDate dateFin;

    public TimesheetPK() {
        // Default constructor
    }

    public TimesheetPK(int idMission, int idEmploye, LocalDate dateDebut, LocalDate dateFin) {
        this.idMission = idMission;
        this.idEmploye = idEmploye;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public int getIdMission() {
        return idMission;
    }

    public void setIdMission(int idMission) {
        this.idMission = idMission;
    }

    public int getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(int idEmploye) {
        this.idEmploye = idEmploye;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimesheetPK)) return false;
        TimesheetPK that = (TimesheetPK) o;
        return idMission == that.idMission &&
               idEmploye == that.idEmploye &&
               Objects.equals(dateDebut, that.dateDebut) &&
               Objects.equals(dateFin, that.dateFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMission, idEmploye, dateDebut, dateFin);
    }

    @Override
    public String toString() {
        return "TimesheetPK{" +
               "idMission=" + idMission +
               ", idEmploye=" + idEmploye +
               ", dateDebut=" + dateDebut +
               ", dateFin=" + dateFin +
               '}';
    }
}
