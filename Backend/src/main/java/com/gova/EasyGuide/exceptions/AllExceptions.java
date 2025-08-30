package com.gova.EasyGuide.exceptions;

public class AllExceptions {

    public static  class userAllReadyExist extends RuntimeException{

        public userAllReadyExist(String str)
        {
            super(str);
        }
    }

    public static class courseAllReadyExist extends RuntimeException{
        public courseAllReadyExist(String str) {
            super(str);
        }

    }
    public static class userNotFound extends RuntimeException{
        public userNotFound(String str)
        {
            super(str);
        }
    }

    public static class invalidCredentails extends RuntimeException
    {
        public invalidCredentails(String str)
        {
            super(str);
        }
    }

    public static class resourceAllreadyExist extends RuntimeException
    {
        public resourceAllreadyExist(String str)
        {
            super(str);
        }
    }
}
