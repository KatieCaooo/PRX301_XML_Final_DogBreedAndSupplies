/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuct.crawlers;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            if (nodeName.getAttributes().getNamedItem("alt").getNodeValue().equals("Rat Terrier")) {
                System.out.println("AAAAAAAAAAA");
            }
            breed.setName(nodeName.getAttributes().getNamedItem("alt").getNodeValue());
            breed.setPhoto(nodePhoto.getAttributes().getNamedItem("src").getNodeValue());
            String link = nodeLink.getAttributes().getNamedItem("href").getNodeValue();
            dogInputStream = getInputStreamForUrl(link);
            dogDocument = getDogDetailsHTML(dogInputStream, dogDocument);
            //remove special characters
            dogDocument = dogDocument.replaceAll("&[a-zA-Z0-9#]*;", "");
            Document doc = XMLUtils.convertStringToDocument(dogDocument);
            String xmlBreed;
            //Information Table02
            crawlInformation(breed, xPath, doc);
            //Characteristics Table02
            crawlCharacteristics(breed, xPath, doc);
            //insert
            System.out.println("Inserted " + (DogBreedCrawler.count++) + " - " + breed.getName() + " breeds");
            breedDAO.insertDogBreed(breed);
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

    public DogBreed crawlInformation(String xml, DogBreed breed, XPath xPath, Document doc) throws XPathExpressionException {
        Node nodeElement;

        //size
        nodeElement = (Node) xPath.evaluate("//table[@class='table-01']/tbody/tr[7]/td[2]", doc, XPathConstants.NODE);
        String size = nodeElement.getTextContent();
        size = size.replaceAll("<[a-zA-Z0-9:=\"-/. ]*>", "");
        breed.setSize(size);
        xml += "<>"+size+"</>";

        //life span
        nodeElement = (Node) xPath.evaluate("//table[@class='table-01']/tbody/tr[9]/td[2]", doc, XPathConstants.NODE);
        String lifeSpan = nodeElement.getTextContent();
        breed.setLifeSpan(lifeSpan);

        //weight
        nodeElement = (Node) xPath.evaluate("//table[@class='table-01']/tbody/tr[12]/td[2]", doc, XPathConstants.NODE);
        String weight = nodeElement.getTextContent();
        if (weight.contains(":")) {
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

    public DogBreed crawlCharacteristics(DogBreed breed, XPath xPath, Document doc) throws XPathExpressionException {
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
