package com.shyam.gujarat_police.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Police {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty(message = "{validation.name.NotEmpty}")
    @Length(min = 2,max = 70, message = "{validation.name.Size}")
    private String fullName;
    private String buckleNumber;

    @Pattern(regexp="(^$|[0-9]{10})", message = "{validation.name.Size}")
    private String number;

    @NotNull(message = "{validation.name.NotEmpty}")
    @Min(value = 15, message = "{validation.name.Size}")
    @Max(value = 90, message = "{validation.name.Size}")
    private int age;

    @NotEmpty(message = "{validation.name.NotEmpty}")
    @Length(min = 2,max = 50, message = "{validation.name.Size}")
    private String district;

    @NotEmpty(message = "{validation.name.NotEmpty}")
    @Length(min = 2,max = 10, message = "{validation.name.Size}")
    private String gender;
    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "policeStation_id")
    @NotNull(message = "{validation.name.NotEmpty}")
    private PoliceStation policeStation;
    @ManyToOne
    @JoinColumn(name = "designation_id")
//    @JsonIgnore
    @NotNull(message = "{validation.name.NotEmpty}")
    private Designation designation;
}
