package com.example.payitforward.controllers;

/*
https://beginnersbook.com/2013/05/http-session/



import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserSession {


    protected void doPost(HttpServletRequest req, HttpServletResponse res)
                            throws ServletException, IOException {

        // create sessions
        HttpSession session = req.getSession();

        // get form variables
        String name = request.getParameter("userName");
        String password = request.getParameter("userPassword");

        // set for variables as session attributes
        session.setAttribute("uName", "ChaitanyaSingh");
        session.setAttribute("uemailId", "myemailid@gmail.com");
        session.setAttribute("uAge", "30");
    }
}

    /* TO GET VALUE FROM SESSION
    String userName = (String) session.getAttribute("uName");
    String userEmailId = (String) session.getAttribute("uemailId");
    String userAge = (String) session.getAttribute("uAge");
     */
