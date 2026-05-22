package mm.com.mytel.articleapi.controller;

import lombok.RequiredArgsConstructor;
import mm.com.mytel.articleapi.dto.ArticleSummaryResponse;
import mm.com.mytel.articleapi.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ArticleService articleService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        List<ArticleSummaryResponse> articles = articleService.findAllSummaries();
        model.addAttribute("articles", articles);
        return "home";
    }
}
