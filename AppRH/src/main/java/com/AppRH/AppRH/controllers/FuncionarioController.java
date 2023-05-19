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

import com.AppRH.AppRH.models.Dependente;
import com.AppRH.AppRH.models.Ferias;
import com.AppRH.AppRH.models.Funcionario;
import com.AppRH.AppRH.repository.DependenteRepository;
import com.AppRH.AppRH.repository.FeriasRepository;
import com.AppRH.AppRH.repository.FuncionarioRepository;

@Controller
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository fr;

	@Autowired
	private DependenteRepository dr;
	
	@Autowired
	private FeriasRepository er;

	// GET que chama o form para cadastrar funcionários
	@RequestMapping("/cadastrarFuncionario")
	public String form() {
		return "funcionario/form-funcionario";
	}
	
	// GET que chama o form para gestão de funcionários
	@RequestMapping("/gestaoFuncionarios")
	public String dash() {
		return "funcionario/gestao-funcionarios";
	}

	// POST que cadastra funcionários
	@RequestMapping(value = "/cadastrarFuncionario", method = RequestMethod.POST)
	public String form(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/cadastrarFuncionario";
		}

		fr.save(funcionario);
		attributes.addFlashAttribute("mensagem", "Funcionário cadastrado com sucesso!");
		return "redirect:/cadastrarFuncionario";
	}

	// GET que lista funcionários
	@RequestMapping("/funcionarios")
	public ModelAndView listaFuncionarios() {
		ModelAndView mv = new ModelAndView("funcionario/lista-funcionario");
		Iterable<Funcionario> funcionarios = fr.findAll();
		mv.addObject("funcionarios", funcionarios);
		return mv;
	}

	// GET que lista dependentes e detalhes dos funcionário
	@RequestMapping("/detalhes-funcionario/{id}")
	public ModelAndView detalhesFuncionario(@PathVariable("id") long id) {
		Funcionario funcionario = fr.findById(id);
		ModelAndView mv = new ModelAndView("funcionario/detalhes-funcionario");
		mv.addObject("funcionarios", funcionario);

		// lista de dependentes baseada no id do funcionário
		Iterable<Dependente> dependentes = dr.findByFuncionario(funcionario);
		mv.addObject("dependentes", dependentes);
		
//		// lista de ferias baseada no id do funcionário
//		Iterable<Ferias> ferias = er.findByFuncionario(funcionario);
//		mv.addObject("ferias", ferias);
		
		return mv;

	}

	// POST que adiciona dependentes
	@RequestMapping(value="/detalhes-funcionario/{id}", method = RequestMethod.POST)
	public String detalhesFuncionarioPost(@PathVariable("id") long id, Dependente dependentes, BindingResult result,
			RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/detalhes-funcionario/{id}";
		}
		
		if(dr.findByCpf(dependentes.getCpf()) != null) {
			attributes.addFlashAttribute("mensagem_erro", "CPF duplicado");
			return "redirect:/detalhes-funcionario/{id}";
		}
		
		Funcionario funcionario = fr.findById(id);
		dependentes.setFuncionario(funcionario);
		dr.save(dependentes);
		attributes.addFlashAttribute("mensagem", "Dependente adicionado com sucesso");
		return "redirect:/detalhes-funcionario/{id}";
		
	}
	
	//GET que deleta funcionário
	@RequestMapping("/deletarFuncionario")
	public String deletarFuncionario(long id) {
		Funcionario funcionario = fr.findById(id);
		fr.delete(funcionario);
		return "redirect:/funcionarios";
		
	}
	
	// Métodos que atualizam funcionário
	// GET que chama o FORM de edição do funcionário
	@RequestMapping("/editar-funcionario")
	public ModelAndView editarFuncionario(long id) {
		Funcionario funcionario = fr.findById(id);
		ModelAndView mv = new ModelAndView("funcionario/update-funcionario");
		mv.addObject("funcionario", funcionario);
		return mv;
	}
	
	// POST que atualiza o funcionário
	@RequestMapping(value = "/editar-funcionario", method = RequestMethod.POST)
	public String updateFuncionario(@Valid Funcionario funcionario,  BindingResult result, RedirectAttributes attributes){
		
		fr.save(funcionario);
		attributes.addFlashAttribute("successs", "Funcionário alterado com sucesso!");
		
		long idLong = funcionario.getId();
		String id = "" + idLong;
		return "redirect:/detalhes-funcionario/" + id;
		
	}
	
	// GET que deleta dependente
	@RequestMapping("/deletarDependente")
	public String deletarDependente(String cpf) {
		Dependente dependente = dr.findByCpf(cpf);
		
		Funcionario funcionario = dependente.getFuncionario();
		String codigo = "" + funcionario.getId();
		
		dr.delete(dependente);
		return "redirect:/detalhes-funcionario/" + codigo;
	
	}
	
	
//	// POST que adiciona ferias
//	@RequestMapping(value="/detalhes-funcionario/{id}", method = RequestMethod.POST)
//	public String detalhesFeriasPost(@PathVariable("id") long id, Ferias ferias, BindingResult result,
//			RedirectAttributes attributes) {
//		
//		if(result.hasErrors()) {
//			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
//			return "redirect:/detalhes-funcionario/{id}";
//		}
//		
//		if(dr.findByCpf(ferias.getCpf()) != null) {
//			attributes.addFlashAttribute("mensagem_erro", "CPF duplicado");
//			return "redirect:/detalhes-funcionario/{id}";
//		}
//		
//		Funcionario funcionario = fr.findById(id);
//		ferias.setFuncionario(funcionario);
//		er.save(ferias);
//		attributes.addFlashAttribute("mensagem", "Dependente adicionado com sucesso");
//		return "redirect:/detalhes-funcionario/{id}";
//		
//	}
	
//	// GET que deleta ferias
//	@RequestMapping("/deletarFerias")
//	public String deletarFerias(String cpf) {
//		Ferias ferias = er.findByCpf(cpf);
//		
//		Funcionario funcionario = ferias.getFuncionario();
//		String codigo = "" + funcionario.getId();
//		
//		er.delete(ferias);
//		return "redirect:/detalhes-funcionario/" + codigo;
//	
//	}
}
