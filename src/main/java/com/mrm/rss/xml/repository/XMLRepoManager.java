package com.mrm.rss.xml.repository;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mrm.rss.xml.model.Repo;

/**
 * Class to open or save a XML file
 * 
 * @author ebrigand
 * 
 */
@Service
public class XMLRepoManager {

  // TODO parameterize the file name and file path in a property
  private static String REPO_ABSOLUTE_FILE_PATH = "repo.xml";

  @Resource(name = "xmlRepoConverter")
  private XMLRepoConverter xmlConverter;

  /**
   * Create the repo xml file at the start of the web app if the file not exists
   * 
   * @throws IOException
   *           if an exception occurs when creating the file
   */
  @PostConstruct
  private void initialize() throws IOException {
    File repo = new File(REPO_ABSOLUTE_FILE_PATH);
    if (!repo.exists()) {
      repo.createNewFile();
    }
  }

  /**
   * Open the repo XML file
   * 
   * @return the Repo object
   * @throws IOException
   *           if an exception occurs when accessing the file
   */
  public Repo openRepo() throws IOException {
    Repo repo = xmlConverter.convertFromXMLToRepo(REPO_ABSOLUTE_FILE_PATH);
    return repo;
  }

  /**
   * Save the repo XML file
   * 
   * @param repo
   * @throws IOException
   *           if an exception occurs when accessing / saving the file
   */
  public synchronized void saveRepo(Repo repo) throws IOException {
    xmlConverter.convertFromRepoToXML(repo, REPO_ABSOLUTE_FILE_PATH);
  }
}