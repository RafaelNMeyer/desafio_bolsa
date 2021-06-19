package br.ufsc.labsec.pbad.hiring.criptografia.certificado;

import br.ufsc.labsec.pbad.hiring.Constantes;
import br.ufsc.labsec.pbad.hiring.InsertDataTable;
import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.asn1.x509.Certificate;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe responsável por gerar certificados no padrão X.509.
 * <p>
 * Um certificado é basicamente composto por três partes, que são:
 * <ul>
 * <li>
 * Estrutura de informações do certificado;
 * </li>
 * <li>
 * Algoritmo de assinatura;
 * </li>
 * <li>
 * Valor da assinatura.
 * </li>
 * </ul>
 */

public class GeradorDeCertificados {

    /**
     * Gera a estrutura de informações de um certificado.
     *
     * @param chavePublica  chave pública do titular.
     * @param numeroDeSerie número de série do certificado.
     * @param nome          nome do titular.
     * @param nomeAc        nome da autoridade emissora.
     * @param dias          a partir da data atual, quantos dias de validade
     *                      terá o certificado.
     * @return Estrutura de informações do certificado.
     */
    public TBSCertificate gerarEstruturaCertificado(PublicKey chavePublica, int numeroDeSerie, String nome, String nomeAc, int dias) {
        V3TBSCertificateGenerator generator = new V3TBSCertificateGenerator();
        generator.setSubject(new X500Name(nome));
        generator.setSerialNumber(new ASN1Integer(numeroDeSerie));
        generator.setIssuer(new X500Name(nomeAc));
        try {
            generator.setSubjectPublicKeyInfo(SubjectPublicKeyInfo.getInstance(new ASN1InputStream(chavePublica.getEncoded()).readObject()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();

        Date date = c.getTime();
        generator.setStartDate(new Time(date));

        Date dt = new Date();
        c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, dias);
        dt = c.getTime();
        generator.setEndDate(new Time(dt));

        AlgorithmIdentifier sigAlgID = new AlgorithmIdentifier(PKCSObjectIdentifiers.x509Certificate, DERNull.INSTANCE);
        generator.setSignature(sigAlgID);

        TBSCertificate certificate = generator.generateTBSCertificate();

        return certificate;
    }

    /**
     * Gera valor da assinatura do certificado.
     *
     * @param estruturaCertificado estrutura de informações do certificado.
     * @param chavePrivadaAc       chave privada da AC que emitirá esse
     *                             certificado.
     * @return Bytes da assinatura.
     */
    public DERBitString geraValorDaAssinaturaCertificado(TBSCertificate estruturaCertificado, PrivateKey chavePrivadaAc) {
        try {
            java.security.Signature signature = java.security.Signature.getInstance("SHA1withECDSA");
            signature.initSign(chavePrivadaAc);
            byte[] signed = signature.sign();
            return new DERBitString(signed);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gera um certificado.
     *
     * @param estruturaCertificado  estrutura de informações do certificado.
     * @param algoritmoDeAssinatura algoritmo de assinatura.
     * @param valorDaAssinatura     valor da assinatura.
     * @return Objeto que representa o certificado.
     * @see ASN1EncodableVector
     */
    public X509Certificate gerarCertificado(TBSCertificate estruturaCertificado, AlgorithmIdentifier algoritmoDeAssinatura, DERBitString valorDaAssinatura){
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(estruturaCertificado);
        v.add(algoritmoDeAssinatura);
        v.add(valorDaAssinatura);

        ASN1Encodable asn1Encodable = new DERSequence(v);
        Certificate cert = Certificate.getInstance(asn1Encodable);

        try {
            byte[] encoded = cert.getEncoded();
            ByteArrayInputStream bis = new ByteArrayInputStream(encoded);
            java.security.cert.CertificateFactory cf = java.security.cert.CertificateFactory.getInstance(Constantes.formatoCertificado);
            return (java.security.cert.X509Certificate) cf.generateCertificate(bis);
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
