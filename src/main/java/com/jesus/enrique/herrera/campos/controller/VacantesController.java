package com.jesus.enrique.herrera.campos.controller;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jesus.enrique.herrera.campos.model.Vacante;
import com.jesus.enrique.herrera.campos.service.IntServiceCategorias;
import com.jesus.enrique.herrera.campos.service.IntServiceVacantes;

import comjesus.enrique.herrera.campos.utileria.Utileria;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {
	
	@Autowired
	private IntServiceVacantes serviceVacantes;
	
	@Autowired
	private IntServiceCategorias serviceCategorias;
	
	@ModelAttribute
	public void setGenero(Model model) {
		model.addAttribute("categorias",serviceCategorias.obtenerCategorias());
		model.addAttribute("vacantes", serviceVacantes.obtenerTodas());
		
	}
	
	@GetMapping("/mostrar")
	public String mostrar(@RequestParam("id") Integer id, Model model) {
		Vacante vac = new  Vacante();
		vac = serviceVacantes.buscarPorId(id);
		model.addAttribute("vacante", vac);
		return "vacantes/detalleVacantes";
	}
	
	//aqui se implemento databinding que es implementacion 
		@PostMapping("/guardar2")
		public String guardar2(Vacante vacante, BindingResult result, RedirectAttributes attributes, @RequestParam("archivoImagen") MultipartFile multiPart) {
			if (result.hasErrors()) {
				for (ObjectError error: result.getAllErrors()){
					System.out.println("Ocurrio un error: "+ error.getDefaultMessage());
					}
				return "vacantes/formVacante";
			}
			if (!multiPart.isEmpty()) {
				String ruta = "C:\\empleos\\img-vacantes\\"; // Windows
				String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
				if (nombreImagen != null){
				vacante.setImagen(nombreImagen);
				}}
			Vacante cat = new Vacante();
			int id = serviceVacantes.obtenerTodas().size();
			Vacante c = serviceVacantes.obtenerTodas().get(id-1);
			System.out.println(id);
			vacante.setId(++id);
			vacante.setCategoria(serviceCategorias.buscarPorId(vacante.getCategoria().getId()));
			System.out.println(vacante);
			/*c.setId(++id);
			c.setNombre(vacante.getNombre());
			c.setDescripcion(descripcion);
			cat.setFecha(fecha);
			cat.setSalario(salario);
			*/
			attributes.addFlashAttribute("msg", "Registro Guardado");
			serviceVacantes.guardar(vacante);
			return "redirect:/vacantes/index";
		}
	/*
	@PostMapping("/guardar")
	public String guardar(@RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion, 
			@RequestParam("categoria") String categoria, @RequestParam("estatus") String estatus, 
			@RequestParam("fecha") LocalDate fecha, @RequestParam("salario") Double salario, @RequestParam("destacado") Integer destacado,
			@RequestParam("imagen") String imagen, @RequestParam("detalles") String detalles) {
		
		Vacante vac = new Vacante();
		int id = serviceVacantes.obtenerTodas().size();
		Vacante v = serviceVacantes.obtenerTodas().get(id-1);
		System.out.println(id);
		vac.setId(++id);
		vac.setNombre(nombre);
		vac.setDescripcion(descripcion);
		vac.setFecha(fecha);
		vac.setSalario(salario);
		vac.setDestacado(destacado);
		vac.setEstatus(estatus);
		vac.setImagen(imagen);
		vac.setDetalles(detalles);
		serviceVacantes.guardar(vac);
		return "redirect:/vacantes/index";
	}*/
	
	@GetMapping("/nueva")
	public String nuevaVacante(Vacante vacante, Model model) {
		model.addAttribute("categorias", serviceCategorias.obtenerCategorias());
		return "vacantes/formVacante";
	}
	
	@GetMapping("/eliminar")
	public String eliminar(@RequestParam("id") int idVacante) {
		serviceVacantes.eliminar(idVacante);
		return "redirect:/vacantes/index";
	}
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Vacante> vacantes = new LinkedList<Vacante>();
		vacantes = serviceVacantes.obtenerTodas();
		for(Vacante v : vacantes) {
			System.out.println(v);
		}
		//model.addAttribute("vacantes", vacantes);
		model.addAttribute("total", serviceVacantes.obtenerTodas().size());
		return "vacantes/listaVacantes";
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException{
				setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
			}
			
			@Override
			public String getAsText() throws IllegalArgumentException {
				return DateTimeFormatter.ofPattern("dd-MM-yyyy").format((LocalDate) getValue());
			}
		});
	}
}
