package br.com.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.model.EmailModel;

public class HTMLPageControl {
	URL urlMailHTML;
	private String iPServer;
	private Map<Integer, EmailModel> titleBodyEmail;
	private EmailModel emailModel;

	public HTMLPageControl(String iPServer) {
		this.iPServer = iPServer;
		readPage();
	}

	public Map<Integer, EmailModel> readPage() {
		int n = 1;
		String urlString = "http://" + iPServer + "/mailserver/email/body/-";
		int tmp = 0;
		titleBodyEmail = new LinkedHashMap<Integer, EmailModel>();
		String titleEmail = "";
		StringBuilder bodyEmail;
		int tmpMail = 1;
		try {
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
							if (tmpMail == 4 || tmpMail == 3) {
								titleEmail = titleEmail + corrigeString(inputLine);
							}
						}
					}

				}
				in.close();
				tmpMail = 0;
				emailModel.setTitleEmail(corrigeString(titleEmail));
				titleEmail = "";
				emailModel.setBodyEmail(bodyEmail);
				titleBodyEmail.put(n++, emailModel);

			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return titleBodyEmail;
	}

	private boolean checkStatusHTTP(URL URL) throws IOException {
		return (((HttpURLConnection) URL.openConnection()).getResponseCode()) == 200;
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
