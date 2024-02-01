package com.codeworld.motorsportapp.dto;

import com.codeworld.motorsportapp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleDto {
    private Integer id;
    private String name;
    private String image;
    private String description;
    private double price;
    private LocalDate createdAt;
    private List<Integer> likes = new ArrayList<>();
    private User user;
}
