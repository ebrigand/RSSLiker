package com.mrm.rss.service.async;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import com.mrm.rss.xml.model.Story;

/**
 * Manage the deferredResults list for the async requests
 * 
 * @author ebrigand
 * 
 */
@Service(value = "deferredResultContainer")
public class DeferredResultContainer {

  private final Set<DeferredResult<List<Story>>> deferredResults = Collections.synchronizedSet(new HashSet<DeferredResult<List<Story>>>());

  public void register(DeferredResult<List<Story>> deferredResult) {
    deferredResults.add(deferredResult);
  }

  public void updateAllResults(List<Story> value) {
    for (DeferredResult<List<Story>> deferredResult : deferredResults) {
      deferredResult.setResult(value);
    }
  }

  public void remove(DeferredResult<List<Story>> deferredResult) {
    deferredResults.remove(deferredResult);
  }
}
