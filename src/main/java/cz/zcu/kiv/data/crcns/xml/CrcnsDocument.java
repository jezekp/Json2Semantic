package cz.zcu.kiv.data.crcns.xml;

import cz.zcu.kiv.data.crcns.xml.Resource;
import cz.zcu.kiv.data.crcns.json.Doc;

/**
 * Created by petr-jezek on 28.9.16.
 */
public class CrcnsDocument {

    private Resource resource;
    private String minted;
    private String doi;
    private String updated;

    public CrcnsDocument(Doc doc, Resource resource) {
        this.setMinted(doc.getMinted());
        this.setDoi(doc.getDoi());
        this.setUpdated(doc.getUpdated());
        this.setResource(resource);
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getMinted() {
        return minted;
    }

    public void setMinted(String minted) {
        this.minted = minted;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}