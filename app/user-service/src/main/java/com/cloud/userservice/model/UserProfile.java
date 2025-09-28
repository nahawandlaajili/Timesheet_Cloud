// user-profile-service/src/main/java/com/cloud/userservice/model/UserProfile.java
package com.cloud.userservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    public int getRemainingDaysOff() {
        return remainingDaysOff;
    }

    public void setRemainingDaysOff(int remainingDaysOff) {
        this.remainingDaysOff = remainingDaysOff;
    }
}
