//Fazer a insercao no fim da Lista usando o IF
//Fazer pesquisas sequencias usando o atributo nome
//Printa se encontrou o nome na pesquisa se achar SIM, caso não ache NAO

import java.util.*;
import java.io.*;
import java.text.*;


//Classe Filmes
class Filme{
   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
   private String Nome;
   private String Titulo;
   private Date Datadelancamento;
   private int Duracao;
   private String Genero;
   private String Idioma;
   private String Situacao;
   private float Orcamento;
   private String[] Palavrachave;

   //Construtores
   public Filme(){
      Nome = "";
      Titulo = "";
      Duracao = 0;
      Genero = "";
      Idioma = "";
      Situacao = "";
      Orcamento = 0;
      Palavrachave = new String[1000];
   }
   
   public Filme(String Nome,String Titulo,Date Datadelancamento,int Duracao,String Genero,String Idioma,String Situacao,float Orcamento,String[] Palavrachave){
      this.Nome = Nome;
      this.Titulo = Titulo;
      this.Datadelancamento = Datadelancamento;
      this.Duracao = Duracao;
      this.Genero = Genero;
      this.Idioma = Idioma;
      this.Situacao = Situacao;
      this.Orcamento = Orcamento;
      this.Palavrachave = Palavrachave;
   }

   //Metodos sets
   public void setNome(String Nome){this.Nome = Nome;};
   public void setTitulo(String Titulo){this.Titulo = Titulo;};
   public void setDatalancamento(Date Datadelancamento){this.Datadelancamento = Datadelancamento;};
   public void setDuracao(int Duracao){this.Duracao = Duracao;};
   public void setGenero(String Genero){this.Genero = Genero;};
   public void setIdioma(String Idioma){this.Idioma = Idioma;};
   public void setSituacao(String Situacao){this.Situacao = Situacao;};
   public void setOrcamento(float Orcamento){this.Orcamento = Orcamento;};
   public void setPalavrachave(String[] Palavrachave){this.Palavrachave = Palavrachave;};

   //Metodos gets
   public String getNome(){return this.Nome;};
   public String getTitulo(){return this.Titulo;};
   public Date getDatadelancamento(){return this.Datadelancamento;};
   public int getDuracao(){return this.Duracao;};
   public String getGenero(){return this.Genero;};
   public String getIdioma(){return this.Idioma;};
   public String getSituacao(){return this.Situacao;};
   public float getOrcamento(){return this.Orcamento;};
   public String[] getPalavrachave(){return this.Palavrachave;};   
   public String getPalavrachaveString(){
     String palavraschave = "";
     //MyIO.print("[");
     for(String s:this.Palavrachave){
       palavraschave +=  s + ",";
       if(s == null){
         break;
       }
       
       
     }
     palavraschave = palavraschave.replaceAll(",null,", "");
    // MyIO.print("]");
     
     return palavraschave;
   }

 
   //Metodo clone
   public void Clone(){
     Filme filmes = new Filme();
        filmes.setNome(this.Nome);
        filmes.setTitulo(this.Titulo);
        filmes.setDatalancamento(this.Datadelancamento);
        filmes.setDuracao(this.Duracao);
        filmes.setGenero(this.Genero);
        filmes.setIdioma(this.Idioma);
        filmes.setSituacao(this.Situacao);
        filmes.setOrcamento(this.Orcamento);
        filmes.setPalavrachave(this.Palavrachave);
   }

   public String removeTags(String line){
    String resp = "";
    int i = 0;
    while(i < line.length()){ 
        if(line.charAt(i) == '<'){ 
            i++;
            while(line.charAt(i) != '>') i++;
        } else if(line.charAt(i) == '&'){ 
          i++;
          while(line.charAt(i) != ';') i++;
      } else { 
            resp += line.charAt(i);
        }
        i++;
    }
    return resp;
   }

   public static String pegarIdioma(String limpa){
    limpa = limpa.replaceAll("Idioma original", "");
    return limpa;
   }

   public static String pegarTitulo(String limpa){
    limpa = limpa.replaceAll("Título original", "");
    return limpa;
   }

   public static String pegarSituacao(String limpa){
     limpa = limpa.replaceAll("Situação", "");
     return limpa;
   }

   public static String pegarOrcamento(String limpa){
     limpa = limpa.replaceAll("Orçamento $", "");
     return limpa;
   }

   public static int pegarDuracao(String limpa){
     String line = "";
     line = limpa.trim();
     int duracao = 0;
     
     if(line.contains("h")){
       duracao += Integer.parseInt(line.charAt(0)+"")*60;
       if(line.contains("m")){
         line.trim();
         String minutos = line.substring(3, line.indexOf("m"));
         duracao += Integer.parseInt(minutos);
       }
     }

     else if(line.contains("m")){
       String minutos = line.substring(0, (line.indexOf("m")));
       duracao += Integer.parseInt(minutos);
     }
     return duracao;
   }

   public static String buscarAteParenteses(String filename){
     String line = "";

    for(int i = 0;filename.charAt(i) != '('; i++){
        line += filename.charAt(i);
    }    
     return line;
   }

   private String removeLetters(String value){
      // Data declaration
      String result = "";

      for(int i = 0; i < value.length(); i++){
          // If char is a number, a blank space, or a '.' (Used on convertBudget), will be stored into "result"
          if( (value.charAt(i) >= 48 && value.charAt(i) <= 57) || value.charAt(i) == ' ' || value.charAt(i) == '.')
              result += value.charAt(i);
      }
      return result;
   } 

