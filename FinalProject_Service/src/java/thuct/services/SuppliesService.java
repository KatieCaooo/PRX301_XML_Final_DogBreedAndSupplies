/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuct.services;

import java.util.ArrayList;
import java.util.List;
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
            minPriceList.add(dogSupplies);
            minPrice += dogSupplies.getPrice();
        }

        List<DogSupplies> maxPriceList = new ArrayList<>();
        Float maxPrice = 0F;
        for (int i = 1; i < 8; i++) {
            dogSupplies = dogSuppliesDAO.getMaxPrice(sizeSupplies, i);
            maxPriceList.add(dogSupplies);
            maxPrice += dogSupplies.getPrice();
        }

        Float currentSum = 0F;
        Float pricePossible = 0F;
        Float priceRadio = 0F;
        int num = 0;
        int sizeList = -1;
        if (priceHope >= maxPrice) {
            listSuppliesResult = maxPriceList;
        } else if (priceHope == minPrice) {
            listSuppliesResult = minPriceList;
        } else if (priceHope < minPrice) {
            for (int i = 1; i < 8; i++) {
                dogSupplies = dogSuppliesDAO.getMinPrice(sizeSupplies, i);
                currentSum += dogSupplies.getPrice();
                if (currentSum <= priceHope) {
                    listSuppliesResult.add(dogSupplies);
                } else {
                    currentSum -= dogSupplies.getPrice();
                }
            }
        } else if (priceHope < maxPrice) { //nếu giá mong muốn < MAX
            Float priceExcess = priceHope; //Tiền thừa hiện = HOPE
            for (int i = 1; i < 8; i++) {
                if (currentSum < priceHope) { //Nếu tiền tạm tính < HOPE
                    while (sizeList < i) { //Nếu listSuppliesResult < vòng lặp thì chạy
                        priceRadio = maxPrice / priceExcess; //Tỉ lệ giữa MAX với EXCESS
                        pricePossible = maxPriceList.get(i).getPrice() * priceRadio; //Tiền có thể dùng để mua
                        priceHopeList = dogSuppliesDAO.getSuppliesForPrice(sizeSupplies, i, pricePossible);//Lấy list hàng từ tiền có thể mua
                        if (priceHopeList.get(num).getPrice() <= pricePossible) { //nếu tiền của thứ đó <= có thể mua
                            currentSum += priceHopeList.get(num).getPrice(); //tính tiền
                            if (currentSum <= priceExcess) { //nhỏ hơn tiền thừa 
                                listSuppliesResult.add(priceHopeList.get(num)); //mua
                                sizeList++; //listSuppliesResult tăng
                                priceExcess = priceHope - currentSum; //tính tiền thừa
                            } else {//nếu không đủ tiền thì không mua
                                currentSum -= priceHopeList.get(num).getPrice();//trừ tiền lại
                                num +=0; //tăng position của priceHopeList để duyệt lại
                            }
                        }
                    }
                }
            }
            System.out.println("priceExcess " + priceExcess);
        }
        return listSuppliesResult;
    }

}
