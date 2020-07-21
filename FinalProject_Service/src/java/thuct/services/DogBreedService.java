/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuct.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import thuct.daos.DogBreedDAO;
import thuct.daos.GenericDAO;
import thuct.dtos.DogBreed;

/**
 *
 * @author kloecao
 */
@Path("/dog-breed")
public class DogBreedService {

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<DogBreed> getAllDogBreeds() {
        GenericDAO<DogBreed> genericDAO = new GenericDAO<>(DogBreed.class);
        return genericDAO.findAll();
    }

    @GET
    @Path("/recommendDog")
    @Produces(MediaType.APPLICATION_XML)
    public List<DogBreed> getRecommendDog(
            @QueryParam("size") String size,
            @QueryParam("experienceW") int experienceW,
            @QueryParam("homeW") int homeW,
            @QueryParam("childrenW") int childrenW,
            @QueryParam("strangerW") int strangerW,
            @QueryParam("barkW") int barkW,
            @QueryParam("catW") int catW,
            @QueryParam("dogW") int dogW,
            @QueryParam("excerciseW") int excerciseW,
            @QueryParam("playfulW") int playfulW,
            @QueryParam("smartW") int smartW,
            @QueryParam("trainingW") int trainingW,
            @QueryParam("watchdogW") int watchdogW,
            @QueryParam("groomW") int groomW,
            @QueryParam("shedW") int shedW) {
        int adapW, healthW;
        if (experienceW == 3) {
            adapW = 17;
            healthW = 17;
        } else if (experienceW == 2) {
            adapW = experienceW * 5;
            healthW = experienceW * 5;
        } else {
            adapW = 8;
            healthW = 8;
        }
        DogBreedDAO breedDAO = new DogBreedDAO();
        List<DogBreed> listSizeResult = breedDAO.getSizeDog(size);
        List<DogBreedScore> listDogScore = new ArrayList<>();
        for (int i = 0; i < listSizeResult.size(); i++) {
            Float adapS = listSizeResult.get(i).getAdaptability() * adapW;
            Float homeS = listSizeResult.get(i).getApartmentFriendly() * homeW;
            Float childS = listSizeResult.get(i).getChildFriendly() * childrenW;
            Float strangerS = listSizeResult.get(i).getStrangerFriendly() * strangerW;
            Float barkS = listSizeResult.get(i).getBarkingTendency() * barkW;
            Float healthS = listSizeResult.get(i).getHealthIssuse() * healthW;
            Float dogS = listSizeResult.get(i).getDogFriendly() * dogW;
            Float catS = listSizeResult.get(i).getCatFriendly() * catW;
            Float trainingS = listSizeResult.get(i).getTrainability() * trainingW;
            Float watchdogS = listSizeResult.get(i).getWatchdogAbility() * watchdogW;
            Float groomS = listSizeResult.get(i).getGrooming() * groomW;
            Float shedS = listSizeResult.get(i).getSheddingLevel() * shedW;
            Float smartS = listSizeResult.get(i).getIntelligence() * smartW;
            Float playfulS = listSizeResult.get(i).getPlayfulness() * playfulW;
            Float excerS = listSizeResult.get(i).getExerciseNeed() * excerciseW;
            Float avgScore = (adapS + homeS + catS + childS + strangerS + barkS + healthS + dogS + trainingS + watchdogS + groomS + shedS + smartS + playfulS + excerS)
                    / (adapW + homeW + catW + childrenW + strangerW + barkW + healthW + dogW + trainingW + watchdogW + groomW + shedW + smartW + playfulW + excerciseW);
            listDogScore.add(new DogBreedScore(listSizeResult.get(i), avgScore));
        }
        Collections.sort(listDogScore, (DogBreedScore o1, DogBreedScore o2) -> o1.getScore().compareTo(o2.getScore()));
        List<DogBreed> listResult = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            listResult.add(listDogScore.get(i).getDogBreed());
        }
        return listResult;
    }

    class DogBreedScore {

        private DogBreed dogBreed;
        private Float score;

        public DogBreedScore(DogBreed dogBreed, Float score) {
            this.dogBreed = dogBreed;
            this.score = score;
        }

        public DogBreed getDogBreed() {
            return dogBreed;
        }

        public Float getScore() {
            return score;
        }

    }

}
