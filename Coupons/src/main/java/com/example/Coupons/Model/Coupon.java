package com.example.Coupons.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "coupons")
public class Coupon {

    @Id
    private String id;
    private String provider;
    private String code;
    private String description;
    private String expiryDate;

    public Coupon() {
    }

    public Coupon(String id, String provider, String code, String description, String expiryDate) {
        super();
        this.id = id;
        this.provider = provider;
        this.code = code;
        this.description = description;
        this.expiryDate = expiryDate;
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getProvider() {
        return provider;
    }
    public void setProvider(String provider) {
        this.provider = provider;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}

