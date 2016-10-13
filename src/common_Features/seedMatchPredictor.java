package common_Features;

/**
 *
 * @author ian
 */

/*
Variables importantes:
miRNA_id -> Identificador de micro RNA

miRNA -> Secuencia de nucleótidos de micro RNA

rev_mre -> Micro RNA Recognition Element invertido, secuencia de nucleótidos de 
long non code RNA que hace match con la semilla, en dirección 5' - 3'

lncRNA_id -> Identificador de long non code RNA

lncRNA -> Secuencia de nucleótidos de long non code RNA

position -> Posición en long non code RNA donde se parea con MRE.


*/
public class seedMatchPredictor {
    //Obtiene cadena complementaria
    public String complementSeq(String seq){
    StringBuilder builder = new StringBuilder();
    for(int i = 0 ; i < seq.length() ; i++){
            if(seq.charAt(i) == 'G'){
                    builder.append('C');
            }
            if(seq.charAt(i) == 'C'){
                    builder.append('G');
            }
            if(seq.charAt(i) == 'A'){
                    builder.append('U');
            }
            if(seq.charAt(i) == 'U'){
                    builder.append('A');
            }
    }
    return builder.toString();
}
   
    //7mer-m8, Pareamiento WC en nucleótidos 2 a 8 de miRNA (5'-3')
    public void SeedDefault(String cadena){
        String[] parts = cadena.split("LLLL");
        String miRNA_id = parts[0];
        String miRNA = parts[1];
        String lncRNA_id = parts[2];
        String lncRNA = parts[3];
        
        String rev_lncRNA = new StringBuilder(lncRNA).reverse().toString();
        String miRNA_seed = miRNA.substring(1, 8);
        
        int lncRNA_size = rev_lncRNA.length();
	int seed_size = miRNA_seed.length();
        int inf = 0;
	int sup = seed_size;
	int position = 0; //Position of the binding (seed)
        String rev_mre,mre;
        
        
        //Obtiene miRNA Recognition Element
        while( sup <= lncRNA_size ){
            String lncRNA_window = rev_lncRNA.substring(inf,sup); //3'-5'
            String lncRNA_comp = complementSeq(lncRNA_window); //3'-5'
            if(miRNA_seed.equals(lncRNA_comp)){ //seed: 5'-3' y lncRNA_com en 3'-5'
                    //Condiciones de borde (izq y derecha)
                    
                    if( (sup+(miRNA.length()-8)) <= rev_lncRNA.length() && (inf-1) >= 0 ){ 
                        String lncRNACompleteWindow = rev_lncRNA.substring(inf-1,sup+miRNA.length()-8);
                        //miRNA Recognition Element
                            mre = rev_lncRNA.substring( (inf-1), (sup+(miRNA.length()-8)) ); //MRE 3'-5'
                            rev_mre = new StringBuilder(mre).reverse().toString(); //MRE 5'-3'
                            position = lncRNA_size - sup; 
                            //mre y pos son los diferentes en cada emit
                           //formato de energía requiere mre en dirección 5' - 3' ->rev_mre
                            energyPredictor ep = new energyPredictor();
                            ep.calculate_Energy(miRNA_id,miRNA,rev_mre,lncRNA_id, lncRNA,position);
                    }
                    
            }

            inf += 1;
            sup += 1;
            
        }
        
    }
    
    //6mer, Pareamiento WC en nucleótidos 2 a 7 de miRNA (5'-3') 
    public void sixMer(String cadena){
        String[] parts = cadena.split("LLLL");
        String miRNA_id = parts[0];
        String miRNA = parts[1];
        String lncRNA_id = parts[2];
        String lncRNA = parts[3];
        
        String rev_lncRNA = new StringBuilder(lncRNA).reverse().toString();
        String miRNA_seed = miRNA.substring(1, 7);
        
        int lncRNA_size = rev_lncRNA.length();
	int seed_size = miRNA_seed.length();
        int inf = 0;
	int sup = seed_size;
	int position = 0; 
        String mre, rev_mre;
        int i=1;
        
        
        while( sup <= lncRNA_size ){
            String lncRNA_window = rev_lncRNA.substring(inf,sup); //3'-5'
            String lncRNA_comp = complementSeq(lncRNA_window); //3'-5'
            if(miRNA_seed.equals(lncRNA_comp)){ //seed: 5'-3' y lncRNA_com en 3'-5'
                    //Condiciones de borde (izq y derecha)
                    if( (sup+(miRNA.length()-7)) <= rev_lncRNA.length() && (inf-1) >= 0 ){ 
                            mre = rev_lncRNA.substring( (inf-1), (sup+(miRNA.length()-7)) ); //MRE 3'-5'
                            rev_mre = new StringBuilder(mre).reverse().toString(); //MRE 5'-3'
                            position = lncRNA_size - sup; 
                            //mre y pos son los diferentes en cada emit
                            //collector.emit(new Values(miRNA_id, miRNA, mre, lncRNA_id, lncRNA, pos));
                            
                            energyPredictor ep = new energyPredictor();
                            ep.calculate_Energy(miRNA_id,miRNA,rev_mre,lncRNA_id, lncRNA,position);
                            
                    }
            }

            inf += 1;
            sup += 1;
        }
        
    }
    
