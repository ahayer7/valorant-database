/* 
This program logs the stats of user inputted valorant players and displays it in table format. 
*/
//Imports java io, util, and text packages.
import java.io.*;
import java.util.*;
import java.text.*;

class Main {
  public static void main(String[] args) throws IOException, FileNotFoundException{
    //Calls 3 methods created to perform the programs purpose
    startUp();
    fileWrite();     
    fileReadOutput();
  }

  static void startUp(){
    /* 
    The startUp function is the first function that the program runs. It starts by clearing the screen then creates a scanner to collect user input. It guides the user through the program and then if a file named "valorantStats.txt" doesn't exists it creates the file. If it already does exist the program asks the user whether they want to use the file or clear it and start from scratch. Once the file has been created the program moves to the next function.
    */
    clearScreen();
    Scanner scan = new Scanner(System.in);
    System.out.println("\033[0;95m" + "Welcome to the Valorant Pro Player Database - Made by Arleen" + "\n");
    System.out.println("To find players and their stats you can use the site 'vlr.gg/stats' and set the timespan filter to all time, then click apply." + "\u001B[0m" + "\n");
    try {
      File valorant = new File("valorantStats.txt");
      if(valorant.createNewFile()){
        System.out.println("\u001B[32m" + "A new file has been created!" + "\u001B[0m");
      }else{
        System.out.println("File already exists. Would you like to use this file or clear it and start from scratch?" + "\n" + "\n" + "(1) Use File" + "\n" + "(2) Clear File" + "\n" + "\n" + "Please type either '1' or '2' to make a selection...");
        int choice = 0;
        while(true){
          try{
            choice = scan.nextInt();
          } catch (InputMismatchException e) {
            System.out.println("");          
          }
          scan.nextLine();
          if(choice == 1 || choice == 2){
            break;
          }else{
            System.out.println("\u001b[31m" + "Type either '1' or '2' in NUMBER form and press enter to make a selection!!!" + "\u001B[0m");
          }
        }
        if(choice == 2){
          PrintWriter clear = new PrintWriter("valorantStats.txt");
          clear.close();
        }
      }
    }catch(IOException e){
      System.out.println("\u001b[31m" + "An error occured..." + "\u001B[0m");
    }
  }
  
  static void fileWrite() throws IOException, FileNotFoundException{
    /* 
    This method asks the user for inputs into the valorant stats file and then writes the inputs into the file. It catches errors and formats doubles to either no decimal places or none.
    */
    clearScreen();
    Scanner scan = new Scanner(System.in);
    FileWriter writer = null;
    DecimalFormat noD = new DecimalFormat("#");
    DecimalFormat twoD = new DecimalFormat("#.##");
    try{
      writer = new FileWriter("valorantStats.txt",true);
    } catch(IOException e) {
      System.out.println("\u001b[31m" + "An error occured" + "\u001B[0m");
    }
    while(true){
      System.out.println("Would you like to add more players to the file?");
      String answer = scan.next();
      if(answer.equals("yes")){
        String playerName = "";
        while(true){
          System.out.println("\n" + "Enter the player's gamer tag: ");
          try{
            playerName = scan.next();
          } catch (InputMismatchException e) {
            System.out.println("");   
          }
          if(playerName.length() < 16){
            break;
          } else {
            System.out.println("\n" + "\u001b[31m" + "The maximum length of a valorant gamer tag is 16 characters long!!!! Please try again." + "\u001B[0m");
          }
        }
        System.out.println("\n" + "Enter " + playerName + "'s all-time kills: ");
        double kills = scan.nextDouble();
        System.out.println("\n" + "Enter " + playerName + "'s all-time deaths: ");
        double deaths = scan.nextDouble(); 
        System.out.println("\n" + "Enter " + playerName + "'s all-time assists: ");
        int assists = scan.nextInt();
        int hs = 0;
        while(true){
          System.out.println("\n" + "Enter " + playerName + "'s all-time average headshot percentage: ");
          hs = scan.nextInt();
          if(hs < 101){
            break;
          }else{
            System.out.println("\n" + "\u001b[31m" + "Percentage Not Possible! Please Enter A Number From 1-100" + "\u001B[0m");
          }
        }
        System.out.println("\n" + "Enter " + playerName + "'s all-time average combat score: ");
        int cs = scan.nextInt();
        double kd = kills/deaths;
        writer.write(playerName + " " + noD.format(kills) + " " + noD.format(deaths) + " " + assists + " " + twoD.format(kd) + " " + hs + " " + cs);
        writer.write(System.getProperty( "line.separator" ));
        System.out.println("\n" + "\u001B[32m" + playerName + "'s stats have been added to the database" + "\u001B[0m" + "\n");
      } else if(answer.equals("no")){
        System.out.println("\n" + "\u001B[32m" + "Info Saved" + "\u001B[0m");
        break;
      } else if(!answer.equals("no") || !answer.equals("yes")){
        System.out.println("\u001b[31m" + "Please enter 'yes' or 'no'" + "\u001B[0m" + "\n");
      }
    } 
    writer.close();
  }
  
  static void fileReadOutput() {
    /*
    This method reads the stats file and prints it out on the console. The table that is printed on the console is formatted with 7 different columns and the lines are given a colour based on their KD. 
    */
    Scanner scan = new Scanner(System.in);
    String format = "|%1$-16s|%2$-6s|%3$-6s|%4$-6s|%5$-6s|%6$-6s|%7$-6s|\n";
    while(true){
      System.out.println("Would you like to view the data base of entered players? ");
      String prompt = scan.nextLine();
      if(prompt.equals("yes")){
        clearScreen();
        String line = null;
        try{
          FileReader fileReader = new FileReader("valorantStats.txt");
          BufferedReader bufferedReader = new BufferedReader(fileReader);
          System.out.format(format, "Name", "K", "D", "A", "K:D", "HS%", "CS");
          System.out.println("------------------------------------------------------------");
          while((line = bufferedReader.readLine()) != null){
            List fileEntry = new ArrayList();
            try (Scanner scanner = new Scanner(line);) {
              while (scanner.hasNext()) {
                fileEntry.add(scanner.next());
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
            String convertO = fileEntry.get(4).toString();
            double kdCompare = Double.parseDouble(convertO);
            if(kdCompare < 1){
              System.out.print("\u001B[31m");
            } else if (kdCompare >= 1 && kdCompare < 2){
              System.out.print("\033[0;93m");
            } else if (kdCompare >= 2){
              System.out.print("\033[0;92m");
            }
            System.out.format(format, fileEntry.get(0), fileEntry.get(1), fileEntry.get(2), fileEntry.get(3), fileEntry.get(4), fileEntry.get(5), fileEntry.get(6));
            System.out.print("\033[0m");
          }
          System.out.println("\n" + "\u001B[31m" + "Bad: Under 1 K:D");
          System.out.println("\033[0;93m" + "Average: 1 - 1.99 K:D");
          System.out.println("\033[0;92m" + "Good: 2+ K:D");
          bufferedReader.close();
        }catch(IOException ex){
          System.out.println("\u001b[31m" + "Error reading file named 'valorantStats.txt'" + "\u001B[0m");
        }
        break;
      } else if(prompt.equals("no")) {
        System.exit(0);
      } else if(!prompt.equals("no") || !prompt.equals("yes")) {
        System.out.println("\u001b[31m" + "Please enter 'yes' or 'no'" + "\u001B[0m");
      }
    }
  }
  
  public static void clearScreen() {  
    //This method clears the screen.
    System.out.print("\033[H\033[2J");  
    System.out.flush();  
  }
}
