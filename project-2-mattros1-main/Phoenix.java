import java.util.Random;
public class Phoenix extends Creature{
    int timeSinceEaten;
    Creature target;
    public Phoenix(int x, int y, City cty, Random rnd) {
        super(x, y, cty, rnd);
        super.lab = LAB_ORANGE;
    }
    public void step(){
        super.step();
        super.step();
        super.step();
        super.step();
    }
    @Override
    public void takeAction() {
        if(target==null || (target.isDead())){
            if(super.rand.nextInt(19)==0){
                this.setDir(super.rand.nextInt(3));
            }
        }
        if(timeSinceEaten==50){
            int x,y;
            x=this.getX();
            y=this.getY();
            city.creaturesToAdd.add(new Egg(x, y, city, rand));
            this.dead=true;
        }
        for(Creature m: city.creatures){
            
            if(this.dist(m)==0){
                if(m.lab=='b'){
                    m.dead=true;
                    timeSinceEaten=0;

                }
                else if(m.lab=='c' || m.lab=='y' || m.lab=='r'|| m.lab=='k'){
                    m.dead=true;
                    timeSinceEaten=0;
                    
                }
            }   
    }
    if(target==null|| target.isDead()){
        target=null;
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
            if(!(c.lab=='e' || c.lab=='o')){
                if(this.dist(c)<=10){
                    target = c;
                //  System.out.println(this.toString()+ " target:"+ target);
                }
           }
        }
    }
    }

   

