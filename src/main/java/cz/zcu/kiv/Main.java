package cz.zcu.kiv;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


import cz.zcu.kiv.data.crcns.json.Crcns;
import cz.zcu.kiv.data.crcns.json.Doc;
import cz.zcu.kiv.data.crcns.xml.CrcnsDocument;
import cz.zcu.kiv.data.crcns.xml.Resource;
import org.apache.commons.codec.binary.Base64;
import tools.JenaBeanExtensionTool;
import tools.Syntax;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            Gson gson = new Gson();
            String file = "/home/petr-jezek/CRCNs_data/crcns.json";
            //        String file = "/tmp/example.json";
            JsonReader reader = new JsonReader(new FileReader(file));
            reader.setLenient(true);

            List<Object> resList = new ArrayList<Object>();
            Crcns res = gson.fromJson(reader, Crcns.class);
            JAXBContext jaxbContext = JAXBContext.newInstance(Resource.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            for (Doc item : res.getResponse().getDocs()) {
                byte[] tmpRes = Base64.decodeBase64(item.getXml());
                String xmlRes = new String(tmpRes);
                Resource resource = (Resource) jaxbUnmarshaller.unmarshal(new StringReader(xmlRes));
                resList.add(new CrcnsDocument(item, resource));
            }


            JenaBeanExtensionTool jbe = new JenaBeanExtensionTool();

			/* load the ontology header from a file */
            //jbe.loadStatements(new FileInputStream("ontologyHeader.rdf.xml"), Syntax.RDF_XML_ABBREV);
            /* load and transform the OOM */


            jbe.loadOOM(resList, false);

			/* get the ontology document in RDF/XML */
            FileOutputStream out = new FileOutputStream("ontologyDocument.rdf.xml");
            jbe.writeOntologyDocument(out, Syntax.RDF_XML);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
