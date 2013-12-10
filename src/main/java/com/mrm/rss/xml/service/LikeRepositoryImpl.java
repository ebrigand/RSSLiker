package com.mrm.rss.xml.service;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.mrm.rss.xml.model.Like;
import com.mrm.rss.xml.model.LikeList;
import com.mrm.rss.xml.model.Repo;
import com.mrm.rss.xml.repository.XMLRepoManager;

/**
 * Like Repository implementation
 * 
 * @author ebrigand
 * 
 */
@Repository("likeRepository")
public class LikeRepositoryImpl implements LikeRepository<Like> {

  @Resource
  private XMLRepoManager xmlRepoManager;

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.mrm.rss.xml.service.XmlBaseRepository#saveOrUpdate(java.lang.Object)
   */
  @Override
  public synchronized void saveOrUpdate(Like entity) throws IOException {
    Repo repo = xmlRepoManager.openRepo();
    if (repo.getLikeMap().containsKey(entity.getAccountName())) {
      LikeList likeList = repo.getLikeMap().get(entity.getAccountName());
      boolean isExistsAndUpdated = false;
      for (int i = 0; i < likeList.getLikes().size(); i++) {
        if (likeList.getLikes().get(i).equals(entity)) {
          likeList.getLikes().set(i, entity);
          isExistsAndUpdated = true;
          break;
        }
      }
      if (!isExistsAndUpdated) {
        likeList.getLikes().add(entity);
      }
    } else {
      LikeList likeList = new LikeList();
      likeList.getLikes().add(entity);
      repo.getLikeMap().put(entity.getAccountName(), likeList);
    }
    xmlRepoManager.saveRepo(repo);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.mrm.rss.xml.service.XmlBaseRepository#isEntityExists(java.lang.Object)
   */
  @Override
  public boolean isEntityExists(Like entity) throws IOException {
    Repo repo = xmlRepoManager.openRepo();
    return isEntityExistsInternal(repo, entity);

  }

  /**
   * Internal research
   * 
   * @param repo
   * @param entity
   * @return
   * @throws IOException
   */
  private boolean isEntityExistsInternal(Repo repo, Like entity) throws IOException {
    if (repo.getLikeMap().containsKey(entity.getAccountName())) {
      LikeList likeList = repo.getLikeMap().get(entity.getAccountName());
      for (int i = 0; i < likeList.getLikes().size(); i++) {
        if (likeList.getLikes().get(i).equals(entity)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public Like find(String accountName, String uriWithoutSpecialChars) throws IOException {
    Repo repo = xmlRepoManager.openRepo();
    if (repo.getLikeMap().containsKey(accountName)) {
      LikeList likeList = repo.getLikeMap().get(accountName);
      for (int i = 0; i < likeList.getLikes().size(); i++) {
        if (likeList.getLikes().get(i).getUriWithoutSpecialChars().equals(uriWithoutSpecialChars)) {
          return likeList.getLikes().get(i);
        }
      }
    }
    return null;
  }
}
