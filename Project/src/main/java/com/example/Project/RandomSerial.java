package com.example.Project;

import br.ufsc.labsec.pbad.hiring.Constantes;
import br.ufsc.labsec.pbad.hiring.criptografia.certificado.LeitorDeCertificados;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.*;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Random;

public class RandomSerial {
    public static int randomSerial(){

        Random rand = new Random();
        int randomNumber = rand.nextInt(10000);
        ArrayList<BigInteger> serials = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("D:/Ufsc/Project/src/main/resources/artefatos/certificados"))) {
            for (java.nio.file.Path file: stream) {

                X509Certificate cerc = LeitorDeCertificados.lerCertificadoDoDisco(Constantes.caminhoCertificadoUsuario+file.getFileName());

                serials.add(cerc.getSerialNumber());

            }
        } catch (IOException | DirectoryIteratorException ex) {
            System.err.println(ex);
        }
        int i = 0;
        while(i < serials.size()){
            if(serials.get(i).intValue() == randomNumber){
                randomNumber = rand.nextInt(10000);
                i=0;
                continue;
            }
            i++;
        }
        return randomNumber;
    }
}
