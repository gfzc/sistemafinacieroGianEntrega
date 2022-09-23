package com.ciclo3.sistemafinanciero.controller;

import com.ciclo3.sistemafinanciero.model.Empleado;
import com.ciclo3.sistemafinanciero.model.Empresa;
import com.ciclo3.sistemafinanciero.model.MovimientoDinero;
import com.ciclo3.sistemafinanciero.service.EmpleadoService;
import com.ciclo3.sistemafinanciero.service.EmpresaService;
import com.ciclo3.sistemafinanciero.service.MovimientoDineroService;
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
public class MovimientoDineroController {
    @Autowired
    MovimientoDineroService movimientoDineroService;
    @Autowired
    EmpleadoService empleadoService;

    //Controller que lleva a ver movimientos
    @GetMapping("/VerMovimientos") //Anotacion que mapea y crea el servicio
    public String verMovimientos(Model model, @ModelAttribute("mensaje") String mensaje) { //Metodo para obtener cadenas de con objeto de clase tipo Model, recibe cualquiercosa y modela segun lo necesitado
        List<MovimientoDinero> listMovimientos = movimientoDineroService.getAllMovimientos(); //Se crea lista de tipo Empleado con el metodo getAll
        model.addAttribute("listMovimientos", listMovimientos); //Sa alimenta objeto model con lista de movimientos
        model.addAttribute("mensaje", mensaje);
        return "verMovimientos"; //LLama a pagina html creada
    }

    @GetMapping("/AgregarMovimiento") //Controller que lleva a template para crear movimiento
    public String nuevoMovimiento(Model model, @ModelAttribute("mensaje") String mensaje) { //Inserta objeto model
        MovimientoDinero movimiento= new MovimientoDinero(); //Crea objeto de tipo Movimiento para crear el nuevo utilizando el constructor vacio
        model.addAttribute("mov", movimiento); //Envia el objeto por medio de model
        model.addAttribute("mensaje", mensaje);
        List<Empleado> listEmpleados = empleadoService.getAllEmpleado(); //Trae la lista de empresas para empleado
        model.addAttribute("emplelist", listEmpleados); //Modela lista de empresas para empleado
        return "agregarMovimiento"; //LLama a pagina html creada

    }

    //Metodo par guardar Movimiento
    @PostMapping("/GuardarMovimiento")
    public String guardarMovimiento(MovimientoDinero mov, RedirectAttributes redirectAttributes) {
        if (movimientoDineroService.saveOrUpdateMovimiento(mov)) {
            redirectAttributes.addFlashAttribute("mensaje", "saveOK");
            return "redirect:/VerMovimientos";
        }
        redirectAttributes.addFlashAttribute("mensaje", "saveError");
        return "redirect:/AgregarMovimientos";
    }

    //Metodo par editar Movimiento
    @GetMapping("/EditarMovimiento/{id}")
    public String editarMovimiento(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje) { //Elimina id que llega en la ruta con @PathVariable
        MovimientoDinero mov = movimientoDineroService.getMovimientoById(id);
        //Crea atributo para el modelo llamado igual que el objeto creado que va al html y llena campos de ese objeto
        model.addAttribute("mov", mov);
        model.addAttribute("mensaje", mensaje);
        List<Empleado> listEmpleados = empleadoService.getAllEmpleado();
        model.addAttribute("emplelist", listEmpleados);
        return "editarMovimiento";
    }


    @PostMapping("/ActualizarMovimiento")
    public String ActualizarMovimiento(@ModelAttribute("mov") MovimientoDinero mov, RedirectAttributes redirectAttributes) {
        if (movimientoDineroService.saveOrUpdateMovimiento(mov)) {
            redirectAttributes.addFlashAttribute("mensaje", "updateOK");
            return "redirect:/VerMovimientos";
        }
        redirectAttributes.addFlashAttribute("mensaje", "updateError");
        return "redirect:/EditarMovimiento/" + mov.getIdMovimiento();

    }

    @GetMapping("/EliminarMovimiento/{id}")
    public String eliminarMovimiento(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        if (movimientoDineroService.deleteMovimiento(id)) {
            redirectAttributes.addFlashAttribute("mensaje", "deleteOK");
            return "redirect:/VerMovimientos";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/VerMovimientos";

    }

}
