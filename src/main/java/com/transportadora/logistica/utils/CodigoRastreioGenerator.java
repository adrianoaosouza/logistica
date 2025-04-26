package com.transportadora.logistica.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CodigoRastreioGenerator {


    public Object gerar() {
        // Pode personalizar depois, por exemplo com prefixos
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
