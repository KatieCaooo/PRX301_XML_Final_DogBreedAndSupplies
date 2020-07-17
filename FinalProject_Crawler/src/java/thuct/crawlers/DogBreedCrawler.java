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
import java.util.List;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import thuct.daos.DogBreedDAO;
import thuct.daos.TemperamentDAO;
import thuct.dtos.DogBreed;
import thuct.dtos.Temperament;
import thuct.utils.XMLUtils;

/**
 *
 * @author katherinecao
 */
public class DogBreedCrawler {

    private static final String configFile = "/web/WEB-INF/config.xml";

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
                    //crawl temperament list
//                    crawlTemperList(xPath, doc);
                    //crawl Child Pages
//                    crawlDogDetails(xPath, doc);
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

    public static void crawlTemperList(XPath xPath, Document doc) throws XPathExpressionException, IOException {
        TemperamentDAO temperamentDAO = new TemperamentDAO();
        NodeList nodeLinks = (NodeList) xPath.evaluate("//div[@class='left']/a", doc, XPathConstants.NODESET);
        String dogDocument = "";
        for (int j = 0; j < nodeLinks.getLength(); j++) {
            String link = nodeLinks.item(j).getAttributes().getNamedItem("href").getNodeValue();
            InputStream dogInputStream = getInputStreamForUrl(link);

            dogDocument = getDogDetailsHTML(dogInputStream, dogDocument);
            //remove special characters
            dogDocument = dogDocument.replaceAll("&[a-zA-Z0-9#]*;", "");
            doc = XMLUtils.convertStringToDocument(dogDocument);

            NodeList nodeList = (NodeList) xPath.evaluate("//table[@class='table-01']/tbody/tr[10]/td[2]/p", doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Temperament temperament = new Temperament();

                String content = nodeList.item(i).getTextContent();
                temperament.setContent(content);
                temperamentDAO.insertTemperament(temperament);
            }
        }
    }

    public static void crawlDogDetails(XPath xPath, Document doc) throws IOException, XPathExpressionException {
        NodeList nodeLinks = (NodeList) xPath.evaluate("//div[@class='left']/a", doc, XPathConstants.NODESET);
        NodeList nodeNames = (NodeList) xPath.evaluate("//div[@class='left']/a/img", doc, XPathConstants.NODESET);
        NodeList nodePhotos = (NodeList) xPath.evaluate("//div[@class='left']/a/img", doc, XPathConstants.NODESET);

        DogBreedDAO breedDAO = new DogBreedDAO();
        String dogDocument = "";
        for (int j = 0; j < nodeNames.getLength(); j++) {
            DogBreed breed = new DogBreed();
            breed.setName(nodeNames.item(j).getAttributes().getNamedItem("alt").getNodeValue());
            breed.setPhoto(nodePhotos.item(j).getAttributes().getNamedItem("src").getNodeValue());

            String link = nodeLinks.item(j).getAttributes().getNamedItem("href").getNodeValue();
            InputStream dogInputStream = getInputStreamForUrl(link);

            dogDocument = getDogDetailsHTML(dogInputStream, dogDocument);
            //remove special characters
            dogDocument = dogDocument.replaceAll("&[a-zA-Z0-9#]*;", "");

            doc = XMLUtils.convertStringToDocument(dogDocument);
            //Information Table02
            crawlInformation(breed, xPath, doc);
            //Characteristics Table02
            crawlCharacteristics(breed, xPath, doc);
            //insert
            breedDAO.insertDogBreed(breed);
            //delete breed not full fields
            List idNotFullList = breedDAO.getIdDogBreedNotFull();
            for (int i = 0; i < idNotFullList.size(); i++) {
                breedDAO.removeAllDogBreedNotFull(Integer.parseInt(idNotFullList.get(i).toString()));
            }
        }
    }

