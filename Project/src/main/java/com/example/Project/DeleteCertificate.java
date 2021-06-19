package com.example.Project;

import br.ufsc.labsec.pbad.hiring.Constantes;
import br.ufsc.labsec.pbad.hiring.DeleteDataTable;
import br.ufsc.labsec.pbad.hiring.SearchDataTable;
import br.ufsc.labsec.pbad.hiring.criptografia.certificado.LeitorDeCertificados;
import com.google.gson.Gson;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

@Path("/delete-data")
public class DeleteCertificate {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteCertificate(final RequestBody requestBody) {

        String name = requestBody.name;
        int serial = requestBody.serial;

        DeleteDataTable d = new DeleteDataTable();
        d.delete(name, serial);


        ArrayList<CertificateInfos> cercs = new ArrayList<>();
        X509Certificate cerc = null;

        CertificateInfos cercInfo = new CertificateInfos();

        String pathName = "";

        try (DirectoryStream<java.nio.file.Path> stream = Files.newDirectoryStream(Paths.get("D:/Ufsc/Project/src/main/resources/artefatos/certificados"))) {
            for (java.nio.file.Path file: stream) {

                cerc = LeitorDeCertificados.lerCertificadoDoDisco(Constantes.caminhoCertificadoUsuario+file.getFileName());

                String name1 = ("CN="+name).trim().toLowerCase();
                String name2 = cerc.getSubjectX500Principal().getName().trim().toLowerCase();

                if(name1.equals(name2) && serial == cerc.getSerialNumber().intValue()){
                    //aqui era pra deletar o arquivo mas nao estava funcionando o codigo...
                }
            }
        } catch (IOException | DirectoryIteratorException ex) {
            System.err.println(ex);
        }
        String json = new Gson().toJson(cercs);
        return json;
    }
}
