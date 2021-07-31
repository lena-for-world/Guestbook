package projectB.guestbook.likehatetest;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import projectB.guestbook.domain.Post;
import projectB.guestbook.repository.ReactingRepository;
import projectB.guestbook.repository.WritingRepository;

@SpringBootTest
@Transactional
public class likeHateTest {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private WritingRepository writingRepository;
    @Autowired
    private ReactingRepository reactingRepository;

    @Test
    @DisplayName("포스트 추가 테스트")
    public void findTest() {
        //given
        Post post = new Post();
        post.setName("natasha");
        post.setContent("ha......");
        post.setLiked(0);
        post.setHate(0);

        //when
        writingRepository.save(post);
        List<Post> list = writingRepository.findAllPosts();

        //then
        Assertions.assertThat(1).isEqualTo(list.size());
    }

    @Test
    @DisplayName("좋아요 증가 테스트")
    public void addLikeTest() {
        //given
        Post post = new Post();
        post.setName("natasha");
        post.setContent("ha......");
        post.setLiked(0);
        post.setHate(0);

        //when
        writingRepository.save(post);
        reactingRepository.plusLike(post.getId());
        System.out
            .println("writingRepository = " + writingRepository.findAllPosts().get(0).getLiked());

        //then
        Assertions.assertThat(1).isEqualTo(writingRepository.findAllPosts().get(0).getLiked());
    }


    @Test
    @DisplayName("id 체크")
    public void checkid() {
        // given
        Post post = new Post();
        post.setName("natasha");
        post.setContent("ha......");
        post.setLiked(0);
        post.setHate(0);
        Post post2 = new Post();
        post2.setName("nat");
        post2.setContent("ha...!!!!!!!!!...");
        post2.setLiked(0);
        post2.setHate(0);
        // when
        writingRepository.save(post);
        writingRepository.save(post2);
        post = writingRepository.findPost(post.getId()).get(0);
        post2 = writingRepository.findPost(post2.getId()).get(0);

        //then
        System.out.println("post2.getId() = " + post2.getId());
        Assertions.assertThat(post.getId()).isNotEqualTo(post2.getId());
    }
}
