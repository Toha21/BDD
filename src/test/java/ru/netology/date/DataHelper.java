package ru.netology.date;

import lombok.Value;

public class DataHelper {

    private DataHelper() {
    }
    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }
    @Value
    public static class Card {
        private String CardNumber;
    }
  public static AuthInfo getAuthInfo(){
        return new AuthInfo("vasya","qwerty123");
  }
  public static Card getFirstCardInfo(AuthInfo authInfo){
       return new Card("5559 0000 0000 0001");
  }
  public static Card getSecondCardInfo(AuthInfo authInfo){
        return new Card("5559 0000 0000 0002");
  }
  @Value
  public static class VerificationCode{
        private String code;
  }
  public static VerificationCode getVerificationCodeFor(AuthInfo authInfo){
        return new VerificationCode("12345");
  }
}
/*



    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }*/