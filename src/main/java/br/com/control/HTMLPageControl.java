package br.com.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;

public class HTMLPageControl {
	URL urlMailHTML;

	public void readPage(String IPServer) {
		int n = 1;
		String urlString = "http://" + IPServer + "/mailserver/email/body/-";
		int tmp = 0;
		LinkedHashMap<String, StringBuilder> titleBodyEmail = new LinkedHashMap<String, StringBuilder>();
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
								titleEmail = inputLine+titleEmail;
								System.out.println(n+" - "+inputLine);
							}
						}
					}
				}
				in.close();
				tmpMail =0;
				n++;
				titleBodyEmail.put(titleEmail, bodyEmail);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean checkStatusHTTP(URL URL) throws IOException {
		return (((HttpURLConnection) URL.openConnection()).getResponseCode()) == 200;
	}
}
