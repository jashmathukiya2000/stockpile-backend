package com.example.auth.commons;

import com.google.common.io.Resources;

import net.fortuna.ical4j.model.property.Url;

import java.net.URL;

import java.nio.charset.StandardCharsets;


public class FileLoader {

    public static String loadHtmlTemplateOrReturnNull(String name){
        try{
            URL url= Resources.getResource("templates/"+ name+".html");
            return Resources.toString(url, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }
}
