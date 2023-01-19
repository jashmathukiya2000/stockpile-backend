package com.example.auth.commons.model;

import com.amazonaws.services.autoscaling.model.NotificationConfiguration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.*;


@Document(collection= "admin_config")
@Data
@AllArgsConstructor
@Component
@Builder
public class AdminConfiguration {
    @Id
    String id;
    String from;
    String username;
    String password;
    Set<String> requiredEmailItems = getRequiredItems();
    String host;
    String port;
    boolean smptAuth;
    boolean starttls;
    String nameRegex;
    String emailRegex;
    String regex;
    String semesterRegex;
    String passwordRegex;
    String mobileNoRegex;
    String CreatedBy;
    String UpdatedBy;
    Date Created;
    Date Updated;
    Set<String> extensions = getExtensionsData();
    Set<String> techAdmins = getTechAdminEmails();
    NotificationConfiguration notificationConfiguration;
    Map<String,String> userImportMappingFields = new LinkedHashMap<>();


    private Set<String> getRequiredItems() {
        Set<String> requiredEmailItems = new HashSet<>();
        requiredEmailItems.add("@");
        return requiredEmailItems;
    }

    private Set<String> getExtensionsData() {
        Set<String> extensions = new HashSet<>();
        extensions.add("gmail.com");
        extensions.add("yahoo.com");
        return extensions;
    }
    private Set<String> getTechAdminEmails() {
        Set<String> emails = new HashSet<>();
        emails.add("dency.g@techroversolutions.com");
        return emails;
    }


    public AdminConfiguration(){

        this.from = "dency.g@techroversolutions.com";
        this.username ="dency.g@techroversolutions.com" ;
        this.password = "Rakshit@05";
        this.host = "smtp.office365.com";
        this.port = "587";
        this.smptAuth = true;
        this.starttls = true;
        this.extensions=getExtensionsData();
        this.nameRegex = "^[a-zA-Z]+$";
        this.emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        this.regex= "^(?=.{1,64}@)[a-z0-9_-]+(\\.[a-z0-9_-]+)*@"
                + "[^-][a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,})$";
        this.semesterRegex = "^[0-8]{1}$";
        this.passwordRegex ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,15}$";
        this.mobileNoRegex="^[0-9]{10}$";
        this.notificationConfiguration=new NotificationConfiguration();
        userImportMappingFields.put("First Name","firstName");
        userImportMappingFields.put("Last Name", "lastName");
        userImportMappingFields.put("Middle Name", "middleName");
        userImportMappingFields.put("Address", "Address");
        userImportMappingFields.put(" City", "city");
        userImportMappingFields.put(" State", "state");
        userImportMappingFields.put("Email", "email");
        userImportMappingFields.put("UserName", "userName");
        userImportMappingFields.put("MobileNo", "mobileNo");

    }
}