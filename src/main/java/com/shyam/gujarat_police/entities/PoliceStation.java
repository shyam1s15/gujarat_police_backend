package com.shyam.gujarat_police.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PoliceStation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy="policeStation", cascade = CascadeType.PERSIST)
    private List<Police> police;

    @NotEmpty(message = "{validation.name.NotEmpty}")
    private String district;

    @NotEmpty(message = "{validation.name.NotEmpty}")
    private String taluko;

    @Pattern(regexp="(^$|[0-9]{10})", message = "{validation.name.Size}")
    private String contactNumber;

    @NotEmpty(message = "{validation.name.NotEmpty}")
    private String address;

    @NotEmpty(message = "{validation.name.NotEmpty}")
    private String policeStationName;


    @OneToOne
    @JoinColumn(name="headPolice_id")
    private Police headPolice;
}
