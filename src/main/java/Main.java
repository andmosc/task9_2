import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static final ObjectMapper mapper = new ObjectMapper();
    public static String outFile = "data/";
    public static String keyFile = "data/key";

    public static void main(String[] args) throws IOException {

        Path path = Paths.get(keyFile);
        String key = Files.readAllLines(path).get(0);

        ApiRequest.setUrlApiKey(key);
        CloseableHttpResponse response = ApiRequest.getResponse();

        ApiNasa nasa = mapper.readValue(response.getEntity().getContent(), new TypeReference<>() {
        });
        response.close();

        String url = nasa.getUrl();

        String[] fileName = url.split("/");

        saveUrl(outFile + fileName[fileName.length - 1], url);
    }

    public static void saveUrl(final String outFileName, final String urlString) throws IOException {

        /*
        ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(urlString).openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(outFileName);
        fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
*/

        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        long outFileSize = new File(outFileName).length();

        OutputStream outSFile = new FileOutputStream(outFileName, true);
        connection.setRequestProperty("Range", "bytes=" + outFileSize + "-");
        connection.connect();

        InputStream input = new BufferedInputStream(connection.getInputStream());

        byte dataBuffer[] = new byte[1024];
        int count = 0;
        while ((count = input.read(dataBuffer)) != -1) {
            outSFile.write(dataBuffer, 0, count);
        }
    }
}
