package projectB.guestbook.service;

import java.time.DateTimeException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;
import projectB.guestbook.domain.Post;
import projectB.guestbook.repository.WritingRepository;

@Service
@RequiredArgsConstructor
public class WritingService {

    private final WritingRepository writingRepository;

    // 모든 포스트를 select하는 repository 함수 호출
    public List<Post> getPosts() {
        return writingRepository.findAllPosts();
    }

    // 동일한 이름 체크
    // 통과 하면 저장 (repository)
    public void save(Post post) {
        post.setLiked(0);
        post.setHate(0);
        post.setDateTime(DateTime.now().toString("yyyy년MM월dd일 HH시mm분ss초"));
        if (validateContinuousPost(post)) {
            writingRepository.save(post);
        } else {
            throw new DateTimeException("방명록은 1분에 1개만 작성할 수 있습니다");
        }
        /* 동일한 이름에 대한 검증은 필요하지 않음! 게시글 구별을 이름으로 할 예정이기 때문 (∵ 로그인 구현이 없음)*/
    }

    public boolean validateContinuousPost(Post post) {
        List<Post> list = writingRepository.findPostByName(post.getName());
        if (list.size() == 0) {
            return true;
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy년MM월dd일 HH시mm분ss초");
        String postTimeString = list.get(list.size() - 1).getDateTime();
        DateTime postTimeDt = formatter.parseDateTime(postTimeString);
        DateTime nowTime = DateTime.now();
        Duration duration = new Duration(postTimeDt, nowTime);
        if (duration.getStandardSeconds() <= 60) {
            return false;
        } else {
            return true;
        }
    }

    /*public boolean writerHasPosts(Long id) {
        List<Post> list = writingRepository.findPost(id);
        if(list.size() > 0) {
            String name = list.get(0).getName();
            if(writingRepository.findPostByName(name).size() > 0) {
                return true;
            } else return false;
        } else return false;
    }*/

}