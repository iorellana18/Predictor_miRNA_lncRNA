/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package predictor_mirna.lncrna;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;

/**
 *
 * @author ian
 */
public class results {
    
    private static Float precision(int decimalPlace, Float d) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_UP);
        return bd.floatValue();
    }
    
    
    public void printResults(String miRNA_id, String miRNA, String lncRNA_id, String lncRNA, String mre, int position, float DG_duplex, float DG_binding, double DG_open){
        String duplex = Float.toString(DG_duplex);
        String binding = Float.toString(DG_binding);
        String open = Double.toString(DG_open);

        
        float result_ddg = DG_duplex - (float)DG_open; 
        String result = Float.toString(precision(2,result_ddg));//DG duplex(minimum free energy) - DG open
        String position_string = String.valueOf(position);
        //Generate CSV outfile
        OutputStream o;
        if (result_ddg < 0) {
            try{
                o = new FileOutputStream("/home/ian/Escritorio/results.csv",true);
                //mir_id, lncRNA transcript id, position of seed in transcript, dG duplex, dG binding, dG open, ddG
                o.write( (miRNA_id+","+lncRNA_id+","+position_string+","+duplex+","+binding+","+open+","+result+"\n").getBytes() );
                o.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
