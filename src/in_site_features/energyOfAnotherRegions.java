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
                miRNA_Region3.append(miRNA.charAt(i));
            }else{
                miRNA_Region5.append(miRNA.charAt(i));
            }
            i++;
        }
        while(j<miRNA_length){
            if(j<miRNA_length-8){
                lncRNA_Region5.append(rev_mre.charAt(j));
            }else{
                lncRNA_Region3.append(rev_mre.charAt(j));
            }
            j++;
        }
        
        String seq1=miRNA_Region3+"&"+lncRNA_Region3;
        String seq2=miRNA_Region5+"&"+lncRNA_Region5;
        
        
        
    }
}
