package com.cassio.nicepay.util;

import java.util.InputMismatchException;

public class DocumentUtil {
  public static String cleanDocument(String document){
    return document.replaceAll("[^\\d]", "");
  }
  public static boolean isCPF(String cpf) {
    if (cpf == null || cpf.isEmpty() || cpf.length() != 11) {
      return false;
    }

    if (cpf.matches("(\\d)\\1{10}")) {
      return false;
    }

    try {
      int[] digits = {10, 9, 8, 7, 6, 5, 4, 3, 2};
      int sum = 0;
      for (int i = 0; i < 9; i++) {
        sum += (cpf.charAt(i) - '0') * digits[i];
      }

      int digit1 = 11 - (sum % 11);
      if (digit1 > 9) {
        digit1 = 0;
      }

      digits = new int[]{11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
      sum = 0;
      for (int i = 0; i < 10; i++) {
        sum += (cpf.charAt(i) - '0') * digits[i];
      }

      int digit2 = 11 - (sum % 11);
      if (digit2 > 9) {
        digit2 = 0;
      }

      return (cpf.charAt(9) - '0' == digit1) && (cpf.charAt(10) - '0' == digit2);
    } catch (InputMismatchException e) {
      return false;
    }
  }
  public static boolean isCNPJ(String cnpj) {
    if (cnpj == null || cnpj.isEmpty() || cnpj.length() != 14) {
      return false;
    }

    if (cnpj.matches("(\\d)\\1{13}")) {
      return false;
    }

    try {
      int[] digits = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
      int sum = 0;
      for (int i = 0; i < 12; i++) {
        sum += (cnpj.charAt(i) - '0') * digits[i];
      }

      int digit1 = 11 - (sum % 11);
      if (digit1 > 9) {
        digit1 = 0;
      }

      digits = new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
      sum = 0;
      for (int i = 0; i < 13; i++) {
        sum += (cnpj.charAt(i) - '0') * digits[i];
      }

      int digit2 = 11 - (sum % 11);
      if (digit2 > 9) {
        digit2 = 0;
      }

      return (cnpj.charAt(12) - '0' == digit1) && (cnpj.charAt(13) - '0' == digit2);
    } catch (InputMismatchException e) {
      return false;
    }
  }
}
