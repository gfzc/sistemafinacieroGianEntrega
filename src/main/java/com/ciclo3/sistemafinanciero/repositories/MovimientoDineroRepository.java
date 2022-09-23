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
    @Query(value="select * from movimientos where id_empleado in (select id from empleado where empresa_id= ?1)", nativeQuery = true)
    public abstract ArrayList<MovimientoDinero> findByEmpresa(Integer id);
}
