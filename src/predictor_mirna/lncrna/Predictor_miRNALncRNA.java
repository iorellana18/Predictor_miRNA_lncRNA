package predictor_mirna.lncrna;

import common_Features.seedMatchPredictor;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author ian
 */
public class Predictor_miRNALncRNA {

    public static void main(String[] args) {
        //Lee archivos .fa y los guarda en buffer
        FileReader miRNA_Reader, lnc_Reader;
        ArrayList<String> miRNA = new ArrayList();
        ArrayList<String> lncRNA = new ArrayList();
        try {
                miRNA_Reader = new FileReader("src/targets/mature_small.fa");
                lnc_Reader = new FileReader("src/targets/lnc_small.fa");
        } catch (FileNotFoundException e) {
                throw new RuntimeException("Error reading file");
        }
        
        BufferedReader miRNA_buffer= new BufferedReader(miRNA_Reader);
        BufferedReader lnc_buffer = new BufferedReader(lnc_Reader);
        
        
        
        String temp, conc="";
        try{
            //Lee lineas de archivo miRNA y obtiene id y cadena
            while((temp = miRNA_buffer.readLine()) != null){
                    if ( !String.valueOf(temp.charAt(0)).equals(">") ){
                           
                            String to_add = conc + "LLLL" + temp;
                            miRNA.add(to_add);
                    }
                    else{
                            String[] parts = temp.split("\\s+");
                            conc = parts[0].substring(1);
                    }
            }
			System.out.println("miRNA readed!");
			System.out.println("total miRNAs: " + miRNA.size());
	    
                        
            //Lee archivo lncRNA y obtiene id y cadena
            while((temp = lnc_buffer.readLine()) != null ){
                    if ( !String.valueOf(temp.charAt(0)).equals(">") ){
                            String replacement = temp.replaceAll("T","U");
                            String to_add = conc + "LLLL" + replacement;
                            lncRNA.add(to_add);
                    }
                    else{
                            String[] parts2 = temp.split("\\|");
                            conc = parts2[0].substring(1);
                    }
            }
			System.out.println("lncRNA readed!");
			System.out.println("total lncRNAs: " + lncRNA.size());
			miRNA_buffer.close();
			lnc_buffer.close();
			
		}catch(Exception e){
			throw new RuntimeException("Error reading tuple",e);
		}finally{
                //Envía todos los lncRNA con cada miRNA        
			System.out.println("Sending data...");
			for(int i = 0 ; i < miRNA.size() ; i++){
				for(int j = 0; j < lncRNA.size() ; j++){
					//Formato: miRNA_id LLLL miRNA_code LLLL lncRNA_id LLLL lncRNA_code
					String str_to_send = miRNA.get(i) + "LLLL" + lncRNA.get(j);
                                        seedMatchPredictor smp = new seedMatchPredictor();
                                        smp.sevenMerA1(str_to_send);
				}
			}	
		}   
    }
    
}
