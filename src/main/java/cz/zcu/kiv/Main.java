package cz.zcu.kiv;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;


import cz.zcu.kiv.data.crcns.xml.Resource;
import cz.zcu.kiv.data.eegbase.json.Crcns;
import cz.zcu.kiv.data.eegbase.json.Doc;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.cfg.SecondaryTableSecondPass;
import tools.JenaBeanExtensionTool;
import tools.Syntax;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
                //System.out.println(xmlRes);
                //System.out.println("---------------------");
                Resource resource = (Resource) jaxbUnmarshaller.unmarshal(new StringReader(xmlRes));
                resList.add(new CrcnsDocument(item, resource));
            }

            //    }
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
