package com.vtm.browsedirectory.Process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import com.vtm.browsedirectory.management.SlangMap;

public class Process {
    BufferedReader bReader;
    public static String slangFileName = "./editedSlang.txt";
    public static String hisFileName = "./history.dat";
    public static String originSlangFileName = "./slang.txt";

    public Process() {
        bReader = new BufferedReader(new InputStreamReader(System.in));
        SlangMap.getInstance().setHisFileName(hisFileName);
        SlangMap.getInstance().setSlangFileName(slangFileName);
        SlangMap.getInstance().setOriginSlangFileName(originSlangFileName);
        SlangMap.getInstance().loadData();
    };

    public static void menu() {
        System.out.println("1. Browse by SlangWord");
        System.out.println("2. Browse Definition");
        System.out.println("3. Show History");
        System.out.println("4. Add new SlangWord");
        System.out.println("5. Edit SlangWord");
        System.out.println("6. Delete SlangWord");
        System.out.println("7. Reset SlangWord");
        System.out.println("8. Random SlangWord");
        System.out.println("9. Quiz with SlangWord");
        System.out.println("10. Quiz Definition");
        System.out.println("0. Exit");
    }

    public void run() {
        Integer lc = 0;
        Boolean isRunning = true;
        while (isRunning) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            menu();
            System.out.print(">> ");
            try {
                lc = Integer.parseInt(bReader.readLine());
                switch (lc) {
                case 1:
                    this.searchBySlangWord();
                    break;
                case 2:
                    this.searchByDefinition();
                    break;
                case 3:
                    this.printHistory();
                    break;
                case 4:
                    this.addNewSlang();
                    break;
                case 5:
                    this.editSlangWord();
                    break;
                case 6:
                    this.deleteSlangWord();
                    break;
                case 7:
                    this.resetSlangWord();
                    break;
                case 8:
                    this.randomSlangWord();
                    break;
                case 9:
                    this.quizBySlangWord();
                    break;
                case 10:
                    this.quizByDefinition();
                    break;
                case 0:
                    isRunning = exit();
                    break;
                default:
                    break;
                }
                System.out.println("_Press any key to continue");
                this.bReader.readLine();
            } catch (NumberFormatException | IOException e) {
                e.printStackTrace();
            }

        }
    }

    private Boolean exit() {
        System.out.println("Confirm exit!");
        System.out.println("1. Yes");
        System.out.println("2. No");
        try {
            if (Integer.parseInt(this.bReader.readLine()) == 1) {
                return false;
                //SlangMap.getInstance().saveHistoryData();
            }
            else {
                return true;
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
            return true;
        }
    }

    private Boolean searchBySlangWord() {
        System.out.print("Slang word: ");
        try {
            String res = SlangMap.getInstance().searchBySlangWord(this.bReader.readLine());
            if (res != "") {
                System.out.println("___" + res + "___");
                return true;
            } else {
                System.out.println("___SlangWord is not exist___");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Boolean searchByDefinition() {
        System.out.print("Definition: ");
        try {
            ArrayList<String> res = SlangMap.getInstance().searchByDefinition(this.bReader.readLine());
            if (res.size() != 0) {
                for (int i = 0; i < res.size(); i++) {
                    System.out.println("___" + res.get(i) + "___");
                }
                return true;
            } else {
                System.out.println("___No matching Definition was found___");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private Boolean addNewSlang() {
        try {
            String slang, definition;
            System.out.print("New Slang: ");
            slang = this.bReader.readLine();
            System.out.print("New Definition: ");
            definition = this.bReader.readLine();
            Integer res = SlangMap.getInstance().addNewSlang(slang, definition, bReader);
            if (res == 0) {
                System.out.println("___Fail to add new SlangWord___");
                return false;
            } else if (res == 1) {
                System.out.println("___Overwrite success___");
            } else if (res == 2) {
                System.out.println("___Duplicate success___");
            } else if (res == 3) {
                System.out.println("___Add new SlangWord success___");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private Boolean printHistory() {
        SlangMap.getInstance().printHistory();
        return true;
    }

    private Boolean editSlangWord() {
        System.out.print("Slang-word: ");
        try {
            Boolean res = SlangMap.getInstance().editSlangWord(this.bReader.readLine(), bReader);
            if (res == true) {
                System.out.println("___Success edit SlangWord___");
                return true;
            } else {
                System.out.println("___Fail to edit SlangWord___");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private Boolean deleteSlangWord() {
        System.out.print("Slang-word: ");
        try {
            Boolean res = SlangMap.getInstance().deleteSlangWord(this.bReader.readLine(), bReader);
            if (res == true) {
                System.out.println("___Delete success___");
                return true;
            } else {
                System.out.println("___Delete fail___");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Boolean resetSlangWord() {
        try {
            SlangMap.getInstance().resetData();
            System.out.println("___Reset completed!___");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Boolean randomSlangWord() {
        try {
            System.out.print("Random-Slangword: ");
            String randomSlang = SlangMap.getInstance().randomSlangWord();
            String definition = SlangMap.getInstance().getDefinition(randomSlang);
            String sladefi = SlangMap.getInstance().SlaDefi(randomSlang, definition);
            if (randomSlang != "") {
                System.out.println("___" + sladefi + "___");
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private Boolean quizBySlangWord() {
        try {
            System.out.println("---Funny quiz---");
            System.out.print("Random-Slangword: ");
            String slangWord = SlangMap.getInstance().randomSlangWord();
            System.out.println("*****" + slangWord + "*****");
            System.out.println("Choose the right Definition");
            String ansA = SlangMap.getInstance().randomDefinition();
            String ansB = SlangMap.getInstance().randomDefinition();
            String ansC = SlangMap.getInstance().randomDefinition();
            String ansD = SlangMap.getInstance().randomDefinition();
            Random random = new Random();
            int number = random.nextInt(4);
            if (number == 0) {
                ansA = SlangMap.getInstance().getDefinition(slangWord);
            } else if (number == 1) {
                ansB = SlangMap.getInstance().getDefinition(slangWord);
            } else if (number == 2) {
                ansC = SlangMap.getInstance().getDefinition(slangWord);
            } else if (number == 3) {
                ansD = SlangMap.getInstance().getDefinition(slangWord);
            }
            System.out.println("    A. " + ansA);
            System.out.println("    B. " + ansB);
            System.out.println("    C. " + ansC);
            System.out.println("    D. " + ansD);
            String yourAns = "";
            System.out.print("Your answer >> ");
            yourAns = this.bReader.readLine();
            yourAns = yourAns.toUpperCase();
            if ((number == 0 && yourAns.equals("A")) || (number == 1 && yourAns.equals("B"))
                    || (number == 2 && yourAns.equals("C")) || (number == 3 && yourAns.equals("D"))) {
                System.out.println("___Correct! congratulation___");
                return true;
            } else {
                System.out.println("___Your answer is wrong___");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Boolean quizByDefinition() {
        try {
            System.out.println("---Funny quiz---");
            System.out.print("Random-Definition: ");
            String slangWord = SlangMap.getInstance().randomSlangWord();
            String definition = SlangMap.getInstance().getDefinition(slangWord);
            System.out.println("*****" + definition + "*****");
            System.out.println("Choose the right SlangWord");
            String ansA = SlangMap.getInstance().randomSlangWord();
            String ansB = SlangMap.getInstance().randomSlangWord();
            String ansC = SlangMap.getInstance().randomSlangWord();
            String ansD = SlangMap.getInstance().randomSlangWord();
            Random random = new Random();
            int number = random.nextInt(4);
            if (number == 0) {
                ansA = slangWord;
            } else if (number == 1) {
                ansB = slangWord;
            } else if (number == 2) {
                ansC = slangWord;
            } else if (number == 3) {
                ansD = slangWord;
            }
            System.out.println("    A. " + ansA);
            System.out.println("    B. " + ansB);
            System.out.println("    C. " + ansC);
            System.out.println("    D. " + ansD);
            String yourAns = "";
            System.out.print("Your answer >> ");
            yourAns = this.bReader.readLine();
            yourAns = yourAns.toUpperCase();
            if ((number == 0 && yourAns.equals("A")) || (number == 1 && yourAns.equals("B"))
                    || (number == 2 && yourAns.equals("C")) || (number == 3 && yourAns.equals("D"))) {
                System.out.println("___Correct! congratulation___");
                return true;
            } else {
                System.out.println("___Your answer is wrong___");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
