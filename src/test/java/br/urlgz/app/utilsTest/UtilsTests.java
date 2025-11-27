package br.urlgz.app.utilsTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import br.urlgz.app.utils.Base62;

@DisplayName("Test for the base62 class")
public class UtilsTests {


  //    char: 8 charValue: 8 value: 8
  //char: m charValue: 22 value: 518
  //char: 0 charValue: 0 value: 32116
  //char: K charValue: 46 value: 1991238
  //char: x charValue: 33 value: 123456789
  //recordId: 123456789
  //recordId: 8m0Kx
  //recordId: 123456789
  @Test
  @DisplayName("Encode Test. Test an input value is encode to a base62 value.")
  void EncodeWithNonNullValueAndShouldReturnTheShortCode(){
    int testId = 123456789;
    String expectedValue = "8m0Kx";

    var encodeValue = Base62.encode(testId);

    assertNotNull(encodeValue);
    assertEquals(expectedValue, encodeValue);
  }

  @Test()
  @DisplayName("Decode Test. Test if a encoded base62 string is decode to original base10 value. ")
  void DecodeWhitNonNullValueAndShouldReturnTheCodeUsedForEncondig(){
    String encodeString = "8m0Kx";
    long expectedValue = 123456789;
    long decodeValue = Base62.decode(encodeString);

    assertNotNull(decodeValue);
    assertEquals(expectedValue, decodeValue);

  }

  @Test()
  @DisplayName("Decode Test. Test input string values thas is beyond base62 alphabet.\n"+
  "char: 8 charValue: 8 value: 8\n"+
  "char: 8 charValue: 8 value: 8\n"+
  "char: m charValue: 22 value: 518\n"+
  "char: 0 charValue: 0 value: 32116\n"+
  "char: K charValue: 46 value: 1991238\n"+
  "char: x charValue: 33 value: 123456789\n"+
  "char: 8 charValue: 8 value: 8\n"+
  "char: m charValue: 22 value: 518\n"+
  "char: 0 charValue: 0 value: 32116\n"+
  "char: K charValue: 46 value: 1991238\n"+
  "char: x charValue: 33 value: 123456789\n"+
  "char: ! charValue: -1 value: 7654320917\n"+
  "recordId: 123456789\n"+
  "UrlShort: 8m0Kx\n"+
  "recoveryid: 123456789\n"+
  "otherCode: 7654320917\n"+
  "decodeOtherCode: 8m0KwZ\n"
)
  void DecodeShouldRetunrNotExpectedValueWithNonBase62Charater(){
    String encodeString = "8m0Kx!";
    long decodeValue = Base62.decode(encodeString);
    String wrongStringEncode = Base62.encode(decodeValue);

    assertNotEquals(encodeString, wrongStringEncode);

  }

}
