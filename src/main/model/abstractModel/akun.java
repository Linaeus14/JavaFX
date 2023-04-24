package main.model.abstractModel;

import main.model.dataClass;

public abstract class akun extends dataClass {
    
    protected String userid;
    protected String pass;
    protected String nama;
    protected String email;
    protected String status;
    
    public abstract void setUserid(String userid);
    public abstract void setPass(String pass);
    public abstract void setNama(String nama);
    public abstract void setEmail(String email);
    public abstract void setStatus(String status);

    public akun() {
        
    }

    public akun(String userid) {
        this.userid = userid;
    }

    public akun(String userid, String pass, String nama, String email, String status) {

        this.userid = userid;
        this.pass = pass;
        this.nama = nama;
        this.email = email;
        this.status = status;
    }

    public String getUserid() {
        return this.userid;
    }

    public String getPass() {
        return this.pass;
    }

    public String getNama() {
        return this.nama;
    }
    
    public String getEmail() {
        return this.email;
    }

    public String getStatus() {
        return this.status;
    }
}
