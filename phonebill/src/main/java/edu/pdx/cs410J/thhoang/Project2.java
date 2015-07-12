package edu.pdx.cs410J.thhoang;

/**
 * The main class for CS410J Phone Bill Project 2,
 * (working with text file)
 */
public class Project2 {

    public static void main(String[] args) {

        System.exit(1);
    }

    /**
     * Print out README  and exit program.
     */
    private static void printReadMeAndExit() {

        System.out.println("Project 1");
        System.out.println("@author: Thanh Hoang");
        System.out.println("This project is designed to build fundamental PhoneBill and PhoneCall classes, and project1 class that parses the commmand line.");
        System.out.println("The command line has to be in this order:");
        System.out.println("\t[options] <args>");
        System.out.println("options are:");
        System.out.println("\t-print\t\tprints a description of the new phone call");
        System.out.println("\t-README\t\tprints a README for this project and exits\n");
        System.out.println("args are (in this order):");
        System.out.println("\tcustomer\tperson whose phone bill we're modeling");
        System.out.println("\tcallerNumber\tPhone number of caller (in format nnn-nnn-nnnn)");
        System.out.println("\tcalleeNumber\tphone number of person who was called (in format nnn-nnn-nnnn)");
        System.out.println("\tstartTime\tDate and time call began (24- hour time)");
        System.out.println("\tendTime \tDate and time call end (24-hour time)");
        System.out.println("\t\t\tDate and time in this formate :\tmm/dd/yyyy hh:mm");

        System.exit(1);


        /*
        File f = new File("README.txt");

        try {
            if (!f.exists())
                f.createNewFile();
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            StringBuffer sb = new StringBuffer();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }

            System.out.print(sb.toString());
            System.exit(1);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(" ** " + e);
            System.exit(1);
        }
        */
    }




}
