/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in_site_features;

/**
 *
 * @author ian
 */
public class matchCounter {
    public void counter(String sequence, String code){
        String[] split = code.split(" ");
        String[] cleanCode = split[0].split("&");
        String miRNA_code = cleanCode[0]+"|";
        String lncRNA_code = new StringBuilder(cleanCode[1]).reverse().toString()+"|";
        
        split = sequence.split("&");
        String miRNA = split[0];
        String lncRNA = new StringBuilder(split[1]).reverse().toString();
        System.out.println(miRNA);
        System.out.println(lncRNA);
        
        
        int i=0, j=0, k=0,match=0,miss=0, GU_match=0, GC_match=0, AU_match=0;
        int miRNA_length=miRNA_code.length(), lncRNA_length=lncRNA_code.length();
        int miRNA_bulge=0, lncRNA_bulge=0, nucleotidsOnBulge=0;
        boolean newBulge=true;
        StringBuilder builder = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();
        StringBuilder meanChain = new StringBuilder();

        while(miRNA_code.charAt(k)!='|'){
            
           if(miRNA_code.charAt(k)==')'){
               builder.append('|');
               builder2.append('|');
               break;
            
            }else{
               builder.append(miRNA_code.charAt(k));
               builder2.append(lncRNA_code.charAt(k));
           }
           k++;
        }
           
        miRNA_code = builder.toString()+"|";
        lncRNA_code = builder2.toString()+"|";
        
        
        System.out.println(sequence);
        System.out.println(miRNA_code);
       
        while(i<miRNA_length && j<lncRNA_length && miRNA_code.charAt(i)!='|' && lncRNA_code.charAt(j)!='|'){
            
            if(miRNA_code.charAt(i) == lncRNA_code.charAt(j) || miRNA_code.charAt(i)=='(' && lncRNA_code.charAt(j)== ')'){
                newBulge=true;
                if(miRNA_code.charAt(i)=='('){
                    meanChain.append("|");
                    System.out.print("|");
                    match++;
                    switch(miRNA.charAt(i)){
                        case 'A':{
                            if(lncRNA.charAt(j) == 'U'){
                                AU_match++;
                            }
                            break;
                        }
                        case 'C':{
                            if(lncRNA.charAt(j) == 'G'){
                                GC_match++;
                            }
                            break;
                        }
                        case 'G':{ 
                           if(lncRNA.charAt(j) == 'C'){
                                GC_match++;
                            }else if(lncRNA.charAt(j) == 'U'){
                                GU_match++;
                            }
                            break;
                        }
                        case 'U':{ 
                            if(lncRNA.charAt(j) == 'G'){
                                GU_match++;
                            }else if(lncRNA.charAt(j) == 'A'){
                                AU_match++;
                            }
                            break;
                        }
                        default:{
                            System.out.println("Error, la cadena contiene caracter erroneo: "+miRNA.charAt(i));
                            break;
                        }
                    }
                }else{
                    newBulge=true;
                    meanChain.append("x");
                    System.out.print("x");
                    miss++;
                }
            }else{
                if(miRNA_code.charAt(i)=='.'){
                    j--;  nucleotidsOnBulge++;
                    meanChain.append("°");
                    System.out.print("°");
                    if(newBulge){
                        miRNA_bulge++;
                        newBulge=false;
                    }
                    if(meanChain.charAt(i-1)=='x'){
                       miRNA_bulge--;
                    }
                }else if(lncRNA_code.charAt(j)=='.'){
                    i--; nucleotidsOnBulge++;
                    meanChain.append(".");
                    System.out.print(".");
                    if(newBulge){
                        lncRNA_bulge++;
                        newBulge=false;
                    }
                    if(meanChain.charAt(i-1)=='x'){
                       lncRNA_bulge--;
                    }
                }
                
            }
            i++;
            j++;
        }
        System.out.print(match+" "+miss);
        System.out.println("\n"+lncRNA_code+"\n");
        System.out.println("G:U: "+GU_match);
        System.out.println("A:U: "+AU_match);
        System.out.println("C:G: "+GC_match);
        System.out.println("Nucleotids on bulge "+nucleotidsOnBulge);
        System.out.println("miRNA bulges: "+miRNA_bulge);
        System.out.println("lncRNA bulges: "+lncRNA_bulge);
        System.out.println("Nucleotids not matched: "+((miss)*2+nucleotidsOnBulge));
        System.out.println(meanChain);
    }
}
