package br.ufsc.labsec.pbad.hiring.etapas;

import br.ufsc.labsec.pbad.hiring.Constantes;
import br.ufsc.labsec.pbad.hiring.criptografia.chave.EscritorDeChaves;
import br.ufsc.labsec.pbad.hiring.criptografia.chave.GeradorDeChaves;

import java.security.KeyPair;

public class SegundaEtapa {

    public static void executarEtapa(String name, int randomNumber) {
        GeradorDeChaves gerador = new GeradorDeChaves("EC");

        KeyPair keyPair256 = gerador.gerarParDeChaves(256);

        EscritorDeChaves.escreveChaveEmDisco(keyPair256.getPublic(), Constantes.caminhoChavePublicaUsuario+"chavePublica"+name+randomNumber+".pem");
        EscritorDeChaves.escreveChaveEmDisco(keyPair256.getPrivate(), Constantes.caminhoChavePrivadaUsuario+"chavePrivada"+name+randomNumber+".pem");

    }

}
