package vn.edu.usth.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "imagedata")
public class Image {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "path")
    private String path;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "date")
    private String date;
    @Basic
    @Column(name = "userId")
    private int userId;

}
