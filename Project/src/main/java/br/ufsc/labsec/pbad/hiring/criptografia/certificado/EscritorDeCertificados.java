package br.ufsc.labsec.pbad.hiring.criptografia.certificado;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.*;

/**
 * Classe responsável por escrever um certificado no disco.
 */
public class EscritorDeCertificados {

    /**
     * Escreve o certificado indicado no disco.
     *
     * @param nomeArquivo           caminho que será escrito o certificado.
     * @param certificadoCodificado bytes do certificado.
     */
    public static void escreveCertificado(String nomeArquivo, byte[] certificadoCodificado) {
        PemObject pemObject = new PemObject("Certificate", certificadoCodificado);
        PemWriter pemWriter = null;
        try {
            pemWriter = new PemWriter(new OutputStreamWriter(new FileOutputStream(nomeArquivo)));
            pemWriter.writeObject(pemObject);
            pemWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
