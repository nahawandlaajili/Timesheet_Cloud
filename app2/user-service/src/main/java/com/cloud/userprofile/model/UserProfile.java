// user-profile-service/src/main/java/com/cloudserprofile/model/UserProfile.java
package com.cloud.userprofile.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Department department;

    @Enumerated(EnumType.STRING)
    private JobRole jobRole;

    @Enumerated(EnumType.STRING)
    private ContractType contractType;
}