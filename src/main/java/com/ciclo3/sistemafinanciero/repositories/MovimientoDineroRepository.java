package com.ciclo3.sistemafinanciero.repositories;

import com.ciclo3.sistemafinanciero.model.MovimientoDinero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MovimientoDineroRepository extends JpaRepository<MovimientoDinero, Integer> {

    @Query(value ="select * from movimiento_dinero where id_empleado= ?1", nativeQuery = true)
    public abstract ArrayList<MovimientoDinero> findByEmpleado(Integer id);

    //Metodo para filtrar movimientos por empresa
    @Query(value="select * from movimiento_dinero where id_empleado in (select id_empleado from empleado where id_empresa= ?1)", nativeQuery = true)
    public abstract ArrayList<MovimientoDinero> findByEmpresa(Integer id);

    //Metodo para suma de Movimientos
    @Query(value="SELECT Sum (monto) from movimiento_dinero", nativeQuery = true)
    public abstract Long SumaMonto();

    //Metodo para suma de Movimientos empleado
    @Query(value="SELECT Sum (monto) from movimiento_dinero where id_empleado =?1", nativeQuery = true)
    public abstract Long MontoEmpleado(Integer id); //Id empleado

    //Metodo para suma de Movimientos empresa
    @Query(value="select sum(monto) from movimiento_dinero where id_empleado in (select id_empleado from empleado where id_empresa= ?1)", nativeQuery = true)
    public abstract Long MontoEmpresa(Integer id); //Id empresa

}
