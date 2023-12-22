package vn.edu.usth.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "statisticaldata")
public class Statistical {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "replicate")
    private String replicate;
    @Basic
    @Column(name = "chlorophyll")
    private Double chlorophyll;
    @Basic
    @Column(name = "longitude")
    private String longitude;
    @Basic
    @Column(name = "latitude")
    private String latitude;
    @Basic
    @Column(name = "pConc")
    private Double pConc;
    @Basic
    @Column(name = "kConc")
    private Double kConc;
    @Basic
    @Column(name = "nConc")
    private Double nConc;
    @Basic
    @Column(name = "wetWeight")
    private Double wetWeight;
    @Basic
    @Column(name = "driedWeight")
    private Double driedWeight;
    @Basic
    @Column(name = "moiture")
    private Double moiture;
    @Basic
    @Column(name = "digesion")
    private Double digesion;
    @Basic
    @Column(name = "date")
    private String date;
    @Basic
    @Column(name = "subReplicate")
    private String subReplicate;
    @Basic
    @Column(name = "userId")
    private int userId;


}
