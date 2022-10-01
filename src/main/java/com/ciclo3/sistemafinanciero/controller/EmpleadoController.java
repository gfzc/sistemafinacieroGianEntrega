package com.ciclo3.sistemafinanciero.controller;

import com.ciclo3.sistemafinanciero.model.Empleado;
import com.ciclo3.sistemafinanciero.model.Empresa;
import com.ciclo3.sistemafinanciero.service.EmpleadoService;
import com.ciclo3.sistemafinanciero.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        String passEncriptada=passwordEncoder().encode(emple.getPassword());
        emple.setPassword(passEncriptada);
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
        Integer id=emple.getIdEmpleado(); //Saca el id del objeto Empleado
        String passRegistrada=empleadoService.getEmpleadoByID(id).get().getPassword(); //Con ese id se consulta la contraseña almacenada en la BD
        if (!emple.getPassword().equals(passRegistrada)){ //Verificamos que las contraseñas sean diferentes
            String passEncriptada=passwordEncoder().encode(emple.getPassword());
            emple.setPassword(passEncriptada);
        }
        System.out.println(passRegistrada);
        System.out.println(emple.getPassword());
        if (empleadoService.saveOrUpdateEmpleado(emple)) {
            redirectAttributes.addFlashAttribute("mensaje", "updateOK");
            return "redirect:/VerEmpleados";
        }
        redirectAttributes.addFlashAttribute("mensaje", "updateError");
        return "redirect:/EditarEmpleado/" + emple.getIdEmpleado();

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

    //Controlador que lleva a template de acceso denegado
    @RequestMapping(value = "/Denegado")
    public String accesoDenegado(){
        return "accesoDenegado";

    }

    //Metodo para encriptar contraseñas
   @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }




}