package com.ciclo3.sistemafinanciero.service;

import com.ciclo3.sistemafinanciero.model.Empleado;
import com.ciclo3.sistemafinanciero.model.MovimientoDinero;
import com.ciclo3.sistemafinanciero.repositories.MovimientoDineroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovimientoDineroService {

    @Autowired
    MovimientoDineroRepository movimientoDineroRepository;

    public List<MovimientoDinero> getAllMovimientos(){ //Metodo que me muestra todos los movimientos sin ningn filtro
        List<MovimientoDinero> movimientosList = new ArrayList<>();
        movimientoDineroRepository.findAll().forEach(movimiento -> movimientosList.add(movimiento));  //Recorremos el iterable que regresa el metodo findAll del Jpa y lo guardamos en la lista creada
        return movimientosList;
    }

    public MovimientoDinero getMovimientoById(Integer id){ //Ver movimientos por id

        return movimientoDineroRepository.findById(id).get();
    }

    public boolean saveOrUpdateMovimiento(MovimientoDinero movimientoDinero){ //Guardar o actualizar elementos
        MovimientoDinero mov=movimientoDineroRepository.save(movimientoDinero);
            if (movimientoDineroRepository.findById(mov.getIdMovimiento())!=null) {
                return true;
            }
            return false;
        }

    public boolean deleteMovimiento(Integer id){ //Eliminar movimiento por id
        movimientoDineroRepository.deleteById(id); //Eliminar usando el metodo que nos ofrece el repositorio
        if(this.movimientoDineroRepository.findById(id).isPresent()){ //Si al buscar el movimiento lo encontramos, no se eliminó (false)
            return false;
        }
        return true; //Si al buscar el movimiento no lo encontramos, si lo eliminò (true)
    }

    public ArrayList<MovimientoDinero> obtenerPorEmpleado(Integer id) { //Obterner teniendo en cuenta el id del empleado
        return movimientoDineroRepository.findByEmpleado(id);
    }

    public ArrayList<MovimientoDinero> obtenerPorEmpresa(Integer id) { //Obtener movimientos teniendo en cuenta el id de la empresa a la que pertencen los empleados que la registraron
        return movimientoDineroRepository.findByEmpresa(id);
    }

}
