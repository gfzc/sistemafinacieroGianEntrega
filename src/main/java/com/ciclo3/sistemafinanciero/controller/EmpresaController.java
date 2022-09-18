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
    public String verEmpresas(Model model) { //Metodo para obtener cadenas de empresas con objeto de clase tipo Model, recibe cualquiercosa y modela segun lo necesitado
        List<Empresa> listEmpresas = empresaService.getAllEmpresas(); //Se crea lista de tipo Empresas con el metodo getAllEmpresas
        model.addAttribute("emplist", listEmpresas); //Sa alimenta objeto model con lista de empresas
        return "verEmpresas"; //LLama a pagina html creada
    }

    //Metodo par para agregar Empresas
    @GetMapping({"/AgregarEmpresa"}) //Anotacion que mapea y crea el servicio AgregarEmpresa obtener informacion
    public String nuevaEmpresa(Model model) { //Inserta objeto model
        Empresa nuevaEmp = new Empresa(); //Crea objeto de tipo Empresa para crear la nueva empresa utilizando el constructor vacio
        model.addAttribute("nuevaEmp", nuevaEmp); //Envia el objeto por medio de model
        return "agregarEmpresas"; //LLama a pagina html creada

    }

    //Metodo par guardar Empresas
    @PostMapping("/GuardarEmpresa")
    public String guardarEmpresa(Empresa nuevaEmp, RedirectAttributes redirectAttributes) {
        if (empresaService.saveOrUpdateEmpresa(nuevaEmp) == true) {
            return "redirect:/VerEmpresas";
        }
        return "redirect:/AgregarEmpresa";
    }

    //Metodo par editar Empresas
    @GetMapping("/EditarEmpresa/{id}")
    public String editarEmpresa(Model model, @PathVariable Long id) { //Elimina id que llega en la ruta con @PathVariable
        Empresa editEmp = empresaService.getEmpresaById(id);
        //Crea atributo para el modelo llamado igual que el objeto creado que va al html y llena campos de ese objeto
        model.addAttribute("editEmp", editEmp);
        return "editarEmpresa";
    }

    //Metodo para actualizar Empresas
    @PostMapping("/ActualizarEmpresa")
    public String ActualizarEmpresa(@ModelAttribute("actEmpresa") Empresa actEmpresa) {
        if (empresaService.saveOrUpdateEmpresa(actEmpresa)) {
            return "redirect:/VerEmpresas";
        }
        return "redirect:/EditarEmpresa";

    }

    @GetMapping("/EliminarEmpresa/{id}")
    public String eliminarEmpresa(@PathVariable Long id) {
        try {
            empresaService.deleteEmpresa(id);
            return "redirect:/VerEmpresas";
        }catch(Exception e){
            return "redirect:/VerEmpresas";

        }



    }


}
