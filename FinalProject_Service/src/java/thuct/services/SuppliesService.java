/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuct.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import thuct.daos.DogSuppliesDAO;
import thuct.dtos.DogSupplies;

/**
 *
 * @author kloecao
 */
@Path("/dog-supplies")
public class SuppliesService {

    @GET
    @Path("/recommendSupplies")
    @Produces(MediaType.APPLICATION_XML)
    public static List<DogSupplies> listSuggestSupplies(@QueryParam("sizeDog") String sizeDog,
            @QueryParam("priceHope") Float priceHope) {
        DogSuppliesDAO dogSuppliesDAO = new DogSuppliesDAO();
        String sizeSupplies;
        switch (sizeDog) {
            case "Smallest":
                sizeSupplies = "X-Small";
                break;
            case "Small":
                sizeSupplies = "Small";
                break;
            case "Medium":
                sizeSupplies = "Medium";
                break;
            case "Large":
                sizeSupplies = "Large";
                break;
            case "Giant":
                sizeSupplies = "X-Large";
                break;
            default:
                sizeSupplies = "";
                break;
        }
        List<DogSupplies> listSuppliesResult = new ArrayList<>();
        DogSupplies dogSupplies;
        List<DogSupplies> priceHopeList = new ArrayList<>();
        List<DogSupplies> minPriceList = new ArrayList<>();

        Float minPrice = 0F;
        for (int i = 1; i < 8; i++) {
            dogSupplies = dogSuppliesDAO.getMinPrice(sizeSupplies, i);
            minPrice += dogSupplies.getPrice();
        }

        Float maxPrice = 0F;
        for (int i = 1; i < 8; i++) {
            dogSupplies = dogSuppliesDAO.getMaxPrice(sizeSupplies, i);
            maxPrice += dogSupplies.getPrice();
        }

        Float currentSum = 0F;
        Float pricePossible = 0F;
        Float priceRadio = 0F;

        if (priceHope >= maxPrice) {
            for (int i = 1; i < 8; i++) {
                dogSupplies = dogSuppliesDAO.getMaxPrice(sizeSupplies, i);
                listSuppliesResult.add(dogSupplies);
            }
        } else if (priceHope <= minPrice) {
            for (int i = 1; i < 8; i++) {
                dogSupplies = dogSuppliesDAO.getMinPrice(sizeSupplies, i);
                currentSum += dogSupplies.getPrice();
                if (currentSum <= priceHope) {
                    listSuppliesResult.add(dogSupplies);
                } else {
                    currentSum -= dogSupplies.getPrice();
                }
            }
        } else if (priceHope < maxPrice && priceHope > minPrice) { //nếu giá mong muốn < MAX
            priceRadio = priceHope / maxPrice; //Tỉ lệ giữa MAX với EXCESS
            for (int i = 1; i < 8; i++) { //chạy category
                if (currentSum < priceHope) { //Nếu tiền tạm tính < HOPE
                    dogSupplies = dogSuppliesDAO.getMaxPrice(sizeSupplies, i);
                    pricePossible = dogSupplies.getPrice() * priceRadio; //Tiền có thể dùng để mua
                    priceHopeList = dogSuppliesDAO.getSuppliesForPrice(sizeSupplies, i, pricePossible);//Lấy list hàng từ tiền có thể mua
                    if (!priceHopeList.isEmpty()) {
                        listSuppliesResult.add(priceHopeList.get(0)); //mua
                        currentSum += priceHopeList.get(0).getPrice(); //tính tiền
                    } else {
                        minPriceList.add(dogSuppliesDAO.getMinPrice(sizeDog, i));
                        listSuppliesResult.add(minPriceList.get(0));
                        currentSum += minPriceList.get(0).getPrice();
                    }
                }
            }
            boolean[] status = new boolean[7];
            for (int i = 0; i < status.length; i++) {
                status[i] = false;
            }
            while (!IntStream.range(0, status.length).allMatch(i -> status[i])) {
                for (int i = 0; i < listSuppliesResult.size(); i++) {
                    if (!status[i]) {
                        dogSupplies = null;
                        try {
                            dogSupplies = dogSuppliesDAO.getFirstGreater(sizeDog, i + 1, listSuppliesResult.get(i).getPrice());
                        } catch (NoResultException e) {
                            status[i] = true;
                        }
                        if (!status[i]) {
                            currentSum += (dogSupplies.getPrice() - listSuppliesResult.get(i).getPrice());
                            if (currentSum <= priceHope) {
                                listSuppliesResult.set(i, dogSupplies);
                            } else {
                                currentSum -= (dogSupplies.getPrice() - listSuppliesResult.get(i).getPrice());
                                status[i] = true;
                            }
                        }
                    }
                    System.out.println(currentSum);
                }
            }
            Float excess = priceHope - currentSum;
            System.out.println("excess: " + excess);
        }
        return listSuppliesResult;
    }

}
