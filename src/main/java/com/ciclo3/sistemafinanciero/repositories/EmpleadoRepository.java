package com.ciclo3.sistemafinanciero.repositories;

import com.ciclo3.sistemafinanciero.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    @Query(value="SELECT * FROM empleado where id_empresa= ?1", nativeQuery=true)
    public abstract ArrayList<Empleado> findByEmpresa(Integer id);


}
