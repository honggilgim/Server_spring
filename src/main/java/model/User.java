package model;

import model.audit.DateAudit;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }),
		@UniqueConstraint(columnNames = { "email" }) })
public class User extends DateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long UserCode; //UserCode

	@NaturalId
	@NotBlank
	@Size(max = 40)
	@Email
	private String ID; // ID를 이메일 형식으로 받아오는게 좋을 것 같아서 그렇게 설정

	@NotBlank
	@Size(max = 100)
	private String password; // PW

	@NotBlank
	@Size(max = 15)
	private String Nick; // NICK

	@NotBlank
	@Size(max = 15)
	private int UserNum; // 유저 생년월일

	@NotBlank
	@Size(max = 15)
	private int UserTell; // 유저 전화번호

	@NotBlank
	@Size(max = 30)
	private String UserName; // 유저 이름


	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User() {

	}

	public User(String ID, String password, String Nick, int UserNum, int UserTell, String UserName) {
		this.ID = ID;
		this.password = password;
		this.Nick = Nick;
		this.UserNum = UserNum;
		this.UserTell = UserTell;
		this.UserName = UserName;
	}

	public Long getUserCode() {
		return UserCode;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setUserCode(Long UserCode) {
		this.UserCode = UserCode;
	}

	public String getNick() {
		return Nick;
	}

	public void setNick(String Nick) {
		this.Nick = Nick;
	}

	public int getUserNum() {
		return UserNum;
	}

	public void setUserNum(int UserNum) {
		this.UserNum = UserNum;
	}

	public int getUserTell() {
		return UserTell;
	}

	public void setUserTell(int UserTell) {
		this.UserTell = UserTell;
	}

	public void setUserName(String UserName) {
		this.UserName = UserName;
	}

	public String getUserName() {return UserName; }

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
