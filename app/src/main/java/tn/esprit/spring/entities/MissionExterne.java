package tn.esprit.spring.entities;

import java.math.BigDecimal;
import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import tn.esprit.spring.entities.Mission;   
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * Entity representing an external mission.
 */
@Entity
public class MissionExterne extends Mission {

    private static final long serialVersionUID = -3046278688391172322L;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Billing email cannot be blank")
    @Column(name = "email_facturation", nullable = false, length = 100)
    private String emailFacturation;

    @Positive(message = "Daily rate must be positive")
    @Column(name = "taux_journalier_moyen", nullable = false, precision = 10, scale = 2)
    private BigDecimal tauxJournalierMoyen;

    public MissionExterne() {
        super("bla","bla");
    }

    public MissionExterne(String name, String description, String emailFacturation, BigDecimal tauxJournalierMoyen) {
        super(name, description);
        this.emailFacturation = emailFacturation;
        this.tauxJournalierMoyen = tauxJournalierMoyen;
    }

    public String getEmailFacturation() {
        return emailFacturation;
    }

    public void setEmailFacturation(String emailFacturation) {
        this.emailFacturation = emailFacturation;
    }

    public BigDecimal getTauxJournalierMoyen() {
        return tauxJournalierMoyen;
    }

    public void setTauxJournalierMoyen(BigDecimal tauxJournalierMoyen) {
        this.tauxJournalierMoyen = tauxJournalierMoyen;
    }

    @Override
    public String toString() {
        return "MissionExterne{" +
                "emailFacturation='" + emailFacturation + '\'' +
                ", tauxJournalierMoyen=" + tauxJournalierMoyen +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MissionExterne)) return false;
        if (!super.equals(o)) return false;
        MissionExterne that = (MissionExterne) o;
        return Objects.equals(emailFacturation, that.emailFacturation) &&
                Objects.equals(tauxJournalierMoyen, that.tauxJournalierMoyen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), emailFacturation, tauxJournalierMoyen);
    }
}
