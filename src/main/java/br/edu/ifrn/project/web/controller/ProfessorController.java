package br.edu.ifrn.project.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.project.api.client.dto.ProfessorDTO;
import br.edu.ifrn.project.api.client.resource.ProfessorResource;

@Controller
@RequestMapping("/professor")
public class ProfessorController {
	
	@Autowired
	ProfessorResource resource;
	
	@GetMapping("/adicionar")
	public ModelAndView view (ProfessorDTO professor){
		return new ModelAndView("formprofessor").addObject("professor",professor);
	}
	
	@PostMapping("/criar/atualizar")
	public String createOrUpdate(ProfessorDTO professor,RedirectAttributes atribute){
		
		if(resource.POSTPUTcreateOrUpdate(professor)){
			atribute.addFlashAttribute("mensagem","- Professor atualizado com sucesso!");
			return "redirect:/professor/lista";
		}else{
			atribute.addFlashAttribute("mensagemErro","- Não foi possivel cadastrar o professor");
			view(professor);
		}
		return "redirect:/professor/adicionar";
	}
	
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id,RedirectAttributes atribute){
		if(resource.DELETEdelete(id)){
			atribute.addFlashAttribute("mensagem","- Professor deletado com sucesso!");
			System.out.print("deu certo"+id);
			return "redirect:/professor/lista";
		}else{
			System.out.print("Não está dando certo");
			atribute.addFlashAttribute("mensagemErro","- Não foi possivel deletar o professor!");
			return "redirect:/professor/lista";
		}
	}
	
	@GetMapping("/update/{id}")
	public ModelAndView update(@PathVariable("id") long id,RedirectAttributes atribute){
		List<ProfessorDTO>  listProfessor = resource.GETread();
		ProfessorDTO finByProfessor = null;
		
		for(ProfessorDTO professor : listProfessor){
			if(professor.getId() == id){
				finByProfessor = professor;
			}
		}
		return new ModelAndView("formprofessor").addObject("professor",finByProfessor);
	}
	
	@GetMapping("/lista")
	public ModelAndView read() {
		return new ModelAndView("professores").addObject("professores",resource.GETread());
	}
}
