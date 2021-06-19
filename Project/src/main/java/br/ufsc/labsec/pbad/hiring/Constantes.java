package br.ufsc.labsec.pbad.hiring;

public class Constantes {

    public static final String algoritmoChave = "EC";
    public static final String formatoCertificado = "X.509";

    public static final String caminhoDb = "jdbc:sqlite:C://sqlite/db/certificateInfos.db";

    public static final String caminhoArtefatos =
            "D:/Ufsc/Project/src/main/resources/artefatos/";

    public static final String caminhoChavePublicaUsuario =
            caminhoArtefatos + "chaves/";
    public static final String caminhoChavePrivadaUsuario =
            caminhoArtefatos + "chaves/";

    public static final String caminhoCertificadoUsuario =
            caminhoArtefatos + "certificados/";
}
