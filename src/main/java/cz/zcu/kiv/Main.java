package cz.zcu.kiv;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import tools.JenaBeanExtensionTool;
import tools.Syntax;

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
            String file = "/home/petr-jezek/elasticSearchData/json_one_exp.json";
            //String file = "/tmp/example.json";
            JsonReader reader = new JsonReader(new FileReader(file));
            reader.setLenient(true);
            Object o = new Object();

            Object res = gson.fromJson(reader, o.getClass());
            System.out.println(res);
            Map linkedTeeMap = (Map) res;
            List<Object> resList = new ArrayList<Object>();
            for(Object entry : linkedTeeMap.entrySet()) {
                Map.Entry mapEntry = (Map.Entry) entry;
                System.out.println("entry: " + mapEntry.getKey() + " => " + mapEntry.getValue());
                resList.add(mapEntry);
            }
            JenaBeanExtensionTool jbe = new JenaBeanExtensionTool();

			/* load the ontology header from a file */
            //jbe.loadStatements(new FileInputStream("ontologyHeader.rdf.xml"), Syntax.RDF_XML_ABBREV);
			/* load and transform the OOM */


            jbe.loadOOM(resList, false);

			/* get the ontology document in RDF/XML */
            FileOutputStream out = new FileOutputStream("ontologyDocument.rdf.xml");
            jbe.writeOntologyDocument(out, Syntax.RDF_XML_ABBREV);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
