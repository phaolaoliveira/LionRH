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

import com.AppRH.AppRH.models.Candidato;
import com.AppRH.AppRH.models.Ferias;
import com.AppRH.AppRH.models.Vaga;
import com.AppRH.AppRH.repository.CandidatoRepository;
import com.AppRH.AppRH.repository.FeriasRepository;

@Controller
public class FeriasController {

	@Autowired
	private FeriasRepository vr;
	
	@Autowired
	private CandidatoRepository cr;

	// GET que chama o FORM que cadastra vaga
	@RequestMapping("/cadastrarFerias")
	public String form() {
		return "funcionario/form-ferias";
	}

	// POST que cadastra a vaga
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

	// GET que lista as vagas
	@RequestMapping("/ferias")
	public ModelAndView listaFerias() {
		ModelAndView mv = new ModelAndView("funcionario/lista-ferias");
		Iterable<Ferias> ferias = vr.findAll();
		mv.addObject("ferias", ferias);
		return mv;
	}

	// GET que mostra os detalhes da vaga e os candidatos
	@RequestMapping("/ferias/{codigo}")
	public ModelAndView detalhesFerias(@PathVariable("codigo") long codigo) {
		Ferias ferias = vr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("funcionario/detalhes-vaga");
		mv.addObject("ferias", ferias);

		Iterable<Candidato> candidatos = cr.findByFerias(ferias);
		mv.addObject("candidatos", candidatos);

		return mv;

	}

	// GET que deleta a vaga
	@RequestMapping("/deletarFerias")
	public String deletarFerias(long codigo) {
		Ferias ferias = vr.findByCodigo(codigo);
		vr.delete(ferias);
		return "redirect:/ferias";
	}

	// Métodos que atualizam vaga
	// GET que chama o formulário de edição da vaga
	@RequestMapping("/editar-ferias")
	public ModelAndView editarFerias(long codigo) {
		Ferias ferias = vr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("funcionario/update-ferias");
		mv.addObject("ferias", ferias);
		return mv;
	}

	// POST do FORM que atualiza a vaga
	@RequestMapping(value = "/editar-ferias", method = RequestMethod.POST)
	public String updateFerias(@Valid Ferias ferias, BindingResult result, RedirectAttributes attributes) {
		vr.save(ferias);
		attributes.addFlashAttribute("success", "Vaga alterada com sucesso!");

		long codigoLong = ferias.getCodigo();
		String codigo = "" + codigoLong;
		return "redirect:/ferias/" + codigo;
	}

}
