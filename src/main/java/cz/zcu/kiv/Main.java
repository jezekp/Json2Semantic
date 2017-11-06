package cz.zcu.kiv;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.apache.commons.lang.WordUtils;
import tools.JenaBeanExtensionTool;
import tools.Syntax;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        try {
            if(args.length > 0) {
                InputStream is = Main.class.getClassLoader().getResourceAsStream("my.properties");
                Properties p = new Properties();
                p.load(is);

                Gson gson = new Gson();

                String file = args[0];
                JsonReader reader = new JsonReader(new FileReader(file));
                reader.setLenient(true);

                String packageName = p.getProperty("pojo.package");
                java.io.File fileName = new java.io.File(file);
                String capitalizedName = WordUtils.capitalize(fileName.getName());
                capitalizedName = WordUtils.capitalize(capitalizedName, new char[]{'.'});
                capitalizedName = WordUtils.capitalize(capitalizedName, new char[]{'-'});
                capitalizedName = WordUtils.capitalize(capitalizedName, new char[]{'_'});
                String nameWithoutExtension = capitalizedName.substring(0, capitalizedName.lastIndexOf('.')).replaceAll("[.]", "");
                nameWithoutExtension = nameWithoutExtension
                        .replaceAll("-", "")
                        .replaceAll("_", "");

                //org.jsonschema2pojo transformation is done according to the input json file -> configured in pom.xml.
                Object res2 = gson.fromJson(reader, Class.forName(packageName + "." + nameWithoutExtension));

                List<Object> resList = new ArrayList<Object>();
                resList.add(res2);

                JenaBeanExtensionTool jbe = new JenaBeanExtensionTool();

			/* load the ontology header from a packageName */
                //jbe.loadStatements(new FileInputStream("ontologyHeader.rdf.xml"), Syntax.RDF_XML_ABBREV);
            /* load and transform the OOM */


                jbe.loadOOM(resList, false);

			/* get the ontology document in RDF/XML */

                PrintStream out = new PrintStream(System.out);
                jbe.writeOntologyDocument(out, Syntax.RDF_XML);
                reader.close();
                out.close();

            }
            else {
                System.out.println("Input file has not been given");
                System.out.println("Usage: java -jar Json2Semantic[version].jar jsonfile");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
