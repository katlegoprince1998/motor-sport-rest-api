package com.codeworld.motorsportapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String image;
    private String description;
    private double price;
    private LocalDate createdAt;
    private List<Integer> likes = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
