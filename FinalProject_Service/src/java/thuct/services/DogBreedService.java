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

    private static final int BCHILDREN = 5;
    private static final int BADAP = 4;
    private static final int BHEALTH = 4;
    private static final int BHOME = 3;
    private static final int BSTRANGER = 5;
    private static final int BBARK = 3;
    private static final int BCAT = 3;
    private static final int BDOG = 3;
    private static final int BEXCER = 3;
    private static final int BPLAY = 4;
    private static final int BSMART = 3;
    private static final int BTRAIN = 4;
    private static final int BGROOM = 2;
    private static final int BSHED = 2;
    private static final int BWATCH = 4;

//    @GET
//    @Produces(MediaType.APPLICATION_XML)
//    public List<DogBreed> getAllDogBreeds() {
//        GenericDAO<DogBreed> genericDAO = new GenericDAO<>(DogBreed.class);
//        return genericDAO.findAll();
//    }

    @GET
    @Path("/recommendDog")
    @Produces(MediaType.APPLICATION_XML)
    public List<DogBreed> getRecommendDog(
            @QueryParam("item") int item,
            @QueryParam("row") int row,
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
            int rChildren = childrenW + BCHILDREN;
            int rHome = homeW + BHOME;
            int rAdap = adapW + BADAP;
            int rHealth = healthW + BHEALTH;
            int rStranger = strangerW + BSTRANGER;
            int rBark = barkW + BBARK;
            int rCat = catW + BCAT;
            int rDog = dogW + BDOG;
            int rExercise = excerciseW + BEXCER;
            int rPlay = playfulW + BPLAY;
            int rSmart = smartW + BSMART;
            int rTrain = trainingW + BTRAIN;
            int rGroom = groomW + BGROOM;
            int rShed = shedW + BSHED;
            int rWatch = watchdogW + BWATCH;
//            adap
            switch (adapW) {
                case 2:
                    if (listSizeResult.get(i).getAdaptability() < 4) {
                        adapS = (-1) * (5 - listSizeResult.get(i).getAdaptability()) * rAdap;
                    } else {
                        adapS = listSizeResult.get(i).getAdaptability() * rAdap;
                    }
                    break;
                case 1:
                    if (listSizeResult.get(i).getAdaptability() < 3) {
                        adapS = (-1) * (5 - listSizeResult.get(i).getAdaptability()) * rAdap;
                    } else {
                        adapS = listSizeResult.get(i).getAdaptability() * rAdap;
                    }
                    break;

            }
//            home
            switch (homeW) {
                case 3:
                    if (listSizeResult.get(i).getApartmentFriendly() < 4) {
                        homeS = (-1) * (5 - listSizeResult.get(i).getApartmentFriendly()) * rHome;
                    } else {
                        homeS = listSizeResult.get(i).getApartmentFriendly() * rHome;
                    }
                    break;
                case 2:
                    if (listSizeResult.get(i).getApartmentFriendly() < 3) {
                        homeS = (-1) * (5 - listSizeResult.get(i).getApartmentFriendly()) * rHome;
                    } else {
                        homeS = listSizeResult.get(i).getApartmentFriendly() * rHome;
                    }
                    break;
                default:
                    //whatever
                    homeS = listSizeResult.get(i).getApartmentFriendly() * rHome;
                    break;
            }
//            child
            switch (childrenW) {
                case 3:
                    if (listSizeResult.get(i).getChildFriendly() < 4) {
                        childS = (-1) * (5 - listSizeResult.get(i).getChildFriendly()) * rChildren;
                    } else {
                        childS = listSizeResult.get(i).getChildFriendly() * rChildren;
                    }
                    break;
                case 2:
//                    whatever
                    childS = listSizeResult.get(i).getChildFriendly() * rChildren;
                    break;
                default:
                    if (listSizeResult.get(i).getChildFriendly() < 3) {
                        childS = (5 - listSizeResult.get(i).getChildFriendly()) * (rChildren *= 3);
                    } else {
                        childS = (-1) * listSizeResult.get(i).getChildFriendly() * (rChildren *= 3);
                    }
                    break;
            }
//            stranger
            switch (strangerW) {
                case 3:
                    if (listSizeResult.get(i).getStrangerFriendly() < 3) {
                        strangerS = (-1) * (5 - listSizeResult.get(i).getStrangerFriendly()) * rStranger;
                    } else {
                        strangerS = listSizeResult.get(i).getStrangerFriendly() * rStranger;
                    }
                    break;
                case 2:
                    //whatever
                    strangerS = listSizeResult.get(i).getStrangerFriendly() * rStranger;
                    break;
                default:
                    if (listSizeResult.get(i).getStrangerFriendly() < 3) {
                        strangerS = (5 - listSizeResult.get(i).getStrangerFriendly()) * (rStranger *= 3);
                    } else {
                        strangerS = (-1) * listSizeResult.get(i).getStrangerFriendly() * (rStranger *= 3);
                    }
                    break;
            }
//             bark
            switch (barkW) {
                case 3:
                    if (listSizeResult.get(i).getBarkingTendency() < 4) {
                        barkS = (-1) * (5 - listSizeResult.get(i).getBarkingTendency()) * rBark;
                    } else {
                        barkS = listSizeResult.get(i).getBarkingTendency() * rBark;
                    }
                    break;
                case 2:
                    if (listSizeResult.get(i).getBarkingTendency() < 3) {
                        barkS = (-1) * (5 - listSizeResult.get(i).getBarkingTendency()) * rBark;
                    } else {
                        barkS = listSizeResult.get(i).getBarkingTendency() * rBark;
                    }
                    break;
                default:
                    if (listSizeResult.get(i).getBarkingTendency() < 3) {
                        barkS = (5 - listSizeResult.get(i).getBarkingTendency()) * (rBark *= 3);
                    } else {
                        barkS = (-1) * listSizeResult.get(i).getBarkingTendency() * (rBark *= 3);
                    }
                    break;
            }

//            health
            switch (healthW) {
                case 2:
                    if (listSizeResult.get(i).getHealthIssuse() < 4) {
                        healthS = (-1) * (5 - listSizeResult.get(i).getHealthIssuse()) * rHealth;
                    } else {
                        healthS = listSizeResult.get(i).getHealthIssuse() * rHealth;
                    }
                    break;
                case 1:
                    if (listSizeResult.get(i).getHealthIssuse() < 3) {
                        healthS = (-1) * (5 - listSizeResult.get(i).getHealthIssuse()) * rHealth;
                    } else {
                        healthS = listSizeResult.get(i).getHealthIssuse() * rHealth;
                    }
                    break;
            }
//            dog
            if (dogW == 2) {
                if (listSizeResult.get(i).getDogFriendly() < 3) {
                    dogS = (-1) * (5 - listSizeResult.get(i).getDogFriendly()) * rDog;
                } else {
                    dogS = listSizeResult.get(i).getDogFriendly() * rDog;
                }
            } else {
                //whatever
                dogS = listSizeResult.get(i).getDogFriendly() * rDog;
            }
//            cat
            if (catW == 2) {
                if (listSizeResult.get(i).getCatFriendly() < 3) {
                    healthS = (-1) * (5 - listSizeResult.get(i).getCatFriendly()) * rCat;
                } else {
                    catS = listSizeResult.get(i).getCatFriendly() * rCat;
                }
            } else {
                //whatever
                catS = listSizeResult.get(i).getCatFriendly() * rCat;
            }
//            training
            switch (trainingW) {
                case 3:
                    if (listSizeResult.get(i).getTrainability() < 4) {
                        trainingS = (-1) * (5 - listSizeResult.get(i).getTrainability()) * rTrain;
                    } else {
                        trainingS = listSizeResult.get(i).getTrainability() * rTrain;
                    }
                    break;
                case 2:
                    if (listSizeResult.get(i).getTrainability() < 3) {
                        trainingS = (-1) * (5 - listSizeResult.get(i).getTrainability()) * rTrain;
                    } else {
                        trainingS = listSizeResult.get(i).getTrainability() * rTrain;
                    }
                    break;
                default:
                    //whatever
                    trainingS = listSizeResult.get(i).getTrainability() * rTrain;
                    break;
            }
//            watchdog
            switch (watchdogW) {
                case 3:
                    if (listSizeResult.get(i).getWatchdogAbility() < 4) {
                        watchdogS = (-1) * (5 - listSizeResult.get(i).getWatchdogAbility()) * rWatch;
                    } else {
                        watchdogS = listSizeResult.get(i).getWatchdogAbility() * rWatch;
                    }
                    break;
                case 2:
                    if (listSizeResult.get(i).getWatchdogAbility() < 3) {
                        watchdogS = (-1) * (5 - listSizeResult.get(i).getWatchdogAbility()) * rWatch;
                    } else {
                        watchdogS = listSizeResult.get(i).getWatchdogAbility() * rWatch;
                    }
                    break;
                default:
                    if (listSizeResult.get(i).getWatchdogAbility() < 3) {
                        watchdogS = (5 - listSizeResult.get(i).getWatchdogAbility()) * (rWatch *= 3);
                    } else {
                        watchdogS = (-1) * listSizeResult.get(i).getWatchdogAbility() * (rWatch *= 3);
                    }
                    break;
            }
//            groom
            switch (groomW) {
                case 3:
                    if (listSizeResult.get(i).getGrooming() < 4) {
                        groomS = (-1) * (5 - listSizeResult.get(i).getGrooming()) * rGroom;
                    } else {
                        groomS = listSizeResult.get(i).getGrooming() * rGroom;
                    }
                    break;
                case 2:
                    if (listSizeResult.get(i).getGrooming() < 3) {
                        groomS = (-1) * (5 - listSizeResult.get(i).getGrooming()) * rGroom;
                    } else {
                        groomS = listSizeResult.get(i).getGrooming() * rGroom;
                    }
                    break;
                default:
                    if (listSizeResult.get(i).getGrooming() < 3) {
                        groomS = (5 - listSizeResult.get(i).getGrooming()) * (rGroom *= 3);
                    } else {
                        groomS = (-1) * listSizeResult.get(i).getGrooming() * (rGroom *= 3);
                    }
                    break;
            }

//            shed
            switch (shedW) {
                case 3:
                    if (listSizeResult.get(i).getSheddingLevel() < 3) {
                        shedS = (-1) * (5 - listSizeResult.get(i).getSheddingLevel()) * rShed;
                    } else {
                        shedS = listSizeResult.get(i).getSheddingLevel() * rShed;
                    }
                    break;
                case 2:
                    //whatever
                    shedS = listSizeResult.get(i).getSheddingLevel() * rShed;
                    break;
                default:
                    if (listSizeResult.get(i).getSheddingLevel() < 3) {
                        shedS = (5 - listSizeResult.get(i).getSheddingLevel()) * (rShed *= 3);
                    } else {
                        shedS = (-1) * listSizeResult.get(i).getSheddingLevel() * (rShed *= 3);
                    }
                    break;
            }
//            smart
            switch (smartW) {
                case 3:
                    if (listSizeResult.get(i).getIntelligence() < 4) {
                        smartS = (-1) * (5 - listSizeResult.get(i).getIntelligence()) * (rSmart);
                    } else {
                        smartS = listSizeResult.get(i).getIntelligence() * (rSmart);
                    }
                    break;
                case 2:
                    if (listSizeResult.get(i).getIntelligence() < 3) {
                        smartS = (-1) * (5 - listSizeResult.get(i).getIntelligence()) * (rSmart);
                    } else {
                        smartS = listSizeResult.get(i).getIntelligence() * (rSmart);
                    }
                    break;
                default:
                    //whatever
                    smartS = listSizeResult.get(i).getIntelligence() * (rSmart);
                    break;
            }
//            playful
            switch (playfulW) {
                case 3:
                    if (listSizeResult.get(i).getPlayfulness() < 4) {
                        playfulS = (-1) * (5 - listSizeResult.get(i).getPlayfulness()) * (rPlay);
                    } else {
                        playfulS = listSizeResult.get(i).getPlayfulness() * (rPlay);
                    }
                    break;
                case 2:
                    if (listSizeResult.get(i).getPlayfulness() < 3) {
                        playfulS = (-1) * (5 - listSizeResult.get(i).getPlayfulness()) * (rPlay);
                    } else {
                        playfulS = listSizeResult.get(i).getPlayfulness() * (rPlay);
                    }
                    break;
                default:
                    if (listSizeResult.get(i).getPlayfulness() < 3) {
                        playfulS = (5 - listSizeResult.get(i).getPlayfulness()) * (rPlay *= 3);
                    } else {
                        playfulS = (-1) * listSizeResult.get(i).getPlayfulness() * (rPlay *= 3);
                    }
                    break;
            }
//          excer
            switch (excerciseW) {
                case 4:
                    if (listSizeResult.get(i).getExerciseNeed() < 4) {
                        excerS = (-1) * (5 - listSizeResult.get(i).getExerciseNeed()) * (rExercise);
                    } else {
                        excerS = listSizeResult.get(i).getExerciseNeed() * (rExercise);
                    }
                    break;
                case 3:
                    if (listSizeResult.get(i).getExerciseNeed() < 4) {
                        excerS = (-1) * (5 - listSizeResult.get(i).getExerciseNeed()) * (rExercise);
                    } else {
                        excerS = listSizeResult.get(i).getExerciseNeed() * (rExercise);
                    }
                    break;
                case 2:
                    if (listSizeResult.get(i).getExerciseNeed() < 3) {
                        excerS = (-1) * (5 - listSizeResult.get(i).getExerciseNeed()) * (rExercise *= 3);
                    } else {
                        excerS = listSizeResult.get(i).getExerciseNeed() * (rExercise *= 3);
                    }
                    break;
                default:
                    if (listSizeResult.get(i).getExerciseNeed() < 3) {
                        excerS = (5 - listSizeResult.get(i).getExerciseNeed()) * (rExercise *= 4);
                    } else {
                        excerS = (-1) * listSizeResult.get(i).getExerciseNeed() * (rExercise *= 4);
                    }
                    break;
            }

            Float avgScore = (adapS + homeS + catS + childS + strangerS + barkS + healthS + dogS + trainingS + watchdogS + groomS + shedS + smartS + playfulS + excerS)
                    / (rAdap + rHome + rCat + rChildren + rStranger + rBark + rHealth + rDog + rTrain + rWatch + rGroom + rShed + rSmart + rPlay + rExercise);
            listDogScore.add(new DogBreedScore(listSizeResult.get(i), avgScore));
        }
        System.out.println("a");
        Collections.sort(listDogScore, (DogBreedScore o1, DogBreedScore o2) -> o2.getScore().compareTo(o1.getScore()));
        List<DogBreed> listResult = new ArrayList<>();
        for (int i = row * item; i < row * item + item; i++) {
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
