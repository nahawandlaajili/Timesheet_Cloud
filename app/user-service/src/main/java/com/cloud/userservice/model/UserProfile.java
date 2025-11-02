package com.cloud.userservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; 

    private String email;

    private String phone;

    private String address;

    private int remainingDaysOff = 30;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Department department;

    @Enumerated(EnumType.STRING)
    private JobRole jobRole;

    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    // Manual getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getRemainingDaysOff() {
        return remainingDaysOff;
    }

    public void setRemainingDaysOff(int remainingDaysOff) {
        this.remainingDaysOff = remainingDaysOff;
    }

    // Manual builder implementation
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String email;
        private String phone;
        private String address;
        private int remainingDaysOff = 30;
        private String password;
        private Department department;
        private JobRole jobRole;
        private ContractType contractType;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder remainingDaysOff(int remainingDaysOff) {
            this.remainingDaysOff = remainingDaysOff;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder department(Department department) {
            this.department = department;
            return this;
        }

        public Builder jobRole(JobRole jobRole) {
            this.jobRole = jobRole;
            return this;
        }

        public Builder contractType(ContractType contractType) {
            this.contractType = contractType;
            return this;
        }

        public UserProfile build() {
            UserProfile userProfile = new UserProfile();
            userProfile.id = this.id;
            userProfile.name = this.name;
            userProfile.email = this.email;
            userProfile.phone = this.phone;
            userProfile.address = this.address;
            userProfile.remainingDaysOff = this.remainingDaysOff;
            userProfile.password = this.password;
            userProfile.department = this.department;
            userProfile.jobRole = this.jobRole;
            userProfile.contractType = this.contractType;
            return userProfile;
        }
    }
}