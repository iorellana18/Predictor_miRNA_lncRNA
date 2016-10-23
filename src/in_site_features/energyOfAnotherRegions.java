/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in_site_features;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 *
 * @author ian
 */
public class energyOfAnotherRegions {
    public void calculateEnergy(String miRNA_id, String miRNA, String lncRNA_id, String lncRNA, String rev_mre, int position, float dg_binding,float dg_duplex) throws IOException{
        String line,line2;
        String execstr = "RNAcofold -p --noPS"; ///Revisar diferencia con RNAfold LLLLL, diferencias en energía -> free energy of another regions
        StringBuilder miRNA_Region3 = new StringBuilder();
        StringBuilder miRNA_Region5 = new StringBuilder();
        StringBuilder lncRNA_Region3 = new StringBuilder();
        StringBuilder lncRNA_Region5 = new StringBuilder();
        int miRNA_length = miRNA.length();
        int i=0, j=0;
        float mfe_Region3 = 0;
        float mfe_Region5 = 0;
        int flag = 0;
        
        
        while(i<miRNA_length){
            if(i<8){
                miRNA_Region5.append(miRNA.charAt(i));
            }else{
                miRNA_Region3.append(miRNA.charAt(i));
            }
            i++;
        }
        while(j<miRNA_length){
            if(j<miRNA_length-8){
                lncRNA_Region3.append(rev_mre.charAt(j));
            }else{
                lncRNA_Region5.append(rev_mre.charAt(j));
            }
            j++;
        }
        
        String seq1=miRNA_Region3+"&"+lncRNA_Region3;
        String seq2=miRNA_Region5+"&"+lncRNA_Region5;
        System.out.println(seq1);
        System.out.println(seq2);
        
        ///Obtiene energía mínima de región 3 
        try{
            Process p2 = Runtime.getRuntime().exec(execstr);

            // Get input 
            BufferedReader input_buffer = new BufferedReader(new InputStreamReader(p2.getInputStream()));

            // Generate response to command
            OutputStream ops = p2.getOutputStream();
            ops.write(seq1.getBytes());
            ops.close(); 
            
            while ((line = input_buffer.readLine()) != null){
                if (line.contains("-")) {
                    String[] parts = line.split("-");
                    String number = parts[1]; //minimum free energy / free energy ensemble /delta G binding
                    if(number.charAt(number.length()-1)== ')' || number.charAt(number.length()-1)==']'){//Limpia número de paréntesis
                        number=number.substring(0,number.length()-1);
                    }
                    if (flag == 0) {//-->Mínimum free energy
                        flag++;
                        mfe_Region3 = Float.parseFloat(number);
                        mfe_Region3 = mfe_Region3*-1.0f; 
                        //System.out.println("Region 3: "+mfe_Region3);       
                    }
                    
                }
            
            }
            input_buffer.close();
            
         
        }catch(IOException e){
        System.out.println("IOException");
        
        }
        flag=0;
        
        // Obtiene energía de región 5 (semilla)
        try{
            Process p3 = Runtime.getRuntime().exec(execstr);
            BufferedReader input_buffer2 = new BufferedReader(new InputStreamReader(p3.getInputStream()));
            OutputStream ops2 = p3.getOutputStream();
            ops2.write(seq2.getBytes());
            ops2.close(); 
            while ((line = input_buffer2.readLine()) != null){
                if (line.contains("-")) {
                    String[] parts = line.split("-");
                    String number = parts[1]; //minimum free energy / free energy ensemble /delta G binding
                    if(number.charAt(number.length()-1)== ')' || number.charAt(number.length()-1)==']'){//Limpia número de paréntesis
                        number=number.substring(0,number.length()-1);
                    }
                    if (flag == 0) {//-->Mínimum free energy
                        flag++;
                        mfe_Region5 = Float.parseFloat(number);
                        mfe_Region5 = mfe_Region5*-1.0f; 
                        //System.out.println("Region 5: "+mfe_Region5);       
                    }
                    
                }
            
            }
        
            input_buffer2.close();
            
        }catch(IOException e){
            System.out.println("IOExeotion");
        }
        
    }
}
