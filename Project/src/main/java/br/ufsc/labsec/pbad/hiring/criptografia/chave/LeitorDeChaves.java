package br.ufsc.labsec.pbad.hiring.criptografia.chave;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.*;

/**
 * Classe responsável por ler uma chave assimétrica do disco.
 *
 * @see KeyFactory
 * @see KeySpec
 */
public class LeitorDeChaves {

    /**
     * Lê a chave privada do local indicado.
     *
     * @param caminhoChave local do arquivo da chave privada.
     * @param algoritmo    algoritmo de criptografia assimétrica que a chave
     *                     foi gerada.
     * @return Chave privada.
     */
    public static PrivateKey lerChavePrivadaDoDisco(String caminhoChave, String algoritmo){
        File pemFile = new File(caminhoChave);
        PemReader reader = null;
        PemObject pemObject = null;
        byte[] content = null;
        PrivateKey privateKey = null;
        try {
            reader = new PemReader(new FileReader(pemFile));
            pemObject = reader.readPemObject();
            reader.close();
            content = pemObject.getContent();
            KeyFactory kf = KeyFactory.getInstance(algoritmo);
            EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(content);
            privateKey = kf.generatePrivate(keySpec);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    /**
     * Lê a chave pública do local indicado.
     *
     * @param caminhoChave local do arquivo da chave pública.
     * @param algoritmo    algoritmo de criptografia assimétrica que a chave
     *                     foi gerada.
     * @return Chave pública.
     */
    public static PublicKey lerChavePublicaDoDisco(String caminhoChave, String algoritmo){
        File pemFile = new File(caminhoChave);
        PemReader reader = null;
        PemObject pemObject = null;
        byte[] content = null;
        PublicKey publicKey = null;

        try {
            reader = new PemReader(new FileReader(pemFile));
            pemObject = reader.readPemObject();
            reader.close();
            content = pemObject.getContent();
            KeyFactory kf = KeyFactory.getInstance(algoritmo);
            EncodedKeySpec keySpec = new X509EncodedKeySpec(content);
            publicKey = kf.generatePublic(keySpec);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

}
