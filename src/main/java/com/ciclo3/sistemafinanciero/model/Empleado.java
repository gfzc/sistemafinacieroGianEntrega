package com.ciclo3.sistemafinanciero.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Integer id_Empleado;

    @Column(name = "id_empresa")
    private Integer id_Empresa;

    //@Column(name = "id_perfil")
    //private String idPerfil;

    @Column (name = "nombre")
    private String nombre;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "rol", nullable = false, length = 20)
    private String rol;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "fecha_actualizacion")
    private Date fechaActualizacion;

    @ManyToOne
    @JoinColumn(name = "id_Empresa", insertable = false, updatable = false)
    private Empresa empresa;

    //@OneToMany(mappedBy = "empleado")
    //private List<MovimientoDinero> movimientoDinero;

    //@OneToOne
    //@JoinColumn(name = "id_perfil", insertable = false, updatable = false)
    //private Perfil perfil;




}


