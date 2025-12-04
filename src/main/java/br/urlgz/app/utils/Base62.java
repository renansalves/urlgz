package br.urlgz.app.utils;
public class Base62 {

  private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final Integer BASE62 = ALPHABET.length();

  public static String encode(long value) {
    if (value == 0) {
      return String.valueOf(value);
    }

    StringBuilder sb = new StringBuilder();

    while (value > 0) {
      sb.append(ALPHABET.charAt((int) (value % BASE62)));
      value /= BASE62;
    }
    return sb.reverse().toString();
  }

  public static Long decode(String encodeString) {
    if (encodeString.isEmpty()) {
      return Long.valueOf(encodeString);
    }

    Long value = 0L;
    for (char c : encodeString.toCharArray()) {
      value = value * BASE62 + ALPHABET.indexOf(c);
    }
    return value;
  }

}
