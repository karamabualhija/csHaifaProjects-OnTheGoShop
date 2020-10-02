package com.tsofen.onthegoshopClient.DataServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class TextDownloader {
    private static TextDownloader textDownloader = null;

    private TextDownloader() { }

    public static TextDownloader getInstance()
    {
        if (textDownloader == null)
            textDownloader = new TextDownloader(); // TODO - add synchronized.

        return textDownloader;
    }


    public String getText(String urlAddress, OnDataReadyHandler handler) {
        try {
            // Create a URL for the desired page
            URL url = new URL(urlAddress);
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str = "";
            String input = "";

            while ((input = in.readLine()) != null) {
                str += input;
            }

            if(handler!=null) // if there is a handler, we want to activate the completed downloaded
            {
                handler.onDataDownloadCompleted(str); // activating handlers function to set result(str)
            }

            in.close();
            return str;
        } catch (MalformedURLException e) {
            handler.onDownloadError();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            handler.onDownloadError();
            return null;
        }
    }
}
