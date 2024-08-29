package com.cassio.nicepay.controller.validation;


import static com.cassio.nicepay.util.DocumentUtil.cleanDocument;
import static com.cassio.nicepay.util.DocumentUtil.isCNPJ;
import static com.cassio.nicepay.util.DocumentUtil.isCPF;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DocumentValidator implements ConstraintValidator<Document, String> {

  @Override
  public boolean isValid(String document, ConstraintValidatorContext constraintValidatorContext) {
    document = cleanDocument(document);
    return isCNPJ(document) || isCPF(document);
  }


}
