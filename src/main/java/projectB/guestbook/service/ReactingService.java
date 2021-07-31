package projectB.guestbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import projectB.guestbook.domain.Post;
import projectB.guestbook.repository.ReactingRepository;
import projectB.guestbook.repository.WritingRepository;

@Service
@RequiredArgsConstructor
public class ReactingService {

    private final WritingRepository writingRepository;
    private final ReactingRepository reactingRepository;

    public void plusLike(Long id) {
        reactingRepository.plusLike(id);
    }

    // HateCount가 10이상이면 포스트삭제
    public void plusHate(Long id) {
        if (checkHateCount(id) == true) {
            ;
        } else {
            reactingRepository.plusHate(id);
        }
    }

    public boolean checkHateCount(Long id) {
        if (reactingRepository.checkHateCount(id)) {
            writingRepository.deletePost(id);
            return true;
        } else {
            return false;
        }
    }

}
