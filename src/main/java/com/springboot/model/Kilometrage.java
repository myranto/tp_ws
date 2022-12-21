package com.springboot.model;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kilometrage")
public class Kilometrage  {
    @Id
    @Column(name = "idavion")
    private int idavion;
    @Column(name = "date_Kil")
    private Date date;
    @Column(name = "debutKM")
    private double debut;
    @Column(name = "finKM")
    private double fin;
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getDebut() {
        return debut;
    }

    public void setDebut(double debut) {
        this.debut = debut;
    }

    public double getFin() {
        return fin;
    }

    public void setFin(double fin) {
        this.fin = fin;
    }


}