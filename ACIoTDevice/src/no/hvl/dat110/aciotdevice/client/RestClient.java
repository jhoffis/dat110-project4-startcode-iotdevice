package no.hvl.dat110.aciotdevice.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class RestClient {

	public RestClient() {
	}

	private static String logpath = "/accessdevice/log";

	public void doPostAccessEntry(String message) {

		// TODO: implement a HTTP POST on the service to post the message

	}

	private static String codepath = "/accessdevice/code";

	public AccessCode doGetAccessCode() {

		AccessCode code = null;

		// TODO: implement a HTTP GET on the service to get current access code

		try (Socket s = new Socket(Configuration.host, Configuration.port)) {
			// construct get request
			String httpgetrequest = "GET " + codepath + " HTTP/1.1\r\n" + "Accept: application/json\r\n" + "Host: "
					+ Configuration.host + "\r\n" + "Connection: close\r\n" + "\r\n";

			// send HTTP request
			OutputStream output = s.getOutputStream();

			PrintWriter pw = new PrintWriter(output, false);

			pw.print(httpgetrequest);
			pw.flush();

			// read the HTTP response
			InputStream in = s.getInputStream();

			Scanner scan = new Scanner(in);
			StringBuilder jsonresponse = new StringBuilder();
			boolean header = true;

			while (scan.hasNext()) {

				String nextline = scan.nextLine();

				if (header) {
					System.out.println(nextline);
				} else {
					jsonresponse.append(nextline);
				}

				// simplified approach to identifying start of body: the empty line
				if (nextline.isEmpty()) {
					header = false;
				}

			}

			System.out.println("BODY:");
			System.out.println(jsonresponse.toString());

			scan.close();

		} catch (IOException ex) {
			System.err.println(ex);
		}

		return code;
	}
}
