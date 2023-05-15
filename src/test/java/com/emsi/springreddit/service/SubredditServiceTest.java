package com.emsi.springreddit.service;

import com.emsi.springreddit.dto.request.SubredditRequest;
import com.emsi.springreddit.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.NoSuchElementException;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = "classpath:insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SubredditServiceTest {

    @Autowired
    private SubredditService subredditService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldNotCreateSubredditReasonMissingUser(){
        var subredditRequest = new SubredditRequest();
        subredditRequest.setName("test");
        subredditRequest.setDescription("test");
        Assertions.assertThrowsExactly(
                DataIntegrityViolationException.class,
                () -> subredditService.createSubreddit(subredditRequest, null)
        );
    }

    @Test
    public void shouldCreateSubreddit(){
        var subredditRequest = new SubredditRequest();
        subredditRequest.setName("test");
        subredditRequest.setDescription("test");
        subredditService.createSubreddit(subredditRequest, userRepository.findByUsername("admin").orElseThrow());
        Assertions.assertNotNull(subredditService.getSubredditByName("test"));
    }
    @Test
    public void shouldCreateSubredditRemoveWhitespace(){
        var subredditRequest = new SubredditRequest();
        subredditRequest.setName(" te st ");
        subredditRequest.setDescription("test");
        subredditService.createSubreddit(subredditRequest, userRepository.findByUsername("admin").orElseThrow());
        Assertions.assertNotNull(subredditService.getSubredditByName("test"));
    }

    @Test
    public void shouldNotCreateSubredditReasonDuplicate(){
        var subredditRequest = new SubredditRequest();
        subredditRequest.setName("test");
        subredditRequest.setDescription("test");
        subredditService.createSubreddit(subredditRequest, userRepository.findByUsername("admin").orElseThrow());
        Assertions.assertThrowsExactly(
                DataIntegrityViolationException.class,
                () -> subredditService.createSubreddit(subredditRequest,
                        userRepository.findByUsername("admin").orElseThrow())
        );
    }

    @Test
    public void shouldNotCreateSubredditReasonMissingName(){
        var subredditRequest = new SubredditRequest();
        subredditRequest.setDescription("test");
        Assertions.assertThrowsExactly(
                DataIntegrityViolationException.class,
                () -> subredditService.createSubreddit(subredditRequest,
                        userRepository.findByUsername("admin").orElseThrow())
        );
    }

    @Test
    public void shouldNotCreateSubredditReasonMissingDescription(){
        var subredditRequest = new SubredditRequest();
        subredditRequest.setName("test");
        Assertions.assertThrowsExactly(
                DataIntegrityViolationException.class,
                () -> subredditService.createSubreddit(subredditRequest,
                        userRepository.findByUsername("admin").orElseThrow())
        );
    }

    @Test
    public void shouldGetSubredditByName(){
        Assertions.assertNotNull(subredditService.getSubredditByName("java"));
    }

    @Test
    public void shouldNotGetSubredditByNameReasonNotFound(){
        Assertions.assertThrowsExactly(
                NoSuchElementException.class,
                () -> subredditService.getSubredditByName("wrongSubreddit")
        );
    }

    @Test
    public void shouldUpdateSubreddit(){
        var subredditRequest = new SubredditRequest();
        subredditRequest.setName("test");
        subredditRequest.setDescription("test");
        subredditRequest.setNewOwner("admin");
        subredditService.updateSubreddit(subredditRequest, 1L);
        Assertions.assertEquals("test", subredditService.getSubredditByName("test").getName());
        Assertions.assertEquals("test", subredditService.getSubredditByName("test").getDescription());
        Assertions.assertEquals("admin", subredditService.getSubredditByName("test").getUser().getUsername());
    }

    @Test
    public void shouldUpdateWithMissingNewOwner(){
        var subredditRequest = new SubredditRequest();
        subredditRequest.setName("test");
        subredditRequest.setDescription("test");
        subredditService.updateSubreddit(subredditRequest, 1L);
        Assertions.assertEquals("test", subredditService.getSubredditByName("test").getName());
        Assertions.assertEquals("test", subredditService.getSubredditByName("test").getDescription());
        Assertions.assertEquals("admin", subredditService.getSubredditByName("test").getUser().getUsername());
    }
    @Test
    public void shouldUpdateSubredditWithMissingName(){
        var subredditRequest = new SubredditRequest();
        subredditRequest.setDescription("test");
        subredditService.updateSubreddit(subredditRequest, 1L);
        Assertions.assertEquals("java", subredditService.getSubredditByName("java").getName());
        Assertions.assertEquals("test", subredditService.getSubredditByName("java").getDescription());
        Assertions.assertEquals("admin", subredditService.getSubredditByName("java").getUser().getUsername());

    }

    @Test
    public void shouldUpdateSubredditWithMissingDescription(){
        var subredditRequest = new SubredditRequest();
        subredditRequest.setName("test");
        subredditService.updateSubreddit(subredditRequest, 1L);
        Assertions.assertEquals("test", subredditService.getSubredditByName("test").getName());
        Assertions.assertEquals("Java subreddit", subredditService.getSubredditByName("test").getDescription());
        Assertions.assertEquals("admin", subredditService.getSubredditByName("test").getUser().getUsername());
    }

    @Test
    public void shouldUpdateSubredditWithMissingNameAndDescription(){
        var subredditRequest = new SubredditRequest();
        subredditRequest.setNewOwner("user");
        subredditService.updateSubreddit(subredditRequest, 1L);
        Assertions.assertEquals("java", subredditService.getSubredditByName("java").getName());
        Assertions.assertEquals("Java subreddit", subredditService.getSubredditByName("java").getDescription());
        Assertions.assertEquals("user", subredditService.getSubredditByName("java").getUser().getUsername());
    }

    @Test
    public void shouldUpdateSubredditWithMissingDescriptionAndNewOwner(){
        var subredditRequest = new SubredditRequest();
        subredditRequest.setName("test");
        subredditService.updateSubreddit(subredditRequest, 1L);
        Assertions.assertEquals("test", subredditService.getSubredditByName("test").getName());
        Assertions.assertEquals("Java subreddit", subredditService.getSubredditByName("test").getDescription());
        Assertions.assertEquals("admin", subredditService.getSubredditByName("test").getUser().getUsername());
    }

    @Test
    public void shouldUpdateSubredditWithMissingNameAndNewOwner(){
        var subredditRequest = new SubredditRequest();
        subredditRequest.setDescription("test");
        subredditService.updateSubreddit(subredditRequest, 1L);
        Assertions.assertEquals("java", subredditService.getSubredditByName("java").getName());
        Assertions.assertEquals("test", subredditService.getSubredditByName("java").getDescription());
        Assertions.assertEquals("admin", subredditService.getSubredditByName("java").getUser().getUsername());
    }

    @Test
    public void shouldNotUpdateSubredditReasonDuplicate(){
        var subredditRequest = new SubredditRequest();
        subredditRequest.setName("java");
        subredditRequest.setDescription("test");
        Assertions.assertThrowsExactly(
                DataIntegrityViolationException.class,
                () -> subredditService.updateSubreddit(subredditRequest, 2L)
        );
    }

    @Test
    public void shouldUpdateSubredditWithMissingNameAndDescriptionAndNewOwner(){
        var subredditRequest = new SubredditRequest();
        subredditService.updateSubreddit(subredditRequest, 1L);
        Assertions.assertEquals("java", subredditService.getSubredditByName("java").getName());
        Assertions.assertEquals("Java subreddit", subredditService.getSubredditByName("java").getDescription());
        Assertions.assertEquals("admin", subredditService.getSubredditByName("java").getUser().getUsername());
    }

    @Test
    public void shouldNotUpdateSubredditReasonNotFound(){
        var subredditRequest = new SubredditRequest();
        subredditRequest.setName("test");
        subredditRequest.setDescription("test");
        subredditRequest.setNewOwner("admin");
        Assertions.assertThrowsExactly(
                NoSuchElementException.class,
                () -> subredditService.updateSubreddit(subredditRequest, 100L)
        );
    }

    @Test
    public void shouldNotUpdateSubredditReasonNewOwnerNotFound(){
        var subredditRequest = new SubredditRequest();
        subredditRequest.setName("test");
        subredditRequest.setDescription("test");
        subredditRequest.setNewOwner("wrongUser");
        Assertions.assertThrowsExactly(
                NoSuchElementException.class,
                () -> subredditService.updateSubreddit(subredditRequest, 1L)
        );
    }

    @Test
    public void shouldUpdateSubredditRemoveWhitespacesFromName(){
        var subredditRequest = new SubredditRequest();
        subredditRequest.setName(" te st ");
        subredditRequest.setDescription("test");
        subredditService.createSubreddit(subredditRequest, userRepository.findByUsername("admin").orElseThrow());
        Assertions.assertEquals("test", subredditService.getSubredditByName("test").getName());
    }

    @Test
    public void shouldDeleteSubreddit(){
        subredditService.deleteSubreddit(3L);
        Assertions.assertThrowsExactly(
                NoSuchElementException.class,
                () -> subredditService.getSubredditByName("java")
        );
    }

    @Test
    public void shouldNotDeleteSubredditReasonNotFound() {
        Assertions.assertThrowsExactly(
                NoSuchElementException.class,
                () -> subredditService.deleteSubreddit(100L)
        );
    }

}
