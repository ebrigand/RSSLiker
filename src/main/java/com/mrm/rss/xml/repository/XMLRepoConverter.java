package com.mrm.rss.xml.repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.Resource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.stereotype.Component;

import com.mrm.rss.xml.model.Repo;

/**
 * Converter to load/save a Repo object from a XML file
 * 
 * @author ebrigand
 * 
 */
@Component(value = "xmlRepoConverter")
public class XMLRepoConverter {

  @Resource(name = "castorMarshaller")
  private Marshaller marshaller;

  @Resource(name = "castorMarshaller")
  private Unmarshaller unmarshaller;

  public Marshaller getMarshaller() {
    return marshaller;
  }

  public void setMarshaller(Marshaller marshaller) {
    this.marshaller = marshaller;
  }

  public Unmarshaller getUnmarshaller() {
    return unmarshaller;
  }

  public void setUnmarshaller(Unmarshaller unmarshaller) {
    this.unmarshaller = unmarshaller;
  }

  /**
   * Load a Repo object from a XML file
   * 
   * @param xmlFilePath
   *          a XML absolute file path
   * @return a Repo object load from the XML file
   * @throws IOException
   *           if an exception occurs when accessing the file
   */
  public Repo convertFromXMLToRepo(String xmlFilePath) throws IOException {
    FileInputStream is = null;
    try {
      is = new FileInputStream(xmlFilePath);
      return (Repo) getUnmarshaller().unmarshal(new StreamSource(is));
    } catch (XmlMappingException xme) {
      // return a new Repo if a XmlMappingException occurs (if the file is
      // empty)
      return new Repo();
    } finally {
      if (is != null) {
        is.close();
      }
    }
  }

  /**
   * Save a Repo object in a XML file
   * 
   * @param repo
   *          a Repo object
   * @param xmlFilePath
   *          a XML absolute file path
   * @throws IOException
   *           if an exception occurs when accessing / saving the file
   */
  public void convertFromRepoToXML(Repo repo, String xmlFilePath) throws IOException {
    FileOutputStream os = null;
    try {
      os = new FileOutputStream(xmlFilePath);
      getMarshaller().marshal(repo, new StreamResult(os));
    } finally {
      if (os != null) {
        os.close();
      }
    }
  }
}
