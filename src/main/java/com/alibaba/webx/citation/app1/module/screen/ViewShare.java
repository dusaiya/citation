/**
 * ICT NASC
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.alibaba.webx.citation.app1.module.screen;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author xueye.duanxy
 * @version $Id: ViewShare.java, v 0.1 2016-1-21 下午4:58:19  Exp $
 */
public class ViewShare {

    private static final String BASH_BEDROOM_DUANXUEYE_TAS_CON_NASC_RUN_SH = "bash /usr/local/tomcat/citation/run.sh";

    public void execute(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub

        String titleStr = request.getParameter("title");
        String titleId = request.getParameter("titleId");
        String citationCount = "2";
        String authorCount = "3";
        List<String[]> shareList = new ArrayList();
        //System.out.println(titleStr);
        //返回脚步输出信息
        /*List<String> outputList = getShOutStr(titleId);
        System.out.println("test sh method: "+outputList.get(0));*/
        //StringList 转换成输出数据 Data List
        /*for(String line : outputList){
            if(line.startsWith("citation")){
                citationCount=line.split("/t")[1];
            }if(line.startsWith("author")){
                authorCount=line.split("/t")[1];
            }else{
                String[] dataLineList =line.split("/t");
                shareList.add(dataLineList);
            }
        }*/
        HttpSession session = request.getSession();
        //session.setAttribute("testSh", outputList.get(0));
        session.setAttribute("title", titleStr);
        session.setAttribute("citationCount", citationCount);
        session.setAttribute("authorCount", authorCount);
        session.setAttribute("shareList", shareList);
        //response.sendRedirect("viewShare.jsp");
    }

    private List<String> getShOutStr(String titleId) {
        List<String> outputList = new ArrayList<String>();
        try {
            Process process = null;
            process = Runtime.getRuntime().exec(BASH_BEDROOM_DUANXUEYE_TAS_CON_NASC_RUN_SH);
            BufferedReader input = new BufferedReader(new InputStreamReader(
                process.getInputStream()));

            String line = "";
            while ((line = input.readLine()) != null) {
                outputList.add(line);
            }

            input.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputList;
    }
}
