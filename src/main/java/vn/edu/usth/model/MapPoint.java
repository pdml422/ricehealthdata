package vn.edu.usth.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "map")
public class MapPoint {
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
    @Column(name = "imageId")
    private Integer imageId;
    @Basic
    @Column(name = "dataId")
    private Integer dataId;

}
