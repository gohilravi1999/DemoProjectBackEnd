package com.myProject.model;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private EnumRoles roleName;

	public Role() {

	}

	public Role(EnumRoles roleName) {
		this.roleName = roleName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EnumRoles getRoleName() {
		return roleName;
	}

	public void setRoleName(EnumRoles roleName) {
		this.roleName = roleName;
	}

}
