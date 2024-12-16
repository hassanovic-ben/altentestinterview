package com.hassan.altensecurity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer product_id;
    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private int price;
    private int quantity;
    private String internalReference;
    private int shellId;
    @Enumerated
    private InventoryStatus inventoryStatus;
    private int rating;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
