package com.dsf.escalade.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtRequest implements Serializable {
   private static final long serialVersionUID = 5926468583005150707L;
   private String login;
   private String password;

   //need default constructor for JSON Parsing
   public JwtRequest() {
   }

   public JwtRequest(String login, String password) {
      this.setLogin(login);
      this.setPassword(password);
   }
}