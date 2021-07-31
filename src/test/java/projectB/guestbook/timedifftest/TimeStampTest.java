package projectB.guestbook.timedifftest;

import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import projectB.guestbook.domain.Post;
import projectB.guestbook.service.WritingService;

@SpringBootTest
@Transactional
public class TimeStampTest {

    @Autowired
    private WritingService writingService;

    @Test
    @DisplayName("1분 이후 게시글 작성")
    public void postAfterAMinute() {
        Post post = new Post();
        post.setName("nat");
        post.setDateTime(DateTime.now().toString());
        writingService.save(post);
        try {
            Thread.sleep(61000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Post post2 = new Post();
        post2.setName("nat");
        post2.setDateTime(DateTime.now().toString());
        writingService.save(post2);
        System.out.println("post.getDateTime() = " + post.getDateTime());
        System.out.println("post2.getDateTime() = " + post2.getDateTime());
        Assertions.assertThat(post.getDateTime()).isNotEqualTo(post2.getDateTime());
    }
}
