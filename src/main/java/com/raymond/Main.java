package com.raymond;

import sun.net.www.http.HttpClient;

import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String basePath = "C:\\Users\\PeterLei\\Desktop";
        File file = new File(basePath);
        String[] list = file.list();
        ArrayList<String> needToConvertList = new ArrayList<>();
        for (String s : list) {
            if (s.endsWith(".md")) {
                needToConvertList.add(s);
            }
        }
        String temp = basePath + File.separator + needToConvertList.get(0);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(temp)))) {
            String readLine = null;
            while ((readLine = bufferedReader.readLine()) != null) {
                if (readLine.startsWith("![](")){
                    String substring = readLine.substring(4, readLine.length() - 1);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
