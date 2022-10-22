package com.shyam.gujarat_police.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty(message = "{validation.name.NotEmpty}")
    private String taluka;

    @NotEmpty(message = "{validation.name.NotEmpty}")
    private String district;

    @NotEmpty(message = "{validation.name.NotEmpty}")
    private String pointName;

    @JsonIgnore
    @OneToMany(mappedBy = "point", cascade = CascadeType.PERSIST)
    private List<AssignPolice> assignPolice;
}
