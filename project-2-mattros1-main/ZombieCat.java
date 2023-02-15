import java.util.Random;

public class ZombieCat extends Creature{
    Creature target;
    int timeSinceEaten;
    public ZombieCat(int x, int y, City cty, Random rnd){
        super(x, y, cty, rnd);
        super.lab='r';
    }
    public void step(){
        super.step();
        super.step();
        super.step();
        
    }
    public void takeAction(){
        if(target==null || !(target.isDead()));{
            if(super.rand.nextInt(19)==0){
                this.setDir(super.rand.nextInt(3));
            }
        }
        if(timeSinceEaten==100){
            this.dead=true;
        }
       
        for(Creature m: city.creatures){
            
                if(this.dist(m)==0){
                    if(m.lab=='b'){
                        m.dead=true;
                        timeSinceEaten=0;
                    }
                    else if(m.lab=='c' || m.lab=='y'){
                        int x,y;
                        x=this.getX();
                        y=this.getY();
                        city.creaturesToAdd.add(new ZombieCat (x, y, city, rand));
                        m.dead=true;
                        timeSinceEaten=0;
                    }
                }   
        }
            
           if(target==null|| target.isDead()){
                target=null;
                this.lab='r';
                this.setTarget();
            }
            else if(!target.isDead()){
                if(Math.abs(getX()-target.getX())>=Math.abs(getY()-target.getY())){
                    if(getX()<=target.getX()){
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
    public void setTarget(){
        for(Creature c:city.creatures){
            if(!(c.lab=='r' || c.lab=='k' || c.lab=='o' || c.lab=='e')){
                if(this.dist(c)<=20){
                    target = c;
                    this.lab='k';
                //  System.out.println(this.toString()+ " target:"+ target);
                }
            }
        }
    }

}
