package com.AppRH.AppRH.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppRH.AppRH.models.Ferias;
import com.AppRH.AppRH.models.Funcionario;
import com.AppRH.AppRH.repository.FeriasRepository;
import com.AppRH.AppRH.repository.FuncionarioRepository;

@Controller
public class FeriasController {

	@Autowired
	private FeriasRepository vr;
	
	@Autowired
	private FuncionarioRepository cr;

	// GET que chama o FORM que cadastra ferias
	@RequestMapping("/cadastrarFerias")
	public String form() {
		return "funcionario/form-ferias";
	}

	// POST que cadastra as ferias
	@RequestMapping(value = "/cadastrarFerias", method = RequestMethod.POST)
	public String form(@Valid Ferias ferias, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/cadastrarFerias";
		}

		vr.save(ferias);
		attributes.addFlashAttribute("mensagem", "Férias cadastrada com sucesso!");
		return "redirect:/cadastrarFerias";
	}

	// GET que lista as ferias
	@RequestMapping("/ferias")
	public ModelAndView listaFerias() {
		ModelAndView mv = new ModelAndView("funcionario/lista-ferias");
		Iterable<Ferias> ferias = vr.findAll();
		mv.addObject("ferias", ferias);
		return mv;
	}

	// GET que mostra os detalhes das ferias
	@RequestMapping("/ferias/{codigo}")
	public ModelAndView detalhesFerias(@PathVariable("codigo") long codigo) {
		Ferias ferias = vr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("funcionario/detalhes-ferias");
		mv.addObject("ferias", ferias);

		Iterable<Funcionario> funcionarios = cr.findByFerias(ferias);
		mv.addObject("funcionarios", funcionarios);

		return mv;

	}

	// GET que deleta as ferias
	@RequestMapping("/deletarFerias")
	public String deletarFerias(long codigo) {
		Ferias ferias = vr.findByCodigo(codigo);
		vr.delete(ferias);
		return "redirect:/ferias";
	}

	// Métodos que atualizam as ferias
	// GET que chama o formulário de edição das ferias
	@RequestMapping("/editar-ferias")
	public ModelAndView editarFerias(long codigo) {
		Ferias ferias = vr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("funcionario/update-ferias");
		mv.addObject("ferias", ferias);
		return mv;
	}

	// POST do FORM que atualiza as ferias
	@RequestMapping(value = "/editar-ferias", method = RequestMethod.POST)
	public String updateFerias(@Valid Ferias ferias, BindingResult result, RedirectAttributes attributes) {
		vr.save(ferias);
		attributes.addFlashAttribute("success", "Férias alteradas com sucesso!");

		return "redirect:/ferias/";
	}

}
