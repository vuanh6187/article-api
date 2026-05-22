package mm.com.mytel.articleapi.controller;

import lombok.RequiredArgsConstructor;
import mm.com.mytel.articleapi.dto.ArticleSummaryResponse;
import mm.com.mytel.articleapi.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ArticleService articleService;

    @GetMapping({"/", "/home"})
    public String home(@RequestParam(value = "tag", required = false) String tag, Model model) {
        List<ArticleSummaryResponse> articles;
        if (tag != null && !tag.isBlank()) {
            String keyword = tag.trim();
            articles = articleService.findArticleByTag(keyword);
            model.addAttribute("searchTag", keyword);
        } else {
            articles = articleService.findAllSummaries();
        }
        model.addAttribute("articles", articles);
        return "home";
    }
}
