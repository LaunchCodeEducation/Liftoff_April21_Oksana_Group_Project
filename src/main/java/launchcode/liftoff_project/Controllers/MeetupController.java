package launchcode.liftoff_project.Controllers;

import launchcode.liftoff_project.Model.data.MeetupCategoryRepository;
import launchcode.liftoff_project.Model.data.MeetupRepository;
import launchcode.liftoff_project.Model.Meetup;
import launchcode.liftoff_project.Model.MeetupCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("meetups")
public class MeetupController {
    @Autowired
    private MeetupRepository meetupRepository;

    @Autowired
    private MeetupCategoryRepository meetupCategoryRepository;

    @GetMapping
    public String displayMeetups(@RequestParam(required = false) Integer categoryId, Model model) {
        if(categoryId == null) {
            model.addAttribute("title", "Trail Meetups");
            model.addAttribute("meetups", meetupRepository.findAll());
        } else {
            Optional<MeetupCategory> result = meetupCategoryRepository.findById(categoryId);
            if (result.isEmpty()) {
                model.addAttribute("title", "Invalid: A Meetup With a Category ID of " + categoryId + " Does Not Exist.");
            }else{
                MeetupCategory category = result.get();
                model.addAttribute("title", "Results For Meetups in Category" + category.getMeetupCategoryName());
                model.addAttribute("meetups", category.getMeetups());
            }
        }
        return "meetups/index";
    }

    @GetMapping("create")
    public String displayCreateMeetupsForm(Model model) {
        model.addAttribute("title", "Create A New Meetup");
        model.addAttribute(new Meetup());
        model.addAttribute("categories", meetupCategoryRepository.findAll());
        return "meetups/create";
    }

    @PostMapping("create")
    public String processCreateMeetupsForm(@Valid @ModelAttribute Meetup newMeetup, Errors errors, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Create A New Meetup");
            //model.addAttribute(new Meetup());
            return "meetups/create";
        }

        meetupRepository.save(newMeetup);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteMeetupsForm(Model model) {
        model.addAttribute("title", "Delete A Meetup");
        model.addAttribute("meetups", meetupRepository.findAll());
        return "meetups/delete";
    }

    @PostMapping("delete")
    public String processDeleteMeetupsForm(@RequestParam(required = false) int[] meetupIds) {
        if(meetupIds != null) {
            for (int id: meetupIds) {
                meetupRepository.deleteById(id);
            }
        }
        return "redirect:";
    }

    @GetMapping("details")
    public String displayMeetupDetails(@RequestParam Integer meetupId, Model model) {
        Optional<Meetup> result = meetupRepository.findById(meetupId);
        if(result.isEmpty()) {
            model.addAttribute("title", "Invalid: A meetup with ID " + meetupId + "does not seem to exist.");
        } else {
            Meetup meetup = result.get();
            model.addAttribute("title", meetup.getMeetupName() + " Details");
            model.addAttribute("meetup", meetup);
        }
        return "meetups/details";
    }

}