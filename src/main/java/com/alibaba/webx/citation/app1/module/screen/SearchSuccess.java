/**
 * ICT NASC
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.alibaba.webx.citation.app1.module.screen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.turbine.Context;

/**
 * 
 * @author xueye.duanxy
 * @version $Id: SearchSuccess.java, v 0.1 2016-1-21 ÏÂÎç4:52:56  Exp $
 */
public class SearchSuccess {

    /**
     * 
     * 
     * @param request
     * @param response
     * @param context
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void execute(HttpServletRequest request, HttpServletResponse response, Context context) {
        String titleStr = request.getParameter("title");

        //search DB
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            System.out.print("Class Not Found Exception");
        }
        String url = "jdbc:mysql://10.61.2.147:3306/wos";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) DriverManager.getConnection(url, "shen", "123456");
            stmt = (Statement) conn.createStatement();
            String sql = "select title,year,journal,wos_id from papers where match(title) against ("
                         + "\"" + titleStr + "\"" + ") limit 10";
            //System.out.println(sql);
            rs = stmt.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //return results
        HttpSession session = request.getSession();
        try {
            List rsList = new ArrayList();
            rs.next();
            int rowIndex = rs.getRow();
            //System.out.println(rowIndex);
            if (rowIndex == 1) {
                rs.previous();
                //System.out.println(rs.getRow());
                while (rs.next()) {
                    List<String> rowList = new ArrayList<String>();
                    rowList.add(rs.getString("title").toUpperCase());
                    rowList.add(rs.getString("year").toUpperCase());
                    rowList.add(rs.getString("journal").toUpperCase());
                    rowList.add(rs.getString("wos_id").toUpperCase());
                    rsList.add(rowList);
                }
                //System.out.println(rsList.size());
                session.setAttribute("resultSet", rsList);
                //response.sendRedirect("searchSuccess.jsp");
                return;
            } else {
                session.setAttribute("message", "No match for input!");
                //response.sendRedirect("searchFailure.jsp");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
