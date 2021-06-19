package br.ufsc.labsec.pbad.hiring.etapas;

import br.ufsc.labsec.pbad.hiring.Constantes;
import br.ufsc.labsec.pbad.hiring.InsertDataTable;
import br.ufsc.labsec.pbad.hiring.criptografia.certificado.EscritorDeCertificados;
import br.ufsc.labsec.pbad.hiring.criptografia.certificado.GeradorDeCertificados;
import br.ufsc.labsec.pbad.hiring.criptografia.certificado.LeitorDeCertificados;
import br.ufsc.labsec.pbad.hiring.criptografia.chave.LeitorDeChaves;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.x509.TBSCertificate;


import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.*;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Random;

public class TerceiraEtapa {

    public static void executarEtapa(String name, int dias, int randomNumber) {
        GeradorDeCertificados gerador = new GeradorDeCertificados();


        PublicKey publicKeyUser = LeitorDeChaves.lerChavePublicaDoDisco(Constantes.caminhoChavePublicaUsuario+"chavePublica"+name+randomNumber+".pem", Constantes.algoritmoChave);
        PrivateKey privateKeyUser = LeitorDeChaves.lerChavePrivadaDoDisco(Constantes.caminhoChavePrivadaUsuario+"chavePrivada"+name+randomNumber+".pem", Constantes.algoritmoChave);

        TBSCertificate tbsCertificateUser = gerador.gerarEstruturaCertificado(publicKeyUser, randomNumber, "CN="+name, "CN="+"", dias);

        DERBitString valorUser = gerador.geraValorDaAssinaturaCertificado(tbsCertificateUser, privateKeyUser);

        X509Certificate certificateUser = gerador.gerarCertificado(tbsCertificateUser, tbsCertificateUser.getSignature(), valorUser);

        InsertDataTable insertData = new InsertDataTable();
        insertData.insert(name, Long.toString((certificateUser.getNotAfter().getTime())), randomNumber);


        try {
            EscritorDeCertificados.escreveCertificado(Constantes.caminhoCertificadoUsuario+"certificado"+name+randomNumber+".pem", certificateUser.getEncoded());
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }

    }
}
