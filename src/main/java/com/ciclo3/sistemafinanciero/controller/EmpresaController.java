package com.ciclo3.sistemafinanciero.controller;

import com.ciclo3.sistemafinanciero.model.Empresa;
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
public class EmpresaController {
    @Autowired //Inyeccion de metodos con services
    EmpresaService empresaService; //Creo objeto de tipo EmpresaService para usar metodos del servicio creado

    //Metodo para ver Empresas
    @GetMapping({"/", "/VerEmpresas"}) //Anotacion que mapea y crea el servicio VerEmpresas
    public String verEmpresas(Model model, @ModelAttribute("mensaje") String mensaje) { //Metodo para obtener cadenas de empresas con objeto de clase tipo Model, recibe cualquiercosa y modela segun lo necesitado
        List<Empresa> listEmpresas = empresaService.getAllEmpresas(); //Se crea lista de tipo Empresas con el metodo getAllEmpresas
        model.addAttribute("emplist", listEmpresas); //Sa alimenta objeto model con lista de empresas
        model.addAttribute("mensaje",mensaje);
        return "verEmpresas"; //LLama a pagina html creada
    }

    //Metodo par para agregar Empresas
    @GetMapping({"/AgregarEmpresa"}) //Anotacion que mapea y crea el servicio AgregarEmpresa obtener informacion
    public String nuevaEmpresa(Model model, @ModelAttribute("mensaje") String mensaje) { //Inserta objeto model
        Empresa nuevaEmp = new Empresa(); //Crea objeto de tipo Empresa para crear la nueva empresa utilizando el constructor vacio
        model.addAttribute("nuevaEmp", nuevaEmp); //Envia el objeto por medio de model
        model.addAttribute("mensaje",mensaje);
        return "agregarEmpresas"; //LLama a pagina html creada

    }

    //Metodo par guardar Empresas
    @PostMapping("/GuardarEmpresa")
    public String guardarEmpresa(Empresa nuevaEmp, RedirectAttributes redirectAttributes) {
        if (empresaService.saveOrUpdateEmpresa(nuevaEmp) == true) {
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
            return "redirect:/VerEmpresas";
        }
        redirectAttributes.addFlashAttribute("mensaje","saveError");
        return "redirect:/AgregarEmpresa";
    }

    //Metodo par editar Empresas
    @GetMapping("/EditarEmpresa/{id}")
    public String editarEmpresa(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje) { //Elimina id que llega en la ruta con @PathVariable
        Empresa editEmp = empresaService.getEmpresaById(id);
        //Crea atributo para el modelo llamado igual que el objeto creado que va al html y llena campos de ese objeto
        model.addAttribute("editEmp", editEmp);
        model.addAttribute("mensaje", mensaje);
        return "editarEmpresa";
    }

    //Metodo para actualizar Empresas
    @PostMapping("/ActualizarEmpresa")
    public String ActualizarEmpresa(@ModelAttribute("actEmpresa") Empresa actEmpresa, RedirectAttributes redirectAttributes) {
        if (empresaService.saveOrUpdateEmpresa(actEmpresa)) {
            redirectAttributes.addFlashAttribute("mensaje","updateOK");
            return "redirect:/VerEmpresas";
        }
        redirectAttributes.addFlashAttribute("mensaje","updateError");
        return "redirect:/EditarEmpresa";

    }

    @GetMapping("/EliminarEmpresa/{id}")
    public String eliminarEmpresa(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
            if (empresaService.deleteEmpresa(id)==true) {
            redirectAttributes.addFlashAttribute("mensaje", "deleteOK");
            return "redirect:/VerEmpresas";
        }
            redirectAttributes.addFlashAttribute("mensaje", "deleteError");
            return "redirect:/VerEmpresas";

    }


}
