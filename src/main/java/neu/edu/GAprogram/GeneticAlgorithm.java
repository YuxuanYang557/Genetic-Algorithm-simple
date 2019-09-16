package neu.edu.GAprogram;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//this project is to solve the "to be or not to be"
public class GeneticAlgorithm {

    //Random class is really useful when generate random number
    Random random = new Random();
    //Population an array of DNA --- represent the variation in Darwinian evolution theory
    DNA[] population = new DNA[100];
    //An arraylsit of DNA used for the reproduction process
    List<DNA> matingPool = new ArrayList<DNA>();
    //Describe how likely the mutation will happen after crossover
    double mutationRate = 0.1;

    //initialize all the population
    public void setup() {
        for(int i = 0; i< population.length; i++) {
            population[i] = new DNA();
        }
    }
    //calculate the fitness corresponding to each population in the array of DNA
    public void draw() {
        for(int i = 0; i< population.length; i++) {
            population[i].fitness();
        }
    }
    //put these DNAs into the mating pool according to there fitness
    //the higher the fitness, the more likely it will be chosen as the parent
    public void createMatingPool() {
        for(int i =0; i< population.length; i++) {
            int n = (int) (population[i].fitness * 1000);
            for(int j = 0; j< n; j++) {
                matingPool.add(population[i]);
            }
        }
    }
    //Arbitrary chose the number of parent to produce the next generation
    public void reproduction() {
        for(int i = 0; i< population.length; i++) {
            int a = (int) random.nextInt(matingPool.size());
            int b = (int) random.nextInt(matingPool.size());
            DNA parternA = matingPool.get(a);
            DNA parternB = matingPool.get(b);

            DNA child = parternA.crossover(parternB);
            child.mutation(mutationRate);

            population[i] = child;
        }
    }

    //the subclass of the GA class
    public class DNA{
        String target = "to be or not to be";
        //each DNA, each individual in the population has its fitness toward the target
        float fitness;
        //the array of char is to represent the unique DNA sequence
        char[] genes = new char[18];

        //the constructor --- generate the random information
        public DNA() {
            for(int i = 0; i< genes.length; i++) {
                //we want to chose the character between 32-128 to represent letters in ASCII
                //use the formula new Random().nextInt((max-min)-1)+min
                genes[i] = (char) (random.nextInt(97) + 32);
            }
        }

        //the method which calculates the fitness for specific DNA in population
        public void fitness() {
            int score = 0;
            for(int i = 0; i< genes.length; i++) {
                if(genes[i] == target.charAt(i)) score++;
            }
            fitness = score / target.length();
        }

        //the method used in reproduction process
        //we chose the point split the genes into two part, one from parent one, another from parent two
        public DNA crossover(DNA partern) {
            DNA child = new DNA();
            int midpoint = random.nextInt(genes.length);
            for(int i = 0; i< genes.length; i++) {
                if(i < midpoint) child.genes[i] = this.genes[i];
                else child.genes[i] = partern.genes[i];
            }
            return child;
        }

        //generate a random double number between 0 and 1, and compare to mutation rate
        public void mutation(double mutationRate) {
            for(int i = 0; i< this.genes.length; i++) {
                if(Math.random() < mutationRate) {
                    this.genes[i] = (char) (random.nextInt(97) + 32);
                }
            }
        }
    }
}
