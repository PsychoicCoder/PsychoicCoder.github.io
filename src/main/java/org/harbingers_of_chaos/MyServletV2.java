package org.harbingers_of_chaos;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MyServletV2 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("OWNER")!=null){
        resp.setContentType("text/html");
        resp.getWriter().print("<html><head></head><body><h1>Welcome!</h1><p>"+
                getCountCommit(req.getParameter("OWNER"),req.getParameter("REPO"))+"</p></body></html>");
        resp.getWriter().print(getImag(new URL("https://img.shields.io/badge/Count_commits-"+
                getCountCommit(req.getParameter("OWNER"),req.getParameter("REPO"))+"-blue?style=for-the-badge")));
        }
//        else {
//            throw new IllegalStateException("Help, I don't know what to do with this url");
//        }

    }
    private String getImag(URL url ) throws IOException {
                HttpURLConnection http = (HttpURLConnection)url.openConnection();
                StringBuilder textBuilder = new StringBuilder();
                try (Reader reader = new BufferedReader(new InputStreamReader
                        (http.getInputStream(), StandardCharsets.UTF_8))) {
                    int c = 0;
                    while ((c = reader.read()) != -1) {
                        textBuilder.append((char) c);
                    }
                }http.disconnect();
                return  textBuilder.toString();
    }
    private int getCountCommit(String owner, String repo) throws IOException {
        try {
            URL url = new URL("https://api.github.com/repos/"+owner+"/"+repo+"/commits");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonObject = (JSONArray)jsonParser.parse(
                    new InputStreamReader(http.getInputStream(), StandardCharsets.UTF_8));
            http.disconnect();
            return  jsonObject.size();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}