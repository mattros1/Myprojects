
import java.util.Random;


public class Cat extends Creature {
    public int timeSinceEaten=0;
    Creature target;
    public Cat(int x, int y, City cty, Random rnd) {
        super(x, y, cty, rnd);
        super.lab = LAB_YELLOW;
    }
    public void step(){
        super.step();
        super.step();
        
    }
    public void takeAction(){
        if(target==null || (target.isDead()));{
            if(super.rand.nextInt(19)==0){
                this.setDir(super.rand.nextInt(3));
            }
        }
        if(timeSinceEaten==50){
            int x,y;
            x=this.getX();
            y=this.getY();
            city.creaturesToAdd.add(new ZombieCat(x, y, city, rand));
            this.dead=true;
        }
       
        for(Creature m: city.creatures){
            if(m.lab=='b'){
                if(this.dist(m)==0){
                    m.dead=true;
                    timeSinceEaten=0;
                }
            }
        }
           if(target==null || target.isDead()){
                this.lab='y';
                target=null;
                this.setTarget();
            }
            else if(!target.isDead()){
                if(Math.abs(getX()-target.getX())>Math.abs(getY()-target.getY())){
                    if(getX()<target.getX()){
                        this.setDir(EAST);
                    }
                    else{
                        this.setDir(WEST);
                    }
                }
                else{
                    if(getY()<target.getY()){
                        this.setDir(SOUTH);
                    }
                    else{
                        this.setDir(NORTH);
                    }
                }
            }
            timeSinceEaten++;
    }
    public void setDeathNum(int x){
        this.timeSinceEaten=x;
    }
    public void setTarget(){
        for(Creature c:city.creatures){
            if(c.lab=='b'){
                if(this.dist(c)<=20){
                    target = c;
                    this.lab='c';
                //  System.out.println(this.toString()+ " target:"+ target);
                }
           }
        }
    }
    
}
