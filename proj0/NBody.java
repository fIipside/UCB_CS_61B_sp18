/** Draw the universe. */
public class NBody{
    
    /** Return the radius from a given file.
     *  @param String The file name.
     */
    public static double readRadius(String fileName){
        In in = new In(fileName);
        in.readInt();
        double R = in.readDouble();
        return R;
    }

    /** Return all the planets from a given file.
     *  @param String The file name.
     */
    public static Planet[] readPlanets(String fileName){
        readRadius(fileName);
        In in = new In(fileName);
        int N = in.readInt();
        Planet[] planets = new Planet[N];
        in.readDouble();
        for (int i = 0; i < N; i++) {
            planets[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
        }
        return planets;
    }

    /** Set the background.  */
    private static String imageToDraw = "images/starfield.jpg";
  
    public static void main(String[] args){
        /** Enables double buffering.
		  * An animation technique where all drawing takes place on the offscreen canvas.
		  * Only when you call show() does your drawing get copied from the
		  * offscreen canvas to the onscreen canvas, where it is displayed
		  * in the standard drawing window. You don't have to understand this
		  * for CS61B. Just know that if you don't call this function, any attempt
		  * at smooth animation will look bad and flickery (remove it and see 
		  * what happens!). */
        StdDraw.enableDoubleBuffering();
        
        double T = Double.valueOf(args[0]);
        double dt = Double.valueOf(args[1]);
        String filename = args[2];
        Planet[] planets = readPlanets(filename);
        int N = planets.length;
        double R = readRadius(filename);
        StdDraw.setScale(-R, R);

        double time = 0;
        while (time <= T) {           
            /** Calculate the net force for each planet. */
            double[] xForces = new double[N];
            double[] yForces = new double[N];
            for (int i = 0; i < N; i ++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            
            /** Update due to forces calculated. */
            for (int i = 0; i < N; i ++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }
        
            /** Draw the background and all the planets. */
            StdDraw.picture(0, 0, imageToDraw);
            for (Planet p: planets) {
                p.draw();
            }
        
            /** Show the offscreen buffer and pause for 10 milliseconds. */
            StdDraw.show();
            StdDraw.pause(10);

            time += dt; 
        }   
        
        /** When the simulation is over, i.e. when you’ve reached time T, print out the final state of the universe in the same format as the input */
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", R);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                        planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                        planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }
    }
}