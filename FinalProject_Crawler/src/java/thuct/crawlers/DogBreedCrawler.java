/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuct.crawlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import thuct.daos.DogBreedDAO;
import thuct.utils.XMLUtils;

/**
 *
 * @author kloecao
 */
public class DogBreedCrawler {

    private static final String configFile = "/web/WEB-INF/config.xml";
    public static int count = 0;

    private static ExecutorService service = Executors.newFixedThreadPool(15);

    public static void main(String[] args) {
        try {
            String realPath = System.getProperty("user.dir"); //get project path
            String configPath = realPath + configFile;
            Document doc = XMLUtils.parseFileToDOM(configPath); //read xml file
            int pageMax;
            if (doc != null) {
                XPath xPath = XMLUtils.createXPath();

                Node node = (Node) xPath.evaluate("//dog-domain", doc, XPathConstants.NODE);
                String url = node.getTextContent().trim();

                node = (Node) xPath.evaluate("//dog-breed-list-url", doc, XPathConstants.NODE);
                String listUrl = url + node.getTextContent().trim();

                node = (Node) xPath.evaluate("//dog-breed-url-suffix", doc, XPathConstants.NODE);
                String suffix = node.getTextContent().trim();

                node = (Node) xPath.evaluate("//dog-breed-page", doc, XPathConstants.NODE);
                pageMax = Integer.parseInt(node.getTextContent().trim());

                String document = "";

                boolean start = false;
                for (int i = 1; i <= pageMax; i++) {
                    url = listUrl + i + suffix;

                    InputStream is = getInputStreamForUrl(url);

                    BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String line = "";
                    document = "<doglist>" + "\n";

                    while ((line = br.readLine()) != null) {
                        if (line.contains("<!-- list -->")) {
                            start = true;
                            line = br.readLine();
                        }
                        if (line.contains("<!-- end list -->")) {
                            start = false;
                            document += "</doglist>";
                            break;
                        }
                        if (start) {
                            document += line + "\n";
                        }
                    }

                    doc = XMLUtils.convertStringToDocument(document);
                    //crawl Child Pages
                    crawlDogDetails(xPath, doc);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public static InputStream getInputStreamForUrl(String urlDog) throws MalformedURLException, IOException {
        URL url = new URL(urlDog);
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        return connection.getInputStream();
    }

    public static String getDogDetailsHTML(InputStream dogInputStream, String document) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dogInputStream, "UTF-8"));
        String dogLine = "";
        document = "<root>\n" + "<table class=\"table-01\">" + "\n";
        boolean startDog = false;

        while ((dogLine = bufferedReader.readLine()) != null) {
            if (dogLine.contains("<table class=\"table-01\">")) {
                startDog = true;
                dogLine = bufferedReader.readLine();
            }
            if (dogLine.contains("</table>")) {
                startDog = false;
                document += "</table>";
                break;
            }
            if (startDog) {
                document += dogLine + "\n";
            }
        }

        while ((dogLine = bufferedReader.readLine()) != null) {
            if (dogLine.contains("<table class=\"table-02\">")) {
                startDog = true;
                document += "<table class=\"table-02\">" + "\n";
                dogLine = bufferedReader.readLine();
            }
            if (dogLine.contains("</table>")) {
                document += "</table>\n" + "</root>";
                break;
            }
            if (startDog) {
                document += dogLine + "\n";
            }
        }
        return document;
    }

    public static void crawlDogDetails(XPath xPath, Document doc) throws IOException, XPathExpressionException {
        NodeList nodeLinks = (NodeList) xPath.evaluate("//div[@class='left']/a", doc, XPathConstants.NODESET);
        NodeList nodeNames = (NodeList) xPath.evaluate("//div[@class='left']/a/img", doc, XPathConstants.NODESET);
        NodeList nodePhotos = (NodeList) xPath.evaluate("//div[@class='left']/a/img", doc, XPathConstants.NODESET);
        DogBreedDAO breedDAO = new DogBreedDAO();
        String dogDocument = "";
        List<CompletableFuture> futures = new ArrayList<>();
        for (int j = 0; j < nodeNames.getLength(); j++) {
            try {
                futures.add(CompletableFuture.runAsync(new DogBreedTask(nodeNames.item(j), nodePhotos.item(j), nodeLinks.item(j), dogDocument, breedDAO), service));
            } catch (Exception e) {
                
            }
            //delete breed not full fields
//            List idNotFullList = breedDAO.getIdDogBreedNotFull();
//            for (int i = 0; i < idNotFullList.size(); i++) {
//                breedDAO.removeAllDogBreedNotFull(Integer.parseInt(idNotFullList.get(i).toString()));
//            }
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
    }

}
