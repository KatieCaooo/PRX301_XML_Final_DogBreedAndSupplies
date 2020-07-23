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
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import thuct.daos.CategoryDAO;
import thuct.dtos.Category;
import thuct.utils.XMLUtils;

/**
 *
 * @author kloecao
 */
public class DogSupplyCrawler {

    private static final String configFile = "/web/WEB-INF/config.xml";
    private static final ExecutorService executorService = Executors.newFixedThreadPool(7);
    private static final CompletableFuture[] futures = new CompletableFuture[7];
    public static int count = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String realPath = System.getProperty("user.dir"); //get project path
            String configPath = realPath + configFile;
            Document doc = XMLUtils.parseFileToDOM(configPath); //read xml file
            if (doc != null) {
                XPath xPath = XMLUtils.createXPath();

                Node node = (Node) xPath.evaluate("//supplies-domain", doc, XPathConstants.NODE);
                String url = node.getTextContent().trim();

                crawlDogBed(doc, node, url, xPath);
                crawlDogBowl(doc, node, url, xPath);
                crawlDogCollar(doc, node, url, xPath);
                crawlDogCrate(doc, node, url, xPath);
                crawlDogGate(doc, node, url, xPath);
                crawlDogJacket(doc, node, url, xPath);
                crawlDogToy(doc, node, url, xPath);
                CompletableFuture.allOf(futures).join();
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);

    }

    public static InputStream getInputStreamForUrl(String urlSupply) throws MalformedURLException, IOException {
        URL url = new URL(urlSupply);
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        return connection.getInputStream();
    }

    public static void crawlDogBed(Document doc, Node node, String url, XPath xPath) throws XPathExpressionException, IOException {
        node = (Node) xPath.evaluate("//supplies-bed", doc, XPathConstants.NODE);
        url = url + node.getTextContent().trim();
        String categoryName = "Dog Beds";
        Category category = setCategoryForSupplies(4, categoryName);
        futures[0] = CompletableFuture.runAsync(new DogSupplyTask(doc, url, xPath, category), executorService);
    }

    public static void crawlDogBowl(Document doc, Node node, String url, XPath xPath) throws XPathExpressionException, IOException {
        node = (Node) xPath.evaluate("//supplies-bowl", doc, XPathConstants.NODE);
        url = url + node.getTextContent().trim();
        String categoryName = "Dog Bowls";
        Category category = setCategoryForSupplies(3, categoryName);
        futures[1] = CompletableFuture.runAsync(new DogSupplyTask(doc, url, xPath, category), executorService);
    }

    public static void crawlDogCollar(Document doc, Node node, String url, XPath xPath) throws XPathExpressionException, IOException {
        node = (Node) xPath.evaluate("//supplies-collar", doc, XPathConstants.NODE);
        url = url + node.getTextContent().trim();
        String categoryName = "Dog Collars";
        Category category = setCategoryForSupplies(1, categoryName);
        futures[2] = CompletableFuture.runAsync(new DogSupplyTask(doc, url, xPath, category), executorService);
    }

    public static void crawlDogCrate(Document doc, Node node, String url, XPath xPath) throws XPathExpressionException, IOException {
        node = (Node) xPath.evaluate("//supplies-crate", doc, XPathConstants.NODE);
        url = url + node.getTextContent().trim();
        String categoryName = "Dog Crates";
        Category category = setCategoryForSupplies(5, categoryName);
        futures[3] = CompletableFuture.runAsync(new DogSupplyTask(doc, url, xPath, category), executorService);
    }

    public static void crawlDogGate(Document doc, Node node, String url, XPath xPath) throws XPathExpressionException, IOException {
        node = (Node) xPath.evaluate("//supplies-gate", doc, XPathConstants.NODE);
        url = url + node.getTextContent().trim();
        String categoryName = "Dog Gates";
        Category category = setCategoryForSupplies(6, categoryName);
        futures[4] = CompletableFuture.runAsync(new DogSupplyTask(doc, url, xPath, category), executorService);
    }

    public static void crawlDogJacket(Document doc, Node node, String url, XPath xPath) throws XPathExpressionException, IOException {
        node = (Node) xPath.evaluate("//supplies-jacket", doc, XPathConstants.NODE);
        url = url + node.getTextContent().trim();
        String categoryName = "Dog Jackets";
        Category category = setCategoryForSupplies(7, categoryName);
        futures[5] = CompletableFuture.runAsync(new DogSupplyTask(doc, url, xPath, category), executorService);
    }

    public static void crawlDogToy(Document doc, Node node, String url, XPath xPath) throws XPathExpressionException, IOException {
        node = (Node) xPath.evaluate("//supplies-toy", doc, XPathConstants.NODE);
        url = url + node.getTextContent().trim();
        String categoryName = "Dog Toys";
        Category category = setCategoryForSupplies(2, categoryName);
        futures[6] = CompletableFuture.runAsync(new DogSupplyTask(doc, url, xPath, category), executorService);
    }

    public static Category setCategoryForSupplies(int id, String name) {
        Category category = new Category();
        CategoryDAO categoryDAO = new CategoryDAO();
        if (categoryDAO.findCategoryById(id) == null) {
            category.setIdcategory(id);
            category.setName(name);
            categoryDAO.insertCategory(category);
        } else {
            category.setIdcategory(id);
        }
        return category;
    }

    public static void crawlSuppiesDetails(Document doc, String url, XPath xPath, Category category) throws IOException, XPathExpressionException {

    }

    public static String getDomainPhoto() {
        String url = "";
        try {
            String realPath = System.getProperty("user.dir"); //get project path
            String configPath = realPath + configFile;
            Document doc = XMLUtils.parseFileToDOM(configPath); //read xml file
            if (doc != null) {
                url = "";
                XPath xPath = XMLUtils.createXPath();

                Node node = (Node) xPath.evaluate("//supplies-photo-domain", doc, XPathConstants.NODE);
                url = node.getTextContent().trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getSuppliesHTML(String link) throws UnsupportedEncodingException, IOException {
        InputStream is = getInputStreamForUrl(link);
        boolean start = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String line = "";
        String document = "<supplies>" + "\n";
        while ((line = br.readLine()) != null) {
            if (start) {
                document += line + "\n";
            }
            if (line.contains("productPageOptionSelector")) {
                start = true;
            }
            if (line.contains("hdnPrefix")) {
                document += "</supplies>";
                break;
            }
        }
        document = document.replaceAll("&[a-zA-Z0-9#]*;", "");
        document = document.replace("<br>", "");
        document = document.replace("&", "and");
        return document;
    }

    public static String getListSuppliesHTML(String url) throws UnsupportedEncodingException, IOException {
        InputStream is = getInputStreamForUrl(url);
        boolean start = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String line = "";
        String document = "<supplies-list>" + "\n" + "<div>" + "\n";
        int count = 0;
        while ((line = br.readLine()) != null) {
            if (start) {
                document += line + "\n";
            }
            if (line.contains("pf_container")) {
                start = true;
            }
            if (line.contains("filter-sort")) {
                count++;
            }
            if (count == 2) {
                document += "</div>" + "\n" + "</supplies-list>";
                break;
            }
        }
        document = document.replaceAll("&[a-zA-Z0-9#]*;", "");
        return document;
    }

}
