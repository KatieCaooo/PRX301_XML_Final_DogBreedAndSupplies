/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuct.crawlers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import static thuct.crawlers.DogSupplyCrawler.getDomainPhoto;
import static thuct.crawlers.DogSupplyCrawler.getListSuppliesHTML;
import static thuct.crawlers.DogSupplyCrawler.getSuppliesHTML;
import thuct.daos.DogSuppliesDAO;
import thuct.dtos.Category;
import thuct.dtos.DogSupplies;
import thuct.dtos.DogSuppliesPK;
import thuct.utils.XMLUtils;

/**
 *
 * @author kloecao
 */
public class DogSupplyTask implements Runnable {

    private Document doc;
    private String url;
    private XPath xPath;
    private Category category;

    public DogSupplyTask(Document doc, String url, XPath xPath, Category category) {
        this.doc = doc;
        this.url = url;
        this.xPath = xPath;
        this.category = category;
    }

    @Override
    public void run() {
        try {
            XPath xPath = XMLUtils.createXPath();
            String document = getListSuppliesHTML(url);
            DogSuppliesPK dogSuppliesPK = new DogSuppliesPK();

            doc = XMLUtils.convertStringToDocument(document);

            NodeList nodeLinks = (NodeList) xPath.evaluate("//div[@id='showavailable']//span[@class='TNAIL_PFImage']/a[1]", doc, XPathConstants.NODESET);
            NodeList nodeNames = (NodeList) xPath.evaluate("//div[@id='showavailable']//span[@class='TNAIL_PFName']/a[1]/text()", doc, XPathConstants.NODESET);
            NodeList nodePhotos = (NodeList) xPath.evaluate("//div[@id='showavailable']//span[@class='TNAIL_PFImage']/a[1]/img", doc, XPathConstants.NODESET);
            NodeList nodeTitles = (NodeList) xPath.evaluate("//div[@id='showavailable']//span[@class='TNAIL_PFImage']/a[1]/img", doc, XPathConstants.NODESET);

            String domainPhoto = getDomainPhoto();

            for (int i = 0; i < nodeLinks.getLength(); i++) {
                String link = nodeLinks.item(i).getAttributes().getNamedItem("href").getNodeValue();
                document = getSuppliesHTML(link);
                doc = XMLUtils.convertStringToDocument(document);

                DogSuppliesDAO dogSuppliesDAO = new DogSuppliesDAO();
                NodeList nodeSizes = (NodeList) xPath.evaluate("//div[contains(@id,'pf_Members')]//span[@class='name']", doc, XPathConstants.NODESET);
                NodeList nodePrices = (NodeList) xPath.evaluate("//div[contains(@id,'pf_Members')]//span[@class='MemPrice']", doc, XPathConstants.NODESET);
                NodeList nodeSale = (NodeList) xPath.evaluate("//div[contains(@id,'pf_Members')]//span[@class='MemSalePrice']", doc, XPathConstants.NODESET);
                for (int j = 0; j < nodeSizes.getLength(); j++) {
                    DogSupplies dogSupplies = new DogSupplies();
                    String sizeSupplies = nodeSizes.item(j).getTextContent();
                    String size;
                    int indexSizeSupplies;
                    if (sizeSupplies.contains("X-Small")) {
                        indexSizeSupplies = sizeSupplies.indexOf("X-Small");
                        size = sizeSupplies.substring(indexSizeSupplies, indexSizeSupplies + 7);
                    } else if (sizeSupplies.contains("Small")) {
                        indexSizeSupplies = sizeSupplies.indexOf("Small");
                        size = sizeSupplies.substring(indexSizeSupplies, indexSizeSupplies + 5);
                    } else if (sizeSupplies.contains("Medium")) {
                        indexSizeSupplies = sizeSupplies.indexOf("Medium");
                        size = sizeSupplies.substring(indexSizeSupplies, indexSizeSupplies + 6);
                    } else if (sizeSupplies.contains("XLarge")) {
                        indexSizeSupplies = sizeSupplies.indexOf("XLarge");
                        size = sizeSupplies.substring(indexSizeSupplies, indexSizeSupplies + 6);
                    } else if (sizeSupplies.contains("X-large")) {
                        indexSizeSupplies = sizeSupplies.indexOf("X-large");
                        size = sizeSupplies.substring(indexSizeSupplies, indexSizeSupplies + 7);
                    } else if (sizeSupplies.contains("Large")) {
                        indexSizeSupplies = sizeSupplies.indexOf("Large");
                        size = sizeSupplies.substring(indexSizeSupplies, indexSizeSupplies + 5);
                    } else {
                        size = "For all dogs";
                    }
                    dogSupplies.setSize(size);

                    //Set name
                    String name = nodeNames.item(i).getTextContent();
                    if (name.equals("Orvis Memory Foam Bolster Dog Bed")) {
                        System.out.println("AAA");
                    }
                    name = name.replace("\n", "").trim();
                    if (size.equals("For all dogs")) {
                        dogSupplies.setName(name);
                    } else {
                        dogSupplies.setName("[" + size + "] " + name);
                    }
                    //Set photo
                    String photo = nodePhotos.item(i).getAttributes().getNamedItem("src").getNodeValue();
                    photo = domainPhoto + photo;
                    photo = photo.replace("/secure_assets/", "");
                    dogSupplies.setPhoto(photo);
                    //Set title
                    String title = nodeTitles.item(i).getAttributes().getNamedItem("title").getNodeValue();
                    dogSupplies.setContent("[" + sizeSupplies + "]\n" + title);
                    //Set price
                    String priceString = "";
                    if (nodePrices.item(j) == null) {
                        priceString = nodeSale.item(j).getTextContent();
                    } else {
                        priceString = nodePrices.item(j).getTextContent();

                    }
                    priceString = priceString.replace("$", "");
                    String priceArray[] = priceString.split("-");
                    priceString = priceArray[priceArray.length - 1];
                    Float price = Float.parseFloat(priceString);
                    dogSupplies.setPrice(price);
                    //set category
                    dogSupplies.setCategory1(category);
                    //set primary key relationship w category table
                    dogSuppliesPK.setCategory(category.getIdcategory());
                    dogSupplies.setDogSuppliesPK(dogSuppliesPK);
                    //insert
                    System.out.println("Inserted " + (DogSupplyCrawler.count++) + " - " + dogSupplies.getName() + " breeds");

                    dogSuppliesDAO.insertDogSupplies(dogSupplies);

                }
            }
        } catch (IOException ex) {
            Logger.getLogger(DogSupplyTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DogSupplyTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
