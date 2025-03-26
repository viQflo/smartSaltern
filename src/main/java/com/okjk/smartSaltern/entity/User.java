package com.okjk.smartSaltern.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "USER_ID", length = 30, nullable = false)
    private String userId;

    @Column(name = "USER_PW", length = 255, nullable = false)
    private String userPw;

    @Column(name = "USER_GENDER", length = 10, nullable = false)
    private String userGender;

    @Column(name = "USER_DEPARTMENT", length = 30, nullable = false)
    private String userDepartment;

    @Column(name = "USER_ROLE", length = 20, nullable = false)
    private String userRole = "USER";

    @Column(name = "CREATE_DATE", nullable = false)
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "UPDATE_DATE", nullable = false)
    private LocalDateTime updateDate = LocalDateTime.of(0000, 1, 1, 0, 0);
}
