/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuct.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import thuct.daos.DogBreedDAO;
import thuct.dtos.DogBreed;

/**
 *
 * @author kloecao
 */
@Path("/dog-breed")
public class DogBreedService {

    @GET
    @Produces("application/xml")
    public DogBreed getDogBreedById(@PathParam("id") int id) {
        DogBreedDAO breedDAO = new DogBreedDAO();
        DogBreed tmp = breedDAO.findDogBreedById(1);
        tmp.setName("hihi");
        breedDAO.updateDogBreed(tmp);
        return breedDAO.findDogBreedById(1);
    }
}
