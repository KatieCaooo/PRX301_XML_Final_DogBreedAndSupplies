/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuct.crawlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import static thuct.crawlers.DogBreedCrawler.getDogDetailsHTML;
import static thuct.crawlers.DogBreedCrawler.getInputStreamForUrl;
import thuct.daos.DogBreedDAO;
import thuct.dtos.DogBreed;
import thuct.utils.XMLUtils;

/**
 *
 * @author kloecao
 */
public class DogBreedTask implements Runnable {

    Node nodeName;
    Node nodePhoto;
    Node nodeLink;
    String dogDocument;
    DogBreedDAO breedDAO;

    public DogBreedTask(Node nodeName, Node nodePhoto, Node nodeLink, String dogDocument, DogBreedDAO breedDAO) {
        this.nodeName = nodeName;
        this.nodePhoto = nodePhoto;
        this.nodeLink = nodeLink;
        this.dogDocument = dogDocument;
        this.breedDAO = breedDAO;
    }

    @Override
    public void run() {
        InputStream dogInputStream = null;
        DogBreed breed = new DogBreed();

        try {
            XPath xPath = XMLUtils.createXPath();
            String xmlBreed = "<dogBreed>\n";
            breed.setName(nodeName.getAttributes().getNamedItem("alt").getNodeValue());
            xmlBreed += "<name>" + nodeName.getAttributes().getNamedItem("alt").getNodeValue() + "</name>\n";
            breed.setPhoto(nodePhoto.getAttributes().getNamedItem("src").getNodeValue());
            xmlBreed += "<photo>" + nodePhoto.getAttributes().getNamedItem("src").getNodeValue() + "</photo>\n";
            String link = nodeLink.getAttributes().getNamedItem("href").getNodeValue();
            dogInputStream = getInputStreamForUrl(link);
            dogDocument = getDogDetailsHTML(dogInputStream, dogDocument);
            //remove special characters
            dogDocument = dogDocument.replaceAll("&[a-zA-Z0-9#]*;", "");
            Document doc = XMLUtils.convertStringToDocument(dogDocument);
            //Information Table02
            xmlBreed = crawlInformation(xmlBreed, breed, xPath, doc);
            //Characteristics Table02
            xmlBreed = crawlCharacteristics(xmlBreed, breed, xPath, doc);
            JAXBContext jAXBContext = JAXBContext.newInstance(DogBreed.class);
            Unmarshaller um = jAXBContext.createUnmarshaller();
            DogBreed tmp = (DogBreed) um.unmarshal(new StringReader(xmlBreed));
            //insert
            System.out.println("Inserted " + (DogBreedCrawler.count++) + " - " + breed.getName() + " breeds");
//            breedDAO.insertDogBreed(breed);
        } catch (IOException ex) {
            Logger.getLogger(DogBreedTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DogBreedTask.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error " + breed.getName());
        } finally {
            try {
                dogInputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(DogBreedTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String crawlInformation(String xmlBreed, DogBreed breed, XPath xPath, Document doc) throws XPathExpressionException {
        String xmlResult = xmlBreed;
        Node nodeElement;

        //size
        nodeElement = (Node) xPath.evaluate("//table[@class='table-01']/tbody/tr[7]/td[2]", doc, XPathConstants.NODE);
        String size = nodeElement.getTextContent();
        size = size.replaceAll("<[a-zA-Z0-9:=\"-/. ]*>", "");
        breed.setSize(size);
        xmlResult += "<size>" + size + "</size>\n";

        //life span
        nodeElement = (Node) xPath.evaluate("//table[@class='table-01']/tbody/tr[9]/td[2]", doc, XPathConstants.NODE);
        String lifeSpan = nodeElement.getTextContent();
        breed.setLifeSpan(lifeSpan);
        xmlResult += "<lifeSpan>" + lifeSpan + "</lifeSpan>\n";

        //weight
        nodeElement = (Node) xPath.evaluate("//table[@class='table-01']/tbody/tr[12]/td[2]", doc, XPathConstants.NODE);
        String weight = nodeElement.getTextContent();
        if (weight == "") {
            weight = "Unknown";
        } else if (weight.contains(":")) {
            if (weight.contains("\n")) {
                String weightArray[] = weight.split("\n");
                weight = weightArray[1].substring(weightArray[1].indexOf(" ") + 1).trim();
            } else if (weight.contains("Standard")) {
                String weightArray[] = weight.split(":");
                weight = weightArray[1].trim();
            } else if (weight.contains(" and ")) {
                String weightArray[] = weight.split("and");
                String weightArray1[] = weightArray[0].split(":");
                weight = weightArray1[1].trim();
            }
        }
        breed.setWeight(weight);
        xmlResult += "<weight>" + weight + "</weight>\n";

        //puppy
        nodeElement = (Node) xPath.evaluate("//table[@class='table-01']/tbody/tr[14]/td[2]", doc, XPathConstants.NODE);
        String puppy = nodeElement.getTextContent();
        if (puppy == "") {
            puppy = "Unknown";
        }
        breed.setPuppy(puppy);
        xmlResult += "<puppy>" + puppy + "</puppy>\n";

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
        xmlResult += "<price>" + price + "</price>\n";
        return xmlResult;
    }

    public String crawlCharacteristics(String xmlBreed, DogBreed breed, XPath xPath, Document doc) throws XPathExpressionException {
        String xmlResult = xmlBreed;
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
                    xmlResult += "<adaptability>" + star + "</adaptability>\n";
                    break;

                case "Apartment Friendly":
                    breed.setApartmentFriendly(star);
                    xmlResult += "<apartmentFriendly>" + star + "</apartmentFriendly>\n";
                    break;

                case "Barking Tendencies":
                    breed.setBarkingTendency(star);
                    xmlResult += "<barkingTendency>" + star + "</barkingTendency>\n";
                    break;

                case "Cat Friendly":
                    breed.setCatFriendly(star);
                    xmlResult += "<catFriendly>" + star + "</catFriendly>\n";
                    break;

                case "Child Friendly":
                    breed.setChildFriendly(star);
                    xmlResult += "<childFriendly>" + star + "</childFriendly>\n";
                    break;

                case "Dog Friendly":
                    breed.setDogFriendly(star);
                    xmlResult += "<dogFriendly>" + star + "</dogFriendly>\n";
                    break;

                case "Exercise Needs":
                    breed.setExerciseNeed(star);
                    xmlResult += "<exerciseNeed>" + star + "</exerciseNeed>\n";
                    break;

                case "Grooming":
                    breed.setGrooming(star);
                    xmlResult += "<grooming>" + star + "</grooming>\n";
                    break;

                case "Health Issues":
                    breed.setHealthIssuse(star);
                    xmlResult += "<healthIssuse>" + star + "</healthIssuse>\n";
                    break;

                case "Intelligence":
                    breed.setIntelligence(star);
                    xmlResult += "<intelligence>" + star + "</intelligence>\n";
                    break;

                case "Playfulness":
                    breed.setPlayfulness(star);
                    xmlResult += "<playfulness>" + star + "</playfulness>\n";
                    break;

                case "Shedding Level":
                    breed.setSheddingLevel(star);
                    xmlResult += "<sheddingLevel>" + star + "</sheddingLevel>\n";
                    break;

                case "Stranger Friendly":
                    breed.setStrangerFriendly(star);
                    xmlResult += "<strangerFriendly>" + star + "</strangerFriendly>\n";
                    break;

                case "Trainability":
                    breed.setTrainability(star);
                    xmlResult += "<trainability>" + star + "</trainability>\n";
                    break;

                case "Watchdog Ability":
                    breed.setWatchdogAbility(star);
                    xmlResult += "<watchdogAbility>" + star + "</watchdogAbility>\n";
                    break;

                default:
                    break;
            }
        }
        xmlResult += "</dogBreed>";
        return xmlResult;
    }

}
