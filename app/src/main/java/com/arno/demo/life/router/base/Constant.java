package com.arno.demo.life.router.base;

public class Constant {
    public static class PKG {
        public static final String MAIN_PKG = "com.arno.demo.life";
        public static final String MAIN_CLASSNAME = MAIN_PKG + ".router.module.main.MainRemoteService";
    }

    public static class PRE {
        public static final String PRE_FIX = "Router_";
    }

    public static class CODE {
        public static final int MSG_REPLY = 1;
        public static final int MSG_REQUEST = 2;

    }

    public static class PARAM {
        public static final String RESPONSE = "response";
        public static final String REQUEST = "request";
    }

    public static class DOMAIN {
        public static final String MAIN = "main";
        public static final String MEDIA = "media";

    }

    public static class ACTION {
        public static class MAIN {
            public static final String HELLO = "sayHello";
            public static final String HELLO_REPLY = "reply_sayHello";

            public static final String EXIT = "exit";
            public static final String EXIT_REPLY = "reply_exit";

        }

        public static class MEDIA {
            public static final String PLAY = "play";
            public static final String PAUSE = "pause";


        }


    }
}
