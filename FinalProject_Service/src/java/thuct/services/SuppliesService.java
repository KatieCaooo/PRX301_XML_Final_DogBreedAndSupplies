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
        int position = 0;
        int sizeList = 0;
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
            for (int i = 1; i < 8; i++) { //chạy category
                if (currentSum < priceHope) { //Nếu tiền tạm tính < HOPE
                    priceHopeList = dogSuppliesDAO.getSuppliesForPrice(sizeSupplies, i, priceExcess);//Lấy list hàng từ tiền có thể mua
                    position = getRandomNumber(0, priceHopeList.size() - 1);//set về 0 khi chạy category mới
                    while (sizeList < i && position < priceHopeList.size()) { //Nếu listSuppliesResult < vòng lặp thì chạy && position list<size priceHopeList
                        if (priceHopeList.get(position).getPrice() <= priceExcess) { //nếu tiền của thứ đó <= có thể mua
                            currentSum += priceHopeList.get(position).getPrice(); //tính tiền
                            listSuppliesResult.add(priceHopeList.get(position)); //mua
                            sizeList++; //listSuppliesResult tăng
                            priceExcess = priceHope - currentSum; //tính tiền thừa
                        } else {//nếu không đủ tiền thì không mua
                            currentSum -= priceHopeList.get(position).getPrice();//trừ tiền lại
                            position += 1; //tăng position của priceHopeList để duyệt lại
                        }
                    }
                }
            }
            System.out.println("priceExcess " + priceExcess);
        }
        return listSuppliesResult;
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
