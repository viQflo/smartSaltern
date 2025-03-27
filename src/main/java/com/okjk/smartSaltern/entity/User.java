package com.okjk.smartSaltern.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_USER")
@Getter
@Setter


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
    private LocalDateTime updateDate = LocalDateTime.now();

	public User(String userId, String userPw, String userGender, String userDepartment, String userRole,
			LocalDateTime createDate, LocalDateTime updateDate) {
		super();
		this.userId = userId;
		this.userPw = userPw;
		this.userGender = userGender;
		this.userDepartment = userDepartment;
		this.userRole = userRole;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
	
	public User () {
		
		
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return this.userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserGender() {
		return this.userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserDepartment() {
		return this.userDepartment;
	}

	public void setUserDepartment(String userDepartment) {
		this.userDepartment = userDepartment;
	}

	public String getUserRole() {
		return this.userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}
    
    
}
