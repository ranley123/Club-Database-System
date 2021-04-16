import java.io.*;

public class ODReader {
    public ODReader(String filename){
        try{
            FileReader input = new FileReader(filename);
            BufferedReader br = new BufferedReader(input);
            String message = "";
            String line = null;
            while((line = br.readLine()) != null) {
                System.out.print(line);
            }

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
//    "squash_club_data.ods"
}
