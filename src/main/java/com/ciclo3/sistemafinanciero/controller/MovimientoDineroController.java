package com.ciclo3.sistemafinanciero.controller;

import com.ciclo3.sistemafinanciero.model.Empleado;
import com.ciclo3.sistemafinanciero.model.Empresa;
import com.ciclo3.sistemafinanciero.model.MovimientoDinero;
import com.ciclo3.sistemafinanciero.repositories.MovimientoDineroRepository;
import com.ciclo3.sistemafinanciero.service.EmpleadoService;
import com.ciclo3.sistemafinanciero.service.EmpresaService;
import com.ciclo3.sistemafinanciero.service.MovimientoDineroService;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Security;
import java.util.List;

@Controller
public class MovimientoDineroController {
    @Autowired
    MovimientoDineroService movimientoDineroService;
    @Autowired
    EmpleadoService empleadoService;

    @Autowired
    MovimientoDineroRepository movimientoDineroRepository;

    //Controller que lleva a ver movimientos
    @GetMapping("/VerMovimientos") //Anotacion que mapea y crea el servicio
    public String verMovimientos(@RequestParam(value = "pagina", required = false, defaultValue = "1") int PageNumber,
                                 @RequestParam(value = "medida", required = false, defaultValue = "5") int medida,
                                 Model model, @ModelAttribute("mensaje") String mensaje) {
        Page<MovimientoDinero> paginaMovimiento = movimientoDineroRepository.findAll(PageRequest.of(PageNumber,medida));
        //List<MovimientoDinero> listMovimientos = movimientoDineroService.getAllMovimientos();
        model.addAttribute("listMovimientos", paginaMovimiento.getContent());
        model.addAttribute("paginas", new int[paginaMovimiento.getTotalPages()]);
        model.addAttribute("paginaActual", PageNumber);
        model.addAttribute("mensaje", mensaje);
        Long sumaMonto = movimientoDineroService.obtenerSumaMonto();
        model.addAttribute("sumaMonto", sumaMonto); //Envia suma de montos a plantilla HTML
        return "verMovimientos"; //LLama a pagina html creada
    }

    @GetMapping("/AgregarMovimiento") //Controller que lleva a template para crear movimiento
    public String nuevoMovimiento(Model model, @ModelAttribute("mensaje") String mensaje) { //Inserta objeto model
        MovimientoDinero movimiento= new MovimientoDinero(); //Crea objeto de tipo Movimiento para crear el nuevo utilizando el constructor vacio
        model.addAttribute("mov", movimiento); //Envia el objeto por medio de model
        model.addAttribute("mensaje", mensaje);
        //List<Empleado> listEmpleados = empleadoService.getAllEmpleado(); //Trae la lista de empresas para empleado
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String correo=auth.getName();
        Integer idEmpl=movimientoDineroService.IdPorCorreo(correo);
        model.addAttribute("idEmpl", idEmpl); //Modela lista de empresas para empleado
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
    //Filtrar movimientos por Empleado
    @GetMapping("/Empleado/{id}/Movimientos")
    public String movEmpleado(@PathVariable("id")Integer id, Model model){
        List<MovimientoDinero> listMovimientos = movimientoDineroService.obtenerPorEmpleado(id);
        model.addAttribute("listMovimientos", listMovimientos);
        Long sumaMonto= movimientoDineroService.MontoEmpleado(id);
        model.addAttribute("sumaMonto", sumaMonto);
        return "verMovimientos"; //Llama al html

    }
    //Filtrar movimientos por Empresa
    @GetMapping("/Empresa/{id}/Movimientos")
    public String movEmpresa(@PathVariable("id")Integer id, Model model){
        List<MovimientoDinero> listMovimientos = movimientoDineroService.obtenerPorEmpresa(id);
        model.addAttribute("listMovimientos", listMovimientos);
        Long sumaMonto= movimientoDineroService.MontoEmpresa(id);
        model.addAttribute("sumaMonto", sumaMonto);
        return "verMovimientos"; //Llama al html

    }

}

