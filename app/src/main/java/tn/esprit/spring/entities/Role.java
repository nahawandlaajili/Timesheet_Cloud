package tn.esprit.spring.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

public enum Role{
	CHEF_DEPARTEMENT, ADMINISTRATEUR, INGENIEUR, TECHNICIEN
}
