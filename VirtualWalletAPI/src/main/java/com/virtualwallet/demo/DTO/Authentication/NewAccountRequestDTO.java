package com.virtualwallet.demo.DTO.Authentication;

public class NewAccountRequestDTO
{
    private String name;
    private String email;
    private String pswd;

    public NewAccountRequestDTO(String name, String email, String pswd)
    {
        this.name = name;
        this.email = email;
        this.pswd = pswd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getEmail()
    {
        return email;
    }
}
