package vn.edu.usth.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "map")
public class Map {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "X")
    private int x;
    @Basic
    @Column(name = "Y")
    private int y;
    @Basic
    @Column(name = "label")
    private String label;
    @Basic
    @Column(name = "imageId")
    private Integer imageId;

}
