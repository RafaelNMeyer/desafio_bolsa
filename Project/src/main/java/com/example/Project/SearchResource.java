package com.example.Project;

import br.ufsc.labsec.pbad.hiring.Constantes;
import br.ufsc.labsec.pbad.hiring.SearchDataTable;
import br.ufsc.labsec.pbad.hiring.criptografia.certificado.LeitorDeCertificados;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

@Path("/search-data")
public class SearchResource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String searchCertificate(final RequestBody requestBody) {

        SearchDataTable d = new SearchDataTable();
        ArrayList<UserData> userData = null;

        if(Double.isNaN(Double.parseDouble(requestBody.finalDateTime))) {
            userData = d.onlyName(requestBody.name);
        } else {
            userData = d.getData(requestBody.name, requestBody.inDateTime, requestBody.finalDateTime);
        }

        ArrayList<CertificateInfos> cercs = new ArrayList<>();
        X509Certificate cerc = null;
        for (int i = 0; i < userData.size(); i++) {
            CertificateInfos cercInfo = new CertificateInfos();
            try (DirectoryStream<java.nio.file.Path> stream = Files.newDirectoryStream(Paths.get("D:/Ufsc/Project/src/main/resources/artefatos/certificados"))) {
                for (java.nio.file.Path file: stream) {
                    String name = userData.get(i).getName();
                    long validate = Long.parseLong(userData.get(i).getvalidate());

                    cerc = LeitorDeCertificados.lerCertificadoDoDisco(Constantes.caminhoCertificadoUsuario+file.getFileName());

                    String name1 = ("CN="+name).trim().toLowerCase();
                    String name2 = cerc.getSubjectX500Principal().getName().trim().toLowerCase();

                    if(name1.equals(name2) && validate == cerc.getNotAfter().getTime()){
                        cercInfo.setName(cerc.getSubjectX500Principal().getName());
                        cercInfo.setvalidate(cerc.getNotAfter());
                        cercInfo.setSerial(cerc.getSerialNumber());
                        cercInfo.setPublicKey(cerc.getPublicKey().toString());
                        cercs.add(cercInfo);
                    }
                }
            } catch (IOException | DirectoryIteratorException ex) {
                System.err.println(ex);
            }
        }
        String json = new Gson().toJson(cercs);
        return json;
    }
}