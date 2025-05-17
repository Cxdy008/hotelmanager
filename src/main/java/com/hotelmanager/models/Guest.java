package com.hotelmanager.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "guests")
@NoArgsConstructor
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(nullable = false, length = 50)
    private String phone;

    @Column(nullable = false, unique = true)
    private String document;
}
