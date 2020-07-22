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
            @QueryParam("priceHope") String priceHope) {
        DogSuppliesDAO dogSuppliesDAO = new DogSuppliesDAO();
        List<DogSupplies> listSuppliesResult = dogSuppliesDAO.getAnotherSupplies();
        String sizeBed;
        switch (sizeDog) {
            case "Smallest":
                sizeBed = "X-small";
                break;
            case "Small":
                sizeBed = "Small";
                break;
            case "Medium":
                sizeBed = "Medium";
                break;
            case "Large":
                sizeBed = "Large";
                break;
            case "Giant":
                sizeBed = "X-Large";
                break;
            default:
                sizeBed = "";
                break;
        }
        listSuppliesResult.addAll(dogSuppliesDAO.getBedBySize(sizeBed));

        return listSuppliesResult;
    }

}
