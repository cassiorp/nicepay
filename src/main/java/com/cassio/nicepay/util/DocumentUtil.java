package com.cassio.nicepay.util;

public class DocumentUtil {
  public static String cleanDocument(String document){
    return document.replaceAll("[^\\d]", "");
  }
}
