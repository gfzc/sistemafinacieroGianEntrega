package com.ciclo3.sistemafinanciero.model;

//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa")
    private Integer idEmpresa;

    @Column (name = "nombre", unique = true)
    private String nombre;

    @Column (name = "nit", unique = true)
    private String nit;

    @Column (name = "direccion")
    private String direccion;

    @Column (name = "telefono")
    private String telefono;

    @Column(name = "fecha_creacion")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fechaCreacion;

    @Column(name = "fecha_actualizacion")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fechaActualizacion;

    /*@OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<Empleado> empleado;*/

 /*@OneToMany(mappedBy = "empresa")
  private List<Transaccion> transacciones;*/


}