    //7mer-A1, Pareamiento WC en nucleótidos 2 a 7 de miRNA (5'-3')
    //sumado a una A frente al nucleótido 1 de miRNA
    public void sevenMerA1(String cadena){
        String[] parts = cadena.split("LLLL");
        String miRNA_id = parts[0];
        String miRNA = parts[1];
        String lncRNA_id = parts[2];
        String lncRNA = parts[3];
        
        String rev_lncRNA = new StringBuilder(lncRNA).reverse().toString();
        String miRNA_seed = miRNA.substring(1, 7);
        
        int lncRNA_size = rev_lncRNA.length();
	int seed_size = miRNA_seed.length();
        int inf = 0;
	int sup = seed_size;
	int position = 0; 
        String mre,rev_mre;
        int i=1;
        
        while( sup <= lncRNA_size ){
            String lncRNA_window = rev_lncRNA.substring(inf,sup); //3'-5'
            String lncRNA_comp = complementSeq(lncRNA_window); //3'-5'
            if(miRNA_seed.equals(lncRNA_comp)){ //seed: 5'-3' y lncRNA_com en 3'-5'
                    //Condiciones de borde (izq y derecha)
                    if( (sup+(miRNA.length()-7)) <= rev_lncRNA.length() && (inf-1) >= 0 ){ 
                            mre = rev_lncRNA.substring( (inf-1), (sup+(miRNA.length()-7)) ); //MRE 3'-5'
                            if(mre.charAt(0) == 'A'){
                                rev_mre = new StringBuilder(mre).reverse().toString(); //MRE 5'-3'
                                position = lncRNA_size - sup; 
                                energyPredictor ep = new energyPredictor();
                                ep.calculate_Energy(miRNA_id,miRNA,rev_mre,lncRNA_id,lncRNA,position);
                            }
                            
                    }
            }

            inf += 1;
            sup += 1;
        }
        
    }
    
    //8mer, Pareamiento WC en nucleótidos 2 a 8 de miRNA (5'-3')
    //sumado a una A frente al nucleótido 1 de miRNA
    public void eightMer(String cadena){
        String[] parts = cadena.split("LLLL");
        String miRNA_id = parts[0];
        String miRNA = parts[1];
        String lncRNA_id = parts[2];
        String lncRNA = parts[3];
        
        String rev_lncRNA = new StringBuilder(lncRNA).reverse().toString();
        String miRNA_seed = miRNA.substring(1, 8);
        
        int lncRNA_size = rev_lncRNA.length();
	int seed_size = miRNA_seed.length();
        int inf = 0;
	int sup = seed_size;
	int position = 0; //Position of the binding (seed)
        String mre,rev_mre;
        int i=1;
        
        while( sup <= lncRNA_size ){
            String lncRNA_window = rev_lncRNA.substring(inf,sup); //3'-5'
            String lncRNA_comp = complementSeq(lncRNA_window); //3'-5'
            if(miRNA_seed.equals(lncRNA_comp)){ //seed: 5'-3' y lncRNA_com en 3'-5'
                    //Condiciones de borde (izq y derecha)
                    if( (sup+(miRNA.length()-8)) <= rev_lncRNA.length() && (inf-1) >= 0 ){ 
                            mre = rev_lncRNA.substring( (inf-1), (sup+(miRNA.length()-8)) ); //MRE 3'-5'
                            if(mre.charAt(0) == 'A'){
                            
                                rev_mre = new StringBuilder(mre).reverse().toString(); //MRE 5'-3'
                                position = lncRNA_size - sup; 
                                //mre y pos son los diferentes en cada emit
                                //collector.emit(new Values(miRNA_id, miRNA, mre, lncRNA_id, lncRNA, pos));
                                energyPredictor ep = new energyPredictor();
                                ep.calculate_Energy(miRNA_id,miRNA,rev_mre,lncRNA_id,lncRNA,position);
                            }
                            
                    }
                    
            }

            inf += 1;
            sup += 1;
            
        }
        
    }
}
