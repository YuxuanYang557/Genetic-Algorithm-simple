package neu.edu.GAprogram;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static jdk.nashorn.internal.objects.NativeMath.random;

//this project is to solve the "to be or not to be"
public class GeneticAlgorithm {

    //Random class is really useful when generate random number
    Random random = new Random();
    //Population an array of DNA --- represent the variation in Darwinian evolution theory
    DNA[] population;
    //An arraylsit of DNA used for the reproduction process
    List<DNA> matingPool;
    //Describe how likely the mutation will happen after crossover
    double mutationRate;

    String target;

    //initialize all the population
    public void setup(int num_population, double rate, String target) {
        //initialize all the variables
        this.target = target;
        mutationRate = rate;
        population = new DNA[num_population];
        matingPool = new ArrayList<DNA>();

        for(int i = 0; i< population.length; i++) {
            population[i] = new DNA(target);
        }
    }
    //calculate the fitness corresponding to each population in the array of DNA
    public void draw() {
        for (int i = 0; i < population.length; i++) {
            population[i].fitness();
            System.out.println(population[i].genes);
        }

        //put these DNAs into the mating pool according to there fitness
        //the higher the fitness, the more likely it will be chosen as the parent
        //public void createMatingPool() {
        for (int i = 0; i < population.length; i++) {
            int n = (int) (population[i].fitness * 1000);
            for (int j = 0; j < n; j++) {
                matingPool.add(population[i]);
            }
        }

        if(matingPool.size() == 0){
            draw();
        }
        System.out.println("------------------------------------------------");
        //Arbitrary chose the number of parent to produce the next generation
        //public void reproduction() {
        for (int i = 0; i < population.length; i++) {
            int a = (int) random(matingPool.size());
            int b = (int) random(matingPool.size());
            DNA parternA = matingPool.get(a);
            DNA parternB = matingPool.get(b);

            DNA child = parternA.crossover(parternB);
            child.mutation(mutationRate);

            population[i] = child;
        }
    }
}
