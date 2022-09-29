package com.ciclo3.sistemafinanciero.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "email", unique = true)
    private String email;

    @JoinColumn(name = "rol", insertable = false, updatable = false)
    private String rol;

    @Column(name = "password")
    public String password;

    @Column(name = "estado")
    public Boolean estado;

    @Column(name = "fecha_creacion")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaCreacion;

    @Column(name = "fecha_actualizacion")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaActualizacion;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;


}


