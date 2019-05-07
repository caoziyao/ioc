package main.request;

import main.Utils;

import java.util.Arrays;
import java.util.HashMap;

public class RequestParse {

    private Request req;

    public void parse(byte[] request, Request req) {
        System.out.println("parse");
        this.req = req;

        String str = Utils.byteArrayToStr(request);
        String[] lines = str.split("\r\n");

        // first line
        parseFirstLine(lines[0]);

        // header
        int row = rowOfHeaders(lines);
        String[] newData = Arrays.copyOfRange(lines, 1, row + 1);
        parseHeader(newData);

        // body
        int len = lines.length;
        if (len > row+1) {
            parseBody(lines[len-1]);
        }

    }

    private int rowOfHeaders(String[] lines) {

        int row = 0;
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            if (!line.equals("")) {
                row += 1;
            } else {
                break;
            }
        }

        return row;
    }

    private void parseFirstLine(String line) {
        String[] ws = line.split(" ");
        this.req.setMethod(ws[0]);
        this.req.setPath(ws[1]);
        this.req.setProtocol(ws[2]);
    }

    private void parseHeader(String[] lines) {

        HashMap<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            String[] items = line.split(": ");
            map.put(items[0], items[1]);
        }

        this.req.setHeaders(map);
    }

    private void parseBody(String body) {
        this.req.setBody(body);
    }
}
