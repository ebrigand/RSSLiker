package com.mrm.rss.xml.service;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.mock.env.MockPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mrm.rss.init.WebAppConfig;
import com.mrm.rss.xml.model.Like;
import com.mrm.rss.xml.model.Story;

@ContextConfiguration(classes = WebAppConfig.class, initializers = XMLRepositoryTest.PropertyMockingApplicationContextInitializer.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class XMLRepositoryTest {

  public static final String ACCOUNT_NAME = "myName";

  public static final String URI_WITHOUT_SPECIAL_CHARS = "sdfsdfds5464";

  @Resource
  private LikeRepository<Like> likeRepository;

  @Resource
  private StoryRepository<Story> storyRepository;

  @Test
  public void testSaveAndLoadLike() throws Exception {
    Like like = new Like();
    like.setAccountName(ACCOUNT_NAME);
    like.setIsLike(false);
    like.setUriWithoutSpecialChars(URI_WITHOUT_SPECIAL_CHARS);
    likeRepository.saveOrUpdate(like);
    like = likeRepository.find(ACCOUNT_NAME, URI_WITHOUT_SPECIAL_CHARS);
    Assert.assertNotNull(like);
  }

  @Test
  public void testSaveAndLoadStoryWithCountIncremented() throws Exception {
    Story story = new Story();
    story.setCount(0);
    story.setUriWithoutSpecialChars(URI_WITHOUT_SPECIAL_CHARS);
    Integer countIncremented = story.getCount() + 1;
    story.setCount(countIncremented);
    storyRepository.saveOrUpdate(story);
    story = storyRepository.find(URI_WITHOUT_SPECIAL_CHARS);
    Assert.assertNotNull(story);
    Assert.assertEquals(countIncremented, story.getCount());
  }

  /**
   * override the property xml.file.repository (to define a different repo file)
   * 
   * @author ebrigand
   * 
   */
  public static class PropertyMockingApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
      MutablePropertySources propertySources = applicationContext.getEnvironment().getPropertySources();
      MockPropertySource mockEnvVars = new MockPropertySource().withProperty("xml.file.repository", "test-repo.xml");
      propertySources.replace(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, mockEnvVars);
    }
  }
}
