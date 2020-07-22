
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import thuct.services.AccountService;
import thuct.services.DogBreedService;
import thuct.services.SuppliesService;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kloecao
 */
@ApplicationPath("/")
public class MyApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        h.add(DogBreedService.class);
        h.add(AccountService.class);
        h.add(SuppliesService.class);
        return h;
    }

}
