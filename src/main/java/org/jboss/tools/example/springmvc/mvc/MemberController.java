package org.jboss.tools.example.springmvc.mvc;

import javax.validation.Valid;

import org.jboss.tools.example.springmvc.domain.Member;
import org.jboss.tools.example.springmvc.repo.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.InputStream;

@Controller
@RequestMapping(value="/")
public class MemberController
{
    @Autowired
    private MemberDao memberDao;

    @RequestMapping(method=RequestMethod.GET)
    public String displaySortedMembers(Model model)
    {
        String resource = "classpath:api/API_BACKDETAIL.txt";
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(resource);
        String filePath = this.getClass().getClassLoader().getResource(resource).getFile();
        System.out.println("classPath: " + filePath);
        model.addAttribute("newMember", new Member());
        model.addAttribute("members", memberDao.findAllOrderedByName());
        return "index";
    }

    @RequestMapping(method=RequestMethod.POST)
    public String registerNewMember(@Valid @ModelAttribute("newMember") Member newMember, BindingResult result, Model model)
    {
        if (!result.hasErrors()) {
            memberDao.register(newMember);
            return "redirect:/";
        }
        else {
            model.addAttribute("members", memberDao.findAllOrderedByName());
            return "index";
        }
    }
}
