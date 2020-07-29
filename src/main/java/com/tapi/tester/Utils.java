package com.tapi.tester;

import java.util.Random;

import com.sforce.soap.tooling.Error;
import com.sforce.soap.tooling.SaveResult;

public class Utils {
  /**
  * Prints the results from crud tests.
  * @param sr - SaveResult array.
  * @param operation - Name of the operation executed to get SaveResults.
  */
  protected static void printResults(SaveResult[] sr, String operation) {
    System.out.println("-------------------");
    if (sr.length > 0) {
      for (SaveResult osr : sr) {
        if (osr.getSuccess()) {
          System.out.println(operation + ": success, record id = " + osr.getId());
        }
        if (osr.getErrors().length > 0) {
          for (Error oe : osr.getErrors()) {
            System.out.println(operation + ": had errors, message = " + oe.getMessage());
          }
        }
      }
    }
    System.out.println("-------------------");
    System.out.println("");
  }

  protected static String getSaltString() {
    String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    StringBuilder salt = new StringBuilder();
    Random rnd = new Random();
    while (salt.length() < 18) { // length of the random string.
      int index = (int) (rnd.nextFloat() * SALTCHARS.length());
      salt.append(SALTCHARS.charAt(index));
    }
    String saltStr = salt.toString();
    return saltStr;
  }
}