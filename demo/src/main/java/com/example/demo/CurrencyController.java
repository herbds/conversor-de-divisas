package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Controller
public class CurrencyController {

    @Autowired
    private CambioDivisasService cambioDivisasService;

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }

    @GetMapping("/convertir")
    public String convertir(
            @RequestParam("divisaOrigen") String divisaOrigen,
            @RequestParam("divisaDestino") String divisaDestino,
            @RequestParam("cantidad") float cantidad,
            Model model) throws Exception {

        float tasaCambio = (float) cambioDivisasService.obtenerTasaDeCambio(divisaOrigen, divisaDestino);
        float resultado = cantidad * tasaCambio;

        resultado = (float) redondear(resultado, 3);


        // Datos usando framework aportados al html

        model.addAttribute("resultado", resultado);
        model.addAttribute("tasaCambio", tasaCambio);
        model.addAttribute("divisaOrigen", divisaOrigen);
        model.addAttribute("divisaDestino", divisaDestino);
        model.addAttribute("cantidad", cantidad);

        // Redirigir con parámetros para que JavaScript los recupere
        return "redirect:/?resultado=" + resultado + "&tasaCambio=" + tasaCambio;
    }

    // Metodo redondear
    private double redondear(double valor, int decimales) {
        BigDecimal bd = new BigDecimal(valor);
        bd = bd.setScale(decimales, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
