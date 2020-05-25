package pl.edu.agh.soa;

import javax.ejb.Stateless;

@Stateless
public class SandwichDao {
    public static SandwichEntity sandwichToEntity(Sandwich sandwich) {
        SandwichEntity sandwichEntity = new SandwichEntity();
        sandwichEntity.setName(sandwich.getName());
        return sandwichEntity;
    }

    public static Sandwich entityToSandwich(SandwichEntity sandwichEntity) {
        Sandwich sandwich = new Sandwich();
        sandwich.setName(sandwichEntity.getName());
        return sandwich;
    }
}
