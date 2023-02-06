package com.cojeans.osiopso.entity.closet;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @AllArgsConstructor @NoArgsConstructor
@Builder
@ToString
public class Color {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
