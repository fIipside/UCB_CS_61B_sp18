/** Writing the Planet Class. */
public class Planet{
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    
    /** Declare G as a constant using static final. */
    public static final double G = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    /** Initialize a copy of the planet. 
    *   @param Planet to be analyzed. 
    */
    public Planet(Planet p){
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    /** Calculate the distance between two planets.  
    *   @param Planet to be analyzed. 
    */
    public double calcDistance(Planet p){
        double dx = p.xxPos - this.xxPos;
        double dy = p.yyPos - this.yyPos;
        return Math.pow(dx * dx + dy * dy, 0.5);
    }

    /** Calculate the force between two planets.  
    *   @param Planet to be analyzed. 
    */    
    public double calcForceExertedBy(Planet p){
        return G * this.mass * p.mass / (this.calcDistance(p) * this.calcDistance(p));
    }

    /** Calculate the force between two planets on X axis.  
    *   @param Planet to be analyzed. 
    */  
    public double calcForceExertedByX(Planet p){
        double dx = p.xxPos - this.xxPos;
        return this.calcForceExertedBy(p) * dx / this.calcDistance(p);
    }

    /** Calculate the force between two planets on Y axis.  
    *   @param Planet to be analyzed. 
    */  
    public double calcForceExertedByY(Planet p){
        double dy = p.yyPos - this.yyPos;
        return this.calcForceExertedBy(p) * dy / this.calcDistance(p);
    }

    /** Calculate the Net force between a planet and an array of planets on X axis.  
    *   @param Planet[] an array of planets to be analyzed. 
    */  
    public double calcNetForceExertedByX(Planet[] allPlanets){
        double NetForce = 0;
        for (Planet p : allPlanets) {
            if (this.equals(p)) {
                continue;
            }
            NetForce += this.calcForceExertedByX(p);
        }
        return NetForce;
    }

    /** Calculate the Net force between a planet and an array of planets on Y axis.  
    *   @param Planet[] an array of planets to be analyzed. 
    */  
    public double calcNetForceExertedByY(Planet[] allPlanets){
        double NetForce = 0;
        for (Planet p : allPlanets) {
            if (this.equals(p)) {
                continue;
            }
            NetForce += this.calcForceExertedByY(p);
        }
        return NetForce;
    }

    /** Update the status of the planet.  
    *   @param dt a small period of time. 
    *   @param fX the force on X axis. 
    *   @param fY the force on Y axis. 
    */ 
    public void update(double dt, double fX, double fY){
        double ax = fX / mass;
        double ay = fY / mass;
        xxVel += ax * dt;
        yyVel += ay * dt;
        xxPos += xxVel * dt;
        yyPos += yyVel * dt;
    }

    /** Draw the Planet’s image at the Planet’s position. */
    public void draw(){
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}