   private Float convertBudget(String value){
        return Float.parseFloat(removeLetters(value));
     }



   public void lerHtml(String filename) throws ParseException{   
     String line = "";
     String verde = "/tmp/filmes/" + filename;
     String teste =  filename;
     //String teste2 = "/Users/1325905/Documents/TP2/" + filename;

     try{
      FileReader reader = new FileReader(verde);
      BufferedReader buffer = new BufferedReader(reader);
      
          line  = buffer.readLine();
          while(!line.contains("<title>")){
            line = buffer.readLine();
          }

          this.setNome((buscarAteParenteses(removeTags(line))).trim());

          //line = buffer.readLine();
          while(!line.contains("span class=\"release\"")){
            line = buffer.readLine();
          }
          line = buffer.readLine();
          this.setDatalancamento(sdf.parse(buscarAteParenteses(line).trim()));

          line = buffer.readLine();
          while(!line.contains("span class=\"genres\"")){
            line = buffer.readLine();
          }
          line = buffer.readLine();
          line = buffer.readLine();
          this.setGenero(removeTags(line).trim());

          line = buffer.readLine();
          while(!line.contains("span class=\"runtime\"")){
            line = buffer.readLine();
          }
          line = buffer.readLine();
          line = buffer.readLine();
          this.setDuracao(pegarDuracao((line)));


          line = buffer.readLine();
          while(!line.contains("p class=\"wrap\"") && (!line.contains("<strong><bdi>"))){
            line = buffer.readLine();
          }
          if(line.contains("p class=\"wrap\"")){
            this.setTitulo(removeTags(pegarTitulo(line)).trim());
          }
          if(line.contains("<strong><bdi>")){
            this.Titulo = this.Nome;
          }

          while(!line.contains("<strong><bdi>")){
            line = buffer.readLine();
          }
          this.setSituacao(removeTags(pegarSituacao(line)).trim());    

          line = buffer.readLine();
          while(!line.contains("<p><strong>")){
            line = buffer.readLine();
          }
          this.setIdioma(removeTags(pegarIdioma(line)).trim());

          line = buffer.readLine();
          while(!line.contains("Orçamento</bdi>"));
            String aux = removeTags(line.replace("Orçamento", " ")).trim();
            this.setOrcamento((aux.equals("-")) ? 0.0F : convertBudget(aux));
         
          String[] entrada = new String[20];
          int cont = 0;
          line = buffer.readLine();
          while(!line.contains("</ul>")){
            line = buffer.readLine();
            if(line.contains("<li>")){
              entrada[cont++] = removeTags(line).trim();   
            }
          }
          cont = cont>0?cont-1:0;
          Palavrachave = new String[cont];
          this.setPalavrachave(entrada);

       buffer.close();
     }catch(FileNotFoundException e){
      System.out.println("Nao encontrei no arquivo");
     }catch(ParseException e){
      e.getLocalizedMessage();
     }catch(IOException e){
      System.out.println("Nao consigo ler o arquivo");
     }
   }

   public String imprimir(){
     return (getNome() + " " + getTitulo()+ " " + sdf.format(getDatadelancamento())+ " " + getDuracao() + " " + getGenero() + " " + getIdioma() + " " +getSituacao() + " " +getOrcamento() + " [" + getPalavrachaveString() + "]");  
   }

}//fechando a classe Filmes




class Lista{
      private Filme array[];
      private int n;

      public Lista(){
         this(1000);
      }

      public Lista(int tamanho){
        array = new Filme[tamanho];
        n = 0;
      }

      public void inserirFim(Filme nome)throws Exception{
         if(n >= array.length){
           throw new Exception("Erro ao inserir");
         }
           array[n] = nome;
           n++;
      }
}

//classe Principal
class QT4{

    public static boolean Pesquisar(Filme[] vet,int dir ,String x){
        boolean resp = false;
        int esq = 0, meio;
        dir -=1; 
        while (esq <= dir){
           meio = (esq + dir) / 2;
           if(x.equals(vet[meio].getNome())){
              resp = true;
              esq = dir + 1;
           } else if (x.compareTo(vet[meio].getNome())>0 ) {
              esq = meio + 1;
           } else {
              dir = meio - 1;
           }
        }
        return resp;
     }
   public static void main(String[] args)throws Exception{
     MyIO.setCharset("UTF-8");

      String[] leitura = new String[1000];
      String[] nomeFilmes = new String[1000];
      Filme[] filmes = new Filme[1000];     
      int Nentrada = 0,Nentrada2 = 0;
     
      String line = MyIO.readLine();
      while(!line.contains("FIM")){
        leitura[Nentrada++] = line;     
        line = MyIO.readLine();
      }

      line = MyIO.readLine();
      while(!line.contains("FIM")){
        nomeFilmes[Nentrada2++] = line;     
        line = MyIO.readLine();
      }
      
      
     for(int i = 0 ; i < Nentrada;i++){
       filmes[i] = new Filme();
      try{
       filmes[i].lerHtml(leitura[i]);
      }catch(NullPointerException e){
         e.printStackTrace();
      }catch(ParseException e){
         e.printStackTrace();
      }catch(Exception e){
         e.printStackTrace();
      }
     }

     for(int i = 0;i < Nentrada2; i++){
      if(Pesquisar(filmes,Nentrada,nomeFilmes[i]) == true)
        MyIO.println("SIM");       
      else
        MyIO.println("NAO");
     }    
   }
}
         