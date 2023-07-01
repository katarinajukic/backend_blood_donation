package dev.example.final_donations.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "donations")
public class Donation {
    @Id
    private String id;

    private int ordinalNumber;
    private Date date;
    private String problems;
    private String userId;

    public Donation() {
    }

    public Donation(int ordinalNumber, Date date, String problems, String userId) {
        this.ordinalNumber = ordinalNumber;
        this.date = date;
        this.problems = problems;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public int getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(int ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getProblems() {
        return problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Donation [id=" + id + ", ordinalNumber=" + ordinalNumber + ", date=" + date + ", problems=" + problems + "]";
    }
}
