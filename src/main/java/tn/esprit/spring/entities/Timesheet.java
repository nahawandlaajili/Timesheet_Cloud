package tn.esprit.spring.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Entity representing a timesheet record which links an employee to a mission with validation status.
 */
@Entity
public class Timesheet implements Serializable {

    private static final long serialVersionUID = 3876346912862238239L;

    @EmbeddedId
    private TimesheetPK timesheetPK;

    // idMission is both primary key and foreign key
    @ManyToOne
    @JoinColumn(name = "idMission", referencedColumnName = "id", insertable = false, updatable = false)
    private Mission mission;

    // idEmploye is both primary key and foreign key
    @ManyToOne
    @JoinColumn(name = "idEmploye", referencedColumnName = "id", insertable = false, updatable = false)
    private Employe employe;

    @NotNull
    @Column(name = "is_valide", nullable = false)
    private boolean isValide;

    public Timesheet() {
        // Default constructor
    }

    public Timesheet(TimesheetPK timesheetPK, Mission mission, Employe employe, boolean isValide) {
        this.timesheetPK = timesheetPK;
        this.mission = mission;
        this.employe = employe;
        this.isValide = isValide;
    }

    public TimesheetPK getTimesheetPK() {
        return timesheetPK;
    }

    public void setTimesheetPK(TimesheetPK timesheetPK) {
        this.timesheetPK = timesheetPK;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public boolean isValide() {
        return isValide;
    }

    public void setValide(boolean isValide) {
        this.isValide = isValide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Timesheet)) return false;
        Timesheet timesheet = (Timesheet) o;
        return isValide == timesheet.isValide &&
                Objects.equals(timesheetPK, timesheet.timesheetPK) &&
                Objects.equals(mission, timesheet.mission) &&
                Objects.equals(employe, timesheet.employe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timesheetPK, mission, employe, isValide);
    }

    @Override
    public String toString() {
        return "Timesheet{" +
                "timesheetPK=" + timesheetPK +
                ", mission=" + mission +
                ", employe=" + employe +
                ", isValide=" + isValide +
                '}';
    }
}
