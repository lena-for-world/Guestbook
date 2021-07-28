package projectB.guestbook.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void save(Post post) {
        // 동일한 이름 체크
        // 통과 하면 저장 (repository)
        post.setLiked(0);
        post.setHate(0);
        validateDuplicateWriter(post);
        writingRepository.save(post);
    }

    public void validateDuplicateWriter(Post post) {
        if(writingRepository.findPost(post).size() > 0) throw new IllegalArgumentException("동일한 작성자가 있습니다");
    }

    public void plusLike(String name) {
        System.out.println(name+"!!!!!!!!!!!!!!");
        writingRepository.plusLike(name);
    }

    public void plusHate(String name) {
        // HateCount가 10이상이면 포스트삭제
        checkHateCount(name);
        writingRepository.plusHate(name);
    }

    public void checkHateCount(String name) {
        if(writingRepository.checkHateCount(name)) {
            writingRepository.deletePost(name);
        }
    }

}