    public static DogBreed crawlInformation(DogBreed breed, XPath xPath, Document doc) throws XPathExpressionException {
        NodeList nodeList;
        Node nodeElement;
        //temperament
        nodeList = (NodeList) xPath.evaluate("//table[@class='table-01']/tbody/tr[10]/td[2]/p", doc, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            String temperament = nodeList.item(i).getTextContent();
            TemperamentDAO temperamentDAO = new TemperamentDAO();
            Temperament t = temperamentDAO.findTemperamentByContent(temperament);
            if (t != null) {
                breed.getTemperamentList().add(t);
            }
        }

        //size
        nodeElement = (Node) xPath.evaluate("//table[@class='table-01']/tbody/tr[7]/td[2]", doc, XPathConstants.NODE);
        String size = nodeElement.getTextContent();
        size = size.replaceAll("<[a-zA-Z0-9:=\"-/. ]*>", "");
        breed.setSize(size);

        //life span
        nodeElement = (Node) xPath.evaluate("//table[@class='table-01']/tbody/tr[9]/td[2]", doc, XPathConstants.NODE);
        String lifeSpan = nodeElement.getTextContent();
        breed.setLifeSpan(lifeSpan);

        //puppy
        nodeElement = (Node) xPath.evaluate("//table[@class='table-01']/tbody/tr[14]/td[2]", doc, XPathConstants.NODE);
        String puppy = nodeElement.getTextContent();
        breed.setPuppy(puppy);

        //price
        nodeElement = (Node) xPath.evaluate("//table[@class='table-01']/tbody/tr[15]/td[2]", doc, XPathConstants.NODE);
        String priceString = nodeElement.getTextContent();
        priceString = priceString.replace("Average ", "");
        priceString = priceString.replace("USD", "");
        priceString = priceString.replace("$", "");
        Float price = 0F;
        if (priceString.contains("-")) {
            String priceArray[] = priceString.split("-");
            priceString = priceArray[1].trim();
            price = Float.parseFloat(priceString);
        }
        breed.setPrice(price);
        return breed;
    }

    public static DogBreed crawlCharacteristics(DogBreed breed, XPath xPath, Document doc) throws XPathExpressionException {
        Node nodeElement;
        NodeList nodeList = (NodeList) xPath.evaluate("//table[@class='table-02']//td[1]/text()", doc, XPathConstants.NODESET);
        Float star = 0F;
        String starString = "";
        String starArray[];
        for (int i = 0; i < nodeList.getLength(); i++) {
            String nameChar = nodeList.item(i).getTextContent().trim();
            nodeElement = (Node) xPath.evaluate("//table[@class='table-02']/tbody/tr[" + (i + 2) + "]/td[2]/p[1]", doc, XPathConstants.NODE);
            starString = nodeElement.getAttributes().getNamedItem("class").getNodeValue();
            starArray = starString.split("-");
            starString = starArray[1].trim();
            star = Float.parseFloat(starString);

            switch (nameChar) {
                case "Adaptability":
                    breed.setAdaptability(star);
                    break;

                case "Apartment Friendly":
                    breed.setApartmentFriendly(star);
                    break;

                case "Barking Tendencies":
                    breed.setBarkingTendency(star);
                    break;

                case "Cat Friendly":
                    breed.setCatFriendly(star);
                    break;

                case "Child Friendly":
                    breed.setChildFriendly(star);
                    break;

                case "Dog Friendly":
                    breed.setDogFriendly(star);
                    break;

                case "Exercise Needs":
                    breed.setExerciseNeed(star);
                    break;

                case "Grooming":
                    breed.setGrooming(star);
                    break;

                case "Health Issues":
                    breed.setHealthIssuse(star);
                    break;

                case "Intelligence":
                    breed.setIntelligence(star);
                    break;

                case "Playfulness":
                    breed.setPlayfulness(star);
                    break;

                case "Shedding Level":
                    breed.setSheddingLevel(star);
                    break;

                case "Stranger Friendly":
                    breed.setStrangerFriendly(star);
                    break;

                case "Trainability":
                    breed.setTrainability(star);
                    break;

                case "Watchdog Ability":
                    breed.setWatchdogAbility(star);
                    break;

                default:
                    break;
            }
        }
        return breed;
    }
}
