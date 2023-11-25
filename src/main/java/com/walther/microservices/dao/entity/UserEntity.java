package com.walther.microservices.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ms_user")
public class UserEntity  extends RepresentationModel<UserEntity>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "age")
    private Integer edad;
    private String email;
    private boolean active;
    private LocalDate birthDay;
    private String cif;
    private String title;
    private String body;

}
