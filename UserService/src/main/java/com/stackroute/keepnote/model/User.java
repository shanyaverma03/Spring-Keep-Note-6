package com.stackroute.keepnote.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/*
 * Please note that this class is annotated with @Document annotation
 * @Document identifies a domain object to be persisted to MongoDB.
 *  */
@Document
public class User {

	/*
	 * This class should have five fields (userId,userName,
	 * userPassword,userMobile,userAddedDate). Out of these five fields, the field
	 * userId should be annotated with @Id (This annotation explicitly specifies the document
	 * identifier). This class should also contain the getters and setters for the
	 * fields, along with the no-arg , parameterized constructor and toString
	 * method.The value of userAddedDate should not be accepted from the user but
	 * should be always initialized with the system date.
	 */
	@Id
	private  String userId;
	private  String userName;
	private String userPassword;
	private String userMobile;
	private Date userAddedDate;
	public User() {
	}
	public User(String userId, String userName, String userPassword, String userMobile, Date userAddedDate) {
		this.userId= userId;
		this.userName= userName;
		this.userPassword= userPassword;
		this.userMobile= userMobile;
		this.userAddedDate= userAddedDate;
	}
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId= userId;
	}
	public void setUserName(String userName) {
		this.userName= userName;
	}
	public String getUserPassword() {
		return this.userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword= userPassword;
	}
	public String getUserMobile() {
		return this.userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile= userMobile;
	}
	public void setUserAddedDate(Date date) {
		this.userAddedDate=date;
	}


}
