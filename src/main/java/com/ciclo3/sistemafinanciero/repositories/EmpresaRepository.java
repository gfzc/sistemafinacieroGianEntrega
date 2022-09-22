package com.ciclo3.sistemafinanciero.repositories;

import com.ciclo3.sistemafinanciero.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


    @Repository
    public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {


    }

