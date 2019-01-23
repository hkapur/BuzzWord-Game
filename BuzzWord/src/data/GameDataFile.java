package data;


import components.AppDataComponent;
import components.AppFileComponent;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.*;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

public class GameDataFile implements AppFileComponent {

    public int value;
    HashSet<String> hset =
            new HashSet<String>();
    String f;
    @Override
    public void savelevel(AppDataComponent data, Path to,int x) throws IOException {
        GameData gamedata = (GameData) data;

        try {
            String filename = String.valueOf(to);
            f = filename;
            FileInputStream fis = new FileInputStream(String.valueOf(filename));
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = null;
            FileWriter fw = new FileWriter(filename, true);
            String u = gamedata.getLoguser();
            while ((line = br.readLine()) != null) {
                line = DecryptText(line);
                if (line.equals(u)) {
                    br.readLine();
                    br.readLine();
                    fw.write(String.valueOf(x));
                    fw.close();
                }
            }
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }

    }
    public void hashh() {
        Set<String> crime = new HashSet<String>();
        FileInputStream fis = null;
        String line =null;
        try {
            fis = new FileInputStream(String.valueOf(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        try {
            while ((line = br.readLine()) != null) {
                line = br.readLine();
                //String[] words = line.split("[ˆa-zA-Z]+");
                for (String word : line.split("[ˆa-zA-Z]+")) {
                    crime.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void saveData(AppDataComponent data, Path to) throws IOException {
        GameData gamedata = (GameData) data;
        try
        {
            int x = 1;
            String filename = String.valueOf(to);
            FileWriter fw = new FileWriter(filename,true);
            fw.write(System.getProperty( "line.separator" ));
            fw.write(EncryptText(gamedata.getUser()));
            fw.write(System.getProperty( "line.separator" ));
            fw.write(EncryptText(gamedata.getPass()));
            fw.write(System.getProperty( "line.separator" ));
            fw.write(String.valueOf(x));
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    private static byte[] sharedvector = {
            0x01, 0x02, 0x03, 0x05, 0x07, 0x0B, 0x0D, 0x11
    };

    public String EncryptText(String RawText)
    {
        String EncText = "";
        byte[] keyArray = new byte[24];
        byte[] temporaryKey;
        String key = "developersnotedotcom";
        byte[] toEncryptArray = null;

        try
        {

            toEncryptArray =  RawText.getBytes("UTF-8");
            MessageDigest m = MessageDigest.getInstance("MD5");
            temporaryKey = m.digest(key.getBytes("UTF-8"));

            if(temporaryKey.length < 24) // DESede require 24 byte length key
            {
                int index = 0;
                for(int i=temporaryKey.length;i< 24;i++)
                {
                    keyArray[i] =  temporaryKey[index];
                }
            }

            Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyArray, "DESede"), new IvParameterSpec(sharedvector));
            byte[] encrypted = c.doFinal(toEncryptArray);
            EncText = Base64.encodeBase64String(encrypted);

        }
        catch(NoSuchAlgorithmException | UnsupportedEncodingException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException NoEx)
        {
            JOptionPane.showMessageDialog(null, NoEx);
        }

        return EncText;
    }

    public String DecryptText(String EncText)
    {

        String RawText = "";
        byte[] keyArray = new byte[24];
        byte[] temporaryKey;
        String key = "developersnotedotcom";
        byte[] toEncryptArray = null;

        try
        {
            MessageDigest m = MessageDigest.getInstance("MD5");
            temporaryKey = m.digest(key.getBytes("UTF-8"));

            if(temporaryKey.length < 24) // DESede require 24 byte length key
            {
                int index = 0;
                for(int i=temporaryKey.length;i< 24;i++)
                {
                    keyArray[i] =  temporaryKey[index];
                }
            }

            Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyArray, "DESede"), new IvParameterSpec(sharedvector));
            byte[] decrypted = c.doFinal(Base64.decodeBase64(EncText));

            RawText = new String(decrypted, "UTF-8");
        }
        catch(NoSuchAlgorithmException | UnsupportedEncodingException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException NoEx)
        {
            JOptionPane.showMessageDialog(null, NoEx);
        }

        return RawText;

    }

    public void load(){
        hashh();
    }
    @Override
    public boolean loadData(AppDataComponent data, Path filePath) throws IOException {
        GameData gamedata = (GameData) data;

        FileInputStream fis = new FileInputStream(String.valueOf(filePath));
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line = null;
        String line2;
        String u = gamedata.getLoguser();
        String v = gamedata.getLogpass();
        while ((line = br.readLine()) != null) {
            line = DecryptText(line);
            if(line.equals(u)){
               line2 = br.readLine();
                line2 = DecryptText(line2);
                if(line2.equals(v)) {
                    String n = br.readLine();
                    value = Integer.parseInt((n.substring(n.length()-1)));
                    setval(value);
                    return true;
                }
            }
        }
        br.close();
        return false;
    }

    public void setval(int value)
    {
        this.value = value;
    }

    public int getval()
    {
        return value;
    }

    @Override
    public void exportData(AppDataComponent data, Path filePath) throws IOException { }
}
