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
    public String home(
            @RequestParam(value = "divisaOrigen", defaultValue = "USD") String divisaOrigen,
            @RequestParam(value = "divisaDestino", defaultValue = "USD") String divisaDestino,
            @RequestParam(value = "cantidad", defaultValue = "1") float cantidad,
            Model model) throws Exception {

        // Obtener la tasa de cambio
        float tasaCambio = (float) cambioDivisasService.obtenerTasaDeCambio(divisaOrigen, divisaDestino);
        float resultado =  cantidad * tasaCambio;

        // Redondear el resultado
        resultado = (float) redondear(resultado, 4);

        // Agregar los datos al modelo para Thymeleaf
        model.addAttribute("resultado", resultado);
        model.addAttribute("tasaCambio", tasaCambio);
        model.addAttribute("divisaOrigen", divisaOrigen);
        model.addAttribute("divisaDestino", divisaDestino);
        model.addAttribute("cantidad", cantidad);

        // Retornar la vista que va a mostrar los resultados
        return "index";  // El nombre de la plantilla es "index.html"
    }

    private double redondear(double valor, int decimales) {
        BigDecimal bd = new BigDecimal(valor);
        bd = bd.setScale(decimales, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
