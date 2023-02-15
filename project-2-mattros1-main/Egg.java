import java.util.Random;
public class Egg extends Creature{
    int age=0;
    public Egg(int x, int y, City cty, Random rnd){
        super(x,y,cty,rnd);
        super.lab=LAB_GRAY;
    }
    public void step(){

    }
    @Override
    public void takeAction() {
        // TODO Auto-generated method stub
        
        if(age==100){
            dead=true;
            int x=getX();
            int y=getY();
            city.creaturesToAdd.add(new Phoenix (x, y, city, rand));
            this.dead=true;
        }
        age++;
    }

}
