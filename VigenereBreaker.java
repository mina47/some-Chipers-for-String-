import java.util.*;
import edu.duke.*;
import java.io.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        //REPLACE WITH YOUR CODE
        StringBuilder output =new StringBuilder();
         for(int k=whichSlice;k<message.length();k+=totalSlices)   
          {
             output.append(message.charAt(k)); 
              
            }
        return output.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] keys = new int[klength];
        //WRITE YOUR CODE HERE
        CaesarCracker C = new CaesarCracker(mostCommon);
        for(int i=0;i<klength;i++)
        {
            String Slices =sliceString(encrypted,i,klength);
            keys[i]=C.getKey(Slices);
            
        }
        return keys;
    }

    
    HashSet <String> readDictionary (FileResource fr)
    {
        HashSet<String> dictionary =new HashSet<String> ();
      for(String w :fr.lines())
      {
          w=w.toLowerCase();
          dictionary.add(w);
        }
        return dictionary;
    }
    
    
    
    char mostCommonCharIn (HashSet<String> dictionary)
     {
         String alpha ="abcdefghijklmnopqrstuvwxyz";
         int [] ar =new int [26];
         for(String word : dictionary)
         { for(int i=0;i<word.length();i++)
             { int index=alpha.indexOf(word.charAt(i));
                if( index!=-1)
                {
                    ar[index]++;
                }
                }
          
             
            }
         int maxindex=0 , max=0;
         for(int k=0;k<ar.length;k++)
         { if(ar[k]> max )
             {
              max=ar[k];
              maxindex=k;
            }
         
        }
        return alpha.charAt(maxindex);
    }
     int countwords(HashSet<String> dictionary ,String message)
     { 
         int counter=0;
         String [] words =message.split("\\W+");
         for(String word :words)
         { word=word.toLowerCase();
             if(dictionary.contains(word))
             { counter++;
                }
             }
         return counter;
         
        }
        
       String FindKeyWithBiggestValue(HashMap <String,Integer> map)
       {
           int max=0;
        String Key="";
        for(String Currentkey : map.keySet())
        {
          if(map.get(Currentkey)  > max )
          {
             max=map.get(Currentkey);
             Key=Currentkey;
            }
        }
       return Key;
           
           
    }
        
    String breakForLanguage(String encrypted ,HashSet<String> dictionary)
    {
     HashMap <String,Integer> map =new HashMap <String,Integer> ();
     
     char common =mostCommonCharIn(dictionary);
     for(int i=1;i<100;i++)
     {
         VigenereCipher v =new VigenereCipher (tryKeyLength(encrypted,i,common));
         String message = v.decrypt(encrypted);
         int count =countwords(dictionary,message);
           //if(i==38)
           //{System.out.println(count);
             //  }
         map.put(message,count);
        }
        return FindKeyWithBiggestValue(map);
    }
         
       String breakForAllLanguages(String encrypted ,HashMap<String ,HashSet<String> > map)
       {   HashMap <String,Integer> countForLang =new HashMap <String,Integer>();
           for(String Language : map.keySet())
           { String message =breakForLanguage(encrypted  ,  map.get(Language)  ) ;
             countForLang.put( message ,  countwords(  map.get(Language)  ,  message  )   );
            }
            return FindKeyWithBiggestValue(countForLang);
        }
    
     int FindKeyLength(String encrypted,HashSet<String> dictionary)
     {
         String real =breakForLanguage(encrypted,dictionary);
         int keylength=0;
     for(int i=1;i<100;i++)
     {
         VigenereCipher v =new VigenereCipher (tryKeyLength(encrypted,i,'e'));
         String message = v.decrypt(encrypted);
         if(message.equals(real))
         {keylength=i;
            }
         
        }
        return keylength;
    }
     String FindLanguage(String message, HashMap<String ,HashSet<String> >map)
     { HashMap <String,Integer> LangCount =new HashMap <String,Integer>();
         for(String lang : map.keySet())
         { LangCount.put (lang  ,countwords( map.get(lang)  , message ) );
            }
        return FindKeyWithBiggestValue(LangCount);
        }
    
        public void breakVigenere () {
        //WRITE YOUR CODE HERE
        FileResource m =new FileResource();
        String encrypted =m.asString();
        HashMap<String ,HashSet<String> > map=new HashMap<String ,HashSet<String> >();
        DirectoryResource dr = new DirectoryResource();
        for(File F : dr.selectedFiles())
        {
            FileResource fr =new FileResource(F);
            map.put(F.getName() , readDictionary(fr));
        }
        //FileResource fr =new FileResource();
       //HashSet<String> Set =readDictionary(fr);
       String message =breakForAllLanguages(encrypted,map);
       System.out.println(message.substring(0,100)+"\n"+ FindLanguage(message,map));
       //System.out.println("The key length of message   "+ FindKeyLength(encrypted,Set));
       //System.out.println("Valid words with key 57  "+countwords(Set,message)+"\n"+message.substring(0,100));
       //System.out.println("Valid words with key 38  "+HowManyVaildWords(Set,encrypted,38));
      // System.out.println("The real words in message is  "+SSS.get(message) );
        // String message=fr.asString();
       //int [] keys =tryKeyLength(message,4,'e');
      // int[] keys ={3,20,10,4};
       //VigenereCipher veg =new VigenereCipher(keys);
       //String answer = veg.decrypt(message);
       //System.out.println(answer);
       //for(int key :keys)
       //{
         //  System.out.println(key);
        //}
       //System.out.println(sliceString("abcdefghijklm", 4, 5));
       // System.out.println(keys);
        
        
        
    }
}
