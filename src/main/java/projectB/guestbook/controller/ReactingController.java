package projectB.guestbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import projectB.guestbook.service.ReactingService;

@Controller
@RequiredArgsConstructor
public class ReactingController {

    private final ReactingService reactingService;

    @GetMapping("/guestbooks/pushPlusLike")
    public String pushPlusLike(Long id) {
        reactingService.plusLike(id);
        return "redirect:/guestbooks";
    }

    @GetMapping("/guestbooks/pushPlusHate")
    public String pushPlusHate(Long id) {
        try {
            reactingService.plusHate(id);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return "redirect:/guestbooks";
    }
}
