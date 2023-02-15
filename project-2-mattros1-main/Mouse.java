import java.util.Random;
public class Mouse extends Creature {
    int age=0;
    public Mouse(int x, int y, City cty, Random rnd) {
        super(x, y, cty, rnd);
        super.lab = LAB_BLUE;
    }

    public void takeAction(){
        age++;
        if(super.rand.nextInt(4)==0){
            this.setDir(super.rand.nextInt(3));
        }
        if(age==30){
            this.dead=true;
        }
        else if(age==20){
            this.city.creaturesToAdd.add(new Mouse (this.getX(),this.getY(),this.city,super.rand));
        }

    }

    
}
