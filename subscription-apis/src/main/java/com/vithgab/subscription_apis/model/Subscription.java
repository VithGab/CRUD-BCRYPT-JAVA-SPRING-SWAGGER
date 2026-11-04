package com.vithgab.subscription_apis.model;

import jakarta.persistence.*;

@Entity
@Table(name = "subscriptions")
public class Subscription {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String category;

    @Column(nullable = false)
    private double price;

    @Column(name = "payment_day")
    private Integer paymentDay;

    public Subscription() {
    }

    public Subscription(Long id, String name, String category, double price, Integer paymentDay){
        this.name = name;
        this.category = category;
        this.price = price;
        this.paymentDay = paymentDay;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public Integer getPaymentDay() {
        return paymentDay;
    }
    public void setPaymentDay(Integer paymentDay) {
        this.paymentDay = paymentDay;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

}
