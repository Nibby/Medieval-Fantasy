package hidden.indev0r.core.util;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class XMLParser {

	public static final int TYPE_READ_FILE = 0, TYPE_WRITE = 1;

	private Path     filePath;
	private Document document;

	//For reading XML Strings
	public XMLParser(String xmlData) throws ParserConfigurationException, IOException, SAXException {
		filePath = null;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		InputSource input = new InputSource();
		input.setCharacterStream(new StringReader(xmlData));

		document = dBuilder.parse(input);
		document.normalize();
	}

	//Generic XML from file with type being TYPE_READ_FILE and TYPE_WRITE
	public XMLParser(Path filePath, int type) throws ParserConfigurationException, IOException, SAXException {
		this.filePath = filePath;
		setupInstance(type);
	}

	private void setupInstance(int type) throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		if (type == TYPE_READ_FILE) {
			document = dBuilder.parse(filePath.toString());
			document.normalize();
		}
		if (type == TYPE_WRITE) {
			document = dBuilder.newDocument();
		}
	}

	public Document getDocument() {
		return document;
	}

	public Path getFilePath() {
		return filePath;
	}

	//Writes data to current document
	public void write(Document document, String key) throws Exception {
		Cipher cipher = CipherEngine.getCipher(Cipher.ENCRYPT_MODE, key);

		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer robotsInDisguise = tFactory.newTransformer();

		StringWriter writer = new StringWriter();
		robotsInDisguise.transform(new DOMSource(document), new StreamResult(writer));
		String data = writer.getBuffer().toString();
		byte[] bytes = data.getBytes(Charset.forName("UTF-8"));

		DataOutputStream output = new DataOutputStream(new CipherOutputStream(Files.newOutputStream(filePath), cipher));
		output.writeInt(bytes.length);
		output.write(bytes);
		output.flush();
		output.close();
	}
}
