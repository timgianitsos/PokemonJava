import java.util.Scanner;
import java.util.EnumSet;
import java.util.EnumMap;
//TODO Nik needs to implement trainer battles, Tim needs to implement AI for trainer battle opponent using the simulation data
/*
 * A class to simulate a Trainer battle using Pokemon objects
 */
class TrainerBattle {

    public static void main(String[] args) {
//        testCode();
//        practice();
        battle();
    }

    static void battle() {
        Scanner scan = new Scanner(System.in);
        Pokemon.displayPokemon();
        Pokemon[] playerParty = new Pokemon[3];
        for(int i = 0; i < 3; i++) {
            System.out.println("Enter pokemon " + (i + 1));
            playerParty[i] = Pokemon.askForPokemon(scan);
            System.out.println("This is your pokemon " + playerParty[i].toString());
        }
        Pokemon playerPokemon = playerParty[0];
        
        System.out.println("Your opponent is choosing his pokemon...");
        TrainerAI AIopponent = new TrainerAI();
        Pokemon opponentPokemon = AIopponent.getNextPokemon(playerParty[0].name);
        
        System.out.println("Ready for battle, send out your first pokemon! (press enter)");
        scan.nextLine();
        
        int playerPartyIndex = 1;
        while (playerPartyIndex < 3 && opponentPokemon != null) {
            while(playerPokemon.getCurrentHP() > 0 && opponentPokemon.getCurrentHP() > 0 ){
                
                Pokemon.doTurn(playerPokemon, opponentPokemon);
                scan.nextLine();
            }
            if(playerPokemon.getCurrentHP() == 0){
                playerPokemon = playerParty[playerPartyIndex];
                playerPartyIndex++;
            }
            if(opponentPokemon.getCurrentHP() == 0){
                opponentPokemon = AIopponent.getNextPokemon(playerPokemon.name);
                
            }
        }    
    }

    static void testCode() {
        Scanner scan = new Scanner(System.in);

        //Makes a few pokemon
        System.out.println("\nLets make some pokemon\n");
        
        //--------------------------------------------------------------------------------
        //Here are three different ways of making a pokemon - firstly you can just use a string name. 
        //If you use this method, the name must exist as an enum or else it will default to magikarp
        Pokemon p1 = new Pokemon("_steelix"); //the underscore at the beginning is a secret that maxes out IVs - this Pokemon is way better than any random one
        System.out.println(p1.toString()); //Calling toString on a pokemon prints out the info for it
        scan.nextLine(); //Press enter to proceed

        Pokemon p2 = new Pokemon(PokemonEnum.RAICHU); //We can use a pre-made PokemonEnum to create a Pokemon
        System.out.println(p2.toString());
        scan.nextLine(); //Press enter to proceed

        //We can create a custom pokemon. Notice that if we call this constructor, its name, stats, and attacks can be totally made up
        //Side note - remember that base stats are different than the ACTUAL stats at level 50. Base stats are just used to compute actual stats
        Pokemon p3 = new Pokemon("_primal_groudon", Type.NORMAL, Type.ELECTRIC, new int[]{10, 10, 10, 10, 10, 10}, EnumSet.of(Attack.DRILL_PECK)); 
        System.out.println(p3.toString());
        scan.nextLine(); //Press enter to proceed

        //--------------------------------------------------------------------------------
        //Use some methods to get info on Pokemon
        System.out.println("Lets get some info on " + p1.name);

        Attack bestAttack = p1.getBestAttack(p2); //Returns the Attack that is best against this opponent

        System.out.println(p1.name + "'s best attack against " + p2.name + " is " + bestAttack.name()); //bestAttack is an Attack enum. Call name() on an enum to get it as a String
        
        double damage = p1.attackDamage(bestAttack, p2); //Computes damage of using the attack
        
        System.out.println("Approximate damage of " + bestAttack.name() + " on " + p2.name + ": " + damage + "\n");
        scan.nextLine(); //Press enter to proceed

        //--------------------------------------------------------------------------------
        //A typical turn
        System.out.println("A typical turn-------------\n");
        Pokemon.doTurn(p1, p2); //Both Pokemon attack each other. The Pokemon with higher speed goes first
        scan.nextLine(); //Press enter to proceed

        //--------------------------------------------------------------------------------
        System.out.println("Enter the name of a pokemon \n"
            + "(Or type \"custom\" to make your own pokemon like latios or arceus)\n"
            + "(Even if the pokemon enum does not exist in the code, it will still make a cry if it "
            + "is spelled correctly)\n(The actual stats of a custom pokemon do NOT have to be authentic)");
        Pokemon userPoke = Pokemon.askForPokemon(scan); //Use this method to ask user to enter a pokemon name
        System.out.println("\nHere is the pokemon you chose:\n" + userPoke.toString());
        scan.nextLine(); //Press enter to proceed

        //--------------------------------------------------------------------------------
        System.out.println("\nNow lets use your pokemon to attack (hit enter)");
        scan.nextLine(); //Press enter to proceed
        Attack myAttack = userPoke.getBestAttack(p3);
        //The useAttack method does a single attack against an opponent - it does not initiate a whole turn so the opponent won't hit back
        userPoke.useAttack(myAttack, p3);
        scan.nextLine(); //Press enter to proceed

        //--------------------------------------------------------------------------------
        System.out.println("We can check the opponents health --> " + p3.getCurrentHP());
        scan.nextLine(); //Press enter to proceed

        //--------------------------------------------------------------------------------
        System.out.println("To display a list of all non-hidden pokemon use the displayPokemon() method");
        //This is a static method so we invoke it with Pokemon.method(), not variable.method()
        //This makes sense - displaying a list of all Pokemon should be a behavior of the whole class, not an instance of the class
        Pokemon.displayPokemon();
    }

    static void practice() {
        Scanner scan = new Scanner(System.in);

        Pokemon p1 = new Pokemon(PokemonEnum.CHARIZARD);
        System.out.println(p1.toString());
        scan.nextLine();

        Pokemon p2 = new Pokemon("_ARCANINE", Type.FIRE, null, new int[]{5,5,5,5,5,5}, EnumSet.of(Attack.THUNDERBOLT));
        System.out.println(p2.toString());
        scan.nextLine();

        Pokemon.doTurn(p1, p2);
        scan.nextLine();

        System.out.println("Press enter to stop the music (it may delay a few seconds before stopping - use \"command C\" to stop immediately)");
        AePlayWave battleMusic = new AePlayWave(AePlayWave.BATTLE_MUSIC, AePlayWave.BATTLE_MUSIC_BUFFER_SIZE);
        battleMusic.start();
        scan.nextLine();
        battleMusic.quit();
    }

}
