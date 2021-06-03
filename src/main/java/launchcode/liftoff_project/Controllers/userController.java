package launchcode.liftoff_project.Controllers;

import launchcode.liftoff_project.Model.User;
import launchcode.liftoff_project.Model.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("userProfile")
public class userController {
    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String displayUserProfile( Model model, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null) {
            User theUser = authenticationController.getUserFromSession(session);
            if(theUser != null) {
                model.addAttribute("theUser", theUser);
                return "userProfile";
            }else{
                return "redirect:/login";
            }
        }else {
            return "redirect:/login";


        }
    }
}


//@Controller
//public class userController {
//
//    @Autowired
//    UserRepository userRepository;
//
//    public User getUserFromSession(HttpSession session){
//        Integer userId = (Integer) session.getAttribute(userSessionKey);
//
//        Optional<User> user = userRepository.findById(userId);
//
//        return user.get();
//    }
//
//    @GetMapping("/userProfile")
//    public String displayUser(Model model, User user, @RequestParam Map requestParams, @ModelAttribute @Valid userService userService, @ModelAttribute @Valid RegisterFormDTO registrationFormDTO, HttpServletRequest request, RedirectAttributes redirectAttributes){
//
//
//        redirectAttributes.addAttribute("theUser", user.getEmail());
//
//        return "userProfile";
//
//    }
//}
