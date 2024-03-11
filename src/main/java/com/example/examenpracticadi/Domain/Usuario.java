package com.example.examenpracticadi.Domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "informacioncliente")
public class Usuario implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;
    @Column(name = "sexo")
    private String sexo;
    @Column(name = "peso")
    private Double peso;
    @Column(name = "edad")
    private String edad;
    @Column(name = "talla")
    private Double talla;
    @Column(name = "tipoActividad")
    private String tipoActividad;
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "GER")
    private Double GER;
    @Column(name = "GET")
    private Double GET;
}
