
package com.ciclo3.sistemafinanciero.service;

import com.ciclo3.sistemafinanciero.model.Empleado;
import com.ciclo3.sistemafinanciero.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    @Autowired
    EmpleadoRepository empleadoRepository;

    //Metodo que retorna lista de empleados con metodos heredados del jpa Repository
    public List<Empleado> getAllEmpleado() {
        List<Empleado> empleadoList = new ArrayList<>();
        empleadoRepository.findAll().forEach(Empleado -> empleadoList.add(Empleado)); //Recorre iterable que regresa metodo finAll y lo guarda en lista empresa
        return empleadoList;
    }

    public ArrayList<Empleado> obtenerPorEmpresa(Integer id){
        return empleadoRepository.findByEmpresa(id);
    }

    //Metodo que trae objeto tipo Empleado por id
    public Optional<Empleado> getEmpleadoByID(Integer id){

        return empleadoRepository.findById(id);
    }

    //Busca empleado por empresa
    public ArrayList<Empleado>empleadoPorEmpresa(Integer id){
        return empleadoRepository.findByEmpresa(id);

    }


    //Metodo que guarda o actualiza objeto tipo empleado
    public boolean saveOrUpdateEmpleado(Empleado empleado){
        Empleado emple = empleadoRepository.save(empleado);
        if (empleadoRepository.findById(emple.getIdEmpleado())!=null) {
            return true;
        }
        return false;
    }

    //Metodo que elimina objeto tipo empleado
    public boolean deleteEmpleado(Integer id){
        empleadoRepository.deleteById(id);

        if (this.empleadoRepository.findById(id).isPresent()){
            return false;
        }
        return true;
    }

}
