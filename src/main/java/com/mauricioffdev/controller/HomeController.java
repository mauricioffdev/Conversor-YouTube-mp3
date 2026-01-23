package com.mauricioffdev.controller;

import com.mauricioffdev.service.ConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private ConverterService converterService;

    @GetMapping("/") // Acessou http://localhost:8080/
    public String index() {
        return "index"; // Abre o arquivo index.html
    }

    @PostMapping("/converter") // Quando clicar no botão do formulario
    public String converter(@RequestParam String url,
                            @RequestParam String quality,
                            Model model) {

        // Chama seu serviço antigo
        converterService.downloadAndConvert(url.trim(), quality);

        // Devolve uma mensagem para a tela
        model.addAttribute("mensagem", "✅ Sucesso! O vídeo " + url + " foi baixado na pasta 'downloads'.");

        return "index";
    }
}