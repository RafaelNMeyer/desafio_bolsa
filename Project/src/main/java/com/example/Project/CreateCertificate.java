package com.example.Project;

import br.ufsc.labsec.pbad.hiring.CreateTable;
import br.ufsc.labsec.pbad.hiring.etapas.SegundaEtapa;
import br.ufsc.labsec.pbad.hiring.etapas.TerceiraEtapa;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/create-certificate")
public class CreateCertificate {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createCertificate(final RequestBody requestBody) {

        CreateTable.createNewTable();

        String name = requestBody.name;
        String validate = requestBody.validate;

        int randomNumber = RandomSerial.randomSerial();

        SegundaEtapa.executarEtapa(name, randomNumber);
        TerceiraEtapa.executarEtapa(name, Integer.parseInt(validate), randomNumber);

        return requestBody.name;
    }
}