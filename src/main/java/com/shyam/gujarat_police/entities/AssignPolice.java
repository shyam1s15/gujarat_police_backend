package com.shyam.gujarat_police.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AssignPolice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "police_id")
    private Police police;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "point_id")
    private Point point;

    @NotNull
    private Date assignedDate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "event_id")
    private Event event;
}
