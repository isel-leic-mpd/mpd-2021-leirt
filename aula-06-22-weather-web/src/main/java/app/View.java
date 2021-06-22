package app;

import html.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;
import mappers.HtmlMapper;

import java.io.*;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static html.Dsl.*;

public class View {

	private static final int INTERNAL_ERROR=500;
	private static int RESOURCE_NOT_FOUND=404;
	private static int STATUS_OK=200;

	private static Style rules() {
		return new Style(
			rule(
				"table",
				prop("width", "50%"),
				prop("border-collapse", "collapse")
			),

			rule(
				"th, td",
				prop("border-bottom", "1px solid #ddd")
			),
			rule(
				"th",
				prop("text-align", "center" ),
				prop("height", "70px")
			),
			rule(
				"td",
				prop("vertical-align", "center" ),
				prop("height", "50px"),
				prop("text-align", "center" )

			),
			rule(
				"tr:nth-child(even)",
				prop("background-color",  "#f2f2f2")
			)

		);
	}

	/**
	 * Retrieve API-KEY from resources
	 * @return
	 */
	private static InputStream getResourceStream(String name) {
		try {
			URL keyFile =
				ClassLoader.getSystemResource(name);
			return   keyFile.openStream();
		}
		catch(IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private static void sendErrorResponse(HttpServerResponse resp, int errorCode) {
		resp.putHeader("content-type", "text/html");
		String msg = "<h1> Error" + errorCode + "! </h1>";
		resp.putHeader("content-length", "" + msg.length());
		resp.write(msg);

		resp.setStatusCode(errorCode);
		resp.end();
		resp.close();

	}

	private static void sendOkResponse(HttpServerResponse resp, Html page) {
		try {

			// build response stream
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(bs));

			page.writeOn(bw);
			bw.flush();
			String respText = bs.toString();

			// define response headers
			resp.putHeader("content-type", "text/html");
			resp.putHeader("content-length", Integer.toString(respText.length()));

			// send response
			resp.setStatusCode(STATUS_OK);

			resp.write(respText);

		}

		catch(IOException e) {
			sendErrorResponse(resp, INTERNAL_ERROR);
		}
		finally {
			try {
				resp.end();
				resp.close();
			}
			catch(Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
	}



	public static <T> void buildTableView(HttpServerResponse resp,
	                                  String title,
	                                  CompletableFuture<Stream<T>> source,
	                                  HtmlMapper<T> mapper, String... columns) {

		Table table = table(columns);

		Html page = page(title,
						div(
							h2(title),
							table,
							div(
								a(
									h2("Home"),
									"/"
								)
							)
						),
						//link_css("/css/styles.css")
						rules()
					);

		source
		.whenComplete(
			(s, error) -> {
				if (error != null) {
					sendErrorResponse(resp, INTERNAL_ERROR);
				}
				else {
					s.forEach(t -> {
						table.appendChild(mapper.map(t));

					});

					sendOkResponse(resp, page);
				}
			}
		);
	}


	public static void buildDefaultPage(HttpServerResponse resp) {
		Form f = new Form("Search location", "get","/location",
			new Span(new Text("Location")),
			new InputText("name"),
			new InputSubmit("Submit")
		);

		resp.putHeader("content-type", "text/html");
		resp.setStatusCode(200);

		Html page = page(
			"Location search",
			div(
				h2("Location search"),
				f,
				a("Background vector created by macrovector - www.freepik.com",
					"https://www.freepik.com/free-photos-vectors/background")
			),
			link_css("/css/styles.css")
		);
		sendOkResponse(resp,page);
	}

	private static void sendTextResourceType(HttpServerResponse resp, String name, String type) {
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(getResourceStream(name)))) {

			resp.putHeader("content-type", type);
			resp.setStatusCode(200);
			resp.setChunked(true);
			resp.setWriteQueueMaxSize(128);
			reader.lines()
			.forEach(l -> resp.write(l));

			resp.end();
			resp.close();
		}
		catch(IOException e) {
			sendErrorResponse(resp, RESOURCE_NOT_FOUND);
		}
	}

	private static void sendBinaryResourceType(HttpServerResponse resp, String name, String type) {
		try(InputStream input =  getResourceStream(name)) {
			byte[] buffer =new byte[4096];
			resp.putHeader("content-type", type);
			resp.setStatusCode(200);
			resp.setChunked(true);
			resp.setWriteQueueMaxSize(128);

			int nread;
			while((nread = input.read(buffer)) > 0) {
				Buffer buf = Buffer.buffer(nread);
				buf.appendBytes(buffer, 0, nread);
				resp.write(buf);
			}

			resp.end();
			resp.close();
		}
		catch(IOException e) {
			sendErrorResponse(resp, RESOURCE_NOT_FOUND);
		}
	}

	public static void sendResource(HttpServerResponse resp, String name) {
		sendTextResourceType(resp, name, "text/css");
	}

	public static void sendImage(HttpServerResponse resp, String name) {
		sendBinaryResourceType(resp, name, "image/png");
	}
}
