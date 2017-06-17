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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Controller
@RequestMapping(value="/")
public class MemberController
{
    @Autowired
    private MemberDao memberDao;

    @RequestMapping(method=RequestMethod.GET)
    public String displaySortedMembers(Model model)
    {
        System.out.println("init");
		System.out.println(this.getClass().getClassLoader().getResourceAsStream("api/API_BACKDETAIL.txt"));
		System.out.println(this.getClass().getClassLoader().getResourceAsStream("applicationContext.xml"));
		System.out.println(this.getClass().getResourceAsStream("/applicationContext.xml"));
		System.out.println(Thread.currentThread().getContextClassLoader().getResourceAsStream("applicationContext.xml"));
		System.out.println(this.getClass().getResource("/"));
		System.out.println(this.getClass().getClassLoader().getResource(""));
		this.getClass().getClassLoader();
		System.out.println(ClassLoader.getSystemClassLoader());
        System.out.println("classPath: " + this.getClass().getResource("./"));
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
