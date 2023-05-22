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

import com.AppRH.AppRH.models.Acessos;
import com.AppRH.AppRH.models.Beneficios;
import com.AppRH.AppRH.models.Dependente;
import com.AppRH.AppRH.models.Funcionario;
import com.AppRH.AppRH.repository.AcessosRepository;
import com.AppRH.AppRH.repository.BeneficiosRepository;
import com.AppRH.AppRH.repository.DependenteRepository;
import com.AppRH.AppRH.repository.FuncionarioRepository;

@Controller
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository fr;

	@Autowired
	private DependenteRepository dr;
	
	@Autowired
	private AcessosRepository rr;
	
	@Autowired
	private BeneficiosRepository br;

	// GET que chama o form para cadastrar funcionários
	@RequestMapping("/cadastrarFuncionario")
	public String form() {
		return "funcionario/form-funcionario";
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
	
	// GET que chama o form para gestão de funcionários
	@RequestMapping("/gestaoFuncionarios")
	public String dash() {
		return "funcionario/gestao-funcionarios";
	}

	// GET que lista funcionários
	@RequestMapping("/funcionarios")
	public ModelAndView listaFuncionarios() {
		ModelAndView mv = new ModelAndView("funcionario/lista-funcionario");
		Iterable<Funcionario> funcionarios = fr.findAll();
		mv.addObject("funcionarios", funcionarios);
		return mv;
	}
	
	// GET que lista acessos
	@RequestMapping("/acessos")
	public ModelAndView listaAcessos() {
		ModelAndView mv = new ModelAndView("funcionario/lista-acessos");
		Iterable<Funcionario> funcionarios = fr.findAll();
		mv.addObject("funcionarios", funcionarios);
		return mv;
	}
	
	// GET que lista acessos e detalhes dos funcionário
	@RequestMapping("/detalhes-acessos/{id}")
	public ModelAndView detalhesAcessos(@PathVariable("id") long id) {
		Funcionario funcionario = fr.findById(id);
		ModelAndView mv = new ModelAndView("funcionario/detalhes-acessos");
		mv.addObject("funcionarios", funcionario);

		// lista de acessos baseada no id do funcionário
		Iterable<Acessos> acessos = rr.findByFuncionario(funcionario);
		mv.addObject("acessos", acessos);
		
		return mv;
	}
	
	// GET que lista beneficios
	@RequestMapping("/beneficios")
	public ModelAndView listaBeneficios() {
		ModelAndView mv = new ModelAndView("funcionario/lista-beneficios");
		Iterable<Funcionario> funcionarios = fr.findAll();
		mv.addObject("funcionarios", funcionarios);
		return mv;
	}
	
	// GET que lista beneficios e detalhes dos funcionário
	@RequestMapping("/detalhes-beneficios/{id}")
	public ModelAndView detalhesBeneficios(@PathVariable("id") long id) {
		Funcionario funcionario = fr.findById(id);
		ModelAndView mv = new ModelAndView("funcionario/detalhes-beneficios");
		mv.addObject("funcionarios", funcionario);

		// lista de beneficios baseada no id do funcionário
		Iterable<Beneficios> beneficios = br.findByFuncionario(funcionario);
		mv.addObject("beneficios", beneficios);
		
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
		attributes.addFlashAttribute("mensagem", "Dependente adicionado com sucesso!");
		return "redirect:/detalhes-funcionario/{id}";
		
	}
	
	// POST que adiciona acessos
	@RequestMapping(value="/detalhes-acessos/{id}", method = RequestMethod.POST)
	public String detalhesAcessosPost(@PathVariable("id") long id, Acessos acessos, BindingResult result,
			RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/detalhes-acessos/{id}";
		}
		

		Funcionario funcionario = fr.findById(id);
		acessos.setFuncionario(funcionario);
		rr.save(acessos);
		attributes.addFlashAttribute("mensagem", "Acesso adicionado com sucesso!");
		return "redirect:/detalhes-acessos/{id}";
		
	}
	
	// POST que adiciona beneficios
	@RequestMapping(value="/detalhes-beneficios/{id}", method = RequestMethod.POST)
	public String detalhesBeneficiosPost(@PathVariable("id") long id, Beneficios beneficios, BindingResult result,
			RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/detalhes-beneficios/{id}";
		}
		

		Funcionario funcionario = fr.findById(id);
		beneficios.setFuncionario(funcionario);
		br.save(beneficios);
		attributes.addFlashAttribute("mensagem", "Benefício adicionado com sucesso!");
		return "redirect:/detalhes-beneficios/{id}";
		
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

		return "redirect:/funcionarios";
		
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
	
	// GET que deleta acessos
	@RequestMapping("/deletarAcessos")
	public String deletarAcessos(String cpf) {
		Acessos acessos = rr.findByCpf(cpf);
		
		Funcionario funcionario = acessos.getFuncionario();
		String codigo = "" + funcionario.getId();
		
		rr.delete(acessos);
		return "redirect:/detalhes-acessos/" + codigo;
	
	}
	
	// GET que deleta beneficios
	@RequestMapping("/deletarBeneficios")
	public String deletarBeneficios(String cpf) {
		Beneficios beneficios = br.findByCpf(cpf);
		
		Funcionario funcionario = beneficios.getFuncionario();
		String codigo = "" + funcionario.getId();
		
		br.delete(beneficios);
		return "redirect:/detalhes-beneficios/" + codigo;
	
	}
	

}
