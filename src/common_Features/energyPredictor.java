/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common_Features;

import in_site_features.energyOfAnotherRegions;
import in_site_features.matchCounter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 *
 * @author ian
 */
public class energyPredictor {
    public void calculate_Energy(String miRNA_id, String miRNA, String rev_mre, String lncRNA_id, String lncRNA, int position) throws IOException{
        String line;
        String execstr = "RNAcofold -p --noPS"; ///Revisar diferencia con RNAfold LLLLL, diferencias en energía -> free energy of another regions
        String seq = miRNA+"&"+rev_mre;
        float dg_duplex = 0;
        float dg_binding = 0;
        int flag = 0;
        
        try{
        Process p2 = Runtime.getRuntime().exec(execstr);

        // Get input 
        BufferedReader input_buffer = new BufferedReader(new InputStreamReader(p2.getInputStream()));

        // Generate response to command
        OutputStream ops = p2.getOutputStream();
        ops.write(seq.getBytes());
        ops.close();            
        // Show final output
        
        /////////////////////////////////////////////////
      /*  DeltaG duplex -> minimum free energy?
          DeltaG binding -> free energy of ensemble?
        
        
        
        */
        /////////////////////////////////////////////////
        while ((line = input_buffer.readLine()) != null){
            
            if (line.contains("-")) {
                String[] parts = line.split("-");
                String number = parts[1]; //minimum free energy / free energy ensemble /delta G binding
                if(number.charAt(number.length()-1)== ')' || number.charAt(number.length()-1)==']'){//Limpia número de paréntesis
                    number=number.substring(0,number.length()-1);
                }
            if (flag == 0) {//-->Mínimum free energy
                flag++;
                dg_duplex = Float.parseFloat(number);
                dg_duplex = dg_duplex*-1.0f;
                matchCounter mc = new matchCounter();
                mc.counter(seq, parts[0]);     
                System.out.println(dg_duplex);
            }
            else if (flag == 1){ //-->Free energy of ensemble
                flag++;
                dg_binding = Float.parseFloat(number);
                dg_binding = dg_binding*-1.0f;
                //System.out.println("DeltaG binding: " + dg_binding + " kcal/mol");
                 
            }//---> delta G binding
                //System.out.println("Minimum Free Energy: " + final_value + " kcal/mol");
            }
            
        }
        input_buffer.close();
    }catch(IOException e){
        System.out.println("IOException");
    }
        accessibilityPredictor ap = new accessibilityPredictor();
        ap.accessibilityEnergy(miRNA_id, miRNA, lncRNA_id, lncRNA, rev_mre, position, dg_binding, dg_duplex);
        //energyOfAnotherRegions eoar = new energyOfAnotherRegions();
        //eoar.calculateEnergy(miRNA_id, miRNA, lncRNA_id, lncRNA, rev_mre, position, dg_binding, dg_duplex);
    }
}
