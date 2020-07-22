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

        adapW = healthW = experienceW;
        DogBreedDAO breedDAO = new DogBreedDAO();
        List<DogBreed> listSizeResult = breedDAO.getSizeDog(size);
        List<DogBreedScore> listDogScore = new ArrayList<>();
        Float adapS = null, homeS = null, childS = null, strangerS = null,
                barkS = null, healthS = null, dogS = null, catS = null,
                trainingS = null, watchdogS = null, groomS = null, shedS = null,
                smartS = null, playfulS = null, excerS = null;
        for (int i = 0; i < listSizeResult.size(); i++) {
//            adap
            switch (adapW) {
                case 3:
                    if (listSizeResult.get(i).getAdaptability() < 4) {
                        adapS = (-1) * (5 - listSizeResult.get(i).getAdaptability()) * (4 + adapW);
                    } else {
                        adapS = listSizeResult.get(i).getAdaptability() * (4 + adapW);
                    }
                    break;
                case 2:
                    if (listSizeResult.get(i).getAdaptability() < 3) {
                        adapS = (-1) * (5 - listSizeResult.get(i).getAdaptability()) * (4 + adapW);
                    } else {
                        adapS = listSizeResult.get(i).getAdaptability() * (4 + adapW);
                    }
                    break;
                default:
                    adapS = listSizeResult.get(i).getAdaptability() * (4 + adapW);
                    break;
            }
//            home
            switch (homeW) {
                case 3:
                    if (listSizeResult.get(i).getApartmentFriendly() < 4) {
                        homeS = (-1) * (5 - listSizeResult.get(i).getApartmentFriendly()) * (4 + homeW);
                    } else {
                        homeS = listSizeResult.get(i).getApartmentFriendly() * (4 + homeW);
                    }
                    break;
                case 2:
                    if (listSizeResult.get(i).getApartmentFriendly() < 3) {
                        homeS = (-1) * (5 - listSizeResult.get(i).getApartmentFriendly()) * (4 + homeW);
                    } else {
                        homeS = listSizeResult.get(i).getApartmentFriendly() * (4 + homeW);
                    }
                    break;
                default:
                    homeS = listSizeResult.get(i).getApartmentFriendly() * (4 + homeW);
                    break;
            }
//            child
            switch (childrenW) {
                case 3:
                    if (listSizeResult.get(i).getChildFriendly() < 4) {
                        childS = (-1) * (5 - listSizeResult.get(i).getChildFriendly()) * (5 + childrenW);
                    } else {
                        childS = listSizeResult.get(i).getChildFriendly() * (5 + childrenW);
                    }
                    break;
                case 2:
                    if (listSizeResult.get(i).getChildFriendly() < 3) {
                        childS = (-1) * (5 - listSizeResult.get(i).getChildFriendly()) * (5 + childrenW);
                    } else {
                        childS = listSizeResult.get(i).getChildFriendly() * (5 + childrenW);
                    }
                    break;
                default:
                    childS = listSizeResult.get(i).getChildFriendly() * (5 + childrenW);
                    break;
            }
//            stranger
            switch (strangerW) {
                case 3:
                    if (listSizeResult.get(i).getStrangerFriendly() < 4) {
                        strangerS = (-1) * (5 - listSizeResult.get(i).getStrangerFriendly()) * (5 + strangerW);
                    } else {
                        strangerS = listSizeResult.get(i).getStrangerFriendly() * (5 + strangerW);
                    }
                    break;
                case 2:
                    if (listSizeResult.get(i).getStrangerFriendly() < 3) {
                        strangerS = (-1) * (5 - listSizeResult.get(i).getStrangerFriendly()) * (5 + strangerW);
                    } else {
                        strangerS = listSizeResult.get(i).getStrangerFriendly() * (5 + strangerW);
                    }
                    break;
                default:
                    strangerS = (-1) * (5 - listSizeResult.get(i).getStrangerFriendly()) * (5 + strangerW);
                    break;
            }
//             bark
            switch (barkW) {
                case 3:
                    if (listSizeResult.get(i).getBarkingTendency() < 4) {
                        barkS = (-1) * (5 - listSizeResult.get(i).getBarkingTendency()) * (2 + barkW);
                    } else {
                        barkS = listSizeResult.get(i).getBarkingTendency() * (2 + barkW);
                    }
                    break;
                case 2:
                    if (listSizeResult.get(i).getBarkingTendency() < 3) {
                        barkS = (-1) * (5 - listSizeResult.get(i).getBarkingTendency()) * (2 + barkW);
                    } else {
                        barkS = listSizeResult.get(i).getBarkingTendency() * (2 + barkW);
                    }
                    break;
                default:
                    barkS = (-1) * (5 - listSizeResult.get(i).getBarkingTendency()) * (2 + barkW);
                    break;
            }

//            health
            switch (healthW) {
                case 3:
                    if (listSizeResult.get(i).getHealthIssuse() < 4) {
                        healthS = (-1) * (5 - listSizeResult.get(i).getHealthIssuse()) * (4 + healthW);
                    } else {
                        healthS = listSizeResult.get(i).getHealthIssuse() * (4 + healthW);
                    }
                    break;
                case 2:
                    if (listSizeResult.get(i).getHealthIssuse() < 3) {
                        healthS = (-1) * (5 - listSizeResult.get(i).getHealthIssuse()) * (4 + healthW);
                    } else {
                        healthS = listSizeResult.get(i).getHealthIssuse() * (4 + healthW);
                    }
                    break;
                default:
                    healthS = listSizeResult.get(i).getHealthIssuse() * (4 + healthW);
                    break;
            }
//            dog
            if (dogW == 2) {
                if (listSizeResult.get(i).getDogFriendly() < 3) {
                    dogS = (-1) * (5 - listSizeResult.get(i).getDogFriendly()) * (3 + dogW);
                } else {
                    dogS = listSizeResult.get(i).getDogFriendly() * (3 + dogW);
                }
            } else {
                dogS = listSizeResult.get(i).getDogFriendly() * (3 + dogW);
            }
//            cat
            if (catW == 2) {
                if (listSizeResult.get(i).getCatFriendly() < 3) {
                    healthS = (-1) * (5 - listSizeResult.get(i).getCatFriendly()) * (3 + catW);
                } else {
                    catS = listSizeResult.get(i).getCatFriendly() * (3 + catW);
                }
            } else {
                catS = listSizeResult.get(i).getCatFriendly() * (3 + catW);
            }
//            training
            switch (trainingW) {
                case 3:
                    if (listSizeResult.get(i).getTrainability() < 4) {
                        trainingS = (-1) * (5 - listSizeResult.get(i).getTrainability()) * (4 + trainingW);
                    } else {
                        trainingS = listSizeResult.get(i).getTrainability() * (4 + trainingW);
                    }
                    break;
                case 2:
                    if (listSizeResult.get(i).getTrainability() < 3) {
                        trainingS = (-1) * (5 - listSizeResult.get(i).getTrainability()) * (4 + trainingW);
                    } else {
                        trainingS = listSizeResult.get(i).getTrainability() * (4 + trainingW);
                    }
                    break;
                default:
                    trainingS = listSizeResult.get(i).getTrainability() * (4 + trainingW);
                    break;
            }
//            watchdog
            switch (watchdogW) {
                case 3:
                    if (listSizeResult.get(i).getWatchdogAbility() < 4) {
                        watchdogS = (-1) * (5 - listSizeResult.get(i).getWatchdogAbility()) * (4 + watchdogW);
                    } else {
                        watchdogS = listSizeResult.get(i).getWatchdogAbility() * (4 + watchdogW);
                    }
                    break;
                case 2:
                    if (listSizeResult.get(i).getWatchdogAbility() < 3) {
                        watchdogS = (-1) * (5 - listSizeResult.get(i).getWatchdogAbility()) * (4 + watchdogW);
                    } else {
                        watchdogS = listSizeResult.get(i).getWatchdogAbility() * (4 + watchdogW);
                    }
                    break;
                default:
                    watchdogS = (-1) * (5 - listSizeResult.get(i).getWatchdogAbility()) * (4 + watchdogW);
                    break;
            }
//            groom
            switch (groomW) {
                case 3:
                    if (listSizeResult.get(i).getGrooming() < 4) {
                        groomS = (-1) * (5 - listSizeResult.get(i).getGrooming()) * (2 + groomW);
                    } else {
                        groomS = listSizeResult.get(i).getGrooming() * (2 + groomW);
                    }
                    break;
                case 2:
                    if (listSizeResult.get(i).getGrooming() < 3) {
                        groomS = (-1) * (5 - listSizeResult.get(i).getGrooming()) * (2 + groomW);
                    } else {
                        groomS = listSizeResult.get(i).getGrooming() * (2 + groomW);
                    }
                    break;
                default:
                    groomS = (-1) * (listSizeResult.get(i).getGrooming()) * (2 + groomW);
                    break;
            }

//            shed
            switch (shedW) {
                case 3:
                    if (listSizeResult.get(i).getSheddingLevel() < 4) {
                        shedS = (-1) * (5 - listSizeResult.get(i).getSheddingLevel()) * (2 + shedW);
                    } else {
                        shedS = listSizeResult.get(i).getSheddingLevel() * (2 + shedW);
                    }
                    break;
                case 2:
                    if (listSizeResult.get(i).getSheddingLevel() < 3) {
                        shedS = (-1) * (5 - listSizeResult.get(i).getSheddingLevel()) * (2 + shedW);
                    } else {
                        shedS = listSizeResult.get(i).getSheddingLevel() * (2 + shedW);
                    }
                    break;
                default:
                    shedS = (-1) * (listSizeResult.get(i).getSheddingLevel()) * (2 + shedW);
                    break;
            }
//            smart
            switch (smartW) {
                case 3:
                    if (listSizeResult.get(i).getIntelligence() < 4) {
                        smartS = (-1) * (5 - listSizeResult.get(i).getIntelligence()) * (4 + smartW);
                    } else {
                        smartS = listSizeResult.get(i).getIntelligence() * (4 + smartW);
                    }
                    break;
                case 2:
                    if (listSizeResult.get(i).getIntelligence() < 3) {
                        smartS = (-1) * (5 - listSizeResult.get(i).getIntelligence()) * (4 + smartW);
                    } else {
                        smartS = listSizeResult.get(i).getIntelligence() * (4 + smartW);
                    }
                    break;
                default:
                    smartS = listSizeResult.get(i).getIntelligence() * (4 + smartW);
                    break;
            }
//            playful
            switch (playfulW) {
                case 3:
                    if (listSizeResult.get(i).getPlayfulness() < 4) {
                        playfulS = (-1) * (5 - listSizeResult.get(i).getPlayfulness()) * (3 + playfulW);
                    } else {
                        playfulS = listSizeResult.get(i).getPlayfulness() * (3 + playfulW);
                    }
                    break;
                case 2:
                    if (listSizeResult.get(i).getPlayfulness() < 3) {
                        playfulS = (-1) * (5 - listSizeResult.get(i).getPlayfulness()) * (3 + playfulW);
                    } else {
                        playfulS = listSizeResult.get(i).getPlayfulness() * (3 + playfulW);
                    }
                    break;
                default:
                    playfulS = (-1) * (5 - listSizeResult.get(i).getPlayfulness()) * (3 + playfulW);
                    break;
            }
//          excer
            switch (excerciseW) {
                case 4:
                case 3:
                    if (listSizeResult.get(i).getExerciseNeed() < 4) {
                        excerS = (-1) * (5 - listSizeResult.get(i).getExerciseNeed()) * (4 + excerciseW);
                    } else {
                        excerS = listSizeResult.get(i).getExerciseNeed() * (4 + excerciseW);
                    }
                    break;
                case 2:
                    if (listSizeResult.get(i).getExerciseNeed() < 3) {
                        excerS = (-1) * (5 - listSizeResult.get(i).getExerciseNeed()) * (4 + excerciseW);
                    } else {
                        excerS = listSizeResult.get(i).getExerciseNeed() * (4 + excerciseW);
                    }
                    break;
                default:
                    excerS = (-1) * (5 - listSizeResult.get(i).getExerciseNeed()) * (4 + excerciseW);
                    break;
            }

            Float avgScore = (adapS + homeS + catS + childS + strangerS + barkS + healthS + dogS + trainingS + watchdogS + groomS + shedS + smartS + playfulS + excerS)
                    / (adapW + homeW + catW + childrenW + strangerW + barkW + healthW + dogW + trainingW + watchdogW + groomW + shedW + smartW + playfulW + excerciseW);
            listDogScore.add(new DogBreedScore(listSizeResult.get(i), avgScore));
        }
        System.out.println("a");
        Collections.sort(listDogScore, (DogBreedScore o1, DogBreedScore o2) -> o2.getScore().compareTo(o1.getScore()));
        List<DogBreed> listResult = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
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

    @GET
    @Path("/recommendDog/detail")
    @Produces(MediaType.APPLICATION_XML)
    public DogBreed getDetailDog(@QueryParam("idDog") int idDog) {
        DogBreedDAO breedDAO = new DogBreedDAO();
        DogBreed breed = breedDAO.getDogID(idDog);
        return breed;
    }
}
