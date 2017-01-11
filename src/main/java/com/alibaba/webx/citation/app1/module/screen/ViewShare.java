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

import org.json.JSONArray;
import org.json.JSONObject;

import com.alibaba.citrus.turbine.Context;

/**
 * 
 * @author xueye.duanxy
 * @version $Id: ViewShare.java, v 0.1 2016-1-21 下午4:58:19  Exp $
 */
public class ViewShare {

    /**执行指令*/
    private static final String BASH_BEDROOM_DUANXUEYE_TAS_CON_NASC_RUN_SH = "bash /usr/local/tomcat/citation/run.sh";

    /**
     * 
     * 
     * @param request
     * @param context
     */
    public void execute(HttpServletRequest request, Context context) {
        try {

            String titleStr = request.getParameter("title");
            String titleId = request.getParameter("titleId");
            String citationCount = "2";
            String authorCount = "3";
            List<String[]> shareList = new ArrayList();
            //返回脚步输出信息
            List<String> outputList = getShOutStr(titleId);
            context.put("outputResult", outputList.get(0));
            //StringList 转换成输出数据 Data List
            /**for(String line : outputList){
                if(line.startsWith("citation")){
                    citationCount=line.split("/t")[1];
                }if(line.startsWith("author")){
                    authorCount=line.split("/t")[1];
                }else{
                    String[] dataLineList =line.split("/t");
                    shareList.add(dataLineList);
                }
            }**/
            /**一下为模拟运行的数据**/
            List<String> strList = new ArrayList<String>();
            strList.add("Citations   6");
            strList.add("Author\t4");
            strList.add("Name\tDASH, UN\tDAS, BB\tBISWAL, UK\tPANDA, T");
            strList.add("4\t1987\t0.509551\t0.163483\t0.163483\t0.163483");
            strList.add("6\t1996\t0.482749\t0.172417\t0.172417\t0.172417");
            strList.add("6\t2002\t0.507319\t0.164227\t0.164227\t0.164227");
            strList.add("total       0.507319    0.164227    0.164227    0.164227");
            dataMsg(context, strList);

        } catch (Exception e) {
            context.put("message", e.getMessage());
        }
    }

    /**
     * 
     * @param context
     * @param strList
     * @throws NumberFormatException
     */
    private void dataMsg(Context context, List<String> strList) throws NumberFormatException {
        int authorCnt = 0;
        List<String> nameList = new ArrayList<String>();
        List<JSONArray> yearDataList = new ArrayList<JSONArray>();
        for (String str : strList) {
            if (str.startsWith("Citations")) {
                continue;
            } else if (str.startsWith("Author")) {
                String[] countStr = str.split("\t");
                //authorCnt = Integer.parseInt(countStr);
                continue;
            } else if (str.startsWith("Name") || str.startsWith("total")) {
                continue;
            } else {
                //yearDataList.add(str);
                String[] yearData = str.split("\t");

                List<JSONObject> linedata = new ArrayList<JSONObject>();
                for (String data : yearData) {
                    JSONObject ob = new JSONObject();
                    ob.put("a", data);
                    linedata.add(ob);
                }
                JSONArray dataList = new JSONArray(linedata);
                yearDataList.add(dataList);
            }
        }
        JSONArray dataList = new JSONArray(yearDataList);
        context.put("dataList", dataList);
        /**
        int authorCnt = 0;
        List<String> nameList = new ArrayList<String>();
        List<String> yearDataList = new ArrayList<String>();
        for (String str : strList) {
            if (str.startsWith("Citations")) {
                context.put("citation", str.split("\t")[1]);
                continue;
            } else if (str.startsWith("Author")) {
                String countStr = str.split("\t")[1];
                context.put("authorCount", countStr);
                authorCnt = Integer.parseInt(countStr);
                continue;
            } else if (str.startsWith("Name")) {
                String[] nameStrList = str.split("\t");
                for (String name : nameStrList) {
                    if ("Name".equals(name)) {
                        continue;
                    }
                    nameList.add(name);
                }
                context.put("nameList", nameList);
                continue;
            } else if (str.startsWith("total")) {
                List<String> totalList = new ArrayList<String>(authorCnt);
                String[] totalStrList = str.split("\t");
                for (String total : totalStrList) {
                    if ("total".equals(total)) {
                        continue;
                    }
                    totalList.add(total);
                }
                context.put("totalList", totalList);
                continue;
            } else if (authorCnt == 0) {
                continue;
            } else {
                yearDataList.add(str);
            }
           
        }
        **/
    }

    /**
     * 获取运行代码数据
     * 
     * @param titleId
     * @return
     * @throws Exception 
     */
    private List<String> getShOutStr(String titleId) throws Exception {
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
            throw e;
        }
        return outputList;
    }
}
