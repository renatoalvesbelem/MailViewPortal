package br.com.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.model.EmailModel;

public class HTMLPageControl {
    private String iPServer;
    private Map<Integer, EmailModel> titleBodyEmail;

    public HTMLPageControl(String iPServer) {
        this.iPServer = iPServer;
    }

    private Map<Integer, EmailModel> readPage() {
        URL urlMailHTML;
        EmailModel emailModel;
        int n = 1;
        String urlString = "http://" + iPServer + "/mailserver/email/body/-";
        int tmp = 0;
        titleBodyEmail = new LinkedHashMap<Integer, EmailModel>();
        StringBuilder titleEmail = new StringBuilder();
        StringBuilder bodyEmail;
        int tmpMail = 0;
        try {
            if (checkStatusHTTP(new URL(urlString + n))) {
                while (checkStatusHTTP(new URL(urlString + n))) {
                    urlMailHTML = new URL(urlString + n);
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlMailHTML.openStream()));
                    String inputLine;
                    emailModel = new EmailModel();
                    bodyEmail = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        if (inputLine.contains("<body>") || tmp == 1) {
                            if (inputLine.contains("</body>")) {
                                tmp = 0;
                            } else {
                                bodyEmail.append((inputLine));
                                tmp = 1;
                                tmpMail++;
                                if (tmpMail == 3 || tmpMail == 4) {
                                    titleEmail.append(corrigeString(inputLine));
                                }
                            }
                        }
                    }
                    in.close();
                    tmpMail = 0;
                    emailModel.setTitleEmail(corrigeString(titleEmail.toString()));
                    titleEmail = new StringBuilder();
                    emailModel.setBodyEmail(bodyEmail);
                    titleBodyEmail.put(n++, emailModel);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Não existe nenhum email registrado em memória");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return titleBodyEmail;
    }

    private boolean checkStatusHTTP(URL URL) {
        try {
            return (((HttpURLConnection) URL.openConnection()).getResponseCode()) == 200;
        } catch (ConnectException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor: " + URL.getHost());
            return false;
        } catch (IOException e) {
            return false;
        }

    }

    public List<String> retornaTitulosEmails() {
        LinkedHashMap<Integer, EmailModel> keyMap = (LinkedHashMap<Integer, EmailModel>) this.readPage();
        List<String> keys = new ArrayList<String>();
        for (EmailModel t : keyMap.values()) {
            keys.add(t.getTitleEmail());
        }
        return keys;
    }

    private String corrigeString(String string) {
        return (StringEscapeUtils.unescapeHtml(string)).replaceAll("<.*?>", " ");
    }

    public StringBuilder retornaBodyEmail(Integer key) {
        return titleBodyEmail.get(key + 1).getBodyEmail();
    }
}
