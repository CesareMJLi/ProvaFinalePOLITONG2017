package com.OCS.Dao.model;

import java.io.Serializable;

public class Client implements Serializable{
	private static final long serialVersionUID = 1L;
    private int id;// ���
    private String username;// �û���
    private String password;// ����
    private String email;// ��������
    
    //��ȡid����
    public int getId() {
        return id;
    }
    //����id����
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}