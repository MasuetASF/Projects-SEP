package com.eventoapp.eventoapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventoapp.eventoapp.models.Convidado;
import com.eventoapp.eventoapp.models.Evento;
import com.eventoapp.eventoapp.repository.ConvidadoRepository;
import com.eventoapp.eventoapp.repository.EventoRepository;

import jakarta.validation.Valid;


@Controller
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ConvidadoRepository convidadoRepository;


    @RequestMapping(value="/cadastrarEvento", method=RequestMethod.GET)
    public String form(){
        return "evento/formEvento";
    }

    @RequestMapping(value="/cadastrarEvento", method=RequestMethod.POST)
    public String form(@Valid Evento evento,
                       BindingResult result,
                       RedirectAttributes attributes){
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos");
            return "redirect:/cadastrarEvento";
        }else
        {
            eventoRepository.save(evento);
            attributes.addFlashAttribute("mensagem", "Evento " + evento.getNome() + " cadastrado com sucesso");
            return "redirect:/cadastrarEvento";
        }                
        
    }

    @RequestMapping(value="/eventos")
    public ModelAndView listaEventos(){
        ModelAndView modelAndView = new ModelAndView("index");
        List<Evento> listaEventos = eventoRepository
        .findAll();
        modelAndView.addObject("evento",listaEventos);
        return modelAndView;
    }


    @RequestMapping(value="eventos/{codigo}", method=RequestMethod.GET)
    public ModelAndView detalhesEventoGet(@PathVariable("codigo") long codigo) {
        Evento evento = eventoRepository.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("evento/detalhesEvento");
        mv.addObject("evento", evento);

        List<Convidado> convidados = convidadoRepository.findByEvento(evento);

        mv.addObject("convidados", convidados);

        return mv;
    }

    @RequestMapping(value="eventos/{codigo}", method=RequestMethod.POST)
    public String detalhesEventoPost(@PathVariable("codigo") long codigo, 
                                     @Valid Convidado convidado, 
                                     BindingResult result, 
                                     RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos!");
            return "redirect:/eventos/{codigo}";
        }else{
            Evento evento = eventoRepository.findByCodigo(codigo);
            convidado.setEvento(evento);
            convidadoRepository.save(convidado);
            attributes.addFlashAttribute("mensagem", "Convidado "+ convidado.getNomeConvidado()+" adicionado com sucesso!");
            return "redirect:/eventos/{codigo}";
        }
       
    }

    @RequestMapping("/deletarEvento")
    public String deletarEvento(long codigo) {
        Evento evento = eventoRepository.findByCodigo(codigo);
        eventoRepository.delete(evento);
        return "redirect:/eventos";
    }
    
    @RequestMapping("/deletarConvidado")
    public String deletarConvidado(String rg) {
        Convidado convidado = convidadoRepository.findFirstByRg(rg);
        convidadoRepository.delete(convidado);
        
        Evento evento = convidado.getEvento();
        long codigoLong = evento.getCodigo();
        String codigo = ""+codigoLong;
        return "redirect:/eventos/"+ codigo;
    }

}
