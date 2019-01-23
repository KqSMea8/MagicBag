package org.github.helixcs.netty.chapter2;

import java.io.Serializable;

public class CustomerProtocol implements Serializable {

    public static class Header  implements Serializable{
        private int version;

        private int length;

        private String sessionId;

        public Header(int version, int length, String sessionId) {
            this.version = version;
            this.length = length;
            this.sessionId = sessionId;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

    }

    private Header header;

    private String content;

    public CustomerProtocol(Header header, String content) {
        this.header = header;
        this.content = content;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {

        this.content = content;
    }

    @Override
    public String toString() {
        return "CustomerProtocol{" +
                "header=" + header +
                ", content='" + content + '\'' +
                '}';
    }
}
