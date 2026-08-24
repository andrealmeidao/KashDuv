package com.Kash.KashDuv.validation;

import com.Kash.KashDuv.entity.Categoria;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoriaValidaValidator implements ConstraintValidator<CategoriaValida, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Categoria.valida(value);
    }
}