package br.com.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.StringEscapeUtils;

public class HTMLPageControl {
	URL urlMailHTML;
	private String iPServer;
	private LinkedHashMap<String, StringBuilder> titleBodyEmail;

	public HTMLPageControl(String iPServer) {
		this.iPServer = iPServer;
		readPage();
	}

	public LinkedHashMap<String, StringBuilder> readPage() {
		int n = 1;
		String urlString = "http://" + iPServer + "/mailserver/email/body/-";
		int tmp = 0;
		titleBodyEmail = new LinkedHashMap<String, StringBuilder>();
		String titleEmail = null;
		StringBuilder bodyEmail = new StringBuilder();
		int tmpMail = 1;
		try {
			while (checkStatusHTTP(new URL(urlString + n))) {
				urlMailHTML = new URL(urlString + n);
				BufferedReader in = new BufferedReader(new InputStreamReader(urlMailHTML.openStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					if (inputLine.contains("<body>") || tmp == 1) {
						if (inputLine.contains("</body>")) {
							tmp = 0;
						} else {
							bodyEmail.append(inputLine);
							tmp = 1;
							tmpMail++;
							if (tmpMail == 4 || tmpMail == 3) {
								titleEmail = inputLine + titleEmail;
							}
						}
					}
				}
				in.close();
				tmpMail = 0;
				n++;
				titleBodyEmail.put(titleEmail, bodyEmail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return titleBodyEmail;
	}

	private boolean checkStatusHTTP(URL URL) throws IOException {
		return (((HttpURLConnection) URL.openConnection()).getResponseCode()) == 200;
	}

	public List<String> retornaTitulosEmails() {
		LinkedHashMap<String, StringBuilder> keyMap = this.readPage();
		List<String> keys = new ArrayList<String>();
		for (Entry<String, StringBuilder> t : keyMap.entrySet()) {
			keys.add(StringEscapeUtils.unescapeHtml(t.getKey()));
		}
		return keys;
	}
}
