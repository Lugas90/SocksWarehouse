package com.example.sockswarehouse.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Socks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accounting;
    private String color;
    private Integer cottonPart;
    private int quantity;

    public Socks(String color, Integer cottonPart, int quantity) {
        this.color = color;
        this.cottonPart = cottonPart;
        this.quantity = quantity;
    }

    public Socks() {
    }

    public Long getAccounting() {
        return accounting;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getCottonPart() {
        return cottonPart;
    }

    public void setCottonPart(Integer cottonPart) {
        if (cottonPart < 0 || cottonPart > 100) {
            throw new IllegalArgumentException("unacceptable cotton content");
        }
        this.cottonPart = cottonPart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("unacceptable quantity");
        }
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socks socks = (Socks) o;
        return quantity == socks.quantity && Objects.equals(accounting, socks.accounting) && Objects.equals(color, socks.color) && Objects.equals(cottonPart, socks.cottonPart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accounting, color, cottonPart, quantity);
    }
}
