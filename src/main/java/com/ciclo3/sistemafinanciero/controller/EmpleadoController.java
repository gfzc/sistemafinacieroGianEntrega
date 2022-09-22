package com.ciclo3.sistemafinanciero.controller;

import com.ciclo3.sistemafinanciero.model.Empleado;
import com.ciclo3.sistemafinanciero.model.Empresa;
import com.ciclo3.sistemafinanciero.service.EmpleadoService;
import com.ciclo3.sistemafinanciero.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;
    @Autowired
    EmpresaService empresaService;

    //Metodo para ver Empleado
    @GetMapping("/VerEmpleados") //Anotacion que mapea y crea el servicio VerEmpleado
    public String verEmpleados(Model model, @ModelAttribute("mensaje") String mensaje) { //Metodo para obtener cadenas de empleados con objeto de clase tipo Model, recibe cualquiercosa y modela segun lo necesitado
        List<Empleado> listEmpleados = empleadoService.getAllEmpleado(); //Se crea lista de tipo Empleado con el metodo getAllEmleado
        model.addAttribute("emplelist", listEmpleados); //Sa alimenta objeto model con lista de empleados
        model.addAttribute("mensaje", mensaje);
        return "verEmpleados"; //LLama a pagina html creada
    }

    //Metodo par para agregar Empleado
    @GetMapping("/AgregarEmpleado") //Anotacion que mapea y crea el servicio AgregarEmpresa obtener informacion
    public String nuevoEmpleado(Model model, @ModelAttribute("mensaje") String mensaje) { //Inserta objeto model
        Empleado emple = new Empleado(); //Crea objeto de tipo Empresa para crear la nueva empresa utilizando el constructor vacio
        model.addAttribute("emple", emple); //Envia el objeto por medio de model
        model.addAttribute("mensaje", mensaje);
        List<Empresa> listEmpresas = empresaService.getAllEmpresas(); //Trae la lista de empresas para empleado
        model.addAttribute("emprelist", listEmpresas); //Modela lista de empresas para empleado
        return "agregarEmpleado"; //LLama a pagina html creada

    }

    //Metodo par guardar Empleado
    @PostMapping("/GuardarEmpleado")
    public String guardarEmpleado(Empleado emple, RedirectAttributes redirectAttributes) {
        if (empleadoService.saveOrUpdateEmpleado(emple)) {
            redirectAttributes.addFlashAttribute("mensaje", "saveOK");
            return "redirect:/VerEmpleados";
        }
        redirectAttributes.addFlashAttribute("mensaje", "saveError");
        return "redirect:/AgregarEmpleado";
    }


    //Metodo par editar Empleado
    @GetMapping("/EditarEmpleado/{id}")
    public String editarEmpleado(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje) { //Elimina id que llega en la ruta con @PathVariable
        Empleado emple = empleadoService.getEmpleadoByID(id).get();
        //Crea atributo para el modelo llamado igual que el objeto creado que va al html y llena campos de ese objeto
        model.addAttribute("emple", emple);
        model.addAttribute("mensaje", mensaje);
        List<Empresa> listEmpresas = empresaService.getAllEmpresas(); //Trae la lista de empresas para empleado
        model.addAttribute("emprelist", listEmpresas); //Modela lista de empresas para empleado
        return "editarEmpleado";
    }

    @PostMapping("/ActualizarEmpleado")
    public String ActualizarEmpleado(@ModelAttribute("emple") Empleado emple, RedirectAttributes redirectAttributes) {
        if (empleadoService.saveOrUpdateEmpleado(emple)) {
            redirectAttributes.addFlashAttribute("mensaje", "updateOK");
            return "redirect:/VerEmpleados";
        }
        redirectAttributes.addFlashAttribute("mensaje", "updateError");
        return "redirect:/EditarEmpleado";

    }

    @GetMapping("/EliminarEmpleado/{id}")
    public String eliminarEmpleado(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        if (empleadoService.deleteEmpleado(id)==true) {
            redirectAttributes.addFlashAttribute("mensaje", "deleteOK");
            return "redirect:/VerEmpleados";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/VerEmpleados";

    }

    @GetMapping("/Empresa/{id}/Empleados")
    public String verEmpleadosEmpresa(@PathVariable("id") Integer id, Model model){
        List<Empleado> listEmpleados = empleadoService.empleadoPorEmpresa(id);
        model.addAttribute("emplelist", listEmpleados);
        return "verEmpleados";
    }
}